package de.codebucket.customspawners.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemSerialiser 
{
	@SuppressWarnings("deprecation")
	public static String itemToString(ItemStack item)
	{
		if(item != null)
		{
			String string = item.getType().toString() + ":" + item.getData().getData();
			return string;
		}
		
		return null;
	}
	
	public static ItemStack stringToItem(String string)
	{
		String[] data = string.split(":");
		
		if(data.length == 1)
		{
			ItemStack item = new ItemStack(Material.valueOf(data[0].toUpperCase()), 1, (short) 0);
			return item;
		}
		else if(data.length == 2)
		{
			ItemStack item = new ItemStack(Material.valueOf(data[0].toUpperCase()), 1, (short) Integer.parseInt(data[1]));
			return item;
		}
		
		return null;
	}
}
