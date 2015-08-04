package com.net.h1karo.sharecontrol.metabase;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.metadata.FixedMetadataValue;

import com.net.h1karo.sharecontrol.ShareControl;


public class MetaBase {
	
	private static ShareControl main;
	
	public MetaBase(ShareControl h)
	{
		MetaBase.main = h;
	}
	
	public static void AddBlockMetadata(Block b) {
		b.setMetadata("ShareControl.CREATIVE_BLOCK", new FixedMetadataValue(main, "true"));
	}
	
	public static void AddLocationMetadata(Location l) {
		World w = l.getWorld();
		w.getBlockAt(l).setMetadata("ShareControl.CREATIVE_BLOCK", new FixedMetadataValue(main, "true"));
	}
	
	public static void RemoveBlockMetadata(Block b) {
		b.removeMetadata("ShareControl.CREATIVE_BLOCK", main);
	}
	
	public static boolean CheckCreative(Block b) {
		if(b.hasMetadata("ShareControl.CREATIVE_BLOCK"))
			return true;
		else return false;
	}
	
	public static boolean ListCheckCreative(List<Block> Blocks) {
		for(Block b : Blocks) {
			if(b.hasMetadata("ShareControl.CREATIVE_BLOCK"))
				return true;
		}
		return false;
	}
	
	public static void UpdateBlockToLocation(Block b, Location l) {
		if(!CheckCreative(b)) return;
		World w = l.getWorld();
		
		AddBlockMetadata(w.getBlockAt(l));
	}
	
	public static void FullClear(Block b) {
		if(!CheckCreative(b)) return;
		b.setType(Material.AIR);
		RemoveBlockMetadata(b);
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
			AddLocationMetadata(w.getBlockAt(X + 1, Y, Z).getLocation());
		if(Dir == BlockFace.WEST)
			AddLocationMetadata(w.getBlockAt(X - 1, Y, Z).getLocation());
		if(Dir == BlockFace.SOUTH)
			AddLocationMetadata(w.getBlockAt(X, Y, Z + 1).getLocation());
		if(Dir == BlockFace.NORTH)
			AddLocationMetadata(w.getBlockAt(X, Y, Z - 1).getLocation());
		if(Dir == BlockFace.UP)
			AddLocationMetadata(w.getBlockAt(X, Y + 1, Z).getLocation());
		if(Dir == BlockFace.DOWN)
			AddLocationMetadata(w.getBlockAt(X, Y - 1, Z).getLocation());
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
