package de.codebucket.customspawners.spawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.codebucket.customspawners.CustomSpawners;
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
					loc.getWorld().dropItemNaturally(loc, data.getItemStack());
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
					ExperienceOrb exp = (ExperienceOrb)data.spawnEntity(loc);
					exp.setExperience(1);
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
					data.spawnEntity(loc);
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
					Firework fw = data.spawnFirework(loc);
					if(data.getFireworkMeta().getPower() <= 0)
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
					data.throwPotion(loc);
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
					data.playParticle(loc);
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
					data.spawnEntity(loc);
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
					data.spawnEntity(loc);
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
