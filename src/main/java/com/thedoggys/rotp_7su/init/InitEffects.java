package com.thedoggys.rotp_7su.init;

import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.effect.AnastesiaEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEffects {
public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(
        ForgeRegistries.POTIONS, AddonMain.MOD_ID);
    public static final RegistryObject<Effect> ANASTESIA_EFFECT = EFFECTS.register("anastesia",
            () -> new AnastesiaEffect(0xB1F971));

}
