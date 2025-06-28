package unit06;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@FunctionalInterface
interface EnergyAllocation {
    void allocate(String region, int amount);
}

class EnergyDistributor {
    private int totalEnergy = 50000;
    private final Map<String, Integer> regionEnergy;

    public EnergyDistributor() {
        regionEnergy = new HashMap<>();
        // 초기 구역들
        regionEnergy.put("테라노바", 0);
        regionEnergy.put("루미나베이", 0);
        regionEnergy.put("플로우브릿지", 0);
    }

    public int getTotalEnergy() {
        return totalEnergy;
    }

    public void allocateEnergy(String region, int amount, EnergyAllocation allocation) {
        allocation.allocate(region, amount);
    }

    public void printRegionEnergy() {
        System.out.println("→ 구역별 에너지 조회");
        for (Map.Entry<String, Integer> entry : regionEnergy.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public boolean regionExists(String region) {
        return regionEnergy.containsKey(region);
    }

    public void addRegionIfAbsent(String region) {
        regionEnergy.putIfAbsent(region, 0);
    }

    public int getRegionEnergy(String region) {
        return regionEnergy.getOrDefault(region, 0);
    }

    public void updateRegionEnergy(String region, int amount) {
        regionEnergy.put(region, amount);
    }

    public void decreaseTotalEnergy(int amount) {
        totalEnergy -= amount;
    }
}

public class RunBiodome02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EnergyDistributor distributor = new EnergyDistributor();

        EnergyAllocation allocation = (region, amount) -> {
            distributor.addRegionIfAbsent(region);
            int current = distributor.getRegionEnergy(region);
            distributor.updateRegionEnergy(region, current + amount);
            distributor.decreaseTotalEnergy(amount);
            System.out.println("→ " + region + "에 " + amount + "의 에너지가 할당되었습니다. 남은 전체 에너지: " + distributor.getTotalEnergy());
        };

        System.out.println("바이오도메 에너지 관리 시스템에 오신 것을 환영합니다.");

        while (true) {
            System.out.println("\n전체 에너지 조회하기");
            System.out.println("특정 구역 에너지 할당하기");
            System.out.println("구역별 에너지 조회하기");
            System.out.println("종료하기");
            System.out.print("메뉴를 선택하세요: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.println("→ 전체 남은 에너지: " + distributor.getTotalEnergy());
                    break;

                case "2":
                    System.out.print("할당하려는 구역 이름을 입력하세요: ");
                    String region = scanner.nextLine().trim();

                    System.out.print("할당하려는 에너지량을 입력하세요: ");
                    String energyStr = scanner.nextLine().trim();

                    int energyAmount;
                    try {
                        energyAmount = Integer.parseInt(energyStr);
                        if (energyAmount <= 0) {
                            System.out.println("→ 에너지량은 0보다 큰 숫자여야 합니다.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("→ 숫자 형식으로 에너지량을 입력하세요.");
                        break;
                    }

                    if (energyAmount > distributor.getTotalEnergy()) {
                        System.out.println("→ 할당 가능한 에너지를 초과했습니다.");
                        break;
                    }

                    distributor.allocateEnergy(region, energyAmount, allocation);
                    break;

                case "3":
                    distributor.printRegionEnergy();
                    break;

                case "4":
                    System.out.println("→ 바이오도메 에너지 관리 시스템을 종료합니다. 감사합니다.");
                    scanner.close();
                    return;

                default:
                    System.out.println("→ 1번에서 4번 메뉴를 선택해주세요.");
                    break;
            }
        }
    }
}
