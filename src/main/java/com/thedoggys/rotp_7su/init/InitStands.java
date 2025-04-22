package com.thedoggys.rotp_7su.init;

import java.util.ArrayList;
import java.util.List;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.*;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.github.standobyte.jojo.util.mod.StoryPart;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.action.*;
import com.thedoggys.rotp_7su.entity.CardigansEntity;
import com.thedoggys.rotp_7su.entity.SpecialsEntity;
import com.thedoggys.rotp_7su.specials.SpecialsStandType;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;

@EventBusSubscriber(modid = AddonMain.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), AddonMain.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), AddonMain.MOD_ID);
    public static final StoryPart SEVENTH_STAND_USER = StoryPart.create(new ResourceLocation(AddonMain.MOD_ID, "7su"), "rotp_7su.story_part", name -> name.withStyle(TextFormatting.YELLOW));
 // ======================================== Cardigans ========================================


    // Create all the abilities here...

    public static final RegistryObject<StandEntityLightAttack> CARDIGANS_SLASH_LIGHT = ACTIONS.register("cardigans_slash_light",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(() -> null).swingSound(ModSounds.SILVER_CHARIOT_SWEEP_LIGHT)));

    public static final RegistryObject<CardigansSlashFinisher> CARDIGANS_SLASH = ACTIONS.register("cardigans_slash",
            () -> new CardigansSlashFinisher(new CardigansSlashFinisher.Builder()
                    .standPose(CardigansSlashFinisher.HEAVY)
                    .resolveLevelToUnlock(1)
                    .cooldown(20)
                    .punchSound(InitSounds.CARDIGANS_PUNCH_LIGHT)));

    public static final RegistryObject<StandEntityHeavyAttack> CARDIGANS_SLASH_HEAVY = ACTIONS.register("cardigans_slash_heavy",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .punchSound(() -> null)
                    .setFinisherVariation(CARDIGANS_SLASH)
                    .shiftVariationOf(CARDIGANS_SLASH_LIGHT)
                    .swingSound(ModSounds.SILVER_CHARIOT_SWEEP_LIGHT)));


    public static final RegistryObject<StandEntityAction> CARDIGANS_SHOCK = ACTIONS.register("cardigans_shock",
            () -> new CardigansDefibrylator(new StandEntityLightAttack.Builder()
                    .standPose(CardigansDefibrylator.DEFIBRILATE_ANIM)
                    .resolveLevelToUnlock(4)
                    .standOffsetFront()
                    .partsRequired(StandPart.ARMS)
                    .cooldown(100)
                    .holdToFire(30, false)
            ));
    
    public static final RegistryObject<StandEntityAction> CARDIGANS_BLOCK = ACTIONS.register("cardigans_block",
            () -> new StandEntityBlock());

    public static final RegistryObject<CardigansHealYourself> CARDIGANS_HEAL_YOURSELF = ACTIONS.register("cardigans_cure_yourself",
            () -> new CardigansHealYourself(new CardigansHealYourself.Builder()
                    .standPose(CardigansHealYourself.INJECT_SELF)
                    .standOffsetFromUser(0f, -0.5f, 0.5f)
                    .resolveLevelToUnlock(2)
                    .holdToFire(25, false)
                    .cooldown(175)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> CARDIGANS_HEAL = ACTIONS.register("cardigans_cure",
            () -> new CardigansHeal(new StandEntityAction.Builder()
                    .shiftVariationOf(CARDIGANS_HEAL_YOURSELF)
                    .standOffsetFront()
                    .partsRequired(StandPart.ARMS)
                    .cooldown(100)
                    .holdToFire(20, false)
            ));

    public static final RegistryObject<StandEntityAction> CARDIGANS_ANASTESIA = ACTIONS.register("cardigans_anesthesia",
            () -> new CardigansAnasthesia(new StandEntityAction.Builder()
                    .standPose(CardigansAnasthesia.INJECT_TARGET)
                    .resolveLevelToUnlock(3)
                    .standOffsetFront()
                    .partsRequired(StandPart.ARMS)
                    .cooldown(250)
                    .holdToFire(20, false)
            ));

    public static final RegistryObject<CardigansAnasthesiaYourself> CARDIGANS_ANASTHESIA_YOURSELF = ACTIONS.register("cardigans_anesthesia_yourself",
            () -> new CardigansAnasthesiaYourself(new CardigansAnasthesiaYourself.Builder()
                    .standPose(CardigansHealYourself.INJECT_SELF)
                    .standOffsetFromUser(0f, -0.5f, 0.5f)
                    .resolveLevelToUnlock(3)
                    .shiftVariationOf(CARDIGANS_ANASTESIA)
                    .holdToFire(30, false)
                    .cooldown(301)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> CARDIGANS_DOPPING = ACTIONS.register("cardigans_dopping",
            () -> new CardigansDopping(new StandEntityAction.Builder()
                    .standOffsetFromUser(0f, -0.5f, 0.5f)
                    .standPose(CardigansHealYourself.INJECT_SELF)
                    .resolveLevelToUnlock(1)
                    .partsRequired(StandPart.ARMS)
                    .cooldown(100)
                    .holdToFire(20, false)
            ));



    // ...then create the Stand type instance. Moves, stats, entity sizes, and a few other things are determined here.
    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<CardigansEntity>> STAND_CARDIGANS =
            new EntityStandRegistryObject<>("cardigans",
                    STANDS, 
                    () -> new EntityStandType.Builder<StandStats>()
                    .color(0xffaec9)
                            .storyPartName(InitStands.SEVENTH_STAND_USER.getName())
                    .leftClickHotbar(
                            CARDIGANS_SLASH_LIGHT.get(),
                            CARDIGANS_SHOCK.get(),
                            CARDIGANS_ANASTESIA.get()
                            )
                    .rightClickHotbar(
                            CARDIGANS_BLOCK.get(),
                            CARDIGANS_HEAL_YOURSELF.get(),
                            CARDIGANS_DOPPING.get()
                            )
                    .defaultStats(StandStats.class, new StandStats.Builder()
                            .power(7)
                            .speed(13)
                            .randomWeight(0.3)
                            .range(5, 10)
                            .durability(16)
                            .precision(16)
                            .build())
                    .addSummonShout(InitSounds.CARDIGANS_SUMMON_VOICELINE)
                    .addOst(InitSounds.CARDIGANS_OST)
                    .build(),
                    
                    InitEntities.ENTITIES,
                    () -> new StandEntityType<CardigansEntity>(CardigansEntity::new, 0.7F, 2.1F)
                    .summonSound(InitSounds.CARDIGANS_SUMMON_SOUND)
                    .unsummonSound(InitSounds.CARDIGANS_UNSUMMON_SOUND))
            .withDefaultStandAttributes();
    
    
    
    // ======================================== Specials ========================================
    
    public static final RegistryObject<StandEntityLightAttack> SPECIALS_PUNCH = ACTIONS.register("specials_punch", 
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()));
    
    public static final RegistryObject<StandEntityMeleeBarrage> SPECIALS_BARRAGE = ACTIONS.register("specials_barrage", 
            () -> new StandEntityMeleeBarrage(new StandEntityMeleeBarrage.Builder()));
    
    public static final RegistryObject<StandEntityHeavyAttack> SPECIALS_HEAVY_PUNCH = ACTIONS.register("specials_heavy_punch", 
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .partsRequired(StandPart.ARMS)
                    .shiftVariationOf(SPECIALS_PUNCH).shiftVariationOf(SPECIALS_BARRAGE)));
    
    public static final RegistryObject<StandEntityBlock> SPECIALS_BLOCK = ACTIONS.register("specials_block", 
            () -> new StandEntityBlock());
    
    public static final RegistryObject<StandAction> SPECIALS_PICK_ENTITY = ACTIONS.register("specials_pick_entity", 
            () -> new SpecialsPickEntityAbility(new StandAction.Builder()));
    
    
    /* 
     * This is kinda an exceptional case, so we register the Stand type and the entity types separately, the old way, 
     * instead of using the EntityStandRegistryObject utility class.
     */
    
    public static final RegistryObject<StandEntityType<? extends SpecialsEntity>> SPECIALS_0 = InitEntities.ENTITIES.register("specials0", 
            () -> new StandEntityType<SpecialsEntity>(SpecialsEntity::new, 0.6F, 1.8F)
            .summonSound(ModSounds.STAR_PLATINUM_SUMMON)
            .unsummonSound(ModSounds.STAR_PLATINUM_UNSUMMON));
    
    public static final RegistryObject<StandEntityType<? extends SpecialsEntity>> SPECIALS_1 = InitEntities.ENTITIES.register("specials1", 
            () -> new StandEntityType<SpecialsEntity>(SpecialsEntity::new, 0.6F, 1.8F)
            .summonSound(ModSounds.STAR_PLATINUM_SUMMON)
            .unsummonSound(ModSounds.STAR_PLATINUM_UNSUMMON));
    
    public static final RegistryObject<StandEntityType<? extends SpecialsEntity>> SPECIALS_2 = InitEntities.ENTITIES.register("specials2", 
            () -> new StandEntityType<SpecialsEntity>(SpecialsEntity::new, 0.6F, 1.8F)
            .summonSound(ModSounds.STAR_PLATINUM_SUMMON)
            .unsummonSound(ModSounds.STAR_PLATINUM_UNSUMMON));
    
    public static final RegistryObject<StandEntityType<? extends SpecialsEntity>> SPECIALS_3 = InitEntities.ENTITIES.register("specials3", 
            () -> new StandEntityType<SpecialsEntity>(SpecialsEntity::new, 0.6F, 1.8F)
            .summonSound(ModSounds.STAR_PLATINUM_SUMMON)
            .unsummonSound(ModSounds.STAR_PLATINUM_UNSUMMON));
    
    public static final RegistryObject<StandEntityType<? extends SpecialsEntity>> SPECIALS_4 = InitEntities.ENTITIES.register("specials4", 
            () -> new StandEntityType<SpecialsEntity>(SpecialsEntity::new, 0.6F, 1.8F)
            .summonSound(ModSounds.STAR_PLATINUM_SUMMON)
            .unsummonSound(ModSounds.STAR_PLATINUM_UNSUMMON));
    
    public static final RegistryObject<StandEntityType<? extends SpecialsEntity>> SPECIALS_5 = InitEntities.ENTITIES.register("specials5", 
            () -> new StandEntityType<SpecialsEntity>(SpecialsEntity::new, 0.6F, 1.8F)
            .summonSound(ModSounds.STAR_PLATINUM_SUMMON)
            .unsummonSound(ModSounds.STAR_PLATINUM_UNSUMMON));
    
    public static final List<RegistryObject<StandEntityType<? extends SpecialsEntity>>> SPECIALS_ENTITY_TYPES = Util.make(new ArrayList<>(), list -> {
        list.add(SPECIALS_0);
        list.add(SPECIALS_1);
        list.add(SPECIALS_2);
        list.add(SPECIALS_3);
        list.add(SPECIALS_4);
        list.add(SPECIALS_5);
    });

    public static final double[] specialsXOffsets = {-0.866,  0, 0.866, 0.866, 0, -0.866};
    public static final double[] specialsZOffsets = {  -0.5, -1,  -0.5,   0.5, 1,    0.5};
    
    public static final RegistryObject<SpecialsStandType> STAND_SPECIALS = STANDS.register("specials", 
            () -> new SpecialsStandType(new EntityStandType.Builder<StandStats>()
                    .color(0x8E45FF)
                    .storyPartName(InitStands.SEVENTH_STAND_USER.getName())
                    .leftClickHotbar(
                            SPECIALS_PUNCH.get(),
                            SPECIALS_BARRAGE.get()
                            )
                    .rightClickHotbar(
                            SPECIALS_BLOCK.get(),
                            SPECIALS_PICK_ENTITY.get()
                            )
                    .defaultStats(StandStats.class, new StandStats.Builder()
                            .power(16.0, 18.5)
                            .speed(16.0, 19.0)
                            .range(2.0, 10.0)
                            .durability(16.0, 20.0)
                            .precision(20.0)
                            )
                    .addSummonShout(ModSounds.JOTARO_STAR_PLATINUM)
                    .addOst(ModSounds.STAR_PLATINUM_OST)));
    
    
    @SubscribeEvent
    public static void createDefaultStandAttributes(EntityAttributeCreationEvent event) {
        for (RegistryObject<StandEntityType<? extends SpecialsEntity>> entityType : SPECIALS_ENTITY_TYPES) {
            event.put(entityType.get(), StandEntity.createAttributes().build());
        }
    }
    
    @SubscribeEvent
    public static final void afterStandsRegister(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            for (RegistryObject<StandEntityType<? extends SpecialsEntity>> entityType : SPECIALS_ENTITY_TYPES) {
                entityType.get().setStandType(STAND_SPECIALS);
            }
            STAND_SPECIALS.get().setEntityType(SPECIALS_0);
        });
    }



//    // ======================================== Miracles ========================================
//
//
//    public static final RegistryObject<StandEntityAction> MIRACLES = ACTIONS.register("cardigans_punch",
//            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
//                    .punchSound(InitSounds.CARDIGANS_PUNCH_LIGHT)));
//
//
//    // ...then create the Stand type instance. Moves, stats, entity sizes, and a few other things are determined here.
//    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<MiraclesEntity>> STAND_MIRACLES =
//            new EntityStandRegistryObject<>("miracles",
//                    STANDS,
//                    () -> new EntityStandType.Builder<StandStats>()
//                            .color(0xffaec9)
//                            .storyPartName(StoryPart.OTHER.getName())
//                            .leftClickHotbar(
//                            )
//                            .rightClickHotbar(
//                            )
//                            .defaultStats(StandStats.class, new StandStats.Builder()
//                                    .power(7)
//                                    .speed(13)
//                                    .range(5, 10)
//                                    .durability(16)
//                                    .precision(16)
//                                    .build())
//                            .addSummonShout(InitSounds.CARDIGANS_SUMMON_VOICELINE)
//                            .addOst(InitSounds.CARDIGANS_OST)
//                            .build(),
//
//                    InitEntities.ENTITIES,
//                    () -> new StandEntityType<MiraclesEntity>(MiraclesEntity::new, 0.7F, 2.1F)
//                            .summonSound(InitSounds.CARDIGANS_SUMMON_SOUND)
//                            .unsummonSound(InitSounds.CARDIGANS_UNSUMMON_SOUND))
//                    .withDefaultStandAttributes();
//
//
//
//    // ======================================== ??? ========================================
}
