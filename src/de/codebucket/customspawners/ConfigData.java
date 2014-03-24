package de.codebucket.customspawners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import de.codebucket.customspawners.data.EntityData;
import de.codebucket.customspawners.data.FireworkData;
import de.codebucket.customspawners.data.ItemData;
import de.codebucket.customspawners.spawner.CustomSpawner;
import de.codebucket.customspawners.spawner.SpawnerType;
import de.codebucket.customspawners.util.ColorSerialiser;
import de.codebucket.customspawners.util.ItemSerialiser;
import de.codebucket.customspawners.util.LocationSerialiser;

public class ConfigData 
{
	CustomSpawners plugin;
	
	private FileConfiguration config;
	private File config_file = new File("plugins/CustomSpawners/spawners.yml");
	private List<CustomSpawner> spawners = new ArrayList<>();
	
	public ConfigData(CustomSpawners plugin)
	{
		this.plugin = plugin;
	}
	
	public void loadConfig()
	{
		config = null;
		spawners.clear();		
		if(config_file.exists())
		{
			config = YamlConfiguration.loadConfiguration(config_file);
		}
		else
		{
			plugin.saveResource("spawners.yml", false);
			config = YamlConfiguration.loadConfiguration(config_file);
		}
		
		loadSpawners();
	}
	
	public FileConfiguration getConfig()
	{
		return config;
	}
	
	public void saveConfig()
	{
		try 
		{
			config.save(config_file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void unloadConfig()
	{
		config = null;
		spawners.clear();
	}
	
	public void reloadConfig()
	{
		unloadConfig();
		loadConfig();
	}
	
	@SuppressWarnings("deprecation")
	private void loadSpawners()
	{
		ConfigurationSection s = this.config.getConfigurationSection("spawners");
		for(String section : s.getKeys(false)) 
		{
		    ConfigurationSection cs = s.getConfigurationSection(section);
		    String sptype = cs.getString("type");
		    SpawnerType type = SpawnerType.valueOf(sptype.toUpperCase());
		    if(type == SpawnerType.ITEM)
		    {
		    	ConfigurationSection ics = cs.getConfigurationSection("itemdata");
		    	ItemStack item = ItemSerialiser.stringToItem(ics.getString("material"));
		    	Material material = item.getType();
		    	short data = item.getData().getData();
		    	String displayname = null;
		    	List<String> lore = null;
		    	Map<Enchantment, Integer> enchantments = null;
		    	
		    	if(ics.isSet("displayname"))
		    	{
		    		displayname = ChatColor.translateAlternateColorCodes('&', ics.getString("displayname"));
		    	}
		    	
		    	if(ics.isSet("lore"))
		    	{
		    		lore = new ArrayList<>();
			    	for(String disp : ics.getStringList("lore"))
			    	{
			    		lore.add(ChatColor.translateAlternateColorCodes('&', disp));
			    	}
		    	}
		    	
		    	if(ics.isSet("enchantments"))
		    	{
		    		enchantments = new HashMap<>();
			    	for(String ench : ics.getStringList("enchantments"))
			    	{
			    		String[] values = ench.split(",");
			    		Enchantment enchantment = Enchantment.getByName(values[0].toUpperCase());
			    		Integer level = Integer.parseInt(values[1]);
			    		enchantments.put(enchantment, level);
			    	}
		    	}
		    	
		    	Location location = LocationSerialiser.stringToLocationEntity(cs.getString("location"));
		    	int radius = cs.getInt("radius");
		    	long ticks = cs.getLong("ticks");
		    	
		    	ItemData itemdata = new ItemData(material, data, displayname, lore, enchantments);
		    	CustomSpawner spawner = new CustomSpawner(section, type, itemdata, location, radius, ticks);
		    	spawners.add(spawner);
		    }
		    else if(type == SpawnerType.EXP)
		    {
		    	Location location = LocationSerialiser.stringToLocationEntity(cs.getString("location"));
		    	int radius = cs.getInt("radius");
		    	long ticks = cs.getLong("ticks");
		    	
		    	EntityData entitydata = new EntityData(EntityType.EXPERIENCE_ORB);
		    	CustomSpawner spawner = new CustomSpawner(section, type, entitydata, location, radius, ticks);
		    	spawners.add(spawner);
		    }
		    else if(type == SpawnerType.ENTITY)
		    {
		    	EntityType entity = EntityType.valueOf(cs.getString("entity").toUpperCase());
		    	Location location = LocationSerialiser.stringToLocationEntity(cs.getString("location"));
		    	int radius = cs.getInt("radius");
		    	long ticks = cs.getLong("ticks");
		    	
		    	EntityData entitydata = new EntityData(entity);
		    	CustomSpawner spawner = new CustomSpawner(section, type, entitydata, location, radius, ticks);
		    	spawners.add(spawner);
		    }
		    else if(type == SpawnerType.FIREWORK)
		    {
		    	ConfigurationSection fws = cs.getConfigurationSection("fireworkdata");
		    	Type fwtype = null;
		    	List<Color> colors = null;
		    	List<Color> fades = null;
		    	boolean flicker = fws.getBoolean("flicker");
		    	boolean trail = fws.getBoolean("trail");
		    	int power = fws.getInt("power");
		    	
		    	if(fws.isSet("type"))
		    	{
		    		fwtype = Type.valueOf(fws.getString("type").toUpperCase());
		    	}
		    	
		    	if(fws.isSet("colors"))
		    	{
		    		colors = new ArrayList<>();
			    	for(String colr : fws.getStringList("colors"))
			    	{
			    		Color color = ColorSerialiser.stringToColor(colr);
			    		colors.add(color);
			    	}
		    	}
		    	
		    	if(fws.isSet("fades"))
		    	{
		    		fades = new ArrayList<>();
			    	for(String colr : fws.getStringList("fades"))
			    	{
			    		Color color = ColorSerialiser.stringToColor(colr);
			    		fades.add(color);
			    	}
		    	}
		    	
		    	Location location = LocationSerialiser.stringToLocationEntity(cs.getString("location"));
		    	int radius = cs.getInt("radius");
		    	long ticks = cs.getLong("ticks");
		    	
		    	FireworkData fwdata = new FireworkData(fwtype, colors, fades, flicker, trail, power);
		    	CustomSpawner spawner = new CustomSpawner(section, type, fwdata, location, radius, ticks);
		    	spawners.add(spawner);
		    }
		    else if(type == SpawnerType.BOAT)
		    {
		    	Location location = LocationSerialiser.stringToLocationEntity(cs.getString("location"));
		    	int radius = cs.getInt("radius");
		    	long ticks = cs.getLong("ticks");
		    	
		    	EntityData entitydata = new EntityData(EntityType.BOAT);
		    	CustomSpawner spawner = new CustomSpawner(section, type, entitydata, location, radius, ticks);
		    	spawners.add(spawner);
		    }
		    else if(type == SpawnerType.MINECART)
		    {
		    	Location location = LocationSerialiser.stringToLocationEntity(cs.getString("location"));
		    	int radius = cs.getInt("radius");
		    	long ticks = cs.getLong("ticks");
		    	
		    	EntityData entitydata = new EntityData(EntityType.MINECART);
		    	CustomSpawner spawner = new CustomSpawner(section, type, entitydata, location, radius, ticks);
		    	spawners.add(spawner);
		    }
		}
	}
	
	public CustomSpawner getSpawner(String name)
	{
		for(CustomSpawner spawner : getSpawners())
		{
			if(spawner.getName().equals(name))
			{
				return spawner;
			}
		}
		return null;
	}
	
	public List<CustomSpawner> getSpawners()
	{
		return spawners;
	}
	
	public void addSpawner(CustomSpawner spawner)
	{
		ConfigurationSection cs = config.getConfigurationSection("spawners." + spawner.getName());
		if(cs == null) cs = config.createSection("spawners." + spawner.getName());
		if(spawner.getSpawnerType() == SpawnerType.ITEM)
	    {
			cs.set("type", spawner.getSpawnerType().toString());
	    	ConfigurationSection ics = cs.getConfigurationSection("itemdata");
	    	if(ics == null) ics = cs.createSection("itemdata");
	    	ics.set("material", ItemSerialiser.itemToString(spawner.getSpawnerData().getItemStack()));
	    	if(spawner.getSpawnerData().getItemStack().hasItemMeta())
	    	{
	    		if(spawner.getSpawnerData().getItemMeta().getDisplayName() != null)
	    		{
	    			ics.set("displayname", decodeColors(spawner.getSpawnerData().getItemMeta().getDisplayName()));
	    		}
	    		
	    		if(spawner.getSpawnerData().getItemMeta().getLore() != null)
	    		{
	    			List<String> lore = new ArrayList<>();
	    			List<String> desc = spawner.getSpawnerData().getItemMeta().getLore();
	    	    	for(String disp : desc)
	    	    	{
	    	    		lore.add(decodeColors(disp));
	    	    	}
	    	    	ics.set("lore", lore);
	    		}
	    		
	    		if(spawner.getSpawnerData().getItemMeta().hasEnchants())
	    		{
	    			List<String> enchantments = new ArrayList<>();
	    			Map<Enchantment, Integer> enchs = spawner.getSpawnerData().getItemMeta().getEnchants();
	    	    	for(Enchantment ench : enchs.keySet())
	    	    	{
	    	    		String enchantment = ench.getName().toUpperCase() + "," + enchs.get(ench);
	    	    		enchantments.add(enchantment);
	    	    	}
	    	    	ics.set("enchantments", enchantments);
	    		}
	    	}
	    	
	    	cs.set("location", LocationSerialiser.locationEntityToString(spawner.getLocation()));
	    	cs.set("radius", spawner.getRadius());
	    	cs.set("ticks", spawner.getTicks());
	    	spawners.add(spawner);
	    	this.saveConfig();
	    }
	    else if(spawner.getSpawnerType() == SpawnerType.EXP)
	    {
	    	cs.set("type", spawner.getSpawnerType().toString());
	    	cs.set("location", LocationSerialiser.locationEntityToString(spawner.getLocation()));
	    	cs.set("radius", spawner.getRadius());
	    	cs.set("ticks", spawner.getTicks());
	    	spawners.add(spawner);
	    	this.saveConfig();
	    }
	    else if(spawner.getSpawnerType() == SpawnerType.ENTITY)
	    {
	    	cs.set("type", spawner.getSpawnerType().toString());
	    	cs.set("entity", spawner.getSpawnerData().getEntityType().toString());
	    	cs.set("location", LocationSerialiser.locationEntityToString(spawner.getLocation()));
	    	cs.set("radius", spawner.getRadius());
	    	cs.set("ticks", spawner.getTicks());
	    	spawners.add(spawner);
	    	this.saveConfig();
	    }
	    else if(spawner.getSpawnerType() == SpawnerType.FIREWORK)
	    {
			cs.set("type", spawner.getSpawnerType().toString());
	    	ConfigurationSection fws = cs.getConfigurationSection("fireworkdata");
	    	if(fws == null) fws = cs.createSection("fireworkdata");
	    	if(spawner.getSpawnerData().getFireworkMeta().hasEffects())
	    	{
	    		FireworkEffect fweffect = spawner.getSpawnerData().getFireworkMeta().getEffects().get(0);	    		
	    		if(fweffect.getType() != null)
	    		{
	    	    	fws.set("type", fweffect.getType().toString());
	    		}
	    		
	    		if(fweffect.getColors() != null)
	    		{
	    			List<String> colors = new ArrayList<>();
	    	    	for(Color colr : fweffect.getColors())
	    	    	{
	    	    		String color = ColorSerialiser.colorToString(colr);
	    	    		colors.add(color);
	    	    	}
	    	    	fws.set("colors", colors);
	    		}
	    		
	    		if(fweffect.getFadeColors() != null)
	    		{
	    			List<String> fades = new ArrayList<>();
	    	    	for(Color colr : fweffect.getFadeColors())
	    	    	{
	    	    		String color = ColorSerialiser.colorToString(colr);
	    	    		fades.add(color);
	    	    	}
	    	    	fws.set("fades", fades);
	    		}
	    		
	    		fws.set("flicker", fweffect.hasFlicker());
	    		fws.set("trail", fweffect.hasTrail());
	    	}
	    	
	    	fws.set("power", spawner.getSpawnerData().getFireworkMeta().getPower());
	    	cs.set("location", LocationSerialiser.locationEntityToString(spawner.getLocation()));
	    	cs.set("radius", spawner.getRadius());
	    	cs.set("ticks", spawner.getTicks());
	    	spawners.add(spawner);
	    	this.saveConfig();
	    }
	    else if(spawner.getSpawnerType() == SpawnerType.BOAT)
	    {
	    	cs.set("type", spawner.getSpawnerType().toString());
	    	cs.set("location", LocationSerialiser.locationEntityToString(spawner.getLocation()));
	    	cs.set("radius", spawner.getRadius());
	    	cs.set("ticks", spawner.getTicks());
	    	spawners.add(spawner);
	    	this.saveConfig();
	    }
	    else if(spawner.getSpawnerType() == SpawnerType.MINECART)
	    {
	    	cs.set("type", spawner.getSpawnerType().toString());
	    	cs.set("location", LocationSerialiser.locationEntityToString(spawner.getLocation()));
	    	cs.set("radius", spawner.getRadius());
	    	cs.set("ticks", spawner.getTicks());
	    	spawners.add(spawner);
	    	this.saveConfig();
	    }
	}
	
	public void editSpawner(CustomSpawner spawner)
	{
		removeSpawner(spawner);
		addSpawner(spawner);
	}
	
	public void removeSpawner(CustomSpawner spawner)
	{
		config.set("spawners." + spawner.getName(), null);
		spawners.remove(spawner);
		this.saveConfig();
	}
	
	private String decodeColors(String line)
	{
		line = line.replaceAll(ChatColor.BLACK.toString(), "&0");
		line = line.replaceAll(ChatColor.DARK_BLUE.toString(), "&1");
		line = line.replaceAll(ChatColor.DARK_GREEN.toString(), "&2");
		line = line.replaceAll(ChatColor.DARK_AQUA.toString(), "&3");
		line = line.replaceAll(ChatColor.DARK_RED.toString(), "&4");
		line = line.replaceAll(ChatColor.DARK_PURPLE.toString(), "&5");
		line = line.replaceAll(ChatColor.GOLD.toString(), "&6");
		line = line.replaceAll(ChatColor.GRAY.toString(), "&7");
		line = line.replaceAll(ChatColor.DARK_GRAY.toString(), "&8");
		line = line.replaceAll(ChatColor.BLUE.toString(), "&9");
		line = line.replaceAll(ChatColor.GREEN.toString(), "&a");
		line = line.replaceAll(ChatColor.AQUA.toString(), "&b");
		line = line.replaceAll(ChatColor.RED.toString(), "&c");
		line = line.replaceAll(ChatColor.LIGHT_PURPLE.toString(), "&d");
		line = line.replaceAll(ChatColor.YELLOW.toString(), "&e");
		line = line.replaceAll(ChatColor.WHITE.toString(), "&f");
		line = line.replaceAll(ChatColor.STRIKETHROUGH.toString(), "&m");
		line = line.replaceAll(ChatColor.UNDERLINE.toString(), "&n");
		line = line.replaceAll(ChatColor.BOLD.toString(), "&l");
		line = line.replaceAll(ChatColor.MAGIC.toString(), "&k");
		line = line.replaceAll(ChatColor.ITALIC.toString(), "&o");
		line = line.replaceAll(ChatColor.RESET.toString(), "&r");
		return line;
	}
}
