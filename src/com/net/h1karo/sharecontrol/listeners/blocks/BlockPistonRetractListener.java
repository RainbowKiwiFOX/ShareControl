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

import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonRetractEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;

public class BlockPistonRetractListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public BlockPistonRetractListener(ShareControl h)
	{
		this.main = h;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPistonRetract(BlockPistonRetractEvent e)
	{
		if(e.isCancelled()) return;
		List<Block> blocks = e.getBlocks();
		World w = e.getBlock().getWorld();
		String Dir = e.getDirection().toString();
		
		int X = e.getBlock().getX();
		int Y = e.getBlock().getY();
		int Z = e.getBlock().getZ();
		
		if(Dir.compareToIgnoreCase("WEST") == 0)
			BasicHandlers.RemoveofDatabase(w.getBlockAt(X + 1, Y, Z));
		if(Dir.compareToIgnoreCase("EAST") == 0)
			BasicHandlers.RemoveofDatabase(w.getBlockAt(X - 1, Y, Z));
		if(Dir.compareToIgnoreCase("NORTH") == 0) 
			BasicHandlers.RemoveofDatabase(w.getBlockAt(X, Y, Z + 1));
		if(Dir.compareToIgnoreCase("SOUTH") == 0) 
			BasicHandlers.RemoveofDatabase(w.getBlockAt(X, Y, Z - 1));
		if(Dir.compareToIgnoreCase("UP") == 0) 
			BasicHandlers.RemoveofDatabase(w.getBlockAt(X, Y - 1, Z));
		if(Dir.compareToIgnoreCase("DOWN") == 0) 
			BasicHandlers.RemoveofDatabase(w.getBlockAt(X, Y + 1, Z));
		
		if(BasicHandlers.checkSamenessList(blocks)) {
			for(int i=0; i < blocks.size(); i++) {
				Block b = blocks.get(i);
				if(BasicHandlers.InBase(b)) {
					BasicHandlers.EditBlockByPiston(b, i, Dir, w);
				}
			}
		
			for(int i = blocks.size() - 1; i >= 0; i--) {
				Block b = blocks.get(i);
				X = b.getX();
				Y = b.getY();
				Z = b.getZ();
				if(Dir.compareToIgnoreCase("EAST") == 0)
					X++;
				if(Dir.compareToIgnoreCase("WEST") == 0)
					X--;
				if(Dir.compareToIgnoreCase("SOUTH") == 0)
					Z++;
				if(Dir.compareToIgnoreCase("NORTH") == 0)
					Z--;
				if(Dir.compareToIgnoreCase("UP") == 0)
					Y++;
				if(Dir.compareToIgnoreCase("DOWN") == 0)
					Y--;
					
				Block bt = w.getBlockAt(X, Y, Z);
				if(!BasicHandlers.InBase(bt)) {
					BasicHandlers.clearBlock(b);
				}
			}
		}
	}
}
