
public class Class 
{	
	private int c_id; 
	private String c_number;
	private String term;
	private int sec_number; 
	private String description; 
	
	public Class (String C_number, String Term, int Sec_number) //constructor w/o description
	{
		this.c_number = C_number;
		this.term = Term;
		this.sec_number = Sec_number;
	}
	
	public Class (String C_number, String Term, int Sec_number, String Description)
	{
		this.c_number = C_number;
		this.term = Term;
		this.sec_number = Sec_number;
		this.description = Description;
	}
	
	public int get_c_id()
	{
		return this.c_id; 
	}
	
	public String get_c_number()
	{
		return this.c_number;
	}
	
	public String get_term()
	{
		return this.term;
	}
	
	public int get_sec_number()
	{
		return this.sec_number;
	}
	
	public String get_description()
	{
		return this.description;
	}
	
	public void set_c_number(String C_number)
	{
		this.c_number = C_number;
	}
	
	public void set_term(String Term)
	{
		this.term = Term;
	}
	
	public void set_sec_number(int Sec_number)
	{
		this.sec_number = Sec_number;
	}
	
	public void set_description(String Description)
	{
		this.description = Description;
	}
}
