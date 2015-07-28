package com.net.h1karo.sharecontrol.listeners.supports;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;

import net.slipcor.pvparena.events.PAJoinEvent;
import net.slipcor.pvparena.events.PALeaveEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.listeners.inventory.PlayerGameModeChangeListener;

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
