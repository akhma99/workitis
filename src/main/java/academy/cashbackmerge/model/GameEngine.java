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

        }
    }

    private List<Tile> extractLine(Board board, int index, Direction direction){
        List<Tile> result = new ArrayList<>();

    }

}
