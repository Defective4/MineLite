package net.defekt.code.minelite.core.data.bossbar;

@SuppressWarnings("javadoc")
public enum BossBarAction {
    ADD(0), REMOVE(1), UPDATE_HP(2), UPDATE_TITLE(3), UPDATE_STYLE(4), UPDATE_FLAGS(5);

    private final int id;

    private BossBarAction(int id) {
        this.id = id;
    }

    public static BossBarAction getByID(int id) {
        for (BossBarAction color : BossBarAction.values())
            if (color.id == id) return color;
        return REMOVE;
    }

}
