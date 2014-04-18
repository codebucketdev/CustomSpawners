package de.codebucket.customspawners.spawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.scheduler.BukkitTask;

import de.codebucket.customspawners.CustomSpawners;
import de.codebucket.customspawners.data.EntityData;
import de.codebucket.customspawners.data.FireworkData;
import de.codebucket.customspawners.data.ItemData;
import de.codebucket.customspawners.data.ParticleData;
import de.codebucket.customspawners.data.PotionData;
import de.codebucket.customspawners.data.SpawnerData;

public class CustomSpawner implements Runnable
{
	private String name;
	private SpawnerType type;
	private SpawnerData data;
	private Location location;
	private int radius;
	private long ticks;
	private BukkitTask task;
	private boolean running;
	
	public CustomSpawner(String name, SpawnerType type, SpawnerData data, Location location, int radius, long ticks) 
	{
		this.name = name;
		this.type = type;
		this.data = data;
		this.location = location;
		this.radius = radius;
		this.ticks = ticks;
	}
	
	@Override
	public void run() 
	{
		if(type == SpawnerType.ITEM)
		{
			double x = 0.0;
			double y = 0.0;
			double z = 0.0;
			if(radius > 0)
			{
				x = (double)getRandomInt((radius * -1), (radius * 1));
				z = (double)getRandomInt((radius * -1), (radius * 1));
			}
			Location loc = location.clone().add(x, y, z);
			if(loc.getWorld().getChunkAt(loc).isLoaded())
			{
				if(arePlayersInRange(loc))
				{
					Item item = ((ItemData) data).dropItem(loc);
					CustomSpawnEvent event = new CustomSpawnEvent(item, this, data);
					Bukkit.getPluginManager().callEvent(event);
				}
			}
		}
		else if(type == SpawnerType.EXP)
		{
			double x = 0.0;
			double y = 0.0;
			double z = 0.0;
			if(radius > 0)
			{
				x = (double)getRandomInt((radius * -1), (radius * 1));
				z = (double)getRandomInt((radius * -1), (radius * 1));
			}
			Location loc = location.clone().add(x, y, z);
			if(loc.getWorld().getChunkAt(loc).isLoaded())
			{
				if(arePlayersInRange(loc))
				{
					ExperienceOrb exp = (ExperienceOrb)((EntityData) data).spawnEntity(loc);
					CustomSpawnEvent event = new CustomSpawnEvent(exp, this, data);
					Bukkit.getPluginManager().callEvent(event);
				}
			}
		}
		else if(type == SpawnerType.ENTITY)
		{
			double x = 0.0;
			double y = 0.0;
			double z = 0.0;
			if(radius > 0)
			{
				x = (double)getRandomInt((radius * -1), (radius * 1));
				z = (double)getRandomInt((radius * -1), (radius * 1));
			}
			Location loc = location.clone().add(x, y, z);
			loc.setPitch(0.0F);
			loc.setYaw(0.0F);
			if(loc.getWorld().getChunkAt(loc).isLoaded())
			{
				if(arePlayersInRange(loc))
				{
					Entity ent = ((EntityData) data).spawnEntity(loc);
					CustomSpawnEvent event = new CustomSpawnEvent(ent, this, data);
					Bukkit.getPluginManager().callEvent(event);
				}
			}
		}
		else if(type == SpawnerType.FIREWORK)
		{
			double x = 0.0;
			double y = 0.0;
			double z = 0.0;
			if(radius > 0)
			{
				x = (double)getRandomInt((radius * -1), (radius * 1));
				z = (double)getRandomInt((radius * -1), (radius * 1));
			}
			Location loc = location.clone().add(x, y, z);
			if(loc.getWorld().getChunkAt(loc).isLoaded())
			{
				if(arePlayersInRange(loc))
				{
					Firework fw = ((FireworkData) data).spawnFirework(loc);
					CustomSpawnEvent event = new CustomSpawnEvent(fw, this, data);
					Bukkit.getPluginManager().callEvent(event);
					if(fw.getFireworkMeta().getPower() <= 0)
					{
						fw.detonate();
					}
				}
			}
		}
		else if(type == SpawnerType.POTION)
		{
			double x = 0.0;
			double y = 0.0;
			double z = 0.0;
			if(radius > 0)
			{
				x = (double)getRandomInt((radius * -1), (radius * 1));
				z = (double)getRandomInt((radius * -1), (radius * 1));
			}
			Location loc = location.clone().add(x, y, z);
			loc.setPitch(0.0F);
			loc.setYaw(0.0F);
			if(loc.getWorld().getChunkAt(loc).isLoaded())
			{
				if(arePlayersInRange(loc))
				{
					ThrownPotion pt = ((PotionData) data).throwPotion(loc);
					CustomSpawnEvent event = new CustomSpawnEvent(pt, this, data);
					Bukkit.getPluginManager().callEvent(event);
				}
			}
		}
		else if(type == SpawnerType.PARTICLE)
		{
			double x = 0.0;
			double y = 0.0;
			double z = 0.0;
			if(radius > 0)
			{
				x = (double)getRandomInt((radius * -1), (radius * 1));
				z = (double)getRandomInt((radius * -1), (radius * 1));
			}
			Location loc = location.clone().add(x, y, z);
			loc.setPitch(0.0F);
			loc.setYaw(0.0F);
			if(loc.getWorld().getChunkAt(loc).isLoaded())
			{
				if(arePlayersInRange(loc))
				{
					((ParticleData) data).playParticle(loc);
					CustomSpawnEvent event = new CustomSpawnEvent(null, this, data);
					Bukkit.getPluginManager().callEvent(event);
				}
			}
		}
		else if(type == SpawnerType.BOAT)
		{
			double x = 0.0;
			double y = 0.0;
			double z = 0.0;
			if(radius > 0)
			{
				x = (double)getRandomInt((radius * -1), (radius * 1));
				z = (double)getRandomInt((radius * -1), (radius * 1));
			}
			Location loc = location.clone().add(x, y, z);
			if(loc.getWorld().getChunkAt(loc).isLoaded())
			{
				if(arePlayersInRange(loc))
				{
					Boat boat = (Boat)((EntityData) data).spawnEntity(loc);
					CustomSpawnEvent event = new CustomSpawnEvent(boat, this, data);
					Bukkit.getPluginManager().callEvent(event);
				}
			}
		}
		else if(type == SpawnerType.MINECART)
		{
			double x = 0.0;
			double y = 0.0;
			double z = 0.0;
			if(radius > 0)
			{
				x = (double)getRandomInt((radius * -1), (radius * 1));
				z = (double)getRandomInt((radius * -1), (radius * 1));
			}
			Location loc = location.clone().add(x, y, z);
			if(loc.getWorld().getChunkAt(loc).isLoaded())
			{
				if(arePlayersInRange(loc))
				{
					Minecart cart = (Minecart)((EntityData) data).spawnEntity(loc);
					CustomSpawnEvent event = new CustomSpawnEvent(cart, this, data);
					Bukkit.getPluginManager().callEvent(event);
				}
			}
		}
	}
	
	public boolean isRunning()
	{
		return running;
	}
	
	public void start()
	{
		if(!isRunning())
		{
			this.task = Bukkit.getScheduler().runTaskTimer(CustomSpawners.getInstance(), this, 0L, ticks);
			this.running = true;
		}
	}
	
	public void stop()
	{
		if(isRunning())
		{
			Bukkit.getScheduler().cancelTask(task.getTaskId());
			this.running = false;
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public SpawnerType getSpawnerType()
	{
		return type;
	}
	
	public SpawnerData getSpawnerData()
	{
		return data;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public int getRadius()
	{
		return radius;
	}
	
	public long getTicks()
	{
		return ticks;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setSpawnerType(SpawnerType type)
	{
		this.type = type;
	}
	
	public void setSpawnerData(SpawnerData data)
	{
		this.data = data;
	}
	
	public void setLocation(Location location)
	{
		this.location = location;
	}
	
	public void setRadius(int radius)
	{
		this.radius = radius;
	}
	
	public void setTicks(long ticks)
	{
		this.ticks = ticks;
	}
	
	private List<Player> getPlayersInRange(Location center, double range)
	{
		List<Player> players = new ArrayList<Player>();
		String name = center.getWorld().getName();
		double squared = range * range;
		for (Player player : Bukkit.getOnlinePlayers())
		{
			if (player.getWorld().getName().equals(name) && player.getLocation().distanceSquared(center) <= squared)
			{
				players.add(player);
			}
		}
		return players;
	}
	
	@SuppressWarnings("unused")
	private boolean isPlayerInRange(Player player, Location center)
	{
		return getPlayersInRange(center, 16.0).contains(player);
	}
	
	private boolean arePlayersInRange(Location center)
	{
		return (!getPlayersInRange(center, 16.0).isEmpty());
	}
	
	private int getRandomInt(int low, int high)
	{
		Random r = new Random();
		return r.nextInt(high-low) + low;
	}

}
