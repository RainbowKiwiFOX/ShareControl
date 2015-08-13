package com.net.h1karo.sharecontrol.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.net.h1karo.sharecontrol.ShareControl;


public class Database {
	
	private static ShareControl main;
	
	public Database(ShareControl h)
	{
		Database.main = h;
	}
	
	private static File dataFolder;
    private static File blockBaseFolder;
    private static FileConfiguration blockBase = null;
    private static File blockBaseFile = null;
    
    public static void reloadBlockBase(Chunk chunk) {
    	dataFolder = new File(main.getDataFolder(), "data");
        if (!dataFolder.exists()) dataFolder.mkdirs();
    	blockBaseFolder = new File(main.getDataFolder(), "data" + File.separator + "blocks");
        if (!blockBaseFolder.exists()) blockBaseFolder.mkdirs();
    	if (blockBaseFile == null)	blockBaseFile = new File(blockBaseFolder + File.separator + "blocks." + chunk.getWorld().getName() + "." + chunk.getX() + "." + chunk.getZ() + ".yml");
    	
    	blockBase = YamlConfiguration.loadConfiguration(blockBaseFile);
    	 
    		InputStream defConfigStream = main.getResource(blockBaseFolder + File.separator + "blocks." + chunk.getWorld().getName() + "." + chunk.getX() + "." + chunk.getZ() + ".yml");
    		if (defConfigStream != null) {
				@SuppressWarnings("deprecation")
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
    			blockBase.setDefaults(defConfig);
    		}
    	saveBlockBase(chunk);
    	}
    
    public static FileConfiguration getBlockBase(Chunk chunk) {
    	if (blockBase == null) 	reloadBlockBase(chunk);
    	return blockBase;
    	}
    
    public static void saveBlockBase(Chunk chunk) {
    	if (blockBase == null || blockBaseFile == null) return;
    	try {
    		getBlockBase(chunk).save(blockBaseFile);
    	} 
    	catch (IOException ex) {
    		main.getLogger().log(Level.WARNING, "Could not save config to " + blockBaseFile, ex);
    	}
    }
	
	@SuppressWarnings("deprecation")
	public static void AddBlock(Block b) {
		reloadBlockBase(b.getChunk());
		getBlockBase(b.getChunk()).set(b.getX() + "." + b.getY() + "." + b.getZ(), b.getTypeId());
		saveBlockBase(b.getChunk());
	}
	
	@SuppressWarnings("deprecation")
	public static void AddLocation(Location l) {
		reloadBlockBase(l.getChunk());
		World w = l.getWorld();
		getBlockBase(w.getBlockAt(l).getChunk()).set(w.getBlockAt(l).getX() + "." + w.getBlockAt(l).getY() + "." + w.getBlockAt(l).getZ(), w.getBlockAt(l).getTypeId());
		saveBlockBase(l.getChunk());
	}
	
	public static void RemoveBlock(Block b) {
		reloadBlockBase(b.getChunk());
		getBlockBase(b.getChunk()).set(b.getX() + "." + b.getY() + "." + b.getZ(), null);
		saveBlockBase(b.getChunk());
	}
	
	@SuppressWarnings("deprecation")
	public static boolean CheckCreative(Block b) {
		
		FileConfiguration db = getBlockBase(b.getChunk());
		if(db.getInt(b.getX() + "." + b.getY() + "." + b.getZ()) == b.getTypeId())
			return true;
		else  {
			 if(db.getInt(b.getX() + "." + b.getY() + "." + b.getZ()) != 0)
					RemoveBlock(b);
			return false;
		}
	}
	
	public static boolean ListCheckCreative(List<Block> Blocks) {
		for(Block b : Blocks) {
			if(CheckCreative(b))
				return true;
		}
		return false;
	}
	
	public static void UpdateBlockToLocation(Block b, Location l) {
		if(!CheckCreative(b)) return;
		World w = l.getWorld();
		AddBlock(w.getBlockAt(l));
		
	}
	
	public static void FullClear(Block b) {
		if(!CheckCreative(b)) return;
		b.setType(Material.AIR);
		RemoveBlock(b);
	}
	
	
	@SuppressWarnings("deprecation")
	public static void DropBlocks(World w, Block b)
	{
		int h = b.getLocation().getBlockY();
		for(int j = b.getLocation().getBlockY() + 1; j <= 256; j++) {
			h++;
			Block thish = w.getBlockAt(b.getX(), j, b.getZ());
			if(!ifUpDrop(thish) && !ifOneUpDrop(thish)) {
				j = 257;
			}
		}
		
		for(int j = h; j > b.getLocation().getBlockY(); j--) 
		{
			Block NewB = w.getBlockAt(b.getLocation().getBlockX(), j, b.getLocation().getBlockZ());
				if(ifUpDrop(NewB))	FullClear(NewB);
		}
		
		Block NewB = w.getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY() + 1, b.getLocation().getBlockZ());
		if(ifOneUpDrop(NewB))	FullClear(NewB);
		
		
		NewB = w.getBlockAt(b.getLocation().getBlockX() + 1, b.getLocation().getBlockY(), b.getLocation().getBlockZ());
		if(ifLaterallyDrop(NewB) && NewB.getData() == 2)
			FullClear(NewB);
		
		NewB = w.getBlockAt(b.getLocation().getBlockX() - 1, b.getLocation().getBlockY(), b.getLocation().getBlockZ());
		if(ifLaterallyDrop(NewB) && NewB.getData() == 3)
			FullClear(NewB);
		
		NewB = w.getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ() + 1);
		if(ifLaterallyDrop(NewB) && NewB.getData() == 5)
			FullClear(NewB);
		
		NewB = w.getBlockAt(b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ() - 1);
		if(ifLaterallyDrop(NewB) && NewB.getData() == 4) 
			FullClear(NewB);
	}
	
	public static void CheckBlock(Block b) {
		if(ifWaterDrop(b) && CheckCreative(b))
				FullClear(b);
	}
	
	public static void EditBlockByPiston(Block b, BlockFace Dir, World w) {
		int X = b.getX();
		int Y = b.getY();
		int Z = b.getZ();
		
		DropBlocks(w, b);
		CheckBlock(b);
		if(Dir == BlockFace.EAST)
			AddLocation(w.getBlockAt(X + 1, Y, Z).getLocation());
		if(Dir == BlockFace.WEST)
			AddLocation(w.getBlockAt(X - 1, Y, Z).getLocation());
		if(Dir == BlockFace.SOUTH)
			AddLocation(w.getBlockAt(X, Y, Z + 1).getLocation());
		if(Dir == BlockFace.NORTH)
			AddLocation(w.getBlockAt(X, Y, Z - 1).getLocation());
		if(Dir == BlockFace.UP)
			AddLocation(w.getBlockAt(X, Y + 1, Z).getLocation());
		if(Dir == BlockFace.DOWN)
			AddLocation(w.getBlockAt(X, Y - 1, Z).getLocation());
	}
	
	
	
	
	
	/** EXTRA **/
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s);
	    }
	    catch(NumberFormatException e) { 
	    	return false; 
	    }
	    return true;
	}
	
	public static boolean ifUpDrop(Block b) {
		return b.getType() == Material.CARPET ||
			b.getType() == Material.SIGN_POST ||
			b.getType() == Material.CACTUS;
	}
	
	public static boolean ifOneUpDrop(Block b) {
		return b.getType() == Material.BED_BLOCK ||
			b.getType() == Material.LEVER ||
			b.getType() == Material.TORCH ||
			b.getType() == Material.REDSTONE_TORCH_ON ||
			b.getType() == Material.REDSTONE_TORCH_OFF ||
			b.getType() == Material.STONE_PLATE ||
			b.getType() == Material.WOOD_PLATE ||
			b.getType() == Material.GOLD_PLATE ||
			b.getType() == Material.IRON_PLATE ||
			b.getType() == Material.WOODEN_DOOR ||
			b.getType() == Material.ACACIA_DOOR ||
			b.getType() == Material.IRON_DOOR_BLOCK ||
			b.getType() == Material.REDSTONE ||
			b.getType() == Material.REDSTONE_COMPARATOR ||
			b.getType() == Material.REDSTONE_COMPARATOR_OFF ||
			b.getType() == Material.REDSTONE_COMPARATOR_ON ||
			b.getType() == Material.REDSTONE_WIRE ||
			b.getType() == Material.DIODE_BLOCK_ON ||
			b.getType() == Material.DIODE_BLOCK_OFF ||
			b.getType() == Material.DIODE ||
			b.getType() == Material.RAILS ||
			b.getType() == Material.DETECTOR_RAIL ||
			b.getType() == Material.ACTIVATOR_RAIL ||
			b.getType() == Material.POWERED_RAIL ||
			b.getType() == Material.BED ||
			b.getType() == Material.FLOWER_POT ||
			b.getType() == Material.SPRUCE_DOOR ||
			b.getType() == Material.DOUBLE_PLANT ||
			b.getType() == Material.RED_ROSE ||
			b.getType() == Material.YELLOW_FLOWER ||
			b.getType() == Material.BIRCH_DOOR ||
			b.getType() == Material.JUNGLE_DOOR ||
			b.getType() == Material.DARK_OAK_DOOR ||
			b.getType() == Material.STANDING_BANNER ||
			b.getType() == Material.STONE_BUTTON ||
			b.getType() == Material.WOOD_BUTTON;
	}
	
	public static boolean ifLaterallyDrop(Block b) {
		return b.getType() == Material.LEVER ||
			b.getType() == Material.TORCH ||
			b.getType() == Material.REDSTONE_TORCH_ON ||
			b.getType() == Material.REDSTONE_TORCH_OFF ||
			b.getType() == Material.STONE_BUTTON ||
			b.getType() == Material.WOOD_BUTTON ||
			b.getType() == Material.TRAP_DOOR ||
			b.getType() == Material.LADDER ||
			b.getType() == Material.STANDING_BANNER ||
			b.getType() == Material.WALL_SIGN ||
			b.getType() == Material.IRON_TRAPDOOR;
	}
	
	public static boolean ifWaterDrop(Block b) {
		return b.getType() == Material.LEVER ||
			b.getType() == Material.WOOD_BUTTON ||
			b.getType() == Material.TORCH ||
			b.getType() == Material.REDSTONE_TORCH_ON ||
			b.getType() == Material.REDSTONE_TORCH_OFF ||
			b.getType() == Material.REDSTONE ||
			b.getType() == Material.REDSTONE_COMPARATOR ||
			b.getType() == Material.REDSTONE_COMPARATOR_OFF ||
			b.getType() == Material.REDSTONE_COMPARATOR_ON ||
			b.getType() == Material.REDSTONE_WIRE ||
			b.getType() == Material.DIODE_BLOCK_ON ||
			b.getType() == Material.DIODE_BLOCK_OFF ||
			b.getType() == Material.DIODE ||
			b.getType() == Material.RAILS ||
			b.getType() == Material.DETECTOR_RAIL ||
			b.getType() == Material.ACTIVATOR_RAIL ||
			b.getType() == Material.POWERED_RAIL ||
			b.getType() == Material.FLOWER_POT ||
			b.getType() == Material.FLOWER_POT_ITEM ||
			b.getType() == Material.DOUBLE_PLANT ||
			b.getType() == Material.RED_ROSE ||
			b.getType() == Material.YELLOW_FLOWER ||
			b.getType() == Material.STONE_BUTTON ||
			b.getType() == Material.CARPET ||
			b.getType() == Material.SAPLING ||
			b.getType() == Material.WEB ||
			b.getType() == Material.SKULL ||
			b.getType() == Material.SKULL_ITEM ||
			b.getType() == Material.ITEM_FRAME;
	}
}
