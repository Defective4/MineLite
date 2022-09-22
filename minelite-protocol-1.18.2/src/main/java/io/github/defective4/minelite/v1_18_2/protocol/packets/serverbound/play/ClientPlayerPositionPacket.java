package io.github.defective4.minelite.v1_18_2.protocol.packets.serverbound.play;

import io.github.defective4.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")public class ClientPlayerPositionPacket extends OutPacket {

    public ClientPlayerPositionPacket(final double x, final double feetY, final double z, final boolean onGround) {
        super(0x11);
        putDouble(x);
        putDouble(feetY);
        putDouble(z);
        putBoolean(onGround);
    }

}
