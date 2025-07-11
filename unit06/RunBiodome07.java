package unit06;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RunBiodome07 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        City terraNova = new City("TerraNova");
        City luminaBay = new City("LuminaBay");
        City flowBridges = new City("FlowBridges");

        Map<String, City> cityMap = new HashMap<>();
        cityMap.put("TerraNova", terraNova);
        cityMap.put("LuminaBay", luminaBay);
        cityMap.put("FlowBridges", flowBridges);

        while (true) {
            System.out.println("\n에너지 관리 시스템에 오신걸 환영합니다.");
            System.out.println("1. 중앙 에너지 센터와 3개 도시 에너지양 조회하기");
            System.out.println("2. 도시에 에너지 할당하기");
            System.out.println("3. 프로그램 종료하기");
            System.out.print("메뉴 선택: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    System.out.println("\n중앙 에너지 센터와 3개 도시 에너지양 조회");
                    System.out.println("중앙 에너지 센터: " + EnergyManageCenter.getInstance().getCurrentEnergy());
                    for (City city : cityMap.values()) {
                        city.printEnergyStatus();
                    }
                }
                case "2" -> {
                    System.out.print("\n도시 이름 입력: ");
                    String cityName = scanner.nextLine().trim();
                    if (!cityMap.containsKey(cityName)) {
                        System.out.println("해당 도시가 존재하지 않습니다.");
                        continue;
                    }

                    System.out.print("할당할 에너지양 입력: ");
                    try {
                        int amount = Integer.parseInt(scanner.nextLine());
                        cityMap.get(cityName).requestEnergy(amount);
                    } catch (NumberFormatException e) {
                        System.out.println("숫자로 입력해주세요.");
                    }
                }
                case "3" -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("올바른 메뉴 번호를 입력해주세요.");
            }
        }
    }
}

class EnergyManageCenter {
    private static EnergyManageCenter instance = null;
    private int currentEnergy = 5000;

    private EnergyManageCenter() {
    }

    public static EnergyManageCenter getInstance() {
        if (instance == null) {
            instance = new EnergyManageCenter();
        }
        return instance;
    }

    public synchronized boolean allocateEnergy(int amount) {
        if (amount < 0) {
            System.out.println("에너지 할당량은 음수일 수 없습니다.");
            return false;
        }
        if (amount > currentEnergy) {
            System.out.println("중앙 에너지 센터의 에너지가 부족합니다.");
            return false;
        }
        currentEnergy -= amount;
        return true;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }
}

class City {
    private final String name;
    private int energy;

    public City(String name) {
        this.name = name;
        this.energy = 0;
    }

    public void requestEnergy(int amount) {
        EnergyManageCenter center = EnergyManageCenter.getInstance();
        if (center.allocateEnergy(amount)) {
            energy += amount;
            System.out.println(name + ": " + amount + " 에너지 할당 받음");
            System.out.println("중앙 에너지 센터: " + center.getCurrentEnergy());
        }
    }

    public void printEnergyStatus() {
        System.out.println(name + ": " + energy);
    }
}
