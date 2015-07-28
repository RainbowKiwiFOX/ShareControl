package com.net.h1karo.sharecontrol.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.net.h1karo.sharecontrol.MessageManager;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.MessageManager.MessageType;
import com.net.h1karo.sharecontrol.localization.LanguageFiles;

public class Configuration {
	
	private static ShareControl main;
	public Configuration(ShareControl h) {
        Configuration.main = h;
    }
	
	//
	//
	// CONFIGURATION
	//
	public static boolean versionCheck;
	public static List<String> BlockingBlocksPlaceList, BlockingBlocksBreakList, BlockingItemsInvList, BlockingCmdsList;
    public static boolean CreatureInteract, PlayerInteract, InventorySeparation, CreativeNotify, SurvivalNotify, material, BlockingBreak;
    public static String Language;
    
    public static boolean BlockingCmdsEnabled;
    
    public static boolean GamemodesControlEnabled;
    public static boolean FullGCEnabled;
    
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
		
		main.getConfig().set("Settings.BlockingCreatureInteract", CreatureInteract);
		main.getConfig().set("Settings.BlockingPlayerInteract", PlayerInteract);
		main.getConfig().set("Settings.BlockingBreak", BlockingBreak);
		
		main.getConfig().set("Settings.InventorySeparation", InventorySeparation);
        
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
        
        main.getConfig().get("settings");
        
        CreatureInteract = main.getConfig().getBoolean("Settings.BlockingCreatureInteract", true);
        PlayerInteract = main.getConfig().getBoolean("Settings.BlockingPlayerInteract", true);
        InventorySeparation = main.getConfig().getBoolean("Settings.InventorySeparation", true);
        BlockingBreak = main.getConfig().getBoolean("Settings.BlockingBreak", true);
	
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
		
        CheckError();
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

	@SuppressWarnings("deprecation")
	protected static void CheckError()
	{
		int j = BlockingBlocksPlaceList.toArray().length;
		for(int i=0; i < j; i++)
		{
			String StrBPL = BlockingBlocksPlaceList.toArray()[i].toString().replace("'", "");
			Material MaterialBPL;
			isInteger(StrBPL);
			
			if(ifInt)
			{
				int ID = Integer.parseInt(StrBPL);
				MaterialBPL = Material.getMaterial(ID);
			}
			else MaterialBPL = Material.getMaterial(StrBPL);
			if(!(MaterialBPL instanceof Material) && StrBPL.compareToIgnoreCase("none") != 0) ShareControl.error = true;
		}
		j = BlockingBlocksBreakList.toArray().length;
		for(int i=0; i < j; i++)
		{
			String StrBBL = BlockingBlocksBreakList.toArray()[i].toString().replace("'", "");
			Material MaterialBBL;
			
			isInteger(StrBBL);
			
			if(ifInt)
			{
				int ID = Integer.parseInt(StrBBL);
				MaterialBBL = Material.getMaterial(ID);
			}
			else MaterialBBL = Material.getMaterial(StrBBL);
			
			if(!(MaterialBBL instanceof Material) && StrBBL .compareToIgnoreCase("none") != 0) ShareControl.error = true;
		}
		j = BlockingItemsInvList.toArray().length;
		for(int i=0; i < j; i++)
		{
			String StrBIUL = BlockingItemsInvList.toArray()[i].toString().replace("'", "");
			Material MaterialBIUL;
			isInteger(StrBIUL);
			
			if(ifInt)
			{
				int ID = Integer.parseInt(StrBIUL);
				MaterialBIUL = Material.getMaterial(ID);
			}
			else MaterialBIUL = Material.getMaterial(StrBIUL);
			if(!(MaterialBIUL instanceof Material) && StrBIUL.compareToIgnoreCase("none") != 0) ShareControl.error = true;
		}
	}

	public static void Error(CommandSender sender)
	{
		String msg = "CAUTION! An error is found in the file config.yml! Work plugin suspended!";
		if(main.checkSender(sender)) 	main.getLogger().warning(msg);
		else {
			main.getLogger().warning(ChatColor.RED + msg);
			MessageManager.getManager().msg(sender, MessageType.ERROR, msg);
		}
		
		main.getPluginLoader().disablePlugin(main);
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
