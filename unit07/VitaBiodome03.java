package unit07;

import java.io.*;
import java.net.*;

public class VitaBiodome03 {
    public static void main(String[] args) {
        final int PORT = 8080;

        System.out.println("Vitamin Storage Web Server 시작 중... (포트: " + PORT + ")");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("서버 시작 실패: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) {
                return;
            }

            System.out.println("클라이언트 요청: " + requestLine);
            String[] parts = requestLine.split(" ");

            String method = parts[0];
            String path = parts[1];

            String body;
            String status;
            String response;

            if ("GET".equals(method) && "/".equals(path)) {
                status = "HTTP/1.1 200 OK";
                body = "<html><body><h1>Welcome to Vitamin Storage :)</h1></body></html>";
            } else {
                status = "HTTP/1.1 404 Not Found";
                body = "<html><body><h1>Page Not Found :(</h1></body></html>";
            }

            response = status + "\r\n"
                    + "Content-Type: text/html; charset=UTF-8\r\n"
                    + "Content-Length: " + body.getBytes().length + "\r\n"
                    + "Connection: close\r\n"
                    + "\r\n"
                    + body;

            out.write(response);
            out.flush();

        } catch (IOException e) {
            System.out.println("클라이언트 처리 중 오류 발생: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("소켓 종료 오류: " + e.getMessage());
            }
        }
    }
}
