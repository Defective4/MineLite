package io.github.defective4.minelite.v1_12_2.protocol;

import java.io.IOException;
import java.util.List;

import io.github.defective4.minelite.core.ClientListener;
import io.github.defective4.minelite.core.MinecraftClient;
import io.github.defective4.minelite.core.Option;
import io.github.defective4.minelite.core.data.GameState;
import io.github.defective4.minelite.core.data.PlayerListAction;
import io.github.defective4.minelite.core.data.PlayerListInfo;
import io.github.defective4.minelite.core.protocol.abstr.PacketHandler;
import io.github.defective4.minelite.core.protocol.packets.InPacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.login.ServerLoginCompressionPacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.login.ServerLoginSuccessPacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play.ServerChatMessagePacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play.ServerDisconnectPacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play.ServerJoinGamePacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play.ServerKeepAlivePacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play.ServerPlayerInfoPacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play.ServerPlayerPositionAndLookPacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play.ServerPluginMessagePacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play.ServerTimeUpdatePacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play.ServerUpdateExperiencePacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play.ServerUpdateHealthPacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.serverbound.play.ClientKeepAlivePacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.serverbound.play.ClientPlayerPositionAndLookPacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.serverbound.play.ClientPluginMessagePacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.serverbound.play.ClientStatusPacket;
import io.github.defective4.minelite.v1_12_2.protocol.packets.serverbound.play.ClientStatusPacket.Action;
import io.github.defective4.minelite.v1_12_2.protocol.packets.serverbound.play.ClientTeleportConfirmPacket;

/**
 * An implementation of {@link PacketHandler} for version 1.18.2
 * 
 * @author Defective4
 *
 */
public class PacketHandler_1_12_2 implements PacketHandler {

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
                    ls.messageReceived(p.getMessage(), p.getPosition(), null);
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
