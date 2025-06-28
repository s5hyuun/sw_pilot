package unit06;

import java.util.*;
import java.util.stream.*;

public class RunBiodome01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("멤버 리스트를 입력하세요:");

        String input = scanner.nextLine().trim();

        input = input.replaceAll("^\\[|\\]$", "");
        List<String> members = Arrays.stream(input.split(",\\s*"))
                .toList();

        List<String> welcomeMessages = members.stream()
                .filter(name -> name.startsWith("신입-"))
                .map(name -> name.substring(3) + "님 환영합니다")
                .collect(Collectors.toList());

        System.out.println("→\n");
        System.out.println(welcomeMessages);
    }
}

