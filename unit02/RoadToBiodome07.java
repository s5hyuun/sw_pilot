package unit02;

import java.util.*;

public class RoadToBiodome07 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("입력된 동물 이름이 없습니다");
            return;
        }

        String joined = String.join(" ", args)
                .replace("[", "")
                .replace("]", "")
                .replace("\"", "")
                .replace(",", " ");

        String[] tokens = joined.split("\\s+");

        // 숫자 검사
        for (String token : tokens) {
            if (token.matches("\\d+")) {
                System.out.println("동물이 아닌 숫자 값이 존재합니다");
                return;
            }
        }

        List<String> uniqueList = new ArrayList<>();
        for (String name : tokens) {
            if (!uniqueList.contains(name)) {
                uniqueList.add(name);
            }
        }

        String[] animals = uniqueList.toArray(new String[0]);
        int[] frequencies = new int[animals.length];

        for (int i = 0; i < animals.length; i++) {
            for (String input : tokens) {
                if (input.equals(animals[i])) {
                    frequencies[i]++;
                }
            }
        }

        // 선택 정렬
        for (int i = 0; i < animals.length - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < animals.length; j++) {
                if (frequencies[j] > frequencies[maxIndex]) {
                    maxIndex = j;
                } else if (frequencies[j] == frequencies[maxIndex]) {
                    if (animals[j].compareTo(animals[maxIndex]) < 0) {
                        maxIndex = j;
                    }
                }
            }

            // swap
            int tempFreq = frequencies[i];
            frequencies[i] = frequencies[maxIndex];
            frequencies[maxIndex] = tempFreq;

            String tempName = animals[i];
            animals[i] = animals[maxIndex];
            animals[maxIndex] = tempName;
        }

        for (int i = 0; i < animals.length; i++) {
            System.out.print(animals[i]);
            if (i != animals.length - 1) System.out.print(", ");
        }
    }
}
