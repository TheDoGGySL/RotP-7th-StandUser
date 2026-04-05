package com.thedoggys.rotp_7su.entity;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.init.ModEntityTypes;
import com.thedoggys.rotp_7su.init.InitEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;

public class BulletEntity extends ModdedProjectileEntity {
    public final List<Vector3d> tracePos = new LinkedList<>();
    public Vector3d initialPos;

    public BulletEntity(LivingEntity shooter, World world) {
        super(InitEntities.BULLET.get(), shooter, world);
    }

    public BulletEntity(EntityType<? extends BulletEntity> type, World world) {
        super(type, world);
    }

    @Override
    public int ticksLifespan() {
        return 40;
    }

    @Override
    public float getBaseDamage() {
        return 2F;
    }

    @Override
    protected float getMaxHardnessBreakable() {
        return 0.3F;
    }

    @Override
    public boolean standDamage() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (tickCount == 5) {
            setNoGravity(false);
        }
        if (level.isClientSide()) {
            Vector3d pos = position();
            boolean addPos = true;
            if (tracePos.size() > 1) {
                Vector3d lastPos = tracePos.get(tracePos.size() - 1);
                addPos &= pos.distanceToSqr(lastPos) >= 0.0625;
            }
            if (addPos) {
                tracePos.add(pos);
            }
        }
    }

    @Override
    protected double getGravityAcceleration() {
        return 1.5;
    }


    @Override
    public boolean hasDeflectedVisuals() {
        return true;
    }

    @Override
    public void setIsDeflected(Vector3d deflectVec, Vector3d deflectPos) {
        super.setIsDeflected(deflectVec, deflectPos);
        if (level.isClientSide()) {
            setDeltaMovement(deflectVec);
            tracePos.add(deflectPos);
        }
    }


    protected boolean blockDestroyed;
    @Override
    protected void afterBlockHit(BlockRayTraceResult blockRayTraceResult, boolean blockDestroyed) {
        this.blockDestroyed = blockDestroyed;
        if (blockDestroyed) {
            if (!level.isClientSide()) {
                setDeltaMovement(getDeltaMovement().scale(0.9));
                checkHit();
            }
        }
        else {
            BlockPos blockPos = blockRayTraceResult.getBlockPos();
            BlockState blockState = level.getBlockState(blockPos);
            blockSound(blockPos, blockState);
        }
    }

    private void blockSound(BlockPos blockPos, BlockState blockState) {
        SoundType soundType = blockState.getSoundType(level, blockPos, this);
//        level.playSound(null, blockPos, soundType.getHitSound(), SoundCategory.BLOCKS, 1.0F, soundType.getPitch() * 0.5F);
    }

    @Override
    protected void breakProjectile(ActionTarget.TargetType targetType, RayTraceResult hitTarget) {
        if (!(targetType == ActionTarget.TargetType.BLOCK && blockDestroyed)) {
            super.breakProjectile(targetType, hitTarget);
        }
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        super.readSpawnData(additionalData);
        initialPos = position();
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }
}
