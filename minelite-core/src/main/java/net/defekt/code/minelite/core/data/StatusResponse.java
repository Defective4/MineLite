package net.defekt.code.minelite.core.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * Stored information received from server during Server List Ping
 * 
 * @author Defective4
 *
 */
@SuppressWarnings("javadoc")
public class StatusResponse {
    private final Map<String, Object> players;
    private Map<String, Object> version;
    private final JsonElement description;

    /**
     * Default constructor
     * 
     * @param online
     * @param max
     * @param vName
     * @param protocol
     * @param desc
     */
    public StatusResponse(final int online, final int max, final String vName, final int protocol, final String desc) {
        final Map<String, Object> map = new ConcurrentHashMap<String, Object>();
        players = version = map;
        map.put("online", online);
        map.put("max", max);
        map.put("name", vName);
        map.put("protocol", protocol);
        description = new JsonPrimitive(desc);
    }

    public int getOnlinePlayers() {
        return getInt(players, "online");
    }

    public int getMaxPlayers() {
        return getInt(players, "max");
    }

    public int getProtocolNumber() {
        return getInt(version, "protocol");
    }

    public String getVersionName() {
        return version.getOrDefault("name", "Unknown").toString();
    }

    public String getDescriptionJson() {
        return description.toString();
    }

    public String getDescription() {
        return Messages.parse(description.toString());
    }

    private static int getInt(final Map<String, Object> map, final String key) {
        int val = 0;
        if (map.containsKey(key)) {
            try {
                val = (int) Double.parseDouble(map.get(key).toString());
            } catch (final NumberFormatException ex) {
                val = 0;
            }
        }
        return val;
    }
}
