package com.net.h1karo.sharecontrol;

import org.bukkit.entity.Player;

public class Permissions {
	
	public static boolean perms(Player p, String per)
	{
		if(p.hasPermission("sharecontrol." + per)) return true;
		else return false;
	}
}
