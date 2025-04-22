package com.thedoggys.rotp_7su.specials;

import java.util.ArrayList;
import java.util.List;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.action.stand.effect.StandEffectInstance;
import com.github.standobyte.jojo.action.stand.effect.StandEffectType;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.network.PacketManager;
import com.github.standobyte.jojo.network.packets.fromserver.TrSetStandEntityPacket;
import com.thedoggys.rotp_7su.init.InitStandEffects;

public class SpecialsEntities extends StandEffectInstance {
    private int specialsPickedEntity = 0;
    private boolean isSummoned;
    public final List<StandEntity> specialsEntities = new ArrayList<>();

    public SpecialsEntities() {
        this(InitStandEffects.SPECIALS_SUMMONED_ENTITIES.get());
    }

    public SpecialsEntities(StandEffectType<?> effectType) {
        super(effectType);
    }
    
    public void addSpecialsEntity(StandEntity entity) {
        specialsEntities.add(entity);
    }
    
    public void setIsSummoned(boolean isSummoned) {
        this.isSummoned = isSummoned;
        if (!isSummoned && !world.isClientSide()) {
            for (StandEntity entity : specialsEntities) {
                entity.remove();
            }
            specialsEntities.clear();
            userPower.setStandManifestation(null);
            PacketManager.sendToClientsTrackingAndSelf(new TrSetStandEntityPacket(user.getId(), -1), user);
        }
    }
    
    public Iterable<StandEntity> getSpecialsEntities() {
        return specialsEntities;
    }
    
    public void pickEntity(int index) {
        this.specialsPickedEntity = index;
        if (isSummoned) {
            if (specialsEntities.isEmpty()) {
                JojoMod.LOGGER.error("Specials entities list is empty");
                return;
            }
            if (index < 0 || index >= specialsEntities.size()) {
                JojoMod.LOGGER.error("Invalid Specials entity index ({})", index);
                return;
            }
            StandEntity standEntity = specialsEntities.get(index);
            if (!standEntity.removed) {
                userPower.setStandManifestation(standEntity);
            }
        }
    }
    
    public void onSummon() {
        pickEntity(specialsPickedEntity);
    }

    @Override
    protected void start() {}

    @Override
    protected void tick() {
        if (isSummoned) {
            boolean allEntitiesRemoved = specialsEntities.stream().allMatch(entity -> entity.removed);
            if (allEntitiesRemoved) {
                setIsSummoned(false);
            }
        }
    }

    @Override
    protected void stop() {
        setIsSummoned(false);
    }

    @Override
    protected boolean needsTarget() {
        return false;
    }
    
}
