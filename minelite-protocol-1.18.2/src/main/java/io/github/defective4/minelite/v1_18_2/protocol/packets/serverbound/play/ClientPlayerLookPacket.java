package io.github.defective4.minelite.v1_18_2.protocol.packets.serverbound.play;

import io.github.defective4.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")public class ClientPlayerLookPacket extends OutPacket {

    public ClientPlayerLookPacket(final float yaw, final float pitch, final boolean onGround) {
        super(0x13);
        putFloat(yaw);
        putFloat(pitch);
        putBoolean(onGround);
    }

}
