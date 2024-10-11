package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class CardiganesHeal extends StandEntityAction {

    public CardiganesHeal(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower power, StandEntityTask task) {
        if (!world.isClientSide()) {
            LivingEntity user = power.getUser();
            Entity targetEntity = target.getType() == ActionTarget.TargetType.ENTITY ? target.getEntity() : null;
            LivingEntity targetLiving = targetEntity instanceof LivingEntity ? (LivingEntity) targetEntity : null;
            LivingEntity entityToHeal = targetEntity != null && canBeHealed(targetLiving, user) ? targetLiving : user;
            entityToHeal.removeEffect(Effects.POISON);
            entityToHeal.removeEffect(Effects.WITHER);
            entityToHeal.removeEffect(Effects.HUNGER);
            entityToHeal.removeEffect(Effects.CONFUSION);
        }
    }


    private boolean canBeHealed(LivingEntity targetEntity, LivingEntity user) {
        boolean shiftVariation = JojoModUtil.useShiftVar(user);
        return shiftVariation && !JojoModUtil.isUndead(targetEntity);
    }
}