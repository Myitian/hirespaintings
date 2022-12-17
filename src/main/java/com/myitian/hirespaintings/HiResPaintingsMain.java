package com.myitian.hirespaintings;

import com.myitian.entity.decoration.hirespainting.HiResPaintingEntity;
import com.myitian.entity.decoration.hirespainting.HiResPaintingMotive;
import com.myitian.item.HiResPaintingItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HiResPaintingsMain implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("HiResPaintings");

    public static final String MODID = "hirespaintings";

    public static final EntityType<HiResPaintingEntity> HIRESPAINTING_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MODID, "hirespainting"),
            FabricEntityTypeBuilder.create(
                            EntityCategory.MISC,
                            (EntityType.EntityFactory<HiResPaintingEntity>) HiResPaintingEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .trackable(160, Integer.MAX_VALUE).build());

    public static final Item HIRESPAINTING_ITEM = Registry.register(
            Registry.ITEM,
            new Identifier(MODID, "hirespainting"),
            new HiResPaintingItem(new Item.Settings().group(ItemGroup.DECORATIONS)));

    public static final DefaultedRegistry<HiResPaintingMotive> HIRESPAINTING_MOTIVE = new DefaultedRegistry<>("hirespaintings:kebab");

    @Override
    public void onInitialize() {
        initMotive();
    }

    private void initMotive() {
        LOGGER.info("HiResPainting init / " + HiResPaintingMotive.KEBAB.toString());
    }
}
