package com.net.h1karo.sharecontrol.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.listeners.multiinventories.PlayerGameModeChangeListener;

public class Database {
	
	private static ShareControl main;
	
	public Database(ShareControl h)
	{
		Database.main = h;
	}
	
	public static BukkitTask SaveScheduler;
	
	private static File dataFolder;
    private static File blockBaseFolder;
    private static FileConfiguration blockBase = null;
    private static File blockBaseFile = null;
    
    public static void reloadBlockBase() {
    	dataFolder = new File(main.getDataFolder(), "data");
    	if (!dataFolder.exists()) dataFolder.mkdirs();
    	blockBaseFolder = new File(main.getDataFolder(), "data");
    	if (!blockBaseFolder.exists()) blockBaseFolder.mkdirs();
    	if (blockBaseFile == null)	blockBaseFile = new File(blockBaseFolder + File.separator + "blocks.yml");
    	
    	blockBase = YamlConfiguration.loadConfiguration(blockBaseFile);
    	
    	InputStream defConfigStream = main.getResource(blockBaseFolder + File.separator + "blocks.yml");
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
    
    /** CACHE **/
    
    static HashMap<List<Integer>, Integer> cache = new HashMap<List<Integer>, Integer>();
	
	public static void saveDatabase() {
		if(Configuration.Database.compareToIgnoreCase("yaml") == 0 || Configuration.Database.compareToIgnoreCase("yml") == 0)
			YAMLSave();
		else
			SQLSave();
		cache.clear();
	}
	
	public static void AsyncSaveDatabase() {
		SaveScheduler = Bukkit.getServer().getScheduler().runTaskAsynchronously(main,  new Runnable() {
            @Override
            public void run() {
            	saveDatabase();
            }
		});
	}
	
	
	
	
	public static void AsyncSaveInvSave() {
		SaveScheduler = Bukkit.getServer().getScheduler().runTaskAsynchronously(main,  new Runnable() {
            @Override
            public void run() {
            	PlayerGameModeChangeListener.saveMultiInv();
            }
		});
	}
	
	@SuppressWarnings("deprecation")
	public static void autoSaveDatabase() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleAsyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
            	boolean empty = false;
            	
            	if(cache.size() != 0 && PlayerGameModeChangeListener.cache.size() != 0) {
            		empty = true;
            		main.getLogger().info("Saving...");
            	}
            	
                AsyncSaveDatabase();
            	AsyncSaveInvSave();
            	
            	if(empty)
            		main.getLogger().info("Database have been background saved!");
            	
            	empty = false;
            }
        }, Configuration.DBInterval * 120L, Configuration.DBInterval * 120L);
	}
	
	/** GENERAL FUNCTION OF HANDLER **/
	
	@SuppressWarnings("deprecation")
	public static void AddBlock(Block b) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), id = b.getTypeId();
		
		List<Integer> key = new ArrayList<Integer>();
		key.add(x); key.add(y); key.add(z);
		cache.put(key, id);
	}
	
	public static void AddLocation(Location l) {
		World w = l.getWorld();
		Block b = w.getBlockAt(l);
		AddBlock(b);
	}
	
	public static void RemoveBlock(Block b) {
		int x = b.getX(), y = b.getY(), z = b.getZ();
		List<Integer> key = new ArrayList<Integer>();
		key.add(x); key.add(y); key.add(z);
		cache.put(key, 0);
	}
	
	@SuppressWarnings("deprecation")
	public static boolean CheckCreative(Block b) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), id = b.getTypeId();
		List<Integer> key = new ArrayList<Integer>();
		key.add(x); key.add(y); key.add(z);
		
		if(Configuration.Database.compareToIgnoreCase("yaml") == 0 || Configuration.Database.compareToIgnoreCase("yml") == 0) {
			FileConfiguration db = getBlockBase();
			if((db.getInt(x + "." + y + "." + z) == id && (!cache.containsKey(key) || (cache.containsKey(key) && cache.get(key) != 0))) || (cache.containsKey(key) && cache.get(key) == id))
				return true;
			else  {
				if(db.getInt(x + "." + y + "." + z) != 0 || (cache.containsKey(key) && cache.get(key) != 0 && cache.get(key) != id))
					RemoveBlock(b);
				return false;
			}
		}
		else {
			if((MySQL.getID(x, y, z) == id && (!cache.containsKey(key) || (cache.containsKey(key) && cache.get(key) != 0))) || (cache.containsKey(key) && cache.get(key) == id))
				return true;
			else  {
				if(MySQL.getID(x, y, z) != 0 || (cache.containsKey(key) && cache.get(key) != 0 && cache.get(key) != id))
					RemoveBlock(b);
				return false;
			}
		}
	}
	
	public static boolean ListCheckCreative(List<Block> Blocks) {
		for(Block b : Blocks) {
			if(CheckCreative(b))
				return true;
		}
		return false;
	}
	
	
	/** EXTRA **/
	
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
			b.getType() == Material.CACTUS ||
			b.getType() == Material.SUGAR_CANE_BLOCK;
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
			b.getType() == Material.WOOD_BUTTON ||
			b.getType() == Material.BROWN_MUSHROOM ||
			b.getType() == Material.RED_MUSHROOM;
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
			b.getType() == Material.ITEM_FRAME ||
			b.getType() == Material.BROWN_MUSHROOM ||
			b.getType() == Material.RED_MUSHROOM;
	}
	
	public static void YAMLSave() {
		reloadBlockBase();
    	Integer id;
    	for(List<Integer> key : cache.keySet()) {
    		String coords = "";
    		for(int i=0; i < key.size(); i++)
    			if(coords == "") coords = key.get(i).toString();
    			else coords = coords + "." + key.get(i);
    		if(cache.get(key) == 0) id = null;
    		else id = cache.get(key);
    		getBlockBase().set(coords, id);
    	}
    	saveBlockBase();
    	cache.clear();
	}
	
	public static void SQLSave() {
		Integer id;
    	for(List<Integer> key : cache.keySet()) {
    		Integer x = key.get(0),
    			y = key.get(1),
    			z = key.get(2);
    		
    		if(cache.get(key) == 0) id = null;
    		else id = cache.get(key);
    		/** SAVE **/
			MySQL.SQLUpdate(x, y, z, id);
    	}
    	cache.clear();
	}
	
	/** DEBUG FUNCTIONS **/
	
	public static void getCache() {
		for(List<Integer> key : cache.keySet()) {
    		String coords = "";
    		for(int i=0; i < key.size(); i++)
    			if(coords == "") coords = key.get(i).toString();
    			else coords = coords + "." + key.get(i);
    		main.getLogger().info(coords + " " + cache.get(key));
    	}
	}
}


