package io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play;

import io.github.defective4.minelite.core.protocol.packets.InPacket;

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
