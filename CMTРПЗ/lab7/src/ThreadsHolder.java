import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadsHolder {
    static final ThreadsHolder instance = new ThreadsHolder();
    private AtomicInteger allPoints;
    private AtomicInteger piPoints;
    private AtomicInteger pointCounter; //iterations

    private ThreadsHolder() {
        allPoints = new AtomicInteger(10000000);
        piPoints = new AtomicInteger(0);
        pointCounter = new AtomicInteger(0);

    };

    public static ThreadsHolder getInstance() {
        return instance;
    };

    public int getAllPoints() {
        return allPoints.intValue();
    }

    public int getPointCounter() {
        return pointCounter.intValue();
    }


    public void addPointCounter() {
        pointCounter.incrementAndGet();
    }

    public void addPiPoint() {
        piPoints.incrementAndGet();
    }

    public String calculate(int threads) {
        //TimerStart
        long startTime = System.currentTimeMillis();

        //Actions
        ArrayList <MyThread> list = new ArrayList<MyThread>();

        for (int i = 0; i < threads; i++) {
            MyThread mt = new MyThread();
            list.add(mt);
            mt.start();
        }

        for (MyThread mt : list) {
            try {
                mt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double result = piPoints.doubleValue() / pointCounter.doubleValue() * 4;

        //TimerEnd
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;

        String output = "PI is " + result + "\nTHREADS " + threads + "\nITERATIONS " + pointCounter + "\nTIME " + time + "ms";
        return output;
    }
}
