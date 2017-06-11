package mx.ipn.web.servlet.listadoxalumno;

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
import mx.ipn.web.dao.EscuelaDAO;
import mx.ipn.web.model.Alumno;
import mx.ipn.web.model.Escuela;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

/**
 * Servlet implementation class ReportBinarioXLS
 */
@WebServlet("/ReportBinarioXLS")
public class ReportBinarioXLS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String RUTA_JASPER = "/WEB-INF/classes/mx/ipn/web/report/ListoAlumno.jasper";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportBinarioXLS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		List<Alumno> alumnos = null;//Inicializar lista
		AlumnoDAO dao = null; //Inicializar DAO
		InputStream is = getReporte(); //Inicializar abrir el flujo para obtener el reporte
		//Tipo de contenido a desplegar
		response.setContentType( "application/vnd.ms-excel" );

		if( is == null ){
			System.err.println( "El reporte es nulo" );
			return;
		}

		dao = new AlumnoDAO( );
		dao.inicializarConexion( );
		alumnos = dao.getAlumnos();

		//cosos de reporte
		JRXlsxExporter exporter = new JRXlsxExporter();
	  
		SimpleXlsxExporterConfiguration config = new SimpleXlsxExporterConfiguration( );
		//Obtener el recurso         
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

		//map.put( "Listado", "Escuela" );

		return null;
	}
}

	
		
		
		
		


	