package SumOfDigit;

public class Main
{
	static int sumOfNumber(int n)
	{
		int sum=0;
		do {
			int r=n%10;
			sum += r;
			n=n/10;
		} while (n!=0);
		return sum;
	}
	public static void main(String[] args) 
	{
		System.out.println("Sum of Number :"+sumOfNumber(25648));
	}
}
