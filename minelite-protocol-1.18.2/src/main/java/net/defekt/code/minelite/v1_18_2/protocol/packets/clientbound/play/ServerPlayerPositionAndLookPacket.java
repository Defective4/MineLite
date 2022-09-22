package net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play;

import java.nio.ByteBuffer;

import net.defekt.code.minelite.core.data.DataTypes;
import net.defekt.code.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerPlayerPositionAndLookPacket extends InPacket {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private byte flags;
    private int teleportID;
    private boolean dismount;

    public ServerPlayerPositionAndLookPacket(final byte[] data) {
        super(data);
        final ByteBuffer buf = getBuffer();
        x = buf.getDouble();
        y = buf.getDouble();
        z = buf.getDouble();
        yaw = buf.getFloat();
        pitch = buf.getFloat();
        flags = buf.get();
        teleportID = DataTypes.readVarInt(buf);
        dismount = buf.get() == 1;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public byte getFlags() {
        return flags;
    }

    public int getTeleportID() {
        return teleportID;
    }

    public boolean isDismount() {
        return dismount;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public void setZ(final double z) {
        this.z = z;
    }

    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }

    public void setFlags(final byte flags) {
        this.flags = flags;
    }

    public void setTeleportID(final int teleportID) {
        this.teleportID = teleportID;
    }

    public void setDismount(final boolean dismount) {
        this.dismount = dismount;
    }

}
