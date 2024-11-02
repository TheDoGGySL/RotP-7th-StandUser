package com.thedoggys.rotp_7su.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;


public class BandageItem extends Item {
    public BandageItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (player.getHealth() < 20) {
            player.heal(2);
            player.addEffect(new EffectInstance(Effects.REGENERATION, 100, 1, false, false));
            item.shrink(1);
            player.getCooldowns().addCooldown(this, getCooldown());
            return ActionResult.consume(item);
        }
        return ActionResult.fail(item);
    }
    protected int getCooldown() {
        return 250;
    }
}

