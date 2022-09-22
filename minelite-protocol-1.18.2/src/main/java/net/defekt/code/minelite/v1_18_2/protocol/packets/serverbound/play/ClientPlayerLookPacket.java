package net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play;

import net.defekt.code.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")

    public ClientPlayerLookPacket(final float yaw, final float pitch, final boolean onGround) {
        super(0x13);
        putFloat(yaw);
        putFloat(pitch);
        putBoolean(onGround);
    }

}