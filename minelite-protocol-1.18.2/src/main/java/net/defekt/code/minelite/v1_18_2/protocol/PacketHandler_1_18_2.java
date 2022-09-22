package net.defekt.code.minelite.v1_18_2.protocol;

import java.io.IOException;
import java.util.List;

import net.defekt.code.minelite.core.ClientListener;
import net.defekt.code.minelite.core.MinecraftClient;
import net.defekt.code.minelite.core.Option;
import net.defekt.code.minelite.core.data.GameState;
import net.defekt.code.minelite.core.data.Message;
import net.defekt.code.minelite.core.data.PlayerListAction;
import net.defekt.code.minelite.core.data.PlayerListInfo;
import net.defekt.code.minelite.core.protocol.abstr.PacketHandler;
import net.defekt.code.minelite.core.protocol.packets.InPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.login.ServerLoginCompressionPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.login.ServerLoginSuccessPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerActionBarPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerChatMessagePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerDisconnectPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerJoinGamePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerKeepAlivePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerPlayerInfoPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerPlayerPositionAndLookPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerPluginMessagePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerTimeUpdatePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerUpdateExperiencePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerUpdateHealthPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play.ClientKeepAlivePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play.ClientPlayerPositionAndLookPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play.ClientPluginMessagePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play.ClientStatusPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play.ClientStatusPacket.Action;
import net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play.ClientTeleportConfirmPacket;

/**
 * An implementation of {@link PacketHandler} for version 1.18.2
 * 
 * @author Defective4
 *
 */
public class PacketHandler_1_18_2 implements PacketHandler {

    @Override
    public void handle(final InPacket packet, final MinecraftClient client) throws IOException {

        for (final ClientListener ls : client.getClientListeners()) {
            ls.packetReceiving(packet);
        }
        if (!packet.isCanceled()) {
            if (packet instanceof ServerPlayerInfoPacket) {
                final ServerPlayerInfoPacket p = (ServerPlayerInfoPacket) packet;
                final PlayerListAction act = p.getAction();
                final List<PlayerListInfo> players = p.getPlayers();
                for (final ClientListener ls : client.getClientListeners()) {
                    ls.playerListUpdated(act, players);
                }
            } else if (packet instanceof ServerActionBarPacket) {
                final Message msg = ((ServerActionBarPacket) packet).getMessage();
                for (final ClientListener ls : client.getClientListeners()) {
                    ls.messageReceived(msg, 2, null);
                }
            } else if (packet instanceof ServerUpdateExperiencePacket) {
                final ServerUpdateExperiencePacket p = (ServerUpdateExperiencePacket) packet;
                for (final ClientListener ls : client.getClientListeners()) {
                    ls.experienceUpdated(p.getExpBar(), p.getLevel(), p.getTotalExp());
                }

            } else if (packet instanceof ServerUpdateHealthPacket) {
                final ServerUpdateHealthPacket p = (ServerUpdateHealthPacket) packet;
                for (final ClientListener ls : client.getClientListeners()) {
                    ls.healthUpdated(p.getHealth(), p.getFood(), p.getSaturation());
                }
                if (p.getHealth() <= 0 && client.getOption(Option.AUTO_RESPAWN)) {
                    client.sendPacket(new ClientStatusPacket(Action.RESPAWN));
                }
            } else if (packet instanceof ServerTimeUpdatePacket) {
                final ServerTimeUpdatePacket p = (ServerTimeUpdatePacket) packet;
                for (final ClientListener ls : client.getClientListeners()) {
                    ls.timeUpdated(p.getWorldAge(), p.getDayTime());
                }
            } else if (packet instanceof ServerJoinGamePacket) {
                if (!client.hasJoined()) {
                    client.setJoined(true);
                    for (final ClientListener ls : client.getClientListeners()) {
                        ls.joined(((ServerJoinGamePacket) packet).getEntityID());
                    }
                    if (client.getOption(Option.SEND_CLIENT_BRAND_ON_JOIN)) {
                        client.sendPacket(
                                new ClientPluginMessagePacket("minecraft:brand", client.getBrand().getBytes("UTF-8")));
                    }
                }
            } else if (packet instanceof ServerPluginMessagePacket) {
                final ServerPluginMessagePacket p = (ServerPluginMessagePacket) packet;
                final String channel = p.getChannel();
                final byte[] data = p.getData();
                for (final ClientListener ls : client.getClientListeners()) {
                    ls.pluginMessageReceived(channel, data);
                }
                if (channel.equals("minecraft:register") && client.getOption(Option.AUTO_REGISTER_PLUGIN_CHANNELS)) {
                    client.sendPacket(new ClientPluginMessagePacket(channel, data));
                }
            } else if (packet instanceof ServerPlayerPositionAndLookPacket) {
                final ServerPlayerPositionAndLookPacket p = (ServerPlayerPositionAndLookPacket) packet;
                if (client.isSpawned()) {
                    for (final ClientListener ls : client.getClientListeners()) {
                        ls.positionUpdated(p.getX(), p.getY(), p.getZ(), client.getX(), client.getY(), client.getZ(),
                                p.getYaw(), p.getPitch(), client.getYaw(), client.getPitch(), p.getTeleportID());
                    }
                }
                client.setPosition(p.getX(), p.getY(), p.getZ(), p.getFlags());
                client.setYaw(p.getYaw());
                client.setPitch(p.getPitch());

                if (!client.isSpawned()) {
                    client.setSpawned(true);
                }
                if (client.getOption(Option.AUTO_UPDATE_SERVER_POSITION)) {
                    client.sendPacket(new ClientTeleportConfirmPacket(p.getTeleportID()));
                    client.sendPacket(new ClientPlayerPositionAndLookPacket(p.getX(), p.getY(), p.getZ(), p.getYaw(),
                            p.getPitch(), true));
                }
            } else if (packet instanceof ServerDisconnectPacket) {
                for (final ClientListener ls : client.getClientListeners()) {
                    ls.disconnected(((ServerDisconnectPacket) packet).getDisconnectMessage());
                }
                try {
                    client.close();
                } catch (final Exception ex) {
                    throw new IOException(ex);
                }
            } else if (packet instanceof ServerChatMessagePacket) {
                final ServerChatMessagePacket p = (ServerChatMessagePacket) packet;
                for (final ClientListener ls : client.getClientListeners()) {
                    ls.messageReceived(p.getMessage(), p.getPosition(), p.getSender());
                }
            } else if (packet instanceof ServerKeepAlivePacket) {
                if (client.getOption(Option.KEEP_ALIVE)) {
                    final long payload = ((ServerKeepAlivePacket) packet).getPayload();
                    client.sendPacket(new ClientKeepAlivePacket(payload));
                }
            } else if (packet instanceof ServerLoginSuccessPacket) {
                final ServerLoginSuccessPacket p = (ServerLoginSuccessPacket) packet;
                client.setState(GameState.PLAY);
                client.setUsername(p.getUsername());
                client.setUUID(p.getUUID());

                for (final ClientListener ls : client.getClientListeners()) {
                    ls.connected(p.getUsername(), p.getUUID());
                }
            } else if (packet instanceof ServerLoginCompressionPacket) {
                client.setCompressionEnabled(((ServerLoginCompressionPacket) packet).getThreshold() > 0);
            }
            for (final ClientListener ls : client.getClientListeners()) {
                ls.packetReceived(packet);
            }
        }
    }

}
