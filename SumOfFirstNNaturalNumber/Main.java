package SumOfFirstNNaturalNumber;

public class Main
{
	static int sumOfNaturalNumber(int n)
	{
		int sum=0;
		while (n>0) 
		{
			int r=n%10;
			sum +=r;
			n--;
		}
		return sum;
	}
	public static void main(String[] args)
	{
		System.out.println("Sum of n natural number :"+sumOfNaturalNumber(5));
	}
}
