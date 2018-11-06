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

package com.net.h1karo.sharecontrol.listeners.blocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.version.CoreVersion;

public class BlockMoveByPistonListener implements Listener {
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public BlockMoveByPistonListener(ShareControl h)
	{
		this.main = h;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void onPistonExtend(BlockPistonExtendEvent e)
	{
		if(e.isCancelled()) return;
		
		blocksHandling(e.getBlocks(), e.getDirection());
		
		Block b = e.getBlock();
		if(Database.CheckCreative(b)) {
			if(e.getDirection().equals(BlockFace.EAST))
				Database.AddBlockMoreArguments(b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ()), Material.PISTON_HEAD.name());
			if(e.getDirection().equals(BlockFace.WEST))
				Database.AddBlockMoreArguments(b.getWorld().getBlockAt(b.getX() - 1, b.getY(), b.getZ()), Material.PISTON_HEAD.name());
			if(e.getDirection().equals(BlockFace.SOUTH))
				Database.AddBlockMoreArguments(b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() + 1), Material.PISTON_HEAD.name());
			if(e.getDirection().equals(BlockFace.NORTH))
				Database.AddBlockMoreArguments(b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() - 1), Material.PISTON_HEAD.name());
			if(e.getDirection().equals(BlockFace.UP))
				Database.AddBlockMoreArguments(b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ()), Material.PISTON_HEAD.name());
			if(e.getDirection().equals(BlockFace.DOWN))
				Database.AddBlockMoreArguments(b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ()), Material.PISTON_HEAD.name());
		}
	}
	

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void onPistonRetract(BlockPistonRetractEvent e)
	{
		if(e.isCancelled()) return;
		
		Block b = e.getBlock();
		if(Database.CheckCreative(b)) {
			if(e.getDirection().equals(BlockFace.EAST))
				Database.RemoveBlock(b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ()));
			if(e.getDirection().equals(BlockFace.WEST))
				Database.RemoveBlock(b.getWorld().getBlockAt(b.getX() - 1, b.getY(), b.getZ()));
			if(e.getDirection().equals(BlockFace.SOUTH))
				Database.RemoveBlock(b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() + 1));
			if(e.getDirection().equals(BlockFace.NORTH))
				Database.RemoveBlock(b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() - 1));
			if(e.getDirection().equals(BlockFace.UP))
				Database.RemoveBlock(b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ()));
			if(e.getDirection().equals(BlockFace.DOWN))
				Database.RemoveBlock(b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ()));
		}
		if(CoreVersion.getVersionsArray().contains(CoreVersion.OneDotEightPlus))
			blocksHandling(e.getBlocks(), e.getDirection());
		else 	if(b.getType().equals(Material.STICKY_PISTON))
				blockHandling(e.getRetractLocation().getBlock(), e.getDirection());
	}
	
	@SuppressWarnings("deprecation")
	public static void blocksHandling(List<Block> blocks, BlockFace Direction) {
		List<Block> successUpdatedBlocks = new ArrayList<Block>();
		for(Block b : blocks) {
			Database.DropBlocks(b);
			if(Database.ifOneUpDrop(b)) {
				Database.FullClear(b);
				continue;
			}
			if(Database.CheckBlock(b) || Database.CheckCreativeRough(b) == 0) continue;
			
			Block newCreativeBlock = null;
			
			if(Direction.equals(BlockFace.EAST)) 
				newCreativeBlock = b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ());
			if(Direction.equals(BlockFace.WEST)) 
				newCreativeBlock = b.getWorld().getBlockAt(b.getX() - 1, b.getY(), b.getZ());
			if(Direction.equals(BlockFace.SOUTH)) 
				newCreativeBlock = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
			if(Direction.equals(BlockFace.NORTH)) 
				newCreativeBlock = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() - 1);
			if(Direction.equals(BlockFace.UP)) 
				newCreativeBlock = b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ());
			if(Direction.equals(BlockFace.DOWN)) 
				newCreativeBlock = b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ());
			
			if(newCreativeBlock != null) {
				Database.AddBlockMoreArguments(newCreativeBlock, b.getType().name());
				successUpdatedBlocks.add(newCreativeBlock);
			}
		}
		
		for(Block b : blocks) {
			if(successUpdatedBlocks.contains(b)) continue;
			Database.RemoveBlock(b);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void blockHandling(Block b, BlockFace Direction) {
		Database.DropBlocks(b);
		if(Database.ifOneUpDrop(b)) {
			Database.FullClear(b);
			return;
		}
		if(Database.CheckBlock(b) || Database.CheckCreativeRough(b) == 0) return;
		Block newCreativeBlock = null;
			
		if(Direction.equals(BlockFace.EAST)) 
			newCreativeBlock = b.getWorld().getBlockAt(b.getX() - 1, b.getY(), b.getZ());
		if(Direction.equals(BlockFace.WEST)) 
			newCreativeBlock = b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ());
		if(Direction.equals(BlockFace.SOUTH)) 
			newCreativeBlock = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() - 1);
		if(Direction.equals(BlockFace.NORTH)) 
			newCreativeBlock = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
		if(Direction.equals(BlockFace.UP)) 
			newCreativeBlock = b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ());
		if(Direction.equals(BlockFace.DOWN)) 
			newCreativeBlock = b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ());
			
		if(newCreativeBlock != null) {
			Database.AddBlockMoreArguments(newCreativeBlock, b.getType().name());
			Database.RemoveBlock(b);
		}
	}
}
