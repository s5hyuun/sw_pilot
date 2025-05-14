package unit02;

public class RoadToBiodome04 {

    // 선택 정렬 함수
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // 평균값 계산
    public static double calculateAverage(int[] arr) {
        double sum = 0;
        for (int value : arr) {
            sum += value;
        }
        return sum / arr.length;
    }

    // 중앙값 계산
    public static double calculateMedian(int[] arr) {
        int n = arr.length;
        if (n % 2 == 1) {
            return arr[n / 2];
        } else {
            return (arr[n / 2 - 1] + arr[n / 2]) / 2.0;
        }
    }

    public static int[] parseToIntArray(String[] input) {
        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = Integer.parseInt(input[i].trim());
        }
        return result;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("에너지 소비 값을 입력하세요.");
            return;
        }

        String input = args[0];
        String[] groups = input.split(";");

        if (groups.length == 1) {
            try {
                int[] data = parseToIntArray(groups[0].split(","));
                selectionSort(data);
                double average = calculateAverage(data);
                double median = calculateMedian(data);
                System.out.println("평균값 : " + average + ", 중앙값 : " + median);
            } catch (Exception e) {
                System.out.println("입력 오류가 발생했습니다.");
            }
        } else {
            // ++
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < groups.length; i++) {
                try {
                    int[] regionData = parseToIntArray(groups[i].split(","));
                    selectionSort(regionData);
                    double median = calculateMedian(regionData);
                    result.append(median);
                    if (i != groups.length - 1) {
                        result.append(", ");
                    }
                } catch (Exception e) {
                    System.out.println("지역 " + (i + 1) + " 데이터 입력 오류.");
                    return;
                }
            }
            System.out.println(result.toString());
        }
    }
}
