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

package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerInteractListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public PlayerInteractListener(ShareControl h)
	{
		this.main = h;
	}
    
	boolean ifOpen = false;
	
    @EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent e)
	{
    	if(e.isCancelled()) return;
    	
    	Player p = e.getPlayer();
		if(Permissions.perms(p, "allow.open-inventory") || e.getPlayer().getGameMode() != GameMode.CREATIVE)	return;
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(e.getClickedBlock().getType() != Material.CHEST && e.getClickedBlock().getType() != Material.TRAPPED_CHEST) return;
			Localization.openInv(p);
			e.setCancelled(true);
		}
	}
    
    @EventHandler(priority = EventPriority.HIGH)
	public void onInteractJuxeBox(PlayerInteractEvent e)
	{
    	if(e.isCancelled()) return;
    	
    	Player p = e.getPlayer();
		if(Permissions.perms(p, "allow.open-inventory") || e.getPlayer().getGameMode() != GameMode.CREATIVE)	return;
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if(e.getClickedBlock().getType() != Material.JUKEBOX) return;
					
			Localization.interact(p);
			e.setCancelled(true);
		}
	}
    
    @SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void BlockingInteractWithBlock(PlayerInteractEvent e)
	{
    	if(e.isCancelled() || Configuration.BlockingInteractList.contains("none") || e.getClickedBlock() == null) return;
    	Player p = e.getPlayer();
    	Block b = e.getClickedBlock();
		if(Permissions.perms(p, "allow.blocking-interact.*") || p.getGameMode() != GameMode.CREATIVE)	return;
		if((Configuration.BlockingInteractList.contains(b.getTypeId()) && !Permissions.perms(p, "allow.blocking-interact." + b.getTypeId())) || (Configuration.BlockingInteractList.contains(b.getType().toString()) && !Permissions.perms(p, "allow.blocking-interact." + b.getType().toString()))) {
			Localization.interact(p);
			e.setCancelled(true);
		}
	}
    
    @SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void BlockingUseItem(PlayerInteractEvent e) {
    	if(e.isCancelled() || Configuration.BlockingItemsInvList.contains("none") || e.getItem() == null || e.getItem().getType().equals(Material.AIR)) return;
    	Player p = e.getPlayer();
    	ItemStack b = e.getItem();
		if(Permissions.perms(p, "allow.blocking-inventory.*") || p.getGameMode() != GameMode.CREATIVE)	return;
		if((Configuration.BlockingItemsInvList.contains(b.getTypeId()) && !Permissions.perms(p, "allow.blocking-inventory." + b.getTypeId())) || (Configuration.BlockingItemsInvList.contains(b.getType().toString()) && !Permissions.perms(p, "allow.blocking-inventory." + b.getType().toString()))) {
			Localization.invNotify(b.getType(), p);
			p.setItemInHand(new ItemStack(Material.AIR));
			e.setCancelled(true);
		}
	}
}
