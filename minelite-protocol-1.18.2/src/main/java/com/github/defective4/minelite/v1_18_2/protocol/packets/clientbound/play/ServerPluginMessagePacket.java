package com.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play;

import java.nio.ByteBuffer;

import com.github.defective4.minelite.core.data.DataTypes;
import com.github.defective4.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerPluginMessagePacket extends InPacket {

    private final String channel;
    private final byte[] data;

    public ServerPluginMessagePacket(final byte[] data) {
        super(data);
        final ByteBuffer buf = getBuffer();
        channel = DataTypes.readString(buf);
        this.data = new byte[buf.remaining()];
        buf.get(this.data);
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public byte[] getData() {
        return data;
    }

}
