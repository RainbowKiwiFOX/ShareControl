package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerInteractEntityListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public PlayerInteractEntityListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void PlayerInteractCreature(PlayerInteractEntityEvent e)
	{
		Player p = e.getPlayer();
		Entity entDamage = e.getRightClicked();
		if((!(entDamage.toString() == "CraftVillager") || !(entDamage.toString() == "CraftSlime")) && p.getItemInHand().getType() != Material.SADDLE) return;
		if(p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.creature-interact"))
		{
			e.setCancelled(true);
			Localization.MonsterInteractNotify(p);
		}
	}
	
	@EventHandler
	public void PlayerInteractEntity(PlayerInteractEntityEvent e)
	{
		if(e.isCancelled()) return;
		Player p = (Player) e.getPlayer();
		if(Permissions.perms(p, "allow.blocking-inventory") || p.getGameMode() != GameMode.CREATIVE)	return;
		EntityType typeThisEntity = e.getRightClicked().getType();
		if(typeThisEntity == EntityType.ITEM_FRAME || typeThisEntity == EntityType.PAINTING)
		{
			Localization.EntityUseNotify(typeThisEntity, p);
			e.setCancelled(true);
		}
	}
}
