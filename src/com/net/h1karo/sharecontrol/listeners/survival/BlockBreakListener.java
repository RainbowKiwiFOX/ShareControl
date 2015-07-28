package com.net.h1karo.sharecontrol.listeners.survival;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;
import com.net.h1karo.sharecontrol.localization.Localization;

public class BlockBreakListener implements Listener {
	
	@SuppressWarnings("unused")
	private final ShareControl main;
	public BlockBreakListener(ShareControl h)
	{
		this.main = h;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void OnBreak(BlockBreakEvent e)
	{
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		if(p.getGameMode() == GameMode.CREATIVE) return;
		Block b = e.getBlock();

		if(b.getType() == Material.PISTON_EXTENSION) {
			World w = b.getWorld();
			if(!BasicHandlers.InBase(b)) return;
			if(b.getData() == 13) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX() - 1, b.getY(), b.getZ());
				ClearPiston(piston, e);
				ClearBlock(b, p, e);
			}

			if(b.getData() == 12) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX() + 1, b.getY(), b.getZ());
				ClearPiston(piston, e);
				ClearBlock(b, p, e);
			}

			if(b.getData() == 11) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX(), b.getY(), b.getZ() - 1);
				ClearPiston(piston, e);
				ClearBlock(b, p, e);
			}

			if(b.getData() == 10) {
				e.setCancelled(true);
				Block piston = w.getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
				ClearPiston(piston, e);
				ClearBlock(b, p, e);
			}
			return;
		}
		
		ClearBlock(b, p, e);
	}
	
	public void ClearBlock(Block b, Player p, BlockBreakEvent e) {
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		
		if(Database.getBlockBase().get(x + "." + y + "." + z) != null && BasicHandlers.checkSameness(b)) {
			e.setCancelled(true);
			if(!Configuration.BlockingBreak)
			{
				b.setType(Material.AIR);
				Localization.SurvivalBlockNotDrop(p);
			}
			else
			{
				Localization.SurvivalBlockNotBreak(p);
				return;
			}
				
			Database.getBlockBase().set(x + "." + y + "." + z, null);
			Database.saveBlockBase();
			return;
		}
	}
	
	public void ClearPiston(Block b, BlockBreakEvent e) {
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		
		if(Database.getBlockBase().get(x + "." + y + "." + z) != null && BasicHandlers.checkSameness(b)){
			e.setCancelled(true);
			if(!Configuration.BlockingBreak)
				b.setType(Material.AIR);
			else
				return;
			
			Database.getBlockBase().set(x + "." + y + "." + z, null);
			Database.saveBlockBase();
			return;
		}
	}
}
