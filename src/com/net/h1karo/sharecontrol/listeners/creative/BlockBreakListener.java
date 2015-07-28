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


package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;
import com.net.h1karo.sharecontrol.localization.Localization;

public class BlockBreakListener implements Listener {
	
	@SuppressWarnings("unused")
	private final ShareControl main;
	public BlockBreakListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void CreativeBlockBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		if(p.getGameMode() != GameMode.CREATIVE || e.isCancelled()) return;
		Block b = e.getBlock();
		BasicHandlers.RemoveofDatabase(b);
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
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
				BasicHandlers.isInteger(StrListBlock);
				
				if(BasicHandlers.ifInt)
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
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onAutoBreak(BlockBreakEvent e)
	{
		if(e.isCancelled()) return;
		Block b = e.getBlock();
		String[] DropBlocks = new String[3];
		BasicHandlers.upDropBlocksMore(DropBlocks);
		World w = e.getBlock().getWorld();
		for(int j = 256; j > b.getLocation().getBlockY(); j--) 
		{
			for(int i=0; i < DropBlocks.length; i++)
			{
				Block NewB = w.getBlockAt(b.getLocation().getBlockX(), j, b.getLocation().getBlockZ());
				if(NewB.getType() == Material.getMaterial(DropBlocks[i]))	BasicHandlers.OnBase(NewB);
			}
		}
		
		DropBlocks = new String[36];
		BasicHandlers.upDropBlocks(DropBlocks);
		for(int i=0; i < DropBlocks.length; i++)
		{
			Block NewB = w.getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY() + 1, b.getLocation().getBlockZ());
			if(NewB.getType() == Material.getMaterial(DropBlocks[i]))	BasicHandlers.OnBase(NewB);
		}
		
		DropBlocks = new String[10];
		BasicHandlers.laterallyDropBlocks(DropBlocks);
		for(int i=0; i < DropBlocks.length; i++)
		{
			Block NewB = w.getBlockAt(b.getLocation().getBlockX() + 1, b.getLocation().getBlockY(), b.getLocation().getBlockZ());
			if(NewB.getType() == Material.getMaterial(DropBlocks[i]))	BasicHandlers.OnBase(NewB);
			
			NewB = w.getBlockAt(b.getLocation().getBlockX() - 1, b.getLocation().getBlockY(), b.getLocation().getBlockZ());
			if(NewB.getType() == Material.getMaterial(DropBlocks[i]))	BasicHandlers.OnBase(NewB);
			
			NewB = w.getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ() + 1);
			if(NewB.getType() == Material.getMaterial(DropBlocks[i]))	BasicHandlers.OnBase(NewB);
			
			NewB = w.getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ() - 1);
			if(NewB.getType() == Material.getMaterial(DropBlocks[i]))	BasicHandlers.OnBase(NewB);
		}
	}
}
