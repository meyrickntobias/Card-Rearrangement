import java.util.Arrays;


public class SplitArray {
	public static void main(String[] args) {

		int[] arr = new int[]{1,2,3,4,5,6};
		int rowLength = 3;
		int noOfRows = 2;
		int[][] arrTwo = new int[noOfRows][rowLength];
		for (int i = 0; i < noOfRows; i++) {
			arrTwo[i] = Arrays.copyOfRange(arr, i * rowLength, i*rowLength+rowLength);
		}
		System.out.println(Arrays.deepToString(arrTwo));
		
		int[] temp = new int[arr.length];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				temp[j+(2*i)] = arrTwo[j][i];
			}
		}
		
		System.out.println(Arrays.toString(temp));
	}
}

/*
i = 0
arrTwo[0] = {1,2,3}
arrTwo[1] = {4,5,6}

temp[0]
temp[1]
temp[2]
temp[3]
temp[4]
temp[5]

*/