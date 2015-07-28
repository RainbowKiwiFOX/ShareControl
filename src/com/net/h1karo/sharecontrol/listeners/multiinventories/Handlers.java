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


package com.net.h1karo.sharecontrol.listeners.multiinventories;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import com.net.h1karo.sharecontrol.configuration.Configuration;

public class Handlers {
	
	public static void clear(Player p) {
		p.getInventory().clear();
    	p.getInventory().setHelmet(AIR);
    	p.getInventory().setBoots(AIR);
    	p.getInventory().setLeggings(AIR);
    	p.getInventory().setChestplate(AIR);
		
		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
	}

	static ItemStack[] CreativeInventory = new ItemStack[37];
	static ItemStack[] SurvivalInventory = new ItemStack[37];
    static ItemStack[] AdventureInventory = new ItemStack[37];
    
    static ItemStack[] SurvivalArmor = new ItemStack[4];
    static ItemStack[] CreativeArmor = new ItemStack[4];
    static ItemStack[] AdventureArmor = new ItemStack[4];
    
    static ItemStack AIR = new ItemStack(Material.AIR, 1);
    
    public static void BasicHandler(PlayerInventory inv, Player p, GameMode newgm, GameMode gm)
    {
    	Configuration.reloadInvConfig();
    	
    	if(gm == GameMode.CREATIVE)
	    	SaveCreative(p, inv);
    	
    	if(gm == GameMode.SURVIVAL)
    		SaveSurvival(p, inv);
    	
    	if(gm == GameMode.ADVENTURE)
    		SaveAdventure(p, inv);
    	
    	if(newgm == GameMode.CREATIVE)
    		PasteCreative(p, inv);

    	if(newgm == GameMode.SURVIVAL)
    		PasteSurvival(p, inv);
    	
    	if(newgm == GameMode.ADVENTURE)
    		PasteAdventure(p, inv);
    	
    	if(newgm == GameMode.SPECTATOR)
    		clear(p);
    	
    	Configuration.saveInvConfig();
    }
	
	
    // HANDLERS
    
    public static void SaveSurvival(Player p, PlayerInventory inv) {
		SurvivalArmor[0] = inv.getHelmet();
		SurvivalArmor[1] = inv.getChestplate();
		SurvivalArmor[2] = inv.getLeggings();
		SurvivalArmor[3] = inv.getBoots();
		
		for(int i=0; i < 37; i++)
		{
			SurvivalInventory[i] = p.getInventory().getItem(i);
			if(SurvivalInventory[i] == null) SurvivalInventory[i] = new ItemStack(Material.AIR, 1);
		}
		
		//ARMOR
		for(int i=0; i < 4; i++)
			Configuration.getInvConfig().set(p.getUniqueId() + ".survival.armor."+i, SurvivalArmor[i]);
		
		//INVENTORY
		for(int i=0; i < 37; i++)
			Configuration.getInvConfig().set(p.getUniqueId() + ".survival.inventory.slot" + i, SurvivalInventory[i]);
	}
	
    public static void SaveCreative(Player p, PlayerInventory inv) {
		CreativeArmor[0] = inv.getHelmet();
		CreativeArmor[1] = inv.getChestplate();
		CreativeArmor[2] = inv.getLeggings();
		CreativeArmor[3] = inv.getBoots();
		
		for(int i=0; i < 37; i++)
		{
			CreativeInventory[i] = p.getInventory().getItem(i);
			if(CreativeInventory[i] == null) CreativeInventory[i] = new ItemStack(Material.AIR, 1);
		}		
		
		//ARMOR
		for(int i=0; i < 4; i++)
			Configuration.getInvConfig().set(p.getUniqueId() + ".creative.armor." + i, CreativeArmor[i]);
		
		//INVENTORY
		for(int i=0; i < 37; i++)
			Configuration.getInvConfig().set(p.getUniqueId() + ".creative.inventory.slot" + i, CreativeInventory[i]);
	}
	
    public static void SaveAdventure(Player p, PlayerInventory inv) {
		AdventureArmor[0] = inv.getHelmet();
		AdventureArmor[1] = inv.getChestplate();
		AdventureArmor[2] = inv.getLeggings();
		AdventureArmor[3] = inv.getBoots();
		
		for(int i=0; i < 37; i++)
		{
			AdventureInventory[i] = p.getInventory().getItem(i);
			if(AdventureInventory[i] == null) AdventureInventory[i] = new ItemStack(Material.AIR, 1);
		}
		
		//ARMOR
		for(int i=0; i < 4; i++)
			Configuration.getInvConfig().set(p.getUniqueId() + ".adventure.armor."+i, AdventureArmor[i]);
		
		//INVENTORY
		for(int i=0; i < 37; i++)
			Configuration.getInvConfig().set(p.getUniqueId() + ".adventure.inventory.slot" + i, AdventureInventory[i]);
	}
	
	
    public static void PasteCreative(Player p, PlayerInventory inv) {
		clear(p);
    	
    	for(int i=0; i < 4; i++)
    		CreativeArmor[i] = Configuration.getInvConfig().getItemStack(p.getUniqueId() + ".creative.armor." + i, AIR);
    	
    	ItemStack ISH = new ItemStack(CreativeArmor[0]);
    	ItemStack ISC = new ItemStack(CreativeArmor[1]);
    	ItemStack ISL = new ItemStack(CreativeArmor[2]);
    	ItemStack ISB = new ItemStack(CreativeArmor[3]);
    	
    	p.getInventory().setHelmet(ISH);
    	p.getInventory().setChestplate(ISC);
    	p.getInventory().setLeggings(ISL);
    	p.getInventory().setBoots(ISB);
    	
    	for(int i=0; i < 37; i++)
    	{
    		CreativeInventory[i] = Configuration.getInvConfig().getItemStack(p.getUniqueId() + ".creative.inventory.slot" + i, AIR);
    		if(CreativeInventory[i] == null)
    			CreativeInventory[i] = new ItemStack(Material.AIR, 1);
    		p.getInventory().setItem(i, CreativeInventory[i]);
    	}
	}
	
    public static void PasteSurvival(Player p, PlayerInventory inv) {
		clear(p);
    	
    	for(int i=0; i < 4; i++)
    		SurvivalArmor[i] = Configuration.getInvConfig().getItemStack(p.getUniqueId() + ".survival.armor." + i, AIR);
    	
    	ItemStack ISH = new ItemStack(SurvivalArmor[0]);
    	ItemStack ISC = new ItemStack(SurvivalArmor[1]);
    	ItemStack ISL = new ItemStack(SurvivalArmor[2]);
    	ItemStack ISB = new ItemStack(SurvivalArmor[3]);
    	
    	p.getInventory().setHelmet(ISH);
    	p.getInventory().setChestplate(ISC);
    	p.getInventory().setLeggings(ISL);
    	p.getInventory().setBoots(ISB);
    	
    	for(int i=0; i < 37; i++)
    	{
    		SurvivalInventory[i] = Configuration.getInvConfig().getItemStack(p.getUniqueId() + ".survival.inventory.slot" + i, AIR);
    		
    		if(SurvivalInventory[i] == null) SurvivalInventory[i] = new ItemStack(Material.AIR, 1);
    		
    		p.getInventory().setItem(i, SurvivalInventory[i]);
    	}
	}
	
    public static void PasteAdventure(Player p, PlayerInventory inv) {
		clear(p);
    	
    	for(int i=0; i < 4; i++)
    		AdventureArmor[i] = Configuration.getInvConfig().getItemStack(p.getUniqueId() + ".adventure.armor." + i, AIR);
    	
    	ItemStack ISH = new ItemStack(AdventureArmor[0]);
    	ItemStack ISC = new ItemStack(AdventureArmor[1]);
    	ItemStack ISL = new ItemStack(AdventureArmor[2]);
    	ItemStack ISB = new ItemStack(AdventureArmor[3]);
    	
    	p.getInventory().setHelmet(ISH);
    	p.getInventory().setChestplate(ISC);
    	p.getInventory().setLeggings(ISL);
    	p.getInventory().setBoots(ISB);
    	
    	for(int i=0; i < 37; i++)
    	{
    		AdventureInventory[i] = Configuration.getInvConfig().getItemStack(p.getUniqueId() + ".adventure.inventory.slot" + i, AIR);
    		
    		if(AdventureInventory[i] == null) AdventureInventory[i] = new ItemStack(Material.AIR, 1);
    		
    		p.getInventory().setItem(i, AdventureInventory[i]);
    	}
	}
}
