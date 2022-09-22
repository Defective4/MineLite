package io.github.defective4.minelite.v1_18_2.protocol.packets.serverbound.play;

import io.github.defective4.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")public class ClientStatusPacket extends OutPacket {

    public enum Action {
        RESPAWN, STATS
    }

    public ClientStatusPacket(final Action action) {
        super(0x04);
        putVarInt(action == Action.RESPAWN ? 0 : 1);
    }

}
