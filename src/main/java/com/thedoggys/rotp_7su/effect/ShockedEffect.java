package com.thedoggys.rotp_7su.effect;

import com.github.standobyte.jojo.potion.IApplicableEffect;
import com.thedoggys.rotp_7su.AddonMain;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AddonMain.MOD_ID)
public class ShockedEffect extends Effect implements IApplicableEffect {
    public ShockedEffect(int color) {
        super(EffectType.BENEFICIAL, color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amp) {
        int j =  25 >> amp;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amp) {
        int multiplier = 0;
        for(ItemStack i : entity.getArmorSlots()){
            IEnergyStorage energyStorage = i.getCapability(CapabilityEnergy.ENERGY).orElse(null);
            if(energyStorage != null){
                multiplier++;
            }
        }

        IEnergyStorage energyStorage = entity.getMainHandItem().getCapability(CapabilityEnergy.ENERGY).orElse(null);
        if(energyStorage != null)
            multiplier++;
        energyStorage = entity.getOffhandItem().getCapability(CapabilityEnergy.ENERGY).orElse(null);
        if(energyStorage != null)
            multiplier++;
        if(multiplier > 0){
            int numTicks = 0;
            if(entity instanceof PlayerEntity){
                CompoundNBT tag = entity.getPersistentData().getCompound(PlayerEntity.PERSISTED_NBT_TAG);
            }
            entity.hurt(DamageSource.LIGHTNING_BOLT, 20 * multiplier * (amp + 1));

        }
    }

    @Override
    public boolean isApplicable(LivingEntity entity) {
        return true;
    }
}