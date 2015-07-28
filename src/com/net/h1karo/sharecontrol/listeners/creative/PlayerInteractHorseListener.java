package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitScheduler;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerInteractHorseListener implements Listener
{
	private final ShareControl main;
	
	public PlayerInteractHorseListener(ShareControl h)
	{
		this.main = h;
	}
	private boolean Interact = false;
	private Player player = null;
	
	@EventHandler
	public void InteractWithHorse(PlayerInteractEntityEvent e)
	{	
		Player p = (Player) e.getPlayer();
		if(e.getRightClicked().getType() == EntityType.HORSE && e.getPlayer().getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.blocking-inventory"))
		{
			Interact = true;
			player = e.getPlayer();
    		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	        scheduler.scheduleSyncDelayedTask(main, new Runnable() {
	            @Override
	            public void run() { Interact = false; }}, 30L);
		}	
	}
	
	@EventHandler
	public void InteractWithChest(InventoryOpenEvent e)
	{	
		Player p = (Player) e.getPlayer();
		if(player != null)
			if(e.getInventory().getType() == InventoryType.CHEST && Interact && player == e.getPlayer() && e.getPlayer().getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.blocking-inventory"))
			{
				e.setCancelled(true);
    			Localization.openInv(p);
			}	
	}
}
