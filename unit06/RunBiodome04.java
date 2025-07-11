package unit06;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public class RunBiodome04 {
    static class FruitStore {
        private final Map<String, Integer> stockMap = new HashMap<>();
        private final Map<String, List<SaleRecord>> salesMap = new HashMap<>();

        static class SaleRecord {
            String date;
            int count;

            SaleRecord(String date, int count) {
                this.date = date;
                this.count = count;
            }
        }

        public void loadData(String filename) throws IOException {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            lines.stream().skip(1).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length < 2) return;

                String fruit = parts[0].trim();
                int stock = Integer.parseInt(parts[1].trim());

                List<SaleRecord> records = new ArrayList<>();
                if (parts.length >= 3 && !parts[2].trim().isEmpty()) {
                    String[] entries = parts[2].split("\\|");
                    for (String entry : entries) {
                        try {
                            String[] pair = entry.split(":");
                            if (pair.length == 2) {
                                String date = pair[0].trim();
                                int count = Integer.parseInt(pair[1].trim());
                                records.add(new SaleRecord(date, count));
                            }
                        } catch (Exception e) {
                            System.out.println("판매 기록 파싱 오류: " + entry);
                        }
                    }
                }

                stockMap.put(fruit, stock);
                salesMap.put(fruit, records);
            });
        }

        public void printAllStock() {
            System.out.println("\n모든 과일의 재고량:");
            stockMap.forEach((fruit, stock) ->
                    System.out.println(fruit + " - " + stock + "개"));
        }

        public void printMostSoldFruit() {
            Optional<Map.Entry<String, Integer>> result = salesMap.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue().stream().mapToInt(r -> r.count).sum()
                    ))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue());

            result.ifPresentOrElse(
                    e -> System.out.println("\n가장 많이 팔린 과일: " + e.getKey() + " - 총 " + e.getValue() + "개 판매됨"),
                    () -> System.out.println("판매 데이터가 없습니다.")
            );
        }

        public void printTotalSoldFruits() {
            int total = salesMap.values().stream()
                    .flatMap(List::stream)
                    .mapToInt(r -> r.count)
                    .sum();
            System.out.println("\n총 판매된 과일 수: " + total + "개");
        }

        public void printAverageSoldPerFruit() {
            System.out.println("\n과일별 평균 판매 개수:");
            salesMap.forEach((fruit, records) -> {
                double avg = records.stream().mapToInt(r -> r.count).average().orElse(0.0);
                System.out.printf("%s - %.1f개\n", fruit, avg);
            });
        }

        public void printSalesByFruit(String fruitName) {
            List<SaleRecord> records = salesMap.get(fruitName);

            if (records == null) {
                System.out.println("존재하지 않는 과일입니다.");
                return;
            }

            if (records.isEmpty()) {
                System.out.println(fruitName + "의 판매 기록이 없습니다.");
                return;
            }

            List<SaleRecord> sorted = records.stream()
                    .sorted(Comparator.comparing(r -> r.date))
                    .toList();

            System.out.println("\n" + fruitName + "의 날짜별 판매 이력:");
            sorted.forEach(r -> System.out.println(r.date + " - " + r.count + "개"));

            int total = sorted.stream().mapToInt(r -> r.count).sum();
            System.out.println("총 판매량: " + total + "개");
        }
    }

    public static void main(String[] args) {
        FruitStore store = new FruitStore();

        try {
            store.loadData("unit06/fruit_data_total.csv");
        } catch (IOException e) {
            System.out.println("파일 로드 중 오류: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n과일 상점에 오신 것을 환영합니다!");
            System.out.println("1. 모든 과일 재고 조회하기");
            System.out.println("2. 가장 많이 팔린 과일 조회하기");
            System.out.println("3. 총 판매 과일 수 조회하기");
            System.out.println("4. 과일별 평균 판매 개수 조회하기");
            System.out.println("5. 종료하기");
            System.out.println("6. 과일별 판매 이력 조회하기");
            System.out.print("메뉴를 선택하세요: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> store.printAllStock();
                    case 2 -> store.printMostSoldFruit();
                    case 3 -> store.printTotalSoldFruits();
                    case 4 -> store.printAverageSoldPerFruit();
                    case 5 -> {
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    }
                    case 6 -> {
                        System.out.print("과일 이름을 입력하세요: ");
                        String fruitName = scanner.nextLine().trim();
                        store.printSalesByFruit(fruitName);
                    }
                    default -> System.out.println("존재하지 않는 메뉴입니다. 1번에서 6번을 선택해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요. 1번에서 6번 중 하나를 선택해주세요.");
            }
        }
    }
}
