package com.thedoggys.rotp_7su.init;

import com.github.standobyte.jojo.init.ModItems;
import com.thedoggys.rotp_7su.AddonMain;
import com.thedoggys.rotp_7su.item.BandageItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AddonMain.MOD_ID);

    public static final RegistryObject<BandageItem> BANDAGE_ITEM = ITEMS.register("bandage",
            ()->new BandageItem(new Item.Properties().tab(ModItems.MAIN_TAB).stacksTo(16)));
}
