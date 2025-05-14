package unit01;

public class HelloBiodome07 {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("염기서열이 입력되지 않았습니다.");
            return;
        }

        // ++
        String rawInput = String.join(" ", args).toUpperCase();

        rawInput = rawInput.replaceAll("\\s+", " ");

        String valid = "CJHEY";

        // ++
        for (int i = 0; i < rawInput.length(); i++) {
            char c = rawInput.charAt(i);
            if (c != ' ' && valid.indexOf(c) == -1) {
                System.out.println("염기서열은 C, J, H, E, Y 다섯가지로만 입력됩니다. 확인하고 다시 입력해주세요");
                return;
            }
        }

        StringBuilder result = new StringBuilder();
        String[] parts = rawInput.split(" "); // ++ 공백 기준으로 나누어 각 부분별 처리

        for (int p = 0; p < parts.length; p++) {
            String part = parts[p];
            if (part.isEmpty()) continue;

            char current = part.charAt(0);
            int count = 1;

            for (int i = 1; i < part.length(); i++) {
                char next = part.charAt(i);
                if (next == current) {
                    count++;
                } else {
                    result.append(current).append(count);
                    current = next;
                    count = 1;
                }
            }

            result.append(current).append(count);

            // ++
            if (p < parts.length - 1) {
                result.append(" ");
            }
        }

        System.out.println(result.toString());
    }
}
