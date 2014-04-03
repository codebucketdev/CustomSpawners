package de.codebucket.customspawners.data;

public abstract class SpawnerData
{
	public enum DataType
	{
		ITEM,
		ENTITY,
		FIREWORK,
		POTION,
		PARTICLE;
	}
	
	private DataType type;
	
	public SpawnerData(DataType type)
	{
		this.type = type;
	}
	
	public DataType getDataType()
	{
		return type;
	}
	
	public void setDataType(DataType type)
	{
		this.type = type;
	}
	
}
