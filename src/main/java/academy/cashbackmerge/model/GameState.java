package academy.cashbackmerge.model;

public class GameState {
    private final Board board;
    private int score;
    private int movesLeft;

    public GameState(int movesLeft) {
        this.board = new Board();
        this.score = 0;
        this.movesLeft = movesLeft;
    }

    public Board getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void addScore(int score) {
        if (score < 0) {
            throw new IllegalArgumentException("Попытка добавить отрицательное число очков");
        } else {
            this.score += score;
        }
    }

    public void decrementMoves() {
        movesLeft--;
    }
}
