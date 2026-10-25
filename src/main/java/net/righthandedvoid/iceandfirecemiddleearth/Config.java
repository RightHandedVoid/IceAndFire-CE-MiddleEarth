package net.righthandedvoid.iceandfirecemiddleearth;

import java.util.Map;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    //                        STAGE, MAX AGE
    public static final Map<Integer, Integer> DRAGON_STAGES_LOOKUP_TABLE = Map.ofEntries(
            Map.entry(25, 2500),
            Map.entry(24, 2200),
            Map.entry(23, 2000),
            Map.entry(22, 1800),
            Map.entry(21, 1600),
            Map.entry(20, 1400),
            Map.entry(19, 1300),
            Map.entry(18, 1200),
            Map.entry(17, 1100),
            Map.entry(16, 1000),
            Map.entry(15, 900),
            Map.entry(14, 800),
            Map.entry(13, 700),
            Map.entry(12, 600),
            Map.entry(11, 500),
            Map.entry(10, 400),
            Map.entry(9, 350),
            Map.entry(8, 300),
            Map.entry(7, 250),
            Map.entry(6, 200),
            Map.entry(5, 150),
            Map.entry(4, 100),
            Map.entry(3, 75),
            Map.entry(2, 50),
            Map.entry(1, 25)
    );

    public static final int DRAGON_MIN_TIER = 1;
    public static final int DRAGON_MAX_TIER = 25;
    public static final int BASE_DRAGON_TIERS = 5;

    public static final int DRAGON_AGE_CAP = 2650;
    public static final double DRAGON_HP_PER_TIER = 0.55;
    public static final double DRAGON_DMG_PER_TIER = 0.45;
    public static final double DRAGON_DEF_PER_TIER = 0.15;

}
