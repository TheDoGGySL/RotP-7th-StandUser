package com.thedoggys.rotp_7su.init;

import java.util.function.Supplier;

import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.util.mc.OstSoundList;
import com.thedoggys.rotp_7su.AddonMain;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(
            ForgeRegistries.SOUND_EVENTS, AddonMain.MOD_ID); // TODO sounds.json
    
    public static final RegistryObject<SoundEvent> CARDIGANS_SUMMON_VOICELINE = SOUNDS.register("cardigans_summon_voiceline", 
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "cardigans_summon_voiceline")));

    public static final Supplier<SoundEvent> CARDIGANS_SUMMON_SOUND = ModSounds.STAND_SUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> CARDIGANS_UNSUMMON_SOUND = ModSounds.STAND_UNSUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> CARDIGANS_PUNCH_LIGHT = ModSounds.STAND_PUNCH_LIGHT;
    
    public static final Supplier<SoundEvent> CARDIGANS_PUNCH_HEAVY = ModSounds.STAND_PUNCH_HEAVY;
    
    public static final Supplier<SoundEvent> CARDIGANS_PUNCH_BARRAGE = ModSounds.STAND_PUNCH_LIGHT;
    
    public static final Supplier<SoundEvent> CARDIGANS_THROW_PICKAXE = ModSounds.STAND_PUNCH_LIGHT;
    
    public static final OstSoundList CARDIGANS_OST = new OstSoundList(
            new ResourceLocation(AddonMain.MOD_ID, "cardigans_ost"), SOUNDS);
}
