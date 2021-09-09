import java.util.Arrays;

public class QC {

	static String[] specs = new String[]{"TL","TR","BL","BR","LT","LB","RT","RB"};
	static CP c = new CP();
	static int[][] accessibleArray = new int[362880][11];
	static int accessibleCounter = 0;

	public static void main(String[] args) {
		int[] rowLengths = getFactors(11);
		for (int rowLength : rowLengths) {
			int[] original = new int[]{1,2,3,4,5,6,7,8,9,10,11};
			transformRecursive(original, rowLength);
		}
		System.out.println(accessibleCounter);
		accessibleArray = Arrays.copyOf(accessibleArray, accessibleCounter);
		for (int[] innerArr : accessibleArray) {
			System.out.println(Arrays.toString(innerArr));
		}
	}
	
	public static void transformRecursive(int[] pile, int rowLength) {
		for (String spec : specs) {
			c.load(pile);
			c.transform(rowLength, spec);
			int[] updatedPile = c.getTemp();
			// if the updated pile has not already been found to be accessible
			if (!inArray(accessibleArray, updatedPile)) {
				accessibleArray[accessibleCounter] = updatedPile;
				accessibleCounter++;
				transformRecursive(updatedPile, rowLength);
			}
		}
	}
	
	/*
	* Returns true if the second paramater array is in the array of arrays
	*/
	public static boolean inArray(int[][] arrayOfArrays, int[] arr) {
		for (int[] a : arrayOfArrays) {
			if (Arrays.equals(a, arr)) {
				return true;
			}
		}
		return false;
	}
	
	private static int[] getFactors(int n) {
		int[] factors = new int[5];
		int arrCounter = 0;
		for (int i = n; i > 0; i--) {
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