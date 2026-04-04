package com.thedoggys.rotp_7su.client;

import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.reflection.ClientReflection;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.capability.LivingDataProvider;
import com.thedoggys.rotp_7su.init.InitEffects;
import com.thedoggys.rotp_7su.init.power.InitStands;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Timer;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AddonMain.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    private final Minecraft mc;
    private Timer clientTimer;
    private boolean modPostedEvent = false;
    private static ClientEvents instance = null;
    private static boolean isMiraclesActive(PlayerEntity player) {
        return IStandPower.getStandPowerOptional(player)
                .resolve()
                .map(power -> power.getType() == InitStands.STAND_MIRACLES.getStandType())
                .orElse(false);
    }
    private ClientEvents(Minecraft mc) {
        this.mc = mc;
        this.clientTimer = ClientReflection.getTimer(mc);
    }

    public static void init(Minecraft mc) {
        if (instance == null) {
            instance = new ClientEvents(mc);
            MinecraftForge.EVENT_BUS.register(instance);
        }
    }

    @SubscribeEvent
    public void onPreRender(RenderLivingEvent.Pre event) {
        if (event.getEntity().hasEffect(InitEffects.SHOCKED.get())) {
            event.getMatrixStack().pushPose();
            event.getMatrixStack().mulPose(Vector3f.YP.rotationDegrees((float) (Math.cos((double) event.getEntity().tickCount * 7F) * Math.PI * (double) 1.2F)));
            float vibrate = 0.05F;
            event.getMatrixStack().translate((event.getEntity().getRandom().nextFloat() - 0.5F) * vibrate, (event.getEntity().getRandom().nextFloat() - 0.5F) * vibrate, (event.getEntity().getRandom().nextFloat() - 0.5F) * vibrate);
        }
    }

    @SubscribeEvent
    public void onPostRender(RenderLivingEvent.Post event) {
        if (event.getEntity().hasEffect(InitEffects.SHOCKED.get())) {
            event.getMatrixStack().popPose();
        }
    }

    @SubscribeEvent
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
        Entity cameraEntity = mc.getCameraEntity();
        if (cameraEntity instanceof LivingEntity) {
            LivingEntity livingCameraEntity = (LivingEntity) cameraEntity;
            if (livingCameraEntity.hasEffect(InitEffects.SHOCKED.get())) {
                event.setRoll((float) (Math.cos((double) cameraEntity.getEntity().tickCount * 7F) * Math.PI * (double) 1.2F));
            }
        }
    }
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        PlayerEntity player = event.getPlayer();
        if (player == null) return;
        if (Minecraft.getInstance().player == player) return;

        player.getCapability(LivingDataProvider.CAPABILITY).ifPresent(data -> {
            if (data.isGentlyWeeps() && isMiraclesActive(player)) {
                event.setCanceled(true);
            }
        });
    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.ClientTickEvent.Phase.END) return;
        PlayerEntity player = net.minecraft.client.Minecraft.getInstance().player;
        if (player != null && player.hasEffect(InitEffects.SIZZLE_EFFECT.get())) {
            if (player.getRemainingFireTicks()<= 0) {
                player.setRemainingFireTicks(10);
            }
        }
    }
}
