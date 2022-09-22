package com.github.defective4.minelite.v1_18_2.protocol.packets.serverbound.play;

import com.github.defective4.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")public class ClientChatMessagePacket extends OutPacket {

    public ClientChatMessagePacket(final String message) {
        super(0x03);
        putString(message);
    }

}
