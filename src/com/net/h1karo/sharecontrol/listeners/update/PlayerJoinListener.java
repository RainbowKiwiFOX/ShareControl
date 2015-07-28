package com.net.h1karo.sharecontrol.listeners.update;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.ShareControl.UpdateResult;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerJoinListener implements Listener
{
	private final ShareControl main;
	boolean ifInt;
	
	public PlayerJoinListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e)
	{
		if(Configuration.versionCheck && main.result == UpdateResult.UPDATE_AVAILABLE)
			Localization.UpdateFoundPlayer(e.getPlayer());
	}
}
