package net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play;

import net.defekt.code.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerKeepAlivePacket extends InPacket {

    private final long payload;

    public ServerKeepAlivePacket(final byte[] data) {
        super(data);
        this.payload = getBuffer().getLong();
    }

    public long getPayload() {
        return payload;
    }

}
