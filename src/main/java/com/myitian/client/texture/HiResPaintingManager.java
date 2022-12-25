/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.myitian.client.texture;

import com.myitian.entity.decoration.hirespainting.HiResPaintingMotive;
import com.myitian.hirespaintings.HiResPaintingsMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasHolder;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.DummyProfiler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Environment(value = EnvType.CLIENT)
public class HiResPaintingManager extends SpriteAtlasHolder {
    private static final Identifier PAINTING_BACK_ID = new Identifier(HiResPaintingsMain.MODID, "back");

    public HiResPaintingManager(TextureManager manager) {
        super(manager, new Identifier(HiResPaintingsMain.MODID, "textures/atlas/hirespaintings.png"), "hirespainting");
        MinecraftClient mc = MinecraftClient.getInstance();
        CompletableFuture<Unit> completableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
        CompletableFuture<Unit> prepareStageFuture = new CompletableFuture<>();
        reload(new Synchronizer() {
                   public <T> CompletableFuture<T> whenPrepared(T preparedObject) {
                       mc.execute(() -> prepareStageFuture.complete(Unit.INSTANCE));
                       return prepareStageFuture.thenCombine(completableFuture, (unit, object) -> preparedObject);
                   }
               },
                mc.getResourceManager(),
                DummyProfiler.INSTANCE,
                DummyProfiler.INSTANCE,
                Util.getMainWorkerExecutor(),
                mc);
    }

    @Override
    protected Stream<Identifier> getSprites() {
        return Stream.concat(HiResPaintingsMain.HIRESPAINTING_MOTIVE.getIds().stream(), Stream.of(PAINTING_BACK_ID));
    }

    public Sprite getPaintingSprite(HiResPaintingMotive motive) {
        Identifier motiveId = HiResPaintingsMain.HIRESPAINTING_MOTIVE.getId(motive);
        return getSprite(motiveId);
    }

    public Sprite getBackSprite() {
        return getSprite(PAINTING_BACK_ID);
    }
}

