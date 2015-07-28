package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.localization.Localization;

public class InventoryOpenListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public InventoryOpenListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void InventoryOpen(InventoryOpenEvent e)
	{
		Player p = (Player) e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.blocking-interact"))
		{
			boolean ifVehicle;
			if(p.getVehicle() != null)
				if(p.getVehicle().getType() == EntityType.HORSE)
					ifVehicle = true;
				else ifVehicle = false;
			else ifVehicle = false;
			
			if(!((e.getInventory().getType() == InventoryType.CHEST && ifVehicle) || e.getInventory().getName().compareToIgnoreCase("container.minecart") == 0) && e.getInventory().getType() == InventoryType.CHEST) return;
				e.setCancelled(true);
				Localization.openInv(p);
		}
	}
}
