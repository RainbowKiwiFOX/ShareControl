package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerFishListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public PlayerFishListener(ShareControl h)
	{
		this.main = h;
	}

	@EventHandler
	public void PlayerFish(PlayerFishEvent e) {
		Player p = e.getPlayer();
		if(p.getGameMode() != GameMode.CREATIVE || Permissions.perms(p, "allow.fishing")) return;
		Localization.Fishing(p);
		e.setCancelled(true);
	}
}
