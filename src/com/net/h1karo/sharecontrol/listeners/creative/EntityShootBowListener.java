package com.net.h1karo.sharecontrol.listeners.creative;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.localization.Localization;

public class EntityShootBowListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	
	public EntityShootBowListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void EntityShootBow(EntityShootBowEvent e)
	{
		if(!(e.getEntity() instanceof Player) || e.isCancelled()) return;
		Player p = (Player) e.getEntity();
		if(p.getGameMode() == GameMode.CREATIVE && !Permissions.perms(p, "allow.creature-interact"))
		{
			e.setCancelled(true);
			Localization.Bow(p);
			return;
		}
	}
}
