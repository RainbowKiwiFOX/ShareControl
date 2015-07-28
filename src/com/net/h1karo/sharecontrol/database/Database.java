package com.net.h1karo.sharecontrol.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.net.h1karo.sharecontrol.ShareControl;

public class Database {
	private static ShareControl main;
	public Database(ShareControl h) {
		Database.main = h;
    }
	
	private static File dataFolder;
    private static File blockBaseFolder;
    private static FileConfiguration blockBase = null;
    private static File blockBaseFile = null;
    
    public static void reloadBlockBase() {
    	dataFolder = new File(main.getDataFolder(), "data");
        if (!dataFolder.exists()) dataFolder.mkdirs();
    	blockBaseFolder = new File(main.getDataFolder(), "data" + File.separator + "blocks.db");
        if (!blockBaseFolder.exists()) blockBaseFolder.mkdirs();
    	if (blockBaseFile == null)	blockBaseFile = new File(blockBaseFolder + File.separator + "blocks.db");
    	
    	blockBase = YamlConfiguration.loadConfiguration(blockBaseFile);
    	 
    		InputStream defConfigStream = main.getResource(blockBaseFolder + File.separator + "blocks.db");
    		if (defConfigStream != null) {
				@SuppressWarnings("deprecation")
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
    			blockBase.setDefaults(defConfig);
    		}
    	saveBlockBase();
    	}
    
    public static FileConfiguration getBlockBase() {
    	if (blockBase == null) 	reloadBlockBase();
    	return blockBase;
    	}
    
    public static void saveBlockBase() {
    	if (blockBase == null || blockBaseFile == null) return;
    	try {
    		getBlockBase().save(blockBaseFile);
    	} 
    	catch (IOException ex) {
    		main.getLogger().log(Level.WARNING, "Could not save config to " + blockBaseFile, ex);
    	}
    }
}
