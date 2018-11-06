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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.database.Database;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerItemConsumeListener implements Listener
{
	@SuppressWarnings("unused")
	private final ShareControl main;
	public PlayerItemConsumeListener(ShareControl h)
	{
		this.main = h;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerItemConsume(PlayerItemConsumeEvent e)
	{
		Player p = (Player) e.getPlayer();
		if(p.getGameMode() != GameMode.CREATIVE || Permissions.perms(p, "allow.blocking-inventory.*") || Configuration.BlockingItemsInvList.toArray().length == 0 || Configuration.BlockingItemsInvList.get(0).toString().compareToIgnoreCase("[none]") == 0) return;
		for(int i=0; i < Configuration.BlockingItemsInvList.toArray().length; i++)
		{
			String StrListItem = (String) Configuration.BlockingItemsInvList.toArray()[i];
			if(!Permissions.perms(p, "allow.blocking-inventory." + StrListItem)) {
				Material typeThisItem = e.getItem().getType();
				Material typeListItem;

				typeListItem = Material.getMaterial(StrListItem);
				
				if(typeThisItem == typeListItem)
				{
					Localization.invNotify(typeThisItem, p);
					e.setCancelled(true);
				}
			}
		}
	}
}
