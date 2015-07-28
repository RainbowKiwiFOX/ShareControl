package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;

public class PlayerLeashEntityListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public PlayerLeashEntityListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerLeash(PlayerLeashEntityEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.creature-interact"))
		{
			e.setCancelled(true);
			return;
		}
	}
}
