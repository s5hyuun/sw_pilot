package unit05;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class BiodomeForever03 {
    public static void main(String[] args) {
        String folderPath = "research_logs";

        try {
            ResearchLogExtractor extractor = new ResearchLogExtractor(folderPath);
            extractor.extractAndSaveSummary(folderPath);
        } catch (NoDataAvailableException e) {
            System.out.println(e.getMessage());
        }
    }
}

record ResearchLog(String fileName, String content) {

    public void printContent() {
        System.out.println("[" + fileName + "]\n" + content);
    }

    public String extractPlantInfo() {
        String[] lines = content.split("\n");
        String plantName = null;
        String address = null;

        for (String line : lines) {
            if (line.startsWith("Name.")) {
                plantName = line.substring(5).trim();
            } else if (line.startsWith("ADR.")) {
                address = line.substring(4).trim();
            }
        }

        if (plantName == null || address == null) {
            System.out.println(fileName + ": 식물명 또는 주소 정보가 누락되었습니다.");
            return null;
        }

        return plantName + " - " + address;
    }
}

class ResearchLogExtractor {
    private final List<ResearchLog> logs = new ArrayList<>();

    public ResearchLogExtractor(String folderPath) throws NoDataAvailableException {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            throw new NoDataAvailableException("분석할 파일이 없습니다.");
        }

        for (File file : files) {
            if (file.isFile()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] data = new byte[(int) file.length()];
                    fis.read(data);
                    String content = new String(data);
                    logs.add(new ResearchLog(file.getName(), content));
                } catch (IOException e) {
                    System.out.println(file.getName() + " 읽기 실패: " + e.getMessage());
                }
            }
        }
    }

    public void extractAndSaveSummary(String folderPath) {
        List<String> summaries = new ArrayList<>();
        for (ResearchLog log : logs) {
            String info = log.extractPlantInfo();
            if (info != null) {
                summaries.add(info);
            }
        }

        if (summaries.isEmpty()) {
            System.out.println("요약할 유효한 정보가 없습니다.");
            return;
        }

        String timeStamp = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        String fileName = timeStamp + "_Luminous_ADR.txt";
        File file = new File(folderPath, fileName);

        while (file.exists()) {
            timeStamp = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
            fileName = timeStamp + "_Luminous_ADR.txt";
            file = new File(folderPath, fileName);
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            for (String line : summaries) {
                fos.write((line + "\n").getBytes());
            }
            System.out.println("파일 저장 성공: " + fileName);
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }
}

class NoDataAvailableException extends Exception {
    public NoDataAvailableException(String message) {
        super(message);
    }
}
