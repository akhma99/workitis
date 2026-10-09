package academy.cashbackmerge.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final Tile[][] tiles;

    private final int size;

    public Board() {
        this(4); // Вызывает второй конструктор, передавая ему 4
    }

    public Board(int size) {
        this.size = size;
        this.tiles = new Tile[size][size];
        getReadyBoard(tiles);
    }

    public int getSize() {
        return size;
    }

    public Tile getTile(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Такой клетки не существует, проверьте данные");
        }
        return tiles[row][col];
    }

    public void setTile(int row, int col, Tile tile) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
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
        for (int i = 0; i < size; i++) { // Используем size вместо tiles.length
            for (int j = 0; j < size; j++) {
                int val = getTile(i, j).cashback();
                stringBuilder.append(val == 0 ? "." : val).append("\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public List<Position> getEmptyPositions() {
        List<Position> answer = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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

        if (this.size != board.size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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
