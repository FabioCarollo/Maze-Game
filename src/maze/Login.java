package maze;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;


public class Login extends Application {
	
	private AnchorPane root;
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws IOException{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Login");
		showMainView();
	}
	
	private void showMainView() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Login.class.getResource("view/Login.fxml"));
		root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(Login.class.getResource("view/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
