package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.capability.LivingData;
import com.thedoggys.rotp_7su.capability.LivingDataProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class MiraclesErasePresence extends StandEntityAction {
    public MiraclesErasePresence(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        LazyOptional<LivingData> data = user.getCapability(LivingDataProvider.CAPABILITY);
        boolean gentlyActive = data.map(LivingData::isGentlyWeeps).orElse(false);
        boolean wipeActive = data.map(LivingData::isWipePresence).orElse(false);
        if (gentlyActive) {
            return ActionConditionResult.POSITIVE;
        }
        if (!power.isActive()) {
            return conditionMessage("stand_not_active");
        }
        if (power.getStamina() < power.getMaxStamina() * 0.2f) {
            return conditionMessage("not_enough_stamina");
        }
        if (wipeActive) {
            return conditionMessage("aura_already_active");
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide) {
            LivingEntity standUser = userPower.getUser();
            LazyOptional<LivingData> playerDataOptional = standUser.getCapability(LivingDataProvider.CAPABILITY);
            playerDataOptional.ifPresent(playerData -> {
                if (playerData.isWipePresence()) {
                    playerData.setWipePresence(false);
                }
                boolean isGently = playerData.isGentlyWeeps();
                playerData.setGentlyWeeps(!isGently);
            });
        }
    }

    @Override
    public boolean greenSelection(IStandPower power, ActionConditionResult conditionCheck) {
        LivingEntity standUser = power.getUser();
        LazyOptional<LivingData> playerDataOptional = standUser.getCapability(LivingDataProvider.CAPABILITY);
        boolean auraActive = playerDataOptional.map(LivingData::isGentlyWeeps).orElse(false);
        return auraActive && power.isActive() && power.getStamina() >= power.getMaxStamina() * 0.2f;
    }

    private final ResourceLocation UN_REFLECT = new ResourceLocation(AddonMain.MOD_ID, "textures/action/miracles_aura_off.png");

    @Override
    public ResourceLocation getIconTexture(@Nullable IStandPower power) {
        if (power == null) return super.getIconTexture(null);
        LivingEntity standUser = power.getUser();
        LazyOptional<LivingData> playerDataOptional = standUser.getCapability(LivingDataProvider.CAPABILITY);
        boolean activated = playerDataOptional.map(LivingData::isGentlyWeeps).orElse(false);
        return activated ? UN_REFLECT : super.getIconTexture(power);
    }
}