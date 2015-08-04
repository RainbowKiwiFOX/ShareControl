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

package com.net.h1karo.sharecontrol.localization;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.net.h1karo.sharecontrol.ShareControl;

public class LanguageFiles {
	private static ShareControl main;
	public LanguageFiles(ShareControl h) {
		LanguageFiles.main = h;
    }
    
	private static FileConfiguration languageConfig = null;
    private static File languageConfigFile = null;
    
	
	public static void reloadlanguageConfig(String lang) {
    	if (languageConfigFile == null)	languageConfigFile = new File(main.getDataFolder(), "languages" + File.separator + lang + ".yml");
    	
    	languageConfig = YamlConfiguration.loadConfiguration(languageConfigFile);
    		InputStream defConfigStream = main.getResource(main.getDataFolder() +  "languages" + File.separator + lang + ".yml");
    		if (defConfigStream != null) {
				@SuppressWarnings("deprecation")
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
    			languageConfig.setDefaults(defConfig);
    		}
    }
    
    public static FileConfiguration getLangConfig(String lang) {
    	if (languageConfig == null) 	reloadlanguageConfig(lang);
    	return languageConfig;
    }
    
    public static void savelanguageConfig(String lang) {
    	if (languageConfig == null || languageConfigFile == null) return;
    	
    	if(lang.compareToIgnoreCase("ru") == 0)
    		RussianStrings(lang);
    	else
    		EnglishStrings(lang);
    	
    	languageConfig.options().header(header);
    	
    	getLangConfig(lang).set("Update.UpdateNotFound", UpdateNotFound);
    	getLangConfig(lang).set("Update.Available", UpdateAvailable);
    	
    	getLangConfig(lang).set("NoPermission", NoPerms);
    	
    	getLangConfig(lang).set("Events.Drop", OnDrop);
    	getLangConfig(lang).set("Events.MobsInteract", OnMonsterInteract);
    	getLangConfig(lang).set("Events.PlayerInteract", OnPlayerInteract);
    	getLangConfig(lang).set("Events.Inventory.Message", OnInventoryClick);
    	getLangConfig(lang).set("Events.Inventory.Material", OnInventoryClickMaterial);
    	getLangConfig(lang).set("Events.OpenInventory", OnOpenOtherInventory);
    	getLangConfig(lang).set("Events.BlockBreak.Message", OnBlockBreak);
    	getLangConfig(lang).set("Events.BlockPlace.Message", OnBlockPlace);
    	getLangConfig(lang).set("Events.BlockBreak.Material", OnBlockBreakMaterial);
    	getLangConfig(lang).set("Events.BlockPlace.Material", OnBlockPlaceMaterial);
    	getLangConfig(lang).set("Events.ShootBow", OnBowShoot);
    	getLangConfig(lang).set("Events.InteractWithWorlds", AnotherWorld);
    	getLangConfig(lang).set("Events.NotDropBlock", CreativeBlockNotDrop);
    	getLangConfig(lang).set("Events.NotBreakBlock", CreativeBlockNotBreak);
    	getLangConfig(lang).set("Events.EntityInteract.Message", EntityInteract);
    	getLangConfig(lang).set("Events.EntityInteract.Material", EntityInteractMaterial);
    	getLangConfig(lang).set("Events.UseBlock", UseBlocks);
    	getLangConfig(lang).set("Events.Fishing", OnFishing);
    	getLangConfig(lang).set("Events.ArmorStand", ArmorStand);
    	getLangConfig(lang).set("Events.ProhibitedCommand", OnCommand);
    	
    	getLangConfig(lang).set("GamemodesControl.NotAllowedGamemode", GamemodesControl);
    	
    	getLangConfig(lang).set("PlayersInGamemode.List", PlayerListInGamemode);
    	getLangConfig(lang).set("PlayersInGamemode.UnknownGamemode", UnknownGamemode);
    	getLangConfig(lang).set("PlayersInGamemode.NotFound", PlayerInGamemodeNotFound);
    	getLangConfig(lang).set("PlayersInGamemode.More", PlayerListInGamemodeMore);
    	
    	getLangConfig(lang).set("Menu.This", menu);
    	getLangConfig(lang).set("Menu.Reload", menureload);
    	getLangConfig(lang).set("Menu.Version", menuinfo);
    	getLangConfig(lang).set("Menu.Update", menuupdate);
    	getLangConfig(lang).set("Menu.GetList", menugetlist);
    	getLangConfig(lang).set("Menu.Tools", menutools);
    	getLangConfig(lang).set("Menu.ChangeTool", menusettool);
    	getLangConfig(lang).set("Menu.InfoTool", menuinfotool);
    	getLangConfig(lang).set("Menu.Check", menucheck);
    	
    	getLangConfig(lang).set("Using", using);
    	
    	getLangConfig(lang).set("Reload.Reloading", reloading);
    	getLangConfig(lang).set("Reload.Success", reloadsuccess);
    	
    	getLangConfig(lang).set("ChangeConfig.AddToBlockingPlacement", AMtoPlaceList);
    	getLangConfig(lang).set("ChangeConfig.AddToBlockingBreakage", AMtoBreakList);
    	getLangConfig(lang).set("ChangeConfig.AddToBlockingInventory", AMtoUseList);
    	getLangConfig(lang).set("ChangeConfig.RemoveFromBlockingPlacement", RMtoPlaceList);
    	getLangConfig(lang).set("ChangeConfig.RemoveFromBlockingBreakage", RMtoBreakList);
    	getLangConfig(lang).set("ChangeConfig.RemoveFromBlockingInventory", RMtoUseList);
    	getLangConfig(lang).set("ChangeConfig.ThisNotMaterialAndId", ThisNotMaterialandId);
    	
    	getLangConfig(lang).set("Tools.ChangeTool.Get", getsettool);
    	getLangConfig(lang).set("Tools.ChangeTool.Name", namesettool);
    	getLangConfig(lang).set("Tools.ChangeTool.Lore.1", loreST1);
    	getLangConfig(lang).set("Tools.ChangeTool.Lore.2", loreST2);
    	getLangConfig(lang).set("Tools.ChangeTool.Lore.3", loreST3);
    	getLangConfig(lang).set("Tools.InfoTool.Get", getinfotool);
    	getLangConfig(lang).set("Tools.InfoTool.Name", nameinfotool);
    	getLangConfig(lang).set("Tools.InfoTool.Lore.1", loreIT1);
    	getLangConfig(lang).set("Tools.InfoTool.Lore.2", loreIT2);
    	    	
    	getLangConfig(lang).set("Tools.Type", Type);
    	getLangConfig(lang).set("Tools.Types.Creative", CreativeType);
    	getLangConfig(lang).set("Tools.Types.Natural", NaturalType);
    	getLangConfig(lang).set("Tools.Name", Name);
    	getLangConfig(lang).set("Tools.ID", ID);
    	getLangConfig(lang).set("Tools.Coordinates", Coordinates);
    	getLangConfig(lang).set("Tools.Nickname", Nick);
    	getLangConfig(lang).set("Tools.UUID", UUID);
    	getLangConfig(lang).set("Tools.Gamemode", GM);
    	getLangConfig(lang).set("Tools.Health", Health);
    	getLangConfig(lang).set("Tools.Exp", Exp);
    	getLangConfig(lang).set("Tools.World", World);
    	getLangConfig(lang).set("Tools.BlockHas", BlockHas);
    	getLangConfig(lang).set("Tools.BlockNow", BlockNow);
    	
    	getLangConfig(lang).set("Gamemodes.Creative", Creative);
    	getLangConfig(lang).set("Gamemodes.Survival", Survival);
    	getLangConfig(lang).set("Gamemodes.Adventure", Adventure);
    	getLangConfig(lang).set("Gamemodes.Spectator", Spectator);
    	
    	getLangConfig(lang).set("Version", CurrentVersion);
    	getLangConfig(lang).set("DevelopmentTeam", DevelopmentTeam);
    	getLangConfig(lang).set("Site", WebSite);
    	getLangConfig(lang).set("Author", Author);
    	try {
    		getLangConfig(lang).save(languageConfigFile);
    	} 
    	catch (IOException ex) {
    		main.getLogger().log(Level.WARNING, "Could not save config to " + languageConfigFile, ex);
    	}
    	
    	languageConfigFile = null;
    	languageConfig = null;
    }
   
										/**\ * * * * * * * * \**/
										/**\ DEFAULT STRINGS \**/
    									/**\ * * * * * * * * \**/
    
    	/**\ ENGLISH \**/
    	/**\---------\**/
    
    private static void EnglishStrings(String lang) {
    	header = 
    	  	  " |-------------------------------| \n"
    	  	+ " | Language file of ShareControl | \n"
    	  	+ " |-------------------------------| \n"
    	  	+ "\n"
    	  	+ " HELP:\n"
    	  	+ " %block% - block that place\\break\\use \n"
    	  	+ " %item% - item that use \n"
    	  	+ " %gamemode% - the gamemode of player \n"
    	  	+ " %type% - type of block (natural or creative) \n"
    	  	+ " %coords% - coordinates of block \n"
    	  	+ " %name% - block name \n"
    	  	+ " %id% - block ID \n"
    	  	+ " %command% - ShareControl's command \n"
    	  	+ " %plugin% - ShareControl \n"
    	  	+ " %update% - new version \n"
    	  	+ " %version% - the current version \n"
    	  	+ " %link% - link to site of plugin \n"
    	  	+ " %development-team% - development team of the plugin \n"
    	  	+ " %nickname% - player \n"
    	  	+ " %uuid% - universally unique identifier of player";
    	
    	UpdateNotFound = getLangConfig(lang).getString("Update.UpdateNotFound", "&7Updates not found. You have the latest version!");
		UpdateAvailable = getLangConfig(lang).getString("Update.Available", "&7An update is available: &9%update%&7, download at &9%link%&7!");
		
		NoPerms = getLangConfig(lang).getString("NoPermission", "&cYou do not have permission to do this!");
		
		OnDrop = getLangConfig(lang).getString("Events.Drop", "&cYou can not throw things!");
		OnMonsterInteract = getLangConfig(lang).getString("Events.MobsInteract", "&cYou can not interact with mobs!");
		OnPlayerInteract = getLangConfig(lang).getString("Events.PlayerInteract", "&cYou can not touch the players!");
		OnInventoryClick = getLangConfig(lang).getString("Events.Inventory.Message", "&cYou can not use this item!");
		OnInventoryClickMaterial = getLangConfig(lang).getString("Events.Inventory.Material", "&cYou can not use this &6%item%&c!");
		OnOpenOtherInventory = getLangConfig(lang).getString("Events.OpenInventory", "&cYou can not used the inventory!");
		OnBlockBreak = getLangConfig(lang).getString("Events.BlockBreak.Message", "&cYou can not break this block!");
		OnBlockPlace = getLangConfig(lang).getString("Events.BlockPlace.Message", "&cYou can not place this block!");
		OnBlockBreakMaterial = getLangConfig(lang).getString("Events.BlockBreak.Material", "&cYou can not break this &6%block%&c!");
		OnBlockPlaceMaterial = getLangConfig(lang).getString("Events.BlockPlace.Material", "&cYou can not place this &6%block%&c!");
		OnBowShoot = getLangConfig(lang).getString("Events.ShootBow", "&cYou can not shoot a bow!");
		AnotherWorld = getLangConfig(lang).getString("Events.InteractWithWorlds", "&cYou can not interact with the world around you!");
		CreativeBlockNotDrop = getLangConfig(lang).getString("Events.NotDropBlock", "&7This block is has not dropped because he is from the creative mode!");
		CreativeBlockNotBreak = getLangConfig(lang).getString("Events.NotBreakBlock", "&7This block is has not breaked because he is from the creative mode!");
		EntityInteract = getLangConfig(lang).getString("Events.EntityInteract.Message", "&cYou can not use this item!");
		EntityInteractMaterial = getLangConfig(lang).getString("Events.EntityInteract.Material", "&cYou can not use this &6%item%&c!");
		UseBlocks = getLangConfig(lang).getString("Events.UseBlock", "&cYou can not use it!");
		OnFishing = getLangConfig(lang).getString("Events.Fishing", "&cYou can not fish!");
		ArmorStand = getLangConfig(lang).getString("Events.ArmorStand", "&cYou not can interact with armor stand!");
		OnCommand = getLangConfig(lang).getString("Events.ProhibitedCommand", "&cYou not can use this command!");
		
		GamemodesControl = getLangConfig(lang).getString("GamemodesControl.NotAllowedGamemode", "&cYou can not go in gamemode &6%gamemode%&c!");
		
		PlayerListInGamemode = getLangConfig(lang).getString("PlayersInGamemode.List", "&7Players in &9%gamemode%&7 mode: &9%list%&7");
		UnknownGamemode = getLangConfig(lang).getString("PlayersInGamemode.UnknownGamemode", "&cUnknown gamemode: &6%gamemode%&c.");
		PlayerInGamemodeNotFound = getLangConfig(lang).getString("PlayersInGamemode.NotFound", "&7Players in the gamemode &9%gamemode%&7 not found!");
		PlayerListInGamemodeMore = getLangConfig(lang).getString("PlayersInGamemode.More", "&7To find out detailed information about the player, type &9/sc check <ник игрока>&7!");
		
		menu = getLangConfig(lang).getString("Menu.This", "&9%command% &f- this menu,");
		menureload = getLangConfig(lang).getString("Menu.Reload", "&9%command% &f- reloading,");
		menuinfo = getLangConfig(lang).getString("Menu.Version", "&9%command% &f- information,");
		menuupdate = getLangConfig(lang).getString("Menu.Update", "&9%command% &f- check updates,");
		menugetlist = getLangConfig(lang).getString("Menu.GetList", "&9%command% &f- get a list of players who use this gamemode,");
		menutools = getLangConfig(lang).getString("Menu.Tools", "&9%command% &f- list of tools,");
		menusettool = getLangConfig(lang).getString("Menu.ChangeTool", "&9%command% &f- get changing tool,");
		menuinfotool = getLangConfig(lang).getString("Menu.InfoTool", "&9%command% &f- get information tool.");
		menucheck = getLangConfig(lang).getString("Menu.Check", "&9%command% &f- see information about player.");
		
		
		using = getLangConfig(lang).getString("Using", "&7Using: &c%command%");
		
		reloading = getLangConfig(lang).getString("Reload.Reloading", "&7Reloading...");
		reloadsuccess = getLangConfig(lang).getString("Reload.Success", "&7Reloading the plugin successfully completed!");
		
		AMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingPlacement", "&7The block &9%material%&7 successfully added to list of blocks that are prohibited to place!");
		AMtoBreakList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingBreakage", "&7The block &9%material%&7 successfully added to list of blocks that are prohibited to break!");
		AMtoUseList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingInventory", "&7The item &9%material%&7 successfully added to list of item that are prohibited to use!");
		RMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingPlacement", "&7The block &9%material%&7 successfully removed from list of blocks that are prohibited to place!");
		RMtoBreakList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingBreakage", "&7The block &9%material%&7 successfully removed from list of blocks that are prohibited to break!");
		RMtoUseList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingInventory", "&7The item &9%material%&7 successfully removed from list of items that are prohibited to use!");
		ThisNotMaterialandId = getLangConfig(lang).getString("ChangeConfig.ThisNotMaterialAndId", "&7Error: &9%material%&7 is not a material or id of block or item.");
		
		getsettool = getLangConfig(lang).getString("Tools.ChangeTool.Get", "&7You got the &9change tool&7!");
		namesettool = getLangConfig(lang).getString("Tools.ChangeTool.Name", "&9&lChange Tool");
		loreST1 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.1", "&7Left click to &cSET&7 the Game Mode of a block");
		loreST2 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.2", "&7Right click to &cREMOVE&7 the Game Mode of a block");
		loreST3 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.3", "&7Tool by %plugin%");
		getinfotool = getLangConfig(lang).getString("Tools.InfoTool.Get", "&7You got the &9information tool&7!");
		nameinfotool = getLangConfig(lang).getString("Tools.InfoTool.Name", "&9&lInformation Tool");
		loreIT1 = getLangConfig(lang).getString("Tools.InfoTool.Lore.1", "&7Left or right click to get the Game Mode of a block or a player");
		loreIT2 = getLangConfig(lang).getString("Tools.InfoTool.Lore.2", "&7Tool by %plugin%");
		Type = getLangConfig(lang).getString("Tools.Type", "&7Type: &9%type%");
		CreativeType = getLangConfig(lang).getString("Tools.Types.Creative", "creative");
		NaturalType = getLangConfig(lang).getString("Tools.Types.Natural", "natural");
		Name = getLangConfig(lang).getString("Tools.Name", "&7Name: &9%name%");
		ID = getLangConfig(lang).getString("Tools.ID", "&7ID: &9%id%");
		Coordinates = getLangConfig(lang).getString("Tools.Coordinates", "&7Coordinates: &9%coords%");
		World = getLangConfig(lang).getString("Tools.World", "&7World: &9%world%");
		Nick = getLangConfig(lang).getString("Tools.Nickname", "&7Nickname: &9%nickname%");
		UUID =  getLangConfig(lang).getString("Tools.UUID", "&7UUID: &9%uuid%");
		GM = getLangConfig(lang).getString("Tools.Gamemode", "&7Gamemode: &9%gamemode%");
		Health = getLangConfig(lang).getString("Tools.Health", "&7Health: &9%health%");
		Exp = getLangConfig(lang).getString("Tools.Exp", "&7Exp: &9%exp%");
		BlockHas = getLangConfig(lang).getString("Tools.BlockHas", "&7This block is has a &6%type%&7!");
		BlockNow = getLangConfig(lang).getString("Tools.BlockNow", "&7Now this block &6%type%&7!");
		
		Creative = getLangConfig(lang).getString("Gamemodes.Creative", "creative");
		Survival = getLangConfig(lang).getString("Gamemodes.Survival", "survival");
		Adventure = getLangConfig(lang).getString("Gamemodes.Adventure", "adventure");
		Spectator = getLangConfig(lang).getString("Gamemodes.Spectator", "spectator");
		
		CurrentVersion = getLangConfig(lang).getString("Version", "&7The current version of the plugin: &9%version%&7!");
		DevelopmentTeam = getLangConfig(lang).getString("DevelopmentTeam", "&7Development team: &9%development-team%");
		WebSite = getLangConfig(lang).getString("Site", "&7Site: &9%link%");
		Author = getLangConfig(lang).getString("Author", "&9%nickname% &7[&cauthor&7]");
    }
    
	/**\ RUSSIAN \**/
	/**\---------\**/
    
    private static void RussianStrings(String lang) {
    	header = 
    	  	  " |-------------------------------| \n"
    	  	+ " | Language file of ShareControl | \n"
    	  	+ " |-------------------------------| \n"
    	  	+ "\n"
    	  	+ " HELP:\n"
    	  	+ " %block% - блок \n"
    	  	+ " %item% - предмет \n"
    	  	+ " %gamemode% - игровой режим игрока\n"
    	  	+ " %type% - тип блока (естественный или творческий) \n"
    	  	+ " %coords% - координаты \n"
    	  	+ " %name% - название блока \n"
    	  	+ " %id% - ID блока \n"
    	  	+ " %command% - команда \n"
    	  	+ " %plugin% - ShareControl \n"
    	  	+ " %update% - новая версия \n"
    	  	+ " %version% - установленная версия \n"
    	  	+ " %link% - ссылка на сайт плагина \n"
    	  	+ " %development-team% - команда разработчиков \n"
    	  	+ " %nickname% - ник игрока \n"
    	  	+ " %uuid% - универсальный уникальный индетификатор игрока";
    	
    	UpdateNotFound = getLangConfig(lang).getString("Update.UpdateNotFound", "&7Обновлений не найдено. Вы используете последнюю версию!");
		UpdateAvailable = getLangConfig(lang).getString("Update.Available", "&7Вышло обновление: &9%update%&7, скачать по этой ссылке: &9%link%&7!");
		
		NoPerms = getLangConfig(lang).getString("NoPermission", "&cУ Вас недостаточно прав для этого!");
		
		OnDrop = getLangConfig(lang).getString("Events.Drop", "&cВы не можете выбрасывать вещи!");
		OnMonsterInteract = getLangConfig(lang).getString("Events.MobsInteract", "&cВы не можете взаимодействовать с мобами!");
		OnPlayerInteract = getLangConfig(lang).getString("Events.PlayerInteract", "&cВы не можете взаимодействовать с игроками!");
		OnInventoryClick = getLangConfig(lang).getString("Events.Inventory.Message", "&cВы не можете использовать этот предмет!");
		OnInventoryClickMaterial = getLangConfig(lang).getString("Events.Inventory.Material", "&cВы не можете использовать &6%item%&c!");
		OnOpenOtherInventory = getLangConfig(lang).getString("Events.OpenInventory", "&cВы не можете использовать инвентарь!");
		OnBlockBreak = getLangConfig(lang).getString("Events.BlockBreak.Message", "&cВы не можете ломать этот блок!");
		OnBlockPlace = getLangConfig(lang).getString("Events.BlockPlace.Message", "&cВы не можете ставить этот блок!");
		OnBlockBreakMaterial = getLangConfig(lang).getString("Events.BlockBreak.Material", "&cВы не можете ломать &6%block%&c!");
		OnBlockPlaceMaterial = getLangConfig(lang).getString("Events.BlockPlace.Material", "&cВы не можете ставить &6%block%&c!");
		OnBowShoot = getLangConfig(lang).getString("Events.ShootBow", "&cВы не можете стрелять из лука!");
		AnotherWorld = getLangConfig(lang).getString("Events.InteractWithWorlds", "&cВы не можете взаимодействовать с этим миром!");
		CreativeBlockNotDrop = getLangConfig(lang).getString("Events.NotDropBlock", "&7Этот блок из творчества, поэтому он не выпал!");
		CreativeBlockNotBreak = getLangConfig(lang).getString("Events.NotBreakBlock", "&7Этот блок из творчества, поэтому вы не можете его сломать!");
		EntityInteract = getLangConfig(lang).getString("Events.EntityInteract.Message", "&cВы не можете использовать этот предмет!");
		EntityInteractMaterial = getLangConfig(lang).getString("Events.EntityInteract.Material", "&cВы не можете использовать этот &6%item%&c!");
		UseBlocks = getLangConfig(lang).getString("Events.UseBlock", "&cВы не можете использовать это!");
		OnFishing = getLangConfig(lang).getString("Events.Fishing", "&cВы не можете рыбачить!");
		ArmorStand = getLangConfig(lang).getString("Events.ArmorStand", "&cВы не можете взаимодействовать с стойкой для брони!");
		OnCommand = getLangConfig(lang).getString("Events.ProhibitedCommand", "&cВы не можете использовать эту команду!");
		
		GamemodesControl = getLangConfig(lang).getString("GamemodesControl.NotAllowedGamemode", "&cВы не можете перейти в режим игры &6%gamemode%&c!");
		
		PlayerListInGamemode = getLangConfig(lang).getString("PlayersInGamemode.List", "&7Игроки в режиме игры &9%gamemode%&7: &9%list%&7");
		UnknownGamemode = getLangConfig(lang).getString("PlayersInGamemode.UnknownGamemode", "&cНеизвестный тип игрового режима: &6%gamemode%&c.");
		PlayerInGamemodeNotFound = getLangConfig(lang).getString("PlayersInGamemode.NotFound", "&7Игроки в игровом режиме &9%gamemode%&7 не найдены!");
		PlayerListInGamemodeMore = getLangConfig(lang).getString("PlayersInGamemode.More", "&7Чтобы узнать подробную информацию о игроке, напишите &9/sc check <ник игрока>&7!");
		
		
		menu = getLangConfig(lang).getString("Menu.This", "&9%command% &f- данное меню,");
		menureload = getLangConfig(lang).getString("Menu.Reload", "&9%command% &f- перезагрузка,");
		menuinfo = getLangConfig(lang).getString("Menu.Version", "&9%command% &f- информация,");
		menuupdate = getLangConfig(lang).getString("Menu.Update", "&9%command% &f- проверить обновления,");
		menugetlist = getLangConfig(lang).getString("Menu.GetList", "&9%command% &f- получить список игроков, которые используют данный режим,");
		menutools = getLangConfig(lang).getString("Menu.Tools", "&9%command% &f- список инструментов,");
		menusettool = getLangConfig(lang).getString("Menu.ChangeTool", "&9%command% &f- получить изменяющий предмет,");
		menuinfotool = getLangConfig(lang).getString("Menu.InfoTool", "&9%command% &f- получить информационный предмет,");
		menucheck = getLangConfig(lang).getString("Menu.Check", "&9%command% &f- получить информацию о игроку.");
		
		using = getLangConfig(lang).getString("Using", "&7Использование: &c%command%");
		
		reloading = getLangConfig(lang).getString("Reload.Reloading", "&7Перезагрузка...");
		reloadsuccess = getLangConfig(lang).getString("Reload.Success", "&7Перезагрузка плагина завершена успешно!");
		
		AMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingPlacement", "&7Блок &9%material%&7 успешно добавлен в список блоков, которые запрещено ставить!");
		AMtoBreakList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingBreakage", "&7Блок &9%material%&7 успешно добавлен в список блоков, которые запрещено ломать!");
		AMtoUseList = getLangConfig(lang).getString("ChangeConfig.AddToBlockingInventory", "&7Предмет &9%material%&7 успешно добавлен в список предметов, которые запрещено использовать!");
		RMtoPlaceList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingPlacement", "&7Блок &9%material%&7 успешно удален из списка блоков, которые запрещено ставить!");
		RMtoBreakList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingBreakage", "&7Блок &9%material%&7 успешно удален из списка блоков, которые запрещено ломать!");
		RMtoUseList = getLangConfig(lang).getString("ChangeConfig.RemoveFromBlockingInventory", "&7Предмет &9%material%&7 успешно удален из списка предметов, которые запрещено использовать!");
		ThisNotMaterialandId = getLangConfig(lang).getString("ChangeConfig.ThisNotMaterialAndId", "&7Ошибка: &9%material%&7 не является материалом или ID блока или предмета.");
		
		getsettool = getLangConfig(lang).getString("Tools.ChangeTool.Get", "&7Вы получили &9изменяющий предмет&7!");
		namesettool = getLangConfig(lang).getString("Tools.ChangeTool.Name", "&9&lИзменяющий предмет");
		loreST1 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.1", "&7Нажмите ЛКМ, чтобы &cИЗМЕНИТЬ&7 режим на &cтворческий");
		loreST2 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.2", "&7Нажмите ПКМ, чтобы &cИЗМЕНИТЬ&7 режим на &cестественный");
		loreST3 = getLangConfig(lang).getString("Tools.ChangeTool.Lore.3", "&7Инструмент плагина %plugin%");
		getinfotool = getLangConfig(lang).getString("Tools.InfoTool.Get", "&7Вы получили &9информационный предмет&7!");
		nameinfotool = getLangConfig(lang).getString("Tools.InfoTool.Name", "&9&lИнформационный предмет");
		loreIT1 = getLangConfig(lang).getString("Tools.InfoTool.Lore.1", "&7Нажмите &cЛКМ&7 или &cПКМ&7, чтобы получить информацию о блоке или игроке");
		loreIT2 = getLangConfig(lang).getString("Tools.InfoTool.Lore.2", "&7Инструмент плагина %plugin%");
		Type = getLangConfig(lang).getString("Tools.Type", "&7Тип: &9%type%");
		CreativeType = getLangConfig(lang).getString("Tools.Types.Creative", "творческий");
		NaturalType = getLangConfig(lang).getString("Tools.Types.Natural", "естественный");
		Name = getLangConfig(lang).getString("Tools.Name", "&7Название: &9%name%");
		ID = getLangConfig(lang).getString("Tools.ID", "&7ID: &9%id%");
		Coordinates = getLangConfig(lang).getString("Tools.Coordinates", "&7Координаты: &9%coords%");
		World = getLangConfig(lang).getString("Tools.World", "&7Мир: &9%world%");
		Nick = getLangConfig(lang).getString("Tools.Nickname", "&7Ник: &9%nickname%");
		UUID =  getLangConfig(lang).getString("Tools.UUID", "&7Уникальный индетификатор (UUID): &9%uuid%");
		GM = getLangConfig(lang).getString("Tools.Gamemode", "&7Режим: &9%gamemode%");
		Health = getLangConfig(lang).getString("Tools.Health", "&7Здоровье: &9%health%");
		Exp = getLangConfig(lang).getString("Tools.Exp", "&7Опыт: &9%exp%");
		BlockHas = getLangConfig(lang).getString("Tools.BlockHas", "&7Этот блок уже &6%type%&7!");
		BlockNow = getLangConfig(lang).getString("Tools.BlockNow", "&7Теперь этот блок &6%type%&7!");
		
		Creative = getLangConfig(lang).getString("Gamemodes.Creative", "творчество");
		Survival = getLangConfig(lang).getString("Gamemodes.Survival", "выживание");
		Adventure = getLangConfig(lang).getString("Gamemodes.Adventure", "приключение");
		Spectator = getLangConfig(lang).getString("Gamemodes.Spectator", "наблюдение");
		
		CurrentVersion = getLangConfig(lang).getString("Version", "&7Текущая версия плагина: &9%version%&7!");
		DevelopmentTeam = getLangConfig(lang).getString("DevelopmentTeam", "&7Команда разработчиков: &9%development-team%");
		WebSite = getLangConfig(lang).getString("Site", "&7Сайт: &9%link%");
		Author = getLangConfig(lang).getString("Author", "&9%nickname% &7[&cавтор&7]");
    }
    
    private static String header;

	public static String UpdateNotFound;
	public static String UpdateAvailable;
	
	public static String OnDrop, OnMonsterInteract, OnPlayerInteract, OnInventoryClick, OnInventoryClickMaterial, OnOpenOtherInventory, OnBlockBreak, OnBlockPlace, OnBlockBreakMaterial, OnBlockPlaceMaterial, OnBowShoot, AnotherWorld, CreativeBlockNotDrop, CreativeBlockNotBreak, EntityInteract, EntityInteractMaterial, UseBlocks, OnFishing, ArmorStand, OnCommand;
	
	public static String GamemodesControl;
	
	public static String NoPerms;
	
	public static String menu;
	public static String menureload;
	public static String menuinfo;
	public static String menuupdate;
	public static String menutools;
	public static String menusettool;
	public static String menuinfotool;
	public static String menucheck;
	public static String menugetlist;
	
	public static String using;
	
	public static String AMtoBreakList, AMtoPlaceList, AMtoUseList;
	public static String RMtoBreakList, RMtoPlaceList, RMtoUseList;
	public static String ThisNotMaterialandId;
	
	public static String reloading, reloadsuccess;
	
	public static String getinfotool, getsettool;
	
	public static String namesettool, loreST1, loreST2, loreST3;
	public static String nameinfotool, loreIT1, loreIT2;
	
	public static String CreativeType, NaturalType, Name, Coordinates, Type, ID, Nick, GM, Health, Exp, UUID, World;
	
	public static String Creative, Survival, Adventure, Spectator;
	
	public static String PlayerListInGamemode, PlayerListInGamemodeMore, UnknownGamemode, PlayerInGamemodeNotFound;
	
	public static String BlockHas;
	public static String BlockNow;
	
	public static String CurrentVersion;
	public static String DevelopmentTeam;
	public static String WebSite;
	public static String Author;
}
