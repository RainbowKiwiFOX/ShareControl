package com.net.h1karo.sharecontrol.listeners.entity;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;

public class EntityChangeBlockListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public EntityChangeBlockListener(ShareControl h)
	{
		this.main = h;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void EntityChangeBlock(EntityChangeBlockEvent e)
	{
		if(e.isCancelled()) return;
		
		Block b = e.getBlock();
		World w = e.getBlock().getWorld();
		
		String[] DropBlocks = new String[3];
		BasicHandlers.upDropBlocksMore(DropBlocks);
		
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
		
		if(e.getEntityType() != EntityType.FALLING_BLOCK) return;
		if(BasicHandlers.InBase(e.getBlock()))
			if((b.getType() == Material.ANVIL && b.getData() > 0) || ((b.getType() == Material.SAND || b.getType() == Material.GRAVEL) && b.getData() < 2))
				e.setCancelled(true);
	}
}
