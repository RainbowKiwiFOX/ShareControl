package com.net.h1karo.sharecontrol.listeners.blocks;

import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;

public class BlockPistonExtendListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public BlockPistonExtendListener(ShareControl h)
	{
		this.main = h;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPistonExtend(BlockPistonExtendEvent e)
	{
		if(e.isCancelled()) return;
		List<Block> blocks = e.getBlocks();
		World w = e.getBlock().getWorld();
		int X,Y,Z;
		String Dir = e.getDirection().toString();
		
		/** Blocks edit in list of creative block **/
		if(BasicHandlers.checkSamenessList(blocks)) {
			for(int i=0; i < blocks.size(); i++) {
				Block b = blocks.get(i);
				if(BasicHandlers.InBase(b))
					BasicHandlers.EditBlockByPiston(b, i, Dir, w);
			}
			
			for(int i = blocks.size() - 1; i >= 0; i--) {
				Block b = blocks.get(i);
				X = b.getX();
				Y = b.getY();
				Z = b.getZ();
				if(Dir.compareToIgnoreCase("EAST") == 0)
					X--;
				if(Dir.compareToIgnoreCase("WEST") == 0)
					X++;
				if(Dir.compareToIgnoreCase("SOUTH") == 0)
					Z--;
				if(Dir.compareToIgnoreCase("NORTH") == 0)
					Z++;
				if(Dir.compareToIgnoreCase("UP") == 0)
					Y--;
				if(Dir.compareToIgnoreCase("DOWN") == 0)
					Y++;
				
				Block bt = w.getBlockAt(X, Y, Z);
				if(!BasicHandlers.InBase(bt)) {
					BasicHandlers.clearBlock(b);
				}
			}
		}
		/** Piston add in list of creative block **/
		
		X = e.getBlock().getX();
		Y = e.getBlock().getY();
		Z = e.getBlock().getZ();
		
		if(BasicHandlers.InBase(e.getBlock()))
		{
			if(Dir.compareToIgnoreCase("EAST") == 0) 
				BasicHandlers.AddofDatabase(w.getBlockAt(X + 1, Y, Z));
			if(Dir.compareToIgnoreCase("WEST") == 0) 
				BasicHandlers.AddofDatabase(w.getBlockAt(X - 1, Y, Z));
			if(Dir.compareToIgnoreCase("SOUTH") == 0) 
				BasicHandlers.AddofDatabase(w.getBlockAt(X, Y, Z + 1));
			if(Dir.compareToIgnoreCase("NORTH") == 0) 
				BasicHandlers.AddofDatabase(w.getBlockAt(X, Y, Z - 1));
			if(Dir.compareToIgnoreCase("UP") == 0) 
				BasicHandlers.AddofDatabase(w.getBlockAt(X, Y + 1, Z));
			if(Dir.compareToIgnoreCase("DOWN") == 0) 
				BasicHandlers.AddofDatabase(w.getBlockAt(X, Y - 1, Z));
		}
	}
}
