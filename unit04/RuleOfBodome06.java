package unit04;

import java.util.*;

public class RuleOfBodome06 {

    public static void main(String[] args) {
        if (args.length < 20) {
            System.out.println("20마리 이상의 동물 이름을 입력해 주세요.");
            return;
        }

        // 분석기 객체 생성 및 분석 실행
        AnimalFrequencyAnalyzer analyzer = new AnimalFrequencyAnalyzer();
        analyzer.analyze(args);

        // 가장 많이 등장한 동물 출력
        Set<String> mostFrequentAnimals = analyzer.getMostFrequentAnimals();
        System.out.print("가장 많이 발견된 동물 : ");
        System.out.println(String.join(", ", mostFrequentAnimals));

        Set<String> uniqueAnimals = analyzer.getUniqueAnimals();
        System.out.print("관찰된 모든 동물 : ");
        System.out.println(String.join(", ", uniqueAnimals));
    }
}


class AnimalFrequencyAnalyzer {
    private Map<String, Integer> animalCountMap = new HashMap<>();

    public void analyze(String[] animals) {
        for (String animal : animals) {
            animalCountMap.put(animal, animalCountMap.getOrDefault(animal, 0) + 1);
        }
    }

    // 가장 많이 등장한 동물들 반환
    public Set<String> getMostFrequentAnimals() {
        Set<String> result = new LinkedHashSet<>();
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : animalCountMap.entrySet()) {
            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
                result.clear();
                result.add(entry.getKey());
            } else if (count == maxCount) {
                result.add(entry.getKey());
            }
        }

        return result;
    }

    public Set<String> getUniqueAnimals() {
        return new TreeSet<>(animalCountMap.keySet()); // 정렬된 Set 반환
    }
}
