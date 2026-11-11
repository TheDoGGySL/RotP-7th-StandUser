package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.thedoggys.rotp_7su.init.power.InitStandEffects;
import com.thedoggys.rotp_7su.specials.SpecialsEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class SpecialsGreenPunch extends StandEntityLightAttack {
    public SpecialsGreenPunch(StandEntityLightAttack.Builder builder){
        super(builder);
    }

    @Override
    public void onClick(World world, LivingEntity user, IStandPower power) {
        SpecialsEntities specialsEntities = IStandPower.getPlayerStandPower((PlayerEntity) user).getContinuousEffects().getOrCreateEffect(InitStandEffects.SPECIALS_SUMMONED_ENTITIES.get());
        power.stopHeldAction(false);
        specialsEntities.pickEntity(3);
        super.onClick(world, user, power);
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        return super.punchEntity(stand, target, dmgSource)
                .damage((float) (stand.getAttackDamage() * 0.4))
                .addKnockback((float) (stand.getAttackKnockback() * 1.2));
    }
}
