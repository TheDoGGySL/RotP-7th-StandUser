package com.thedoggys.rotp_7su.init;

import com.github.standobyte.jojo.action.stand.effect.StandEffectType;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.specials.SpecialsEntities;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStandEffects {
    public static final DeferredRegister<StandEffectType<?>> STAND_EFFECTS = DeferredRegister.create(
            (Class<StandEffectType<?>>) ((Class<?>) StandEffectType.class), AddonMain.MOD_ID);
    
    public static final RegistryObject<StandEffectType<SpecialsEntities>> SPECIALS_SUMMONED_ENTITIES = STAND_EFFECTS.register("specials_entities", 
            () -> new StandEffectType<>(SpecialsEntities::new));
    

}
