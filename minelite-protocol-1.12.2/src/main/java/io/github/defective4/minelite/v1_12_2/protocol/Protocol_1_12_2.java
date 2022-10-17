package io.github.defective4.minelite.v1_12_2.protocol;

import java.io.IOException;

import io.github.defective4.minelite.core.MinecraftClient;
import io.github.defective4.minelite.core.protocol.abstr.PacketHandler;
import io.github.defective4.minelite.core.protocol.abstr.PacketRegistry;
import io.github.defective4.minelite.core.protocol.abstr.ProtocolProvider;
import io.github.defective4.minelite.v1_12_2.protocol.packets.serverbound.play.ClientChatMessagePacket;

/**
 * An implementation of {@link ProtocolProvider} for version 1.18.2
 * 
 * @author Defective4
 *
 */
public class Protocol_1_12_2 implements ProtocolProvider {

    @Override
    public int getPVN() {
        return 340;
    }

    @Override
    public PacketRegistry createPacketRegistry() {
        return new PacketRegistry_1_12_2();
    }

    @Override
    public PacketHandler createPacketHandler() {
        return new PacketHandler_1_12_2();
    }

    @Override
    public void sendChatMessage(final MinecraftClient client, final String message) throws IOException {
        client.sendPacket(new ClientChatMessagePacket(message));
    }

}
