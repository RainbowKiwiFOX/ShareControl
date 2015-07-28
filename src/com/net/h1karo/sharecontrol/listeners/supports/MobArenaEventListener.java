package com.net.h1karo.sharecontrol.listeners.supports;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;

import com.garbagemule.MobArena.events.ArenaPlayerJoinEvent;
import com.garbagemule.MobArena.events.ArenaPlayerLeaveEvent;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.listeners.inventory.PlayerGameModeChangeListener;

public class MobArenaEventListener implements Listener {
	
	private final ShareControl main;
	public MobArenaEventListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void ArenaPlayerJoin(ArenaPlayerJoinEvent e) {
		PlayerGameModeChangeListener.joinMA = true;
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() { PlayerGameModeChangeListener.joinMA = false; }}, 25L);
	}

	@EventHandler
	public void ArenaPlayerLeave(ArenaPlayerLeaveEvent e) {
		PlayerGameModeChangeListener.leaveMA = true;
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() { PlayerGameModeChangeListener.leaveMA = false; }}, 25L);
	}
}
