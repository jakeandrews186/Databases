
public class Category 
{
	private int cg_id; 
	private int c_id; 
	private String name; 
	private long weight; 
	
	public Category (int C_id, String Name, long Weight)
	{
		this.c_id = C_id;
		this.name = Name;
		this.weight = Weight; 
	}
	
	public int get_cg_id()
	{
		return this.cg_id; 
	}
	
	public int get_c_id()
	{
		return this.c_id; 
	}
	
	public String get_name()
	{
		return this.name; 
	}
	
	public long get_weight()
	{
		return this.weight; 
	}
	
	public void set_c_id(int C_id)
	{
		this.cg_id = C_id; 
	}
	
	public void set_name(String Name)
	{
		this.name = Name;
	}
	
	public void set_weight(long Weight)
	{
		this.weight = Weight; 
	}
		
}
