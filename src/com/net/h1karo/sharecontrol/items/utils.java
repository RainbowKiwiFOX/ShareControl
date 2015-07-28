package com.net.h1karo.sharecontrol.items;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class utils {
	public static ItemStack setMeta(ItemStack material, String name, List<String> lore)
	{
		if(material==null || material.getType() == Material.AIR || (name == null && lore == null))
			return null;
		
		ItemMeta im = material.getItemMeta();
		if(name != null)
			im.setDisplayName(name);
		if(lore != null)
			im.setLore(lore);
		
		im.addEnchant(Enchantment.DURABILITY, 5, true);
		
		material.setItemMeta(im);		
		return material;
	}
}
