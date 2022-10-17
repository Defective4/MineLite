package io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.login;

import io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play.ServerDisconnectPacket;

@SuppressWarnings("javadoc")public class ServerLoginDisconnectPacket extends ServerDisconnectPacket {

    public ServerLoginDisconnectPacket(final byte[] data) {
        super(data);

    }

}
