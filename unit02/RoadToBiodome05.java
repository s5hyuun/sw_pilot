package unit02;

import java.util.ArrayList;

public class RoadToBiodome05 {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("최소 하나 이상의 숫자 리스트를 입력해야 합니다");
                return;
            }

            int[] heights = parseAndValidateInput(args);

            int[] heightsForQuickSort = heights.clone();
            int[] heightsForBubbleSort = heights.clone(); // ++

            long quickStart = System.nanoTime();
            quickSort(heightsForQuickSort, 0, heightsForQuickSort.length - 1);
            long quickEnd = System.nanoTime();

            // ++
            long bubbleStart = System.nanoTime();
            bubbleSort(heightsForBubbleSort);
            long bubbleEnd = System.nanoTime();

            System.out.print("퀵 정렬 결과 : [");
            for (int i = 0; i < heightsForQuickSort.length; i++) {
                System.out.print(heightsForQuickSort[i]);
                if (i < heightsForQuickSort.length - 1) System.out.print(",");
            }
            System.out.println("]");

            // ++
            System.out.print("버블 정렬 결과 : [");
            for (int i = 0; i < heightsForBubbleSort.length; i++) {
                System.out.print(heightsForBubbleSort[i]);
                if (i < heightsForBubbleSort.length - 1) System.out.print(",");
            }
            System.out.println("]");

            System.out.println("퀵 정렬 시간 : " + (quickEnd - quickStart));
            System.out.println("버블 정렬 시간 : " + (bubbleEnd - bubbleStart)); // ++

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int[] parseAndValidateInput(String[] args) throws NumberFormatException {
        ArrayList<Integer> list = new ArrayList<>();

        for (String arg : args) {
            arg = arg.replaceAll("\\[", "").replaceAll("]", "");

            String[] split = arg.split(",");
            for (String s : split) {
                s = s.trim();
                if (s.isEmpty()) continue;

                try {
                    int value = Integer.parseInt(s);
                    if (value < 0) {
                        throw new IllegalArgumentException("물 높이는 0 이상의 정수여야 합니다");
                    }
                    list.add(value);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("모든 입력은 정수여야 합니다");
                }
            }
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // ++ 버블정렬
    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }
}
