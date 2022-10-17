package io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play;

import java.nio.ByteBuffer;

import io.github.defective4.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")
public class ServerJoinGamePacket extends InPacket {

    private final int entityID;
    private final boolean hardcore;
    private final byte gamemode;

    public ServerJoinGamePacket(final byte[] data) {
        super(data);

        final ByteBuffer buf = getBuffer();
        entityID = buf.getInt();
        gamemode = buf.get();
        hardcore = (gamemode & 0x8) != 0;

    }

    public int getEntityID() {
        return entityID;
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public byte getGamemode() {
        return gamemode;
    }

}
