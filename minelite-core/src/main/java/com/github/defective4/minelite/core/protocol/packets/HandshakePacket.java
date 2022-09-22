package com.github.defective4.minelite.core.protocol.packets;

@SuppressWarnings("javadoc")
public class HandshakePacket extends OutPacket {

    public HandshakePacket(final int protocol, final String host, final int port, final int state) {
        super(0x00);
        putVarInt(protocol);
        putString(host);
        putShort(port);
        putVarInt(state);
    }

}
