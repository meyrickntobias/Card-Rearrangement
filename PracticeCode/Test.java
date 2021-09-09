import java.util.Arrays;

/**
 * Testing out CP class for the purposes of the report.
 * What is the maximum count value produced by any spec, row length, and pile size up to 20? 
 * 
 */

public class Test {

	public static void main(String[] args) {
		CP test = new CP();
		allMaximumCountSpecs(test);
	}
	
	public static void printResults(CP c, int rowLength, String spec) {
		do {
			c.transform(rowLength, spec);
			c.printPile();
		} while (!Arrays.equals(c.getTemp(), c.getPile()));
	}
	
	public static void maxCount(CP c) {
		int pileSizeCounter = 2;
		int maximumCount = 0;
		while (pileSizeCounter <= 20) {
			c.load(pileSizeCounter);
			int[] factors = getFactors(pileSizeCounter);
			for (int rowLength : factors) {
				System.out.println("TL, pile size: " + pileSizeCounter + ", row length: " + rowLength + ", count: " + c.count(rowLength, "TL"));
				System.out.println("BL, pile size: " + pileSizeCounter + ", row length: " + rowLength + ", count: " + c.count(rowLength, "BL"));
				System.out.println("TR, pile size: " + pileSizeCounter + ", row length: " + rowLength + ", count: " + c.count(rowLength, "TR"));
				System.out.println("BR, pile size: " + pileSizeCounter + ", row length: " + rowLength + ", count: " + c.count(rowLength, "BR"));
			}
			pileSizeCounter++;
		}
	}
	
	public static void allMaximumCountSpecs(CP c) {
		int pileSizeCounter = 2;
		while (pileSizeCounter <= 20) {
			c.load(pileSizeCounter);
			int[] factors = getFactors(pileSizeCounter);
			for (int rowLength : factors) {
				if (c.count(rowLength, "TL") >= 18) {
					System.out.println("TL, pile size: " + pileSizeCounter + ", row length: " + rowLength + ", count: " + c.count(rowLength, "TL"));
				} else if (c.count(rowLength, "BL") >= 18) {
					System.out.println("BL, pile size: " + pileSizeCounter + ", row length: " + rowLength + ", count: " + c.count(rowLength, "BL"));
				} else if (c.count(rowLength, "TR") >= 18) {
					System.out.println("TR, pile size: " + pileSizeCounter + ", row length: " + rowLength + ", count: " + c.count(rowLength, "TR"));
				} else if (c.count(rowLength, "BR") >= 18) {
					System.out.println("BR, pile size: " + pileSizeCounter + ", row length: " + rowLength + ", count: " + c.count(rowLength, "BR"));
				}
			}
			pileSizeCounter++;
		}
	}
	
	private static int[] getFactors(int n) {
		int[] factors = new int[5];
		int arrCounter = 0;
		for (int i = n/2; i > 1; i--) {
			if (n % i == 0) {
				if (arrCounter >= factors.length) {
					factors = Arrays.copyOf(factors, factors.length*2);
				}
				factors[arrCounter] = i;
				arrCounter++;
			}
		}
		factors = Arrays.copyOf(factors, arrCounter);
		return factors;
	}
}