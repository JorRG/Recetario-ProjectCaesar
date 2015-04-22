package controlador;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import application.Main;
import application.Receta;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NewRecipeController implements Initializable{
	@FXML
	Button butguardar;
	@FXML
	Button butcancelar;
	@FXML
	Button addimagebut;
	@FXML
	Text directorio;
	@FXML
	ChoiceBox<String> choicecategoria;
	@FXML
	ChoiceBox<Integer> choicecomensales;
	@FXML
	ChoiceBox<String> choicedificultad;
	@FXML
	ChoiceBox<Integer> choicevaloracion;
	@FXML
	ImageView imagen;
	@FXML
	TextField nomPlato;
	@FXML
	TextField tprep;
	@FXML
	TextField tcoc;
	@FXML
	TextArea ingred;
	@FXML
	TextArea fprep;
	@FXML
	TextArea obser;
	
	Receta receta = new Receta();
	private boolean okClicked = false;
	
	Stage dialogStage;
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	
	 public boolean isOkClicked() {
	        return okClicked;
	    }
	 
	 public void edReceta(Receta rec){
		 nomPlato.setText(rec.getNombre());
		 imagen.setImage(new Image(new File(rec.imagen).toURI().toString()));
		 choicecategoria.setValue(rec.categoria);
		 choicecomensales.setValue(rec.comensales);
		 choicedificultad.setValue(rec.dificultad);
		 fprep.setText(rec.formapreparacion);
		 ingred.setText(rec.ingredientes);
		 obser.setText(rec.observaciones);
		 tcoc.setText(rec.tiempococcion.toString());
		 tprep.setText(rec.tiempopreparacion.toString());
		 choicevaloracion.setValue(rec.valoracion);
	 }
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
		butguardar.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try{
				System.out.println(true);
				String temp = directorio.getText();
				System.out.println(temp);
				receta.imagen = temp;
				receta.setNombre(nomPlato.getText());
				receta.categoria = choicecategoria.getValue();
				receta.comensales = choicecomensales.getValue();
				receta.dificultad = choicedificultad.getValue();
				receta.formapreparacion = fprep.getText();
				receta.ingredientes = ingred.getText();
				receta.observaciones = obser.getText();
				receta.tiempococcion = (double)Integer.parseInt(tcoc.getText());
				receta.tiempopreparacion = (double)Integer.parseInt(tprep.getText());
				receta.valoracion = choicevaloracion.getValue();
				Main.getRecetas().add(receta);
				Node  source = (Node)  event.getSource(); 
				Stage stage  = (Stage) source.getScene().getWindow();
				stage.close();
			} catch(NullPointerException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("¡Faltan datos!");
				alert.showAndWait();
			}
		}
		});
		butcancelar.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("¿Desea salir sin guardar?");
				alert.setTitle("Confirme si desea salir");
				ButtonType si = new ButtonType("Si");
				ButtonType no = new ButtonType("No");
				alert.getButtonTypes().setAll(si, no);
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get()==si){
					Node nodo = (Node) event.getSource();
					nodo.getScene().getWindow().hide();
				}
			}
			
		});
		
		addimagebut.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				//Creating a file chooser to add an image to the recipe
				FileChooser filechooser = new FileChooser();
				filechooser.setTitle("Elija una imagen");
				File file = filechooser.showOpenDialog(null);
                if(file!=null){
                 directorio.setText(file.getPath());
                 //Image image = new Image(path);
                 //imagen.setImage(image);
                 Image image = new Image(file.toURI().toString());
                 imagen.setImage(image);
                 addimagebut.setText("Cambiar");
                }
			}
			
		});
		
		
		//Fill choiceboxes with items
		choicecategoria.setItems(FXCollections.observableArrayList("Arroces", "Pastas", "Carnes", "Pescados", "Verduras", "Dulces","Otros"));
		choicecomensales.setItems(FXCollections.observableArrayList(1,2,3,4,5,6,7,8));
		choicedificultad.setItems(FXCollections.observableArrayList("Baja", "Media", "Alta"));
		choicevaloracion.setItems(FXCollections.observableArrayList(1,2,3,4,5));
	}
	
	
}
