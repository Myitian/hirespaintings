/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.myitian.client.render.entity;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.myitian.client.texture.HiResPaintingManager;
import com.myitian.entity.decoration.hirespainting.HiResPaintingEntity;
import com.myitian.entity.decoration.hirespainting.HiResPaintingMotive;
import com.myitian.hirespaintings.HiResPaintingsClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.PaintingManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;

@Environment(value = EnvType.CLIENT)
public class HiResPaintingEntityRenderer extends EntityRenderer<HiResPaintingEntity> {

    public HiResPaintingEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(HiResPaintingEntity paintingEntity, double d, double e, double f, float g, float h) {
        GlStateManager.pushMatrix();
        GlStateManager.translated(d, e, f);
        GlStateManager.rotatef(180.0F - g, 0.0F, 1.0F, 0.0F);
        GlStateManager.enableRescaleNormal();
        this.bindEntityTexture(paintingEntity);
        HiResPaintingMotive paintingMotive = paintingEntity.motive;
        float i = 0.0625F;
        GlStateManager.scalef(0.0625F, 0.0625F, 0.0625F);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.setupSolidRenderingTextureCombine(this.getOutlineColor(paintingEntity));
        }

        HiResPaintingManager paintingManager = HiResPaintingsClient.getHiResPaintingManager();
        this.renderPainting(paintingEntity, paintingMotive.getWidth(), paintingMotive.getHeight(), paintingManager.getPaintingSprite(paintingMotive), paintingManager.getBackSprite());
        if (this.renderOutlines) {
            GlStateManager.tearDownSolidRenderingTextureCombine();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.render(paintingEntity, d, e, f, g, h);
    }

    @Override
    public Identifier getTexture(HiResPaintingEntity paintingEntity) {
        return HiResPaintingManager.HIRESPAINTING_ATLAS_TEX;
    }

    private void renderPainting(HiResPaintingEntity paintingEntity, int i, int j, Sprite sprite, Sprite sprite2) {
        float f = (float)(-i) / 2.0F;
        float g = (float)(-j) / 2.0F;
        float h = 0.5F;
        float k = sprite2.getMinU();
        float l = sprite2.getMaxU();
        float m = sprite2.getMinV();
        float n = sprite2.getMaxV();
        float o = sprite2.getMinU();
        float p = sprite2.getMaxU();
        float q = sprite2.getMinV();
        float r = sprite2.getV(1.0D);
        float s = sprite2.getMinU();
        float t = sprite2.getU(1.0D);
        float u = sprite2.getMinV();
        float v = sprite2.getMaxV();
        int w = i / 16;
        int x = j / 16;
        double d = 16.0D / (double)w;
        double e = 16.0D / (double)x;

        for(int y = 0; y < w; ++y) {
            for(int z = 0; z < x; ++z) {
                float aa = f + (float)((y + 1) * 16);
                float ab = f + (float)(y * 16);
                float ac = g + (float)((z + 1) * 16);
                float ad = g + (float)(z * 16);
                this.method_4076(paintingEntity, (aa + ab) / 2.0F, (ac + ad) / 2.0F);
                float ae = sprite.getU(d * (double)(w - y));
                float af = sprite.getU(d * (double)(w - (y + 1)));
                float ag = sprite.getV(e * (double)(x - z));
                float ah = sprite.getV(e * (double)(x - (z + 1)));
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
                bufferBuilder.begin(7, VertexFormats.POSITION_UV_NORMAL);
                bufferBuilder.vertex((double)aa, (double)ad, -0.5D).texture((double)af, (double)ag).normal(0.0F, 0.0F, -1.0F).next();
                bufferBuilder.vertex((double)ab, (double)ad, -0.5D).texture((double)ae, (double)ag).normal(0.0F, 0.0F, -1.0F).next();
                bufferBuilder.vertex((double)ab, (double)ac, -0.5D).texture((double)ae, (double)ah).normal(0.0F, 0.0F, -1.0F).next();
                bufferBuilder.vertex((double)aa, (double)ac, -0.5D).texture((double)af, (double)ah).normal(0.0F, 0.0F, -1.0F).next();
                bufferBuilder.vertex((double)aa, (double)ac, 0.5D).texture((double)k, (double)m).normal(0.0F, 0.0F, 1.0F).next();
                bufferBuilder.vertex((double)ab, (double)ac, 0.5D).texture((double)l, (double)m).normal(0.0F, 0.0F, 1.0F).next();
                bufferBuilder.vertex((double)ab, (double)ad, 0.5D).texture((double)l, (double)n).normal(0.0F, 0.0F, 1.0F).next();
                bufferBuilder.vertex((double)aa, (double)ad, 0.5D).texture((double)k, (double)n).normal(0.0F, 0.0F, 1.0F).next();
                bufferBuilder.vertex((double)aa, (double)ac, -0.5D).texture((double)o, (double)q).normal(0.0F, 1.0F, 0.0F).next();
                bufferBuilder.vertex((double)ab, (double)ac, -0.5D).texture((double)p, (double)q).normal(0.0F, 1.0F, 0.0F).next();
                bufferBuilder.vertex((double)ab, (double)ac, 0.5D).texture((double)p, (double)r).normal(0.0F, 1.0F, 0.0F).next();
                bufferBuilder.vertex((double)aa, (double)ac, 0.5D).texture((double)o, (double)r).normal(0.0F, 1.0F, 0.0F).next();
                bufferBuilder.vertex((double)aa, (double)ad, 0.5D).texture((double)o, (double)q).normal(0.0F, -1.0F, 0.0F).next();
                bufferBuilder.vertex((double)ab, (double)ad, 0.5D).texture((double)p, (double)q).normal(0.0F, -1.0F, 0.0F).next();
                bufferBuilder.vertex((double)ab, (double)ad, -0.5D).texture((double)p, (double)r).normal(0.0F, -1.0F, 0.0F).next();
                bufferBuilder.vertex((double)aa, (double)ad, -0.5D).texture((double)o, (double)r).normal(0.0F, -1.0F, 0.0F).next();
                bufferBuilder.vertex((double)aa, (double)ac, 0.5D).texture((double)t, (double)u).normal(-1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex((double)aa, (double)ad, 0.5D).texture((double)t, (double)v).normal(-1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex((double)aa, (double)ad, -0.5D).texture((double)s, (double)v).normal(-1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex((double)aa, (double)ac, -0.5D).texture((double)s, (double)u).normal(-1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex((double)ab, (double)ac, -0.5D).texture((double)t, (double)u).normal(1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex((double)ab, (double)ad, -0.5D).texture((double)t, (double)v).normal(1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex((double)ab, (double)ad, 0.5D).texture((double)s, (double)v).normal(1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex((double)ab, (double)ac, 0.5D).texture((double)s, (double)u).normal(1.0F, 0.0F, 0.0F).next();
                tessellator.draw();
            }
        }
    }

    private void method_4076(HiResPaintingEntity paintingEntity, float f, float g) {
        int i = MathHelper.floor(paintingEntity.x);
        int j = MathHelper.floor(paintingEntity.y + (double)(g / 16.0F));
        int k = MathHelper.floor(paintingEntity.z);
        Direction direction = paintingEntity.getHorizontalFacing();
        if (direction == Direction.NORTH) {
            i = MathHelper.floor(paintingEntity.x + (double)(f / 16.0F));
        }

        if (direction == Direction.WEST) {
            k = MathHelper.floor(paintingEntity.z - (double)(f / 16.0F));
        }

        if (direction == Direction.SOUTH) {
            i = MathHelper.floor(paintingEntity.x - (double)(f / 16.0F));
        }

        if (direction == Direction.EAST) {
            k = MathHelper.floor(paintingEntity.z + (double)(f / 16.0F));
        }

        int l = this.renderManager.world.getLightmapIndex(new BlockPos(i, j, k), 0);
        int m = l % 65536;
        int n = l / 65536;
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float)m, (float)n);
        GlStateManager.color3f(1.0F, 1.0F, 1.0F);
    }
}

