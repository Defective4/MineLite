package com.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play;

import com.github.defective4.minelite.core.data.DataTypes;
import com.github.defective4.minelite.core.data.Message;
import com.github.defective4.minelite.core.protocol.packets.InPacket;

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
