package unit05;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class BiodomeForever01 {

    static class ResearchLog {
        private String fileName;
        private String content;

        public ResearchLog(String fileName) {
            this.fileName = fileName;
        }

        public void loadContent() throws IOException {
            FileInputStream fis = null;
            InputStreamReader isr = null;
            BufferedReader reader = null;

            try {
                fis = new FileInputStream(fileName);
                isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                reader = new BufferedReader(isr);

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                content = sb.toString();
            } finally {
                if (reader != null) try { reader.close(); } catch (IOException e) {}
                if (isr != null) try { isr.close(); } catch (IOException e) {}
                if (fis != null) try { fis.close(); } catch (IOException e) {}
            }
        }

        public void printContent() {
            System.out.println("→ " + content.trim()); // 맨 끝 개행 제거
        }

        public String getFileName() {
            return fileName;
        }
    }

    static class ResearchLogManager {
        public void readAndPrintLog(String fileName) {
            ResearchLog log = new ResearchLog(fileName);

            try {
                log.loadContent();
                System.out.println(log.getFileName());
                log.printContent();
            } catch (FileNotFoundException e) {
                System.out.println("→ 존재하지 않는 파일입니다. 파일 이름을 다시 확인해주세요.");
            } catch (IOException e) {
                System.out.println("→ 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("파일 이름을 입력해 주세요.");
            return;
        }

        ResearchLogManager manager = new ResearchLogManager();

        for (String fileName : args) {
            manager.readAndPrintLog(fileName);
            System.out.println();
        }
    }
}
