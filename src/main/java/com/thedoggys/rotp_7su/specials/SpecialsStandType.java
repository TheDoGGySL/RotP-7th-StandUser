package com.thedoggys.rotp_7su.specials;

import java.util.Optional;
import java.util.function.Consumer;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.thedoggys.rotp_7su.entity.SpecialsEntity;
import com.thedoggys.rotp_7su.init.InitStandEffects;
import com.thedoggys.rotp_7su.init.InitStands;

import net.minecraft.entity.LivingEntity;

public class SpecialsStandType extends EntityStandType<StandStats> {

    public SpecialsStandType(AbstractBuilder<?, StandStats> arg0) {
        super(arg0);
    }
    
    @Override
    public boolean summon(LivingEntity user, IStandPower standPower, Consumer<StandEntity> beforeTheSummon, boolean withoutNameVoiceLine, boolean addToWorld) {
        if (!standPower.canUsePower()) {
            return false;
        }
        
//        if (!withoutNameVoiceLine && !user.isShiftKeyDown()) {
//            SoundEvent shout = summonShoutSupplier.get();
//            if (shout != null) {
//                JojoModUtil.sayVoiceLine(user, shout);
//            }
//        }
        triggerAdvancement(standPower, standPower.getStandManifestation());
        
        if (!user.level.isClientSide()) {
            SpecialsEntities summonedEntities = getSpecialsEntities(standPower).orElse(null);
            if (summonedEntities == null) {
                summonedEntities = new SpecialsEntities(InitStandEffects.SPECIALS_SUMMONED_ENTITIES.get());
                standPower.getContinuousEffects().addEffect(summonedEntities);
            }
            summonedEntities.setIsSummoned(true);
            
            for (int i = 0; i < InitStands.SPECIALS_ENTITY_TYPES.size(); i++) {
                StandEntityType<?> entityType = InitStands.SPECIALS_ENTITY_TYPES.get(i).get();
                SpecialsEntity standEntity = (SpecialsEntity) entityType.create(user.level);
                StandRelativeOffset offset = StandRelativeOffset.withYOffset(InitStands.specialsXOffsets[i], 0.2, InitStands.specialsZOffsets[i]);
                standEntity.setDefaultOffsetFromUser(offset);
                
                standEntity.copyPosition(user);
                standEntity.setUserAndPower(user, standPower);
                summonedEntities.addSpecialsEntity(standEntity);
                beforeTheSummon.accept(standEntity);
                
                if (addToWorld) {
                    finalizeStandSummonFromAction(user, standPower, standEntity, true);
                }
                
                standEntity.onStandSummonServerSide();
            }
            
            summonedEntities.onSummon();
        }
        return true;
    }

    @Override
    public void unsummon(LivingEntity user, IStandPower standPower) {
        if (!user.level.isClientSide()) {
            getSpecialsEntities(standPower)
            .ifPresent(specials -> {
                for (StandEntity standEntity : specials.getSpecialsEntities()) {
                    if (!standEntity.isBeingRetracted()) {
                        standEntity.retractStand(true);
                    }
                    else if (standEntity.isManuallyControlled()) {
                        standEntity.stopRetraction();
                    }
                }
            });
        }
    }

    @Override
    public void forceUnsummon(LivingEntity user, IStandPower standPower) {
        if (!user.level.isClientSide()) {
            getSpecialsEntities(standPower).ifPresent(specials -> {
                specials.setIsSummoned(false);
            });
        }
        else if (user.is(ClientUtil.getClientPlayer())) {
            StandUtil.setManualControl(ClientUtil.getClientPlayer(), false, false);
        }
    }
    
    public static Optional<SpecialsEntities> getSpecialsEntities(IStandPower standPower) {
        return standPower.getContinuousEffects().getEffects()
                .filter(effects -> effects.effectType == InitStandEffects.SPECIALS_SUMMONED_ENTITIES.get())
                .map(effect -> (SpecialsEntities) effect)
                .findFirst();
    }
    
}
