package unit06;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RunBiodome08 {

    public static void main(String[] args) {
        ElephantSensor sensor = new ElephantSensor();

        DetectorAnalyzer analyzer1 = new DetectorAnalyzer();
        CalculationAnalyzer analyzer2 = new CalculationAnalyzer();

        sensor.registerObserver(analyzer1);
        sensor.registerObserver(analyzer2);

        sensor.startMonitoring();
    }
}

class BioData {
    double temperature;
    int heartRate;

    public BioData(double temperature, int heartRate) {
        this.temperature = temperature;
        this.heartRate = heartRate;
    }
}

interface SensorSubject {
    void registerObserver(SensorObserver observer);
    void removeObserver(SensorObserver observer);
    void notifyObservers(BioData data);
}

interface SensorObserver {
    void update(BioData data);
}

class ElephantSensor implements SensorSubject {
    private final List<SensorObserver> observers = new ArrayList<>();
    private final Random random = new Random();

    @Override
    public void registerObserver(SensorObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(SensorObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(BioData data) {
        for (SensorObserver o : observers) {
            o.update(data);
        }
    }

    public void startMonitoring() {
        while (true) {
            double temperature = 34.0 + random.nextDouble() * 5;
            int heartRate = 20 + random.nextInt(25);

            BioData data = new BioData(temperature, heartRate);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 HH시 mm분 ss초"));
            System.out.printf("\n%s 체온 : %.1f°C, 심박수 : %dbpm\n", timestamp, temperature, heartRate);

            notifyObservers(data);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

class DetectorAnalyzer implements SensorObserver {
    private final List<String> alerts = new ArrayList<>();

    @Override
    public void update(BioData data) {
        boolean alertTriggered = false;

        if (data.temperature < 35.9 || data.temperature > 37.5) {
            alerts.add(String.format("[분석기 1] 임계치 이상 체온 : %.1f°C", data.temperature));
            alertTriggered = true;
        }

        if (data.heartRate < 25 || data.heartRate > 40) {
            alerts.add(String.format("[분석기 1] 임계치 이상 심박수 : %dbpm", data.heartRate));
            alertTriggered = true;
        }

        if (alertTriggered) {
            alerts.forEach(System.out::println);
            alerts.clear();
        }
    }
}

class CalculationAnalyzer implements SensorObserver {
    private final List<Double> temperatureList = new ArrayList<>();

    @Override
    public void update(BioData data) {
        temperatureList.add(data.temperature);

        DoubleSummaryStatistics stats = temperatureList.stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        System.out.printf("[분석기 2] 평균 체온 : %.1f°C\n", Math.round(stats.getAverage() * 10.0) / 10.0);
        System.out.printf("[분석기 2] 최대 체온 : %.1f°C\n", stats.getMax());
        System.out.printf("[분석기 2] 최소 체온 : %.1f°C\n", stats.getMin());
    }
}
