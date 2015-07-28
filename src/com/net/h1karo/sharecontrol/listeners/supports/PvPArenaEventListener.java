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


package com.net.h1karo.sharecontrol.listeners.supports;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;

import net.slipcor.pvparena.events.PAJoinEvent;
import net.slipcor.pvparena.events.PALeaveEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.listeners.multiinventories.PlayerGameModeChangeListener;

public class PvPArenaEventListener implements Listener {
	
	private final ShareControl main;
	public PvPArenaEventListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void ArenaPlayerJoin(PAJoinEvent e) {
		PlayerGameModeChangeListener.joinPVP = true;
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() { PlayerGameModeChangeListener.joinPVP = false; }}, 25L);
	}
	@EventHandler
	public void ArenaPlayerLeave(PALeaveEvent e) {
		PlayerGameModeChangeListener.leavePVP = true;
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() { PlayerGameModeChangeListener.leavePVP = false; }}, 25L);
	}
}
