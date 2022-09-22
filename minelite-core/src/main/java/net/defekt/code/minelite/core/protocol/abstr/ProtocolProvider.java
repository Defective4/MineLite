package net.defekt.code.minelite.core.protocol.abstr;

import java.io.IOException;

import net.defekt.code.minelite.core.MinecraftClient;

/**
 * Interface for providing objects that are part of protocol implementation.
 * 
 * @author Defective4
 *
 */
public interface ProtocolProvider {
    /**
     * Get Protocol Version Number associated with this protocol.
     * 
     */
    public abstract int getPVN();

    /**
     * Create new registry containing packet IDs.
     */
    public abstract PacketRegistry createPacketRegistry();

    /**
     * Create new packet handler to handle incoming packets.
     */
    public abstract PacketHandler createPacketHandler();

    /**
     * Send a chat message.
     * 
     * @throws IOException
     */
    public abstract void sendChatMessage(MinecraftClient client, String message) throws IOException;
}
