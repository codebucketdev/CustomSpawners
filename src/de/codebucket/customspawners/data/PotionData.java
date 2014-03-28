package de.codebucket.customspawners.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

import de.codebucket.customspawners.particle.Particle;


public class PotionData extends SpawnerData
{
	private List<PotionEffect> effects;
	
	public PotionData(List<PotionEffect> effects)
	{
		super(DataType.POTION);
		this.effects = effects;
	}
	
	@Override
	public Item dropItem(Location location)
	{
		return null;
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
	public ItemStack getFireworkItem()
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
		return null;
	}

	@Override
	public EntityType getEntityType() 
	{
		return null;
	}

	@Override
	public ThrownPotion throwPotion(Location location) 
	{
		ThrownPotion potion = location.getWorld().spawn(location, ThrownPotion.class);
		potion.setItem(getPotionItem());
		return potion;
	}
	
	@Override
	public ItemStack getPotionItem()
	{
		ItemStack item = new ItemStack(Material.POTION, 1);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		if(effects != null)
		{
			for(PotionEffect effect : effects)
			{
				meta.addCustomEffect(effect, true);
			}
		}
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public PotionMeta getPotionMeta() 
	{
		return (PotionMeta) getPotionItem().getItemMeta();
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
	
	public static PotionData getFromMeta(ItemStack potion)
	{
		List<PotionEffect> effects = null;
		PotionMeta meta = (PotionMeta) potion.getItemMeta();
		Potion ption = Potion.fromItemStack(potion);
		effects = new ArrayList<>();
		for(PotionEffect effect : ption.getEffects())
		{
			effects.add(effect);
		}
		
		if(meta != null)
		{
			if(meta.hasCustomEffects())
			{
				effects = new ArrayList<>();
				for(PotionEffect effect : meta.getCustomEffects())
				{
					effects.add(effect);
				}
			}
		}
    	return new PotionData(effects);
	}
	
}
