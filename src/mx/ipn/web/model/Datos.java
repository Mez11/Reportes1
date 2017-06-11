package mx.ipn.web.model;

import java.io.Serializable;

public class Datos  implements  Serializable {
	private  int alumnosTotales;
	private String nombreEscuela;
	
	
	public int getAlumnosTotales() {
		return alumnosTotales;
	}
	public void setAlumnosTotales(int alumnosTotales) {
		this.alumnosTotales = alumnosTotales;
	}
	public String getNombreEscuela() {
		return nombreEscuela;
	}
	public void setNombreEscuela(String nombreEscuela) {
		this.nombreEscuela = nombreEscuela;
	}

}
