package com.thedoggys.rotp_7su.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.init.InitBlocks;
import com.thedoggys.rotp_7su.init.power.InitStandEffects;
import com.thedoggys.rotp_7su.network.AddonPackets;
import com.thedoggys.rotp_7su.network.packets.client.PlayerFormationPacket;
import com.thedoggys.rotp_7su.specials.SpecialsEntities;
import com.thedoggys.rotp_7su.tileentity.MineTileEntity;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.github.standobyte.jojo.action.ActionTarget.fromRayTraceResult;
import static com.thedoggys.rotp_7su.block.MineBlock.FACING;
import static net.minecraft.util.math.RayTraceResult.Type.BLOCK;

public class SpecialsPurpleMine extends StandEntityAction {
    public SpecialsPurpleMine(Builder builder) {
        super(builder);
    }

    @Override
    public void onClick(World world, LivingEntity user, IStandPower power) {
        SpecialsEntities specialsEntities = IStandPower.getPlayerStandPower((PlayerEntity) user).getContinuousEffects().getOrCreateEffect(InitStandEffects.SPECIALS_SUMMONED_ENTITIES.get());
        specialsEntities.pickEntity(5);
        super.onClick(world, user, power);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        World world = user.level;
        BlockPos blockPos = target.getBlockPos();
        if (power.getStamina() < 0.1 * power.getMaxStamina()) {
            return conditionMessage("low_stamina");
        }
        if (world.getBlockEntity(blockPos) instanceof MineTileEntity) {
            return ActionConditionResult.POSITIVE;
        } else {
            BlockState state = world.getBlockState(blockPos);
            Direction face = target.getFace();
            BlockPos minePos = blockPos.relative(face);
            if (face != Direction.UP)
                return conditionMessage("need_floor");
            if ((world.getBlockState(minePos).getBlock() instanceof AirBlock
                    || world.getBlockState(minePos).getMaterial().isReplaceable()) && state.isFaceSturdy(world, blockPos, face)) {
                return ActionConditionResult.POSITIVE;
            }
        }
        return ActionConditionResult.NEGATIVE;
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target, @Nullable PacketBuffer extraInput) {
        if (!world.isClientSide()) {
            BlockPos blockPos = target.getBlockPos();
            World level = power.getUser().level;
            if (level != null) {
                if (level.getBlockEntity(blockPos) instanceof MineTileEntity) {
                    level.removeBlock(blockPos, false);
                } else {
                    Direction face = target.getFace();
                    BlockPos minePos = blockPos.relative(face);
                    BlockState mineState = InitBlocks.MINE_BLOCK.get().defaultBlockState().setValue(FACING, face);

                    world.setBlockAndUpdate(minePos, mineState);
                    TileEntity te = world.getBlockEntity(minePos);
                    if (te instanceof MineTileEntity) {
                        MineTileEntity mine = (MineTileEntity) te;
                        mine.setUser(user);
                        mine.setChanged();
                        world.sendBlockUpdated(minePos, mineState, mineState, 3);
                    }
                }
            }
        }
    }

    @Override
    public void renderActionIcon(MatrixStack matrixStack, IStandPower power, float x, float y) {
        Minecraft mc = Minecraft.getInstance();
        RayTraceResult rayTrace = JojoModUtil.rayTrace(power.getUser(), 4,
                entity -> entity instanceof LivingEntity && !(entity instanceof StandEntity && ((StandEntity) entity).getUser() == power.getUser()));
        if (rayTrace.getType() == BLOCK) {
            BlockPos pos = fromRayTraceResult(rayTrace).getBlockPos();
            World level = power.getUser().level;
            if (level != null && level.getBlockEntity(pos) instanceof MineTileEntity) {
                ResourceLocation tex = new ResourceLocation(AddonMain.MOD_ID, "textures/power/sp6.png");
                mc.getTextureManager().bind(tex);
                AbstractGui.blit(matrixStack, (int) x, (int) y, 16, 16, 0, 0, 16, 16, 16, 16);
            } else
                super.renderActionIcon(matrixStack, power, x, y);
        } else
            super.renderActionIcon(matrixStack, power, x, y);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.BLOCK;
    }
}
