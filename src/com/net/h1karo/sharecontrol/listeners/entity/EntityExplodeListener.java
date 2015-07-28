package com.net.h1karo.sharecontrol.listeners.entity;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;

public class EntityExplodeListener implements Listener {
	
	@SuppressWarnings("unused")
	private final ShareControl main;
	public EntityExplodeListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler
	public void EntityExplode(EntityExplodeEvent e)
	{
		if(e.getEntityType() == EntityType.PRIMED_TNT || e.getEntityType() == EntityType.CREEPER) {
			List<Block> blocks = e.blockList();
			for(int i=0; i < blocks.size(); i++) {
				Block b = blocks.get(i);
				if(BasicHandlers.checkSameness(b))
					e.blockList().remove(b);
			}
		}
	}
}
