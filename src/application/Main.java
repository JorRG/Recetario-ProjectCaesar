package application;
	
import java.io.File;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import controlador.controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
Stage primaryStage;
public final static ObservableList<Receta> recetas=FXCollections.observableArrayList();
    
    public Main(){}
    
    
	
    public void start(Stage primaryStage) throws Exception{
		try {
			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/vista.fxml"));
            Parent root=loader.load();
            Scene scene = new Scene(root,900,600);
            controller controlad=loader.getController();
            //cargarRecetas();
            controlad.initData(getRecetas());
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}

	public static ObservableList<Receta> getRecetas() {
		return recetas;
	}

	public static void addReceta(Receta receta1) {
		// TODO Auto-generated method stub
		getRecetas().add(receta1);
	}
	public void saveRecetaToFile(File file) {
		try {
	    JAXBContext context = JAXBContext.newInstance(envoltorioRecetas.class);
	    Marshaller m = context.createMarshaller(); m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    envoltorioRecetas wrapper = new envoltorioRecetas(); 
	    wrapper.setListaRecetas(recetas);
	    m.marshal(wrapper, file);
	    setPersonFilePath(file);
	} catch (Exception e) {
		/**System.err.println(e);
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error"); alert.setHeaderText(null); 
		alert.setContentText("Could not save data to file:\n" +file.getPath()); 
		alert.showAndWait();*/
	}
}
	
	public void setPersonFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(Main.class); 
		if (file != null) {
		        prefs.put("filePath", file.getPath());
		        primaryStage.setTitle("AddressApp - " + file.getName()); } else {
		        prefs.remove("filePath");	
		        primaryStage.setTitle("AddressApp");
		    }
		}
	
	public File getPersonFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}
	 
	 public void loadRecetaFromFile(File file) {
			try {
				JAXBContext context = JAXBContext.newInstance(envoltorioRecetas.class);
				Unmarshaller um = context.createUnmarshaller();

				// Reading XML from the file and unmarshalling.
				envoltorioRecetas wrapper = (envoltorioRecetas) um.unmarshal(file);

				recetas.clear();
				recetas.addAll(wrapper.getListaRecetas());

				// Save the file path to the registry.
				setPersonFilePath(file);

			} catch (Exception e) {}
		}



	public Stage getPrimaryStage() {
		// TODO Auto-generated method stub
		return primaryStage;
	}
	
		
	}
	
	
	

