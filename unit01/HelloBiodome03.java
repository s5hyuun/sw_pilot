package unit01;

public class HelloBiodome03 {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요");
            return;
        }

        double temperature, humidity, oxygen;

        try {
            // 문자열 > 실수형 변환
            temperature = Double.parseDouble(args[0]);
            humidity = Double.parseDouble(args[1]);
            oxygen = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요");
            return;
        }

        // 생명지수 계산
        double mB = 0.415;
        double hIndex = calculateH(mB, temperature, humidity, oxygen);

        System.out.printf("-> 생명지수 H = %.10f%n", hIndex);
    }

    // 생명지수 계산
    public static double calculateH(double mB, double temperature, double humidity, double oxygen) {
        double rootHumidity = sqrt(humidity); // 습도 제곱근
        double absDiff = abs(rootHumidity - temperature); // 절댓값 계산
        double piSquared = PI * PI; // ㅠ^2
        return mB * absDiff + (oxygen / piSquared);
    }

    // 제곱근 근사 계산
    public static double sqrt(double value) {
        double guess = value / 2;
        for (int i = 0; i < 20; i++) {
            guess = (guess + value / guess) / 2;
        }
        return guess;
    }

    // 절댓값 계산
    public static double abs(double value) {
        return (value < 0) ? -value : value;
    }

    public static final double PI = 3.14;
}
