package com.thedoggys.rotp_7su.mixin;

import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.entity.SpecialsEntity;
import com.thedoggys.rotp_7su.init.power.InitStandEffects;
import com.thedoggys.rotp_7su.specials.SpecialsEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(value = StandType.class, remap = false)
public abstract class StandTypeMixin {

    @Unique
    private static final ResourceLocation[] rotp7suAddon_main$specialsFormTextures = {
            new ResourceLocation(AddonMain.MOD_ID, "textures/power/sp1.png"),
            new ResourceLocation(AddonMain.MOD_ID, "textures/power/sp2.png"),
            new ResourceLocation(AddonMain.MOD_ID, "textures/power/sp3.png"),
            new ResourceLocation(AddonMain.MOD_ID, "textures/power/sp4.png"),
            new ResourceLocation(AddonMain.MOD_ID, "textures/power/sp5.png"),
            new ResourceLocation(AddonMain.MOD_ID, "textures/power/sp6.png")
    };

    @Inject(method = "getIconTexture(Lcom/github/standobyte/jojo/power/impl/stand/IStandPower;)Lnet/minecraft/util/ResourceLocation;", at = @At("RETURN"), cancellable = true)
    private void onGetIconTexture(@Nullable IStandPower power, CallbackInfoReturnable<ResourceLocation> cir) {
        if (power != null) {
            SpecialsEntities specials = power.getContinuousEffects().getOrCreateEffect(InitStandEffects.SPECIALS_SUMMONED_ENTITIES.get());
            int act = specials.getSpecialsPickedEntity();
            System.out.println("sosal: " + act);
            if (act >= 0 && act < rotp7suAddon_main$specialsFormTextures.length) {
                cir.setReturnValue(rotp7suAddon_main$specialsFormTextures[act]);
            }
        }
    }
}