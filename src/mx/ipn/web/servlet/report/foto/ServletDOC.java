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
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;


/**
 * Servlet implementation class ServletDOC
 */
@WebServlet("/ServletDOC")
public class ServletDOC extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String RUTA_JASPER = "/WEB-INF/classes/mx/ipn/web/report/Foto.jasper";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDOC() {
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
			   System.out.println("EL id es nulo--@.@--");
			   response.sendError( 400, "Es nulo el parametro id @.@"  );//Recibe el codigo HTTP y descricion del error
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
			JRDocxExporter exporter = new JRDocxExporter();
			SimpleDocxExporterConfiguration config = new SimpleDocxExporterConfiguration();
			
			
			
			try {
				JasperPrint print = JasperFillManager.fillReport( getReport(), getParams(nombre ,id));
				
				//printer, output Exporter
				exporter.setConfiguration(config);
				exporter.setExporterInput( new SimpleExporterInput(print)) ;
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput( response.getOutputStream() ));
				response.setContentType( "application/msword" );
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

	


