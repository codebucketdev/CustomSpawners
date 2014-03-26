package de.codebucket.customspawners.util;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectSerialiser 
{
	public static PotionEffect stringToPotionEffect(String effect)
	{
		String[] splitted = effect.split(",");
		PotionEffectType type = PotionEffectType.getByName(splitted[0].toUpperCase());
		int amplifier = Integer.parseInt(splitted[1]);
		int duration = Integer.parseInt(splitted[2]);
		return new PotionEffect(type, duration, amplifier);
	}
	
	public static String potionEffectToString(PotionEffect effect)
	{
		return effect.getType().getName() + "," + effect.getAmplifier() + "," + effect.getDuration();
	}
}
