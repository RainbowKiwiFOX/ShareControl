package com.net.h1karo.sharecontrol.listeners.gamemodescontrol;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerGameModeChangeListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;
	public PlayerGameModeChangeListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void ChangeGameMode(PlayerGameModeChangeEvent e)
	{
		if(!Configuration.FullGCEnabled || !Configuration.GamemodesControlEnabled || Permissions.perms(e.getPlayer(), "gamemodescontrol.*") || e.isCancelled()) return;
		if(e.getNewGameMode() == GameMode.CREATIVE && !Permissions.perms(e.getPlayer(), "gamemodescontrol.creative")) {
			e.setCancelled(true);
			Localization.NotAllowedGamemode(e.getPlayer(), "creative");
		}
		
		if(e.getNewGameMode() == GameMode.SURVIVAL && !Permissions.perms(e.getPlayer(), "gamemodescontrol.survival")) {
			e.setCancelled(true);
			Localization.NotAllowedGamemode(e.getPlayer(), "survival");
		}
		
		if(e.getNewGameMode() == GameMode.ADVENTURE && !Permissions.perms(e.getPlayer(), "gamemodescontrol.adventure")) {
			e.setCancelled(true);
			Localization.NotAllowedGamemode(e.getPlayer(), "adventure");
		}
		
		if(e.getNewGameMode() == GameMode.SPECTATOR && !Permissions.perms(e.getPlayer(), "gamemodescontrol.spectator")) {
			e.setCancelled(true);
			Localization.NotAllowedGamemode(e.getPlayer(), "spectator");
		}
	}
}
