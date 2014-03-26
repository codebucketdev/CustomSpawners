package de.codebucket.customspawners.data;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import de.codebucket.customspawners.particle.Particle;

public abstract class SpawnerData
{
	public enum DataType
	{
		ITEM,
		ENTITY,
		FIREWORK,
		POTION,
		PARTICLE;
	}
	
	private DataType type;
	
	public SpawnerData(DataType type)
	{
		this.type = type;
	}
	
	public DataType getDataType()
	{
		return type;
	}
	
	public void setDataType(DataType type)
	{
		this.type = type;
	}
	
	public abstract ItemStack getItemStack();
	public abstract ItemMeta getItemMeta();
	public abstract Firework spawnFirework(Location location);
	public abstract FireworkMeta getFireworkMeta();
	public abstract Entity spawnEntity(Location location);
	public abstract EntityType getEntityType();
	public abstract Entity throwPotion(Location location);
	public abstract PotionMeta getPotionMeta();
	public abstract boolean playParticle(Location location);
	public abstract Particle getParticle();
	
}
