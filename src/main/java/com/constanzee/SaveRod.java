package com.constanzee;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveRod implements ModInitializer {
	public static final String MOD_ID = "save_rod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RegistryKey<ItemGroup> SAVE_ROD_ITEM_GROUP_KEY =
			RegistryKey.of(
					Registries.ITEM_GROUP.getKey(),
					Identifier.of(
							MOD_ID,
							"save_rod_item_group"
					)
			);

	public static final ItemGroup SAVE_ROD_ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(Items.BLAZE_ROD))
			.displayName(Text.of("Save Rod Items"))
			.build();

	public static Item saveRodItem;

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		Registry.register(Registries.ITEM_GROUP, SAVE_ROD_ITEM_GROUP_KEY, SAVE_ROD_ITEM_GROUP);

		saveRodItem = Registry.register(
				Registries.ITEM,
				Identifier.of(MOD_ID, "save_rod"),
				new SaveRodItem(new Item.Settings())
		);

		ItemGroupEvents.modifyEntriesEvent(SAVE_ROD_ITEM_GROUP_KEY).register( itemGroup -> {
			itemGroup.add(saveRodItem);
		});
	}
}