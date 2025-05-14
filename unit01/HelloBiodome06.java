package unit01;

public class HelloBiodome06 {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("두 개의 유전자 코드를 입력해주세요.");
            return;
        }

        String gene1 = args[0];
        String gene2 = args[1];

        if (gene1.length() < 5 || gene1.length() > 20 || gene2.length() < 5 || gene2.length() > 20) {
            System.out.println("유전자 코드는 5자 이상 20자 이하이어야 합니다.");
            return;
        }

        if (gene1.length() != gene2.length()) {
            System.out.println("일치하지 않습니다.");
            if (gene1.contains(gene2) || gene2.contains(gene1)) {
                System.out.println("부분적으로 포함됩니다");
            }
            return;
        }

        int i = 0;
        boolean isSame = true;

        while (i < gene1.length()) {
            if (gene1.charAt(i) != gene2.charAt(i)) {
                isSame = false;
                break;
            }
            i++;
        }

        if (isSame) {
            System.out.println("동일한 유전자 코드입니다.");
        } else {
            System.out.println("일치하지 않습니다.");
        }
        // ++
        if (!gene1.contains(gene2) || !gene2.contains(gene1)){
            System.out.println("포함되지 않습니다");
            return;
        }
    }
}

