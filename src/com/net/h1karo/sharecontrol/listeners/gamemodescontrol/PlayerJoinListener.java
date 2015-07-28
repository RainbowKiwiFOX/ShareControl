/*******************************************************************************
 * Copyright (C) 2015 H1KaRo (h1karo)
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


package com.net.h1karo.sharecontrol.listeners.gamemodescontrol;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.localization.Localization;

public class PlayerJoinListener implements Listener {
	
	@SuppressWarnings("unused")
	private final ShareControl main;
	public PlayerJoinListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void PlayerJoin(PlayerJoinEvent e)
	{
		if(!Configuration.GamemodesControlEnabled || Permissions.perms(e.getPlayer(), "gamemodescontrol.*")) return;
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE && !Permissions.perms(e.getPlayer(), "gamemodescontrol.creative")) {
			e.getPlayer().setGameMode(GameMode.SURVIVAL);
			Localization.NotAllowedGamemode(e.getPlayer(), "creative");
		}
		
		if(e.getPlayer().getGameMode() == GameMode.SURVIVAL && !Permissions.perms(e.getPlayer(), "gamemodescontrol.survival")) {
			e.getPlayer().setGameMode(GameMode.CREATIVE);
			Localization.NotAllowedGamemode(e.getPlayer(), "survival");
		}
		
		if(e.getPlayer().getGameMode() == GameMode.ADVENTURE && !Permissions.perms(e.getPlayer(), "gamemodescontrol.adventure")) {
			e.getPlayer().setGameMode(GameMode.SURVIVAL);
			Localization.NotAllowedGamemode(e.getPlayer(), "adventure");
		}
		
		if(e.getPlayer().getGameMode() == GameMode.SPECTATOR && !Permissions.perms(e.getPlayer(), "gamemodescontrol.spectator")) {
			e.getPlayer().setGameMode(GameMode.SURVIVAL);
			Localization.NotAllowedGamemode(e.getPlayer(), "spectator");
		}
	}
}
