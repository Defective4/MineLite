package net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play;

import java.nio.ByteBuffer;

import net.defekt.code.minelite.core.data.DataTypes;
import net.defekt.code.minelite.core.protocol.packets.InPacket;

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
