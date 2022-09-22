package net.defekt.code.minelite.core;

import java.util.List;
import java.util.UUID;

import net.defekt.code.minelite.core.data.Message;
import net.defekt.code.minelite.core.data.PlayerListAction;
import net.defekt.code.minelite.core.data.PlayerListInfo;
import net.defekt.code.minelite.core.protocol.packets.InPacket;
import net.defekt.code.minelite.core.protocol.packets.OutPacket;

/**
 * An abstract adapter class for receiving client related events. All methods of
 * this class are empty, it exists as a convenience for creating listener
 * objects.
 * 
 * @author Defective4
 *
 */
public abstract class ClientAdapter implements ClientListener {

    @Override
    public void messageReceived(final Message message, final int position, final UUID sender) {
    }

    @Override
    public void disconnected(final Message message) {
    }

    @Override
    public void packetReceiving(final InPacket packet) {
    }

    @Override
    public void packetReceived(final InPacket packet) {
    }

    @Override
    public void packetSending(final OutPacket packet) {
    }

    @Override
    public void packetSent(final OutPacket packet) {
    }

    @Override
    public void positionUpdated(final double x, final double y, final double z, final double previousX,
            final double previousY, final double previousZ, final float yaw, final float pitch, final float previousYaw,
            final float previousPitch, final double teleportID) {
    }

    @Override
    public void pluginMessageReceived(final String channel, final byte[] data) {
    }

    @Override
    public void joined(final int entityID) {
    }

    @Override
    public void connected(final String username, final UUID uid) {
    }

    @Override
    public void timeUpdated(final long worldTime, final long dayTime) {
    }

    @Override
    public void healthUpdated(final float health, final int food, final float saturation) {
    }

    @Override
    public void experienceUpdated(final float bar, final int level, final int totalExp) {
    }

    @Override
    public void playerListUpdated(final PlayerListAction action, final List<PlayerListInfo> players) {
    }

}
