package de.codebucket.customspawners.data;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import de.codebucket.customspawners.particle.Particle;


public class ItemData extends SpawnerData
{
	private Material material;
	private short data;
	private String displayname;
	private List<String> lore;
	private Map<Enchantment, Integer> enchantments;
	
	public ItemData(Material material, short data, String displayname, List<String> lore, Map<Enchantment, Integer> enchantments)
	{
		super(DataType.ITEM);
		this.material = material;
		this.data = data;
		this.displayname = displayname;
		this.lore = lore;
		this.enchantments = enchantments;
	}
	
	@Override
	public ItemStack getItemStack()
	{
		ItemStack item = new ItemStack(material, 1, data);
		ItemMeta meta = item.getItemMeta();
		if(displayname != null) meta.setDisplayName(displayname);
		if(lore != null) meta.setLore(lore);
		if(enchantments != null)
		{
			for(Enchantment enchantment : enchantments.keySet())
			{
				meta.addEnchant(enchantment, enchantments.get(enchantment), true);
			}
		}
		item.setItemMeta(meta);
		return item;
	}
	
	@Override
	public ItemMeta getItemMeta()
	{
		return getItemStack().getItemMeta();
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
		return null;
	}

	@Override
	public EntityType getEntityType() 
	{
		return null;
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
	
	@SuppressWarnings("deprecation")
	public static ItemData getFromMeta(ItemStack item)
	{
		Material material = item.getType();
		short data = item.getData().getData();
		String displayname = null;
		List<String> lore = null;
		Map<Enchantment, Integer> enchantments = null;
		
    	if(item.hasItemMeta())
    	{
    		ItemMeta meta = item.getItemMeta();
    		if(meta.getDisplayName() != null)
    		{
    			displayname = meta.getDisplayName();
    		}
    		
    		if(meta.getLore() != null)
    		{
    			lore = meta.getLore();
    		}
    		
    		if(meta.hasEnchants())
    		{
    			enchantments = meta.getEnchants();
    		}
    	}
    	return new ItemData(material, data, displayname, lore, enchantments);
	}
	
}
