package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;

public class PlayerLevelChangeListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public PlayerLevelChangeListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void PlayerLevelChange(PlayerLevelChangeEvent e)
	{
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.*"))
		{
			p.setExp(0);
			p.setLevel(0);
			return;
		}
	}
}
