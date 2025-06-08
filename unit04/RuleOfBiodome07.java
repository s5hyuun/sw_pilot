package unit04;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RuleOfBiodome07 {
    public static void main(String[] args) {
        // 식물 객체 생성
        System.out.println("(식물 객체 생성)");
        Plant p1 = new Plant("백합", "화초", 100, "2130-03-12 12:00", 15);
        Plant p2 = new Plant("나무딸기", "과일나무", 200, "2130-03-12 14:20", 20);
        Plant p3 = new Plant("선인장", "다육식물", 5, "2130-03-12 09:00", 30);
        Plant p4 = new Plant("라일락", "화초", 20, "2130-03-12 11:00", 25);
        Plant p5 = new Plant("대나무", "관목", 15, "2130-03-11 19:00", 50);

        System.out.println();

        // 식물 관리 시스템 생성 및 등록
        System.out.println("(식물 관리 시스템 생성 및 등록)");
        PlantManager manager = new PlantManager();
        manager.addPlant(p1);
        manager.addPlant(p2);
        manager.addPlant(p3);
        manager.addPlant(p4);
        manager.addPlant(p5);

        System.out.println();

        // 식물 관리 반복 3회
        for (int i = 1; i <= 3; i++) {
            System.out.println("(우선 관리 식물 출력 및 관리" + i + ")");
            manager.waterNextPlant();
            System.out.println();
        }

        // 남아있는 식물 출력
        System.out.println("(남아있는 식물)");
        manager.printRemainingPlants();
    }
}

// 식물 클래스
class Plant implements Comparable<Plant> {
    String name;
    String type;
    int waterAmount;
    LocalDateTime lastWatered;
    int wateringCycleHours;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Plant(String name, String type, int waterAmount, String lastWateredStr, int cycleHours) {
        this.name = name;
        this.type = type;
        this.waterAmount = waterAmount;
        try {
            this.lastWatered = LocalDateTime.parse(lastWateredStr, formatter);
        } catch (Exception e) {
            System.out.println("잘못된 날짜 형식입니다: " + lastWateredStr);
            this.lastWatered = LocalDateTime.now();
        }
        this.wateringCycleHours = cycleHours;
        System.out.println(name + ", " + waterAmount + ", 마지막 물 공급 일자: " + lastWateredStr + ", 물 공급 주기: " + cycleHours + "시간");
    }

    public LocalDateTime nextWateringTime() {
        return lastWatered.plusHours(wateringCycleHours);
    }

    public void water() {
        lastWatered = LocalDateTime.now();
        System.out.println(name + "에 물을 공급했습니다. 마지막 물 공급 일자 업데이트: " + lastWatered.format(formatter));
    }

    @Override
    public int compareTo(Plant other) {
        return this.nextWateringTime().compareTo(other.nextWateringTime());
    }

    public void printInfo() {
        System.out.println(name + ", " + waterAmount + ", 마지막 물 공급 일자: " + lastWatered.format(formatter) + ", 물 공급 주기: " + wateringCycleHours + "시간");
    }
}

// 우선순위 큐 구현
class PlantPriorityQueue {
    private List<Plant> heap = new ArrayList<>();

    public void offer(Plant plant) {
        heap.add(plant);
        siftUp(heap.size() - 1);
    }

    public Plant poll() {
        if (heap.isEmpty()) return null;
        Plant top = heap.get(0);
        Plant last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return top;
    }

    public Plant peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void remove(Plant plant) {
        heap.remove(plant);
        rebuild();
    }

    public void clear() {
        heap.clear();
    }

    public List<Plant> getAllPlants() {
        return new ArrayList<>(heap);
    }

    private void siftUp(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;
            if (heap.get(idx).compareTo(heap.get(parent)) < 0) {
                swap(idx, parent);
                idx = parent;
            } else break;
        }
    }

    private void siftDown(int idx) {
        int size = heap.size();
        while (idx < size) {
            int left = 2 * idx + 1;
            int right = 2 * idx + 2;
            int smallest = idx;

            if (left < size && heap.get(left).compareTo(heap.get(smallest)) < 0)
                smallest = left;
            if (right < size && heap.get(right).compareTo(heap.get(smallest)) < 0)
                smallest = right;

            if (smallest != idx) {
                swap(idx, smallest);
                idx = smallest;
            } else break;
        }
    }

    private void swap(int i, int j) {
        Plant temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private void rebuild() {
        List<Plant> old = new ArrayList<>(heap);
        clear();
        for (Plant p : old) offer(p);
    }
}

// 식물 관리 시스템
class PlantManager {
    private PlantPriorityQueue queue;

    public PlantManager() {
        queue = new PlantPriorityQueue();
        System.out.println("식물 관리 시스템이 생성되었습니다.");
    }

    public void addPlant(Plant plant) {
        queue.offer(plant);
        System.out.println(plant.name + "이(가) 관리 대상 목록에 추가되었습니다.");
    }

    public void waterNextPlant() {
        if (queue.isEmpty()) {
            System.out.println("관리할 식물이 없습니다.");
            return;
        }
        Plant next = queue.poll();
        System.out.println("우선 관리 대상: " + next.name + ", 필요한 물의 양: " + next.waterAmount);
        next.water();
    }

    public void printRemainingPlants() {
        List<Plant> remaining = queue.getAllPlants();
        for (Plant p : remaining) {
            p.printInfo();
        }
    }
}
