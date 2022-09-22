package com.github.defective4.minelite.core.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Stores information about a player entry in players list.
 * 
 * @author Defective4
 *
 */
@SuppressWarnings("javadoc")
public class PlayerListInfo {
    private final UUID uid;
    private final String name;
    private final List<PlayerListInfo.PlayerProperty> properties;
    private final PlayerListInfo.Gamemode gamemode;
    private final int ping;
    private final String displayName;

    public PlayerListInfo(final UUID uid, final String name, final List<PlayerListInfo.PlayerProperty> props,
            final PlayerListInfo.Gamemode gamemode, final int ping, final String displayName) {
        super();
        this.uid = uid;
        this.name = name;
        this.properties = props;
        this.gamemode = gamemode;
        this.ping = ping;
        this.displayName = displayName;
    }

    public enum Gamemode {
        SURVIVAL(0), CREATIVE(1), ADVENTURE(2), SPECTATOR(3);

        private final int id;

        private Gamemode(final int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static PlayerListInfo.Gamemode getForID(final int id) {
            for (final PlayerListInfo.Gamemode gm : Gamemode.values())
                if (gm.id == id) return gm;
            return CREATIVE;
        }

    }

    public static class PlayerProperty {
        private final String name;
        private final String value;
        private final String signature;

        public PlayerProperty(final String name, final String value, final String signature) {
            super();
            this.name = name;
            this.value = value;
            this.signature = signature;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public String getSignature() {
            return signature;
        }

    }

    public String getName() {
        return name;
    }

    public List<PlayerListInfo.PlayerProperty> getProperties() {
        return properties == null ? new ArrayList<PlayerListInfo.PlayerProperty>()
                : Collections.unmodifiableList(properties);
    }

    public PlayerListInfo.Gamemode getGamemode() {
        return gamemode;
    }

    public int getPing() {
        return ping;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UUID getUid() {
        return uid;
    }
}