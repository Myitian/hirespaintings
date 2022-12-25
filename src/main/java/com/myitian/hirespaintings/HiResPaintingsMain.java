package com.myitian.hirespaintings;

import com.mojang.serialization.Lifecycle;
import com.myitian.entity.decoration.hirespainting.HiResPaintingEntity;
import com.myitian.entity.decoration.hirespainting.HiResPaintingMotive;
import com.myitian.item.HiResPaintingItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleDefaultedRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiResPaintingsMain implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("HiResPaintings");

    public static final String MODID = "hirespaintings";

    public static final EntityType<HiResPaintingEntity> HIRESPAINTING_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MODID, "hirespainting"),
            FabricEntityTypeBuilder.create(
                            SpawnGroup.MISC,
                            (EntityType.EntityFactory<HiResPaintingEntity>) HiResPaintingEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .trackRangeChunks(10)
                    .trackedUpdateRate(Integer.MAX_VALUE).build());

    public static final Item HIRESPAINTING_ITEM = Registry.register(
            Registries.ITEM,
            new Identifier(MODID, "hirespainting"),
            new HiResPaintingItem(new Item.Settings()));

    public static final RegistryKey<Registry<HiResPaintingMotive>> MOTIVE_KEY = RegistryKey.ofRegistry(new Identifier(MODID, "motive"));

    public static final SimpleDefaultedRegistry<HiResPaintingMotive> HIRESPAINTING_MOTIVE = new SimpleDefaultedRegistry<>(
            "hirespaintings:kebab",
            MOTIVE_KEY,
            Lifecycle.stable(),
            false);

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
            content.addAfter(Items.PAINTING, HIRESPAINTING_ITEM);
        });
        initMotive();
    }

    private void initMotive() {
        LOGGER.info("HiResPainting init / " + HiResPaintingMotive.KEBAB.toString());
    }
}
