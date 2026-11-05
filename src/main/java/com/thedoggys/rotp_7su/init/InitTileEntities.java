package com.thedoggys.rotp_7su.init;

import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.tileentity.MineTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(
            ForgeRegistries.TILE_ENTITIES, AddonMain.MOD_ID);

    public static final RegistryObject<TileEntityType<MineTileEntity>> MINE_TE = TILE_ENTITIES.register("mine_block",
            () -> TileEntityType.Builder.of(MineTileEntity::new, InitBlocks.MINE_BLOCK.get()).build(null));

}
