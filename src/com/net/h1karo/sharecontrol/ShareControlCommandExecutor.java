package com.net.h1karo.sharecontrol;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.net.h1karo.sharecontrol.MessageManager.MessageType;
import com.net.h1karo.sharecontrol.ShareControl.UpdateResult;
import com.net.h1karo.sharecontrol.localization.LanguageFiles;
import com.net.h1karo.sharecontrol.localization.Localization;

public class ShareControlCommandExecutor implements CommandExecutor {
	 
		private ShareControl main;
	 
		public ShareControlCommandExecutor(ShareControl h) {
			this.main = h;
		}
		
		public static int j = 0;
		
		public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("sharecontrol"))
		{
			
			if(args.length == 0)
			{
				Localization.helpMenu(sender);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl"))
			{
				Localization.reloadMsg(sender);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver"))
			{
				main.pluginInfo(sender);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("check") && args.length == 1)
			{
				String command = "/sharecontrol check <nickname>";
				String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.using.replace("%command%", command));
				MessageManager.getManager().msg(sender, MessageType.USE, msg);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("check") && args.length == 2)
			{
				Localization.getGamemode(sender, args[1]);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("tools") && args.length == 1)
			{
				Localization.infoTools(sender);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("tools") && (args[1].equalsIgnoreCase("infotool") || args[1].equalsIgnoreCase("info")))
			{
				Localization.getInfoTool(sender);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("tools") && (args[1].equalsIgnoreCase("changetool") || args[1].equalsIgnoreCase("change")))
			{
				Localization.getSetTool(sender);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("update") || args[0].equalsIgnoreCase("up"))
			{
				main.updateCheck();
				if(main.result == UpdateResult.UPDATE_AVAILABLE)
					Localization.UpdateFoundPlayer(sender);
				if(main.result == UpdateResult.NO_UPDATE)
					Localization.UpdateNotFound(sender);
				return true;
			}
			String command = "/sharecontrol";
			String msg = ChatColor.translateAlternateColorCodes('&', LanguageFiles.using.replace("%command%", command));
			MessageManager.getManager().msg(sender, MessageType.USE, msg);
			return true;
		}
		return false;
	}
}
