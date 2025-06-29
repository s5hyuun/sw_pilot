package unit06;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class RunBiodome03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FruitStore store = new FruitStore("unit06/fruit_data.csv");

        System.out.println("과일 상점에 오신 것을 환영합니다!");

        while (true) {
            System.out.println("\n1. 과일 판매하기");
            System.out.println("2. 과일 재고 추가하기");
            System.out.println("3. 모든 과일 재고 조회하기");
            System.out.println("4. 종료하기");
            System.out.print("메뉴를 선택하세요: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("판매할 과일의 이름을 입력하세요: ");
                    String fruitToSell = scanner.nextLine().trim();
                    System.out.print("판매할 수량을 입력하세요: ");
                    int sellQty = parseIntegerInput(scanner.nextLine());
                    if (sellQty < 0) {
                        System.out.println("판매 수량은 음수가 될 수 없습니다.");
                        break;
                    }
                    store.sellFruit(fruitToSell, sellQty);
                    break;

                case "2":
                    System.out.print("재고를 추가할 과일의 이름을 입력하세요: ");
                    String fruitToAdd = scanner.nextLine().trim();
                    System.out.print("추가할 수량을 입력하세요: ");
                    int addQty = parseIntegerInput(scanner.nextLine());
                    if (addQty < 0) {
                        System.out.println("추가 수량은 음수가 될 수 없습니다.");
                        break;
                    }
                    store.addStock(fruitToAdd, addQty);
                    break;

                case "3":
                    System.out.println("\n모든 과일 재고 조회:");
                    store.displayAllStock();
                    break;

                case "4":
                    store.saveData();
                    System.out.println("\n모든 데이터가 저장되었습니다. 과일 상점 관리 시스템을 종료합니다!");
                    return;

                default:
                    System.out.println("올바른 메뉴 번호를 입력해주세요.");
            }
        }
    }

    private static int parseIntegerInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; // 음수는 오류 처리에 사용됨
        }
    }
}

class FruitStore {
    private final Path filePath;
    private final HashMap<String, Integer> stock = new HashMap<>();
    private final HashMap<String, String> salesHistory = new HashMap<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public FruitStore(String fileName) {
        this.filePath = Paths.get(fileName);
        loadData();
    }

    public void loadData() {
        try {
            Files.lines(filePath).forEach(line -> {
                try {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String name = parts[0].trim();
                        int qty = Integer.parseInt(parts[1].trim());
                        stock.put(name, qty);
                        if (parts.length >= 3) {
                            salesHistory.put(name, parts[2].trim());
                        } else {
                            salesHistory.put(name, "");
                        }
                    } else {
                        System.out.println("무시된 잘못된 줄: " + line);
                    }
                } catch (Exception e) {
                    System.out.println("줄 처리 중 오류 발생: " + line + " / " + e.getMessage());
                }
            });
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류 발생: " + e.getMessage());
        }
    }

    public void saveData() {
        List<String> lines = stock.entrySet().stream()
                .map(entry -> {
                    String name = entry.getKey();
                    int qty = entry.getValue();
                    String history = salesHistory.getOrDefault(name, "");
                    return name + "," + qty + "," + history;
                })
                .collect(Collectors.toList());

        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            System.out.println("파일을 저장하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void sellFruit(String name, int quantity) {
        if (!stock.containsKey(name)) {
            System.out.println("해당 과일은 재고 목록에 없습니다.");
            return;
        }
        int currentStock = stock.get(name);
        if (currentStock < quantity) {
            System.out.println("재고가 부족합니다. 현재 재고: " + currentStock + "개");
            return;
        }
        stock.put(name, currentStock - quantity);

        String history = salesHistory.getOrDefault(name, "");
        String time = LocalDateTime.now().format(formatter);
        history += time + " - " + quantity + "개 판매\n";
        salesHistory.put(name, history);

        System.out.println(name + " " + quantity + "개가 판매되었습니다!");
    }

    public void addStock(String name, int quantity) {
        if (!stock.containsKey(name)) {
            System.out.println("해당 과일은 재고 목록에 없습니다.");
            return;
        }
        int currentStock = stock.get(name);
        stock.put(name, currentStock + quantity);
        System.out.println(name + " 재고가 " + quantity + "개 추가되었습니다!");
    }

    public void displayAllStock() {
        if (stock.isEmpty()) {
            System.out.println("재고 데이터가 없습니다. 파일을 제대로 불러왔는지 확인하세요.");
            return;
        }

        stock.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                        System.out.println(entry.getKey() + "-" + entry.getValue() + "개")
                );
    }
}
