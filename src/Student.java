
public class Student 
{
	private int s_id;
	private String user_name;
	private String name; 
	
	public Student (String User_name, String Name)
	{
		this.user_name = User_name;
		this.name = Name; 
	}
	
	public int get_s_id()
	{
		return this.s_id; 
	}
	
	public String get_user_name()
	{
		return this.user_name; 
	}
	
	public String get_name()
	{
		return this.name; 
	}
	
	public void set_user_name(String User_name)
	{
		this.user_name = User_name;
	}
	
	public void set_name(String Name)
	{
		this.name = Name;
	}
	
	
}
