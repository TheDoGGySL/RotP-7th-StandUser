package com.thedoggys.rotp_7su.item;

import com.thedoggys.rotp_7su.init.InitSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BandageItem extends Item {
    public BandageItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getHealth() != player.getMaxHealth()) {
            if (!world.isClientSide()) {
                this.useStone(world, player, stack);
            }
            return ActionResult.success(stack);
        }
        player.startUsingItem(hand);
        return ActionResult.consume(stack);
    }

    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entity) {
        this.useStone(world, entity, stack);
        return stack;
    }

    protected void useStone(World world, LivingEntity entity, ItemStack itemStack) {
        entity.playSound(InitSounds.BANDAGE_USE.get(), 1, 1);
        if (!world.isClientSide()) {
            entity.heal(2);
            itemStack.shrink(1);
        }
    }
}

