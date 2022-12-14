package io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play;

import java.nio.ByteBuffer;
import java.util.UUID;

import io.github.defective4.minelite.core.data.DataTypes;
import io.github.defective4.minelite.core.data.Message;
import io.github.defective4.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerChatMessagePacket extends InPacket {

    private final String json;
    private final byte position;
    private final UUID sender;

    public ServerChatMessagePacket(final byte[] data) {
        super(data);
        final ByteBuffer buffer = getBuffer();
        json = DataTypes.readString(buffer);
        position = buffer.get();
        sender = DataTypes.readUUID(buffer);
    }

    public Message getMessage() {
        return new Message(json);
    }

    public byte getPosition() {
        return position;
    }

    public UUID getSender() {
        return sender;
    }

}
