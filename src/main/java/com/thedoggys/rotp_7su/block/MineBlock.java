package com.thedoggys.rotp_7su.block;

import com.thedoggys.rotp_7su.entity.SpecialsEntity;
import com.thedoggys.rotp_7su.init.InitTileEntities;
import com.thedoggys.rotp_7su.tileentity.MineTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import static net.minecraft.block.HorizontalFaceBlock.FACE;

public class MineBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected static final VoxelShape FLOOR_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D);

    public MineBlock(AbstractBlock.Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return FLOOR_SHAPE;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if ((facing == state.getValue(FACING).getOpposite() && (facingState == Blocks.AIR.defaultBlockState() || !state.isFaceSturdy(world, facingPos, facing)))
                && !world.isClientSide() && world instanceof World) {
            TileEntity te = world.getBlockEntity(currentPos);
            if (te instanceof MineTileEntity) {
                ((MineTileEntity) te).activate(true);
            }
        }
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide()) {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof MineTileEntity) {
                ((MineTileEntity) te).activate(false);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClientSide()) {
            TileEntity te = world.getBlockEntity(pos);
            if (te instanceof MineTileEntity) {
                if (!(entity == ((MineTileEntity) te).getUser()
                    || (entity instanceof SpecialsEntity && ((SpecialsEntity) entity).getUser() == ((MineTileEntity) te).getUser())
                //    || entity.getTeam() != ((MineTileEntity) te).getUser().getTeam()
                )) ((MineTileEntity) te).activate(false);
            }
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return InitTileEntities.MINE_TE.get().create();
    }
}
