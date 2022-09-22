package io.github.defective4.minelite.core.protocol.packets;

import java.nio.ByteBuffer;

import io.github.defective4.minelite.core.Cancellable;

/**
 * Represents a Clientbound Packet. <br>
 * It contains all data received from server.
 * 
 * @author Defective4
 *
 */
@SuppressWarnings("javadoc")
public class InPacket implements Cancellable {
    private final byte[] data;

    public InPacket(final byte[] data) {
        this.data = data;
    }

    protected byte[] getData() {
        return data;
    }

    protected ByteBuffer getBuffer() {
        return ByteBuffer.wrap(data);
    }

    private boolean isCanceled = false;

    @Override
    public void setCanceled(final boolean canceled) {
        this.isCanceled = canceled;
    }

    @Override
    public boolean isCanceled() {
        return isCanceled;
    }
}
