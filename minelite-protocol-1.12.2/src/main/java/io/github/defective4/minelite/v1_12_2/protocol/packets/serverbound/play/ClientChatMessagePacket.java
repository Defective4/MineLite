package io.github.defective4.minelite.v1_12_2.protocol.packets.serverbound.play;

import io.github.defective4.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")public class ClientChatMessagePacket extends OutPacket {

    public ClientChatMessagePacket(final String message) {
        super(0x02);
        putString(message);
    }

}
