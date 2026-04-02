package com.thedoggys.rotp_7su.effect;

import com.github.standobyte.jojo.potion.IApplicableEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class SizzleEffect extends Effect implements IApplicableEffect {
    public SizzleEffect(int color) {
        super(EffectType.BENEFICIAL, color);
    }
    @Override
    public boolean isApplicable(LivingEntity entity) {
        return true;
    }
}


