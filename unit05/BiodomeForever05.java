package unit05;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BiodomeForever05 {

    public static class EnvironmentData implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String timestamp;
        private final double temperature;
        private final double humidity;
        private final double oxygen;
        private final String location;

        public EnvironmentData(double temperature, double humidity, double oxygen, String location) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.timestamp = LocalDateTime.now().format(formatter);
            this.temperature = temperature;
            this.humidity = humidity;
            this.oxygen = oxygen;
            this.location = location;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public double getTemperature() {
            return temperature;
        }

        public double getHumidity() {
            return humidity;
        }

        public double getOxygen() {
            return oxygen;
        }

        public String getLocation() {
            return location;
        }

        @Override
        public String toString() {
            return String.format("%s, %.1f°C, %.1f%% 습도, %.1f%% 산소 농도, %s",
                    timestamp, temperature, humidity, oxygen, location);
        }
    }

    public static class EnvironmentAnalyzer {
        private static final double MU_B = 0.415;

        public static double calculateLifeIndex(EnvironmentData data) {
            double humidity = data.getHumidity();
            double temperature = data.getTemperature();
            double oxygen = data.getOxygen();
            double part1 = Math.abs(Math.sqrt(humidity - temperature));
            double part2 = oxygen / (Math.PI * Math.PI);
            return MU_B * part1 + part2;
        }
    }

    public static class EnvironmentManager {
        private final String filename = "environment_data.ser";
        private List<EnvironmentData> dataList = new ArrayList<>();

        public EnvironmentManager() {
            loadData();
        }

        public void addData(EnvironmentData data) {
            dataList.add(data);
            saveData();
            System.out.println("데이터가 성공적으로 저장되었습니다.");
        }

        public void viewAllData() {
            if (dataList.isEmpty()) {
                System.out.println("저장된 데이터가 없습니다.");
                return;
            }
            for (int i = 0; i < dataList.size(); i++) {
                System.out.println("[" + i + "] " + dataList.get(i));
            }
        }

        public void viewLifeIndices() {
            if (dataList.isEmpty()) {
                System.out.println("저장된 데이터가 없습니다.");
                return;
            }
            System.out.println("날짜별 생명지수:");
            for (EnvironmentData data : dataList) {
                double index = EnvironmentAnalyzer.calculateLifeIndex(data);
                System.out.printf("%s, %s: %.2f\n", data.getTimestamp(), data.getLocation(), index);
            }
        }

        public void calculateByIndex(int index) {
            if (index < 0 || index >= dataList.size()) {
                System.out.println("잘못된 인덱스 번호입니다.");
                return;
            }
            EnvironmentData data = dataList.get(index);
            double result = EnvironmentAnalyzer.calculateLifeIndex(data);
            System.out.printf("선택된 데이터: %s\n", data);
            System.out.printf("계산된 생명지수: %.2f\n", result);
        }

        @SuppressWarnings("unchecked")
        private void loadData() {
            File file = new File(filename);
            if (!file.exists()) return;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                dataList = (List<EnvironmentData>) ois.readObject();
            } catch (Exception e) {
                System.out.println("데이터를 불러오는 중 오류가 발생했습니다.");
            }
        }

        private void saveData() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(dataList);
            } catch (IOException e) {
                System.out.println("데이터 저장 중 오류가 발생했습니다.");
            }
        }
    }

    public static void main(String[] args) {
        EnvironmentManager manager = new EnvironmentManager();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("환경 정보 관리 프로그램에 오신 것을 환영합니다.");

        while (true) {
            try {
                System.out.println("\n1. 새로운 환경 데이터 입력");
                System.out.println("2. 모든 환경 데이터 조회");
                System.out.println("3. 날짜별 생명지수 조회");
                System.out.println("4. 프로그램 종료");
                System.out.print("선택: ");
                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        System.out.print("온도를 입력하세요: ");
                        double temp = Double.parseDouble(reader.readLine());

                        System.out.print("습도를 입력하세요: ");
                        double hum = Double.parseDouble(reader.readLine());

                        System.out.print("산소 농도를 입력하세요: ");
                        double oxy = Double.parseDouble(reader.readLine());

                        System.out.print("위치를 입력하세요: ");
                        String loc = reader.readLine();

                        EnvironmentData data = new EnvironmentData(temp, hum, oxy, loc);
                        manager.addData(data);
                        break;

                    case "2":
                        manager.viewAllData();
                        String idxInput = reader.readLine();
                        if (!idxInput.isEmpty()) {
                            try {
                                int index = Integer.parseInt(idxInput);
                                manager.calculateByIndex(index);
                            } catch (NumberFormatException e) {
                                System.out.println("숫자 인덱스만 입력 가능합니다.");
                            }
                        }
                        break;

                    case "3":
                        manager.viewLifeIndices();
                        break;

                    case "4":
                        System.out.println("프로그램을 종료합니다. 감사합니다.");
                        return;

                    default:
                        System.out.println("1~4 중에서 선택해주세요.");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("숫자 형식이 올바르지 않습니다.");
            } catch (IOException e){
                System.out.println("입력 처리 중 오류가 발생했습니다");
            }
        }
    }
}