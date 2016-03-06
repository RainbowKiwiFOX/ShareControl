/*******************************************************************************
 * Copyright (C) 2016 H1KaRo (h1karo)
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

package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.localization.Localization;

public class BlockBreakListener implements Listener {
	
	@SuppressWarnings("unused")
	private final ShareControl main;
	public BlockBreakListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void CreativeBlockBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		if(p.getGameMode() != GameMode.CREATIVE || e.isCancelled()) return;
		Block b = e.getBlock();
		Database.RemoveBlock(b);
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void DisableBlockBreak(BlockBreakEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		if(Permissions.perms(p, "allow.blocking-breakage.*") || Configuration.BlockingBlocksBreakList.toArray().length == 0  || Configuration.BlockingBlocksBreakList.get(0).toString() == "[none]" || !(p.getGameMode() == GameMode.CREATIVE))	return;
		
		Material typeThisBlock = e.getBlock().getType();
		Material typeListBlock;
		
		for(int i=0; i < Configuration.BlockingBlocksBreakList.toArray().length; i++)
		{
			String StrListBlock = (String) Configuration.BlockingBlocksBreakList.toArray()[i];
			if(!Permissions.perms(p, "allow.blocking-breakage." + StrListBlock)) {
				if(Database.isInteger(StrListBlock))
				{
					String NewStr = StrListBlock.replace("'", "");
					int ID = Integer.parseInt(NewStr);
					typeListBlock = Material.getMaterial(ID);
				}
				else
					typeListBlock = Material.getMaterial(StrListBlock);
			
				if(typeThisBlock == typeListBlock)
				{
					Localization.BreakBlock(typeThisBlock, p);
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAutoBreak(BlockBreakEvent e)
	{
		if(e.isCancelled()) return;
		
		Block b = e.getBlock();
		Database.DropBlocks(b);
	}
}
