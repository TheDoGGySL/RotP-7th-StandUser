package com.thedoggys.rotp_7su.init;

import com.thedoggys.rotp_7su.AddonMain;

import com.thedoggys.rotp_7su.entity.AxeEntity;
import com.thedoggys.rotp_7su.entity.BulletEntity;
import com.thedoggys.rotp_7su.entity.ButterflyEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
            ForgeRegistries.ENTITIES, AddonMain.MOD_ID);

    public static final RegistryObject<EntityType<AxeEntity>> JOYKILLER_AXE = ENTITIES.register("joykiller_axe",
            () -> EntityType.Builder.<AxeEntity>of(AxeEntity::new, EntityClassification.MISC)
                    .sized(1.0F, 1.0F)
                    .build(AddonMain.MOD_ID + ":joykiller_axe"));

    public static final RegistryObject<EntityType<BulletEntity>> BULLET = ENTITIES.register("bullet",
            () -> EntityType.Builder.<BulletEntity>of(BulletEntity::new, EntityClassification.MISC).sized(0.0625F, 0.0625F).clientTrackingRange(16).setUpdateInterval(1).fireImmune()
                    .build(new ResourceLocation(AddonMain.MOD_ID, "bullet").toString()));

    public static final RegistryObject<EntityType<ButterflyEntity>> KNIFE = ENTITIES.register("knife",
            () -> EntityType.Builder.<ButterflyEntity>of(ButterflyEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).setUpdateInterval(20)
                    .build(new ResourceLocation(AddonMain.MOD_ID, "knife").toString()));
};
