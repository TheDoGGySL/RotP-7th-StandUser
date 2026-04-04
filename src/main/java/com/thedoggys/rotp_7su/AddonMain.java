package com.thedoggys.rotp_7su;

import com.thedoggys.rotp_7su.capability.CapabilityHandler;
import com.thedoggys.rotp_7su.init.*;
import com.thedoggys.rotp_7su.init.power.InitStandEffects;
import com.thedoggys.rotp_7su.init.power.InitStands;
import com.thedoggys.rotp_7su.network.AddonPackets;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// Your addon's main file

@Mod(AddonMain.MOD_ID)
public class AddonMain {

    public static final String MOD_ID = "rotp_7su";
    public static final Logger LOGGER = LogManager.getLogger();

    public AddonMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitParticles.PARTICLES.register((modEventBus));
        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
        InitStandEffects.STAND_EFFECTS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitEffects.EFFECTS.register(modEventBus);
        modEventBus.addListener(this::preInit);
    }
    private void preInit(FMLCommonSetupEvent event){
        AddonPackets.init();
        CapabilityHandler.commonSetupRegister();
    }
    public static Logger getLogger() {
        return LOGGER;
    }
}
