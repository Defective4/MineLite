package io.github.defective4.minelite.v1_12_2.protocol.packets.serverbound.play;

import io.github.defective4.minelite.core.protocol.packets.OutPacket;

@SuppressWarnings("javadoc")

    public ClientKeepAlivePacket(final long payload) {
        super(0x0b);
        putLong(payload);
    }

}