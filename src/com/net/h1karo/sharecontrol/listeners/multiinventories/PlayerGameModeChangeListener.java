/*******************************************************************************
 * Copyright (C) 2015 H1KaRo (h1karo)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


package com.net.h1karo.sharecontrol.listeners.multiinventories;

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
		if(Permissions.perms(p, "allow.multi-inventories") || e.isCancelled() || !Configuration.MultiInventoriesEnabled) return;
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
