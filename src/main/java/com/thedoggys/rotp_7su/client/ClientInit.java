package com.thedoggys.rotp_7su.client;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.particle.HamonSparkParticle;
import com.github.standobyte.jojo.client.particle.custom.CustomParticlesHelper;
import com.github.standobyte.jojo.client.resources.CustomResources;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.ClientEvents;
import com.thedoggys.rotp_7su.client.render.CardigansRenderer;
import com.thedoggys.rotp_7su.client.render.SpecialsModel;
import com.thedoggys.rotp_7su.client.render.SpecialsRenderer;
import com.thedoggys.rotp_7su.init.InitParticles;
import com.thedoggys.rotp_7su.init.InitStands;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
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
        for (int i = 0; i < InitStands.SPECIALS_ENTITY_TYPES.size(); i++) {
            final String index = String.valueOf(i);
            RenderingRegistry.registerEntityRenderingHandler(
                    InitStands.SPECIALS_ENTITY_TYPES.get(i).get(), ClientUtil.logException(manager -> 
                    new SpecialsRenderer(manager, 
                            new ResourceLocation(AddonMain.MOD_ID, "specials" + index), 
                            SpecialsModel::new, 
                            new ResourceLocation(AddonMain.MOD_ID, "textures/entity/stand/specials" + index + ".png"))));
        }
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
