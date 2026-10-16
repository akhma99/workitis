package academy.cashbackmerge.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CampaignConfig(int boardSize, int movesLimit, List<SpawnRule> spawnRules, List<RewardRule> rewardTable,
                             int maxRewardCashback, BigDecimal maxRewardAllowedRate) {

    private static final int DEFAULT_BOARD_SIZE = 4;

    public CampaignConfig {
        if (boardSize <= 0) {
            boardSize = DEFAULT_BOARD_SIZE;
        }
        if (rewardTable == null) {
            rewardTable = List.of();
        }
        if (maxRewardAllowedRate == null) {
            maxRewardAllowedRate = BigDecimal.ONE;
        }
        if (movesLimit <= 0) {
            throw new IllegalArgumentException("Лимит ходов должен быть больше 0");
        }
        if (spawnRules == null || spawnRules.isEmpty()) {
            throw new IllegalArgumentException("Неправильный или пустой список правил");
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (SpawnRule value : spawnRules) {
            sum = sum.add(value.probability());
        }
        if (sum.compareTo(BigDecimal.ONE) != 0) {
            throw new IllegalArgumentException("Сумма вероятностей не равна 1");
        }
    }

    public CampaignConfig(List<SpawnRule> spawnRules, int movesLimit) {
        this(DEFAULT_BOARD_SIZE, movesLimit, spawnRules, List.of(), 0, BigDecimal.ONE);
    }

    public int getRewardForTile(int cashbackTile){
        int reward = 0;
        for (RewardRule rule : rewardTable){
            if (rule.cashback() == cashbackTile){
                reward = rule.reward();
            }
        }
        return reward;
}

}
