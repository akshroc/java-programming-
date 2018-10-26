package CountNumberOfDigit;

public class Main 
{
	static int countNumberOfDigit(int n)
	{
		int count=0;
		do {
			n=n/10;
			count++;
		} while (n!=0);
		return count;
		
	}
	public static void main(String[] args)
	{
		System.out.println("Number of digit :"+countNumberOfDigit(123456));
	}
}
