package sample;

import java.io.Serializable;

public class Score implements Serializable {
    private int snakeScore = 0;


    public Score(int score) {
        this.snakeScore = score;
    }

    public int getSnakeScore() {
        return snakeScore;
    }

}
