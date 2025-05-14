package unit01;

public class HelloBiodome01 {
    public static void main(String[] args) {

        if (args.length == 0 || args[0].trim().isEmpty()) {
            System.out.println("이름을 1글자 이상 입력해주세요. 프로그램을 종료합니다");
            return;
        }

        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            nameBuilder.append(args[i]);
            if (i != args.length - 1) {
                nameBuilder.append(" ");
            }
        }
        String name = nameBuilder.toString();

        System.out.println(name + " -> 스프링와트에 오신걸 환영합니다, " + name + "님!");
    }
}

