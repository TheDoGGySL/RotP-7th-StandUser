package com.thedoggys.rotp_7su.client;

import com.github.standobyte.jojo.client.particle.HamonSparkParticle;
import com.github.standobyte.jojo.client.particle.MeteoriteVirusParticle;
import com.github.standobyte.jojo.client.particle.custom.CustomParticlesHelper;
import com.github.standobyte.jojo.client.resources.CustomResources;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.ClientEvents;
import com.thedoggys.rotp_7su.client.render.CardigansRenderer;
import com.thedoggys.rotp_7su.init.InitParticles;
import com.thedoggys.rotp_7su.init.InitStands;

import com.thedoggys.rotp_7su.particle.CardigansShockParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = AddonMain.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = Minecraft.getInstance();
        RenderingRegistry.registerEntityRenderingHandler(
                InitStands.STAND_CARDIGANS.getEntityType(), CardigansRenderer::new);
        event.enqueueWork(() -> {
            ClientEvents.init(mc);
        });
    }
    @SubscribeEvent
    public static void onMcConstructor(ParticleFactoryRegisterEvent event) {
        Minecraft mc = Minecraft.getInstance();
        mc.particleEngine.register(InitParticles.CARDIGANS_SHOCK.get(), HamonSparkParticle.Factory::new);
        CustomParticlesHelper.saveSprites(mc);
        CustomResources.initCustomResourceManagers(mc);
    }
}
