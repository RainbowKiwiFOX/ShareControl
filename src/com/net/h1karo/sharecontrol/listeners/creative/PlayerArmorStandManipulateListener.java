package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerArmorStandManipulateListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public PlayerArmorStandManipulateListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void ArmorStand(PlayerArmorStandManipulateEvent e) {
		if(Permissions.perms(e.getPlayer(), "allow.blocking-interact") || e.isCancelled()) return;
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			e.setCancelled(true);
			Localization.ArmorStand(e.getPlayer());
		}
	}
}
