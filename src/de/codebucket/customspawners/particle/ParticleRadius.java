package de.codebucket.customspawners.particle;

public class ParticleRadius 
{
	private Float x;
	private Float y;
	private Float z;
	private Float speed;
	private Integer amount;
	
	public ParticleRadius(Float x, Float y, Float z, Float speed, Integer amount)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.speed = speed;
		this.amount = amount;
	}
	
	public Float getX()
	{
		return x;
	}
	
	public Float getY()
	{
		return y;
	}
	
	public Float getZ()
	{
		return z;
	}
	
	public Float getSpeed()
	{
		return speed;
	}
	
	public Integer getAmount()
	{
		return amount;
	}
}
