package com.github.defective4.minelite.v1_18_2.protocol.packets.serverbound.play;

import com.github.defective4.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")public class ClientTeleportConfirmPacket extends OutPacket {

    public ClientTeleportConfirmPacket(final int teleportID) {
        super(0x00);
        putVarInt(teleportID);
    }

}
