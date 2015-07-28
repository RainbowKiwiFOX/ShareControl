package com.net.h1karo.sharecontrol.listeners.creative;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerCommandPreprocessListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public PlayerCommandPreprocessListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void PlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		if(!Configuration.BlockingCmdsEnabled || Permissions.perms(e.getPlayer(), "allow.commands")) return;
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			List<String> list = Configuration.BlockingCmdsList;
			for(int i=0; i < list.size(); i++) {
				String cmd = "/" + list.get(i);
				if(e.getMessage().toLowerCase().contains(cmd)) {
					e.setCancelled(true);
					Localization.ProhibitedCmd(e.getPlayer());
					return;
				}
			}
		}
	}
}
