package academy.cashbackmerge.mandatory.mr2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import academy.cashbackmerge.model.Direction;
import academy.cashbackmerge.model.GameEngine;
import academy.cashbackmerge.model.GameState;
import academy.cashbackmerge.model.LineResult;
import academy.cashbackmerge.model.Tile;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/** Обязательные тесты: правила слияния плиток. */
@DisplayName("MR2. Слияние плиток")
class MergeRulesTest {

    private final GameEngine engine = new GameEngine(null, null, null);

    @ParameterizedTest
    @CsvSource({
        "'1,1,.,.', '2,.,.,.'",
        "'1,1,1,.', '2,1,.,.'",
        "'1,1,1,1', '2,2,.,.'",
        "'2,2,4,4', '4,8,.,.'",
    })
    @DisplayName("Сдвиг влево: {0} -> {1}")
    void rowShiftsLeftAndMerges(String before, String expected) {
        List<Tile> result = convertByList(before);
        List<Tile> answer = convertByList(expected);
        LineResult lineResult = engine.mergeLine(result);
        assertEquals(lineResult.line(), answer);
    }

    @Test
    @DisplayName("Одна плитка не сливается дважды за ход: [1,1,2,.] -> [2,2,.,.]")
    void tileDoesNotMergeTwiceInOneMove() {
        String before = "1,1,2,.";
        String expected = "2,2,.,.";
        List<Tile> result = convertByList(before);
        List<Tile> answer = convertByList(expected);
        LineResult lineResult = engine.mergeLine(result);
        assertEquals(lineResult.line(), answer);
    }

    @Test
    @DisplayName("Слияние работает во всех четырёх направлениях")
    void mergeWorksInAllDirections() {
        GameState state = new GameState(10);
        state.getBoard().setTile(0, 0, new Tile(2));
        state.getBoard().setTile(0, 1, new Tile(2));
        engine.shiftBoard(state, Direction.RIGHT);
        assertEquals(4, state.getBoard().getTile(0, 3).cashback());
        state.getBoard().setTile(1, 3, new Tile(4));
        engine.shiftBoard(state, Direction.DOWN);
        assertEquals(8, state.getBoard().getTile(3, 3).cashback());
        state.getBoard().setTile(3, 2, new Tile(8));
        engine.shiftBoard(state, Direction.LEFT);
        assertEquals(16, state.getBoard().getTile(3, 0).cashback());
        state.getBoard().setTile(2, 0, new Tile(16));
        engine.shiftBoard(state, Direction.UP);
        assertEquals(32, state.getBoard().getTile(0, 0).cashback());
    }

    private List<Tile> convertByList(String before) {
        String[] tiles = before.split(",");
        List<Tile> result = new ArrayList<>();
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].equals(".")) {
                result.add(new Tile(0));
            } else {
                result.add(new Tile(Integer.parseInt(tiles[i])));
            }
        }
        return result;
    }
}
