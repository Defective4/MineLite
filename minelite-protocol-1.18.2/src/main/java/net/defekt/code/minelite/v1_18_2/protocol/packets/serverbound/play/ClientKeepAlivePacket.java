package net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play;

import net.defekt.code.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")public class ClientKeepAlivePacket extends OutPacket {

    public ClientKeepAlivePacket(final long payload) {
        super(0x0f);
        putLong(payload);
    }

}
