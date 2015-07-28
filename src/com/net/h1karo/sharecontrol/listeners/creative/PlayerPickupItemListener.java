package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;

public class PlayerPickupItemListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public PlayerPickupItemListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent e)
	{
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.pickup"))
		{
			e.setCancelled(true);
		}
	}
}
