package academy.cashbackmerge.model;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    public LineResult mergeLine(List<Tile> input){
        int score = 0;
        List<Tile> result = new ArrayList<>();
        for (int i = 0; i<input.size(); i++){
            if (input.get(i).cashback()>0){
                result.add(input.get(i));
            }
        }
        for (int i = 0; i<result.size()-1;i++){
            if (result.get(i).equals(result.get(i+1))){
                result.set(i,new Tile(result.get(i).cashback()*2));
                result.remove(i+1);
                score+=result.get(i).cashback();
            }
        }
        while (result.size() < input.size()) {
            result.add(new Tile(0));
        }
        return new LineResult(result,score);
    }

    public boolean applyMove(GameState state, Direction direction){
        boolean isChanged = false;
        Board board = state.getBoard();
        for (int i = 0; i<board.getSize();i++){
            if (direction == Direction.LEFT || direction == Direction.UP){

            }
        }
    }

    private List<Tile> extractLine(Board board, int index, Direction direction){
        List<Tile> line = new ArrayList<>();
        for (int j = 0; j<board.getSize(); j++){
            if (direction == Direction.LEFT || direction == Direction.RIGHT){
                line.add(board.getTile(index,j));
            } else {
                line.add(board.getTile(j,index));
            }
        }
        return line;
    }

    private void writeLine(Board board, int index, Direction direction, List<Tile> tiles){
        for (int j = 0; j<board.getSize(); j++){
            if (direction == Direction.LEFT || direction == Direction.RIGHT){
                board.setTile(index,j,tiles.get(j));
            } else {
                board.setTile(j,index,tiles.get(j));
            }
        }
    }

}
