package io.github.defective4.minelite.core.data.bossbar;

@SuppressWarnings("javadoc")
public enum BossBarColor {
    PINK(0), BLUE(1), RED(2), GREEN(3), YELLOW(4), PURPLE(5), WHITE(6);

    private final int id;

    private BossBarColor(int id) {
        this.id = id;
    }

    public static BossBarColor getByID(int id) {
        for (BossBarColor color : BossBarColor.values())
            if (color.id == id) return color;
        return PINK;
    }
}