package mx.ipn.web.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.ipn.web.model.Alumno;
import mx.ipn.web.model.Escuela;


public class AlumnoDAO {



	private  static final String SQL_INSERT=
			"insert into Alumno"
					+"(nombre,apellido,foto,idEscuela)"
					+"values (?,?,?,?)";



	private static final String SQL_SELECT_WHERE_ID =
			"SELECT alumno.id AS idAlumno, alumno.nombre AS nombreAlumno, "
					+" alumno.apellido AS apellidoAlumno, "
					+ "alumno.foto AS fotoAlumno,"
					+ " escuela.id AS idEscuela,"
					+ "escuela.nombre AS nombreEscuela "
					+"FROM Alumno AS alumno "
					+"INNER JOIN Escuela AS escuela "
					+"ON escuela.id = alumno.idEscuela where alumno.id=?";



	private static final String SQL_SELECT_WHERE =
			"SELECT alumno.id AS idAlumno, alumno.nombre AS nombreAlumno, "
					+" alumno.apellido AS apellidoAlumno, "
					+ "alumno.foto AS fotoAlumno, "
					+ " escuela.id AS idEscuela, "
					+ "escuela.nombre AS nombreEscuela "
					+"FROM Alumno AS alumno "
					+"INNER JOIN Escuela AS escuela " 
					+"ON escuela.id = alumno.idEscuela";
	
	private static final String SQL_SELECT_WHERE_ESCUELA =
			"SELECT alumno.id AS idAlumno, alumno.nombre AS nombreAlumno, "
					+" alumno.apellido AS apellidoAlumno, "
					+ "alumno.foto AS fotoAlumno, "
					+ " escuela.id AS idEscuela, "
					+ "escuela.nombre AS nombreEscuela "
					+"FROM Alumno AS alumno "
					+"INNER JOIN Escuela AS escuela " 
					+"ON escuela.id = alumno.idEscuela "
					+ "WHERE escuela.id = ?";


	//"select alumno.id, alumno.nombre, alumno.apellido, alumno.foto ,escuela.nombre"
	//  +" from Alumno as alumno"
	//+"inner join Escuela as escuela"
	// +"on escuela.id = alumno.idEscuela";
	//conexion
	private Connection con;

	public List<Alumno>  getAlumnos(){
		List<Alumno> list = null;
		try {

			System.out.println(SQL_SELECT_WHERE);
			PreparedStatement r = con.prepareStatement( SQL_SELECT_WHERE);



			ResultSet rs =r.executeQuery();//Para ejecutar el Query
			list = new ArrayList<Alumno> ();
			Alumno x=null;
			//  Boolean w = new  Boolean(true);
			while(rs.next()){
				x=new Alumno();
				Escuela escuela = null;
				x.setId(rs.getInt("idAlumno") );
				x.setNombre(rs.getString("nombreAlumno"));
				x.setApellido(rs.getString("apellidoAlumno"));
				x.setFoto(rs.getBytes("fotoAlumno"));
				//Parte de inner join mapeo @.@
				escuela = new Escuela( );
				escuela.setId( rs.getInt("idEscuela") );
				escuela.setNombre( rs.getString("nombreEscuela") );

				x.setEscuela(escuela);


				list.add(x);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}//end list

	public List<Alumno>  getAlumnosPorEscuela( int idEscuela ){
		List<Alumno> list = null;
		try {
			System.out.println( SQL_SELECT_WHERE_ESCUELA );
			PreparedStatement r = con.prepareStatement( SQL_SELECT_WHERE_ESCUELA );
			r.setInt( 1, idEscuela );
			ResultSet rs =r.executeQuery();//Para ejecutar el Query
			list = new ArrayList<Alumno> ();
			Alumno x=null;
			//  Boolean w = new  Boolean(true);
			while(rs.next()){
				x=new Alumno();
				Escuela escuela = null;
				x.setId(rs.getInt("idAlumno") );
				x.setNombre(rs.getString("nombreAlumno"));
				x.setApellido(rs.getString("apellidoAlumno"));
				x.setFoto(rs.getBytes("fotoAlumno"));
				//Parte de inner join mapeo @.@
				escuela = new Escuela( );
				escuela.setId( rs.getInt("idEscuela") );
				escuela.setNombre( rs.getString("nombreEscuela") );
				x.setEscuela(escuela);

				list.add(x);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}//end list


	//Metodo by ID
	public Alumno getAlumnoById(Integer id){ //int /Si es clase tivo tiene metodos y si es primitivo no tiene metodos @-@
		List<Alumno> list = null;
		try {
			System.out.println( SQL_SELECT_WHERE_ID );
			PreparedStatement rt = con.prepareStatement(SQL_SELECT_WHERE_ID);
			rt.setInt(1,id );
			ResultSet t = rt.executeQuery();
			Alumno a = null;
			list = new ArrayList<Alumno>( );//INicializar lista
			while(t.next()){
				a= new Alumno();
				Escuela escuela = new Escuela();
				a.setId(t.getInt("idAlumno"));
				a.setNombre(t.getString("nombreAlumno"));
				a.setApellido(t.getString("apellidoAlumno"));
				a.setFoto(t.getBytes("fotoAlumno"));

				//Parte de inner join mapeo @.@
				escuela = new Escuela( );
				escuela.setId( t.getInt("idEscuela") );
				escuela.setNombre( t.getString("nombreEscuela") );

				a.setEscuela(escuela);
				list.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if( list == null || list.isEmpty() ){
			return null;
		}
		return list.get( 0 );
	}

	//COnexion @.@
	public void inicializarConexion(){
		try {
			Class.forName("com.mysql.jdbc.Driver");//El paquete de Class 
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Escuela","root","interpol11");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}// END INICIAR CONEXION
	public void create ( Alumno  e ) {
		PreparedStatement ps = null; //Si la insercion no fue nula	 
		try {
			ps = con.prepareStatement( SQL_INSERT);
			//ps.setInt(1,e.getId());
			ps.setString( 1, e.getNombre());
			ps.setString( 2, e.getApellido());
			ps.setBytes( 3, e.getFoto());
			ps.setInt( 4, e.getEscuela( ).getId( ) );
			//Parte de inner join mapeo @.@


			ps.executeUpdate( );	
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
