package controlador;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import application.Main;
import application.Receta;
import javafx.util.Callback;
import javafx.scene.control.ListCell;

public class controller implements Initializable{
	@FXML
	Button buteditar;
	@FXML
	Button butborrar;
	@FXML
	Button butobservaciones;
	@FXML
	MenuItem menusalir;
	@FXML
	MenuBar menuarchivo;
	@FXML
	Button butanyadir;
	@FXML
	public ListView<Receta> lista;
	@FXML
	TitledPane mostrarReceta;
	@FXML
	ImageView imgReceta;
	@FXML
	TextArea ingredientesReceta;
	@FXML
	TextArea fprepReceta;
	@FXML
	Label valoracionReceta;
	@FXML
	Label comensalesReceta;
	@FXML
	Label categoriaReceta;
	@FXML
	Label dificultadReceta;
	@FXML
	Label tprepReceta;
	@FXML
	Label tcocReceta;
	@FXML
	TextArea textobservaciones;
	
	public void initData(ObservableList<Receta> list) {
        ObservableList<Receta> miListaSelected = list;
        lista.setItems(miListaSelected);
    }
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		lista.setCellFactory(new Callback<ListView<Receta>, ListCell<Receta>>() {
            @Override
            public ListCell<Receta> call(ListView<Receta> param) {
                ListCell<Receta> cell = new ListCell<Receta>() {
                    @Override
                    public void updateItem(Receta item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        }
                        if (item != null) {
                        	ImageView view = new ImageView();
                        	Image imagen = new Image(new File(item.imagen).toURI().toString());
                        	view.setImage(imagen);
                        	view.setFitHeight(60.0);
                        	view.setFitWidth(80.0);
                            setText(item.getNombre());
                            setGraphic(view);
                        }
                    }
                }; // ListCell
                return cell;  //To change body of generated methods, choose Tools | Templates.
            }
        });
		menusalir.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirme si desea salir");
				alert.setHeaderText("Pulse 'Aceptar' si desea salir");
				ButtonType aceptar = new ButtonType("Aceptar");
				ButtonType cancelar = new ButtonType("Cancelar");
				alert.getButtonTypes().setAll(aceptar, cancelar);
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == aceptar){
					Platform.exit();
				}
            }
		});
		
		buteditar.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(lista.getFocusModel().getFocusedItem()!=null){
				try {
					FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/Copy of nuevareceta.fxml"));
					AnchorPane page;
					page = (AnchorPane) loader.load();
					
					Stage dialogStage = new Stage();
					dialogStage.setTitle("Edit Person");
					dialogStage.initModality(Modality.WINDOW_MODAL);
					//dialogStage.initOwner(primaryStage);
					dialogStage.setResizable(false);
					Scene scene = new Scene(page);
					dialogStage.setScene(scene);
					dialogStage.show();
					
					EditRecipeController control = loader.getController();
					control.setDialogStage(dialogStage);
					Receta receta = lista.getFocusModel().getFocusedItem();
					control.edReceta(receta);
					lista.getSelectionModel().clearSelection();
					
				}catch (Exception e) {
				    // Exception gets thrown if the FXML file could not be loaded
				    e.printStackTrace();
				  }
			}
			else{Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Seleccione una receta");
			alert.showAndWait();
			}
			}	
		});
		
		
		butobservaciones.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				 if(textobservaciones.visibleProperty().get()){
					 textobservaciones.setVisible(false);
				 }
				 else{
				 Receta receta = lista.getFocusModel().getFocusedItem();
				 textobservaciones.setVisible(true);
				 textobservaciones.toFront();
				 textobservaciones.setText(receta.observaciones);
				 }
			 }
			
			
		});
		
		
		butborrar.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try{
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirme si desea borrar el elemento");
					alert.setContentText("Tenga en cuenta que esta acción es irreversible");
					alert.setHeaderText("Pulse 'Aceptar' si desea borrarlo");
					ButtonType aceptar = new ButtonType("Aceptar");
					ButtonType cancelar = new ButtonType("Cancelar");
					alert.getButtonTypes().setAll(aceptar, cancelar);
					Optional<ButtonType> result = alert.showAndWait();
					if(result.get() == aceptar){
					Receta elemborrar = lista.getFocusModel().getFocusedItem();
					lista.getItems().remove(elemborrar);
					lista.getSelectionModel().clearSelection();
					Main.recetas.remove(elemborrar);
					mostrarReceta.setVisible(false);
					}
				}catch(Exception e){
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("Seleccione una receta");
					alert.showAndWait();
					}
					}	
			
		});
		
		lista.setOnMouseClicked( new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				try{
				
				Receta recetaAver = lista.getFocusModel().getFocusedItem();
				mostrarReceta.setText(recetaAver.getNombre());
				//imgReceta.setImage(new Image(recetaAver.imagen));
				Image img = new Image(new File(recetaAver.imagen).toURI().toString());
		        imgReceta.setImage(img);
				ingredientesReceta.setText(recetaAver.ingredientes);
				fprepReceta.setText(recetaAver.formapreparacion);
				tcocReceta.setText(" "+Double.toString(recetaAver.tiempococcion));
				tprepReceta.setText(" "+Double.toString(recetaAver.tiempopreparacion));
				valoracionReceta.setText(Integer.toString(recetaAver.valoracion));
				comensalesReceta.setText(Integer.toString(recetaAver.comensales));
				dificultadReceta.setText(recetaAver.dificultad);
				categoriaReceta.setText(recetaAver.categoria);
				mostrarReceta.setVisible(true);
				
			}catch(NullPointerException e){}
			}
		});
		
		butanyadir.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try {
					FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/nuevareceta.fxml"));
				    AnchorPane page;
					page = (AnchorPane) loader.load();
					Stage dialogStage = new Stage();
					dialogStage.setTitle("Edit Person");
					dialogStage.initModality(Modality.WINDOW_MODAL);
					//dialogStage.initOwner(primaryStage);
					dialogStage.setResizable(false);
					Scene scene = new Scene(page);
					dialogStage.setScene(scene);
					dialogStage.show();
				}catch (Exception e) {
				    // Exception gets thrown if the FXML file could not be loaded
				    e.printStackTrace();
				  }
			}
			
		});
	
	}
	
	 @FXML
	    private void guardar() {
		 	Main main = new Main();
	        File personFile = main.getPersonFilePath();
	        if (personFile != null) {
	            main.saveRecetaToFile(personFile);
	        } else {
	            guardarComo();
	        }
	    }
	 @FXML
	 private void guardarComo() {
		 Main main = new Main();
		 FileChooser fileChooser = new FileChooser(); 
		 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"); 
		 fileChooser.getExtensionFilters().add(extFilter);
		 File file = fileChooser.showSaveDialog(main.getPrimaryStage()); 
		 if (file != null) {
	     if (!file.getPath().endsWith(".xml"))
	       file = new File(file.getPath() + ".xml");
	     	main.saveRecetaToFile(file);
	 }
	 }
	 @FXML
	    private void handleOpen() {
		 Main main = new Main();
	        FileChooser fileChooser = new FileChooser();

	        // Set extension filter
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
	                "XML files (*.xml)", "*.xml");
	        fileChooser.getExtensionFilters().add(extFilter);

	        // Show save file dialog
	        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

	        if (file != null) {
	            main.loadRecetaFromFile(file);
	        }
	    }
}
	

	
	

	
