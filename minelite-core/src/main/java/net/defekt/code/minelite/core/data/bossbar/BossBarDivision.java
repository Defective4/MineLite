package net.defekt.code.minelite.core.data.bossbar;

@SuppressWarnings("javadoc")
public enum BossBarDivision {
    NO_DIV(0), DIV_6(1), DIV_10(2), DIV_12(3), DIV_20(4);

    private final int id;

    private BossBarDivision(int id) {
        this.id = id;
    }

    public static BossBarDivision getByID(int id) {
        for (BossBarDivision color : BossBarDivision.values())
            if (color.id == id) return color;
        return NO_DIV;
    }
}