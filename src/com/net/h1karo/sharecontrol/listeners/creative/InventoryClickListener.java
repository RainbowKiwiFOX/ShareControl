package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;
import com.net.h1karo.sharecontrol.localization.Localization;

public class InventoryClickListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public InventoryClickListener(ShareControl h)
	{
		this.main = h;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void InventoryClick(InventoryClickEvent e)
	{
		Player p = (Player) e.getWhoClicked();
		if(p.getGameMode() != GameMode.CREATIVE || Permissions.perms(p, "allow.blocking-inventory") || Configuration.BlockingItemsInvList.toArray().length == 0 || Configuration.BlockingItemsInvList.get(0).toString().compareToIgnoreCase("[none]") == 0) return;
		for(int i=0; i < Configuration.BlockingItemsInvList.toArray().length; i++)
		{
			Material typeThisItem = e.getCursor().getType();
			String StrListItem = (String) Configuration.BlockingItemsInvList.toArray()[i];
			Material typeListItem;
			
			BasicHandlers.isInteger(StrListItem);
			
			if(BasicHandlers.ifInt)
			{
				String NewStr = StrListItem.replace("'", "");
				int ID = Integer.parseInt(NewStr);
				typeListItem = Material.getMaterial(ID);
			}
			else
				typeListItem = Material.getMaterial(StrListItem);
			
			if(typeThisItem == typeListItem)
			{
				Localization.invNotify(typeThisItem, p);
				e.setCancelled(true);
			}
		}
	}
}
