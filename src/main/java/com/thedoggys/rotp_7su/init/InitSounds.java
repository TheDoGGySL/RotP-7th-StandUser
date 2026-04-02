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

    public static final RegistryObject<SoundEvent> BANDAGE_USE = SOUNDS.register("bandage_use",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "bandage_use")));

    public static final RegistryObject<SoundEvent> CARDIGANS_HEAL = SOUNDS.register("cg_heal",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "cg_heal")));

    public static final RegistryObject<SoundEvent> CARDIGANS_INJECT = SOUNDS.register("cg_inject",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "cg_inject")));

    public static final RegistryObject<SoundEvent> CARDIGANS_REVIVE = SOUNDS.register("cg_revive",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "cg_revive")));

    public static final RegistryObject<SoundEvent> CARDIGANS_CHARGE = SOUNDS.register("cg_charge",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "cg_charge")));

    public static final RegistryObject<SoundEvent> AXE_SWING = SOUNDS.register("axe_swing",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "axe_swing")));

    public static final RegistryObject<SoundEvent> RED_GARLAND_AURA = SOUNDS.register("red_garland_aura",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "red_garland_aura")));

    public static final RegistryObject<SoundEvent> RED_GARLAND_AURA_BOOM = SOUNDS.register("red_garland_aura_boom",
            () -> new SoundEvent(new ResourceLocation(AddonMain.MOD_ID, "red_garland_aura_boom")));

    public static final Supplier<SoundEvent> CARDIGANS_SUMMON_SOUND = ModSounds.STAND_SUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> CARDIGANS_UNSUMMON_SOUND = ModSounds.STAND_UNSUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> CARDIGANS_PUNCH_LIGHT = ModSounds.STAND_PUNCH_LIGHT;
    

    public static final OstSoundList CARDIGANS_OST = new OstSoundList(
            new ResourceLocation(AddonMain.MOD_ID, "cardigans_ost"), SOUNDS);

    public static final OstSoundList RED_GARLAND_OST = new OstSoundList(
            new ResourceLocation(AddonMain.MOD_ID, "red_garland_ost"), SOUNDS);
}
