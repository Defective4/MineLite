package io.github.defective4.minelite.v1_12_2.protocol.packets.serverbound.play;

import io.github.defective4.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")public class ClientPluginMessagePacket extends OutPacket {

    public ClientPluginMessagePacket(final String channel, final byte[] data) {
        super(0x09);
        putString(channel);
        putBytes(data);
    }

}
