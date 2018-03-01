package self.drive.main;

import java.util.List;

public class MatrixHandler {

	static int[][] writeToMatrix(List<String> lines, int nbOfRow, int nbOfCol) {
		int[][] matrix = new int[nbOfRow][nbOfCol];

		
		for (int row = 1; row < nbOfRow + 1; row++) {
			String rowContent = lines.get(row);
			String[] a = rowContent.split(" ");
			for (int col = 0; col < a.length; col++) {
				matrix[row - 1][col] = Integer.valueOf(a[col]);
			}
		}

		return matrix;
	}

	static Ride[] generateRides(int[][] matrix) {
		int nbOfRow = matrix.length;
		Ride[] rides = new Ride[nbOfRow];
		for (int row = 0; row < nbOfRow; row++) {
			int col = 0;
			Point startP = new Point((matrix[row][col]), (matrix[row][col + 1]));
			Point endP = new Point((matrix[row][col + 2]), (matrix[row][col + 3]));
			int startTime = (matrix[row][col + 4]);
			int endTime = (matrix[row][col + 5]);
			int rideNb = row;
			Ride ride = new Ride(startP, endP, startTime, endTime, rideNb);
			rides[row] = ride;
		}
		
		return rides;
	}

}
