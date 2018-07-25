package maze.view;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import java.awt.Dimension;
import java.util.concurrent.ThreadLocalRandom;
import javafx.util.Duration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import maze.Board;
import maze.Login;


public class GameController{
	
	private String token = "";
	private String username = "username";
	private String userId = "";
	private String levelId = "";
	private Timeline timeline;
	private DoubleProperty timeSeconds = new SimpleDoubleProperty();
    private Duration time = Duration.ZERO;
	
	
    @FXML
    private Button startButton;
    @FXML
    private Pane pane;  
    @FXML
    private SwingNode nodeGame;  
    
    private Board board;
    
    @FXML
    private Label idTocchi;
    @FXML
    private Label lbTime; 
    @FXML
    private Label pauseLabel;
    @FXML
    private Label labelWinner; 
    @FXML
    private Button startGame;
    @FXML
    private Button stopGame;
    @FXML
    private Button btnEsci;
    @FXML
    private Button btnSave;
    @FXML
    private Label lbUsername;
    @FXML 
    private Pane pane2;
    
    //Inizializza le variabili della game-view
    //questa funzione viene chiamata dalla level-view
    public void initialize(int livello, String user, String usId, String levId, String tk) {
    		System.out.println("initialize");
    		username = user;
    		token = tk;
    		userId = usId;
    		levelId = levId;
    		lbUsername.setText(this.username);
    		pauseLabel.setVisible(true);
    		pauseLabel.setText("Inizia il Gioco!");
    		createAndSetSwingContent(nodeGame, pane, livello);
    }
    
    //Funzione che ferma il gioco
    public void stopGame(ActionEvent e) {
    		timeline.pause();
    		if(board.getMessage() != "Winner") {
    			board.setstopGameTrue();
    			pauseLabel.setVisible(true);
    			pauseLabel.setText("Premi Start per Riprendere");
    			startGame.setVisible(true);
    			stopGame.setVisible(false);
    		}
    }
    
    //Funzione che fa ripartire il gioco
    public void startGame(ActionEvent e) {
    		if (timeline != null)
			timeline.play();
		else {
            timeline = new Timeline(
                new KeyFrame(Duration.millis(100),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        Duration duration = ((KeyFrame)t.getSource()).getTime();
                        time = time.add(duration);
                        timeSeconds.set(time.toSeconds());
                    }
                })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    	
    		if(board.getMessage() != "Winner") {
			board.setstopGameFalse();
			stopGame.setVisible(true);
			startGame.setVisible(false);
			pauseLabel.setVisible(false);
		}
    		lbTime.textProperty().bind(timeSeconds.asString());
    }
    
    //Funzione che aggiorna la label che mostra il numero di volte che l'utente 
    //tocca il muro
    public void updateLabelTocchi(MouseEvent e) {
    		System.out.println("Premuto");
    		idTocchi.setText(String.valueOf(board.getCounter()));
    		if(board.getMessage()=="Winner") {
    			timeline.stop();
    			pane2.setVisible(true);
    			labelWinner.setVisible(true);
    			btnSave.setVisible(true);
    		}
    }
    
    //Inizializza la board interna alla view
    //la variabile board è un contenuto Java Swing, e per questo deve essere inizializzato in questo modo
	public void createAndSetSwingContent(SwingNode swingNode, Pane panel,int levelNumber) {
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	        		if(levelNumber>10 || levelNumber<1) {
	        			int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
	        			board = new Board(randomNum);
	        		}else {
	        			board = new Board(levelNumber);
	        		}
	        		System.out.println("Caricamento Gioco");
	        		board.setPreferredSize(new Dimension(448,448));
	        		board.setstopGameTrue();
	            	swingNode.setContent(board); 
	         }
	    	});
		
	}
	
	//Funzione che mi rimanda alla level-view settandola con i campi:
	// token,userId,username
	public void EsciGioco(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		
		Parent  parent = loader.load(getClass().getResource("LevelView.fxml").openStream());
		LevelController out = new LevelController();
		out = loader.getController();
		out.SetVariables(token,userId,username);
		out.setTable();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.getScene().getStylesheets().add(Login.class.getResource("view/application.css").toExternalForm());
		stage.setScene(new Scene(parent));
		stage.show();
	}
	
	
	//Funzione che effettua il salvataggio della partita mandanto una PUT Request al server
	public void SalvaPartita(ActionEvent event) throws Exception {
					
		JSONObject json = new JSONObject();
		json.put("numerotocchi", board.getCounter());  
		json.put("tempo", timeSeconds.doubleValue());
		json.put("svolto", true); 
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
		    HttpPut request = new HttpPut("https://servermaze.herokuapp.com/api/levels/?levelId=" + levelId +"&userId=" + userId);
		    StringEntity params = new StringEntity(json.toString());
		    request.addHeader("content-type", "application/json");
		    request.addHeader("x-access-token", token);
		    request.setEntity(params);
		    HttpResponse response = httpClient.execute(request);
		    System.out.println(response.getStatusLine().getStatusCode());
		    if(response.getStatusLine().getStatusCode()==200) {
		    	//Cambio view dopo la risposta
			    ((Node) (event.getSource())).getScene().getWindow().hide();
				FXMLLoader loader = new FXMLLoader();
				
				Parent  parent = loader.load(getClass().getResource("LevelView.fxml").openStream());
				LevelController out = new LevelController();
				out = loader.getController();
				out.SetVariables(token,userId,username);
				out.setTable();
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.getScene().getStylesheets().add(Login.class.getResource("view/application.css").toExternalForm());
				stage.setScene(new Scene(parent));
				stage.show();
		    }
		    
		} catch (Exception ex) {
		    // handle exception here
		} finally {
		    httpClient.close();
		}
			

			
	}	
			
}
