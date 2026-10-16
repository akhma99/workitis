package academy.cashbackmerge.config;

public record RewardRule(int cashback, int reward) {
    public RewardRule{
        if (cashback<=0 || reward<0){
            throw new IllegalArgumentException("Некорректный кэшбэк или награда");
        }
    }
}
