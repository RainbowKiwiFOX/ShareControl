package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;

public class PlayerDeathListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public PlayerDeathListener(ShareControl h)
	{
		this.main = h;
	}
	
	static ItemStack AIR = new ItemStack(Material.AIR, 1);

	@EventHandler
	public void PlayerDeath(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		if(p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.*"))
		{
			e.getDrops().clear();
			e.setDroppedExp(0);
			return;
		}
	}
}
