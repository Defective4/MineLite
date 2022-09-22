package net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play;

import java.nio.ByteBuffer;

import net.defekt.code.minelite.core.data.DataTypes;
import net.defekt.code.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerUpdateHealthPacket extends InPacket {

    private float health;
    private int food;
    private float saturation;

    public ServerUpdateHealthPacket(final byte[] data) {
        super(data);
        final ByteBuffer buf = getBuffer();
        health = buf.getFloat();
        food = DataTypes.readVarInt(buf);
        saturation = buf.getFloat();
    }

    public float getHealth() {
        return health;
    }

    public int getFood() {
        return food;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setHealth(final float health) {
        this.health = health;
    }

    public void setFood(final int food) {
        this.food = food;
    }

    public void setSaturation(final float saturation) {
        this.saturation = saturation;
    }

}
