package de.codebucket.customspawners.particle;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.codebucket.customspawners.util.ReflectionHandler;
import de.codebucket.customspawners.util.ReflectionHandler.PackageType;
import de.codebucket.customspawners.util.ReflectionHandler.PacketType;
import de.codebucket.customspawners.util.ReflectionHandler.SubPackageType;

public class ParticleLib
{
	private static Constructor<?> packetPlayOutWorldParticles;
	private static Method getHandle;
	private static Field playerConnection;
	private static Method sendPacket;
	
	static
	{
		try 
		{
			packetPlayOutWorldParticles = ReflectionHandler.getConstructor(PacketType.PLAY_OUT_WORLD_PARTICLES.getPacket(), String.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);
			getHandle = ReflectionHandler.getMethod("CraftPlayer", SubPackageType.ENTITY, "getHandle");
			playerConnection = ReflectionHandler.getField("EntityPlayer", PackageType.MINECRAFT_SERVER, "playerConnection");
			sendPacket = ReflectionHandler.getMethod(playerConnection.getType(), "sendPacket", ReflectionHandler.getClass("Packet", PackageType.MINECRAFT_SERVER));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private static Object createParticlePacket(String name, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int amount)
	{
		if (amount < 1)
			throw new PacketInstantiationException("Amount cannot be lower than 1");
		
		try
		{
			return packetPlayOutWorldParticles.newInstance(name, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), offsetX, offsetY, offsetZ, speed, amount);
		} 
		catch (Exception e)
		{
			throw new PacketInstantiationException("Packet instantiation failed", e);
		}
	}

	private static Object createIconCrackPacket(int id, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int amount)
	{
		return createParticlePacket("iconcrack_" + id, loc, offsetX, offsetY, offsetZ, speed, amount);
	}
	
	private static Object createBlockCrackPacket(int id, byte data, Location loc, float offsetX, float offsetY, float offsetZ, int amount)
	{
		return createParticlePacket("blockcrack_" + id + "_" + data, loc, offsetX, offsetY, offsetZ, 0, amount);
	}
	
	private static Object createBlockDustPacket(int id, byte data, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int amount)
	{
		return createParticlePacket("blockdust_" + id + "_" + data, loc, offsetX, offsetY, offsetZ, speed, amount);
	}

	private static void sendPacket(Player player, Object packet)
	{
		try
		{
			sendPacket.invoke(playerConnection.get(getHandle.invoke(player)), packet);
		}
		catch (Exception e) 
		{
			throw new PacketSendingException("Failed to send a packet to player '" + player.getName() + "'",  e);
		}
	}
	
	public static void playParticle(Player p, Location loc, Particle particle) throws PacketSendingException
	{
		float offsetX = new Random().nextFloat();
		float offsetY = new Random().nextFloat();
		float offsetZ = new Random().nextFloat();
		float speed = particle.getDefaultSpeed();
		int amount = particle.getParticleAmount();
		sendPacket(p, createParticlePacket(particle.getParticleName(), loc, offsetX, offsetY, offsetZ, speed, amount));
	}

	public static void playParticle(Player p, Location loc, ParticleRadius radius, Particle particle) throws PacketSendingException
	{
		float offsetX = radius.getX();
		float offsetY = radius.getY();
		float offsetZ = radius.getZ();
		float speed = radius.getSpeed();
		int amount = radius.getAmount();
		sendPacket(p, createParticlePacket(particle.getParticleName(), loc, offsetX, offsetY, offsetZ, speed, amount));
	}
	
	public static void playIconCrack(Player p, Location loc, int id, ParticleRadius radius) throws PacketSendingException
	{
		float offsetX = radius.getX();
		float offsetY = radius.getY();
		float offsetZ = radius.getZ();
		float speed = radius.getSpeed();
		int amount = radius.getAmount();
		sendPacket(p, createIconCrackPacket(id, loc, offsetX, offsetY, offsetZ, speed, amount));
	}
	
	public static void playBlockCrack(Player p, Location loc, int id, byte data, ParticleRadius radius) throws PacketSendingException
	{
		float offsetX = radius.getX();
		float offsetY = radius.getY();
		float offsetZ = radius.getZ();
		int amount = radius.getAmount();
		sendPacket(p, createBlockCrackPacket(id, data, loc, offsetX, offsetY, offsetZ, amount));
	}
	
	public static void playBlockDust(Player p, Location loc, int id, byte data, ParticleRadius radius) throws PacketSendingException
	{
		float offsetX = radius.getX();
		float offsetY = radius.getY();
		float offsetZ = radius.getZ();
		float speed = radius.getSpeed();
		int amount = radius.getAmount();
		sendPacket(p, createBlockDustPacket(id, data, loc, offsetX, offsetY, offsetZ, speed, amount));
	}
	
	public static final class PacketInstantiationException extends RuntimeException 
	{
		private static final long serialVersionUID = 3203085387160737484L;

		public PacketInstantiationException(String message)
		{
			super(message);
		}

		public PacketInstantiationException(String message, Throwable cause) 
		{
			super(message, cause);
		}
	}

	public static final class PacketSendingException extends RuntimeException 
	{
		private static final long serialVersionUID = 3203085387160737484L;

		public PacketSendingException(String message, Throwable cause) 
		{
			super(message, cause);
		}
	}
	
}
