package unit01;

public class HelloBiodome04 {
    public static void main(String[] args){
        if (args.length != 3) {
            System.out.println("입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요");
            return;
        }

        double temperature, humidity, oxygen;

        try {
            temperature = Double.parseDouble(args[0]);
            humidity = Double.parseDouble(args[1]);
            oxygen = Double.parseDouble(args[2]);
        } catch (NumberFormatException e){
            System.out.println("입력된 값이 올바르지 않습니다. [온도][습도][산소농도] 순서대로 숫자 값을 입력해주세요");
            return;
        }
        boolean isTemperatureSafe = isTemperatureInRange(temperature);
        boolean isHumiditySafe = isHumidityInRange(humidity);
        boolean isOxygenSafe = isOxygenInRange(oxygen);

        if (isTemperatureSafe && isHumiditySafe && isOxygenSafe){
            System.out.println("-> 생명의 나무는 안정적인 상태입니다 :)");

        } else {
            if (!isTemperatureSafe) {
                System.out.println("-> 온도값이 정상 범위를 벗어났습니다. 확인이 필요합니다");
            } else if (!isHumiditySafe) {
                System.out.println("-> 습도값이 정상 범위를 벗어났습니다. 확인이 필요합니다");
            } else if (!isOxygenSafe) {
                System.out.println("-> 산소 농도값이 정상 범위를 벗어났습니다. 확인이 필요합니다");
            }
        }
    }
    public static boolean isTemperatureInRange(double temp){
        return temp >= 10.0 && temp < 27.5;
    }
    public static boolean isHumidityInRange(double humidity){
        return humidity > 40.0 && humidity < 60.0;
    }
    public static boolean isOxygenInRange(double oxygen){
        return oxygen >= 19.5 && oxygen <= 23.5;
    }
}
