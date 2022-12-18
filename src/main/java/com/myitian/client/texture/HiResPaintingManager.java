/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.myitian.client.texture;

import com.google.common.collect.Iterables;
import com.myitian.entity.decoration.hirespainting.HiResPaintingMotive;
import com.myitian.hirespaintings.HiResPaintingsMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.*;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Environment(value = EnvType.CLIENT)
public class HiResPaintingManager extends SpriteAtlasHolder {
    public static final Identifier HIRESPAINTING_ATLAS_TEX = new Identifier(HiResPaintingsMain.MODID, "textures/atlas/hirespaintings.png");
    private static final Identifier PAINTING_BACK_ID = new Identifier(HiResPaintingsMain.MODID, "back");
    private final Map<Identifier, Boolean> missingMotives = new HashMap<>();

    public HiResPaintingManager(TextureManager manager) {
        super(manager, HIRESPAINTING_ATLAS_TEX, "textures/hirespainting");
        MinecraftClient mc = MinecraftClient.getInstance();
        SpriteAtlasTexture.Data data = prepare(mc.getResourceManager(), mc.getProfiler());
        apply(data, mc.getResourceManager(), mc.getProfiler());
    }

    @Override
    protected Iterable<Identifier> getSprites() {
        return Iterables.concat(HiResPaintingsMain.HIRESPAINTING_MOTIVE.getIds(), Collections.singleton(PAINTING_BACK_ID));
    }

    public Sprite getPaintingSprite(HiResPaintingMotive motive) {
        Identifier motiveId = HiResPaintingsMain.HIRESPAINTING_MOTIVE.getId(motive);
        Sprite sprite = this.getSprite(motiveId);
        if (sprite.getId() == MissingSprite.getMissingSpriteId() && !missingMotives.getOrDefault(motiveId, false)) {
            HiResPaintingsMain.LOGGER.warn("MissingSprite: " + motiveId);
            missingMotives.put(motiveId, true);
        }
        return sprite;
    }

    public Sprite getBackSprite() {
        Sprite sprite = this.getSprite(PAINTING_BACK_ID);
        if (sprite.getId() == MissingSprite.getMissingSpriteId() && !missingMotives.getOrDefault(PAINTING_BACK_ID, false)) {
            HiResPaintingsMain.LOGGER.warn("MissingSprite: " + PAINTING_BACK_ID);
            missingMotives.put(PAINTING_BACK_ID, true);
        }
        return sprite;
    }
}

