package com.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.github.defective4.minelite.core.data.DataTypes;
import com.github.defective4.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerJoinGamePacket extends InPacket {

    private final int entityID;
    private final boolean hardcore;
    private final byte gamemode;
    private final byte previousGamemode;
    private final List<String> dimensions;

    public ServerJoinGamePacket(final byte[] data) {
        super(data);

        final ByteBuffer buf = getBuffer();
        entityID = buf.getInt();
        hardcore = buf.get() == 1;
        gamemode = buf.get();
        previousGamemode = buf.get();
        dimensions = new ArrayList<String>();
        for (int x = 0; x < DataTypes.readVarInt(buf); x++) {
            dimensions.add(DataTypes.readString(buf));
        }
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

    public byte getPreviousGamemode() {
        return previousGamemode;
    }

    public List<String> getDimensions() {
        return dimensions;
    }

}
