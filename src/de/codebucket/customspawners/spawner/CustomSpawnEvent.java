package de.codebucket.customspawners.spawner;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

import de.codebucket.customspawners.data.SpawnerData;

public class CustomSpawnEvent extends EntityEvent 
{
	private static final HandlerList handlers = new HandlerList();
	private CustomSpawner spawner;
	private SpawnerData data;
	
	public CustomSpawnEvent(Entity entity, CustomSpawner spawner, SpawnerData data) 
	{
		super(entity);
		this.spawner = spawner;
		this.data = data;
	}
	
	public CustomSpawner getSpawner()
	{
		return spawner;
	}
	
	public SpawnerData getData()
	{
		return data;
	}
	
	public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
	
}
