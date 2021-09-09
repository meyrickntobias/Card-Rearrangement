import java.util.Arrays;

public class PermutationTest {
	/**
	* Testing out the class CP for the purposes of the report
	* Test "accessibility" of all possible card piles (1 through 6)
	*/
	private static int transformationsCounter = 0;
	private static int[][] transformations = new int[720][6];
	private static String[] specs = new String[]{"TL","BL","TR","BR","LB","RT","RB"};
	
	public static void main(String[] args) {
		// try find out how many possible permutations for row length of 3
		CP test = new CP();
		int[] rowLengths = getFactors(6);
		for (int rowLength : rowLengths) {
			for (String specification : specs) {
				test.load(6);
				test.transform(rowLength, specification);
				/*
				transformationsCounter++;
					transformations[transformationsCounter] = 
				*/
				recursiveTransformations(test, test.getTemp(), rowLength);
			}
		}
		
		System.out.println(Arrays.deepToString(transformations));
		System.out.println(transformationsCounter);
	}
	
	private static void recursiveTransformations(CP c, int[] current, int rowLength) {
		for (int[] transformation : transformations) {
			// base case
			if (Arrays.equals(current, transformation)) {
				return;
			}
		}
		transformations[transformationsCounter] = current;
		transformationsCounter++;
		for (String spec : specs) {
			c.load(current);
			c.transform(rowLength, spec);
			recursiveTransformations(c, c.getTemp(), rowLength);
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