package self.drive.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		final String FILE_PATH_FROM = "D:\\Userfiles\\glin\\Downloads\\hashCode\\a_example.in";
		final String FILE_PATH_TO = "D:\\Userfiles\\glin\\Downloads\\hashCode\\a_example.out";

		List<String> lines = FileIOer.read(FILE_PATH_FROM);

		int nbOfRow = lines.size();
		int nbOfCol = lines.get(0).length() / 2;

		char[][] matrix = MatrixHandler.writeToMatrix(lines, nbOfRow, nbOfCol);

		Ride[] rides = MatrixHandler.generateRides(matrix);

		List<Ride> rideList = new ArrayList<Ride>(Arrays.asList(rides));

		sortRideList(rideList);

		int carNb = Character.getNumericValue(matrix[0][2]);

		Point[] cars = new Point[carNb];

		for (int i = 0; i < carNb; i++) {
			cars[i] = new Point(0, 0);
		}

		HashMap<Integer, ArrayList<Integer>> scheduledTrips = scheduleTrips(rides, carNb, cars);
		
		writeScheduledTrips(FILE_PATH_TO, scheduledTrips);

	}

	private static void writeScheduledTrips(final String FILE_PATH_TO, HashMap<Integer, ArrayList<Integer>> trips)
			throws IOException {
		List<String> toWrite = new ArrayList<String>();
		for (int i = 0; i < trips.size(); i++) {
			ArrayList<Integer> listOfRides = trips.get(i);
			int size = listOfRides.size();
			
			StringBuilder sb = new StringBuilder()
					.append(String.valueOf(size))
					.append(" ");
			
			for (int j = 0; j < listOfRides.size(); j++) {
				sb.append(listOfRides.get(j)).append(" ");
			}
			
			toWrite.add(sb.toString());
		}
		
		FileIOer.write(toWrite, FILE_PATH_TO);
	}

	private static HashMap<Integer, ArrayList<Integer>> scheduleTrips(Ride[] rides, int carNb, Point[] cars) {
		// (car, list of rides)
		HashMap<Integer, ArrayList<Integer>> output = new HashMap<>();

		for (int i = 0; i < rides.length; i++) {
			if (i < carNb) {
				ArrayList<Integer> arrayList = new ArrayList<>();
				arrayList.add(rides[i].rideNb);
				output.put(i, arrayList);
				// update car place
				cars[i] = rides[i].endP;
			} else {
				//
				int v = findClosestcar(cars, rides[i].startP);
				ArrayList<Integer> vrides = output.get(v);
				vrides.add(rides[i].rideNb);
			}
		}
		return output;
	}

	private static void sortRideList(List<Ride> rideList) {
		Collections.sort(rideList, new Comparator<Ride>() {
			@Override
			public int compare(Ride a, Ride b) {
				return Integer.compare(a.distanceToOrigin(), b.distanceToOrigin());
			}
		});
	}

	public static int findClosestcar(Point[] cars, Point point) {
		int min = Integer.MAX_VALUE;
		int res = -1;
		for (int i = 0; i < cars.length; i++) {
			int distance = cars[i].getDistance(point);
			if (distance < min) {
				min = distance;
				res = i;
			}
		}
		return res;
	}

}
