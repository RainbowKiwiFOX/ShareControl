package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;

public class EntityDamageByEntityListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public EntityDamageByEntityListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void CreativeDamageCreature(EntityDamageByEntityEvent e)
	{
		Entity player = e.getDamager();
		Entity entity = e.getEntity();
		if(!(player instanceof Player) || !(entity instanceof LivingEntity)) return;
		Player p = (Player) e.getDamager();
		if(p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.creature-interact") && Configuration.CreatureInteract)
		{
			e.setCancelled(true);
			Localization.MonsterInteractNotify(p);
		}
	}
	
	@EventHandler
	public void CreativeDamagePlayer(EntityDamageByEntityEvent e)
	{
		Entity entDamager = e.getDamager();
		Entity entDamage = e.getEntity();
		if(!(entDamager instanceof Player) || !(entDamage instanceof Player)) return;
		Player p = (Player) e.getDamager();
		if(p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.player-interact") && Configuration.PlayerInteract)
		{
			e.setCancelled(true);
			Localization.PlayerInteractNotify(p);
		}
	}
}
