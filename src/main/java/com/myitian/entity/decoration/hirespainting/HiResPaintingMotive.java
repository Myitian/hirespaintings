package com.myitian.entity.decoration.hirespainting;

import com.myitian.hirespaintings.HiResPaintingsMain;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HiResPaintingMotive {
    public static final HiResPaintingMotive KEBAB = HiResPaintingMotive.register("kebab", 16, 16);
    public static final HiResPaintingMotive AZTEC = HiResPaintingMotive.register("aztec", 16, 16);
    public static final HiResPaintingMotive ALBAN = HiResPaintingMotive.register("alban", 16, 16);
    public static final HiResPaintingMotive AZTEC2 = HiResPaintingMotive.register("aztec2", 16, 16);
    public static final HiResPaintingMotive BOMB = HiResPaintingMotive.register("bomb", 16, 16);
    public static final HiResPaintingMotive PLANT = HiResPaintingMotive.register("plant", 16, 16);
    public static final HiResPaintingMotive WASTELAND = HiResPaintingMotive.register("wasteland", 16, 16);
    public static final HiResPaintingMotive POOL = HiResPaintingMotive.register("pool", 32, 16);
    public static final HiResPaintingMotive COURBET = HiResPaintingMotive.register("courbet", 32, 16);
    public static final HiResPaintingMotive SEA = HiResPaintingMotive.register("sea", 32, 16);
    public static final HiResPaintingMotive SUNSET = HiResPaintingMotive.register("sunset", 32, 16);
    public static final HiResPaintingMotive CREEBET = HiResPaintingMotive.register("creebet", 32, 16);
    public static final HiResPaintingMotive WANDERER = HiResPaintingMotive.register("wanderer", 16, 32);
    public static final HiResPaintingMotive GRAHAM = HiResPaintingMotive.register("graham", 16, 32);
    public static final HiResPaintingMotive MATCH = HiResPaintingMotive.register("match", 32, 32);
    public static final HiResPaintingMotive BUST = HiResPaintingMotive.register("bust", 32, 32);
    public static final HiResPaintingMotive STAGE = HiResPaintingMotive.register("stage", 32, 32);
    public static final HiResPaintingMotive VOID = HiResPaintingMotive.register("void", 32, 32);
    public static final HiResPaintingMotive SKULL_AND_ROSES = HiResPaintingMotive.register("skull_and_roses", 32, 32);
    //public static final HiResPaintingMotive WITHER = HiResPaintingMotive.register("wither", 32, 32); ** no source image **
    public static final HiResPaintingMotive FIGHTERS = HiResPaintingMotive.register("fighters", 64, 32);
    public static final HiResPaintingMotive POINTER = HiResPaintingMotive.register("pointer", 64, 64);
    public static final HiResPaintingMotive PIGSCENE = HiResPaintingMotive.register("pigscene", 64, 64);
    public static final HiResPaintingMotive BURNING_SKULL = HiResPaintingMotive.register("burning_skull", 64, 64);
    public static final HiResPaintingMotive SKELETON = HiResPaintingMotive.register("skeleton", 64, 48);
    public static final HiResPaintingMotive DONKEY_KONG = HiResPaintingMotive.register("donkey_kong", 64, 48);
    private final int width;
    private final int height;

    public HiResPaintingMotive(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private static HiResPaintingMotive register(String name, int width, int height) {
        return Registry.register(HiResPaintingsMain.HIRESPAINTING_MOTIVE, new Identifier(HiResPaintingsMain.MODID, name), new HiResPaintingMotive(width, height));
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public String toString() {
        return HiResPaintingsMain.HIRESPAINTING_MOTIVE.getId(this) + "@" + width + "x" + height;
    }
}
