package unit01;

public class HelloBiodome05 {

    public static void main(String[] args) {
        int[] result = findValidGandH();
        int g = result[0];
        int h = result[1];

        System.out.println("찾은 값: g = " + g + ", h = " + h);

        int finalResult = evaluateFinalExpression(g, h);
        System.out.println("최종 수식 결과: " + finalResult);
    }

    public static int[] findValidGandH() {
        for (int g = 0; g < 16; g++) {
            for (int h = 0; h < 16; h++) {
                // 첫 번째 수식
                boolean expr1 = (g & 1 >> g << 2 | h + g ^ h) == 1;

                // 두 번째 수식
                boolean expr2 = (g % 2 << h >> g | 1 & 0 ^ 0) == 2;

                if (expr1 && expr2) {
                    return new int[]{g, h};
                }
            }
        }
        return new int[]{-1, -1};
    }
    public static int evaluateFinalExpression(int g, int h) {
        int part1 = h * h + g;
        int part2 = (h << h);
        int part3 = (g << g);

        return part1 * part2 + part3;
    }
}
