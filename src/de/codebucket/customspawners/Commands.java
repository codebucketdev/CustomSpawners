package de.codebucket.customspawners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.PotionMeta;

import de.codebucket.customspawners.data.EntityData;
import de.codebucket.customspawners.data.FireworkData;
import de.codebucket.customspawners.data.ItemData;
import de.codebucket.customspawners.data.ParticleData;
import de.codebucket.customspawners.data.PotionData;
import de.codebucket.customspawners.particle.Particle;
import de.codebucket.customspawners.spawner.CustomSpawner;
import de.codebucket.customspawners.spawner.SpawnerType;

public class Commands implements CommandExecutor
{
	CustomSpawners plugin;
	private String pre = "§6[§bc§aSpawners§6] §r";
	
	public Commands(CustomSpawners plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String arg, String[] args) 
	{
		if(cs instanceof Player)
		{
			Player p = (Player)cs;
			if(args.length == 0)
			{
				cs.sendMessage(pre + "§eVersion 1.0 by Codebucket");
				cs.sendMessage(pre + "§eType '/cs help' for help");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("help"))
			{
				p.sendMessage(pre + "§6========= §bCustomSpawners v1.0 §eHelp §6=========");
				p.sendMessage(pre + "§a/cs list");
				p.sendMessage(pre + "§6> §eLists all created spawners");
				p.sendMessage(pre + "§a/cs create <name> <type> [...]");
				p.sendMessage(pre + "§6> §eCreates a new spawner");
				p.sendMessage(pre + "§a/cs modify <name> <content|radius|ticks> [...]");
				p.sendMessage(pre + "§6> §eModifies an existing spawner");
				p.sendMessage(pre + "§a/cs remove <name>");
				p.sendMessage(pre + "§6> §eRemoves an existing spawner");
				p.sendMessage(pre + "§a/cs start <name>");
				p.sendMessage(pre + "§6> §eStarts an existing spawner");
				p.sendMessage(pre + "§a/cs stop <name>");
				p.sendMessage(pre + "§6> §eStops an existing spawner");
				p.sendMessage(pre + "§a/cs startall");
				p.sendMessage(pre + "§6> §eStarts all existing spawners");
				p.sendMessage(pre + "§a/cs stopall");
				p.sendMessage(pre + "§6> §eStopps all existing spawners");
				p.sendMessage(pre + "§a/cs tp <name>");
				p.sendMessage(pre + "§6> §eTeleports you to an existing spawner");
				p.sendMessage(pre + "§6=========================================");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("list"))
			{
				if(p.hasPermission("customspawners.list"))
				{
					p.sendMessage(pre + "§6=========== §bCustomSpawners: §eList §6===========");
					for(int i = 0; i < plugin.getData().getSpawners().size(); i++) 
					{
						CustomSpawner spw = plugin.getData().getSpawners().get(i);
						Location loc = spw.getLocation();
						ChatColor run = ChatColor.RED;
						if(spw.isRunning()) run = ChatColor.GREEN;
						String msg = pre + run + "#" + (i+1) + ". §e" + spw.getName() + " (World: " + loc.getWorld().getName() + "), Type: " + spw.getSpawnerType().toString();
						p.sendMessage(msg);
					}
					p.sendMessage(pre + "§6=========================================");
					return true;
				}
				else
				{
					p.sendMessage(pre + "§cYou don't have permission to execute this command!");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("create"))
			{
				if(p.hasPermission("customspawners.create"))
				{
					if(args.length >= 3)
					{
						String name = args[1];
						if(plugin.getData().getSpawner(name) == null)
						{
							String type = args[2];
							if(type.equalsIgnoreCase("item"))
							{
								if(args.length >= 5)
								{
									if(p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() != Material.AIR)
									{
										if(isInteger(args[3]) && isInteger(args[4]))
										{
											Location location = p.getLocation();
											location.setX(location.getBlockX() + 0.5);
											location.setY(location.getBlockY() + 0.0);
											location.setZ(location.getBlockZ() + 0.5);
											int radius = Integer.parseInt(args[3]);
											long ticks = Long.parseLong(args[4]);
											
											ItemStack item = p.getInventory().getItemInHand();
											ItemData data = ItemData.getFromMeta(item);
											CustomSpawner spawner = new CustomSpawner(name, SpawnerType.ITEM, data, location, radius, ticks);
											plugin.getData().addSpawner(spawner);
											spawner.start();
											
											p.sendMessage(pre + "§aSpawner '" + name + "' sucessfully created!");
											return true;
										}
										else
										{
											if(!isInteger(args[3]))
											{
												p.sendMessage(pre + "§c'" + args[3] + "' is not a valid value for radius.");
												p.sendMessage(pre + "§cUsage: /cs create <name> item <radius> <ticks>");
												return true;
											}
											if(!isInteger(args[4]))
											{
												p.sendMessage(pre + "§c'" + args[4] + "' is not a valid value for ticks.");
												p.sendMessage(pre + "§cUsage: /cs create <name> item <radius> <ticks>");
												return true;
											}
										}
									}
									else
									{
										p.sendMessage(pre + "§cYou have not selected any item in your hand!");
										p.sendMessage(pre + "§cUsage: /cs create <name> item <radius> <ticks>");
										return true;
									}
								}
								else 
								{
									p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
									p.sendMessage(pre + "§cUsage: /cs create <name> item <radius> <ticks>");
									return true;
								}
							}
							else if(type.equalsIgnoreCase("exp"))
							{
								if(args.length >= 5)
								{
									if(isInteger(args[3]) && isInteger(args[4]))
									{
										Location location = p.getLocation();
										location.setX(location.getBlockX() + 0.5);
										location.setY(location.getBlockY() + 0.0);
										location.setZ(location.getBlockZ() + 0.5);
										int radius = Integer.parseInt(args[3]);
										long ticks = Long.parseLong(args[4]);
										
										EntityData data = new EntityData(EntityType.EXPERIENCE_ORB);
										CustomSpawner spawner = new CustomSpawner(name, SpawnerType.EXP, data, location, radius, ticks);
										plugin.getData().addSpawner(spawner);
										spawner.start();
										
										p.sendMessage(pre + "§aSpawner '" + name + "' sucessfully created!");
										return true;
									}
									else
									{
										if(!isInteger(args[3]))
										{
											p.sendMessage(pre + "§c'" + args[3] + "' is not a valid value for radius.");
											p.sendMessage(pre + "§cUsage: /cs create <name> exp <radius> <ticks>");
											return true;
										}
										if(!isInteger(args[4]))
										{
											p.sendMessage(pre + "§c'" + args[4] + "' is not a valid value for ticks.");
											p.sendMessage(pre + "§cUsage: /cs create <name> exp <radius> <ticks>");
											return true;
										}
									}
								}
								else 
								{
									p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
									p.sendMessage(pre + "§cUsage: /cs create <name> exp <radius> <ticks>");
									return true;
								}
							}
							else if(type.equalsIgnoreCase("entity"))
							{
								if(args.length >= 6)
								{
									if(isEntityType(args[3]))
									{
										if(isInteger(args[4]) && isInteger(args[5]))
										{
											Location location = p.getLocation();
											location.setX(location.getBlockX() + 0.5);
											location.setY(location.getBlockY() + 0.0);
											location.setZ(location.getBlockZ() + 0.5);
											EntityType entity = EntityType.valueOf(args[3].toUpperCase());
											int radius = Integer.parseInt(args[4]);
											long ticks = Long.parseLong(args[5]);
											
											EntityData data = new EntityData(entity);
											CustomSpawner spawner = new CustomSpawner(name, SpawnerType.ENTITY, data, location, radius, ticks);
											plugin.getData().addSpawner(spawner);
											spawner.start();

											p.sendMessage(pre + "§aSpawner '" + name + "' sucessfully created!");
											return true;
										}
										else
										{
											if(!isInteger(args[4]))
											{
												p.sendMessage(pre + "§c'" + args[4] + "' is not a valid value for radius.");
												p.sendMessage(pre + "§cUsage: /cs create <name> entity <entitytype> <radius> <ticks>");
												return true;
											}
											if(!isInteger(args[5]))
											{
												p.sendMessage(pre + "§c'" + args[5] + "' is not a valid value for ticks.");
												p.sendMessage(pre + "§cUsage: /cs create <name> entity <entitytype> <radius> <ticks>");
												return true;
											}
										}
									}
									else
									{
										p.sendMessage(pre + "§cUnacceptable entity type '" + args[3] + "'.");
										p.sendMessage(pre + "§cUsage: /cs create <name> entity <entitytype> <radius> <ticks>");
										return true;
									}
								}
								else 
								{
									p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
									p.sendMessage(pre + "§cUsage: /cs create <name> entity <entitytype> <radius> <ticks>");
									return true;
								}
							}
							else if(type.equalsIgnoreCase("firework"))
							{
								if(args.length >= 5)
								{
									if(p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() == Material.FIREWORK)
									{
										if(isInteger(args[3]) && isInteger(args[4]))
										{
											Location location = p.getLocation();
											location.setX(location.getBlockX() + 0.5);
											location.setY(location.getBlockY() + 0.0);
											location.setZ(location.getBlockZ() + 0.5);
											int radius = Integer.parseInt(args[3]);
											long ticks = Long.parseLong(args[4]);
											
											ItemStack item = p.getInventory().getItemInHand();
											FireworkData data = FireworkData.getFromMeta((FireworkMeta) item.getItemMeta());											
											CustomSpawner spawner = new CustomSpawner(name, SpawnerType.FIREWORK, data, location, radius, ticks);
											plugin.getData().addSpawner(spawner);
											spawner.start();

											p.sendMessage(pre + "§aSpawner '" + name + "' sucessfully created!");
											return true;
										}
										else
										{
											if(!isInteger(args[3]))
											{
												p.sendMessage(pre + "§c'" + args[3] + "' is not a valid value for radius.");
												p.sendMessage(pre + "§cUsage: /cs create <name> firework <radius> <ticks>");
												return true;
											}
											if(!isInteger(args[4]))
											{
												p.sendMessage(pre + "§c'" + args[4] + "' is not a valid value for ticks.");
												p.sendMessage(pre + "§cUsage: /cs create <name> firework <radius> <ticks>");
												return true;
											}
										}
									}
									else
									{
										p.sendMessage(pre + "§cYou have not selected any firework in your hand!");
										p.sendMessage(pre + "§cUsage: /cs create <name> firework <radius> <ticks>");
										return true;
									}
								}
								else 
								{
									p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
									p.sendMessage(pre + "§cUsage: /cs create <name> firework <radius> <ticks>");
									return true;
								}
							}
							else if(type.equalsIgnoreCase("potion"))
							{
								if(args.length >= 5)
								{
									if(p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() == Material.POTION)
									{
										if(isInteger(args[3]) && isInteger(args[4]))
										{
											Location location = p.getLocation();
											location.setX(location.getBlockX() + 0.5);
											location.setY(location.getBlockY() + 0.0);
											location.setZ(location.getBlockZ() + 0.5);
											int radius = Integer.parseInt(args[3]);
											long ticks = Long.parseLong(args[4]);
											
											ItemStack item = p.getInventory().getItemInHand();
											PotionData data = PotionData.getFromMeta((PotionMeta) item.getItemMeta());										
											CustomSpawner spawner = new CustomSpawner(name, SpawnerType.POTION, data, location, radius, ticks);
											plugin.getData().addSpawner(spawner);
											spawner.start();

											p.sendMessage(pre + "§aSpawner '" + name + "' sucessfully created!");
											return true;
										}
										else
										{
											if(!isInteger(args[3]))
											{
												p.sendMessage(pre + "§c'" + args[3] + "' is not a valid value for radius.");
												p.sendMessage(pre + "§cUsage: /cs create <name> potion <radius> <ticks>");
												return true;
											}
											if(!isInteger(args[4]))
											{
												p.sendMessage(pre + "§c'" + args[4] + "' is not a valid value for ticks.");
												p.sendMessage(pre + "§cUsage: /cs create <name> potion <radius> <ticks>");
												return true;
											}
										}
									}
									else
									{
										p.sendMessage(pre + "§cYou have not selected any potion in your hand!");
										p.sendMessage(pre + "§cUsage: /cs create <name> potion <radius> <ticks>");
										return true;
									}
								}
								else 
								{
									p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
									p.sendMessage(pre + "§cUsage: /cs create <name> potion <radius> <ticks>");
									return true;
								}
							}
							else if(type.equalsIgnoreCase("particle"))
							{
								if(args.length >= 6)
								{
									if(isParticle(args[3]))
									{
										if(isInteger(args[4]) && isInteger(args[5]))
										{
											Location location = p.getLocation();
											location.setX(location.getBlockX() + 0.5);
											location.setY(location.getBlockY() + 0.0);
											location.setZ(location.getBlockZ() + 0.5);
											Particle particle = Particle.valueOf(args[3].toUpperCase());
											int radius = Integer.parseInt(args[4]);
											long ticks = Long.parseLong(args[5]);
											
											ParticleData data = ParticleData.getFromParticle(particle);
											CustomSpawner spawner = new CustomSpawner(name, SpawnerType.PARTICLE, data, location, radius, ticks);
											plugin.getData().addSpawner(spawner);
											spawner.start();

											p.sendMessage(pre + "§aSpawner '" + name + "' sucessfully created!");
											return true;
										}
										else
										{
											if(!isInteger(args[4]))
											{
												p.sendMessage(pre + "§c'" + args[4] + "' is not a valid value for radius.");
												p.sendMessage(pre + "§cUsage: /cs create <name> particle <particletype> <radius> <ticks>");
												return true;
											}
											if(!isInteger(args[5]))
											{
												p.sendMessage(pre + "§c'" + args[5] + "' is not a valid value for ticks.");
												p.sendMessage(pre + "§cUsage: /cs create <name> particle <particletype> <radius> <ticks>");
												return true;
											}
										}
									}
									else
									{
										p.sendMessage(pre + "§cUnacceptable particle type '" + args[3] + "'.");
										p.sendMessage(pre + "§cUsage: /cs create <name> particle <particletype> <radius> <ticks>");
										return true;
									}
								}
								else 
								{
									p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
									p.sendMessage(pre + "§cUsage: /cs create <name> particle <particletype> <radius> <ticks>");
									return true;
								}
							}
							else if(type.equalsIgnoreCase("boat"))
							{
								if(args.length >= 5)
								{
									if(isInteger(args[3]) && isInteger(args[4]))
									{
										Location location = p.getLocation();
										location.setX(location.getBlockX() + 0.5);
										location.setY(location.getBlockY() + 0.0);
										location.setZ(location.getBlockZ() + 0.5);
										int radius = Integer.parseInt(args[3]);
										long ticks = Long.parseLong(args[4]);
										
										EntityData data = new EntityData(EntityType.BOAT);
										CustomSpawner spawner = new CustomSpawner(name, SpawnerType.BOAT, data, location, radius, ticks);
										plugin.getData().addSpawner(spawner);
										spawner.start();

										p.sendMessage(pre + "§aSpawner '" + name + "' sucessfully created!");
										return true;
									}
									else
									{
										if(!isInteger(args[3]))
										{
											p.sendMessage(pre + "§c'" + args[3] + "' is not a valid value for radius.");
											p.sendMessage(pre + "§cUsage: /cs create <name> boat <radius> <ticks>");
											return true;
										}
										if(!isInteger(args[4]))
										{
											p.sendMessage(pre + "§c'" + args[4] + "' is not a valid value for ticks.");
											p.sendMessage(pre + "§cUsage: /cs create <name> boat <radius> <ticks>");
											return true;
										}
									}
								}
								else 
								{
									p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
									p.sendMessage(pre + "§cUsage: /cs create <name> boat <radius> <ticks>");
									return true;
								}
							}
							else if(type.equalsIgnoreCase("minecart"))
							{
								if(args.length >= 5)
								{
									if(isInteger(args[3]) && isInteger(args[4]))
									{
										Location location = p.getLocation();
										location.setX(location.getBlockX() + 0.5);
										location.setY(location.getBlockY() + 0.0);
										location.setZ(location.getBlockZ() + 0.5);
										int radius = Integer.parseInt(args[3]);
										long ticks = Long.parseLong(args[4]);
										
										EntityData data = new EntityData(EntityType.MINECART);
										CustomSpawner spawner = new CustomSpawner(name, SpawnerType.MINECART, data, location, radius, ticks);
										plugin.getData().addSpawner(spawner);
										spawner.start();

										p.sendMessage(pre + "§aSpawner '" + name + "' sucessfully created!");
										return true;
									}
									else
									{
										if(!isInteger(args[3]))
										{
											p.sendMessage(pre + "§c'" + args[3] + "' is not a valid value for radius.");
											p.sendMessage(pre + "§cUsage: /cs create <name> minecart <radius> <ticks>");
											return true;
										}
										if(!isInteger(args[4]))
										{
											p.sendMessage(pre + "§c'" + args[4] + "' is not a valid value for ticks.");
											p.sendMessage(pre + "§cUsage: /cs create <name> minecart <radius> <ticks>");
											return true;
										}
									}
								}
								else 
								{
									p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
									p.sendMessage(pre + "§cUsage: /cs create <name> minecart <radius> <ticks>");
									return true;
								}
							}
							else 
							{
								p.sendMessage(pre + "§cUnacceptable spawner type '" + type + "'.");
								p.sendMessage(pre + "§cAcceptable types: item, exp, entity, firework, potion, particle, boat, minecart");
								return true;
							}
						}
						else
						{
							p.sendMessage(pre + "§cSpawner with name '" + name + "' already exists!");
							return true;
						}
					}
					else
					{
						p.sendMessage(pre + "§cError in command syntax. Check command help.");
						return true;
					}					
				}
				else
				{
					p.sendMessage(pre + "§cYou don't have permission to execute this command!");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("modify"))
			{
				if(p.hasPermission("customspawners.modify"))
				{
					if(args.length >= 3)
					{
						String name = args[1];
						if(plugin.getData().getSpawner(name) != null)
						{
							String type = args[2];
							CustomSpawner spawner = plugin.getData().getSpawner(name);
							if(type.equalsIgnoreCase("content"))
							{
								if(spawner.getSpawnerType() == SpawnerType.ITEM)
								{
									if(p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() != Material.AIR)
									{
										boolean ran = spawner.isRunning();
										spawner.stop();
										ItemStack item = p.getInventory().getItemInHand();
										ItemData data = ItemData.getFromMeta(item);
										spawner.setSpawnerData(data);
										plugin.getData().editSpawner(spawner);
										if(ran == true) spawner.start();
										
										p.sendMessage(pre + "§aItem from spawner '" + name + "' sucessfully modified!");
										return true;
									}
									else
									{
										p.sendMessage(pre + "§cYou have not selected any item in your hand!");
										p.sendMessage(pre + "§cUsage: /cs modify <name> content");
										return true;
									}
								}
								else if(spawner.getSpawnerType() == SpawnerType.ENTITY)
								{
									if(args.length >= 4)
									{
										if(isEntityType(args[3]))
										{
											boolean ran = spawner.isRunning();
											spawner.stop();
											EntityType entity = EntityType.valueOf(args[3].toUpperCase());										
											EntityData data = new EntityData(entity);
											spawner.setSpawnerData(data);
											plugin.getData().editSpawner(spawner);
											spawner.start();
											if(ran == true) spawner.start();
	
											p.sendMessage(pre + "§aEntity from spawner '" + name + "' sucessfully modified!");
											return true;
										}
										else
										{
											p.sendMessage(pre + "§cUnacceptable entity type '" + args[3] + "'.");
											p.sendMessage(pre + "§cUsage: /cs modify <name> content <entitytype>");
											return true;
										}
									}
									else 
									{
										p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
										p.sendMessage(pre + "§cUsage: /cs modify <name> content <entitytype>");
										return true;
									}
								}
								else if(spawner.getSpawnerType() == SpawnerType.FIREWORK)
								{
									if(p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() == Material.FIREWORK)
									{
										boolean ran = spawner.isRunning();
										spawner.stop();
										ItemStack item = p.getInventory().getItemInHand();
										FireworkData data = FireworkData.getFromMeta((FireworkMeta) item.getItemMeta());											
										spawner.setSpawnerData(data);
										plugin.getData().editSpawner(spawner);
										spawner.start();
										if(ran == true) spawner.start();

										p.sendMessage(pre + "§aFirework from spawner '" + name + "' sucessfully modified!");
										return true;
									}
									else
									{
										p.sendMessage(pre + "§cYou have not selected any firework in your hand!");
										p.sendMessage(pre + "§cUsage: /cs modify <name> content");
										return true;
									}
								}
								else if(spawner.getSpawnerType() == SpawnerType.POTION)
								{
									if(p.getInventory().getItemInHand() != null && p.getInventory().getItemInHand().getType() == Material.POTION)
									{
										boolean ran = spawner.isRunning();
										spawner.stop();
										ItemStack item = p.getInventory().getItemInHand();
										PotionData data = PotionData.getFromMeta((PotionMeta) item.getItemMeta());										
										spawner.setSpawnerData(data);
										plugin.getData().editSpawner(spawner);
										spawner.start();
										if(ran == true) spawner.start();

										p.sendMessage(pre + "§aPotions from spawner '" + name + "' sucessfully modified!");
										return true;
									}
									else
									{
										p.sendMessage(pre + "§cYou have not selected any potion in your hand!");
										p.sendMessage(pre + "§cUsage: /cs modify <name> content");
										return true;
									}
								}
								else if(spawner.getSpawnerType() == SpawnerType.PARTICLE)
								{
									if(args.length >= 4)
									{
										if(isParticle(args[3]))
										{
											boolean ran = spawner.isRunning();
											spawner.stop();
											Particle particle = Particle.valueOf(args[3].toUpperCase());										
											ParticleData data = ParticleData.getFromParticle(particle);
											spawner.setSpawnerData(data);
											plugin.getData().editSpawner(spawner);
											spawner.start();
											if(ran == true) spawner.start();
	
											p.sendMessage(pre + "§aParticle from spawner '" + name + "' sucessfully modified!");
											return true;
										}
										else
										{
											p.sendMessage(pre + "§cUnacceptable particle type '" + args[3] + "'.");
											p.sendMessage(pre + "§cUsage: /cs modify <name> content <particletype>");
											return true;
										}
									}
									else 
									{
										p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
										p.sendMessage(pre + "§cUsage: /cs modify <name> content <particletype>");
										return true;
									}
								}
								else 
								{
									p.sendMessage(pre + "§cYou can not modify the contents of the spawner!");
									return true;
								}
							}
							else if(type.equalsIgnoreCase("radius"))
							{
								if(args.length >= 4)
								{
									if(!isInteger(args[3]))
									{
										p.sendMessage(pre + "§c'" + args[3] + "' is not a valid value for radius.");
										p.sendMessage(pre + "§cUsage: /cs modify <name> radius <radius>");
										return true;
									}
									
									int radius = Integer.parseInt(args[3]);
									boolean ran = spawner.isRunning();
									spawner.stop();
									spawner.setRadius(radius);
									plugin.getData().editSpawner(spawner);
									spawner.start();
									if(ran == true) spawner.start();

									p.sendMessage(pre + "§aRadius from spawner '" + name + "' sucessfully modified!");
									return true;
								}
								else 
								{
									p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
									p.sendMessage(pre + "§cUsage: /cs modify <name> radius <radius>");
									return true;
								}
							}
							else if(type.equalsIgnoreCase("ticks"))
							{
								if(args.length >= 4)
								{
									if(!isInteger(args[3]))
									{
										p.sendMessage(pre + "§c'" + args[3] + "' is not a valid value for ticks.");
										p.sendMessage(pre + "§cUsage: /cs modify <name> ticks <ticks>");
										return true;
									}
									
									long ticks = Long.parseLong(args[3]);
									boolean ran = spawner.isRunning();
									spawner.stop();
									spawner.setTicks(ticks);
									plugin.getData().editSpawner(spawner);
									spawner.start();
									if(ran == true) spawner.start();

									p.sendMessage(pre + "§aTicks from spawner '" + name + "' sucessfully modified!");
									return true;
								}
								else 
								{
									p.sendMessage(pre + "§cError in command syntax: Not enough arguments.");
									p.sendMessage(pre + "§cUsage: /cs modify <name> ticks <ticks>");
									return true;
								}
							}
							else 
							{
								p.sendMessage(pre + "§cUnacceptable modify type '" + type + "'.");
								p.sendMessage(pre + "§cAcceptable types: content, radius, ticks");
								return true;
							}
						}
						else
						{
							p.sendMessage(pre + "§cSpawner with name '" + name + "' not exists!");
							return true;
						}
					}
					else
					{
						p.sendMessage(pre + "§cError in command syntax. Check command help.");
						return true;
					}					
				}
				else
				{
					p.sendMessage(pre + "§cYou don't have permission to execute this command!");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("remove"))
			{
				if(p.hasPermission("customspawners.remove"))
				{
					if(args.length >= 2)
					{
						String name = args[1];
						if(plugin.getData().getSpawner(name) != null)
						{
							CustomSpawner spawner = plugin.getData().getSpawner(name);
							spawner.stop();
							plugin.getData().removeSpawner(spawner);
							p.sendMessage(pre + "§aSpawner '" + name + "' sucessfully removed!");
							return true;
						}
						else
						{
							p.sendMessage(pre + "§cSpawner with name '" + name + "' not exists!");
							return true;
						}
					}
					else
					{
						p.sendMessage(pre + "§cError in command syntax. Check command help.");
						return true;
					}					
				}
				else
				{
					p.sendMessage(pre + "§cYou don't have permission to execute this command!");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("start"))
			{
				if(p.hasPermission("customspawners.start"))
				{
					if(args.length >= 2)
					{
						String name = args[1];
						CustomSpawner spawner = plugin.getData().getSpawner(name);
						if(!spawner.isRunning())
						{
							spawner.start();
							p.sendMessage(pre + "§aSpawner '" + name + "' sucessfully started!");
							return true;
						}
						else
						{
							p.sendMessage(pre + "§cSpawner '" + name + "' already started!");
							return true;
						}
					}
					else
					{
						p.sendMessage(pre + "§cError in command syntax. Check command help.");
						return true;
					}					
				}
				else
				{
					p.sendMessage(pre + "§cYou don't have permission to execute this command!");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("stop"))
			{
				if(p.hasPermission("customspawners.stop"))
				{
					if(args.length >= 2)
					{
						String name = args[1];
						if(plugin.getData().getSpawner(name) != null)
						{
							CustomSpawner spawner = plugin.getData().getSpawner(name);
							if(spawner.isRunning())
							{
								spawner.stop();
								p.sendMessage(pre + "§aSpawner '" + name + "' sucessfully stopped!");
								return true;
							}
							else
							{
								p.sendMessage(pre + "§cSpawner '" + name + "' already stopped!");
								return true;
							}
						}
						else
						{
							p.sendMessage(pre + "§cSpawner with name '" + name + "' not exists!");
							return true;
						}
					}
					else
					{
						p.sendMessage(pre + "§cError in command syntax. Check command help.");
						return true;
					}					
				}
				else
				{
					p.sendMessage(pre + "§cYou don't have permission to execute this command!");
					return true;
				}
			}
				
			if(args[0].equalsIgnoreCase("startall"))
			{
				if(p.hasPermission("customspawners.startall"))
				{
					p.sendMessage(pre + "§eStarting all custom spawners..");
					for(CustomSpawner spawner : plugin.getData().getSpawners())
					{
						if(!spawner.isRunning())
						{
							spawner.start();
						}
					}
					p.sendMessage(pre + "§aAll spawners sucessfully started!");
					return true;
				}
				else
				{
					p.sendMessage(pre + "§cYou don't have permission to execute this command!");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("stopall"))
			{
				if(p.hasPermission("customspawners.stopall"))
				{
					p.sendMessage(pre + "§eStopping all custom spawners..");
					for(CustomSpawner spawner : plugin.getData().getSpawners())
					{
						if(spawner.isRunning())
						{
							spawner.stop();
						}
					}
					p.sendMessage(pre + "§aAll spawners sucessfully stopped!");	
					return true;
				}
				else
				{
					p.sendMessage(pre + "§cYou don't have permission to execute this command!");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("tp"))
			{
				if(p.hasPermission("customspawners.tp"))
				{
					if(args.length >= 2)
					{
						String name = args[1];
						if(plugin.getData().getSpawner(name) != null)
						{
							CustomSpawner spawner = plugin.getData().getSpawner(name);
							Location location = spawner.getLocation().clone();
							location.setPitch(p.getLocation().getPitch());
							location.setYaw(p.getLocation().getYaw());
							p.teleport(location);
							p.sendMessage(pre + "§aYou were teleported to spawner '" + spawner.getName() + "'!");
							return true;
						}
						else
						{
							p.sendMessage(pre + "§cSpawner with name '" + name + "' not exists!");
							return true;
						}
					}
					else
					{
						p.sendMessage(pre + "§cError in command syntax. Check command help.");
						return true;
					}					
				}
				else
				{
					p.sendMessage(pre + "§cYou don't have permission to execute this command!");
					return true;
				}
			}
			
			p.sendMessage(pre + "§cUnknown command. Type '/cs help' for help.");
			return true;
		}
		else
		{
			cs.sendMessage(pre + "§eThis command can only executed by a player!");
			return true;
		}
	}
	
	private boolean isInteger(String integer)
	{
		try
		{
			Integer.parseInt(integer);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}
	
	private boolean isEntityType(String entity)
	{
		try
		{
			EntityType.valueOf(entity.toUpperCase());
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	private boolean isParticle(String particle)
	{
		try
		{
			Particle.valueOf(particle.toUpperCase());
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
}
