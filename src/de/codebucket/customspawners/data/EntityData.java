package de.codebucket.customspawners.data;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityData extends SpawnerData
{
	private EntityType type;
	
	public EntityData(EntityType type) 
	{
		super(DataType.ENTITY);
		this.type = type;
	}
	
	public Entity spawnEntity(Location location) 
	{
		Entity entity = location.getWorld().spawnEntity(location, type);
		return entity;
	}
	
	public EntityType getEntityType() 
	{
		return type;
	}
	
}
