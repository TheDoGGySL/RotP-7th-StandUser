package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.thedoggys.rotp_7su.init.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public class RedGarlandFinisher extends StandEntityHeavyAttack {
    public static final StandPose FINISHER = new StandPose("finisherPunch");

    public RedGarlandFinisher(StandEntityHeavyAttack.Builder builder) { super(builder); }
    public PlayerEntity getPlayer() { return player; }
    PlayerEntity player;

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        LivingEntity living = (LivingEntity) target;
        if (target != null) {
            living.addEffect(new EffectInstance(InitEffects.INTIMIDATED.get(), 100, 1, false, false, false));
        }
        return super.punchEntity(stand, target, dmgSource).addKnockback(0.5F + stand.getLastHeavyFinisherValue())
                .knockbackXRot(-10.0F);
    }
}
