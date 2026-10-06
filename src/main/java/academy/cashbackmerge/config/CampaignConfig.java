package academy.cashbackmerge.config;

import java.math.BigDecimal;
import java.util.List;

public record CampaignConfig(List<SpawnRule> spawnRules, int movesLimit) {
    public CampaignConfig {
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
}
