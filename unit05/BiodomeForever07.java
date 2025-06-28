package unit05;

import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.Scanner;

public class BiodomeForever07 {

    static class EnvironmentData {
        private final String dateTime;
        private final double oxygen;
        private final double humidity;
        private final double temperature;

        public EnvironmentData(String dateTime, double oxygen, double humidity, double temperature) {
            this.dateTime = dateTime;
            this.oxygen = oxygen;
            this.humidity = humidity;
            this.temperature = temperature;
        }

        @Override
        public String toString() {
            return String.format("%s,%.1f,%.1f,%.1f", dateTime, oxygen, humidity, temperature);
        }
    }

    static class EnvironmentFileSearcher {

        public EnvironmentData searchByDateTime(String targetDateTime) {
            String fileName = "unit05/environment_data_Lumino.txt";
            try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
                String line;
                while ((line = raf.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith(targetDateTime.trim())) {
                        String[] parts = line.split(",");
                        if (parts.length == 4) {
                            String dateTime = parts[0];
                            double oxygen = Double.parseDouble(parts[1]);
                            double humidity = Double.parseDouble(parts[2]);
                            double temperature = Double.parseDouble(parts[3]);
                            return new EnvironmentData(dateTime, oxygen, humidity, temperature);
                        }
                    }
                }
            } catch (IOException | NumberFormatException _) {
            }
            return null;
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EnvironmentFileSearcher searcher = new EnvironmentFileSearcher();

        System.out.println("환경 데이터 검색 프로그램을 시작합니다.");
        System.out.print("검색하고 싶은 날짜를 입력하세요: ");
        String inputDateTime = scanner.nextLine().trim();

        EnvironmentData result = searcher.searchByDateTime(inputDateTime);
        if (result != null) {
            System.out.println("→ 검색 결과: " + result);
        } else {
            System.out.println("→ 해당 날짜의 데이터는 존재하지 않습니다");
        }
    }
}
