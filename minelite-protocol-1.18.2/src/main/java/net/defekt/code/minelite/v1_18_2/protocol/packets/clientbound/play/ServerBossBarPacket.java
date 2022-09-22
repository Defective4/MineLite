package net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play;

import static net.defekt.code.minelite.core.data.DataTypes.*;

import java.nio.ByteBuffer;
import java.util.UUID;

import net.defekt.code.minelite.core.data.Message;
import net.defekt.code.minelite.core.data.bossbar.BossBar;
import net.defekt.code.minelite.core.data.bossbar.BossBarAction;
import net.defekt.code.minelite.core.data.bossbar.BossBarColor;
import net.defekt.code.minelite.core.data.bossbar.BossBarDivision;
import net.defekt.code.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerBossBarPacket extends InPacket {

    private final UUID uid;
    private final BossBarAction action;
    private final BossBar bossBar;

    public ServerBossBarPacket(byte[] data) {
        super(data);
        ByteBuffer buffer = getBuffer();
        uid = readUUID(buffer);
        action = BossBarAction.getByID(readVarInt(buffer));
        switch (action) {
            default:
            case REMOVE: {
                bossBar = null;
                break;
            }
            case ADD: {
                bossBar = new BossBar(new Message(readString(buffer)), buffer.getFloat(),
                        BossBarColor.getByID(readVarInt(buffer)), BossBarDivision.getByID(readVarInt(buffer)),
                        buffer.get());
                break;
            }
            case UPDATE_FLAGS: {
                bossBar = new BossBar(null, 0, null, null, buffer.get());
                break;
            }
            case UPDATE_HP: {
                bossBar = new BossBar(null, buffer.getFloat(), null, null, (byte) 0);
                break;
            }
            case UPDATE_STYLE: {
                bossBar = new BossBar(null, 0, null, BossBarDivision.getByID(readVarInt(buffer)), (byte) 0);
                break;
            }
            case UPDATE_TITLE: {
                bossBar = new BossBar(new Message(readString(buffer)), 0, null, null, (byte) 0);
                break;
            }
        }
    }

    public UUID getUid() {
        return uid;
    }

    public BossBarAction getAction() {
        return action;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

}
