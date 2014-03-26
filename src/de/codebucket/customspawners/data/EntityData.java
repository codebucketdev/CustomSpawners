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

public class EntityData extends SpawnerData
{
	private EntityType type;
	
	public EntityData(EntityType type) 
	{
		super(DataType.ENTITY);
		this.type = type;
	}

	@Override
	public ItemStack getItemStack() 
	{
		return null;
	}

	@Override
	public ItemMeta getItemMeta()
	{
		return null;
	}

	@Override
	public Firework spawnFirework(Location location) 
	{
		return null;
	}

	@Override
	public FireworkMeta getFireworkMeta()
	{
		return null;
	}

	@Override
	public Entity spawnEntity(Location location) 
	{
		Entity entity = location.getWorld().spawnEntity(location, type);
		return entity;
	}

	@Override
	public EntityType getEntityType() 
	{
		return type;
	}

	@Override
	public Entity throwPotion(Location location)
	{
		return null;
	}

	@Override
	public PotionMeta getPotionMeta() 
	{
		return null;
	}

	@Override
	public boolean playParticle(Location location) 
	{
		return false;
	}

	@Override
	public Particle getParticle() 
	{
		return null;
	}
}
