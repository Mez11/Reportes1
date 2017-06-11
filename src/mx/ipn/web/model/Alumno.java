package mx.ipn.web.model;
import java.io.Serializable;


public class Alumno implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1955068303100730372L;
	private int id;
	private String nombre;
	private String apellido;
	private byte[]  foto;
    private Escuela escuela; //Representacion de llave foranea
	
	
	

	public Escuela getEscuela() {
		return escuela;
	}
	public void setEscuela(Escuela escuela) {
		this.escuela = escuela;
		//this.escuela = new Escuela( );
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	
	
	

}
