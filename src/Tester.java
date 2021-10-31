import java.util.ArrayList;

public class Tester {
    public static void main(String[] args) {
        CircleRegion region = new CircleRegion(308, 233, 100);
        DataSet myData = new DataSet(25, 5.316, region);
        DataSet mrDData = new DataSet(25, 5.316, region);

        myData.readFileAsString("/Users/nadavl/IdeaProjects/AnimalTracker/src/LEWIT_NADAV.csv");
        mrDData.readFileAsString("/Users/nadavl/IdeaProjects/AnimalTracker/src/DOBERVICH_DAVID.csv");

        double myTotalDist = myData.getTotalDistanceTraveled(50, 65);
        double mrDTotalDist = mrDData.getTotalDistanceTraveled(50, 65);
        double myAvgSpeed = myData.getAverageSpeedFrom(30, 40);
        double mrDAvgSpeed = mrDData.getAverageSpeedFrom(30, 40);
        double myTotalTimeInROI = myData.getTotalTimeInROI();
        double mrDTotalTimeInROI = mrDData.getTotalTimeInROI();

        System.out.println("My results: Total distance from 0-180 - " + myTotalDist + ", average speed from 30-40 - " + myAvgSpeed + ", total time in ROI - " + myTotalTimeInROI);
        System.out.println("Mr D's results: Total distance from 0-180 - " + mrDTotalDist + ", average speed from 30-40 - " + mrDAvgSpeed + ", total time in ROI - " + mrDTotalTimeInROI);

    }
}


