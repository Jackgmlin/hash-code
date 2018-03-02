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
		final String FILE_PATH_FROM = "D:\\Userfiles\\glin\\Downloads\\hashCode\\d.in";
		final String FILE_PATH_TO = "D:\\Userfiles\\glin\\Downloads\\hashCode\\d.out";

		List<String> lines = FileIOer.read(FILE_PATH_FROM);

		// int nbOfRow = Integer.valueOf(lines.get(0).split(" ")[0]);
		int nbOfRow = lines.size() - 1;
		int nbOfCol = Integer.valueOf(lines.get(0).split(" ").length);

		// int nbOfRow = lines.size();
		// int nbOfCol = lines.get(0).length() / 2;

		int[][] matrix = MatrixHandler.writeToMatrix(lines, nbOfRow, nbOfCol);

		Ride[] rides = MatrixHandler.generateRides(matrix);

		List<Ride> rideList = new ArrayList<Ride>(Arrays.asList(rides));

		sortRideList(rideList);

		int carNb = Integer.valueOf(lines.get(0).split(" ")[2]);
		int bonus = Integer.valueOf(lines.get(0).split(" ")[4]);

		Car[] cars = new Car[carNb];

		for (int i = 0; i < carNb; i++) {
			cars[i] = new Car(new Point(0, 0), 0);
		}

		HashMap<Integer, ArrayList<Integer>> scheduledTrips = scheduleTrips(rides, carNb, cars, bonus);

		writeScheduledTrips(FILE_PATH_TO, scheduledTrips);

	}

	private static void writeScheduledTrips(final String FILE_PATH_TO, HashMap<Integer, ArrayList<Integer>> trips)
			throws IOException {
		List<String> toWrite = new ArrayList<String>();
		for (int i = 0; i < trips.size(); i++) {
			ArrayList<Integer> listOfRides = trips.get(i);
			int size = listOfRides.size();

			StringBuilder sb = new StringBuilder().append(String.valueOf(size)).append(" ");

			for (int j = 0; j < listOfRides.size(); j++) {
				sb.append(listOfRides.get(j)).append(" ");
			}

			toWrite.add(sb.toString());
		}

		FileIOer.write(toWrite, FILE_PATH_TO);
	}

	private static HashMap<Integer, ArrayList<Integer>> scheduleTrips(Ride[] rides, int carNb, Car[] cars, int bonus) {
		// (car, list of rides)
		HashMap<Integer, ArrayList<Integer>> output = new HashMap<>();

		int notAble = 0;
		
		for (int i = 0; i < rides.length; i++) {
			Ride ride = rides[i];

			if (couldArriveInTime(cars, ride)) {
				int carIndex;
				if (output.size() < carNb) {
					ArrayList<Integer> arrayList = new ArrayList<>();
					arrayList.add(ride.rideNb);
					carIndex = i - notAble;
					output.put(carIndex, arrayList);
				} else {
					// find the car could arrive in advance or in time(bonus =
					// 0)
					carIndex = findInTimeWithBonusCar(cars, ride, bonus);
					// if not find
					if (carIndex == -1) {
						carIndex = findNearestAvailable(cars, ride);
						if (carIndex == -1)
							carIndex = findFirstAvailable(cars, ride);
					}
					ArrayList<Integer> vrides = output.get(carIndex);
					vrides.add(ride.rideNb);
				}

				// update car place and validFrom time
				cars[carIndex].validFrom = Math.max(ride.startTime, cars[carIndex].point.getDistance(ride.startP))
						+ ride.startP.getDistance(ride.endP);
				cars[carIndex].point = ride.endP;
			} else {
				ArrayList<Integer> vrides = output.get(0);
				vrides.add(ride.rideNb);
				notAble++;
			}
		}
		return output;
	}

	public static boolean couldArriveInTime(Car[] cars, Ride ride) {
		for (int i = 0; i < cars.length; i++) {
			Car car = cars[i];
			if (car.validFrom + car.point.getDistance(ride.startP) <= ride.endTime) {
				return true;
			}
		}
		return false;
	}

	public static int findFirstAvailable(Car[] cars, Ride ride) {
		int res = -1;
		int min = Integer.MAX_VALUE;

		for (int i = 0; i < cars.length; i++) {
			Car car = cars[i];
			int time = car.validFrom + car.point.getDistance(ride.startP);
			if (time < min) {
				res = i;
				min = time;
			}
		}
		return res;
	}

	// return -1 if not found, arrive in advance bonus, or in time
	public static int findInTimeWithBonusCar(Car[] cars, Ride ride, int bonus) {
		int res = -1;
		for (int i = 0; i < cars.length; i++) {
			Car car = cars[i];
			if (car.validFrom + car.point.getDistance(ride.startP) <= ride.startTime
					&& car.validFrom + car.point.getDistance(ride.startP) + bonus >= ride.startTime) {
				res = i;
			}
		}
		return res;
	}


	private static void sortRideList(List<Ride> rideList) {
		Collections.sort(rideList, new Comparator<Ride>() {
			@Override
			public int compare(Ride a, Ride b) {
				int endTime = Integer.compare(a.distanceToOrigin(), b.distanceToOrigin());
				if (endTime == 0) {
					return Integer.compare(a.endTime - a.distanceToOrigin(), b.endTime - b.distanceToOrigin());
				} else {
					return endTime;
				}

			}
		});
	}

	public static int findClosestcar(Car[] cars, Point point) {
		int min = Integer.MAX_VALUE;
		int res = -1;
		for (int i = 0; i < cars.length; i++) {
			int distance = cars[i].point.getDistance(point);
			if (distance < min) {
				min = distance;
				res = i;
			}
		}
		return res;
	}
	
	public static int findNearestAvailable(Car[] cars, Ride ride){
        int res = -1;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < cars.length; i++){
            Car car = cars[i];
            int distance = car.point.getDistance(ride.startP);
            if( car.validFrom + distance + ride.startP.getDistance(ride.endP) < ride.endTime){
                if (distance < min){
                    res = i;
                    min = distance;
                }
            }
        }
        return res;
    }

}
