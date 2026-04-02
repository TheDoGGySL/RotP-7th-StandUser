package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.thedoggys.rotp_7su.entity.JoykillerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.util.SoundEvent;

public class JoykillerMeleeBarrage extends StandEntityMeleeBarrage {

    public JoykillerMeleeBarrage(Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        if (stand instanceof JoykillerEntity && !((JoykillerEntity) stand).hasAxe()) {
            return conditionMessage("joykiller_axe");
        }
        return super.checkStandConditions(stand, power, target);
    }

    @Override
    public BarrageEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        BarrageEntityPunch stabBarrage = super.punchEntity(stand, target, dmgSource);
        if (target instanceof SkeletonEntity) {
            stabBarrage.damage(stabBarrage.getDamage() * 0.75F);
        }
        return stabBarrage;
    }
    
    @Override
    protected void clTtickSwingSound(int tick, StandEntity standEntity) {
        SoundEvent swingSound = getPunchSwingSound();
        if (swingSound != null) {
            standEntity.playSound(swingSound, 0.25F, 
                    0.9F + standEntity.getRandom().nextFloat() * 0.2F, ClientUtil.getClientPlayer());
        }
    }
}
