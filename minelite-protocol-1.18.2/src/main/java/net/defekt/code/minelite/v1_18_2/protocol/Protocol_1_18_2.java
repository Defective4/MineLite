package net.defekt.code.minelite.v1_18_2.protocol;

import java.io.IOException;

import net.defekt.code.minelite.core.MinecraftClient;
import net.defekt.code.minelite.core.protocol.abstr.PacketHandler;
import net.defekt.code.minelite.core.protocol.abstr.PacketRegistry;
import net.defekt.code.minelite.core.protocol.abstr.ProtocolProvider;
import net.defekt.code.minelite.v1_18_2.protocol.packets.serverbound.play.ClientChatMessagePacket;

/**
 * An implementation of {@link ProtocolProvider} for version 1.18.2
 * 
 * @author Defective4
 *
 */
public class Protocol_1_18_2 implements ProtocolProvider {

    @Override
    public int getPVN() {
        return 758;
    }

    @Override
    public PacketRegistry createPacketRegistry() {
        return new PacketRegistry_1_18_2();
    }

    @Override
    public PacketHandler createPacketHandler() {
        return new PacketHandler_1_18_2();
    }

    @Override
    public void sendChatMessage(final MinecraftClient client, final String message) throws IOException {
        client.sendPacket(new ClientChatMessagePacket(message));
    }

}
