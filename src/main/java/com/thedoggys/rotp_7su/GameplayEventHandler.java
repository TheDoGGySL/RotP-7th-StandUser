package com.thedoggys.rotp_7su;

import com.thedoggys.rotp_7su.init.InitEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thedoggys.rotp_7su.AddonMain.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class GameplayEventHandler {
    public static Map<LivingEntity, List<CustomDamageSource>> damageSourceMap = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerAttack(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.level.isClientSide()) {
            if (entity.hasEffect(InitEffects.ANASTESIA_EFFECT.get())) {
                CustomDamageSource customDamageSource = new CustomDamageSource(event.getSource(), event.getAmount());
                if (damageSourceMap.containsKey(entity)) {
                    List<CustomDamageSource> damageSources = damageSourceMap.get(entity);
                    damageSources.add(customDamageSource);
                    damageSourceMap.put(entity, damageSources);
                } else {
                    List<CustomDamageSource> customDamageSourceList = new ArrayList<>();
                    customDamageSourceList.add(customDamageSource);
                damageSourceMap.put(entity, customDamageSourceList);
                }
                // event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPotionRemove(PotionEvent.PotionRemoveEvent event) {
        onPotionRemove(event.getEntityLiving());
    }

    @SubscribeEvent
    public static void onPotionExpiry(PotionEvent.PotionExpiryEvent event) {
        onPotionRemove(event.getEntityLiving());
    }

    private static void onPotionRemove(LivingEntity entity) {
        if (damageSourceMap.containsKey(entity)) {
            damageSourceMap.get(entity).forEach(damageSource -> {
                if (damageSource.damageSource.getEntity() instanceof LivingEntity) {
                    entity.hurt(damageSource.damageSource, damageSource.damageFloat);
                }
            });
            damageSourceMap.remove(entity);
        }
    }

    public static class CustomDamageSource {
        public final DamageSource damageSource;
        public final float damageFloat;

        public CustomDamageSource(DamageSource damageSource, float damageFloat) {
            this.damageSource = damageSource;
            this.damageFloat = damageFloat;
        }
    }
}

