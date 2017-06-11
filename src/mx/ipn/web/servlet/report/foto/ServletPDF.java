package mx.ipn.web.servlet.report.foto;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 * Servlet implementation class ServletPDF
 * @param id alumno
 * @param nombre alumno
 * 
 */
@WebServlet("/ServletPDF")
public class ServletPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String RUTA_JASPER = "/WEB-INF/classes/mx/ipn/web/report/Foto.jasper";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPDF() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//Obtener los parametros
	   String idString = request.getParameter("id");
	   String nombre =  request.getParameter("nombre");
	   
	   if( idString == null){
		   System.out.println("EL id es nulo--@.q-");
		   response.sendError( 400, "Es nulo el parametro id"  );//Recibe el codigo HTTP y descricion del error
		   return;
	   }
	   Integer id = Integer.parseInt( idString );
	   //Validaciones
	   if(nombre == null || nombre.isEmpty()){
		   response.sendError( 400,"Es nulo el param nombre");
		   return;
	   }
	   //Implementacion
	   imprimirReporte( response, nombre, id );
	   
	}

	private void imprimirReporte( HttpServletResponse response, String nombre, int id ) throws IOException{
		JRPdfExporter exporter = new JRPdfExporter();
		SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
		
		try {
			JasperPrint print = JasperFillManager.fillReport( getReport(), getParams(nombre ,id));
			
			//printer, output Exporter
			exporter.setConfiguration(config);
			exporter.setExporterInput( new SimpleExporterInput(print)) ;
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput( response.getOutputStream() ));
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	private Map<String,Object> getParams(String nombre , int id){
		//Tipo de la llave y tipo del valor
		Map <String,Object> mapa;
		mapa = new HashMap<String,Object>();
		//Pasar parametros en de JasperReport
		mapa.put("alumnoNombre", nombre);
		mapa.put("alumnoId", id);
		
		return mapa;
		
	}
	
	private InputStream getReport( ){
		return getServletContext().getResourceAsStream(RUTA_JASPER);
		
	}
	

}
