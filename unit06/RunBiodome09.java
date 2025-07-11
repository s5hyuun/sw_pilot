package unit06;

import java.util.Scanner;

public class RunBiodome09 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] cityNames = {"테라노바", "루미나베이", "플로우브릿지", "루미노엣지"};
        int[] cityWaterHoldings = new int[4];

        while (true) {
            System.out.println("\n수자원 관리 시스템에 오신걸 환영합니다.");
            System.out.println("1. 중앙 수자원 센터와 4개 도시 보유 물양 조회하기");
            System.out.println("2. 도시에 물 할당하기");
            System.out.println("3. 프로그램 종료하기");
            System.out.print("메뉴를 선택하세요: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    System.out.println("\n중앙 수자원 센터의 현재 물양: " +
                            CentralWaterCenter.getInstance().getCurrentWater());
                    for (int i = 0; i < cityNames.length; i++) {
                        System.out.println(cityNames[i] + "의 현재 보유 물양: " + cityWaterHoldings[i]);
                    }
                }
                case "2" -> {
                    int[] requestedAmounts = new int[4];

                    for (int i = 0; i < cityNames.length; i++) {
                        while (true) {
                            System.out.print(cityNames[i] + "에 필요한 물의 양을 입력하세요: ");
                            try {
                                int amount = Integer.parseInt(scanner.nextLine());
                                if (amount < 0) {
                                    System.out.println("마이너스값이 입력되었습니다. 다시 한번 확인해주세요.");
                                } else {
                                    requestedAmounts[i] = amount;
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("정수를 입력해주세요.");
                            }
                        }
                    }

                    System.out.println("=====물 분배 시작======");

                    WaterRequestThread[] threads = new WaterRequestThread[4];
                    for (int i = 0; i < 4; i++) {
                        threads[i] = new WaterRequestThread(cityNames[i], requestedAmounts[i], cityWaterHoldings, i);
                        threads[i].start();
                    }

                    for (int i = 0; i < 4; i++) {
                        try {
                            threads[i].join();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    System.out.println("최종 남은 물의 양: " +
                            CentralWaterCenter.getInstance().getCurrentWater());
                }
                case "3" -> {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("유효하지 않은 입력입니다. 1~3 중에서 선택해주세요.");
            }
        }
    }
}

class CentralWaterCenter {
    private static volatile CentralWaterCenter instance = null;
    private int currentWater;

    private CentralWaterCenter() {
        currentWater = 10000;
    }

    public static CentralWaterCenter getInstance() {
        if (instance == null) {
            synchronized (CentralWaterCenter.class) {
                if (instance == null) {
                    instance = new CentralWaterCenter();
                }
            }
        }
        return instance;
    }

    public synchronized boolean allocateWater(String cityName, int amount) {
        if (amount <= currentWater) {
            currentWater -= amount;
            System.out.println(cityName + "에 " + amount + "만큼의 물을 할당하였습니다. 남은 물의 양 : " + currentWater);
            return true;
        } else {
            System.out.println(cityName + " 요청 실패 - 물이 부족합니다. 요청량: " + amount + ", 남은 물: " + currentWater);
            return false;
        }
    }

    public synchronized int getCurrentWater() {
        return currentWater;
    }
}

class WaterRequestThread extends Thread {
    private final String cityName;
    private final int requestAmount;
    private final int[] cityHoldings;
    private final int index;

    public WaterRequestThread(String cityName, int requestAmount, int[] cityHoldings, int index) {
        this.cityName = cityName;
        this.requestAmount = requestAmount;
        this.cityHoldings = cityHoldings;
        this.index = index;
    }

    @Override
    public void run() {
        CentralWaterCenter center = CentralWaterCenter.getInstance();
        boolean success = center.allocateWater(cityName, requestAmount);
        if (success) {
            cityHoldings[index] += requestAmount;
        }
    }
}
