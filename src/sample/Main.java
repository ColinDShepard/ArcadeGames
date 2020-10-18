package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private Snake snakeGame = new Snake();

    private Stage window;

    private static final int APP_W = 800;
    private static final int APP_H = 500;

    private final BorderPane mainLayout = new BorderPane();
    private final VBox gameList = new VBox(10);
    private final HBox title = new HBox();

    Text mainTitle = new Text();

    //Creating buttons
    Button quit = new Button("Quit");
    Button snake = new Button("Snake");
    Button pong = new Button("Pong");


    @Override
    public void start(Stage primaryStage) throws Exception{

        setTitle();
        setGameList();

        //-------BUTTON ACTIONS--------------------------------------------------
        quit.setOnAction(e ->{
            System.exit(0);

        });

        snake.setOnAction(e -> {

            try {
                snakeGame.start(window);
            } catch (Exception exception) {
                exception.printStackTrace();
            }


        });



        //-------MAIN LAYOUT-----------------------------------------------------

        mainLayout.setTop(title);
        mainLayout.setCenter(gameList);


        Scene scene = new Scene(mainLayout, APP_W, APP_H);

        window = primaryStage;
        window.setTitle("Arcade Games");
        window.setScene(scene);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    //Creates the title
    private void setTitle() {
        mainTitle.setText("Choose the game you wish to play!");
        mainTitle.setFill(Color.CYAN);
        mainTitle.setFont(Font.font("Arial", 40));
        mainTitle.setStrokeWidth(1);
        mainTitle.setStroke(Color.BLACK);

        title.setAlignment(Pos.CENTER);
        title.getChildren().addAll(mainTitle);
    }

    private void setGameList() {
        gameList.setAlignment(Pos.BASELINE_CENTER);
        gameList.getChildren().addAll(snake, pong, quit);
    }



}
