package unit02;

public class RoadToBiodome01 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("숫자를 입력해주세요.");
            return;
        }

        int[] waveNumbers = new int[args.length];
        int[] count = new int[1001];

        for (int i = 0; i < args.length; i++) {
            try {
                int num = Integer.parseInt(args[i]);
                if (num < 0 || num > 1000) {
                    System.out.println("입력된 값의 범위가 올바르지 않습니다. 0에서 1000까지의 값을 입력해주세요.");
                    return;
                }
                waveNumbers[i] = num;
                count[num]++;
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
                return;
            }
        }

        // ++
        int once = -1; // 한 번 등장한 숫자
        int k = -1;     // k 번 등장한 숫자의 반복 횟수
        boolean isBonusPattern = true; //

        for (int i = 0; i < count.length; i++) {
            if (count[i] == 0) continue;
            if (count[i] == 1) {
                if (once == -1) {
                    once = i;
                } else {
                    isBonusPattern = false;
                    break;
                }
            } else {
                if (k == -1) {
                    k = count[i];
                } else if (count[i] != k) {
                    isBonusPattern = false;
                    break;
                }
            }
        }

        if (isBonusPattern && once != -1) {
            System.out.println(once); // ++
            return;
        }

        // 한 번 등장한 숫자
        boolean found = false;
        for (int num : waveNumbers) {
            if (count[num] == 1) {
                System.out.println(num);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("한 번만 등장하는 숫자가 없습니다.");
        }
    }
}
