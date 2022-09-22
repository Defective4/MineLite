package com.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.login;

import com.github.defective4.minelite.core.data.DataTypes;
import com.github.defective4.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerLoginCompressionPacket extends InPacket {

    private final int threshold;

    public ServerLoginCompressionPacket(final byte[] data) {
        super(data);
        threshold = DataTypes.readVarInt(getBuffer());
    }

    public int getThreshold() {
        return threshold;
    }

}
