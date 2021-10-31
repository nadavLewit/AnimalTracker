import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSet {
    private int FPS;
    private double cmPerPixel;
    private ArrayList<Point> coordinates = new ArrayList<>();
    private ArrayList<Point> inROI = new ArrayList<>();
    private Region ROI;

    public DataSet(int FPS, double cmPerPixel, Region ROI){
        this.FPS = FPS;
        this.cmPerPixel = cmPerPixel;
        this.ROI = ROI;
    }


    public double getTotalDistanceTraveled(){
        double totalDist = 0;
        for (int i = 1; i < coordinates.size() -1; i++) {
            totalDist += getDistance(coordinates.get(i), coordinates.get(i + 1));
        }
        return getCMFrom(totalDist);
    }

    public Point getCoordinatesAtTime(double time){
        return coordinates.get(frameFor(time));
    }

    private int frameFor(double time) {
        return (int) (time *FPS);
    }

    public double getSpeedAtTIme(double time){
        int frame = frameFor(time);
        double distanceTraveled = getDistance(coordinates.get(frame+1), coordinates.get(frame-1));
        return getCMFrom(distanceTraveled)/ secondsFor(2);
    }

    private double secondsFor(int frames) {
        return frames/FPS;
    }

    private double getCMFrom(double distanceTraveled) {
        return distanceTraveled*cmPerPixel;
    }

    private double getDistance(Point nextPoint, Point previousPoint ) {
        double xDiff = Math.abs(nextPoint.getCol() - previousPoint.getCol());
        double yDiff = Math.abs(nextPoint.getRow() - previousPoint.getRow());
        double dist = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
        return dist;
    }

    public double percentOfTimeInROI(){
        return getTotalTimeInROI() / secondsFor(coordinates.size());
    }

    public double getTotalTimeInROI(){
        setROIPoint();
        return secondsFor(inROI.size());
    }

    public double getAverageSpeed(){
        return getTotalDistanceTraveled() / secondsFor(coordinates.size());
    }

    public double getAverageSpeedFrom(int startInSeconds, int endInSeconds){
        return getTotalDistanceTraveled(startInSeconds, endInSeconds) / (endInSeconds - startInSeconds);
    }

    public double getTotalDistanceTraveled(int startInSeconds, int endInSeconds){
        int startInFrames = frameFor(startInSeconds);
        int endInFrames = frameFor(endInSeconds);
        int totalDist = 0;
        for (int i = startInFrames; i < endInFrames - 1 ; i++) {
            totalDist += getDistance(coordinates.get(i), coordinates.get(i + 1));
        }
        return getCMFrom(totalDist);
    }
    public void add( Point p){
        coordinates.add(p);
    }
    public void setROIPoint() {
        for (int i = 0; i < coordinates.size(); i++) {
            if (ROI.contains(coordinates.get(i).getCol(), coordinates.get(i).getRow()))
                inROI.add(coordinates.get(i));
        }
    }


    public static void writeDataToFile(String filepath, String data) {
        try (FileWriter f = new FileWriter(filepath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);) {

            p.println("First line of the file");
            p.println(data);
            p.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " + filepath);
            error.printStackTrace();
        }
    }


    public void readFileAsString(String filepath) {
        try (Scanner scanner = new Scanner(new File(filepath))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                int row = Integer.parseInt(line.substring(0, line.indexOf(",")).trim());
                int col = Integer.parseInt(line.substring(line.indexOf(",") + 1).trim());
                coordinates.add(new Point(row, col));
            }

        } catch (IOException error) {
            error.printStackTrace();
        }

    }


    public void writeDataToFile(String filePath) {
        try (PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {

            if (coordinates != null) {
                for (Point center : coordinates) {
                    p.println(center.getCol() + " , " + center.getRow());
                }
            }
        } catch (IOException error) {
            System.err.println("There was a problem writing the file " + filePath);
            error.printStackTrace();
        }
    }

}
