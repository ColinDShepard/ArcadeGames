package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application {
    Stage window;

    private enum UserAction {
        NONE, LEFT, RIGHT
    }

    private static final int APP_W = 800;
    private static final int APP_H = 600;

    private static final int BALL_RADIUS = 10;
    private static final int BAT_W = 100;
    private static final int BAT_H = 20;

    private Circle ball = new Circle(BALL_RADIUS);
    private Rectangle bat = new Rectangle(BAT_W, BAT_H);

    private boolean ballUp = true, ballLeft = false;

    private UserAction action = UserAction.NONE;

    private Timeline timeline = new Timeline();

    private boolean running = true;

    private int SCORE = 0;

    Text scoreDisplay = new Text();


    private Parent createContent(double diff) {
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);

        //putting bat on screen
        bat.setTranslateX(APP_W / 2);
        bat.setTranslateY(APP_H - BAT_H);
        bat.setFill(Color.GREEN);
        ball.setFill(Color.RED);

        KeyFrame frames = new KeyFrame(Duration.seconds(diff), e -> {
            if(!running) {
                return;
            }

            switch(action) {
                case LEFT:
                    if(bat.getTranslateX() - 10 >= 0) {
                        bat.setTranslateX(bat.getTranslateX() - 10);
                    }
                    break;
                case RIGHT:
                    if(bat.getTranslateX() + BAT_W + 10 <= APP_W) {
                        bat.setTranslateX(bat.getTranslateX() + 10);

                    }
                    break;
                case NONE:
                    break;

            }

            //Control the ball movement

            ball.setTranslateX(ball.getTranslateX() + (ballLeft ? -5 : 5));
            ball.setTranslateY(ball.getTranslateY() + (ballUp ? -5 : 5));

            if(ball.getTranslateX() - BALL_RADIUS == 0) {
                ballLeft = false;
            } else if(ball.getTranslateX() + BALL_RADIUS == APP_W)  {
                ballLeft = true;

            }

            if(ball.getTranslateY() - BALL_RADIUS == 0) {
                ballUp = false;
            } else if(ball.getTranslateY() + BALL_RADIUS == APP_H - BAT_H && ball.getTranslateX() + BALL_RADIUS >= bat.getTranslateX()
                      && ball.getTranslateX() - BALL_RADIUS <= bat.getTranslateX() + BAT_W )    {
                ballUp = true;

                if (ballUp) {
                    SCORE = SCORE + 1;
                    scoreDisplay.setText("Score: " + SCORE);
                    scoreDisplay.setTranslateX(10);
                    scoreDisplay.setTranslateY(22);
                    scoreDisplay.setFill(Color.GREEN);
                    scoreDisplay.setFont(Font.font("Arial", 20));
                }

            }
            if(ball.getTranslateY() + BALL_RADIUS == APP_H + 30) {
                restartGame();
            }



        });

        timeline.getKeyFrames().add(frames);
        timeline.setCycleCount(timeline.INDEFINITE);


        root.getChildren().addAll(ball, bat, scoreDisplay);
        return root;
    }

    private void restartGame () {
        stopGame();
        EndScreen end = new EndScreen();
        window.close();
        end.display(getScore());

    }

    private void startGame() {
        ballUp = true;
        ball.setTranslateX(APP_W / 2);
        ball.setTranslateY(APP_H / 2);

        timeline.play();
        running = true;
    }

    private void stopGame() {
        //SCORE = 0;
        running = false;
        timeline.stop();
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent(0.016));
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    action = UserAction.LEFT;
                    break;
                case D:
                    action = UserAction.RIGHT;
                    break;
            }




        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case A:
                    action = UserAction.NONE;
                    break;
                case D:
                    action = UserAction.NONE;
                    break;


            }

        });
        window = primaryStage;
        window.setTitle("Pong");
        window.setScene(scene);
        window.setResizable(false);
        window.show();
        startGame();

    }

    private int getScore() {
        return SCORE;

    }
    public static void main(String[] args) {
        launch(args);

    }

}
