package com.thedoggys.rotp_7su.init;

import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.block.MineBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS, AddonMain.MOD_ID);

    public static final RegistryObject<Block> MINE_BLOCK = BLOCKS.register("mine_block",
            () -> new MineBlock(Block.Properties.of(Material.METAL).strength(0.5F)));
}
