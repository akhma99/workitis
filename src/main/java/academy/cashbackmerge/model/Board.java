package academy.cashbackmerge.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final Tile[][] tiles;

    public static final int SIZE = 4;

    public Board() {
        this.tiles = new Tile[SIZE][SIZE];
        getReadyBoard(tiles);
    }

    public Tile getTile(int row, int col) {
        if (row < 0 || row > SIZE || col < 0 || col >= SIZE) {
            throw new IllegalArgumentException("Такой клетки не существует, проверьте данные");
        }
        return tiles[row][col];
    }

    public void setTile(int row, int col, Tile tile) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            throw new IllegalArgumentException("Такой клетки не существует, проверьте данные");
        }
        this.tiles[row][col] = tile;
    }

    private static void getReadyBoard(Tile[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile(0);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                int val = getTile(i, j).cashback();
                stringBuilder.append(val == 0 ? "." : val).append("\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public List<Position> getEmptyPositions() {
        List<Position> answer = new ArrayList<>();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j].cashback() == 0) {
                    answer.add(new Position(i, j));
                }
            }
        }
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board = (Board) o;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (this.tiles[i][j].cashback() != board.tiles[i][j].cashback()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }
}
