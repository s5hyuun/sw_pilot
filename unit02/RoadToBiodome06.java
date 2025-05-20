package unit02;

import java.util.*;

public class RoadToBiodome06 {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("입력값이 없습니다");
                return;
            }

            List<Integer> list1 = new ArrayList<>();
            List<Integer> list2 = new ArrayList<>();

            boolean parsingFirst = true;
            for (String arg : args) {
                arg = arg.replaceAll("[\\[\\],]", "");
                if (arg.isEmpty()) continue;

                int num = Integer.parseInt(arg);
                if (num < 0) throw new IllegalArgumentException("물 높이는 0 이상이어야 합니다");

                if (parsingFirst) {
                    list1.add(num);
                } else {
                    list2.add(num);
                }

                if (arg.endsWith("]")) parsingFirst = false;
            }

            if (list2.isEmpty() && list1.size() >= 2) {
                int mid = list1.size() / 2;
                list2.addAll(list1.subList(mid, list1.size()));
                list1 = list1.subList(0, mid);
            }

            if (list1.isEmpty() || list2.isEmpty()) {
                System.out.println("두 배열 모두에 물 높이 값이 포함되어야 합니다");
                return;
            }

            int[] A = list1.stream().mapToInt(i -> i).toArray();
            int[] B = list2.stream().mapToInt(i -> i).toArray();

            Arrays.sort(A);
            Arrays.sort(B);

            double mean = computeMean(A, B);
            double median = findMedianSortedArrays(A, B);

            System.out.printf("Mean : %.1f, Median : %.1f\n", mean, median);

            // ++
            List<Integer> filtered = new ArrayList<>();
            for (int val : A) if (val >= 30) filtered.add(val);
            for (int val : B) if (val >= 30) filtered.add(val);

            if (filtered.isEmpty()) {
                System.out.println("보너스 결과: 물 높이 30 이상인 데이터가 없습니다");
            } else {
                int[] filteredArr = filtered.stream().mapToInt(i -> i).sorted().toArray();
                double bonusMean = computeMean(filteredArr, new int[0]); // 하나의 배열만 사용
                double bonusMedian = findMedianSortedArrays(filteredArr, new int[0]);
                System.out.printf("++ → Mean : %.1f, Median : %.1f\n", bonusMean, bonusMedian);
            }

        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력해야 합니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("입력 오류: " + e.getMessage());
        }
    }

    public static double computeMean(int[] A, int[] B) {
        double sum = 0;
        for (int val : A) sum += val;
        for (int val : B) sum += val;
        return Math.round((sum / (A.length + B.length)) * 10) / 10.0;
    }

    public static double findMedianSortedArrays(int[] A, int[] B) {
        if (A.length > B.length) return findMedianSortedArrays(B, A);

        int m = A.length, n = B.length;
        int total = m + n;
        int half = (total + 1) / 2;

        int l = 0, r = m;
        while (l <= r) {
            int i = (l + r) / 2;
            int j = half - i;

            int A_left = (i == 0) ? Integer.MIN_VALUE : A[i - 1];
            int A_right = (i == m) ? Integer.MAX_VALUE : A[i];
            int B_left = (j == 0) ? Integer.MIN_VALUE : B[j - 1];
            int B_right = (j == n) ? Integer.MAX_VALUE : B[j];

            if (A_left <= B_right && B_left <= A_right) {
                double maxLeft = Math.max(A_left, B_left);
                double minRight = Math.min(A_right, B_right);
                if (total % 2 == 0) {
                    return Math.round(((maxLeft + minRight) / 2.0) * 10) / 10.0;
                } else {
                    return Math.round((maxLeft) * 10) / 10.0;
                }
            } else if (A_left > B_right) {
                r = i - 1;
            } else {
                l = i + 1;
            }
        }

        throw new IllegalArgumentException("중앙값 계산 중 오류 발생");
    }
}
