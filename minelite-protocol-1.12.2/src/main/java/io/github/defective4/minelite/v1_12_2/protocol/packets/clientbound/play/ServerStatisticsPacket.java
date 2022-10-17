package io.github.defective4.minelite.v1_12_2.protocol.packets.clientbound.play;

import static io.github.defective4.minelite.core.data.DataTypes.readVarInt;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.defective4.minelite.core.data.DataTypes;
import io.github.defective4.minelite.core.protocol.packets.InPacket;

@SuppressWarnings("javadoc")
public class ServerStatisticsPacket extends InPacket {

    private final List<Statistic> stats = new ArrayList<ServerStatisticsPacket.Statistic>();

    public ServerStatisticsPacket(byte[] data) {
        super(data);
        ByteBuffer buffer = getBuffer();
        int num = readVarInt(buffer);
        for (int x = 0; x < num; x++)
            stats.add(new Statistic(DataTypes.readString(buffer), readVarInt(buffer)));

    }

    public static class Statistic {
        private final String name;
        private final int value;

        public Statistic(String name, int value) {
            super();
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

    }

    public List<Statistic> getStats() {
        return Collections.unmodifiableList(stats);
    }

}
