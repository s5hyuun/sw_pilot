/*
package unit05;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class EnvironmentData {
    private String timestamp;
    private double temperature;
    private double humidity;
    private double oxygenLevel;
    private String location;

    public EnvironmentData(double temperature, double humidity, double oxygenLevel, String location) {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.temperature = temperature;
        this.humidity = humidity;
        this.oxygenLevel = oxygenLevel;
        this.location = location;
    }

    public String toFileString() {
        return String.format("%s,%.1f,%.1f,%.1f,%s", timestamp, temperature, humidity, oxygenLevel, location);
    }

    @Override
    public String toString() {
        return toFileString();
    }
}

class EnvironmentManager {
    private static final String FILE_NAME = "environment_data.txt";

    public void saveData(EnvironmentData data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(data.toFileString());
            writer.newLine();
            System.out.println("데이터가 environment_data.txt에 저장되었습니다.\n");
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void viewAllData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean hasData = false;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                hasData = true;
            }
            if (!hasData) {
                System.out.println("저장된 데이터가 없습니다.");
            }
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("데이터 파일이 존재하지 않습니다.");
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}

public class BiodomeForever04 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EnvironmentManager manager = new EnvironmentManager();

        System.out.println("환경 정보 관리 시스템에 오신 것을 환영합니다.\n");

        while (true) {
            System.out.println("1. 새로운 환경 데이터 입력");
            System.out.println("2. 모든 환경 데이터 조회");
            System.out.println("3. 프로그램 종료");
            System.out.print("선택: ");

            String choice = scanner.nextLine();
            System.out.println();

            switch (choice) {
                case "1":
                    try {
                        System.out.print("온도를 입력하세요: ");
                        double temp = Double.parseDouble(scanner.nextLine());

                        System.out.print("습도를 입력하세요: ");
                        double hum = Double.parseDouble(scanner.nextLine());

                        System.out.print("산소 농도를 입력하세요: ");
                        double oxy = Double.parseDouble(scanner.nextLine());

                        System.out.print("측정 장소를 입력하세요: ");
                        String location = scanner.nextLine();

                        EnvironmentData data = new EnvironmentData(temp, hum, oxy, location);
                        manager.saveData(data);
                    } catch (NumberFormatException e) {
                        System.out.println("잘못된 값입니다. 숫자로 입력해주세요.\n");
                    }
                    break;

                case "2":
                    manager.viewAllData();
                    break;

                case "3":
                    System.out.println("프로그램을 종료합니다. 감사합니다.");
                    return;

                default:
                    System.out.println("올바르지 않은 선택입니다. 1~3 중에서 선택해주세요.\n");
            }
        }
    }
}
*/