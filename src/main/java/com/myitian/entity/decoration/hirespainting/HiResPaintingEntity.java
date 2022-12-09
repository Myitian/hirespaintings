package com.myitian.entity.decoration.hirespainting;

import com.google.common.collect.Lists;
import com.myitian.Util;
import com.myitian.hirespaintings.HiResPaintingsMain;
import com.myitian.network.packet.s2c.play.HiResPaintingSpawnS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;

public class HiResPaintingEntity extends AbstractDecorationEntity {
    public HiResPaintingMotive motive = HiResPaintingMotive.KEBAB;

    public HiResPaintingEntity(EntityType<? extends HiResPaintingEntity> entityType, World world) {
        super(entityType, world);
    }

    public HiResPaintingEntity(World world, BlockPos pos, Direction direction) {
        super(HiResPaintingsMain.HIRESPAINTING_ENTITY, world, pos);
        HiResPaintingMotive paintingMotive;
        ArrayList<HiResPaintingMotive> list = Lists.newArrayList();
        int i = 0;

        for (HiResPaintingMotive value : HiResPaintingsMain.HIRESPAINTING_MOTIVE) {
            this.motive = paintingMotive = value;
            this.setFacing(direction);
            if (!this.canStayAttached()) continue;
            list.add(paintingMotive);
            int j = paintingMotive.getWidth() * paintingMotive.getHeight();
            if (j <= i) continue;
            i = j;
        }
        if (!list.isEmpty()) {
            Iterator<HiResPaintingMotive> iterator2 = list.iterator();
            while (iterator2.hasNext()) {
                paintingMotive = iterator2.next();
                if (paintingMotive.getWidth() * paintingMotive.getHeight() >= i) continue;
                iterator2.remove();
            }
            this.motive = list.get(this.random.nextInt(list.size()));
        }
        this.setFacing(direction);
    }

    public HiResPaintingEntity(World world, BlockPos pos, Direction direction, HiResPaintingMotive motive) {
        this(world, pos, direction);
        this.motive = motive;
        this.setFacing(direction);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString("Motive", HiResPaintingsMain.HIRESPAINTING_MOTIVE.getId(this.motive).toString());
        nbt.putByte("Facing", (byte) this.facing.getHorizontal());
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        String motiveStr = nbt.getString("Motive");
        if (!Util.isNullOrBlank(motiveStr)) {
            this.motive = HiResPaintingsMain.HIRESPAINTING_MOTIVE.get(Identifier.tryParse(motiveStr));
        }
        this.facing = Direction.fromHorizontal(nbt.getByte("Facing"));
        super.readCustomDataFromNbt(nbt);
        this.setFacing(this.facing);
    }

    @Override
    public int getWidthPixels() {
        return this.motive.getWidth();
    }

    @Override
    public int getHeightPixels() {
        return this.motive.getHeight();
    }

    @Override
    public void onBreak(@Nullable Entity entity) {
        if (!this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            return;
        }
        this.playSound(SoundEvents.ENTITY_PAINTING_BREAK, 1.0f, 1.0f);
        if (entity instanceof PlayerEntity playerEntity) {
            if (playerEntity.getAbilities().creativeMode) {
                return;
            }
        }
        this.dropItem(HiResPaintingsMain.HIRESPAINTING_ITEM);
    }

    @Override
    public void onPlace() {
        this.playSound(SoundEvents.ENTITY_PAINTING_PLACE, 1.0f, 1.0f);
    }

    @Override
    public void refreshPositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        this.setPosition(x, y, z);
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        BlockPos blockPos = this.attachmentPos.add(x - this.getX(), y - this.getY(), z - this.getZ());
        this.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new HiResPaintingSpawnS2CPacket(this);
    }

    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(HiResPaintingsMain.HIRESPAINTING_ITEM);
    }
}

