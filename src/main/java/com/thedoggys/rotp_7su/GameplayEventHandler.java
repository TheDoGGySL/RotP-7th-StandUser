package com.thedoggys.rotp_7su;

import com.thedoggys.rotp_7su.init.InitEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.thedoggys.rotp_7su.AddonMain.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameplayEventHandler {
    private static final String DAMAGE_TAG = MOD_ID + "_stored_damage";

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.level.isClientSide) return;
        if (entity.hasEffect(InitEffects.ANASTESIA_EFFECT.get())) {
            CompoundNBT data = entity.getPersistentData();
            float current = data.getFloat(DAMAGE_TAG);
            data.putFloat(DAMAGE_TAG, current + event.getAmount());
            event.setAmount(0.0f);
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.level.isClientSide) return;

        CompoundNBT data = entity.getPersistentData();
    if (!entity.hasEffect(InitEffects.ANASTESIA_EFFECT.get()) && data.contains(DAMAGE_TAG)) {
            float storedDamage = data.getFloat(DAMAGE_TAG);
            if (storedDamage > 0 && !entity.isDeadOrDying()) {
                entity.hurt(DamageSource.GENERIC, storedDamage);
                data.remove(DAMAGE_TAG);
            }
        }
    }
}
