package unit07;

import java.io.*;
import java.net.*;
import java.util.*;

public class VitaBiodome04 {

    record Fruit(String name, int price, boolean isMoved, boolean isReserved) {
    }

    private static final int PORT = 8080;
    private static final int MAX_REQUESTS = 5;
    private static int requestCount = 0;
    private static final Map<String, Fruit> fruitMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        fruitMap.put("apple", new Fruit("사과", 3000, false, false));
        fruitMap.put("banana", new Fruit("바나나", 7000, true, false));
        fruitMap.put("orange", new Fruit("오렌지", 5000, false, false));
        fruitMap.put("grape", new Fruit("포도", 6000, false, true));

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("VitaBiodome04 서버가 8080 포트에서 실행 중입니다...");

        while (true) {
            try (Socket client = serverSocket.accept()) {
                handleRequest(client);
            } catch (Exception e) {
                System.err.println("클라이언트 처리 중 오류 발생: " + e.getMessage());
            }
        }
    }

    private static void handleRequest(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

        String requestLine = in.readLine();
        if (requestLine == null || !requestLine.startsWith("GET")) return;

        String[] tokens = requestLine.split(" ");
        if (tokens.length < 2) return;

        String path = tokens[1].substring(1).toLowerCase(); // "/apple" -> "apple"

        if (requestCount >= MAX_REQUESTS) {
            sendResponse(out, 429, "Too Many Requests", "과일 안내 업무가 종료되었습니다");
            return;
        }

        requestCount++;

        Fruit fruit = fruitMap.get(path);

        if (fruit == null) {
            sendResponse(out, 404, "Not Found", "농장에 없는 과일입니다");
        } else if (fruit.isMoved()) {
            sendResponse(out, 301, "Moved Permanently", "다른 농장으로 이동하였습니다");
        } else if (fruit.isReserved()) {
            sendResponse(out, 403, "Forbidden", "예약된 과일입니다");
        } else {
            String message = fruit.name() + " 가격은 " + fruit.price() + "원이며, 곧 수확예정입니다";
            sendResponse(out, 200, "OK", message);
        }
    }

    private static void sendResponse(BufferedWriter out, int statusCode, String statusText, String message)
            throws IOException {
        out.write("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
        out.write("Content-Type: text/html; charset=UTF-8\r\n");
        out.write("\r\n");
        out.write("<html><body><h2>" + message + "</h2></body></html>");
        out.flush();
    }
}
