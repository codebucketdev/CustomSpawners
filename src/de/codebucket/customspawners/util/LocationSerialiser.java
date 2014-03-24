package de.codebucket.customspawners.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationSerialiser
{
	public static String locationPlayerToString(Location loc)
	{
		String returnString = loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();
		return returnString;
	}
	
	public static Location stringToLocationPlayer(String loc)
	{
		String[] splited = loc.split(",");
		String world = splited[0];
		double x = Double.parseDouble(splited[1]);
		double y = Double.parseDouble(splited[2]);
		double z = Double.parseDouble(splited[3]);
		double ya = Double.parseDouble(splited[4]);
		double pi = Double.parseDouble(splited[5]);
		
		Location returnLocation = new Location(Bukkit.getWorld(world), x, y, z);
		returnLocation.setYaw((float)ya);
		returnLocation.setPitch((float)pi);
		
		return returnLocation;
	}
	
	public static String locationEntityToString(Location loc)
	{
		String returnString = loc.getWorld() .getName()+ "," + loc.getX() + "," + loc.getY() + "," + loc.getZ();
		return returnString;
	}
	
	public static Location stringToLocationEntity(String loc)
	{
		String[] splited = loc.split(",");
		String world = splited[0];
		double x = Double.parseDouble(splited[1]);
		double y = Double.parseDouble(splited[2]);
		double z = Double.parseDouble(splited[3]);
		
		Location returnLocation = new Location(Bukkit.getWorld(world), x, y, z);
		
		return returnLocation;
	}
}