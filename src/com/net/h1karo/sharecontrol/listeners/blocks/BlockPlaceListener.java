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


package com.net.h1karo.sharecontrol.listeners.blocks;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;

public class BlockPlaceListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	public BlockPlaceListener(ShareControl h)
	{
		this.main = h;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onAutoPlace(BlockPlaceEvent e)
	{
		World w = e.getBlock().getWorld();
		Block b = null;
		
		if(w.getBlockAt(e.getBlockPlaced().getX() + 1, e.getBlockPlaced().getY(), e.getBlockPlaced().getZ()).getType() == Material.CACTUS)
			b = w.getBlockAt(e.getBlockPlaced().getX() + 1, e.getBlockPlaced().getY(), e.getBlockPlaced().getZ());
		
		if(w.getBlockAt(e.getBlockPlaced().getX() - 1, e.getBlockPlaced().getY(), e.getBlockPlaced().getZ()).getType() == Material.CACTUS)
			b = w.getBlockAt(e.getBlockPlaced().getX() - 1, e.getBlockPlaced().getY(), e.getBlockPlaced().getZ());
		
		if(w.getBlockAt(e.getBlockPlaced().getX(), e.getBlockPlaced().getY(), e.getBlockPlaced().getZ() + 1).getType() == Material.CACTUS)
			b = w.getBlockAt(e.getBlockPlaced().getX(), e.getBlockPlaced().getY(), e.getBlockPlaced().getZ() + 1);
		
		if(w.getBlockAt(e.getBlockPlaced().getX(), e.getBlockPlaced().getY(), e.getBlockPlaced().getZ() - 1).getType() == Material.CACTUS)
			b = w.getBlockAt(e.getBlockPlaced().getX(), e.getBlockPlaced().getY(), e.getBlockPlaced().getZ() - 1);
		
		if(w.getBlockAt(e.getBlockPlaced().getX(), e.getBlockPlaced().getY(), e.getBlockPlaced().getZ() - 1).getType() != Material.CACTUS && w.getBlockAt(e.getBlockPlaced().getX(), e.getBlockPlaced().getY(), e.getBlockPlaced().getZ() + 1).getType() != Material.CACTUS && w.getBlockAt(e.getBlockPlaced().getX() - 1, e.getBlockPlaced().getY(), e.getBlockPlaced().getZ()).getType() != Material.CACTUS && w.getBlockAt(e.getBlockPlaced().getX() + 1, e.getBlockPlaced().getY(), e.getBlockPlaced().getZ()).getType() != Material.CACTUS) return;
		
		String[] DropBlocks = new String[3];
		BasicHandlers.upDropBlocksMore(DropBlocks);
		
		for(int j = 256; j >= b.getLocation().getBlockY(); j--) 
		{
			for(int i=0; i < DropBlocks.length; i++)
			{
				Block NewB = w.getBlockAt(b.getLocation().getBlockX(), j, b.getLocation().getBlockZ());
				if(NewB.getType() == Material.getMaterial(DropBlocks[i]))	BasicHandlers.OnBase(NewB);
			}
		}
	}
}
