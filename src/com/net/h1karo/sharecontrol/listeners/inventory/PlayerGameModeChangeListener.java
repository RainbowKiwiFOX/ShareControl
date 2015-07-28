package com.net.h1karo.sharecontrol.listeners.inventory;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.PlayerInventory;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
public class PlayerGameModeChangeListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;
	public PlayerGameModeChangeListener(ShareControl h)
	{
		this.main = h;
	}
	

	
	public static boolean joinMA = false;
	public static boolean leaveMA = false;
	public static boolean joinPVP = false;
	public static boolean leavePVP = false;
	
	@EventHandler
	public void ChangeGameMode(PlayerGameModeChangeEvent e)
	{
		Player p = e.getPlayer();
		if(Permissions.perms(p, "allow.inventory") || e.isCancelled()) return;
		if(!Configuration.InventorySeparation)
		{ 	Handlers.clear(p);
			return;		}
		
		PlayerInventory Inventory = p.getInventory();
		
		if(joinMA || joinPVP) {
			if(p.getGameMode() == GameMode.SURVIVAL)
			{
				Handlers.SaveSurvival(p, Inventory);
			}
			if(p.getGameMode() == GameMode.ADVENTURE)
			{
				Handlers.SaveAdventure(p, Inventory);
			}
			
			if(joinMA) joinMA = false;
			if(joinPVP) joinPVP = false;
			return;
		}
		
		if(leaveMA || leavePVP) {
			if(leaveMA) leaveMA = false;
			if(leavePVP) leavePVP = false;
			return;
		}
		Handlers.BasicHandler(Inventory, p, e.getNewGameMode(), e.getPlayer().getGameMode());
	}
}
