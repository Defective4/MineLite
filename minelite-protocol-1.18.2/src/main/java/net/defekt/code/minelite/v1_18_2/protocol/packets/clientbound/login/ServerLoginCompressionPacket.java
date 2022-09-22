package net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.login;

import net.defekt.code.minelite.core.data.DataTypes;
import net.defekt.code.minelite.core.protocol.packets.InPacket;

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
