package io.github.defective4.minelite.core;

import static io.github.defective4.minelite.core.data.DataTypes.readVarInt;
import static io.github.defective4.minelite.core.data.DataTypes.writeString;
import static io.github.defective4.minelite.core.data.DataTypes.writeVarInt;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import com.google.gson.Gson;

import io.github.defective4.minelite.core.data.DataTypes;
import io.github.defective4.minelite.core.data.StatusResponse;

/**
 * Can be used to retrieve information about a server via Server List Ping.
 * 
 * @author Defective4
 *
 */
public class MinecraftStat {

    /**
     * Perform a legacy Server List Ping on the specified server. <br>
     * Typically used for servers below version 1.7, but can be used with any modern
     * server. <br>
     * For version 1.7 above you should use {@link #ping(String, int)}.
     * 
     * @param host
     * @param port
     * @return data returned by server.
     * @throws IOException
     */
    public static StatusResponse legacyPing(final String host, final int port) throws IOException {
        try (Socket soc = new Socket(host, port)) {
            final OutputStream os = soc.getOutputStream();
            final InputStream is = soc.getInputStream();
            os.write(0xFE);

            final byte[] rawData = new byte[10240];
            final int read = is.read(rawData);
            final byte[] truncData = Arrays.copyOfRange(rawData, 3, read);

            final String data = new String(truncData, "UTF-16BE");
            final int delCount = data.length() - data.replace("\u00a7", "").length();

            String pCountString = data;

            for (int x = 0; x < delCount - 1; x++) {
                pCountString = pCountString.substring(pCountString.indexOf("\u00a7") + 1);
            }

            final String desc = data.substring(0, data.lastIndexOf("\u00a7" + pCountString));
            final String[] pCountSplit = pCountString.split("\u00a7");
            final int online = Integer.parseInt(pCountSplit[0]);
            final int max = Integer.parseInt(pCountSplit[1]);

            return new StatusResponse(online, max, "Legacy", -1, desc);
        }
    }

    /**
     * Perform a Server List Ping on the specified server.<br>
     * Works only for servers with version 1.7 or above. <br>
     * For older versions see {@link #legacyPing(String, int)}
     * 
     * @param host
     * @param port
     * @return data returned by server
     * @throws IOException
     */
    public static StatusResponse ping(final String host, final int port) throws IOException {
        try (Socket soc = new Socket(host, port)) {
            final OutputStream os = soc.getOutputStream();
            final DataInputStream is = new DataInputStream(soc.getInputStream());
            final byte[] handshake = createPingHS(host, port);
            writeVarInt(handshake.length, os);
            os.write(handshake);
            os.write(1);
            os.write(0);

            final int len = readVarInt(is);
            if (len <= 0) throw new IOException("Illegal received packet length: " + len);
            final int id = is.read();
            if (id != 0x00) throw new IOException("Illegal received packet ID: 0x" + Integer.toHexString(len));

            final String json = DataTypes.readString(is);

            return new Gson().fromJson(json, StatusResponse.class);
        }
    }

    private static byte[] createPingHS(final String host, final int port) {
        try {
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            final DataOutputStream wrapper = new DataOutputStream(buffer);
            wrapper.write(0);
            writeVarInt(-1, wrapper);
            writeString(host, wrapper);
            wrapper.writeShort(port);
            wrapper.write(1);

            return buffer.toByteArray();
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
