package com.myitian.client.render.entity;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.myitian.client.texture.HiResPaintingManager;
import com.myitian.entity.decoration.hirespainting.HiResPaintingEntity;
import com.myitian.entity.decoration.hirespainting.HiResPaintingMotive;
import com.myitian.hirespaintings.HiResPaintingsClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

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

    private void renderPainting(HiResPaintingEntity entity, int width, int height, Sprite paintingSprite, Sprite backSprite) {
        float w_2 = (float) (-width) / 2.0F;
        float h_2 = (float) (-height) / 2.0F;
        float backSpriteMinU = backSprite.getMinU();
        float backSpriteMaxU = backSprite.getMaxU();
        float backSpriteMinV = backSprite.getMinV();
        float backSpriteMaxV = backSprite.getMaxV();
        float backSpriteFrameV = backSprite.getFrameV(1.0D);
        float backSpriteFrameU = backSprite.getFrameU(1.0D);
        int w_16 = width / 16;
        int h_16 = height / 16;
        double w = 16.0D / (double) w_16;
        double h = 16.0D / (double) h_16;

        for (int i = 0; i < w_16; ++i) {
            for (int j = 0; j < h_16; ++j) {
                float a = w_2 + (float) ((i + 1) * 16);
                float b = w_2 + (float) (i * 16);
                float c = h_2 + (float) ((j + 1) * 16);
                float d = h_2 + (float) (j * 16);
                int x = MathHelper.floor(entity.x);
                int y = MathHelper.floor(entity.y + (double) ((c + d) / 16.0F));
                int z = MathHelper.floor(entity.z);
                switch (entity.getHorizontalFacing()) {
                    case NORTH: {
                        x = MathHelper.floor(entity.x + (double) ((a + b) / 32.0f));
                    }
                    case WEST: {
                        z = MathHelper.floor(entity.z - (double) ((a + b) / 32.0f));
                    }
                    case SOUTH: {
                        x = MathHelper.floor(entity.x - (double) ((a + b) / 32.0f));
                    }
                    case EAST: {
                        z = MathHelper.floor(entity.z + (double) ((a + b) / 32.0f));
                    }
                }
                int l = this.renderManager.world.getLightmapIndex(new BlockPos(x, y, z), 0);
                int m = l % 65536;
                int n = l / 65536;
                GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float) m, (float) n);
                GlStateManager.color3f(1.0F, 1.0F, 1.0F);
                float paintingSpriteFrameU = paintingSprite.getFrameU(w * (double) (w_16 - i));
                float paintingSpriteFrameU1 = paintingSprite.getFrameU(w * (double) (w_16 - (i + 1)));
                float paintingSpriteFrameV = paintingSprite.getFrameV(h * (double) (h_16 - j));
                float paintingSpriteFrameV1 = paintingSprite.getFrameV(h * (double) (h_16 - (j + 1)));
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferBuilder = tessellator.getBuffer();
                bufferBuilder.begin(7, VertexFormats.POSITION_UV_NORMAL);
                bufferBuilder.vertex(a, d, -0.5D).texture(paintingSpriteFrameU1, paintingSpriteFrameV).normal(0.0F, 0.0F, -1.0F).next();
                bufferBuilder.vertex(b, d, -0.5D).texture(paintingSpriteFrameU, paintingSpriteFrameV).normal(0.0F, 0.0F, -1.0F).next();
                bufferBuilder.vertex(b, c, -0.5D).texture(paintingSpriteFrameU, paintingSpriteFrameV1).normal(0.0F, 0.0F, -1.0F).next();
                bufferBuilder.vertex(a, c, -0.5D).texture(paintingSpriteFrameU1, paintingSpriteFrameV1).normal(0.0F, 0.0F, -1.0F).next();
                bufferBuilder.vertex(a, c, 0.5D).texture(backSpriteMinU, backSpriteMinV).normal(0.0F, 0.0F, 1.0F).next();
                bufferBuilder.vertex(b, c, 0.5D).texture(backSpriteMaxU, backSpriteMinV).normal(0.0F, 0.0F, 1.0F).next();
                bufferBuilder.vertex(b, d, 0.5D).texture(backSpriteMaxU, backSpriteMaxV).normal(0.0F, 0.0F, 1.0F).next();
                bufferBuilder.vertex(a, d, 0.5D).texture(backSpriteMinU, backSpriteMaxV).normal(0.0F, 0.0F, 1.0F).next();
                bufferBuilder.vertex(a, c, -0.5D).texture(backSpriteMinU, backSpriteMinV).normal(0.0F, 1.0F, 0.0F).next();
                bufferBuilder.vertex(b, c, -0.5D).texture(backSpriteMaxU, backSpriteMinV).normal(0.0F, 1.0F, 0.0F).next();
                bufferBuilder.vertex(b, c, 0.5D).texture(backSpriteMaxU, backSpriteFrameV).normal(0.0F, 1.0F, 0.0F).next();
                bufferBuilder.vertex(a, c, 0.5D).texture(backSpriteMinU, backSpriteFrameV).normal(0.0F, 1.0F, 0.0F).next();
                bufferBuilder.vertex(a, d, 0.5D).texture(backSpriteMinU, backSpriteMinV).normal(0.0F, -1.0F, 0.0F).next();
                bufferBuilder.vertex(b, d, 0.5D).texture(backSpriteMaxU, backSpriteMinV).normal(0.0F, -1.0F, 0.0F).next();
                bufferBuilder.vertex(b, d, -0.5D).texture(backSpriteMaxU, backSpriteFrameV).normal(0.0F, -1.0F, 0.0F).next();
                bufferBuilder.vertex(a, d, -0.5D).texture(backSpriteMinU, backSpriteFrameV).normal(0.0F, -1.0F, 0.0F).next();
                bufferBuilder.vertex(a, c, 0.5D).texture(backSpriteFrameU, backSpriteMinV).normal(-1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex(a, d, 0.5D).texture(backSpriteFrameU, backSpriteMaxV).normal(-1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex(a, d, -0.5D).texture(backSpriteMinU, backSpriteMaxV).normal(-1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex(a, c, -0.5D).texture(backSpriteMinU, backSpriteMinV).normal(-1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex(b, c, -0.5D).texture(backSpriteFrameU, backSpriteMinV).normal(1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex(b, d, -0.5D).texture(backSpriteFrameU, backSpriteMaxV).normal(1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex(b, d, 0.5D).texture(backSpriteMinU, backSpriteMaxV).normal(1.0F, 0.0F, 0.0F).next();
                bufferBuilder.vertex(b, c, 0.5D).texture(backSpriteMinU, backSpriteMinV).normal(1.0F, 0.0F, 0.0F).next();
                tessellator.draw();
            }
        }
    }
}

