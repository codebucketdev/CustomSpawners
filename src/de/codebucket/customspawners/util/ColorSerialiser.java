package de.codebucket.customspawners.util;

import org.bukkit.Color;

public class ColorSerialiser 
{
	public static Color stringToColor(String colr)
	{
		String[] splitted = colr.split(",");
		int r = Integer.parseInt(splitted[0]);
		int g = Integer.parseInt(splitted[1]);
		int b = Integer.parseInt(splitted[2]);
		return Color.fromRGB(r, g, b);
	}
	
	public static String colorToString(Color colr)
	{
		return colr.getRed() + "," + colr.getGreen() + "," + colr.getBlue();
	}
}
