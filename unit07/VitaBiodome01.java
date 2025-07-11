package unit07;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class VitaBiodome01 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("URL 주소를 입력하세요: ");
            String input = scanner.nextLine().trim();

            input = input.replaceAll(" ", "");

            URL url = new URL(input);

            System.out.println("\nurl.getAuthority(): " + url.getAuthority());

            try (InputStream content = (InputStream) url.getContent()) {
                System.out.println("url.getContent(): " + content);
            } catch (IOException e) {
                System.out.println("url.getContent(): 콘텐츠를 불러올 수 없습니다. (" + e.getMessage() + ")");
            }

            System.out.println("url.getDefaultPort(): " + url.getDefaultPort());
            System.out.println("url.getPort(): " + url.getPort());
            System.out.println("url.getFile(): " + url.getFile());
            System.out.println("url.getHost(): " + url.getHost());
            System.out.println("url.getPath(): " + url.getPath());
            System.out.println("url.getProtocol(): " + url.getProtocol());
            System.out.println("url.getQuery(): " + url.getQuery());
            System.out.println("url.getRef(): " + url.getRef());
            System.out.println("url.getUserInfo(): " + url.getUserInfo());
            System.out.println("url.toExternalForm(): " + url.toExternalForm());

            try {
                System.out.println("url.toURI(): " + url.toURI());
            } catch (URISyntaxException e) {
                System.out.println("url.toURI(): 변환 실패 (" + e.getMessage() + ")");
            }

        } catch (MalformedURLException e) {
            System.out.println("잘못된 URL 형식입니다. 다시 입력해주세요.");
        }
    }
}
