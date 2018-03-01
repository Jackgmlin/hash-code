package self.drive.main;

class Ride{
    public Point startP;
    public Point endP;
    public int startTime;
    public int endTime;
    public int rideNb;

    public Ride (Point x1, Point x2, int x3, int x4, int x5){
        startP = x1;
        endP = x2;
        startTime = x3;
        endTime = x4;
        rideNb = x5;
    }
    
    public int getDistance(Point source, Point dest){
        return Math.abs(dest.x-source.x) + Math.abs(dest.y-source.y);
    }
    
    public int distanceToOrigin() {
    	return getDistance(new Point(0, 0), startP);
    }
}