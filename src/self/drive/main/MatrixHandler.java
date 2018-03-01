package self.drive.main;

import java.util.List;

public class MatrixHandler {

	static char[][] writeToMatrix(List<String> lines, int nbOfRow, int nbOfCol) {
		char[][] matrix = new char[nbOfRow][nbOfCol];

		for (int row = 0; row < lines.size(); row++) {
			String rowContent = lines.get(row);
			for (int col = 0; col < rowContent.length(); col += 2) {
				matrix[row][col / 2] = rowContent.charAt(col);
			}
		}

		return matrix;
	}

	static Ride[] generateRides(char[][] matrix) {
		int nbOfRow = matrix.length;
		Ride[] rides = new Ride[nbOfRow - 1];
		for (int row = 1; row < nbOfRow; row++) {
			int col = 0;
			Point startP = new Point(Character.getNumericValue(matrix[row][col]), Character.getNumericValue(matrix[row][col + 1]));
			Point endP = new Point(Character.getNumericValue(matrix[row][col + 2]), Character.getNumericValue(matrix[row][col + 3]));
			int startTime = Character.getNumericValue(matrix[row][col + 4]);
			int endTime = Character.getNumericValue(matrix[row][col + 5]);
			int rideNb = row - 1;
			Ride ride = new Ride(startP, endP, startTime, endTime, rideNb);
			rides[row - 1] = ride;
		}
		
		return rides;
	}

}
