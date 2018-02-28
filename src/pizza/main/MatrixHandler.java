package pizza.main;

import java.util.List;

public class MatrixHandler {
	
	static char[][] writeToMatrix(List<String> lines) {
		int nbOfRow = lines.size() - 1;
		int nbOfCol = lines.get(1).length();
		char[][] matrix = new char[nbOfRow][nbOfCol]; 
		
		for (int row = 1; row < lines.size(); row++) {
			String rowContent = lines.get(row);
			for (int col = 0; col < rowContent.length(); col++) {
				matrix[row - 1][col] = rowContent.charAt(col);
			}
		}
		
		return matrix;
	}
	
	static void printMatrix(char[][] matrix) {
		int nbOfRow = matrix.length;
		int nbOfCol = matrix[0].length;
		for (int row = 0; row < nbOfRow; row++) {
			for (int col = 0; col < nbOfCol; col++) {
				System.out.print(matrix[row][col]);
			}
			System.out.print("\n");
		}
	}

}
