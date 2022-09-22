package com.github.defective4.minelite.core.protocol.abstr;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.defective4.minelite.core.data.GameState;
import com.github.defective4.minelite.core.protocol.packets.InPacket;

/**
 * Abstract class used to store packet classes and their IDs If you want to
 * implement it, see <a href="https://wiki.vg/Protocol">wiki.vg</a>
 */
public abstract class PacketRegistry {
    private final Map<GameState, Map<Integer, Class<? extends InPacket>>> packets = new ConcurrentHashMap<GameState, Map<Integer, Class<? extends InPacket>>>() {
        {
            put(GameState.LOGIN, initLoginPackets());
            put(GameState.PLAY, initPlayPackets());
        }
    };

    /**
     * Has to be implemented to return Map object containing Clientbound Packets
     * with their IDs set as keys.
     * 
     * @return non-null Map
     */
    public abstract Map<Integer, Class<? extends InPacket>> initLoginPackets();

    /**
     * Has to be implemented to return Map object containing Clientbound Packets
     * with their IDs set as keys.
     * 
     * @return non-null Map
     */
    public abstract Map<Integer, Class<? extends InPacket>> initPlayPackets();

    /**
     * Get packet class for provided ID and Game State.
     * 
     * @param state
     * @param id
     * @return packet class or null if not found
     */
    public Class<? extends InPacket> getPacketForID(final GameState state, final int id) {
        final Map<Integer, Class<? extends InPacket>> pMap = packets.get(state);
        if (pMap != null) return pMap.get(id);
        return null;
    }
}
