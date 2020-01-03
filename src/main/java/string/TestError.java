package string;

public class TestError {
	public static void main(String[] args) {
	
			try
			{
				throw new Exception();
			}
			catch (Exception e)
			{
				System.out.println("error1");
			}
			System.out.println("error2");
	}
}
