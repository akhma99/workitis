package academy.cashbackmerge.mandatory.mr1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import academy.cashbackmerge.config.CampaignConfig;
import academy.cashbackmerge.config.SpawnRule;
import academy.cashbackmerge.model.GameInitializer;
import academy.cashbackmerge.model.GameState;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Обязательные тесты: воспроизводимость по seed. */
@DisplayName("MR1. Воспроизводимость по seed")
class SeedTest {

    @Test
    @DisplayName("Одинаковый конфиг и seed дают одинаковое начальное поле")
    void sameConfigAndSeedProduceSameInitialBoard() {
        List<SpawnRule> rules =
                List.of(new SpawnRule(1, new BigDecimal("0.8")), new SpawnRule(2, new BigDecimal("0.2")));
        CampaignConfig config = new CampaignConfig(rules, 10);
        long seed = 9;
        GameInitializer initializer = new GameInitializer();
        GameState state1 = initializer.createInitialState(config, seed);
        GameState state2 = initializer.createInitialState(config, seed);
        assertEquals(state1.getBoard(), state2.getBoard());
    }
}
