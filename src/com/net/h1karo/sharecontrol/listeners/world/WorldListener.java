package com.net.h1karo.sharecontrol.listeners.world;

import org.bukkit.GameMode;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;

public class WorldListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public WorldListener(ShareControl h)
	{
		this.main = h;
	}
    
	@EventHandler(priority = EventPriority.HIGH)
	public void onBreakWorld(BlockBreakEvent e)
	{
		if(e.isCancelled()) return;
		
		if(!Configuration.WorldsCfgEnabled) return;
		
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE)
		{
			for(int i=0; i < Configuration.BlockingCreative.toArray().length; i++)
			{
				if(Permissions.perms(p, "allow.blocking-creative") || Configuration.BlockingCreative.toArray()[i].toString() == "[none]")	return;
				String ListWorld = Configuration.BlockingCreative.toArray()[i].toString();
				if(e.getBlock().getLocation().getWorld().getName().toString().compareToIgnoreCase(ListWorld) == 0)
				{
					e.setCancelled(true);
					p.setGameMode(GameMode.SURVIVAL);
					
					Localization.InBlockWorld(p);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlaceWorld(BlockPlaceEvent e)
	{
		if(e.isCancelled()) return;
		
		if(!Configuration.WorldsCfgEnabled) return;
		
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE)
		{
			for(int i=0; i < Configuration.BlockingCreative.toArray().length; i++)
			{
				if(Permissions.perms(p, "allow.blocking-creative") || Configuration.BlockingCreative.toArray()[i].toString() == "[none]")	return;
				String ListWorld = Configuration.BlockingCreative.toArray()[i].toString();
				if(e.getBlock().getLocation().getWorld().getName().toString().compareToIgnoreCase(ListWorld) == 0)
				{
					e.setCancelled(true);
					p.setGameMode(GameMode.SURVIVAL);
					
					Localization.InBlockWorld(p);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onInteractWorld(EntityDamageByEntityEvent e)
	{
		if(e.isCancelled()) return;
		
		if(!Configuration.WorldsCfgEnabled) return;
		
		Entity entDamager = e.getDamager();
		Entity entDamage = e.getEntity();
		if(!(entDamager instanceof Player) || (!(entDamage instanceof Creature) && !(entDamage.toString() == "CraftSlime"))) return;
		Player p = (Player) e.getDamager();
		if(p.getGameMode() == GameMode.CREATIVE)
		{
			for(int i=0; i < Configuration.BlockingCreative.toArray().length; i++)
			{
				if(Permissions.perms(p, "allow.blocking-creative") || Configuration.BlockingCreative.toArray()[i].toString() == "[none]")	return;
				String ListWorld = Configuration.BlockingCreative.toArray()[i].toString();
				if(e.getDamager().getLocation().getWorld().getName().toString().compareToIgnoreCase(ListWorld) == 0)
				{
					e.setCancelled(true);
					p.setGameMode(GameMode.SURVIVAL);
					
					Localization.InBlockWorld(p);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void ChangeWorld(PlayerChangedWorldEvent e)
	{		
		Player p = e.getPlayer();
		if(!(Configuration.WorldsCfgEnabled || p.getGameMode() == GameMode.CREATIVE)) return;
		
		for(int i=0; i < Configuration.BlockingCreative.toArray().length; i++)
		{
			if(Permissions.perms(p, "allow.blocking-creative") || Configuration.BlockingCreative.toArray()[i].toString() == "[none]")	return;
			String ListWorld = Configuration.BlockingCreative.toArray()[i].toString();
			if(p.getLocation().getWorld().getName().toString().compareToIgnoreCase(ListWorld) == 0) p.setGameMode(GameMode.SURVIVAL);
		}
	}
}
