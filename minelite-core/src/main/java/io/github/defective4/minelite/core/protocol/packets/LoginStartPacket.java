package io.github.defective4.minelite.core.protocol.packets;

@SuppressWarnings("javadoc")
public class LoginStartPacket extends OutPacket {

    public LoginStartPacket(final String username) {
        super(0x00);
        putString(username);
    }

}
