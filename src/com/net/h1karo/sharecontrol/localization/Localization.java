package com.net.h1karo.sharecontrol.localization;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.net.h1karo.sharecontrol.MessageManager;
import com.net.h1karo.sharecontrol.Permissions;
import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.MessageManager.MessageType;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.items.utils;
import com.net.h1karo.sharecontrol.listeners.BasicHandlers;

public class Localization {
	
	@SuppressWarnings("unused")
	private static ShareControl main;
	//
	// UPDATES MESSAGES
	//
	
	public static String name = "";
	public static String LatVersion = "";
	public static String link = "http://dev.bukkit.org/bukkit-plugins/sharecontrol/";

	public static void UpdateNotFound(CommandSender p) {
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.UpdateNotFound);
		MessageManager.getManager().msg(p, MessageType.UPDATE, msg);
	}
	
	public static void UpdateFoundPlayer(CommandSender sender) {
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(!Permissions.perms(p, "update") || !Configuration.versionCheck) return;
		}
		else
			if(!Configuration.versionCheck) return;
		name = "ShareControl v" + ShareControl.newVersion;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.UpdateAvailable.replace("%update%", name).replace("%link%", link));
	    MessageManager.getManager().msg(sender, MessageType.UPDATE, msg);
	}
	
    public static String prefix = ChatColor.BLUE + "Share" + ChatColor.WHITE + "Control" + ChatColor.RESET;
    public static String prefixab = ChatColor.BLUE + "S" + ChatColor.WHITE + "C" + ChatColor.RESET;
	
    //
	// EVENT MESSAGES
    //
    
    public static void NoPerms(Player p) 
	{
    	String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.NoPerms);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void dropNotify(Player p) 
	{
		if(!Configuration.CreativeNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnDrop);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}

	public static void MonsterInteractNotify(Player p) 
	{
		if(!Configuration.CreativeNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnMonsterInteract);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void Fishing(Player p) 
	{
		if(!Configuration.CreativeNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnFishing);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void Bow(Player p) 
	{
		if(!Configuration.CreativeNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnBowShoot);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void PlayerInteractNotify(Player p) 
	{
		if(!Configuration.CreativeNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnPlayerInteract);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void invNotify(Material typeThisItem, Player p) 
	{
		if(!Configuration.CreativeNotify) return;
		String msg;
		String strThisItem = typeThisItem.toString().toLowerCase().replace("_", " ");
		if(Configuration.material)
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnInventoryClickMaterial.replace("%item%", strThisItem));
		else
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnInventoryClick);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void openInv(Player p)
	{
		if(!Configuration.CreativeNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnOpenOtherInventory);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void PlaceBlock(Material typeThisBlock, Player p)
	{
		if(!Configuration.CreativeNotify) return;
		String strThisBlock = typeThisBlock.toString().toLowerCase().replace("_", " ");
		String msg;		
		if(Configuration.material) 
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnBlockPlaceMaterial.replace("%block%", strThisBlock));
		else 
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnBlockPlace);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void BreakBlock(Material typeThisBlock, Player p)
	{
		if(!Configuration.CreativeNotify) return;
		String strThisBlock = typeThisBlock.toString().toLowerCase().replace("_", " ");
		String msg;
		if(Configuration.material) 
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnBlockBreakMaterial.replace("%block%", strThisBlock));
		else 
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnBlockBreak);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void InBlockWorld(Player p)
	{
		if(!Configuration.CreativeNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.AnotherWorld);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void SurvivalBlockNotDrop(Player p) {
		if(!Configuration.SurvivalNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CreativeBlockNotDrop);
		MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
	}
	
	public static void SurvivalBlockNotBreak(Player p) {
		if(!Configuration.SurvivalNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CreativeBlockNotBreak);
		MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
	}
	
	public static void EntityUseNotify(EntityType typeThisEntity, Player p) {
		if(!Configuration.CreativeNotify) return;
		String strThisItem = typeThisEntity.toString().toLowerCase().replace("_", " ");
		String msg;
		if(Configuration.material) 
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.EntityInteractMaterial.replace("%item%", strThisItem));
		else 
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.EntityInteract);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void interact(Player p) {
		if(!Configuration.CreativeNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.UseBlocks);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void ANEntityUseNotify(EntityType typeThisEntity, Player p) {
		if(!Configuration.CreativeNotify) return;
		String strThisItem = typeThisEntity.toString().toLowerCase().replace("_", " ");
		String msg;
		if(Configuration.material) 
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.EntityInteractMaterial.replace("%item%", strThisItem));
		else 
			msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.EntityInteract);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void ANinteract(Player p) {
		if(!Configuration.CreativeNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.UseBlocks);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	
	public static void ProhibitedCmd(Player p) {
		if(!Configuration.CreativeNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.OnCommand);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	public static void ArmorStand(Player p) {
		if(!Configuration.CreativeNotify) return;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.ArmorStand);
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	

	public static void NotAllowedGamemode(Player p, String gamemode) {
		if(gamemode.compareToIgnoreCase("creative") == 0) 
			gamemode = LanguageFiles.Creative;
		if(gamemode.compareToIgnoreCase("survival") == 0) 
			gamemode = LanguageFiles.Survival;
		if(gamemode.compareToIgnoreCase("adventure") == 0) 
			gamemode = LanguageFiles.Adventure;
		if(gamemode.compareToIgnoreCase("spectator") == 0) 
			gamemode = LanguageFiles.Spectator;
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.GamemodesControl.replace("%gamemode%", gamemode));
		MessageManager.getManager().msg(p, MessageType.BAD, msg);
	}
	
	//
	// COMMANDS MESSAGES
	//
	
	public static void helpMenu(CommandSender sender)
	{
		String command = " /sharecontrol";
		String msg1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menu.replace("%command%", command));
		command = " /sc reload";
		String msg2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menureload.replace("%command%", command));
		command = " /sc version";
		String msg3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menuinfo.replace("%command%", command));
		command = " /sc update";
		String msg4 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menuupdate.replace("%command%", command));
		command = " /sc tools";
		String msg5 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menutools.replace("%command%", command));
		command = " /sc tools changetool";
		String msg6 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menusettool.replace("%command%", command));
		command = " /sc tools infotool";
		String msg7 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menuinfotool.replace("%command%", command));
		command = " /sc check <nickname>";
		String msg8 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menucheck.replace("%command%", command));
		MessageManager.getManager().msg(sender, MessageType.HELP, "========[" + prefix + ChatColor.BLUE + " Menu" + ChatColor.GRAY + "]========");
		MessageManager.getManager().msg(sender, MessageType.HELP, msg1);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg2);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg3);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg4);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg5);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg6);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg7);
		MessageManager.getManager().msg(sender, MessageType.HELP, msg8);
		MessageManager.getManager().msg(sender, MessageType.HELP, ChatColor.GRAY + "=================================");
	}
	
	public static void reloadMsg(CommandSender sender)
	{
		String msg1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.reloading);
		String msg2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.reloadsuccess);
		MessageManager.getManager().msg(sender, MessageType.PLINFO, msg1);
		Configuration.loadCfg();
		Configuration.saveCfg();
		if(ShareControl.error) {
			Configuration.Error(sender);
		}
		else MessageManager.getManager().msg(sender, MessageType.PLINFO, msg2);
	}

	public static void infoTools(CommandSender p) {
		
		String command = " /sc tools";
		String msg1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menu.replace("%command%", command));
		command = " /sc tools changetool";
		String msg2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menusettool.replace("%command%", command));
		command = " /sc tools infotool";
		String msg3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.menuinfotool.replace("%command%", command));
		
		MessageManager.getManager().msg(p, MessageType.HELP, "======[" + prefix + ChatColor.BLUE + " Tool Menu" + ChatColor.GRAY + "]======");
		MessageManager.getManager().msg(p, MessageType.HELP, msg1);
		MessageManager.getManager().msg(p, MessageType.HELP, msg2);
		MessageManager.getManager().msg(p, MessageType.HELP, msg3);
		MessageManager.getManager().msg(p, MessageType.HELP, ChatColor.GRAY + "=================================");
	}
	

	public static void getGamemode(CommandSender sender, String nickname) {
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CheckGamemode.replace("%nickname%", nickname).replace("%gamemode%", Bukkit.getPlayer(nickname).getGameMode().toString()));
		MessageManager.getManager().msg(sender, MessageType.BAD, msg);
	}
	
	 // TOOLS
	public static void getInfoTool(CommandSender p) {
		if(!(p instanceof Player))
		{
			MessageManager.getManager().msg(p, MessageType.HELP, "This command only for players!");
			return;
		}
		
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.getinfotool);

		MessageManager.getManager().msg(p, MessageType.INFO, msg);
		
		String nameIT = ChatColor.translateAlternateColorCodes('&', LanguageFiles.nameinfotool);
		String loreStr1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreIT1);
		String loreStr2;
		if(LanguageFiles.loreIT2.contains("%plugin%"))
			loreStr2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreIT2.replace("%plugin%", prefix));
		else
			loreStr2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreIT2);

		List<String> loreIT = Arrays.asList(loreStr1, loreStr2);
		ItemStack infotool = utils.setMeta(new ItemStack(Material.BLAZE_POWDER), nameIT, loreIT);
		
		((Player) p).getInventory().addItem(infotool);
	}

	public static void getSetTool(CommandSender p)
	{
		if(!(p instanceof Player))
		{
			MessageManager.getManager().msg(p, MessageType.HELP, "This command only for players!");
			return;
		}
		String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.getsettool);
		MessageManager.getManager().msg(p, MessageType.INFO, msg);
		
		String nameST = ChatColor.translateAlternateColorCodes('&', LanguageFiles.namesettool);
		String loreST1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST1);
		String loreST2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST2);
		String loreST3;
		if(LanguageFiles.loreST3.contains("%plugin%"))
			loreST3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST3.replace("%plugin%", prefix));
		else
			loreST3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.loreST3);
		List<String> loreST = Arrays.asList(loreST1, loreST2, loreST3);
		ItemStack settool = utils.setMeta(new ItemStack(Material.MAGMA_CREAM), nameST, loreST);
		
		((Player) p).getInventory().addItem(settool);		
	}

	@SuppressWarnings("deprecation")
	public static void BlockInfo(Player p, Block b)
	{
		String id = b.getTypeId() + "";
		String material = b.getType().toString();
		String coordinates = b.getX() + ", " + b.getY() + ", " + b.getZ();
		String type;
		
		if(BasicHandlers.InBase(b))
			type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CreativeType);
		else
			type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.NaturalType);
		
		String msg1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Name.replace("%name%", material));
		String msg2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.ID.replace("%id%", id));
		String msg3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Coordinates.replace("%coords%", coordinates));
		String msg4 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Type.replace("%type%", type));
		
		MessageManager.getManager().msg(p, MessageType.HELP, "---[ " + prefixab + ChatColor.BLUE + " Block Information" + ChatColor.GRAY + " ]---");
		MessageManager.getManager().msg(p, MessageType.HELP, msg1);
		MessageManager.getManager().msg(p, MessageType.HELP, msg2);
		MessageManager.getManager().msg(p, MessageType.HELP, msg3);
		MessageManager.getManager().msg(p, MessageType.HELP, msg4);
	}
	
	public static void PlayerInfo(Player p, Player m)
	{
		String gamemode = m.getGameMode() + "";
		String name = m.getDisplayName();
		String uuid = m.getUniqueId().toString();
		double health = m.getHealthScale();
		float exp = m.getExp();
		
		String msg1 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Nick.replace("%nickname%", name));
		String msg2 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.UUID.replace("%uuid%", uuid));
		String msg3 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.GM.replace("%gamemode%", gamemode));
		String msg4 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Health.replace("%health%", health + ""));
		String msg5 = ChatColor.translateAlternateColorCodes('&', LanguageFiles.Exp.replace("%exp%", exp + ""));
		
		MessageManager.getManager().msg(p, MessageType.HELP, "----[ " + ChatColor.BLUE + "Player Information" + ChatColor.GRAY + " ]----");
		MessageManager.getManager().msg(p, MessageType.HELP, msg1);
		MessageManager.getManager().msg(p, MessageType.HELP, msg2);
		MessageManager.getManager().msg(p, MessageType.HELP, msg3);
		MessageManager.getManager().msg(p, MessageType.HELP, msg4);
		MessageManager.getManager().msg(p, MessageType.HELP, msg5);
	}
	
	public static void SetBlockType(Player p, Block b, PlayerInteractEvent e)
	{
		if(BasicHandlers.InBase(b) && e.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			e.setCancelled(true);
			String type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CreativeType);
			String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.BlockHas.replace("%type%", type));
			MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
			return;
		}
		
		if(!BasicHandlers.InBase(b) && e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			String type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.NaturalType);
			String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.BlockHas.replace("%type%", type));
			MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
			return;
		}
		
		if(BasicHandlers.InBase(b) && e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			String type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.NaturalType);
			String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.BlockNow.replace("%type%", type));
			MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
			BasicHandlers.AddofDatabase(b);
			return;
		}
		
		if(!BasicHandlers.InBase(b) && e.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			e.setCancelled(true);
			String type = ChatColor.translateAlternateColorCodes('&', LanguageFiles.CreativeType);
			String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.BlockNow.replace("%type%", type));
			MessageManager.getManager().msg(p, MessageType.PLINFO, msg);
			
			BasicHandlers.AddofDatabase(b);
			return;
		}
	}
}
