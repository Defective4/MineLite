package net.defekt.code.minelite.v1_18_2.protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.defekt.code.minelite.core.protocol.abstr.PacketRegistry;
import net.defekt.code.minelite.core.protocol.packets.InPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.login.ServerLoginCompressionPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.login.ServerLoginDisconnectPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.login.ServerLoginSuccessPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerActionBarPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerBossBarPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerChatMessagePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerDisconnectPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerJoinGamePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerKeepAlivePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerPlayerInfoPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerPlayerPositionAndLookPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerPluginMessagePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerStatisticsPacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerTimeUpdatePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerUpdateExperiencePacket;
import net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play.ServerUpdateHealthPacket;

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
