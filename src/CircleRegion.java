public class CircleRegion implements Region {
    private int x, y, rad;
    Point center;
    public CircleRegion(int x, int y, int rad){
        this.x = x;
        this.y = y;
        this.rad = rad;
        center = new Point(y, x);
    }
    @Override
    public boolean contains(int x, int y) {
        Point other = new Point(y, x);
        if(getDistance(other, center) < rad){
            return true;
        }
        return false;
    }
    private double getDistance(Point nextPoint, Point previousPoint ) {
        double xDiff = Math.abs(nextPoint.getCol() - previousPoint.getCol());
        double yDiff = Math.abs(nextPoint.getRow() - previousPoint.getRow());
        double dist = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
        return dist;
    }
}
