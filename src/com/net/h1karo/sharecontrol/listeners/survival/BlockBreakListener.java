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


package com.net.h1karo.sharecontrol.listeners.survival;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;
import com.net.h1karo.sharecontrol.localization.Localization;

public class BlockBreakListener implements Listener {
	
	@SuppressWarnings("unused")
	private final ShareControl main;
	public BlockBreakListener(ShareControl h)
	{
		this.main = h;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void OnBreak(BlockBreakEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE) return;
		Block b = e.getBlock();

		if(b.getType() == Material.PISTON_EXTENSION) {
			World w = b.getWorld();
			if(!BasicHandlers.InBase(b)) return;
			if(b.getData() == 13) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX() - 1, b.getY(), b.getZ());
				ClearPiston(piston, e);
				ClearBlock(b, p, e);
			}

			if(b.getData() == 12) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX() + 1, b.getY(), b.getZ());
				ClearPiston(piston, e);
				ClearBlock(b, p, e);
			}

			if(b.getData() == 11) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX(), b.getY(), b.getZ() - 1);
				ClearPiston(piston, e);
				ClearBlock(b, p, e);
			}

			if(b.getData() == 10) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
				ClearPiston(piston, e);
				ClearBlock(b, p, e);
			}
			return;
		}
		
		ClearBlock(b, p, e);
	}
	
	public void ClearBlock(Block b, Player p, BlockBreakEvent e) {
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		
		if(Database.getBlockBase().get(x + "." + y + "." + z) != null && BasicHandlers.checkSameness(b)) {
			e.setCancelled(true);
			if(!Configuration.BlockingBreak)
			{
				b.setType(Material.AIR);
				Localization.SurvivalBlockNotDrop(p);
			}
			else
			{
				Localization.SurvivalBlockNotBreak(p);
				return;
			}
				
			Database.getBlockBase().set(x + "." + y + "." + z, null);
			Database.saveBlockBase();
			return;
		}
	}
	
	public void ClearPiston(Block b, BlockBreakEvent e) {
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		
		if(Database.getBlockBase().get(x + "." + y + "." + z) != null && BasicHandlers.checkSameness(b)){
			e.setCancelled(true);
			if(!Configuration.BlockingBreak)
				b.setType(Material.AIR);
			else
				return;
			
			Database.getBlockBase().set(x + "." + y + "." + z, null);
			Database.saveBlockBase();
			return;
		}
	}
}
