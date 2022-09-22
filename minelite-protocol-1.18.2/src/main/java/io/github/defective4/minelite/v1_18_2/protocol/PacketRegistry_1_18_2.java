package io.github.defective4.minelite.v1_18_2.protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.defective4.minelite.core.protocol.abstr.PacketRegistry;
import io.github.defective4.minelite.core.protocol.packets.InPacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.login.ServerLoginCompressionPacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.login.ServerLoginDisconnectPacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.login.ServerLoginSuccessPacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerActionBarPacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerBossBarPacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerChatMessagePacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerDisconnectPacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerJoinGamePacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerKeepAlivePacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerPlayerInfoPacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerPlayerPositionAndLookPacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerPluginMessagePacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerStatisticsPacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerTimeUpdatePacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerUpdateExperiencePacket;
import io.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play.ServerUpdateHealthPacket;

/**
 * An implementation of {@link PacketRegistry} for version 1.18.2
 * 
 * @author Defective4
 *
 */
public class PacketRegistry_1_18_2 extends PacketRegistry {

    @Override
    public Map<Integer, Class<? extends InPacket>> initLoginPackets() {
        return new ConcurrentHashMap<Integer, Class<? extends InPacket>>() {
            {
                put(0x00, ServerLoginDisconnectPacket.class);
                put(0x03, ServerLoginCompressionPacket.class);
                put(0x02, ServerLoginSuccessPacket.class);
            }
        };
    }

    @Override
    public Map<Integer, Class<? extends InPacket>> initPlayPackets() {
        return new ConcurrentHashMap<Integer, Class<? extends InPacket>>() {
            {
                put(0x1A, ServerDisconnectPacket.class);
                put(0x21, ServerKeepAlivePacket.class);
                put(0x0f, ServerChatMessagePacket.class);
                put(0x38, ServerPlayerPositionAndLookPacket.class);
                put(0x18, ServerPluginMessagePacket.class);
                put(0x26, ServerJoinGamePacket.class);
                put(0x59, ServerTimeUpdatePacket.class);
                put(0x52, ServerUpdateHealthPacket.class);
                put(0x51, ServerUpdateExperiencePacket.class);
                put(0x41, ServerActionBarPacket.class);
                put(0x36, ServerPlayerInfoPacket.class);
                put(0x07, ServerStatisticsPacket.class);
                put(0x0D, ServerBossBarPacket.class);
            }
        };
    }

}
