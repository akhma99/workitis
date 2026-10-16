package academy.cashbackmerge.mandatory.mr3;

import static org.junit.jupiter.api.Assertions.fail;

import academy.cashbackmerge.config.CampaignConfig;
import academy.cashbackmerge.config.SpawnRule;
import academy.cashbackmerge.model.Board;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/** Обязательные тесты: награды, симуляция и отчёт. */
@DisplayName("MR3. Награды и симуляция")
class RewardAndSimulationTest {

    private static final int  DEFAULT_SIZE = 4;

    @Test
    @Disabled("MR3: реализуй тест и удали эту строку")
    @DisplayName("Максимальная плитка 16% даёт награду соответствующего tier")
    void rewardMatchesHighestTile() {
        Board board = new Board(DEFAULT_SIZE);
        //CampaignConfig config = new CampaignConfig(board.getSize(),5,)
    }

    @Test
    @Disabled("MR3: реализуй тест и удали эту строку")
    @DisplayName("Weighted random учитывает вероятности из конфига")
    void spawnRespectsConfiguredProbabilities() {
        fail("Тест не реализован");
    }

    @Test
    @Disabled("MR3: реализуй тест и удали эту строку")
    @DisplayName("Отчёт содержит распределение максимальных плиток и наград")
    void reportContainsDistributions() {
        fail("Тест не реализован");
    }

    @Test
    @Disabled("MR3: реализуй тест и удали эту строку")
    @DisplayName("Отчёт содержит долю игроков с максимальной наградой")
    void reportContainsMaxRewardRate() {
        fail("Тест не реализован");
    }

    @Test
    @Disabled("MR3: реализуй тест и удали эту строку")
    @DisplayName("Статус OK, если actualMaxRewardRate <= maxRewardAllowedRate, иначе FAILED")
    void reportStatusReflectsBusinessConstraint() {
        fail("Тест не реализован");
    }
}
