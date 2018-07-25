package maze;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import maze.view.GameController;

//Classe di appoggio per la view table presente nel levelController
public class Livello {
	private final String username;
	private final String userId;
	private final String token;
	private final String idLivello;
    private final String svolto;
    private final Integer numeroLivello;
    private final String numeroTocchi;
    private final String tempo;
    private final Button btn;
    
    public Livello(String id, String s, Integer numero, String tocchi, String t, String user, String tk, String ud){
    		this.idLivello = id;
    		this.svolto = s;
    		this.numeroLivello = numero;
    		this.numeroTocchi = tocchi;
    		this.tempo = t;
    		this.username = user;
    		this.token = tk;
    		this.btn = new Button("Gioca");
    		btn.getStyleClass().add("tableButton");
    		this.userId = ud;
    }
  
    
    public String getIdLivello() {
		return idLivello;
	}
    
	public String getNumeroTocchi() {
		return numeroTocchi;
	}

	public Integer getNumeroLivello() {
		return numeroLivello;
	}

	public String getSvolto() {
		return svolto;
	}


	public String getTempo() {
		return tempo;
	}
	
	public Button getBtn() {
		btn.setOnAction(event -> {
			System.out.println(numeroLivello);
			System.out.println(token);
			System.out.println(username);
			System.out.println(userId);
			try {
				FXMLLoader loader = new FXMLLoader();
				
				Parent  parent = loader.load(getClass().getResource("view/Game.fxml").openStream());
				GameController out = new GameController();
				out = loader.getController();
				out.initialize(numeroLivello, username, userId, idLivello, token);
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.getScene().getStylesheets().add(Login.class.getResource("view/application.css").toExternalForm());
				stage.setScene(new Scene(parent));
				stage.show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return btn;
	}
	
	
}
