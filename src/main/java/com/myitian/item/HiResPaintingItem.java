package com.myitian.item;

import com.myitian.entity.decoration.hirespainting.HiResPaintingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class HiResPaintingItem extends Item {
    public HiResPaintingItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        Direction direction = context.getSide();
        BlockPos blockPos2 = blockPos.offset(direction);
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();
        if (playerEntity != null && !this.canPlaceOn(playerEntity, direction, itemStack, blockPos2)) {
            return ActionResult.FAIL;
        }
        World world = context.getWorld();
        HiResPaintingEntity painting = new HiResPaintingEntity(world, blockPos2, direction);
        CompoundTag nbtCompound = itemStack.getTag();
        if (nbtCompound != null) {
            EntityType.loadFromEntityTag(world, playerEntity, painting, nbtCompound);
        }
        if (painting.method_6888()) {
            if (!world.isClient) {
                painting.onPlace();
                world.spawnEntity(painting);
            }
            itemStack.decrement(1);
        }
        return ActionResult.SUCCESS;
    }

    protected boolean canPlaceOn(PlayerEntity player, Direction side, ItemStack stack, BlockPos pos) {
        return !side.getAxis().isVertical() && player.canPlaceOn(pos, side, stack);
    }
}
