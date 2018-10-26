package EvenNumberInNaturalNumber;

public class Main
{
	static int countEvenNumber(int n)
	{
		int count=0;
		for (int i = 0; i <=n; i++)
		{
			if (i%2==0) 
			{
				count++;
			}
		}
		return count;
	}
	public static void main(String[] args)
	{
		System.out.println("No. of even number :"+countEvenNumber(15));
	}
}
