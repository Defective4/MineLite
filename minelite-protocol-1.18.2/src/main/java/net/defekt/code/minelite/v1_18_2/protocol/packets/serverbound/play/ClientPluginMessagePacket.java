package net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play;

import net.defekt.code.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")public class ClientPluginMessagePacket extends OutPacket {

    public ClientPluginMessagePacket(final String channel, final byte[] data) {
        super(0x0A);
        putString(channel);
        putBytes(data);
    }

}
