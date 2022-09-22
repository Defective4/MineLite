package io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play;

import java.nio.ByteBuffer;

import io.github.defective4.minelite.core.data.DataTypes;
import io.github.defective4.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerUpdateExperiencePacket extends InPacket {

    private final float expBar;
    private int level;
    private int totalExp;

    public ServerUpdateExperiencePacket(final byte[] data) {
        super(data);
        final ByteBuffer buf = getBuffer();
        expBar = buf.getFloat();
        level = DataTypes.readVarInt(buf);
        totalExp = DataTypes.readVarInt(buf);
    }

    public float getExpBar() {
        return expBar;
    }

    public int getLevel() {
        return level;
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public void setTotalExp(final int totalExp) {
        this.totalExp = totalExp;
    }

}
