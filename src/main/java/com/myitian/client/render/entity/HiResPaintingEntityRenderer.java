/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.myitian.client.render.entity;

import com.myitian.client.texture.HiResPaintingManager;
import com.myitian.entity.decoration.hirespainting.HiResPaintingEntity;
import com.myitian.entity.decoration.hirespainting.HiResPaintingMotive;
import com.myitian.hirespaintings.HiResPaintingsClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

@Environment(value = EnvType.CLIENT)
public class HiResPaintingEntityRenderer extends EntityRenderer<HiResPaintingEntity> {

    public HiResPaintingEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(HiResPaintingEntity paintingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f - f));
        HiResPaintingMotive paintingMotive = paintingEntity.motive;
        matrixStack.scale(0.0625f, 0.0625f, 0.0625f);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(this.getTexture(paintingEntity)));
        HiResPaintingManager paintingManager = HiResPaintingsClient.getHiResPaintingManager();

        this.renderPainting(
                matrixStack,
                vertexConsumer,
                paintingEntity,
                paintingMotive.getWidth(),
                paintingMotive.getHeight(),
                paintingManager.getPaintingSprite(paintingMotive),
                paintingManager.getBackSprite()
        );
        matrixStack.pop();
        super.render(paintingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(HiResPaintingEntity paintingEntity) {
        return HiResPaintingsClient.getHiResPaintingManager().getBackSprite().getAtlas().getId();
    }

    private void renderPainting(MatrixStack matrices, VertexConsumer vertexConsumer, HiResPaintingEntity entity, int width, int height, Sprite paintingSprite, Sprite backSprite) {
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f positionMatrix = entry.getModel();
        Matrix3f normalMatrix = entry.getNormal();
        float w_2 = (float) (-width) / 2.0f;
        float h_2 = (float) (-height) / 2.0f;
        float backSpriteMinU = backSprite.getMinU();
        float backSpriteMaxU = backSprite.getMaxU();
        float backSpriteMinV = backSprite.getMinV();
        float backSpriteMaxV = backSprite.getMaxV();
        float backSpriteFrameV = backSprite.getFrameV(1.0);
        float backSpriteFrameU = backSprite.getFrameU(1.0);
        int w_16 = width / 16;
        int h_16 = height / 16;
        double w = 16.0 / (double) w_16;
        double h = 16.0 / (double) h_16;
        for (int i = 0; i < w_16; ++i) {
            for (int j = 0; j < h_16; ++j) {
                float a = w_2 + (float) ((i + 1) * 16);
                float b = w_2 + (float) (i * 16);
                float c = h_2 + (float) ((j + 1) * 16);
                float d = h_2 + (float) (j * 16);
                int x = MathHelper.floor(entity.getX());
                int y = MathHelper.floor(entity.getY() + (double) ((c + d) / 32.0f));
                int z = MathHelper.floor(entity.getZ());
                switch (entity.getHorizontalFacing()) {
                    case NORTH: {
                        x = MathHelper.floor(entity.getX() + (double) ((a + b) / 32.0f));
                        break;
                    }
                    case WEST: {
                        z = MathHelper.floor(entity.getZ() - (double) ((a + b) / 32.0f));
                        break;
                    }
                    case SOUTH: {
                        x = MathHelper.floor(entity.getX() - (double) ((a + b) / 32.0f));
                        break;
                    }
                    case EAST: {
                        z = MathHelper.floor(entity.getZ() + (double) ((a + b) / 32.0f));
                        break;
                    }
                }
                int light = WorldRenderer.getLightmapCoordinates(entity.world, new BlockPos(x, y, z));
                float paintingSpriteFrameU = paintingSprite.getFrameU(w * (double) (w_16 - i));
                float paintingSpriteFrameU1 = paintingSprite.getFrameU(w * (double) (w_16 - (i + 1)));
                float paintingSpriteFrameV = paintingSprite.getFrameV(h * (double) (h_16 - j));
                float paintingSpriteFrameV1 = paintingSprite.getFrameV(h * (double) (h_16 - (j + 1)));
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, d, paintingSpriteFrameU1, paintingSpriteFrameV, -0.5f, 0, 0, -1, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, d, paintingSpriteFrameU, paintingSpriteFrameV, -0.5f, 0, 0, -1, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, c, paintingSpriteFrameU, paintingSpriteFrameV1, -0.5f, 0, 0, -1, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, c, paintingSpriteFrameU1, paintingSpriteFrameV1, -0.5f, 0, 0, -1, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, c, backSpriteMaxU, backSpriteMinV, 0.5f, 0, 0, 1, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, c, backSpriteMinU, backSpriteMinV, 0.5f, 0, 0, 1, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, d, backSpriteMinU, backSpriteMaxV, 0.5f, 0, 0, 1, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, d, backSpriteMaxU, backSpriteMaxV, 0.5f, 0, 0, 1, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, c, backSpriteMinU, backSpriteMinV, -0.5f, 0, 1, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, c, backSpriteMaxU, backSpriteMinV, -0.5f, 0, 1, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, c, backSpriteMaxU, backSpriteFrameV, 0.5f, 0, 1, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, c, backSpriteMinU, backSpriteFrameV, 0.5f, 0, 1, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, d, backSpriteMinU, backSpriteMinV, 0.5f, 0, -1, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, d, backSpriteMaxU, backSpriteMinV, 0.5f, 0, -1, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, d, backSpriteMaxU, backSpriteFrameV, -0.5f, 0, -1, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, d, backSpriteMinU, backSpriteFrameV, -0.5f, 0, -1, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, c, backSpriteFrameU, backSpriteMinV, 0.5f, -1, 0, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, d, backSpriteFrameU, backSpriteMaxV, 0.5f, -1, 0, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, d, backSpriteMinU, backSpriteMaxV, -0.5f, -1, 0, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, a, c, backSpriteMinU, backSpriteMinV, -0.5f, -1, 0, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, c, backSpriteFrameU, backSpriteMinV, -0.5f, 1, 0, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, d, backSpriteFrameU, backSpriteMaxV, -0.5f, 1, 0, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, d, backSpriteMinU, backSpriteMaxV, 0.5f, 1, 0, 0, light);
                this.vertex(positionMatrix, normalMatrix, vertexConsumer, b, c, backSpriteMinU, backSpriteMinV, 0.5f, 1, 0, 0, light);
            }
        }
    }

    private void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, float x, float y, float u, float v, float z, int normalX, int normalY, int normalZ, int light) {
        vertexConsumer.vertex(positionMatrix, x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, normalX, normalY, normalZ).next();
    }
}

