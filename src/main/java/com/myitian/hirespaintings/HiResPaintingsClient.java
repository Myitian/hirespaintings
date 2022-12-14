package com.myitian.hirespaintings;

import com.myitian.client.render.entity.HiResPaintingEntityRenderer;
import com.myitian.client.texture.HiResPaintingManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ReloadableResourceManagerImpl;

@Environment(EnvType.CLIENT)
public class HiResPaintingsClient implements ClientModInitializer, ClientLifecycleEvents.ClientStarted, ClientLifecycleEvents.ClientStopping {

    public static HiResPaintingManager HIRESPAINTING_MANAGER = null;

    public static HiResPaintingManager getHiResPaintingManager() {
        return HIRESPAINTING_MANAGER;
    }

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(this);
        ClientLifecycleEvents.CLIENT_STOPPING.register(this);
        EntityRendererRegistry.register(HiResPaintingsMain.HIRESPAINTING_ENTITY, HiResPaintingEntityRenderer::new);
    }

    @Override
    public void onClientStarted(MinecraftClient client) {
        HIRESPAINTING_MANAGER = new HiResPaintingManager(client.getTextureManager());
        ((ReloadableResourceManagerImpl) client.getResourceManager()).registerReloader(HIRESPAINTING_MANAGER);
    }

    @Override
    public void onClientStopping(MinecraftClient client) {
        HIRESPAINTING_MANAGER.close();
    }
}
