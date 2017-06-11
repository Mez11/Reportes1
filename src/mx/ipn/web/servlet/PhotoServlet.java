package mx.ipn.web.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.ipn.web.dao.AlumnoDAO;
import mx.ipn.web.model.Alumno;

/**
 * Servlet implementation class PhotoServlet
 */
@WebServlet("/PhotoServlet")
/**
 * Servlet para mostrar imagenes de la base de datos.
 * Recibe: "id", el ID del alumno.
 */
public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AlumnoDAO dao = null;
		Alumno alumno = null;
		String idAlumnoString;
		int idAlumno;
		
		idAlumnoString = request.getParameter( "id" );
		if( idAlumnoString == null || idAlumnoString.isEmpty() ){
			response.getWriter( ).println( "No se encontro el ID" );
			return;
		}
		
		idAlumno = Integer.parseInt( idAlumnoString );
		
		dao = new AlumnoDAO( );
		dao.inicializarConexion( );
		alumno = dao.getAlumnoById( idAlumno );
		
		if( alumno == null ){
			response.getWriter().println( "No se encontro el alumno" );
			return;
		}
		
		if( alumno.getFoto() == null ){
			response.getWriter( ).println( "No se encontro la fotografia" );
			return;
		}
		
		response.setContentType( "image/jpg" );
		OutputStream outputStream = response.getOutputStream( );//Servidor->Cliente
		outputStream.write( alumno.getFoto( ) );
	}

	

}
