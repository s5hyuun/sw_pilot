package unit01;

public class HelloBiodome08 {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].trim().isEmpty()) {
            System.out.println("메시지를 입력해주세요.");
            return;
        }

        String input = args[0];
        if (input.length() > 100) {
            System.out.println("메시지는 최대 100자까지 입력 가능합니다.");
            return;
        }

        String[] englishDictionary = {
                "solution", "problem", "please", "thanks", "biodome", "believein",
                "hello", "where", "there", "your", "tree", "help", "need", "this",
                "isn't", "is", "can", "any", "new", "the", "for", "you", "we", "a", "?"
        };

        // ++
        String[] koreanDictionary = {
                "안녕하세요", "신속한", "지원", "감사합니다", "당신의", "도움이", "필요합니다",
                "새로운", "나무를", "발견했습니다"
        };

        boolean isKorean = input.codePoints().anyMatch(ch -> (ch >= 0xAC00 && ch <= 0xD7A3)); // ++ 한글 포함 여부

        String[] dictionary = isKorean ? koreanDictionary : englishDictionary;
        String inputProcessed = isKorean ? input : input.toLowerCase(); // ++ 한글은 그대로, 영어는 소문자로

        StringBuilder result = new StringBuilder();
        int index = 0;

        while (index < inputProcessed.length()) {
            boolean matched = false;

            for (String word : dictionary) {
                int len = word.length();
                if (index + len > inputProcessed.length()) continue;

                String sub = inputProcessed.substring(index, index + len);
                if (sub.equals(word)) {
                    if (!result.isEmpty()) result.append(" ");
                    result.append(sub);
                    index += len;
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                if (!result.isEmpty()) result.append(" ");
                while (index < inputProcessed.length()) {
                    boolean found = false;
                    for (String word : dictionary) {
                        int len = word.length();
                        if (index + len > inputProcessed.length()) continue;
                        if (inputProcessed.substring(index, index + len).equals(word)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                    result.append(inputProcessed.charAt(index));
                    index++;
                }
            }
        }

        // ++
        if (!isKorean && inputProcessed.endsWith("?")) {
            System.out.println(result.toString());
        } else {
            System.out.println(result.toString() + ".");
        }
    }
}
