package com.net.h1karo.sharecontrol.listeners;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.net.h1karo.sharecontrol.database.Database;

public class BasicHandlers {
	
	public static boolean ifInt;
	public static boolean checkSameness(Block b) {
		int x = b.getLocation().getBlockX();
		int y = b.getLocation().getBlockY();
		int z = b.getLocation().getBlockZ();
		
		if(Database.getBlockBase().getInt(x + "." + y + "." + z) == z)
			return true;
		else
			return false;
	}
	
	public static boolean checkSamenessList(List<Block> Block) {
		for(int i=0; i < Block.size(); i++)
		{
			Block b = (org.bukkit.block.Block) Block.toArray()[i];
			int x = b.getX();
			int y = b.getY();
			int z = b.getZ();
			
			if(Database.getBlockBase().get(x + "." + y + "." + z) != null) {
					if(checkSameness(b))
						return true;
			}
		}
		return false;
	}
	
	public static void RemoveofDatabase(Block b) {
		int x = b.getLocation().getBlockX();
		int y = b.getLocation().getBlockY();
		int z = b.getLocation().getBlockZ();
		
		Database.getBlockBase().set(x + "." + y + "." + z, null);
		Database.saveBlockBase();
	}
	
	public static void AddofDatabase(Block b) {
		int x = b.getLocation().getBlockX();
		int y = b.getLocation().getBlockY();
		int z = b.getLocation().getBlockZ();
		Database.getBlockBase().set(x + "." + y + "." + z, z);
		Database.saveBlockBase();
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
	
	
	
	public static void OnEditBase(Block b, Location l)
	{
		if(!checkSameness(b)) return;
		
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		Database.getBlockBase().set(x + "." + y + "." + z, z);
		Database.saveBlockBase();
	}
	
	public static void LocationEditBase(Location l)
	{
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		Database.getBlockBase().set(x + "." + y + "." + z, z);
		Database.saveBlockBase();
	}
	
	public static void clearBlock(Block b) {
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		
		Database.getBlockBase().set(x + "." + y + "." + z, null);
		Database.saveBlockBase();
	}

	public static void OnBase(Block b)
	{
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		if(Database.getBlockBase().get(x +"."+ y +"."+ z) != null && checkSameness(b))
		{
			b.setType(Material.AIR);
			Database.getBlockBase().set(x +"."+ y +"."+ z, null);
			Database.saveBlockBase();
			return;
		}
	}
	
	public static void upDropBlocksMore(String[] DropBlocksMore) {
		DropBlocksMore[0] = "CARPET";
		DropBlocksMore[1] = "SIGN_POST";
		DropBlocksMore[2] = "CACTUS";
	}
	
	public static void upDropBlocks(String[] DropBlocks) {
		DropBlocks[0] = "BED_BLOCK";
		DropBlocks[1] = "LEVER";
		DropBlocks[2] = "TORCH";
		DropBlocks[3] = "REDSTONE_TORCH_ON";
		DropBlocks[4] = "REDSTONE_TORCH_OFF";
		DropBlocks[5] = "STONE_PLATE";
		DropBlocks[6] = "WOOD_PLATE";
		DropBlocks[7] = "GOLD_PLATE";
		DropBlocks[8] = "IRON_PLATE";
		DropBlocks[9] = "WOODEN_DOOR";
		DropBlocks[10] = "ACACIA_DOOR";
		DropBlocks[11] = "IRON_DOOR_BLOCK";
		DropBlocks[12] = "REDSTONE";
		DropBlocks[13] = "REDSTONE_COMPARATOR";
		DropBlocks[14] = "REDSTONE_COMPARATOR_OFF";
		DropBlocks[15] = "REDSTONE_COMPARATOR_ON";
		DropBlocks[16] = "REDSTONE_WIRE";
		DropBlocks[17] = "DIODE_BLOCK_ON";
		DropBlocks[18] = "DIODE_BLOCK_OFF";
		DropBlocks[19] = "DIODE";
		DropBlocks[20] = "RAILS";
		DropBlocks[21] = "DETECTOR_RAIL";
		DropBlocks[22] = "ACTIVATOR_RAIL";
		DropBlocks[23] = "POWERED_RAIL";
		DropBlocks[24] = "BED";
		DropBlocks[25] = "FLOWER_POT";
		DropBlocks[26] = "SPRUCE_DOOR";
		DropBlocks[27] = "DOUBLE_PLANT";
		DropBlocks[28] = "RED_ROSE";
		DropBlocks[29] = "YELLOW_FLOWER";
		DropBlocks[30] = "BIRCH_DOOR";
		DropBlocks[31] = "JUNGLE_DOOR";
		DropBlocks[32] = "DARK_OAK_DOOR";
		DropBlocks[33] = "STANDING_BANNER";
		DropBlocks[34] = "STONE_BUTTON";
		DropBlocks[35] = "WOOD_BUTTON";
	}
	
	public static void laterallyDropBlocks(String[] DropBlocks) {
		DropBlocks[0] = "LEVER";
		DropBlocks[1] = "TORCH";
		DropBlocks[2] = "REDSTONE_TORCH_ON";
		DropBlocks[3] = "REDSTONE_TORCH_OFF";
		DropBlocks[4] = "STONE_BUTTON";
		DropBlocks[5] = "WOOD_BUTTON";
		DropBlocks[6] = "TRAP_DOOR";
		DropBlocks[7] = "LADDER";
		DropBlocks[8] = "WALL_SIGN";
		DropBlocks[9] = "IRON_TRAPDOOR";
	}
	
	public static void WaterDropBlocks(String[] WaterDropBlocks) {
		WaterDropBlocks[0] = "LEVER";
		WaterDropBlocks[1] = "WOOD_BUTTON";
		WaterDropBlocks[2] = "TORCH";
		WaterDropBlocks[3] = "REDSTONE_TORCH_ON";
		WaterDropBlocks[4] = "REDSTONE_TORCH_OFF";
		WaterDropBlocks[5] = "REDSTONE";
		WaterDropBlocks[6] = "REDSTONE_COMPARATOR";
		WaterDropBlocks[7] = "REDSTONE_COMPARATOR_OFF";
		WaterDropBlocks[8] = "REDSTONE_COMPARATOR_ON";
		WaterDropBlocks[9] = "REDSTONE_WIRE";
		WaterDropBlocks[10] = "DIODE_BLOCK_ON";
		WaterDropBlocks[11] = "DIODE_BLOCK_OFF";
		WaterDropBlocks[12] = "DIODE";
		WaterDropBlocks[13] = "RAILS";
		WaterDropBlocks[14] = "DETECTOR_RAIL";
		WaterDropBlocks[15] = "ACTIVATOR_RAIL";
		WaterDropBlocks[16] = "POWERED_RAIL";
		WaterDropBlocks[17] = "FLOWER_POT";
		WaterDropBlocks[18] = "FLOWER_POT_ITEM";
		WaterDropBlocks[19] = "DOUBLE_PLANT";
		WaterDropBlocks[20] = "RED_ROSE";
		WaterDropBlocks[21] = "YELLOW_FLOWER";
		WaterDropBlocks[22] = "STONE_BUTTON";
		WaterDropBlocks[23] = "CARPET";
		WaterDropBlocks[24] = "SAPLING";
		WaterDropBlocks[25] = "WEB";
		WaterDropBlocks[26] = "SKULL";
		WaterDropBlocks[27] = "SKULL_ITEM";
		WaterDropBlocks[28] = "ITEM_FRAME";
	}
	
	public static boolean InBase(Block b) {
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		if(Database.getBlockBase().get(x + "." + y + "." + z) != null && checkSameness(b))
			return true;
		if(Database.getBlockBase().get(x + "." + y + "." + z) == null)
			return false;
		return false;
	}
	
	public static void DropBlocks(World w, Block b)
	{
		String[] DropBlocks = new String[3];
		upDropBlocksMore(DropBlocks);
		
		for(int j = 256; j > b.getLocation().getBlockY(); j--) 
		{
			for(int k=0; k < DropBlocks.length; k++)
			{
				Block NewB = w.getBlockAt(b.getLocation().getBlockX(), j, b.getLocation().getBlockZ());
				if(NewB.getType() == Material.getMaterial(DropBlocks[k]))	OnBase(NewB);
			}
		}
		
		DropBlocks = new String[36];
		upDropBlocks(DropBlocks);
		for(int k=0; k < DropBlocks.length; k++)
		{
			Block NewB = w.getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY() + 1, b.getLocation().getBlockZ());
			if(NewB.getType() == Material.getMaterial(DropBlocks[k]))	OnBase(NewB);
		}
		
		DropBlocks = new String[10];
		laterallyDropBlocks(DropBlocks);
		for(int i=0; i < DropBlocks.length; i++)
		{
			Block NewB = w.getBlockAt(b.getLocation().getBlockX() + 1, b.getLocation().getBlockY(), b.getLocation().getBlockZ());
			if(NewB.getType() == Material.getMaterial(DropBlocks[i]))	OnBase(NewB);
			
			NewB = w.getBlockAt(b.getLocation().getBlockX() - 1, b.getLocation().getBlockY(), b.getLocation().getBlockZ());
			if(NewB.getType() == Material.getMaterial(DropBlocks[i]))	OnBase(NewB);
			
			NewB = w.getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ() + 1);
			if(NewB.getType() == Material.getMaterial(DropBlocks[i]))	OnBase(NewB);
			
			NewB = w.getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ() - 1);
			if(NewB.getType() == Material.getMaterial(DropBlocks[i]))	OnBase(NewB);
			
		}	
	}
	
	public static void CheckBlock(Block b) {
		String[] DropBlocks = new String[29];
		WaterDropBlocks(DropBlocks);
		for (int i=0; i < DropBlocks.length; i++) {
			if(b.getType().toString() == DropBlocks[i] && InBase(b))
				OnBase(b);
		}
	}
	
	public static void EditBlockByPiston(Block b, int i, String Dir, World w) {
		int X = b.getX();
		int Y = b.getY();
		int Z = b.getZ();
		
		BasicHandlers.DropBlocks(w, b);
		BasicHandlers.CheckBlock(b);
		if(Dir.compareToIgnoreCase("EAST") == 0)
			BasicHandlers.LocationEditBase(w.getBlockAt(X + 1, Y, Z).getLocation());
		if(Dir.compareToIgnoreCase("WEST") == 0)
			BasicHandlers.LocationEditBase(w.getBlockAt(X - 1, Y, Z).getLocation());
		if(Dir.compareToIgnoreCase("SOUTH") == 0)
			BasicHandlers.LocationEditBase(w.getBlockAt(X, Y, Z + 1).getLocation());
		if(Dir.compareToIgnoreCase("NORTH") == 0)
			BasicHandlers.LocationEditBase(w.getBlockAt(X, Y, Z - 1).getLocation());
		if(Dir.compareToIgnoreCase("UP") == 0)
			BasicHandlers.LocationEditBase(w.getBlockAt(X, Y + 1, Z).getLocation());
		if(Dir.compareToIgnoreCase("DOWN") == 0)
			BasicHandlers.LocationEditBase(w.getBlockAt(X, Y - 1, Z).getLocation());
	}
}
