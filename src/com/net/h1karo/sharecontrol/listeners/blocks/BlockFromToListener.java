package com.net.h1karo.sharecontrol.listeners.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;

public class BlockFromToListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public BlockFromToListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void WaterBreakBlock(BlockFromToEvent e) {
		String[] WBD = new String[29];
		BasicHandlers.WaterDropBlocks(WBD);
		Block b = e.getToBlock();
		for(int i = 0; i < WBD.length; i++)
		if(b.getType() == Material.getMaterial(WBD[i]) && BasicHandlers.InBase(b))
		{
			e.setCancelled(true);
			b.setType(Material.AIR);
			BasicHandlers.OnBase(b);
			return;
		}
	}
}
