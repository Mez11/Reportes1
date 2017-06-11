package mx.ipn.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.ipn.web.dao.AlumnoDAO;
import mx.ipn.web.model.Alumno;

/**
 * Servlet para "enrutar" la petici&oacute;n, 
 * seg&uacute;n sea:
 * <ul>
 * <li>PDF</li>
 * <li>DOC</li>
 * <li>XLS</li>
 * </ul>
 * Lo que hace es obtener de la base de datos el alumno con el 
 * ID indicado, para estos datos mandarlos posteriormente a los servlets
 * coorrrespondientes.
 * Par&aacute;metros:
 * <ul>
 * <li>ID del alumno</li>
 * <li>TIPO reporte (pdf,doc,xls)</li>
 * </ul>
 * Salida:
 * Redirecci&oacute;n al servlet del tipo que puede despachar.
 */
@WebServlet("/FotoLogica")
public class FotoLogica extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Alumno alumno = null;
		AlumnoDAO  dao = null;
		String idAlumnoString = null;
		String tipoReporte = null;
		
		idAlumnoString = request.getParameter( "idAlumno" );
		if( idAlumnoString == null || idAlumnoString.isEmpty() ){
			   System.out.println("EL id es nulo--@.q-");
			   response.sendError( 400, "Es nulo el parametro id"  );//Recibe el codigo HTTP y descricion del error
			   return;
		   }
		 Integer id = Integer.parseInt( idAlumnoString );
		
		tipoReporte = request.getParameter( "tipoReporte" );
		if( tipoReporte == null || tipoReporte.isEmpty() ){
			response.sendError(400,"Es nulo  el reporte");
			//hacer cosas para cuando tipoReporte es nulo
			return;
		}
		
		dao = new AlumnoDAO( );
		dao.inicializarConexion( );
		alumno = dao.getAlumnoById( Integer.parseInt( idAlumnoString ) );
		
		if( alumno == null ){
			response.sendError(400,"Es nulo el alumno @.@");
			//hacer cosas para avisar que el alumno no se encontro.
			return;
		}
		
		//ya tenemos el tipo reporte, el alumno y asi
		
		elecion(tipoReporte, request, response, alumno);
	}

	/**
	 * Elige el tipo de reporte y lo redirecciona
	 * @param tipoReporte Ya sea pdf, doc, xls
	 * @param request para poner los atributos en la peticion
	 * @param response Para redirigir al servlet coorrespondiente
	 * @param alumno Para obtener la informaci&oacute;n
	 */
	public void elecion( String tipoReporte, HttpServletRequest request, HttpServletResponse response, Alumno alumno) throws IOException {
        switch ( tipoReporte ) {
            case "pdf":
            	System.out.println ("Mostrara el reporte tipo PDF");
            	response.sendRedirect( "ServletPDF?id=" + alumno.getId() + "&nombre=" + alumno.getNombre() );
            	break;
            case "doc": 
            	System.out.println ("Mostrara el reporte tipo DOC"); 
				response.sendRedirect( "ServletDOC?id=" + alumno.getId() + "&nombre=" + alumno.getNombre() );
            	break;
            case "xls": 
            	System.out.println ("Mostrara el reporte tipo XLS"); 
				response.sendRedirect( "ServletXLS?id=" + alumno.getId() + "&nombre=" + alumno.getNombre() );
            	break;
            default: 
            	System.out.println ("No es ningun tipo de reporte"); 
            	break;
        }//end switch
    }//end eleccion
}//end class
