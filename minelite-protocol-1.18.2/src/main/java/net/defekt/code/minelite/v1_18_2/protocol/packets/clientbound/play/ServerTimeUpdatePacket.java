package net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play;

import java.nio.ByteBuffer;

import net.defekt.code.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerTimeUpdatePacket extends InPacket {

    private final long worldAge;
    private final long dayTime;

    public ServerTimeUpdatePacket(final byte[] data) {
        super(data);
        final ByteBuffer buffer = getBuffer();
        worldAge = buffer.getLong();
        dayTime = buffer.getLong();
    }

    public long getWorldAge() {
        return worldAge;
    }

    public long getDayTime() {
        return dayTime;
    }

}
