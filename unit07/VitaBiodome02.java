package unit07;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class VitaBiodome02 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("URL을 입력하세요: ");
            String input = scanner.nextLine().trim();

            input = input.replaceAll(" ", "");

            URL url = new URL(input);

            try (
                    InputStream is = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is))
            ) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            System.out.println("웹 사이트 데이터를 읽어오는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
