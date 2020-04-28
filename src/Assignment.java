
public class Assignment 
{
	private int a_id; 
	//private int cg_id; 
	private String cg_name;
	private String name;
	private String description; 
	private int point_value; 
	//private int s_id; 
	private String grade; 
	
//	public Assignment (int Cg_id, String Name, int Point_value, int S_id, String Grade) //constructor w/o description
//	{
//		this.cg_id = Cg_id;
//		this.name = Name;
//		this.point_value = Point_value;
//		this.s_id = S_id;
//		this.grade = Grade; 
//	}
//	
//	public Assignment (int Cg_id, String Name, String Description, int Point_value, int S_id, String Grade)
//	{
//		this.cg_id = Cg_id;
//		this.name = Name;
//		this.description = Description;
//		this.point_value = Point_value;
//		this.s_id = S_id;
//		this.grade = Grade; 
//	}
	
	public Assignment (String Name0, String Name1, String Description, int Point_value)
	{
		this.name = Name0;
		this.cg_name = Name1;
		this.description = Description;
		this.point_value = Point_value;
	}
		
	public int get_a_id()
	{
		return this.a_id; 
	}
	
	public String get_cg_name()
	{
		return this.cg_name; 
	}
	
	public String get_name()
	{
		return this.name; 
	}
	
	public String get_description()
	{
		return this.description; 
	}
	
	public int get_point_value()
	{
		return this.point_value; 
	}
	
//	public int get_s_id()
//	{
//		return this.s_id; 
//	}
	
	public String get_grade()
	{
		return this.grade; 
	}
	
	public void set_cg_name(String Cg_name)
	{
		this.cg_name = Cg_name; 
	}
	
	public void set_name(String Name)
	{
		this.name = Name; 
	}
	
	public void set_description(String Description)
	{
		this.description = Description; 
	}
	
	public void set_point_value(int Point_value)
	{
		this.point_value = Point_value; 
	}
	
	public void set_grade(String Grade)
	{
		this.grade = Grade; 
	}

}
