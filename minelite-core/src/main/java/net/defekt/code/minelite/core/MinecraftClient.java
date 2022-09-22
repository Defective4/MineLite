package net.defekt.code.minelite.core;

import static net.defekt.code.minelite.core.data.DataTypes.readVarInt;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.Inflater;

import net.defekt.code.minelite.core.data.DataTypes;
import net.defekt.code.minelite.core.data.GameState;
import net.defekt.code.minelite.core.protocol.abstr.PacketHandler;
import net.defekt.code.minelite.core.protocol.abstr.PacketRegistry;
import net.defekt.code.minelite.core.protocol.abstr.ProtocolProvider;
import net.defekt.code.minelite.core.protocol.packets.HandshakePacket;
import net.defekt.code.minelite.core.protocol.packets.InPacket;
import net.defekt.code.minelite.core.protocol.packets.LoginStartPacket;
import net.defekt.code.minelite.core.protocol.packets.OutPacket;

/**
 * This is a client that can be connected to a server
 * 
 * @author Defective4
 *
 */
public class MinecraftClient implements AutoCloseable {
    private final String host;
    private final int port;
    private final ProtocolProvider protocol;
    private final PacketRegistry reg;
    private final PacketHandler handler;

    private String username;
    private UUID uid = null;

    private double x, y, z = 0;
    private float yaw, pitch;

    /**
     * Default constructor. You must specify an implementaion of ProtocolProvider,
     * which can be found in minecraft-protocol-x.x.x.jar
     */
    public MinecraftClient(final String host, final int port, final String username, final ProtocolProvider protocol) {
        this.host = host;
        this.username = username;
        this.port = port;
        this.protocol = protocol;
        this.reg = protocol.createPacketRegistry();
        this.handler = protocol.createPacketHandler();
    }

    private final Map<Option, Boolean> options = new ConcurrentHashMap<Option, Boolean>();

    private String brand = "vanilla";

    /**
     * Set an behavior option on this client
     */
    public void setOption(final Option option, final boolean state) {
        options.put(option, state);
    }

    /**
     * Get state of a behavior option.<br>
     * It an option is not set its default value will be returned.
     */
    public boolean getOption(final Option option) {
        return options.containsKey(option) ? options.get(option) : option.isDefaultOn();
    }

    private Socket socket = null;
    private OutputStream os = null;
    private DataInputStream is = null;

    private boolean compressionEnabled = false;
    private GameState state = GameState.LOGIN;

    private final List<ClientListener> clientListeners = Collections.synchronizedList(new ArrayList<ClientListener>());

    /**
     * Send a ServerboundPacket from this client.<br>
     * All client listeners will also get fired.
     */
    public void sendPacket(final OutPacket packet) throws IOException {
        if (isOpen()) {
            for (final ClientListener ls : getClientListeners()) {
                ls.packetSending(packet);
            }
            if (!packet.isCanceled()) {
                os.write(packet.getData(compressionEnabled));
                for (final ClientListener ls : getClientListeners()) {
                    ls.packetSent(packet);
                }
            }
        }
    }

    /**
     * Send a chat message from this client. <br>
     * Underlying implementation of this method is found in {@link ProtocolProvider}
     */
    public void sendMessage(final String message) throws IOException {
        protocol.sendChatMessage(this, message);
    }

    private final Inflater inflater = new Inflater();

    private boolean joined = false;

    /**
     * Connect to the server specified in constructor.
     */
    public void connect() throws IOException {
        try (Socket soc = new Socket(host, port)) {
            this.socket = soc;
            os = soc.getOutputStream();
            is = new DataInputStream(soc.getInputStream());

            sendPacket(new HandshakePacket(protocol.getPVN(), host, port, 2));
            sendPacket(new LoginStartPacket(username));

            while (isOpen()) {
                final int len = readVarInt(is);
                int id = -1;
                byte[] data = new byte[0];
                if (compressionEnabled) {
                    final int dlen = readVarInt(is);
                    if (dlen <= 0) {
                        id = readVarInt(is);
                        data = new byte[len - 2];
                        is.readFully(data);
                    } else {
                        final byte[] uncompressed = new byte[dlen];
                        final byte[] compressed = new byte[len - DataTypes.varIntSize(dlen)];
                        is.readFully(compressed);

                        inflater.setInput(compressed);
                        inflater.inflate(uncompressed);
                        inflater.reset();

                        id = uncompressed[0];
                        data = Arrays.copyOfRange(uncompressed, 1, uncompressed.length);
                    }
                } else {
                    id = is.readByte();
                    data = new byte[len - 1];
                    is.readFully(data);
                }

                final Class<? extends InPacket> pClass = reg.getPacketForID(state, id);
                if (pClass != null) {
                    final InPacket packet = pClass.getConstructor(byte[].class).newInstance(data);
                    handler.handle(packet, this);
                }
            }
        } catch (final Exception e) {
            throw new IOException(e);
        }
    }

    @SuppressWarnings("javadoc")
    public String getHost() {
        return host;
    }

    @SuppressWarnings("javadoc")
    public String getUsername() {
        return username;
    }

    @SuppressWarnings("javadoc")
    public int getPort() {
        return port;
    }

    /**
     * Check if this client is open and connected to a server
     * 
     * @return connected state
     */
    public boolean isOpen() {
        return socket != null && !socket.isClosed() && os != null && is != null;
    }

    @Override
    public void close() throws Exception {
        if (socket != null) {
            socket.close();
        }
    }

    @SuppressWarnings("javadoc")
    public boolean isCompressionEnabled() {
        return compressionEnabled;
    }

    /**
     * Used internally. <br>
     * Do not change if it's not needed. <br>
     * If compression is in the wrong state, client will throw an error when sending
     * or receiving a packet!
     * 
     * @param compressionEnabled
     */
    public void setCompressionEnabled(final boolean compressionEnabled) {
        this.compressionEnabled = compressionEnabled;
    }

    /**
     * Get current game state
     * 
     * @return LOGIN if client has not yet fully joined, <br>
     *         PLAY if client is fully in game.
     */
    public GameState getState() {
        return state;
    }

    @SuppressWarnings("javadoc")
    public void setState(final GameState state) {
        this.state = state;
    }

    /**
     * Get client's UUID received during login process
     * 
     * @return client's Unique ID
     */
    public UUID getUUID() {
        return uid;
    }

    /**
     * Used internally, has no direct effect on the connection.
     * 
     * @param username new user name
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Used internally, has no direct effect on the connection.
     * 
     * @param uid new UUID
     */
    public void setUUID(final UUID uid) {
        this.uid = uid;
    }

    @SuppressWarnings("javadoc")
    public List<ClientListener> getClientListeners() {
        return Collections.unmodifiableList(clientListeners);
    }

    /**
     * Add new listener to this client.
     * 
     * @param listener
     */
    public void addClientListener(final ClientListener listener) {
        clientListeners.add(listener);
    }

    @SuppressWarnings("javadoc")
    public void removeClientListener(final ClientListener listener) {
        clientListeners.remove(listener);
    }

    @SuppressWarnings("javadoc")
    public double getX() {
        return x;
    }

    @SuppressWarnings("javadoc")
    public double getY() {
        return y;
    }

    @SuppressWarnings("javadoc")
    public double getZ() {
        return z;
    }

    @SuppressWarnings("javadoc")
    public float getYaw() {
        return yaw;
    }

    @SuppressWarnings("javadoc")
    public float getPitch() {
        return pitch;
    }

    /**
     * Used internally, has no direct effect on the connection.
     * 
     * @param x
     */
    public void setX(final double x) {
        this.x = x;
    }

    /**
     * Used internally, has no direct effect on the connection.
     * 
     * @param y
     */
    public void setY(final double y) {
        this.y = y;
    }

    /**
     * Used internally, has no direct effect on the connection.
     * 
     * @param z
     */
    public void setZ(final double z) {
        this.z = z;
    }

    /**
     * Used internally, has no direct effect on the connection.
     * 
     * @param yaw
     */
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }

    /**
     * Used internally, has no direct effect on the connection.
     * 
     * @param pitch
     */
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }

    /**
     * Used internally, has no direct effect on the connection.
     * 
     * @param x
     * @param y
     * @param z
     * @param bitMask
     */
    public void setPosition(final double x, final double y, final double z, final byte bitMask) {
        final boolean relativeX = (bitMask & 0x01) != 0;
        final boolean relativeY = (bitMask & 0x02) != 0;
        final boolean relativeZ = (bitMask & 0x04) != 0;

        this.x = relativeX ? this.x + x : x;
        this.y = relativeY ? this.y + y : y;
        this.z = relativeZ ? this.z + z : z;
    }

    private boolean isSpawned = false;

    /**
     * Check if client has been spawned in the world.
     * 
     * @return spawned state
     */
    public boolean isSpawned() {
        return isSpawned;
    }

    /**
     * Used internally, may have an effect on the connection. <br>
     * Do not set if not needed.
     * 
     * @param isSpawned
     */
    public void setSpawned(final boolean isSpawned) {
        this.isSpawned = isSpawned;
    }

    /**
     * Check if client has successfully joined the server.
     * 
     * @return joined state
     */
    public boolean hasJoined() {
        return joined;
    }

    /**
     * Used internally, may have an effect on the connection.
     * 
     * @param joined
     */
    public void setJoined(final boolean joined) {
        this.joined = joined;
    }

    /**
     * Get client's brand as set in {@link #setBrand(String)}
     * 
     * @return client's brand name. Defaults to "vanilla"
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Set client's name to identify with the server. Only has effect, when set
     * before fully joining the server.<br>
     * If not set, defaults to vanilla. <br>
     * Shouldn't be <code>null</code>
     * 
     * @param brand
     */
    public void setBrand(final String brand) {
        this.brand = brand;
    }

}
