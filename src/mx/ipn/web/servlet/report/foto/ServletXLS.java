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

import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;

/**
 * Servlet implementation class ServletXLS
 */
@WebServlet("/ServletXLS")
public class ServletXLS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String RUTA_JASPER = "/WEB-INF/classes/mx/ipn/web/report/Foto.jasper";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletXLS() {
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
		   //Posibles erores
		   if( idString == null){
			   //Impresion de consola
			   System.out.println("EL id es nulo--@.#-");
			 //Recibe el codigo HTTP y descricion del error
			   response.sendError( 400, "Es nulo el parametro id"  );
			   return;
		   }
		   Integer id = Integer.parseInt( idString ); 
		   //Validaciones
		   if(nombre == null || nombre.isEmpty()){
			   response.sendError( 400,"Es nulo el parametro nombre");
			   return;
		   }
		   //Implementacion
		   imprimirReporte( response, nombre, id );
		   
		}

		private void imprimirReporte( HttpServletResponse response, String nombre, int id ) throws IOException{
			
			JRXlsxExporter exporter = new JRXlsxExporter();
			SimpleXlsxExporterConfiguration config = new SimpleXlsxExporterConfiguration();
			try {
				JasperPrint print = JasperFillManager.fillReport( getReport(), getParams(nombre ,id));
				
				//printer, output Exporter
				exporter.setConfiguration(config);
				exporter.setExporterInput( new SimpleExporterInput(print)) ;
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput( response.getOutputStream() ));
				response.setContentType("application/vnd.ms-excel");
				exporter.exportReport();
				
			} catch (JRException e) {
				e.printStackTrace();
			}
		}
		
		private Map<String,Object> getParams(String nombre , int id){
			//Tipo de la llave y tipo del valor
			Map <String,Object> mapa;
			mapa = new HashMap<String,Object>();
			//Pasar parametros de acuerdo al nombre,JasperReport
			mapa.put("alumnoNombre", nombre);
			mapa.put("alumnoId", id);
			
			return mapa;
			
		}
		//Abre el flujo para obtener el reporte 
		private InputStream getReport( ){
			return getServletContext().getResourceAsStream(RUTA_JASPER);
			
		}
	}

	


