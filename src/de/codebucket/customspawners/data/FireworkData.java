package de.codebucket.customspawners.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class FireworkData extends SpawnerData
{
	private Type fwtype;
	private List<Color> colors;
	private List<Color> fades;
	private boolean flicker;
	private boolean trail;
	private int power;
	
	public FireworkData(Type fwtype, List<Color> colors, List<Color> fades, boolean flicker, boolean trail, int power)
	{
		super(DataType.FIREWORK);
		this.fwtype = fwtype;
		this.colors = colors;
		this.fades = fades;
		this.flicker = flicker;
		this.trail = trail;
		this.power = power;
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
        Firework fw = location.getWorld().spawn(location, Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        Builder effect = FireworkEffect.builder();
       
        if(fwtype != null) 
        {
        	effect.with(fwtype);
        }
        
        if(colors != null)
        {
	        for(Color color : colors) 
			{
	        	effect.withColor(color);
			}
        }
        
        if(fades != null)
        {
	        for(Color fade : fades)
	        {
	        	effect.withFade(fade);
	        }
        }
        
        if(flicker == true)
        {
        	effect.withFlicker();
        }
        
        if(trail == true)
        {
        	effect.withTrail();
        }
        
        meta.addEffect(effect.build());
        meta.setPower(power);
        fw.setFireworkMeta(meta);
		return fw;
	}

	@Override
	public FireworkMeta getFireworkMeta()
	{
		Firework fw = spawnFirework(Bukkit.getWorlds().get(0).getSpawnLocation());
		FireworkMeta meta = fw.getFireworkMeta();
		fw.remove();
		return meta;
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
	
	public static FireworkData getFromMeta(FireworkMeta meta)
	{
		Type fwtype = null;
		List<Color> fwcolors = null;
		List<Color> fwfades = null;
		boolean fwflicker = false;
		boolean fwtrail = false;
		int fwpower = meta.getPower();
		
		if(meta.hasEffects())
    	{
    		FireworkEffect fweffect = meta.getEffects().get(0);	    		
    		if(fweffect.getType() != null)
    		{
    	    	fwtype = fweffect.getType();
    		}
    		
    		if(fweffect.getColors() != null)
    		{
    			fwcolors = new ArrayList<>();
    	    	for(Color colr : fweffect.getColors())
    	    	{
    	    		fwcolors.add(colr);
    	    	}
    		}
    		
    		if(fweffect.getFadeColors() != null)
    		{
    			fwfades = new ArrayList<>();
    	    	for(Color colr : fweffect.getFadeColors())
    	    	{
    	    		fwfades.add(colr);
    	    	}
    		}
    		
    		fwflicker = fweffect.hasFlicker();
    		fwtrail = fweffect.hasTrail();
    	}
		return new FireworkData(fwtype, fwcolors, fwfades, fwflicker, fwtrail, fwpower);
	}
}
