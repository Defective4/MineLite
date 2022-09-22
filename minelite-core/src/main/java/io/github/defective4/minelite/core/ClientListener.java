package io.github.defective4.minelite.core;

import java.util.List;
import java.util.UUID;

import io.github.defective4.minelite.core.data.Message;
import io.github.defective4.minelite.core.data.PlayerListAction;
import io.github.defective4.minelite.core.data.PlayerListInfo;
import io.github.defective4.minelite.core.protocol.packets.InPacket;
import io.github.defective4.minelite.core.protocol.packets.OutPacket;

/**
 * The listener interface for receiving client related events.
 * 
 * @author Defective4
 *
 */
public interface ClientListener {

    /**
     * Invoked when client has successfully passed login process. <br>
     * It does not mean the client is in game yet.
     */
    public void connected(String username, UUID uid);

    /**
     * Invoked when client has successfully joined the game. It should be called
     * after receiving first Join Game packet.
     * 
     */
    public void joined(int entityID);

    /**
     * Invoked when client has received a chat message.
     * 
     * @param position 0 - chat message<br>
     *                 1 - system message<br>
     *                 2 - action bar
     */
    public void messageReceived(Message message, int position, UUID sender);

    /**
     * Invoked when client is disconnected from server.
     */
    public void disconnected(Message message);

    /**
     * Invoked when client receives an packet before it is processed. <br>
     * Packets can be canceled in this method. <br>
     * Canceled packets aren't processed and they don't activate any further
     * listeners.
     */
    public void packetReceiving(InPacket packet);

    /**
     * Invoked when client received an packet after is is processed. <br>
     * Canceled packets do not fire this event.
     */
    public void packetReceived(InPacket packet);

    /**
     * Invoked when client tries to send a packet. <br>
     * Packets can be canceled in this method. <br>
     * Canceled packets aren't sent to the server and they don't activate any
     * further listeners.
     */
    public void packetSending(OutPacket packet);

    /**
     * Invoked after client successfully sends a packet. <br>
     * Canceling packets have no result at this point.
     */
    public void packetSent(OutPacket packet);

    /**
     * Invoked when client receives a plugin message.
     */
    public void pluginMessageReceived(String channel, byte[] data);

    /**
     * Invoked when client's position is updated on the server.
     */
    public void positionUpdated(double x, double y, double z, double previousX, double previousY, double previousZ,
            float yaw, float pitch, float previousYaw, float previousPitch, double teleportID);

    /**
     * Invoked when world time is updated.
     */
    public void timeUpdated(long worldTime, long dayTime);

    /**
     * Invoked when client's health, food or saturation levels get updated.
     */
    public void healthUpdated(float health, int food, float saturation);

    /**
     * Invoked when client's experience level is updated.
     */
    public void experienceUpdated(float bar, int level, int totalExp);

    /**
     * Invoked when client receives a player list update.
     */
    public void playerListUpdated(PlayerListAction action, List<PlayerListInfo> players);
}
