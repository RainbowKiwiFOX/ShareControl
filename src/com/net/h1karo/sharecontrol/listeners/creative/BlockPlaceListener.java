package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;
import com.net.h1karo.sharecontrol.localization.Localization;

public class BlockPlaceListener implements Listener {
	
	@SuppressWarnings("unused")
	private final ShareControl main;
	public BlockPlaceListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void CreativeBlockPlace(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		if(p.getGameMode() != GameMode.CREATIVE || Permissions.perms(p, "allow.notlogging") || e.isCancelled()) return;
		Block b = e.getBlockPlaced();
		BasicHandlers.AddofDatabase(b);
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void DisableBlockPlace(BlockPlaceEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		if(Permissions.perms(p, "allow.blocking-placement") || Configuration.BlockingBlocksPlaceList.toArray().length == 0  || Configuration.BlockingBlocksPlaceList.get(0).toString() == "[none]" || !(p.getGameMode() == GameMode.CREATIVE))	return;
		
		for(int i=0; i < Configuration.BlockingBlocksPlaceList.toArray().length; i++)
		{
			Material typeThisBlock = e.getBlock().getType();
			String StrListBlock = (String) Configuration.BlockingBlocksPlaceList.toArray()[i];
			Material typeListBlock;
			
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
				Localization.PlaceBlock(typeThisBlock, p);
				e.setCancelled(true);
			}
		}
	}
}
