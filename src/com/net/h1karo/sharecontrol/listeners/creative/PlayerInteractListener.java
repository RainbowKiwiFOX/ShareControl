package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.Material;
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
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;
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
	@EventHandler
	public void BlockingUseItem(PlayerInteractEvent e)
	{
		for(int i=0; i < Configuration.BlockingItemsInvList.toArray().length; i++)
		{
			if(!(e.getPlayer() instanceof Player) || e.getPlayer().getItemInHand().getType() == Material.AIR) return;
			Player p = (Player) e.getPlayer();
			if(Permissions.perms(p, "allow.blocking-inventory") || Configuration.BlockingItemsInvList.toArray()[i].toString() == "[none]" || e.getItem() == null)	return;
			
			Material typeThisItem = e.getItem().getType();
			String StrListItem = (String) Configuration.BlockingItemsInvList.toArray()[i];
			Material typeListItem;
			
			BasicHandlers.isInteger(StrListItem);
			
			if(BasicHandlers.ifInt)
			{
				String NewStr = StrListItem.replace("'", "");
				int ID = Integer.parseInt(NewStr);
				typeListItem = Material.getMaterial(ID);
			}
			else
				typeListItem = Material.getMaterial(StrListItem);
			
			if(typeThisItem == typeListItem && p.getGameMode() == GameMode.CREATIVE)
			{
				
				Localization.invNotify(typeThisItem, p);
				p.setItemInHand(new ItemStack(Material.AIR));
				e.setCancelled(true);
			}
		}
	}
}
