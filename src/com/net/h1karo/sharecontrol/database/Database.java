/*******************************************************************************
 * Copyright (C) 2016 H1KaRo (h1karo)
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

package com.net.h1karo.sharecontrol.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import com.net.h1karo.sharecontrol.ShareControl;
import com.net.h1karo.sharecontrol.configuration.Configuration;
import com.net.h1karo.sharecontrol.listeners.multiinventories.PlayerGameModeChangeListener;
import com.net.h1karo.sharecontrol.version.CoreVersion;

public class Database {

	private static ShareControl main;

	public Database(ShareControl h)
	{
		Database.main = h;
	}

	/** CACHE **/

	static HashMap<List<Integer>, String> cache = new HashMap<>();
	static HashMap<List<Integer>, String> extracache = new HashMap<>();
	static HashMap<List<Integer>, String> fullcache = new HashMap<>();
	static boolean saveStatus = false;
	static BukkitTask AsyncSave = null;
	static BukkitTask AsyncSaveInv = null;

	public static void saveDatabase() {
		saveStatus = true;
		SQLSave();
		cache.clear();
		cache.putAll(extracache);
		extracache.clear();
		saveStatus = false;
	}

	public static void SyncSaveDatabase() {
		saveDatabase();
	}

	public static void AsyncSaveDatabase() {
		AsyncSave = Bukkit.getServer().getScheduler().runTaskAsynchronously(main,  new Runnable() {
			@Override
			public void run() {
				if(cache.size() != 0)
					saveDatabase();
			}
		});
	}




	public static void AsyncSaveInvSave() {
		AsyncSaveInv = Bukkit.getServer().getScheduler().runTaskAsynchronously(main,  new Runnable() {
			@Override
			public void run() {
				PlayerGameModeChangeListener.saveMultiInv();
			}
		});
	}

	public static void autoSaveDatabase() {
		Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(main, new Runnable() {
			@Override
			public void run() {
				if(cache.size() > 0 || PlayerGameModeChangeListener.cache.size() > 0)  {
					if(cache.size() > 0) AsyncSaveDatabase();
					if(PlayerGameModeChangeListener.cache.size() > 0) AsyncSaveInvSave();
					main.log("Database have been background saved!");
				}
			}
		}, Configuration.DBInterval * 1200, Configuration.DBInterval * 1200);
	}

	/** GENERAL FUNCTION OF HANDLER **/

	public static void AddBlockMoreArguments(Block b, String material) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), w = Bukkit.getWorlds().indexOf(b.getWorld());
		List<Integer> key = new ArrayList<>();
		key.add(x); key.add(y); key.add(z); key.add(w);

		if(!saveStatus) cache.put(key, material);
		else extracache.put(key, material);
		fullcache.put(key, material);
	}

	public static void AddBlock(Block b) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), w = Bukkit.getWorlds().indexOf(b.getWorld());
		String material = b.getType().name();
		List<Integer> key = new ArrayList<>();
		key.add(x); key.add(y); key.add(z); key.add(w);


		if(!saveStatus) cache.put(key, material);
		else extracache.put(key, material);
		fullcache.put(key, material);
	}

	public static void AddLocation(Location l) {
		World w = l.getWorld();
		Block b = w.getBlockAt(l);
		AddBlock(b);
	}

	public static void RemoveBlock(Block b) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), w = Bukkit.getWorlds().indexOf(b.getWorld());
		List<Integer> key = new ArrayList<>();
		key.add(x); key.add(y); key.add(z); key.add(w);

		if(!saveStatus) cache.put(key, "AIR");
		else extracache.put(key, "AIR");
		fullcache.remove(key);
	}

	public static boolean CheckCreative(Block b) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), w = Bukkit.getWorlds().indexOf(b.getWorld());
		String material = b.getType().name();
		List<Integer> key = new ArrayList<>();
		key.add(x); key.add(y); key.add(z); key.add(w);

		if(fullcache.containsKey(key) && fullcache.get(key).equals(material) && !material.equals("AIR"))
			return true;
		else  {
			if(fullcache.containsKey(key) && !fullcache.get(key).equals(material))
				RemoveBlock(b);
			return false;
		}
	}

	public static int CheckCreativeRough(Block b) {
		int x = b.getX(), y = b.getY(), z = b.getZ(), w = Bukkit.getWorlds().indexOf(b.getWorld());
		String material = b.getType().name();
		List<Integer> key = new ArrayList<>();
		key.add(x); key.add(y); key.add(z); key.add(w);

		if(fullcache.containsKey(key) && !material.equals("AIR"))
			if(fullcache.get(key).equals(material)) return 1;
			else return 2;
		return 0;
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
	public static void DropBlocks(Block b)
	{
		int h = b.getLocation().getBlockY();
		World w = b.getWorld();
		for(int j = b.getLocation().getBlockY() + 1; j <= Bukkit.getWorlds().get(0).getMaxHeight(); j++) {
			h++;
			Block thish = w.getBlockAt(b.getX(), j, b.getZ());
			if(!ifUpDrop(thish) && !ifOneUpDrop(thish)) {
				j = Bukkit.getWorlds().get(0).getMaxHeight() + 1;
			}
		}

		for(int j = h; j > b.getLocation().getBlockY(); j--)
		{
			Block NewB = w.getBlockAt(b.getLocation().getBlockX(), j, b.getLocation().getBlockZ());
			if(ifUpDrop(NewB)) FullClear(NewB);
		}

		Block NewB = w.getBlockAt(b.getX(), b.getY() + 1, b.getZ());
		if(ifOneUpDrop(NewB)) {
			if(ifLaterallyDrop(NewB) == 1 && NewB.getData() == 12)  FullClear(NewB);
			if(ifLaterallyDrop(NewB) == 2 && NewB.getData() == 5)  FullClear(NewB);
			if(ifLaterallyDrop(NewB) == 4 && (NewB.getData() == 6 || NewB.getData() == 14 || NewB.getData() == 5 || NewB.getData() == 13))  FullClear(NewB);
			if(ifLaterallyDrop(NewB) == 5 && (NewB.getData() == 5 || NewB.getData() == 13))  FullClear(NewB);
		}

		NewB = w.getBlockAt(b.getX() + 1, b.getY(), b.getZ());
		if(ifLaterallyDrop(NewB) == 1 && NewB.getData() == 5)  FullClear(NewB);
		if(ifLaterallyDrop(NewB) == 2 && NewB.getData() == 1) FullClear(NewB);
		if(ifLaterallyDrop(NewB) == 3 && NewB.getData() == 3)  FullClear(NewB);
		if((ifLaterallyDrop(NewB) == 4  || ifLaterallyDrop(NewB) == 5) && (NewB.getData() == 1 || NewB.getData() == 9))  FullClear(NewB);

		NewB = w.getBlockAt(b.getX() - 1, b.getY(), b.getZ());
		if(ifLaterallyDrop(NewB) == 1 && NewB.getData() == 4)  FullClear(NewB);
		if(ifLaterallyDrop(NewB) == 2 && NewB.getData() == 2) FullClear(NewB);
		if(ifLaterallyDrop(NewB) == 3 && NewB.getData() == 1)  FullClear(NewB);
		if((ifLaterallyDrop(NewB) == 4  || ifLaterallyDrop(NewB) == 5) && (NewB.getData() == 2 || NewB.getData() == 10))  FullClear(NewB);

		NewB = w.getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
		if((ifLaterallyDrop(NewB) == 1 || ifLaterallyDrop(NewB) == 2) && NewB.getData() == 3)  FullClear(NewB);
		if(ifLaterallyDrop(NewB) == 3 && NewB.getData() == 0)  FullClear(NewB);
		if((ifLaterallyDrop(NewB) == 4  || ifLaterallyDrop(NewB) == 5) && (NewB.getData() == 3 || NewB.getData() == 11))  FullClear(NewB);

		NewB = w.getBlockAt(b.getX(), b.getY(), b.getZ() - 1);
		if(ifLaterallyDrop(NewB) == 1 && NewB.getData() == 2)  FullClear(NewB);
		if(ifLaterallyDrop(NewB) == 2 && NewB.getData() == 4) FullClear(NewB);
		if(ifLaterallyDrop(NewB) == 3 && NewB.getData() == 2)  FullClear(NewB);
		if((ifLaterallyDrop(NewB) == 4  || ifLaterallyDrop(NewB) == 5) && (NewB.getData() == 4 || NewB.getData() == 12))  FullClear(NewB);
	}

	public static boolean CheckBlock(Block b) {
		if(ifWaterDrop(b) && CheckCreative(b)) {
			FullClear(b);
			return true;
		}
		return false;
	}

	public static void cactusClear(Block b) {
		Block cactus = null;

		cactus = b.getWorld().getBlockAt(b.getX() + 1, b.getY(), b.getZ());
		if(cactus.getType().equals(Material.CACTUS) && CheckCreative(cactus)) {
			DropBlocks(cactus);
			FullClear(cactus);
		}

		cactus = b.getWorld().getBlockAt(b.getX() - 1, b.getY(), b.getZ());
		if(cactus.getType().equals(Material.CACTUS) && CheckCreative(cactus)) {
			DropBlocks(cactus);
			FullClear(cactus);
		}

		cactus = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() + 1);
		if(cactus.getType().equals(Material.CACTUS) && CheckCreative(cactus)) {
			DropBlocks(cactus);
			FullClear(cactus);
		}

		cactus = b.getWorld().getBlockAt(b.getX(), b.getY(), b.getZ() - 1);
		if(cactus.getType().equals(Material.CACTUS) && CheckCreative(cactus)) {
			DropBlocks(cactus);
			FullClear(cactus);
		}
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
		return isCarpet(b) ||
			b.getType() == Material.WALL_SIGN ||
			b.getType() == Material.CACTUS ||
			b.getType() == Material.SUGAR_CANE;
	}

	public static boolean ifOneUpDrop(Block b) {
		if(isBed(b) ||
			b.getType() == Material.LEVER ||
			b.getType() == Material.TORCH ||
			isSapling(b) ||
			b.getType() == Material.REDSTONE_WALL_TORCH ||
			isDoor(b) ||
			b.getType() == Material.REDSTONE ||
			b.getType() == Material.COMPARATOR ||
			b.getType() == Material.REDSTONE_WIRE ||
			b.getType() == Material.REPEATER ||
			b.getType() == Material.RAIL ||
			b.getType() == Material.DETECTOR_RAIL ||
			b.getType() == Material.ACTIVATOR_RAIL ||
			b.getType() == Material.POWERED_RAIL ||
			b.getType() == Material.FLOWER_POT ||
			isFlower(b) ||
			b.getType() == Material.STONE_BUTTON ||
			isButton(b)||
			b.getType() == Material.BROWN_MUSHROOM ||
			b.getType() == Material.RED_MUSHROOM ||
			isPlate(b) ||
			b.getType() == Material.POTATO ||
			b.getType() == Material.CARROT ||
			b.getType() == Material.WHEAT ||
			b.getType() == Material.MELON_STEM ||
			b.getType() == Material.PUMPKIN_STEM)
			return true;
		if(CoreVersion.getVersionsArray().contains(CoreVersion.OneDotEightPlus))
			if(isDoor(b) ||
				isBanner(b))
				return true;
		return false;
	}

	public static int ifLaterallyDrop(Block b) {
		if(b.getType().equals(Material.LADDER) ||
				b.getType().equals(Material.WALL_SIGN))
			return 1;
		if(b.getType().equals(Material.REDSTONE_WALL_TORCH))
			return 2;
		if(b.getType().equals(Material.TRIPWIRE_HOOK) ||
				b.getType().equals(Material.TRIPWIRE))
			return 3;
		if(b.getType().equals(Material.LEVER))
			return 4;
		if(isButton(b))
			return 5;
		if(CoreVersion.getVersionsArray().contains(CoreVersion.OneDotEightPlus)) {
			if(isBanner(b)) return 1;
		}
		return 0;
	}

	public static boolean ifWaterDrop(Block b) {
		if(b.getType() == Material.LEVER ||
			isButton(b) ||
			b.getType() == Material.TORCH ||
			b.getType() == Material.REDSTONE_WALL_TORCH ||
			b.getType() == Material.REDSTONE ||
			b.getType() == Material.COMPARATOR ||
			b.getType() == Material.REDSTONE_WIRE ||
			b.getType() == Material.REPEATER ||
			b.getType() == Material.RAIL ||
			b.getType() == Material.DETECTOR_RAIL ||
			b.getType() == Material.ACTIVATOR_RAIL ||
			b.getType() == Material.POWERED_RAIL ||
			isFlower(b)||
			b.getType() == Material.STONE_BUTTON ||
			isCarpet(b) ||
			isSapling(b) ||
			b.getType() == Material.COBWEB ||
			isHead(b) ||
			b.getType() == Material.ITEM_FRAME ||
			b.getType() == Material.BROWN_MUSHROOM ||
			b.getType() == Material.RED_MUSHROOM)
			return true;
		if(CoreVersion.getVersionsArray().contains(CoreVersion.OneDotNinePlus))
			if(b.getType() == Material.END_ROD)
				return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public static void SQLSave() {
		String material;
		Set<List<Integer>> keys = cache.keySet();
		for(int i=0; i < keys.size(); i++) {
			List<Integer> key = (List<Integer>) keys.toArray()[i];
			Integer x = key.get(0),
					y = key.get(1),
					z = key.get(2),
					w = key.get(3);

			if(cache.get(key).equals("AIR")) material = null;
			else material = cache.get(key);

			MySQL.SQLUpdate(x, y, z, material, w);
			MySQL.SQLUpdate(x, y, z, material, w);
		}
		material = null;
	}

	public static boolean isCarpet(Block b) {
		return b.getType().name().contains("_CARPET");
	}
	private static boolean isBed(Block b) {
		return b.getType().name().contains("_BED");
	}

	private static boolean isBanner(Block b) {
		return b.getType().name().contains("_BANNER");
	}

	private static boolean isPlate(Block b) {
		return b.getType().name().contains("_PLATE");
	}

	private static boolean isButton(Block b) {
		return b.getType().name().contains("_BUTTON");
	}

	private static boolean isFlower(Block b) {
		return b.getType().equals(Material.GRASS)
				|| b.getType().equals(Material.FERN)
				|| b.getType().equals(Material.DEAD_BUSH)
				|| b.getType().equals(Material.DANDELION)
				|| b.getType().equals(Material.POPPY)
				|| b.getType().equals(Material.BLUE_ORCHID)
				|| b.getType().equals(Material.ALLIUM)
				|| b.getType().equals(Material.AZURE_BLUET)
				|| b.getType().equals(Material.SUNFLOWER)
				|| b.getType().equals(Material.BROWN_MUSHROOM)
				|| b.getType().equals(Material.RED_MUSHROOM)
				|| b.getType().equals(Material.OXEYE_DAISY)
				|| b.getType().equals(Material.PINK_TULIP)
				|| b.getType().equals(Material.WHITE_TULIP)
				|| b.getType().equals(Material.ORANGE_TULIP)
				|| b.getType().equals(Material.RED_TULIP)
				|| b.getType().equals(Material.LILAC)
				|| b.getType().equals(Material.ROSE_BUSH)
				|| b.getType().equals(Material.PEONY)
				|| b.getType().equals(Material.TALL_GRASS)
				|| b.getType().equals(Material.LARGE_FERN);
	}

	private static boolean isDoor(Block b) {
		return b.getType().name().contains("_DOOR");
	}

	private static boolean isSapling(Block b) {
		return b.getType().name().contains("_SAPLING");
	}
	private static boolean isHead(Block b) {
		return b.getType().name().contains("_HEAD") || b.getType().name().contains("_SKULL");
	}
}