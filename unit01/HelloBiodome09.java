package unit01;

public class HelloBiodome09 {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("잘못된 입력입니다. 3~100 사이의 숫자를 입력하세요.");
            return;
        }

        int height = 0;
        char centerChar = '*';

        try {
            height = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력입니다. 3~100 사이의 숫자를 입력하세요.");
            return;
        }

        if (height < 3 || height > 100) {
            System.out.println("잘못된 입력입니다. 3~100 사이의 숫자를 입력하세요.");
            return;
        }

        // ++
        if (args.length >= 2 && args[1].length() == 1) {
            centerChar = args[1].charAt(0);
        }

        for (int i = 0; i < height; i++) {
            int spaces = height - i - 1;
            int stars = 1 + 2 * i;

            for (int j = 0; j < spaces; j++) {
                System.out.print(" ");
            }

            // ++
            for (int j = 0; j < stars; j++) {
                if (j == stars / 2) {
                    System.out.print(centerChar);
                } else {
                    System.out.print("*");
                }
            }

            System.out.println();
        }

        for (int j = 0; j < height - 1; j++) {
            System.out.print(" ");
        }
        System.out.println("|");
    }
}
