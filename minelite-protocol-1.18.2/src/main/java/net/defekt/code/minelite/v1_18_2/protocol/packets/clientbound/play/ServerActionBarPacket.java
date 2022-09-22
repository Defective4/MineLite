package net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play;

import net.defekt.code.minelite.core.data.DataTypes;
import net.defekt.code.minelite.core.data.Message;
import net.defekt.code.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerActionBarPacket extends InPacket {

    private final String json;

    public ServerActionBarPacket(final byte[] data) {
        super(data);
        json = DataTypes.readString(getBuffer());
    }

    public Message getMessage() {
        return new Message(json);
    }

}
