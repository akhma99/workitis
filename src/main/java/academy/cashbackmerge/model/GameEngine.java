package academy.cashbackmerge.model;

import academy.cashbackmerge.config.CampaignConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameEngine {
    private final CampaignConfig config;
    private final Random random;
    private final GameInitializer gameInitializer;

    public GameEngine(CampaignConfig config, Random random, GameInitializer gameInitializer) {
        this.config = config;
        this.random = random;
        this.gameInitializer = gameInitializer;
    }

    public LineResult mergeLine(List<Tile> input) {
        int score = 0;
        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).cashback() > 0) {
                result.add(input.get(i));
            }
        }
        for (int i = 0; i < result.size() - 1; i++) {
            if (result.get(i).equals(result.get(i + 1))) {
                result.set(i, new Tile(result.get(i).cashback() * 2));
                result.remove(i + 1);
                score += result.get(i).cashback();
            }
        }
        while (result.size() < input.size()) {
            result.add(new Tile(0));
        }
        return new LineResult(result, score);
    }

    public MoveResult applyMove(GameState state, Direction direction) {
        if (state.getMovesLeft() <= 0) {
            return MoveResult.GAME_OVER;
        }

        boolean isChanged = shiftBoard(state, direction);

        if (isChanged) {
            state.decrementMoves();
            gameInitializer.spawnTile(state, config, random);
            return MoveResult.SUCCESS;
        }

        return MoveResult.IGNORED;
    }

    public boolean shiftBoard(GameState state, Direction direction) {
        boolean isChanged = false;
        Board board = state.getBoard();
        for (int i = 0; i < board.getSize(); i++) {
            List<Tile> line = extractLine(board, i, direction);
            List<Tile> originalLine = new ArrayList<>(line);

            if (direction == Direction.DOWN || direction == Direction.RIGHT) {
                Collections.reverse(line);
            }

            LineResult result = mergeLine(line);
            List<Tile> newLine = result.line();

            if (result.score() > 0) {
                state.addScore(result.score());
            }

            if (direction == Direction.DOWN || direction == Direction.RIGHT) {
                Collections.reverse(newLine);
            }

            if (!originalLine.equals(newLine)) {
                isChanged = true;
            }

            writeLine(board, i, direction, newLine);
        }
        return isChanged;
    }

    private List<Tile> extractLine(Board board, int index, Direction direction) {
        List<Tile> line = new ArrayList<>();
        for (int j = 0; j < board.getSize(); j++) {
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                line.add(board.getTile(index, j));
            } else {
                line.add(board.getTile(j, index));
            }
        }
        return line;
    }

    private void writeLine(Board board, int index, Direction direction, List<Tile> tiles) {
        for (int j = 0; j < board.getSize(); j++) {
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                board.setTile(index, j, tiles.get(j));
            } else {
                board.setTile(j, index, tiles.get(j));
            }
        }
    }
}
