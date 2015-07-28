package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerShearEntityListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public PlayerShearEntityListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerShearEntity(PlayerShearEntityEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.creature-interact"))
		{
			e.setCancelled(true);
			Localization.MonsterInteractNotify(p);
			return;
		}
	}
}
