package de.codebucket.customspawners.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkData extends SpawnerData
{
	private Type type;
	private List<Color> colors;
	private List<Color> fades;
	private boolean flicker;
	private boolean trail;
	private int power;
	
	public FireworkData(Type type, List<Color> colors, List<Color> fades, boolean flicker, boolean trail, int power)
	{
		super(DataType.FIREWORK);
		this.type = type;
		this.colors = colors;
		this.fades = fades;
		this.flicker = flicker;
		this.trail = trail;
		this.power = power;
	}
	
	public Firework spawnFirework(Location location) 
	{
        Firework fw = location.getWorld().spawn(location, Firework.class);
        fw.setFireworkMeta(getFireworkMeta());
		return fw;
	}
	
	public ItemStack getFireworkItem()
	{
		ItemStack item = new ItemStack(Material.FIREWORK, 1);
		FireworkMeta meta = (FireworkMeta) item.getItemMeta();
        Builder effect = FireworkEffect.builder();
       
        if(type != null) 
        {
        	effect.with(type);
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
        item.setItemMeta(meta);
        return item;
	}

	public FireworkMeta getFireworkMeta()
	{
		return (FireworkMeta) getFireworkItem().getItemMeta();
	}
	
	public static FireworkData getFromMeta(ItemStack item)
	{
		FireworkMeta meta = (FireworkMeta) item.getItemMeta();
		Type fwtype = null;
		List<Color> fwcolors = null;
		List<Color> fwfades = null;
		boolean fwflicker = false;
		boolean fwtrail = false;
		int fwpower = 0;
		
		if(meta != null)
		{
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
	    		fwpower = meta.getPower();
	    	}
		}
		return new FireworkData(fwtype, fwcolors, fwfades, fwflicker, fwtrail, fwpower);
	}
	
}
