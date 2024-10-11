package com.thedoggys.rotp_7su.client;

import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.client.render.ExamplePickaxeRenderer;
import com.thedoggys.rotp_7su.client.render.ExampleStandRenderer;
import com.thedoggys.rotp_7su.init.InitEntities;
import com.thedoggys.rotp_7su.init.InitStands;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = AddonMain.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(
                InitStands.STAND_EXAMPLE_STAND.getEntityType(), ExampleStandRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(
                InitEntities.EXAMPLE_PICKAXE.get(), manager -> new ExamplePickaxeRenderer(manager));
    }
}
