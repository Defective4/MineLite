package net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play;

import net.defekt.code.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")

    public enum Action {
        RESPAWN, STATS
    }

    public ClientStatusPacket(final Action action) {
        super(0x04);
        putVarInt(action == Action.RESPAWN ? 0 : 1);
    }

}