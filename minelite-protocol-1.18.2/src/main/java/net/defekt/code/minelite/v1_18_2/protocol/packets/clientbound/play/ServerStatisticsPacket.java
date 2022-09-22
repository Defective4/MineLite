package net.defekt.code.minelite.v1_18_2.protocol.packets.clientbound.play;

import static net.defekt.code.minelite.core.data.DataTypes.readVarInt;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.defekt.code.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")public class ServerStatisticsPacket extends InPacket {

    private final List<Statistic> stats = new ArrayList<ServerStatisticsPacket.Statistic>();

    public ServerStatisticsPacket(byte[] data) {
        super(data);
        ByteBuffer buffer = getBuffer();
        int num = readVarInt(buffer);
        for (int x = 0; x < num; x++)
            stats.add(new Statistic(readVarInt(buffer), readVarInt(buffer), readVarInt(buffer)));

    }

    public static class Statistic {
        private final int category;
        private final int id;
        private final int value;

        public Statistic(int category, int id, int value) {
            super();
            this.category = category;
            this.id = id;
            this.value = value;
        }

        public int getCategory() {
            return category;
        }

        public int getId() {
            return id;
        }

        public int getValue() {
            return value;
        }

    }

    public List<Statistic> getStats() {
        return Collections.unmodifiableList(stats);
    }

}
