package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class EndScreen {

    public static void display(int finalscore) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("You lost!");

        VBox column = new VBox(10);

        HBox buttons = new HBox(5);

        Text score = new Text();
        score.setText("YOU LOST " + "\n" + "Your final score was: " + finalscore);
        score.setTextAlignment(TextAlignment.CENTER);


        score.setFont(Font.font("Arial", 20));
        score.setFill(Color.RED);


        Button quit = new Button("Quit");
        Button main = new Button("Main Menu");


        main.setOnAction(e -> {
            window.close();
            Main gotoMain = new Main();
            gotoMain.Deserialization();
            if(finalscore > gotoMain.getSerialScore() ) {
                Score snakeScore1 = new Score(finalscore);
                gotoMain.Serilization(snakeScore1, "score.ser");

            }





            try {
                gotoMain.start(window);
            } catch (Exception exception) {
                exception.printStackTrace();
            }


        });


        quit.setOnAction(e -> {
            System.exit(0);


        });




        buttons.getChildren().addAll(main, quit);
        buttons.setAlignment(Pos.CENTER);


        column.getChildren().addAll(score, buttons);
        column.setAlignment(Pos.CENTER);

        Scene scene = new Scene(column, 400, 400);
        window.setScene(scene);
        window.show();



    }
}
