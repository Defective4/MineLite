package io.github.defective4.minelite.core.data;

@SuppressWarnings("javadoc")
public enum PlayerListAction {
    ADD_PLAYER(0), UPDATE_GAMEMODE(1), UPDATE_LATENCY(2), UPDATE_DISPLAY_NAME(3), REMOVE_PLAYER(4);

    private final int id;

    private PlayerListAction(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PlayerListAction getForID(final int id) {
        for (final PlayerListAction ac : PlayerListAction.values())
            if (ac.id == id) return ac;
        return PlayerListAction.REMOVE_PLAYER;
    }
}