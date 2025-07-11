package unit06;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class RunBiodome05 {

    public static void main(String[] args) {
        System.out.println("센서 모니터링을 시작합니다...\n");

        Sensor temperatureSensor = new Sensor("온도", -5.0, 32.5, "°C");
        Sensor oxygenSensor = new Sensor("산소 농도", 18.5, 23.5, "%");

        temperatureSensor.start();
        oxygenSensor.start();
    }

    static class Sensor extends Thread {
        private final String type;        // 예: "온도", "산소 농도"
        private final double minThreshold;
        private final double maxThreshold;
        private final String unit;        // 단위 (°C, %)
        private final Random rand = new Random();

        public Sensor(String type, double minThreshold, double maxThreshold, String unit) {
            this.type = type;
            this.minThreshold = minThreshold;
            this.maxThreshold = maxThreshold;
            this.unit = unit;
        }

        private double generateData() {
            return minThreshold + (maxThreshold - minThreshold) * rand.nextDouble() * 1.5;
        }

        private String getCurrentTime() {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 HH시 mm분 ss초");
            return now.format(formatter);
        }

        @Override
        public void run() {
            int interval = 5000;

            while (true) {
                double value = generateData();
                String time = getCurrentTime();

                boolean isOutOfRange = (value < minThreshold || value > maxThreshold);

                StringBuilder output = new StringBuilder();
                output.append(time).append(" ").append(type).append(": ")
                        .append(String.format("%.1f", value)).append(unit);

                if (isOutOfRange) {
                    if (value < minThreshold) {
                        output.append(" [경고: ").append(type).append(" 하한 미달]");
                    } else {
                        output.append(" [경고: ").append(type).append(" 상한 초과]");
                    }
                    interval = 1000;
                } else {
                    interval = 5000;
                }

                System.out.println(output);

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    System.out.println(type + " 센서 오류: " + e.getMessage());
                    break;
                }
            }
        }
    }
}
