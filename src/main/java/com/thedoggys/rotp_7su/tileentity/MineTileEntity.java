package com.thedoggys.rotp_7su.tileentity;

import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandPower;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.init.InitTileEntities;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.datafix.fixes.RedstoneConnections;
import net.minecraft.world.Explosion;

public class MineTileEntity extends TileEntity implements ITickableTileEntity {
    private LivingEntity user = null;
    private int activationTicks;
    private boolean active = false;

    public MineTileEntity() {
        super(InitTileEntities.MINE_TE.get());
    }

    protected MineTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public void setUser(LivingEntity user) {
        this.user = user;
    }

    public LivingEntity getUser() {
        return this.user;
    }

    @Override
    public void tick() {
        if (level != null && !level.isClientSide()) {
            if (user == null || !user.isAlive()) {
                    level.removeBlock(getBlockPos(), false);
            } else {
                if (user instanceof PlayerEntity) {
                    IStandPower.getStandPowerOptional(user).ifPresent(power -> {
                        if (power.hasPower()) {
                            StandPower standPower = (StandPower) IStandPower.getPlayerStandPower((PlayerEntity) user);
                            if (standPower.getStamina() < 0.1 * standPower.getMaxStamina()) {
                                level.removeBlock(getBlockPos(), false);
                            }
                        }
                    });
                }
            }
        }
        activationTicks--;
        setChanged();
        if (activationTicks == 0) {
            if (level != null && !level.isClientSide()) {
                level.explode(this.user, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(),2, Explosion.Mode.NONE);
                level.removeBlock(getBlockPos(), false);
            }
        }
    }

    public void activate(boolean instant) {
        setChanged();
        if (!active && level != null)
            level.playSound(null, this.getBlockPos(), SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 1, 1);
        this.activationTicks = 10;
        this.active = true;
        if (level != null && !level.isClientSide() && instant) {
            level.explode(this.user, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(),2, Explosion.Mode.NONE);
            level.removeBlock(getBlockPos(), false);
        }
    }
}
