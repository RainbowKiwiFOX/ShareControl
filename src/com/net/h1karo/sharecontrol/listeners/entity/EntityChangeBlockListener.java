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


package com.net.h1karo.sharecontrol.listeners.entity;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.database.Database;

public class EntityChangeBlockListener implements Listener
{
	private final ShareControl main;
	
	public EntityChangeBlockListener(ShareControl h)
	{
		this.main = h;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void EntityChangeBlock(EntityChangeBlockEvent e)
	{
		if(e.isCancelled()) return;
		
		Block b = e.getBlock();
		World w = e.getBlock().getWorld();
		Entity eventEntity = e.getEntity();
		
		Database.DropBlocks(w, b);
		
        if (eventEntity.getType() == EntityType.FALLING_BLOCK && Database.CheckCreative(b)) {
            FallingBlock entity = (FallingBlock) eventEntity;
            if (e.getTo() == Material.AIR) {
                   entity.setDropItem(false);
                   Database.RemoveBlock(b);
                   entity.setMetadata("ShareControl.CREATIVE_FALLING_BLOCK", new FixedMetadataValue(main, "1"));
            }
        }
        
        if (e.getTo() != Material.AIR && b.getType() == Material.AIR && eventEntity.getType() == EntityType.FALLING_BLOCK) {
        	FallingBlock entity = (FallingBlock) eventEntity;
        	if(entity.hasMetadata("ShareControl.CREATIVE_FALLING_BLOCK")) {
        		Database.AddBlock(b);
        		entity.removeMetadata("ShareControl.CREATIVE_FALLING_BLOCK", main);
        	}
        }
	}
}
