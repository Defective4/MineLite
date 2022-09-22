package com.github.defective4.minelite.core;

/**
 * A behavior option that hints the client's protocol implementation what should
 * and what shouldn't be done.<br>
 * It has two states - <code>true</code> and <code>false</code>.
 * 
 * @author Defective4
 *
 */
@SuppressWarnings("javadoc")
public enum Option {
    KEEP_ALIVE(true), AUTO_REGISTER_PLUGIN_CHANNELS(true), AUTO_UPDATE_SERVER_POSITION(true),
    SEND_CLIENT_BRAND_ON_JOIN(true), AUTO_RESPAWN(true);

    private final boolean def;

    private Option(final boolean def) {
        this.def = def;
    }

    public boolean isDefaultOn() {
        return def;
    }

}
