package academy.cashbackmerge.mandatory.mr2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import academy.cashbackmerge.config.CampaignConfig;
import academy.cashbackmerge.config.SpawnRule;
import academy.cashbackmerge.model.Direction;
import academy.cashbackmerge.model.GameEngine;
import academy.cashbackmerge.model.GameInitializer;
import academy.cashbackmerge.model.GameState;
import academy.cashbackmerge.model.MoveResult;
import academy.cashbackmerge.model.Tile;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: движок ходов — что считается ходом и что после него происходит. */
@DisplayName("MR2. Движок ходов")
class MoveEngineTest {

    private final GameInitializer initializer = new GameInitializer();
    private final Random random = new Random(42);
    private final CampaignConfig config = new CampaignConfig(List.of(new SpawnRule(1, BigDecimal.ONE)), 5);
    private final GameEngine engine = new GameEngine(config, random, initializer);

    @Test
    @DisplayName("Невалидный ход не меняет поле, не тратит ход и не создаёт плитку")
    void invalidMoveChangesNothing() {
        GameState state = new GameState(5);
        state.getBoard().setTile(0, 0, new Tile(2));
        MoveResult result = engine.applyMove(state, Direction.LEFT);
        assertEquals(MoveResult.IGNORED, result);
        assertEquals(5, state.getMovesLeft());
        assertEquals(15, state.getBoard().getEmptyPositions().size());
    }

    @Test
    @DisplayName("После валидного хода появляется ровно одна новая плитка")
    void validMoveSpawnsExactlyOneTile() {
        GameState state = new GameState(5);
        state.getBoard().setTile(0, 0, new Tile(2));
        MoveResult result = engine.applyMove(state, Direction.RIGHT);
        assertEquals(MoveResult.SUCCESS, result);
        assertEquals(4, state.getMovesLeft());
        assertEquals(14, state.getBoard().getEmptyPositions().size());
    }

    @Test
    @DisplayName("После N валидных ходов игра завершена")
    void gameEndsAfterMovesLimit() {
        GameState state = new GameState(1);
        state.getBoard().setTile(0, 0, new Tile(2));
        engine.applyMove(state, Direction.RIGHT);
        MoveResult result = engine.applyMove(state, Direction.DOWN);
        assertEquals(MoveResult.GAME_OVER, result);
    }

    @Test
    @DisplayName("Достижение плитки 32% не заканчивает игру — доигрываем до лимита ходов")
    void reachingMaxTileDoesNotEndGame() {
        GameState state = new GameState(5);
        state.getBoard().setTile(0, 0, new Tile(16));
        state.getBoard().setTile(0, 1, new Tile(16));
        MoveResult result = engine.applyMove(state, Direction.LEFT);
        assertEquals(MoveResult.SUCCESS, result);
        assertEquals(32, state.getBoard().getTile(0, 0).cashback());
        assertEquals(4, state.getMovesLeft());
    }

    @Test
    @DisplayName("Score считается по правилам и растёт только при слияниях")
    void scoreIsCalculatedOnMerges() {
        GameState state = new GameState(5);
        state.getBoard().setTile(0, 0, new Tile(2));
        state.getBoard().setTile(0, 1, new Tile(2));
        engine.applyMove(state, Direction.LEFT);
        assertEquals(4, state.getScore());
    }
}
