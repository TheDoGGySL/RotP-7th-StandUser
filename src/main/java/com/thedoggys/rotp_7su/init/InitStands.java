package com.thedoggys.rotp_7su.init;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.*;
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

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

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
