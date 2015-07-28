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


package com.net.h1karo.sharecontrol.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.net.h1karo.sharecontrol.MessageManager;
import com.net.h1karo.sharecontrol.MessageManager.MessageType;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.localization.LanguageFiles;

public class Configuration {
	
	private static ShareControl main;
	public Configuration(ShareControl h) {
        Configuration.main = h;
    }
	
	public static String errorcode;
	
	//
	//
	// CONFIGURATION
	//
	public static boolean versionCheck;
	public static List<String> BlockingBlocksPlaceList, BlockingBlocksBreakList, BlockingItemsInvList, BlockingCmdsList;
    public static boolean CreatureInteract, PlayerInteract, CreativeNotify, SurvivalNotify, material, BlockingBreak, PrefixEnabled;
    public static boolean MultiInventoriesEnabled, InventorySeparation;
    public static String Language;
    
    public static boolean BlockingCmdsEnabled;
    
    public static boolean GamemodesControlEnabled, FullGCEnabled;
    
    public static boolean WorldsCfgEnabled;
    public static List<String> BlockingCreative;
    
    private static File languageFolder;
    private static File inventoryFolder;
    private static FileConfiguration InvConfig = null;
    private static File InvConfigFile = null;
    
    // Config.yml
	
	public static void saveCfg()
	{
		main.getConfig().set("General.CheckUpdates",versionCheck);
		main.getConfig().set("General.Version", main.version);
		main.getConfig().set("General.Language", Language);
        
		main.getConfig().set("Notifications.SurvivalNotify", SurvivalNotify);
		main.getConfig().set("Notifications.CreativeNotify", CreativeNotify);
		main.getConfig().set("Notifications.Material", material);
		main.getConfig().set("Notifications.PrefixEnabled", PrefixEnabled);
		
		main.getConfig().set("Settings.BlockingCreatureInteract", CreatureInteract);
		main.getConfig().set("Settings.BlockingPlayerInteract", PlayerInteract);
		main.getConfig().set("Settings.BlockingBreak", BlockingBreak);
		
		main.getConfig().set("Settings.MultiInventories.Enabled", MultiInventoriesEnabled);
		main.getConfig().set("Settings.MultiInventories.Separation", InventorySeparation);
        
		main.getConfig().set("Settings.Blocks.BlockingPlacement", BlockingBlocksPlaceList);
		main.getConfig().set("Settings.Blocks.BlockingBreakage", BlockingBlocksBreakList);
		
		main.getConfig().set("Settings.Items.BlockingInventory", BlockingItemsInvList);
		
		main.getConfig().set("Settings.BlockingCmds.List", BlockingCmdsList);
        main.getConfig().set("Settings.BlockingCmds.Enabled", BlockingCmdsEnabled);
		
		main.getConfig().set("WorldsConfig.Enabled", WorldsCfgEnabled);
	    main.getConfig().set("WorldsConfig.BlockingCreativeInWorlds", BlockingCreative);
	    
	    main.getConfig().set("GamemodesControl.Enabled", GamemodesControlEnabled);
	    main.getConfig().set("GamemodesControl.Full", FullGCEnabled);
        
		main.saveConfig();
		
		CheckError();
    }

	public static void loadCfg()
	{
		main.reloadConfig();
	
        inventoryFolder = new File(main.getDataFolder(), "data" + File.separator + "inventories");
        if (!inventoryFolder.exists()) inventoryFolder.mkdirs();
        
        languageFolder = new File(main.getDataFolder(), "languages" + File.separator);
        if (!languageFolder.exists()) languageFolder.mkdirs();
		
		versionCheck = main.getConfig().getBoolean("General.CheckUpdates", true);
		Language = main.getConfig().getString("General.Language", "en");
        
        SurvivalNotify = main.getConfig().getBoolean("Notifications.SurvivalNotify", true);
        CreativeNotify = main.getConfig().getBoolean("Notifications.CreativeNotify", true);
        material = main.getConfig().getBoolean("Notifications.Material", true);
        PrefixEnabled = main.getConfig().getBoolean("Notifications.PrefixEnabled", true);
        
        main.getConfig().get("settings");
        
        CreatureInteract = main.getConfig().getBoolean("Settings.BlockingCreatureInteract", true);
        PlayerInteract = main.getConfig().getBoolean("Settings.BlockingPlayerInteract", true);
        BlockingBreak = main.getConfig().getBoolean("Settings.BlockingBreak", true);
        
        MultiInventoriesEnabled = main.getConfig().getBoolean("Settings.MultiInventories.Enabled", true);
        InventorySeparation = main.getConfig().getBoolean("Settings.MultiInventories.Separation", true);
	
        BlockingBlocksPlaceList = main.getConfig().getStringList("Settings.Blocks.BlockingPlacement");
        BlockingBlocksBreakList = main.getConfig().getStringList("Settings.Blocks.BlockingBreakage");
        
        BlockingItemsInvList = main.getConfig().getStringList("Settings.Items.BlockingInventory");
        
        BlockingCmdsList = main.getConfig().getStringList("Settings.BlockingCmds.List");
        BlockingCmdsEnabled =  main.getConfig().getBoolean("Settings.BlockingCmds.Enabled", false);
        
        WorldsCfgEnabled = main.getConfig().getBoolean("WorldsConfig.Enabled", false);
        
        BlockingCreative = main.getConfig().getStringList("WorldsConfig.BlockingCreativeInWorlds");
        
        GamemodesControlEnabled = main.getConfig().getBoolean("GamemodesControl.Enabled", false);
        FullGCEnabled = main.getConfig().getBoolean("GamemodesControl.Full", true);
        
		reloadInvConfig();
		saveInvConfig();
		
		File languageConfigFile = null;
    	
    	languageConfigFile = new File(main.getDataFolder(), "languages" + File.separator + "en.yml");
    	if(!languageConfigFile.exists()) {
    		main.getLogger().info("English language file not found! Loading...");
    		LanguageFiles.reloadlanguageConfig("en");
    		LanguageFiles.savelanguageConfig("en");
    	}
    	
    	languageConfigFile = new File(main.getDataFolder(), "languages" + File.separator + "ru.yml");
    	if(!languageConfigFile.exists()) {
    		main.getLogger().info("Russian language file not found! Loading...");
    		LanguageFiles.reloadlanguageConfig("ru");
    		LanguageFiles.savelanguageConfig("ru");
    	}
		
		LanguageFiles.reloadlanguageConfig(Configuration.Language);
		LanguageFiles.savelanguageConfig(Configuration.Language);
	}
	
	// inventories.yml
    
    public static void reloadInvConfig() {
    	if (InvConfigFile == null)	InvConfigFile = new File(inventoryFolder + File.separator + "inventories.yml");
    	
    	InvConfig = YamlConfiguration.loadConfiguration(InvConfigFile);
    	 
    		InputStream defConfigStream = main.getResource(inventoryFolder + File.separator + "inventories.yml");
    		if (defConfigStream != null) {
				@SuppressWarnings("deprecation")
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
    			InvConfig.setDefaults(defConfig);
    		}
    	}
    
    public static FileConfiguration getInvConfig() {
    	if (InvConfig == null) 	reloadInvConfig();
    	return InvConfig;
    	}
    
    public static void saveInvConfig() {
    	if (InvConfig == null || InvConfigFile == null) return;
    	try {
    		getInvConfig().save(InvConfigFile);
    	} 
    	catch (IOException ex) {
    		main.getLogger().log(Level.WARNING, "Could not save config to " + InvConfigFile, ex);
    	}
    }
    
	//
	//
	// EXTRA
	//
    
    static boolean ifInt;
    
    /** preform for 2.1 **/
    
    /*@SuppressWarnings("deprecation")
	public static void addBreak(CommandSender sender, String material) {
    	Material Material;
    	isInteger(material);
		
		if(ifInt)
		{
			int ID = Integer.parseInt(material);
			Material = org.bukkit.Material.getMaterial(ID);
		}
		else Material = org.bukkit.Material.getMaterial(material);
		if(!(Material instanceof Material)) {
			Localization.ThisNotMaterialandID(sender, material);
		}
    }*/

	@SuppressWarnings("deprecation")
	protected static void CheckError()
	{
		int j = BlockingBlocksPlaceList.toArray().length;
		for(int i=0; i < j; i++)
		{
			String String = BlockingBlocksPlaceList.toArray()[i].toString().replace("'", "");
			Material Material;
			isInteger(String);
			
			if(ifInt)
			{
				int ID = Integer.parseInt(String);
				Material = org.bukkit.Material.getMaterial(ID);
			}
			else Material = org.bukkit.Material.getMaterial(String);
			if(!(Material instanceof Material) && String.compareToIgnoreCase("none") != 0) {
				ShareControl.error = true;
				errorcode = String;
			}
		}
		j = BlockingBlocksBreakList.toArray().length;
		for(int i=0; i < j; i++)
		{
			String String = BlockingBlocksBreakList.toArray()[i].toString().replace("'", "");
			Material Material;
			
			isInteger(String);
			
			if(ifInt)
			{
				int ID = Integer.parseInt(String);
				Material = org.bukkit.Material.getMaterial(ID);
			}
			else Material = org.bukkit.Material.getMaterial(String);
			
			if(!(Material instanceof Material) && String.compareToIgnoreCase("none") != 0){
				ShareControl.error = true;
				errorcode = String;
			}
		}
		j = BlockingItemsInvList.toArray().length;
		for(int i=0; i < j; i++)
		{
			String String = BlockingItemsInvList.toArray()[i].toString().replace("'", "");
			Material Material;
			isInteger(String);
			
			if(ifInt)
			{
				int ID = Integer.parseInt(String);
				Material = org.bukkit.Material.getMaterial(ID);
			}
			else Material = org.bukkit.Material.getMaterial(String);
			if(!(Material instanceof Material) && String.compareToIgnoreCase("none") != 0) {
				ShareControl.error = true;
				errorcode = String;
			}
		}
	}

	public static void Error(CommandSender sender)
	{
		String msg = "CAUTION! An error is found in the file config.yml!";
		String msg2 = "Possible error: " + errorcode;
		
		if(Language.compareToIgnoreCase("ru") == 0) {
			msg = "Внимание! Найдена ошибка в файле config.yml!";
			msg2 = "Возможная ошибка: " + errorcode;
		}
		if(main.checkSender(sender)) {
			main.getLogger().warning(msg);
			main.getLogger().warning(msg2);
		}
		else {
			main.getLogger().warning(msg);
			MessageManager.getManager().msg(sender, MessageType.ERROR, msg);
			
			main.getLogger().warning(msg2);
			MessageManager.getManager().msg(sender, MessageType.ERROR, msg2);
		}
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s);
	    }
	    catch(NumberFormatException e) { 
	    	ifInt = false;
	    	return false; 
	    }
	    ifInt = true;
	    return true;
	}
	
}
