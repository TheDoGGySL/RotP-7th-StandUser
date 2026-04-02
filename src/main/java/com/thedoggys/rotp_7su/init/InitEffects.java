package com.thedoggys.rotp_7su.init;

import com.github.standobyte.jojo.action.non_stand.HamonHealing;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.potion.HamonShockEffect;
import com.github.standobyte.jojo.potion.StunEffect;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.effect.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InitEffects {
public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(
        ForgeRegistries.POTIONS, AddonMain.MOD_ID);
    public static final RegistryObject<Effect> ANASTESIA_EFFECT = EFFECTS.register("anasthesia",
            () -> new AnastesiaEffect(0xFEFCFF));

    public static final RegistryObject<Effect> SIZZLE_EFFECT = EFFECTS.register("sizzle",
            () -> new SizzleEffect(0xFEFCFF));

    public static final RegistryObject<Effect> DOPING_EFFECT = EFFECTS.register("doping",
            () -> new DopingEffect(0xA39F09).addAttributeModifier(Attributes.MOVEMENT_SPEED, "6ed2d177-af97-423c-84f5-1f80c364639f", (double)+0.3f, AttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(Attributes.ATTACK_SPEED, "c29a3b4b-5a3f-4b5a-9b9a-7b7c3d3d3d3d", (double)+0.3f, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<StunEffect> SHOCKED = EFFECTS.register("shocked",
            () -> new ShockedEffect(0xFFEB81).setUncurable());

    public static final RegistryObject<Effect> INTIMIDATED = EFFECTS.register("intimidated",
            () -> new IntimidatedEffect(0xFF000F).addAttributeModifier(Attributes.ATTACK_DAMAGE, "c29a3b4b-5a3f-4b5a-9b9a-7b7c3d3d3d3d", (double)-0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));

    private static final Set<Effect> TRACKED_EFFECTS = new HashSet<>();
    public static void afterEffectsRegister() {
        StandEntity.addSharedEffectsFromStand(SHOCKED.get());
        setEffectAsTracked(
                SHOCKED.get());
    }

    // Makes it so that the effect is also sent to the surrounding players, in case it is needed for visuals
    public static void setEffectAsTracked(Effect... effects) {
        Collections.addAll(TRACKED_EFFECTS, effects);
    }

    public static boolean isEffectTracked(Effect effect) {
        return TRACKED_EFFECTS.contains(effect);
    }

    public static boolean isShocked(LivingEntity entity) {
        return entity.hasEffect(SHOCKED.get());
    }
}
