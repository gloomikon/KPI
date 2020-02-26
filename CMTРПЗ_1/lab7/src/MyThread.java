import java.util.Random;

public class MyThread extends Thread {
    public void run() {

        final Random random = new Random();


        while (ThreadsHolder.getInstance().getPointCounter() < ThreadsHolder.getInstance().getAllPoints()) {

            double pointX = random.nextDouble();
            double pointY = random.nextDouble();
            if (Math.sqrt(Math.pow(pointX, 2) + Math.pow(pointY, 2)) <= 1) {
                ThreadsHolder.getInstance().addPiPoint();
            }
            ThreadsHolder.getInstance().addPointCounter();
        }
    }
}
