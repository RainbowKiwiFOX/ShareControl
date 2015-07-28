package com.net.h1karo.sharecontrol.listeners.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.items.utils;
import com.net.h1karo.sharecontrol.localization.LanguageFiles;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerInteractEntityListener implements Listener {
	
	@SuppressWarnings("unused")
	private final ShareControl main;
	public PlayerInteractEntityListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void InfotoolInteractWithPlayer(PlayerInteractEntityEvent e)
	{
		Player p = e.getPlayer();
		if(!(p instanceof Player) || !(e.getRightClicked() instanceof Player)) return;
		
		String nameIT = ChatColor.translateAlternateColorCodes('&', LanguageFiles.nameinfotool);
		String loreStr1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreIT1);
		String loreStr2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreIT2);
		List<String> loreIT = Arrays.asList(loreStr1, loreStr2);
		
		ItemStack infotool = utils.setMeta(new ItemStack(Material.BLAZE_POWDER), nameIT, loreIT);
		
		if(p.getItemInHand() == null || p.getItemInHand() == null || p.getItemInHand().getType() != infotool.getType() || p.getItemInHand().getItemMeta().getDisplayName() == null) return;
		if(p.getItemInHand().getItemMeta().getDisplayName().compareToIgnoreCase(nameIT) == 0)
		{
			if(!Permissions.perms(p, "tools.infotool")) {
				Localization.NoPerms(p);
				return;
			}
			Player m = (Player) e.getRightClicked();
			Localization.PlayerInfo(p, m);
			e.setCancelled(true);
		}
	}
}
