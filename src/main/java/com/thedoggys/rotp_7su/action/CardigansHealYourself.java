package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.init.InitSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class CardigansHealYourself extends StandEntityAction {
    public CardigansHealYourself(StandEntityAction.Builder builder) {
        super(builder);
    }
    public static final StandPose INJECT_SELF = new StandPose("injectYourself");

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            userPower.getUser().heal(8);
            standEntity.playSound(InitSounds.CARDIGANS_HEAL.get(), 1f, 1f);
            if (userPower.getResolveLevel() >= 3) {
                userPower.getUser().removeEffect(ModStatusEffects.BLEEDING.get());
                userPower.getUser().removeEffect(Effects.POISON);
                userPower.getUser().removeEffect(Effects.WITHER);
                userPower.getUser().removeEffect(Effects.HUNGER);
                userPower.getUser().removeEffect(Effects.CONFUSION);
                userPower.getUser().removeEffect(Effects.BLINDNESS);
            }
        }
    }
}