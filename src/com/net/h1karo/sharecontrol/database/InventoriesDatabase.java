package com.net.h1karo.sharecontrol.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;

public class InventoriesDatabase {
	
	private static ShareControl main;
	public InventoriesDatabase(ShareControl h) {
		InventoriesDatabase.main = h;
    }
	
	
    private static FileConfiguration InvConfig = null;
    private static File InvConfigFile = null;
    
    public static void reloadInvConfig() {
    	if (InvConfigFile == null)	InvConfigFile = new File(Configuration.inventoryFolder + File.separator + "inventories.yml");
    	
    	InvConfig = YamlConfiguration.loadConfiguration(InvConfigFile);
    	 
    		InputStream defConfigStream = main.getResource(Configuration.inventoryFolder + File.separator + "inventories.yml");
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
}
