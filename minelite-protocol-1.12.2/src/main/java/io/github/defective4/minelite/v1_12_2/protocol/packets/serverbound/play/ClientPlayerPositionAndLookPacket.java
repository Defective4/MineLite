package io.github.defective4.minelite.v1_12_2.protocol.packets.serverbound.play;

import io.github.defective4.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")public class ClientPlayerPositionAndLookPacket extends OutPacket {

    public ClientPlayerPositionAndLookPacket(final double x, final double feetY, final double z, final float yaw,
            final float pitch, final boolean onGround) {
        super(0x0E);
        putDouble(x);
        putDouble(feetY);
        putDouble(z);
        putFloat(yaw);
        putFloat(pitch);
        putBoolean(onGround);
    }

}
