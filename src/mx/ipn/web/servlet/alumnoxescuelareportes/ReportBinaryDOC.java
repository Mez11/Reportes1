package mx.ipn.web.servlet.alumnoxescuelareportes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.ipn.web.dao.AlumnoDAO;
import mx.ipn.web.model.Alumno;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * Servlet implementation class ReportBinary
 */
@WebServlet("/ReportBinaryDOC")
public class ReportBinaryDOC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String RUTA_JASPER = "/WEB-INF/classes/mx/ipn/web/report/alumno_por_carrera.jasper";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Alumno> alumnos = null;
		AlumnoDAO dao = null;
		InputStream is = getReporte();
		
		response.setContentType( "application/msword" );
		
		if( is == null ){
			System.err.println( "El reporte es nulo" );
			return;
		}
		
		dao = new AlumnoDAO( );
		dao.inicializarConexion( );
		alumnos = dao.getAlumnosPorEscuela( 1 );
		
		//cosos de reporte
		JRDocxExporter exporter = new JRDocxExporter( );
		SimpleDocxExporterConfiguration config = new SimpleDocxExporterConfiguration( );
		
		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource( alumnos );
		try {
			//Comunicacion de la fuente de datos llenando el templete 
			JasperPrint printer = JasperFillManager.fillReport( is, getPameters( ), source );
			
			exporter.setConfiguration( config );
			exporter.setExporterInput( new SimpleExporterInput( printer ) );
			exporter.setExporterOutput( new SimpleOutputStreamExporterOutput( response.getOutputStream( ) ) );
			exporter.exportReport( );
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	private InputStream getReporte( ){
		//Ruta de donde proviene el compilado del archivo
		System.out.println( getServletContext( ).getRealPath( RUTA_JASPER ) );
		return getServletContext( ).getResourceAsStream( RUTA_JASPER );
	}
	
	private Map<String,Object> getPameters( ){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put( "CarreraNombre", "Andrea" );
		
		return map;
	}

} 
