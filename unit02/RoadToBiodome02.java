package unit02;

public class RoadToBiodome02 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("입력된 메시지가 올바르지 않습니다. 다시 한번 확인해주세요.");
            return;
        }

        StringBuilder inputBuilder = new StringBuilder();
        for (String arg : args) {
            inputBuilder.append(arg).append(" ");
        }
        String input = inputBuilder.toString().trim();

        if (input.trim().isEmpty()) {
            System.out.println("입력된 메시지가 올바르지 않습니다. 다시 한번 확인해주세요.");
            return;
        }

        // ++ 회문 여부
        String cleaned = input.replaceAll("\\s+", "");
        boolean isPalindrome = true;

        for (int i = 0; i < cleaned.length() / 2; i++) {
            if (cleaned.charAt(i) != cleaned.charAt(cleaned.length() - 1 - i)) {
                isPalindrome = false;
                break;
            }
        }

        if (isPalindrome) {
            System.out.println(input);
            return;
        }

        char[] stack = new char[input.length()];
        int top = -1;

        // push() 연산
        for (int i = 0; i < input.length(); i++) {
            top++;
            stack[top] = input.charAt(i);
        }

        // pop() 연산
        while (top >= 0) {
            System.out.print(stack[top]);
            top--;
        }
        System.out.println();
    }
}
