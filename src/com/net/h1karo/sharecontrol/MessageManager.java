package com.net.h1karo.sharecontrol;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {
	private MessageManager() {};
	private static MessageManager manager = new MessageManager();
	public static MessageManager getManager() 
	{
		return manager;
	}
	
	ShareControl main;
	String latestmsg = "";
	
	public enum MessageType
	{
		INFO(ChatColor.GRAY),
		BAD(ChatColor.RED),
		USE(ChatColor.GRAY),
		PLINFO(ChatColor.GRAY),
		HELP(ChatColor.GRAY),
		ERROR(ChatColor.DARK_RED),
		UPDATE(ChatColor.GRAY);
		
		private ChatColor color;
		MessageType(ChatColor color)
		{
			this.color = color;
		}
		
		public ChatColor getColor()
		{
			return color;
		}
	}
	
	public static String prefix = ChatColor.GRAY + "[" + ChatColor.BLUE + "Share" + ChatColor.WHITE + "Control" + ChatColor.GRAY + "] " + ChatColor.RESET;
	public void msg(CommandSender sender, MessageType type, String... msgs)
	{
		for(String msg : msgs)
		{
			if(type.name() != "HELP")
				sender.sendMessage(prefix + type.getColor() + msg);
			else 
				sender.sendMessage(type.getColor() + msg);
		}
	}
}
