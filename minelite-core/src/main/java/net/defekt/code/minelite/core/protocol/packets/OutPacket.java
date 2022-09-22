package net.defekt.code.minelite.core.protocol.packets;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.defekt.code.minelite.core.Cancellable;
import net.defekt.code.minelite.core.data.DataTypes;

/**
 * Represents a Serverbound Packet.<br>
 * It contains all data that can be sent to a server.
 * 
 * @author Defective4
 *
 */
@SuppressWarnings("javadoc")
public class OutPacket implements Cancellable {
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final DataOutputStream wrapper = new DataOutputStream(buffer);

    public OutPacket(final int id) {
        buffer.write(id);
    }

    protected DataOutputStream getStream() {
        return wrapper;
    }

    protected void putBytes(final byte... value) {
        try {
            wrapper.write(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putFloat(final float value) {
        try {
            wrapper.writeFloat(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putBoolean(final boolean value) {
        try {
            wrapper.writeBoolean(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putDouble(final double value) {
        try {
            wrapper.writeDouble(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putLong(final long value) {
        try {
            wrapper.writeLong(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putShort(final int value) {
        try {
            wrapper.writeShort(value);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    protected void putString(final String value) {
        DataTypes.writeString(value, buffer);
    }

    protected void putVarInt(final int value) {
        DataTypes.writeVarInt(value, buffer);
    }

    /**
     * Wrap this packet in a byte array. <br>
     * Resulting array is usually ready to be sent to the server.
     */
    public byte[] getData(final boolean compressionFormat) {
        try {
            wrapper.flush();
            final byte[] raw = buffer.toByteArray();

            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            DataTypes.writeVarInt(raw.length + (compressionFormat ? 1 : 0), buffer);
            if (compressionFormat) {
                buffer.write(0);
            }
            buffer.write(raw);

            return buffer.toByteArray();
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
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
