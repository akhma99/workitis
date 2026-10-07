package academy.cashbackmerge.model;

import academy.cashbackmerge.config.CampaignConfig;
import academy.cashbackmerge.config.SpawnRule;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class GameInitializer {

    public GameState createInitialState(CampaignConfig config, long seed) {
        Random random = new Random(seed);
        GameState gameState = new GameState(config.movesLimit());
        spawnTile(gameState, config, random);
        spawnTile(gameState, config, random);
        return gameState;
    }

    public int getRandomCashback(List<SpawnRule> spawnRules, Random random) {
        BigDecimal randomValue = BigDecimal.valueOf(random.nextDouble());
        BigDecimal currentSum = BigDecimal.ZERO;
        int answer = 0;
        for (SpawnRule rule : spawnRules) {
            currentSum = currentSum.add(rule.probability());
            answer = rule.cashback();
            if (randomValue.compareTo(currentSum) <= 0) {
                break;
            }
        }
        return answer;
    }

    public void spawnTile(GameState gameState, CampaignConfig config, Random random) {
        List<Position> emptyCells = gameState.getBoard().getEmptyPositions();
        if (emptyCells.isEmpty()) {
            return;
        }
        Position position = emptyCells.get(random.nextInt(emptyCells.size()));
        gameState
                .getBoard()
                .setTile(position.row(), position.col(), new Tile(getRandomCashback(config.spawnRules(), random)));
    }
}
