package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.ModParticles;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.thedoggys.rotp_7su.init.InitEffects;
import com.thedoggys.rotp_7su.init.InitSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RedGarlandIntimidatingAura extends StandEntityAction {
    public static final StandPose AURA = new StandPose("aura");
    public RedGarlandIntimidatingAura(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        int range = 8;
        if (!world.isClientSide()) {
            LivingEntity user = userPower.getUser();
            for (LivingEntity entity : MCUtil.entitiesAround(
                    LivingEntity.class, user, range, false, entity ->
                            entity != user &&
                                    !(entity instanceof StandEntity && user.is(((StandEntity) entity).getUser())))) {
                entity.addEffect(new EffectInstance(ModStatusEffects.STUN.get(), 50));
                entity.addEffect(new EffectInstance(InitEffects.INTIMIDATED.get(), 250));
            }

            Random random = world.getRandom();
            int particleCount = 32 + random.nextInt(6);
            for (int i = 0; i < particleCount; i++) {
                double angle = random.nextDouble() * Math.PI * 2;
                double distance = random.nextDouble() * range;
                double yOffset = (random.nextDouble() - 0.5) * 8;
                double x = user.getX() + Math.cos(angle) * distance;
                double y = user.getY() + yOffset + 0.5;
                double z = user.getZ() + Math.sin(angle) * distance;
                ((ServerWorld) world).sendParticles(
                        ParticleTypes.FLAME, x, y, z, 1, 0, 0, 0, 0.02
                );
            }
        }
        standEntity.playSound(InitSounds.RED_GARLAND_AURA_BOOM.get(), 2.0F, 1.0F);
    }
}
