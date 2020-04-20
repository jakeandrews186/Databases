
public class Grade 
{
	private int g_id;
	private int cg_id;
	private int c_id;
	private int a_id;
	private String grade; 
	
	public Grade (int Cg_id, int C_id, int A_id, String Grade)
	{
		this.cg_id = Cg_id; 
		this.c_id = C_id;
		this.a_id = A_id;
		this.grade = Grade; 
	}
	
	public int get_g_id()
	{
		return this.g_id; 
	}
	
	public int get_cg_id()
	{
		return this.cg_id; 
	}
	
	public int get_c_id()
	{
		return this.c_id; 
	}
	
	public int get_a_id()
	{
		return this.a_id; 
	}
	
	public String get_grade()
	{
		return this.grade;
	}
	
	public void set_cg_id(int Cg_id)
	{
		this.cg_id = Cg_id; 
	}
	
	public void set_c_id(int C_id)
	{
		this.c_id = C_id; 
	}
	
	public void set_a_id(int A_id)
	{
		this.a_id = A_id; 
	}
	
	public void set_grade(String Grade)
	{
		this.grade = Grade; 
	}
	
}
