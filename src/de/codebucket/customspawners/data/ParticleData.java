package de.codebucket.customspawners.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.codebucket.customspawners.particle.Particle;
import de.codebucket.customspawners.particle.ParticleLib;
import de.codebucket.customspawners.particle.ParticleLib.PacketSendingException;
import de.codebucket.customspawners.particle.ParticleRadius;

public class ParticleData extends SpawnerData
{
	private Particle particle;
	private float speed;
	private int amount;
	
	public ParticleData(Particle particle, float speed, int amount) 
	{
		super(DataType.PARTICLE);
		this.particle = particle;
		this.speed = speed;
		this.amount = amount;
	}
	
	public boolean playParticle(Location location) 
	{
		float offsetX = new Random().nextFloat();
		float offsetY = new Random().nextFloat();
		float offsetZ = new Random().nextFloat();
		ParticleRadius radius = new ParticleRadius(offsetX, offsetY, offsetZ, speed, amount);
		try
		{
			for(Player player : getPlayersInRange(location, 20))
			{
				ParticleLib.playParticle(player, location, radius, particle);
			}
		}
		catch(PacketSendingException e)
		{
			return false;
		}
		return true;
	}
	
	public Particle getParticle() 
	{
		return particle;
	}
	
	public static ParticleData getFromParticle(Particle particle)
	{
		return new ParticleData(particle, particle.getDefaultSpeed(), particle.getParticleAmount());
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
	
}
