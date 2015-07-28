package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerItemConsumeListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	public PlayerItemConsumeListener(ShareControl h)
	{
		this.main = h;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerItemConsume(PlayerItemConsumeEvent e)
	{
		Player p = (Player) e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE)
		for(int i=0; i < Configuration.BlockingItemsInvList.toArray().length; i++)
		{
			if(Permissions.perms(p, "allow.blocking-inventory") || Configuration.BlockingItemsInvList.toArray()[i].toString() == "[none]" || Configuration.BlockingItemsInvList.toArray()[i].toString() == "[]")	return;
			
			Material typeThisItem = e.getItem().getType();
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
