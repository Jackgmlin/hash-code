package self.drive.main;

class Point{
    public int x;
    public int y;
    
    public Point() {
    	this.x = 0;
    	this.y = 0;
    }

    public Point (int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getDistance(Point point){
        return Math.abs(x-point.x) + Math.abs(y-point.y);
    }


}

