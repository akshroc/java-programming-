package FindBiggestNumber;

public class Main 
{
	static int biggestNumber(int a, int b)
	{
		if (a>b)
			return a;
		else
			return b;
	}
	
	public static void main(String[] args) 
	{
		//for two number
		System.out.println("Biggest Number :"+biggestNumber(8, 10));
		//for three number
		System.out.println("Biggest Number :"+biggestNumber(15,biggestNumber(16, 8)));
		//for four number
		System.out.println("Biggest Number :"+biggestNumber(biggestNumber(79, 80),biggestNumber(14,65)));
		//for five number
		System.out.println("Biggest Number :"+biggestNumber(888, biggestNumber(biggestNumber(89, 887),biggestNumber(879, 2589))));
	    //so on....	
	}

}
