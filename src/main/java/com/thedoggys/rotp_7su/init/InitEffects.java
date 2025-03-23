package com.thedoggys.rotp_7su.init;

import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.effect.AnastesiaEffect;
import com.thedoggys.rotp_7su.effect.DopingEffect;
import com.thedoggys.rotp_7su.effect.ShockedEffect;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEffects {
public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(
        ForgeRegistries.POTIONS, AddonMain.MOD_ID);
    public static final RegistryObject<Effect> ANASTESIA_EFFECT = EFFECTS.register("anasthesia",
            () -> new AnastesiaEffect(0xFEFCFF));

    public static final RegistryObject<Effect> DOPING_EFFECT = EFFECTS.register("doping",
            () -> new DopingEffect(0xA39F09).addAttributeModifier(Attributes.MOVEMENT_SPEED, "6ed2d177-af97-423c-84f5-1f80c364639f", (double)+0.3f, AttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(Attributes.ATTACK_SPEED, "c29a3b4b-5a3f-4b5a-9b9a-7b7c3d3d3d3d", (double)+0.3f, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> SHOCKED = EFFECTS.register("shocked",
            () -> new ShockedEffect(0xFFEB81).addAttributeModifier(Attributes.MOVEMENT_SPEED, "6ed2d177-af97-423c-84f5-1f80c364639f", (double)-0.3f, AttributeModifier.Operation.MULTIPLY_TOTAL));
}
