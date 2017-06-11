package mx.ipn.web.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.ipn.web.model.Escuela;

public class EscuelaDAO {
	//CONSULTAS @.@
	/*private static final String SQL_SELECT=
			"select id ,nombre  from Escuela where id ='?'";*/
	private static final String SQL_SELECT_ALL=
			"select id,nombre from Escuela";
	
	
	
	//Conexion
	private Connection con;
	//Traer todos los resultados de BD
	public List<Escuela>  getEscuelas(){
		   List<Escuela> list = null;
			   try {
				   PreparedStatement r = con.prepareStatement( SQL_SELECT_ALL);
				   ResultSet rs =r.executeQuery();
				   list = new ArrayList<Escuela> ();
				   Escuela a=null;
				   while(rs.next()){
					   a=new Escuela();//Inicializar escuela
					   a.setId( rs.getInt("id") );
					   a.setNombre(rs.getString("nombre"));
					  
					   list.add(a);
				   }
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			return list;
		}//end list
	
	
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
			
			
	
}
}
