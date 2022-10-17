package io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.login;

import java.nio.ByteBuffer;
import java.util.UUID;

import io.github.defective4.minelite.core.data.DataTypes;
import io.github.defective4.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")
public class ServerLoginSuccessPacket extends InPacket {

    private final UUID uid;
    private final String username;

    public ServerLoginSuccessPacket(final byte[] data) {
        super(data);
        final ByteBuffer buffer = getBuffer();
        uid = UUID.fromString(DataTypes.readString(buffer));
        username = DataTypes.readString(buffer);
    }

    public UUID getUUID() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

}
