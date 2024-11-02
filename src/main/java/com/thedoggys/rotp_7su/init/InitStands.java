package com.thedoggys.rotp_7su.init;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityBlock;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.action.CardigansAntidote;
import com.thedoggys.rotp_7su.action.CardigansAntidoteYourself;
import com.thedoggys.rotp_7su.action.CardigansHeal;
import com.thedoggys.rotp_7su.action.CardigansHealYourself;
import com.thedoggys.rotp_7su.entity.CardigansEntity;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), AddonMain.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), AddonMain.MOD_ID);
    @Deprecated public static final ITextComponent PART_7SU_NAME = new TranslationTextComponent("rotp_7su.story_part").withStyle(TextFormatting.YELLOW);
    
 // ======================================== Example Stand ========================================
    
    
    // Create all the abilities here...
    public static final RegistryObject<StandEntityAction> CARDIGANS_PUNCH = ACTIONS.register("cardigans_punch",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.CARDIGANS_PUNCH_LIGHT)));
    
    public static final RegistryObject<StandEntityAction> CARDIGANS_BARRAGE = ACTIONS.register("cardigans_barrage",
            () -> new StandEntityMeleeBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.CARDIGANS_PUNCH_BARRAGE)));

    public static final RegistryObject<StandEntityHeavyAttack> CARDIGANS_FINISHER_PUNCH = ACTIONS.register("cardigans_finisher_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder() // TODO finisher ability
                    .punchSound(InitSounds.CARDIGANS_PUNCH_HEAVY)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityHeavyAttack> CARDIGANS_HEAVY_PUNCH = ACTIONS.register("cardigans_heavy_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .shiftVariationOf(CARDIGANS_PUNCH).shiftVariationOf(CARDIGANS_BARRAGE)
                    .setFinisherVariation(CARDIGANS_FINISHER_PUNCH)
                    .punchSound(InitSounds.CARDIGANS_PUNCH_HEAVY)
                    .partsRequired(StandPart.ARMS)));
    
    public static final RegistryObject<StandEntityAction> CARDIGANS_BLOCK = ACTIONS.register("cardigans_block",
            () -> new StandEntityBlock());

    public static final RegistryObject<CardigansHealYourself> CARDIGANS_HEAL_YOURSELF = ACTIONS.register("cardigans_cure_yourself",
            () -> new CardigansHealYourself(new CardigansHealYourself.Builder()
                    .resolveLevelToUnlock(3)
                    .holdToFire(25, false)
                    .cooldown(175)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> CARDIGANS_HEAL = ACTIONS.register("cardigans_cure",
            () -> new CardigansHeal(new StandEntityLightAttack.Builder()
                    .shiftVariationOf(CARDIGANS_HEAL_YOURSELF)
                    .partsRequired(StandPart.ARMS)
                    .cooldown(100)
                    .holdToFire(20, false)
            ));
    public static final RegistryObject<CardigansAntidoteYourself> CARDIGANS_ANTIDOTE_YOURSELF = ACTIONS.register("cardigans_antidote_yourself",
            () -> new CardigansAntidoteYourself(new CardigansAntidoteYourself.Builder()
                    .resolveLevelToUnlock(3)
                    .holdToFire(25, false)
                    .cooldown(175)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> CARDIGANS_ANTIDOTE = ACTIONS.register("cardigans_antidote",
            () -> new CardigansAntidote(new StandEntityLightAttack.Builder()
                    .shiftVariationOf(CARDIGANS_ANTIDOTE_YOURSELF)
                    .partsRequired(StandPart.ARMS)
                    .cooldown(100)
                    .holdToFire(20, false)
            ));
    
    

    // ...then create the Stand type instance. Moves, stats, entity sizes, and a few other things are determined here.
    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<CardigansEntity>> STAND_CARDIGANS =
            new EntityStandRegistryObject<>("cardigans",
                    STANDS, 
                    () -> new EntityStandType.Builder<StandStats>()
                    .color(0x00AFAF)
                    .storyPartName(InitStands.PART_7SU_NAME)
                    .leftClickHotbar(
                            CARDIGANS_PUNCH.get(),
                            CARDIGANS_BARRAGE.get()
                            )
                    .rightClickHotbar(
                            CARDIGANS_BLOCK.get(),
                            CARDIGANS_ANTIDOTE_YOURSELF.get(),
                            CARDIGANS_HEAL_YOURSELF.get()
                            )
                    .defaultStats(StandStats.class, new StandStats.Builder()
                            .tier(6)
                            .power(20)
                            .speed(20)
                            .range(50, 100)
                            .durability(20)
                            .precision(20)
                            .build())
                    .addSummonShout(InitSounds.CARDIGANS_SUMMON_VOICELINE)
                    .addOst(InitSounds.CARDIGANS_OST)
                    .build(),
                    
                    InitEntities.ENTITIES,
                    () -> new StandEntityType<CardigansEntity>(CardigansEntity::new, 0.7F, 2.1F)
                    .summonSound(InitSounds.CARDIGANS_SUMMON_SOUND)
                    .unsummonSound(InitSounds.CARDIGANS_UNSUMMON_SOUND))
            .withDefaultStandAttributes();



    // ======================================== ??? ========================================
    
    
    
}
