package net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.login;

import java.nio.ByteBuffer;
import java.util.UUID;

import net.defekt.code.minelite.core.data.DataTypes;
import net.defekt.code.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerLoginSuccessPacket extends InPacket {

    private final UUID uid;
    private final String username;

    public ServerLoginSuccessPacket(final byte[] data) {
        super(data);
        final ByteBuffer buffer = getBuffer();
        uid = DataTypes.readUUID(buffer);
        username = DataTypes.readString(buffer);
    }

    public UUID getUUID() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

}
