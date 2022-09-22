package net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play;

import net.defekt.code.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")

    public ClientTeleportConfirmPacket(final int teleportID) {
        super(0x00);
        putVarInt(teleportID);
    }

}