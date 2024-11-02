package com.thedoggys.rotp_7su;

import com.thedoggys.rotp_7su.init.InitEntities;
import com.thedoggys.rotp_7su.init.InitItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.thedoggys.rotp_7su.init.InitSounds;
import com.thedoggys.rotp_7su.init.InitStands;

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


        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
    }
}
