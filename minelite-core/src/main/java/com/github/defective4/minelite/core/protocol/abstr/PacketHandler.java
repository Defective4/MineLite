package com.github.defective4.minelite.core.protocol.abstr;

import java.io.IOException;

import com.github.defective4.minelite.core.MinecraftClient;
import com.github.defective4.minelite.core.protocol.packets.InPacket;

/**
 * Interface to handle incoming Clientbound Packets received from server.
 * 
 * @author Defective4
 *
 */
@SuppressWarnings("javadoc")
public interface PacketHandler {
    public void handle(InPacket packet, MinecraftClient client) throws IOException;
}
