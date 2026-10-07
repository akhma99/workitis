package academy.cashbackmerge.mandatory.mr1;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import academy.cashbackmerge.config.CampaignConfig;
import academy.cashbackmerge.config.SpawnRule;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: конфиг кампании. */
@DisplayName("MR1. Конфиг кампании")
class ConfigValidationTest {

    @Test
    @DisplayName("Валидный конфиг принимается")
    void validConfigIsAccepted() {
        List<SpawnRule> spawnRules = List.of(new SpawnRule(1, new BigDecimal("1")));
        assertDoesNotThrow(() -> new CampaignConfig(spawnRules, 5));
    }

    @Test
    @DisplayName("Сумма вероятностей spawn-правил не равна 1 — конфиг отклоняется")
    void probabilitiesNotSummingToOneAreRejected() {
        List<SpawnRule> spawnRules = List.of(new SpawnRule(1, new BigDecimal("0.8")));
        assertThrows(IllegalArgumentException.class, () -> {
            new CampaignConfig(spawnRules, 3);
        });
    }

    @Test
    @DisplayName("movesLimit <= 0 — конфиг отклоняется")
    void nonPositiveMovesLimitIsRejected() {
        List<SpawnRule> spawnRules = List.of(new SpawnRule(1, new BigDecimal("1")));
        assertThrows(IllegalArgumentException.class, () -> {
            new CampaignConfig(spawnRules, -2);
        });
    }

    @Test
    @DisplayName("Пустой список spawn-правил — конфиг отклоняется")
    void emptySpawnRulesAreRejected() {
        List<SpawnRule> spawnRules = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new CampaignConfig(spawnRules, 3);
        });
    }
}
