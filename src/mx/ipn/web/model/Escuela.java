package mx.ipn.web.model;

import java.io.Serializable;

public class Escuela implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1023686483808276690L;

	public static final int IPN = 3;
	public static final int ANDREA = 1;
	public static final int OPARIN = 2;
	
	public int id;
	private String nombre;
	
	public Escuela( ){
		
	}
	
	public Escuela( int id ){
		this.id = id;
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
}
