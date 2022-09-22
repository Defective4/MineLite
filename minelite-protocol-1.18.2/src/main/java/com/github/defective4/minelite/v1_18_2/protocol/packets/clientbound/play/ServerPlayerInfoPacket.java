package com.github.defective4.minelite.v1_18_2.protocol.packets.clientbound.play;

import static com.github.defective4.minelite.core.data.DataTypes.readString;
import static com.github.defective4.minelite.core.data.DataTypes.readVarInt;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.github.defective4.minelite.core.data.DataTypes;
import com.github.defective4.minelite.core.data.PlayerListAction;
import com.github.defective4.minelite.core.data.PlayerListInfo;
import com.github.defective4.minelite.core.data.PlayerListInfo.Gamemode;
import com.github.defective4.minelite.core.data.PlayerListInfo.PlayerProperty;
import com.github.defective4.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerPlayerInfoPacket extends InPacket {

    private final PlayerListAction action;
    private final List<PlayerListInfo> players = new ArrayList<PlayerListInfo>();

    public ServerPlayerInfoPacket(final byte[] data) {
        super(data);
        final ByteBuffer buffer = getBuffer();
        action = PlayerListAction.getForID(readVarInt(buffer));
        final int num = readVarInt(buffer);
        for (int x = 0; x < num; x++) {
            PlayerListInfo info;
            final UUID uid = DataTypes.readUUID(buffer);
            switch (action) {
                default:
                case REMOVE_PLAYER: {
                    info = new PlayerListInfo(uid, null, null, null, 0, null);
                    break;
                }
                case ADD_PLAYER: {
                    info = new PlayerListInfo(uid, readString(buffer), readProps(buffer),
                            Gamemode.getForID(readVarInt(buffer)), readVarInt(buffer),
                            buffer.get() == 1 ? readString(buffer) : null);
                    break;
                }
                case UPDATE_DISPLAY_NAME: {
                    info = new PlayerListInfo(uid, null, null, null, x, buffer.get() == 1 ? readString(buffer) : null);
                    break;
                }
                case UPDATE_GAMEMODE: {
                    info = new PlayerListInfo(uid, null, null, Gamemode.getForID(readVarInt(buffer)), x, null);
                    break;
                }
                case UPDATE_LATENCY: {
                    info = new PlayerListInfo(uid, null, null, null, readVarInt(buffer), null);
                    break;
                }
            }
            players.add(info);
        }
    }

    private static List<PlayerProperty> readProps(final ByteBuffer buffer) {
        final int num = readVarInt(buffer);
        final List<PlayerProperty> props = new ArrayList<PlayerListInfo.PlayerProperty>();
        for (int x = 0; x < num; x++) {
            props.add(new PlayerProperty(readString(buffer), readString(buffer),
                    buffer.get() == 1 ? readString(buffer) : null));
        }
        return props;
    }

    public PlayerListAction getAction() {
        return action;
    }

    public List<PlayerListInfo> getPlayers() {
        return Collections.unmodifiableList(players);
    }
}
