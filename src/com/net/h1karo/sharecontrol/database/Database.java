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
