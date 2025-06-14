package unit05;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BiodomeForever02 {

    static class ResearchLog {
        private final String fileName;
        private String content;
        private String dateFormatted;

        public ResearchLog(String fileName) {
            this.fileName = fileName;
        }

        public void parseDateFromFileName() throws ParseException {
            String pureFileName = new File(fileName).getName(); // 경로 제거된 파일 이름 추출
            if (pureFileName.length() < 12) {
                throw new ParseException("파일 이름이 너무 짧아서 날짜를 추출할 수 없습니다.", 0);
            }

            String datePart = pureFileName.substring(0, 12);
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmm");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date date = inputFormat.parse(datePart);
            dateFormatted = outputFormat.format(date);
        }

        public void loadContent() throws IOException, SecurityException {
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
                if (reader != null) try { reader.close(); } catch (IOException _) {}
                if (isr != null) try { isr.close(); } catch (IOException _) {}
                if (fis != null) try { fis.close(); } catch (IOException _) {}
            }
        }

        public void printLog() {
            System.out.println("→ " + dateFormatted + " " + content.trim());
        }

        public String getFileName() {
            return fileName;
        }
    }

    static class ResearchLogManager {
        public void processLog(String fileName) {
            ResearchLog log = new ResearchLog(fileName);

            try {
                log.parseDateFromFileName();
                log.loadContent();
                System.out.println(fileName);
                log.printLog();
            } catch (FileNotFoundException e) {
                System.out.println("→ " + e + " 존재하지 않는 파일입니다. 파일 이름을 다시 확인해주세요.");
                e.printStackTrace();
            } catch (ParseException e) {
                System.out.println("→ " + e + " 파일 이름에서 날짜를 확인할 수 없습니다.");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("→ " + e + " 파일을 읽는 중 오류가 발생했습니다.");
                e.printStackTrace();
            } catch (SecurityException e) {
                System.out.println("→ " + e + " 파일에 접근할 수 없습니다. 권한을 확인해주세요.");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("→ 알 수 없는 오류가 발생했습니다.");
                e.printStackTrace();
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("파일 이름을 입력해 주세요.");
            return;
        }

        ResearchLogManager manager = new ResearchLogManager();

        for (String fileName : args) {
            manager.processLog(fileName);
        }
    }
}
