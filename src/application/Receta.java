package application;

import javax.xml.bind.annotation.XmlRootElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlRootElement
public class Receta {
	private final StringProperty nombre = new SimpleStringProperty();
	public String imagen;
	public String categoria;
	public int comensales;
	public String dificultad;
	public Double tiempopreparacion;
	public Double tiempococcion;
	public int valoracion;
	public String ingredientes;
	public String formapreparacion;
	public String observaciones;
	
	
	public Receta(){
		nombre.setValue("");
		this.imagen = null;
		this.categoria = "";
		this.comensales = 0;
		this.dificultad = "";
		this.tiempopreparacion=0.0;
		this.tiempococcion = 0.0;
		this.valoracion = 0;
		this.ingredientes="";
		this.formapreparacion="";
		this.observaciones= "";
	}
	
	public Receta(String name, String img, String cat, int com, String dif, Double tprep, Double tcoc, int val, String ingr, String forma, String obs){	
		nombre.setValue(name);
		//Image image = new Image(img);
		this.imagen = img;
		this.categoria = cat;
		this.comensales = com;
		this.dificultad = dif;
		this.tiempopreparacion=tprep;
		this.tiempococcion = tcoc;
		this.valoracion = val;
		this.ingredientes=ingr;
		this.formapreparacion=forma;
		this.observaciones= obs;
	}
	
	public String getNombre(){
		return nombre.get();
	}
	
	public void setNombre(String nom){
		nombre.set(nom);
	}
}
