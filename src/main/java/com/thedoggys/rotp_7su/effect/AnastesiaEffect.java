package com.thedoggys.rotp_7su.effect;

import com.thedoggys.rotp_7su.AddonMain;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AddonMain.MOD_ID)
public class AnastesiaEffect extends Effect {
    public AnastesiaEffect(int color) {
        super(EffectType.BENEFICIAL, color);
    }
}


