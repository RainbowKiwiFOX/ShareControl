package com.net.h1karo.sharecontrol.listeners.survival;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;

public class BlockPlaceListener implements Listener {
	
	@SuppressWarnings("unused")
	private final ShareControl main;
	public BlockPlaceListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void SurvivalBlockPlace(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE || e.isCancelled())
			return;
		Block b = e.getBlockPlaced();
		if(BasicHandlers.InBase(b))
			BasicHandlers.RemoveofDatabase(b);
	}
}
