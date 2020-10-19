package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Snake extends Application {
    Scene scene;
    Stage window;

    public enum UserAction {
        UP, DOWN, LEFT, RIGHT
    }

    private static final int BLOCK_SIZE = 40;
    private static final int APP_W = 20 * BLOCK_SIZE;
    private static final int APP_H = 15 * BLOCK_SIZE;

    private UserAction direction = UserAction.RIGHT;

    private boolean moved = false;
    private boolean running = false;

    private Timeline timeline = new Timeline();

    private Text SCORE_DISPLAY = new Text();
    private Text FINAL_SCORE_DISPLAY = new Text();
    private int score = 0;
    private int finalscore = 0;
    private double difficulty;

    BorderPane snakeLayout = new BorderPane();

    //ObservableList uses listeners to track changes when they occur
    private ObservableList<Node> snake;

    public void changeDiff(double x) {
        this.difficulty = x;
    }

    private Parent createContent(double x) {
        Pane root = new Pane();
        root.setPrefSize(APP_W, APP_H);

        Group snakeBody = new Group();
        snake = snakeBody.getChildren();

        //Creating food that the snake will eat in order to get bigger
        Rectangle food = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        food.setFill(Color.RED);
        food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
        food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

        KeyFrame frames = new KeyFrame(Duration.seconds(x), e ->{
            if(!running) {
                return;

            }
            boolean toRemove = snake.size() > 1;

            Node tail = toRemove ? snake.remove(snake.size() - 1) : snake.get(0);

            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            switch(direction) {
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - BLOCK_SIZE);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() + BLOCK_SIZE);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX() - BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX() + BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }

            moved = true;

            if(toRemove) {
                snake.add(0, tail);
            }

            //Collision detection on if the snake hits itself
            for(Node rect : snake) {
                if (rect != tail && tail.getTranslateX() == rect.getTranslateX() && tail.getTranslateY() == rect.getTranslateY()) {
                    restartGame();
                    break;
                }

            }
            //Collision detection on if the snake hits the wall
            if(tail.getTranslateX() < 0 || tail.getTranslateX() >= APP_W || tail.getTranslateY() < 0 || tail.getTranslateY() >= APP_H) {
                restartGame();

            }

            if(tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()) {

                //This can be method that scrambles where food is
                food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
                food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);


                //this can also be a method called eatsfood()
                Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                rect.setFill(Color.BLUE);
                rect.setTranslateX(tailX);
                rect.setTranslateY(tailY);

                snake.add(rect);
                score = snake.size() - 1;

                SCORE_DISPLAY.setText("Score: " + score);
                SCORE_DISPLAY.setTranslateX(10);
                SCORE_DISPLAY.setTranslateY(22);
                SCORE_DISPLAY.setFill(Color.GREEN);
                SCORE_DISPLAY.setFont(Font.font("Arial", 20));




            }





        });

        timeline.getKeyFrames().add(frames);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(food, snakeBody, SCORE_DISPLAY, FINAL_SCORE_DISPLAY);


        return root;
    }

    private void restartGame() {
        stopGame();
        finalScore(score);
        EndScreen end = new EndScreen();
        end.display(getFS());

        window.close();
    }

    private void startGame() {
        direction = UserAction.RIGHT;
        Rectangle head = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        head.setFill(Color.BLUE);
        snake.add(head);
        timeline.play();
        running = true;

    }

    private void stopGame() {
        running = false;
        timeline.stop();
        snake.clear();

    }

    private void finalScore(int score) {
        if (score > finalscore) {
            finalscore = score;
        }

    }

    private int getFS() {
        return finalscore;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent(difficulty));
        scene.setOnKeyPressed(e -> {
            if(moved) {
                switch (e.getCode()) {
                    case W:
                        if (direction != UserAction.DOWN) {
                            direction = UserAction.UP;
                        }
                        break;
                    case S:
                        if (direction != UserAction.UP) {
                            direction = UserAction.DOWN;
                        }
                        break;
                    case A:
                        if (direction != UserAction.RIGHT) {
                            direction = UserAction.LEFT;
                        }
                        break;
                    case D:
                        if (direction != UserAction.LEFT) {
                            direction = UserAction.RIGHT;
                        }
                        break;
                }
            }

            moved = false;
        });

        window = primaryStage;
        window.setTitle("Snake");
        window.setScene(scene);
        window.setResizable(false);
        window.show();
        startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }




}
