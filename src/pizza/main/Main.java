package pizza.main;

import java.io.IOException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		final String FILE_PATH = "D:\\Userfiles\\glin\\Downloads\\hashCode\\small.in";
		
		List<String> lines = FileReader.read(FILE_PATH);
		
		char[][] matrix = MatrixHandler.writeToMatrix(lines);
				
		MatrixHandler.printMatrix(matrix);
		
		// process the matrix as you want
	}
}
