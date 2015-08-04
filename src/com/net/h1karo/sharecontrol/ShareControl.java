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


package com.net.h1karo.sharecontrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.slipcor.pvparena.PVPArena;

import com.earth2me.essentials.Essentials;
import com.garbagemule.MobArena.framework.ArenaMaster;
import com.garbagemule.MobArena.MobArena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.net.h1karo.sharecontrol.MessageManager.MessageType;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.listeners.supports.MobArenaEventListener;
import com.net.h1karo.sharecontrol.listeners.supports.PvPArenaEventListener;
import com.net.h1karo.sharecontrol.localization.LanguageFiles;
import com.net.h1karo.sharecontrol.localization.Localization;
import com.net.h1karo.sharecontrol.metabase.MetaBase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.mcstats.MetricsLite;

public class ShareControl extends JavaPlugin implements Listener
{
	private static ShareControl instance;
	FileConfiguration config = getConfig();
	
    public static boolean error;
	
	public static ShareControl instance()
	{
		return instance;
	}
	public String version = getDescription().getVersion();
	
	private static Configuration mainconfig;
	@SuppressWarnings("unused")
	private static LanguageFiles lang;
	@SuppressWarnings("unused")
	private static MetaBase metabase;
	private ShareControlCommandExecutor Executor;
	
	public String web = getDescription().getWebsite();
    String stringVersion = ChatColor.BLUE + getDescription().getVersion();
    public String authors = getDescription().getAuthors().toString().replace("[", "").replace("]", "");
    
    public static ArenaMaster am;
    private static boolean foundMA = false, foundPVP = false, foundEss = false;
    
	@Override
	public void onEnable()
	{
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l=================== &9&lShare&f&lControl &7&l==================="));
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', " Loading configuration..."));
		
		instance = this;
		setupListeners();

		Configuration.loadCfg();
		Configuration.saveCfg();
		
        if(error) Configuration.Error(null);
        
        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {
           getLogger().warning("Failed to submit the stats!");
        }
        
        Executor = new ShareControlCommandExecutor(this);
		getCommand("sharecontrol").setExecutor(Executor);
		getCommand("sharecontrol").setPermissionMessage(MessageManager.prefix + ChatColor.translateAlternateColorCodes('&', LanguageFiles.NoPerms));
		
		Permissions.RegisterCustomPermissions();
		
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', " Configuration successfully uploaded!"));
		
		if(Configuration.versionCheck) {
			console.sendMessage(ChatColor.translateAlternateColorCodes('&', " Check updates..."));
			updateCheck();
			if(result == UpdateResult.UPDATE_AVAILABLE) {
				String name = newVersion;
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &fAn update is available: &9ShareControl v" + name.replace(" Alpha", "").replace(" Beta", "") + "&f,"));
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &fdownload at"));
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', " &f" + Localization.link));
			}
			if(result == UpdateResult.NO_UPDATE)
				console.sendMessage(ChatColor.translateAlternateColorCodes('&', " Updates not found!"));
		}
		
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l===================================================="));
	}
	@Override
	public void onDisable()
	{
		instance = null;
	}
	
	public boolean checkSender(CommandSender sender)
	{
		if(sender instanceof Player) {
			return false;
		}
		return true;
	}
	
	public static Configuration getMainConfig() {
        return mainconfig;
	}
	
	protected void pluginInfo(CommandSender sender)
	{
		String version = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CurrentVersion.replace("%version%", stringVersion));
		String Author = "\n  %author%,\n";
		String team = ChatColor.translateAlternateColorCodes('&', 
			LanguageFiles.DevelopmentTeam.replace("%development-team%", Author
				.replace("%author%", LanguageFiles.Author.replace("%nickname%", "H1KaRo"))));
		String site = ChatColor.translateAlternateColorCodes('&', LanguageFiles.WebSite.replace("%link%", web));
		MessageManager.getManager().msg(sender, MessageType.HELP, "======[" + Localization.prefix + ChatColor.BLUE + " Information" + ChatColor.GRAY + "]======");
		MessageManager.getManager().msg(sender, MessageType.HELP, version);
		MessageManager.getManager().msg(sender, MessageType.HELP, team);
		MessageManager.getManager().msg(sender, MessageType.HELP, site);
	}
	
	
	public static String newVersion = "";
	public static String currentVersion;
	public UpdateResult result;
	
	public enum UpdateResult {
        NO_UPDATE,
        UPDATE_AVAILABLE,
        ERROR
    }
	
	public void updateCheck() {
        double CurrentVersionNumber = 0, NewVersionNumber = 0, CurrentBuildNumber = 0, NewBuildNumber = 0;
        String CurrentBuildString = "", NewBuildString = "";
        
		currentVersion = getDescription().getVersion();
		
		try {
            URL url = new URL("https://api.curseforge.com/servermods/files?projectids=90354");
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(5000);
            conn.addRequestProperty("User-Agent", "ShareControl Update Checker");
            conn.setDoOutput(true);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final String response = reader.readLine();
            final JSONArray array = (JSONArray) JSONValue.parse(response);

            if (array.size() == 0) {
            	this.getLogger().warning("No files found, or Feed URL is bad.");
				result = UpdateResult.ERROR;
                return;
            }
            String newVersionTitle = ((String) ((JSONObject) array.get(array.size() - 1)).get("name"));
            newVersion = newVersionTitle.replace("ShareControl v", "").trim();
            
              /**\\**//**\\**//**\\**/
            /**\    GET VERSIONS    /**\
              /**\\**//**\\**//**\\**/
            
            if(currentVersion.contains("-")) {
        	CurrentVersionNumber = Double.parseDouble(currentVersion.substring(0, currentVersion.indexOf(".") + 1) + currentVersion.substring(currentVersion.indexOf(".") + 1, currentVersion.indexOf("-")).replace(".", ""));
        	CurrentBuildString = currentVersion.substring(currentVersion.indexOf("-") + 1);
        	
        	if(CurrentBuildString.contains("b")) {
        		CurrentBuildString = CurrentBuildString.replace("b", "");
        		CurrentBuildNumber = Double.parseDouble(CurrentBuildString) - 1;
        	}
        	else if(CurrentBuildString.contains("a")) {
        		CurrentBuildString = CurrentBuildString.replace("a", "");
        		CurrentBuildNumber = Double.parseDouble(CurrentBuildString) - 10;
        	}
        	else CurrentBuildNumber = Double.parseDouble(CurrentBuildString);
        }
        else 
        	CurrentVersionNumber = Double.parseDouble((currentVersion.substring(0, currentVersion.indexOf(".") + 1) + currentVersion.substring(currentVersion.indexOf(".") + 1).replace(".", "")).replace("b", "").replace("a", ""));
            
            if(newVersion.contains("-")) {
            	NewVersionNumber = Double.parseDouble(newVersion.substring(0, newVersion.indexOf(".") + 1) + newVersion.substring(newVersion.indexOf(".") + 1, newVersion.indexOf("-")).replace(".", ""));
            	NewBuildString = newVersion.substring(newVersion.indexOf("-") + 1);
            	
            	if(NewBuildString.contains("b")) {
            		NewBuildString = NewBuildString.replace("b", "");
            		NewBuildNumber = Double.parseDouble(NewBuildString) - 1;
            	}
            	else if(NewBuildString.contains("a")) {
            		NewBuildString = NewBuildString.replace("a", "");
            		NewBuildNumber = Double.parseDouble(NewBuildString) - 10;
            	}
            	else NewBuildNumber = Double.parseDouble(NewBuildString);
            }
            else 
            	NewVersionNumber = Double.parseDouble((newVersion.substring(0, newVersion.indexOf(".") + 1) + newVersion.substring(newVersion.indexOf(".") + 1).replace(".", "")).replace("b", "").replace("a", ""));
            
            /**\\**//**\\**//**\\**/
          /**\   CHECK VERSIONS   /**\
            /**\\**//**\\**//**\\**/
            
            if (NewVersionNumber > CurrentVersionNumber || (NewVersionNumber == CurrentVersionNumber && NewBuildNumber > CurrentBuildNumber)) 
				result = UpdateResult.UPDATE_AVAILABLE;
            else
            	result = UpdateResult.NO_UPDATE;
            return;
        }
        catch (Exception e) {
        	this.getLogger().info("There was an issue attempting to check for the latest version.");
        }
        result = UpdateResult.ERROR;
	}
	
	private void setupListeners()
    {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.blocks.BlockFromToListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.blocks.BlockGrowListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.blocks.BlockPistonExtendListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.blocks.BlockPistonRetractListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.blocks.BlockPlaceListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.blocks.StructureGrowListener(this), this);
		
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.BlockBreakListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.BlockPlaceListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.BlockPlaceToCreationsListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.EntityDamageByEntityListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.EntityShootBowListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.InventoryClickListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.InventoryOpenListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerArmorStandDestroyListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerArmorStandManipulateListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerArmorStandSpawnListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerCommandPreprocessListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerDeathListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerDropItemListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerFishListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerInteractEntityListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerInteractHorseListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerInteractListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerItemConsumeListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerLeashEntityListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerLevelChangeListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerPickupItemListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.creative.PlayerShearEntityListener(this), this);
		
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.hanging.HangingBreakListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.hanging.HangingPlaceListener(this), this);
		
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.entity.BreedingListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.entity.EntityChangeBlockListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.entity.EntityExplodeListener(this), this);
		
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.gamemodescontrol.PlayerCommandPreprocessListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.gamemodescontrol.PlayerGameModeChangeListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.gamemodescontrol.PlayerJoinListener(this), this);
		
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.multiinventories.PlayerGameModeChangeListener(this), this);
		
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.survival.BlockBreakListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.survival.BlockPlaceListener(this), this);
		
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.update.PlayerJoinListener(this), this);
		
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.items.PlayerInteractEntityListener(this), this);
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.items.PlayerInteractListener(this), this);
		
		pm.registerEvents(new com.net.h1karo.sharecontrol.listeners.world.WorldListener(this), this);
		
		mainconfig = new Configuration(this);
		lang = new LanguageFiles(this);
		metabase = new MetaBase(this);

        MobArena maPlugin = (MobArena)pm.getPlugin("MobArena");
        PVPArena pvpPlugin = (PVPArena)pm.getPlugin("pvparena");
        
        Essentials ess = (Essentials)pm.getPlugin("Essentials");
        
        if(maPlugin != null && maPlugin.isEnabled()) {
            am = maPlugin.getArenaMaster();
            foundMA = true;
            pm.registerEvents(new MobArenaEventListener(this), this);
        }
        if(pvpPlugin != null && pvpPlugin.isEnabled()) {
        	foundPVP = true;
            pm.registerEvents(new PvPArenaEventListener(this), this);
        }
        
        if(ess != null && ess.isEnabled()) {
        	foundEss = true;
        }
    }
	
	public static boolean getFoundMA()
    {
        return foundMA;
    }
	
    public static boolean getFoundPVP()
    {
        return foundPVP;
    }
    
    public static boolean getFoundEssentials()
    {
        return foundEss;
    }
}