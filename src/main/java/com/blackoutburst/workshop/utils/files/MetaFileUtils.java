package com.blackoutburst.workshop.utils.files;

import com.blackoutburst.workshop.core.game.MapMetadata;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MetaFileUtils {

	public static void save(File f, MapMetadata m) {
		try {
			YamlConfiguration metaFile = YamlConfiguration.loadConfiguration(f);

			List<ItemStack> armor = Arrays.stream(m.getArmorContents()).toList();
			ItemStack offhand = m.getOffHand();
			ItemStack icon = m.getIcon();

			metaFile.set("armor", armor);
			metaFile.set("offhand", offhand);
			metaFile.set("icon", icon);

			metaFile.save(f);
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static MapMetadata load(File f) {
		YamlConfiguration metaFile = YamlConfiguration.loadConfiguration(f);

		List<ItemStack> inv = new ArrayList<>(Collections.nCopies(36, new ItemStack(Material.AIR)));
		List<ItemStack> armor = new ArrayList<>(Collections.nCopies(4, new ItemStack(Material.AIR)));

		List<?> invTemp = metaFile.getList("inventory", inv);
		List<?> armorTemp = metaFile.getList("armor", armor);
		ItemStack offhand = metaFile.getItemStack("offhand");
		ItemStack icon = metaFile.getItemStack("icon");

		for (int i = 0; i < invTemp.size(); i++) {
			Object o = invTemp.get(i);
			if (o instanceof ItemStack item) {
				inv.set(i, item);
			}
		}
		for (int i = 0; i < armorTemp.size(); i++) {
			Object o = armorTemp.get(i);
			if (o instanceof ItemStack item) {
				armor.set(i, item);
			}
		}

		ItemStack[] invArray = inv.toArray(new ItemStack[] {});
		ItemStack[] armorArray = armor.toArray(new ItemStack[] {});

		return new MapMetadata(invArray,armorArray,offhand,icon);
	}

	public static ItemStack[] loadInventory(File f) {
		YamlConfiguration metaFile = YamlConfiguration.loadConfiguration(f);

		List<ItemStack> inv = new ArrayList<>(Collections.nCopies(36, new ItemStack(Material.AIR)));
		List<?> invTemp = metaFile.getList("inventory", inv);

		for (int i = 0; i < invTemp.size(); i++) {
			Object o = invTemp.get(i);
			if (o instanceof ItemStack item) {
				inv.set(i, item);
			}
		}

		return inv.toArray(new ItemStack[] {});
	}

	public static void saveInventory(File f, ItemStack[] inventory) {
		try {
			YamlConfiguration metaFile = YamlConfiguration.loadConfiguration(f);
			metaFile.set("inventory", Arrays.stream(inventory).toList());
			metaFile.save(f);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ItemStack getIcon(File f) {
		YamlConfiguration metaFile = YamlConfiguration.loadConfiguration(new File(f, "meta.yml"));
		ItemStack item = metaFile.getItemStack("icon");
		if (item != null) {
			return item;
		}
		return new ItemStack(Material.MAP);
	}

}
