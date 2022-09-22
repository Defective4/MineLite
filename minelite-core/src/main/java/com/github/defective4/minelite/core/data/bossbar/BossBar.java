package com.github.defective4.minelite.core.data.bossbar;

import com.github.defective4.minelite.core.data.Message;

/**
 * Stores information about a boss bar.
 * 
 * @author Defective4
 *
 */
@SuppressWarnings("javadoc")
public class BossBar {
    private final Message title;
    private final float health;
    private final BossBarColor color;
    private final BossBarDivision division;
    private final byte flags;

    public BossBar(Message title, float health, BossBarColor color, BossBarDivision division, byte flags) {
        super();
        this.title = title;
        this.health = health;
        this.color = color;
        this.division = division;
        this.flags = flags;
    }

    public Message getTitle() {
        return title;
    }

    public float getHealth() {
        return health;
    }

    public BossBarColor getColor() {
        return color;
    }

    public BossBarDivision getDivision() {
        return division;
    }

    public byte getFlags() {
        return flags;
    }

}
