package unit06;

public class RunBiodome06 {
    public static void main(String[] args) {
        final Object lock = new Object();

        WaterTank[] tanks = new WaterTank[5];

        for (int i = 0; i < 5; i++) {
            tanks[i] = new WaterTank(i + 1, lock);
        }

        for (int i = 0; i < 4; i++) {
            tanks[i].setNextTank(tanks[i + 1]);
        }

        for (int i = 0; i < 5; i++) {
            new Thread(tanks[i]).start();
        }

        synchronized (lock) {
            tanks[0].activate();
            lock.notifyAll();
        }
    }
}

class WaterTank implements Runnable {
    private final int tankNumber;
    private int currentVolume = 0;
    private static final int MAX_VOLUME = 100;
    private static final int FILL_RATE = 10;

    private final Object lock;
    private volatile boolean isActive = false;
    private WaterTank nextTank;

    public WaterTank(int tankNumber, Object lock) {
        this.tankNumber = tankNumber;
        this.lock = lock;
    }

    public void setNextTank(WaterTank nextTank) {
        this.nextTank = nextTank;
    }

    public void activate() {
        this.isActive = true;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                while (!isActive) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                while (currentVolume < MAX_VOLUME) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }

                    currentVolume += FILL_RATE;
                    System.out.println("물 저장소 " + tankNumber + ": " + currentVolume + "리터");
                }

                isActive = false;
                if (nextTank != null) {
                    nextTank.activate();
                    lock.notifyAll();
                } else {
                    System.out.println("물 채우기가 완료되었습니다.");
                }

                return;
            }
        }
    }
}
