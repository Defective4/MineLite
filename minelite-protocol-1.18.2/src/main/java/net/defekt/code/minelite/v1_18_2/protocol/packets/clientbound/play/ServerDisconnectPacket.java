package net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play;

import net.defekt.code.minelite.core.data.DataTypes;
import net.defekt.code.minelite.core.data.Message;
import net.defekt.code.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerDisconnectPacket extends InPacket {

    private final String json;

    public ServerDisconnectPacket(final byte[] data) {
        super(data);
        json = DataTypes.readString(getBuffer());
    }

    public Message getDisconnectMessage() {
        return new Message(json);
    }

    public String getJson() {
        return json;
    }

}
