package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
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

public class MiraclesWipePresence extends StandEntityAction {
    public MiraclesWipePresence(Builder builder) {
        super(builder);
    }


    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        if(!world.isClientSide){
            LivingEntity standUser = userPower.getUser();
            LazyOptional<LivingData> playerDataOptional = standUser.getCapability(LivingDataProvider.CAPABILITY);
            playerDataOptional.ifPresent(playerData ->{
                boolean isGently = playerData.isWipePresence();
                playerData.setWipePresence(!isGently);
            });
        }
    }
    @Override
    public boolean greenSelection(IStandPower power, ActionConditionResult conditionCheck) {
        LivingEntity standUser = power.getUser();
        LazyOptional<LivingData> playerDataOptional = standUser.getCapability(LivingDataProvider.CAPABILITY);
        return playerDataOptional.map(LivingData::isWipePresence).isPresent()?playerDataOptional.map(LivingData::isWipePresence).get() :false;
    }

    ResourceLocation UN_REFLECT = new ResourceLocation(AddonMain.MOD_ID,"textures/action/miracles_aura_off.png");

    @Override
    public ResourceLocation getIconTexture(@Nullable IStandPower power) {
        LivingEntity standUser = power.getUser();
        LazyOptional<LivingData> playerDataOptional = standUser.getCapability(LivingDataProvider.CAPABILITY);
        boolean activated = playerDataOptional.map(LivingData::isWipePresence).isPresent()?playerDataOptional.map(LivingData::isWipePresence).get() :false;
        if(!activated){
            return super.getIconTexture(power);
        }
        return UN_REFLECT;
    }
}
