package com.github.defective4.minelite.core.data;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Utility class with methods to write and read data used by Minecraft protocol.
 * 
 * see <a href="https://wiki.vg/Data_types">wiki.vg</a>
 * 
 * @author Defective4
 *
 */
@SuppressWarnings("javadoc")
public class DataTypes {

    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    public static UUID readUUID(final ByteBuffer buffer) {
        return new UUID(buffer.getLong(), buffer.getLong());
    }

    public static int varIntSize(int value) {
        int size = 0;
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                size++;
                return size;
            }
            size++;
            value >>>= 7;
        }
    }

    public static String readString(final ByteBuffer buffer) {
        final byte[] data = new byte[readVarInt(buffer)];
        buffer.get(data);
        try {
            return new String(data, "UTF-8");
        } catch (final UnsupportedEncodingException ex) {
            return new String(data);
        }
    }

    public static String readString(final DataInputStream is) throws IOException {
        final byte[] data = new byte[readVarInt(is)];
        is.readFully(data);
        return new String(data, "UTF-8");
    }

    public static int readVarInt(final ByteBuffer buffer) {
        int value = 0;
        int position = 0;
        byte currentByte;
        while (true) {
            currentByte = buffer.get();
            value |= (currentByte & SEGMENT_BITS) << position;
            if ((currentByte & CONTINUE_BIT) == 0) {
                break;
            }
            position += 7;
            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }
        return value;
    }

    public static int readVarInt(final DataInputStream is) throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;
        while (true) {
            currentByte = is.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;
            if ((currentByte & CONTINUE_BIT) == 0) {
                break;
            }
            position += 7;
            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }
        return value;
    }

    public static void writeString(final String value, final OutputStream os) throws IOException {
        final byte[] data = value.getBytes("UTF-8");
        writeVarInt(data.length, os);
        os.write(data);
    }

    public static void writeString(final String value, final ByteArrayOutputStream os) {
        try {
            writeString(value, (OutputStream) os);
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeVarInt(final int value, final ByteArrayOutputStream os) {
        try {
            writeVarInt(value, (OutputStream) os);
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeVarInt(int value, final OutputStream os) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                os.write(value);
                return;
            }
            os.write((value & SEGMENT_BITS) | CONTINUE_BIT);
            value >>>= 7;
        }
    }
}
