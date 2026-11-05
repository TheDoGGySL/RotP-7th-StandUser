package com.thedoggys.rotp_7su.tileentity;

import com.github.standobyte.jojo.init.ModSounds;
import com.thedoggys.rotp_7su.init.InitTileEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
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
        if (user == null) {
            if (level != null && !level.isClientSide()) {
                level.removeBlock(getBlockPos(), false);
            }
        }
        activationTicks--;
        setChanged();
        if (activationTicks == 0) {
            if (level != null && !level.isClientSide()) {
                level.explode(this.user, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(),3, Explosion.Mode.NONE);
                level.removeBlock(getBlockPos(), false);
            }
        }
    }

    public void activate(boolean instant) {
        setChanged();
        if (!active && level != null)
            level.playSound(null, this.getBlockPos(), ModSounds.TOMMY_GUN_NO_AMMO.get(), SoundCategory.BLOCKS, 1, 1);
        this.activationTicks = 10;
        this.active = true;
        if (level != null && !level.isClientSide() && instant) {
            level.explode(this.user, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(),3, Explosion.Mode.NONE);
            level.removeBlock(getBlockPos(), false);
        }
    }
}
