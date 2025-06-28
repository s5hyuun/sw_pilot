package unit05;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;

public class BiodomeForever06 {

    static class Researcher {
        private final String id;
        private final String name;
        private final String registrationDate;
        private final String location;

        public Researcher(String id, String name, String registrationDate, String location) {
            this.id = id;
            this.name = name;
            this.registrationDate = registrationDate;
            this.location = location;
        }

        public String getId() { return id; }

        @Override
        public String toString() {
            return String.format("%s, %s, %s, %s", id, name, registrationDate, location);
        }
    }

    static class ResearcherManager {
        private final String fileName = "researchers_data.txt";
        private final List<Researcher> researchers = new ArrayList<>();
        private final Map<String, Integer> yearRegistrationCount = new HashMap<>();

        public ResearcherManager() {
            loadData();
        }

        public void addResearcher(String name, String location) {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String year = date.substring(0, 4);

            int count = yearRegistrationCount.getOrDefault(year, 0) + 1;
            yearRegistrationCount.put(year, count);

            String order = String.format("%02d", count);
            int random = new Random().nextInt(900) + 100;
            String id = String.format("LUMI-%s-%s:%03d", year, order, random);

            Researcher researcher = new Researcher(id, name, date, location);
            researchers.add(researcher);

            if (saveToFile(researcher)) {
                System.out.println("연구원 정보가 성공적으로 등록되었습니다!");
                System.out.println("생성된 연구원 ID: " + id);
            } else {
                System.out.println("파일 저장 중 오류가 발생했습니다.");
            }
        }

        public void showAllResearchers() {
            if (researchers.isEmpty()) {
                System.out.println("저장된 연구원 정보가 없습니다");
                return;
            }

            System.out.println("\n전체 연구원 목록:\n====================================");
            for (Researcher r : researchers) {
                System.out.println(r);
            }
            System.out.println("====================================");
        }

        public void searchById(String id) {
            if (!isValidIdFormat(id)) {
                System.out.println("에러: ID 형식이 잘못되었습니다. (예: LUMI-2130-01:587)");
                return;
            }

            boolean found = false;
            for (Researcher r : researchers) {
                if (r.getId().equals(id)) {
                    System.out.println("\n연구원 정보:\n====================================");
                    System.out.println(r);
                    System.out.println("====================================");
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("에러: 해당 ID를 가진 연구원 정보가 존재하지 않습니다.");
            }
        }

        private boolean isValidIdFormat(String id) {
            String regex = "LUMI-\\d{4}-\\d{2}:\\d{3}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(id);
            return matcher.matches();
        }

        private boolean saveToFile(Researcher researcher) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(researcher.toString());
                writer.newLine();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        private void loadData() {
            File file = new File(fileName);
            if (!file.exists()) return;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(", ");
                    if (parts.length == 4) {
                        String id = parts[0];
                        String name = parts[1];
                        String date = parts[2];
                        String location = parts[3];
                        researchers.add(new Researcher(id, name, date, location));

                        String year = date.substring(0, 4);
                        int count = yearRegistrationCount.getOrDefault(year, 0) + 1;
                        yearRegistrationCount.put(year, count);
                    }
                }
            } catch (IOException e) {
                System.out.println("파일을 불러오는 중 오류가 발생했습니다.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ResearcherManager manager = new ResearcherManager();

        while (true) {
            System.out.println("\n====================================");
            System.out.println("연구원 정보 관리 시스템");
            System.out.println("1. 새로운 연구자 등록");
            System.out.println("2. 모든 연구자 조회");
            System.out.println("3. 조건 기반 연구자 검색");
            System.out.println("4. 프로그램 종료");
            System.out.println("====================================");
            System.out.print("선택하세요 (1-4): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("연구원의 이름을 입력하세요: ");
                    String name = scanner.nextLine();

                    System.out.print("연구원의 담당 위치를 입력하세요: ");
                    String location = scanner.nextLine();

                    manager.addResearcher(name, location);
                    break;

                case "2":
                    manager.showAllResearchers();
                    break;

                case "3":
                    System.out.print("검색할 연구원의 ID를 입력하세요: ");
                    String id = scanner.nextLine();
                    manager.searchById(id);
                    break;

                case "4":
                    System.out.println("프로그램을 종료합니다. 감사합니다");
                    return;

                default:
                    System.out.println("1~4 중에서 선택해주세요");
                    break;
            }
        }
    }
}
