package application;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "recetas")
public class envoltorioRecetas {
	private List<Receta> listaRecetas;
	public envoltorioRecetas(){}
	public List<Receta> getListaRecetas(){
		return listaRecetas;
	}
	
	@XmlElement(name = "Receta")
	public void setListaRecetas(List<Receta> listaRecetas){
		this.listaRecetas = listaRecetas;
	}
}
