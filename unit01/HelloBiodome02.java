package unit01;

public class HelloBiodome02 {
    public static void main(String[] args) {
        if (args.length !=3) {
            System.out.println("에너지 생산량 세 개를 입력해주세요");
            return;
        }
        // 태양광, 풍력, 지열 > 형변환
        int photovoltaic;
        int wind;
        int geothermal;
        photovoltaic = Integer.parseInt(args[0]);
        wind = Integer.parseInt(args[1]);
        geothermal = Integer.parseInt(args[2]);

        if (photovoltaic < 0 || wind < 0 || geothermal < 0) {
            System.out.println("에너지 생산량은 음수가 될 수 없습니다");
            return;
        }

        if (photovoltaic > 30000 || wind > 30000 || geothermal > 30000) {
            System.out.println("에너지 생산량의 최대값은 30000 입니다");
            return;
        }
        int total = photovoltaic + wind + geothermal;


        System.out.println(photovoltaic + " " + wind + " " + geothermal);
        System.out.println("-> 총 에너지 사용량은 " + total + "입니다");

        // ++
        double photovoltaicRatio = ((double) photovoltaic / total) * 100;
        double windRatio = ((double) wind / total) * 100;
        double geothermalRatio = ((double) geothermal / total) * 100;
        System.out.printf("태양광 %.9f%%, 풍력 %.8f%%, 지열 %.7f%%", photovoltaicRatio, windRatio, geothermalRatio);

    }
}