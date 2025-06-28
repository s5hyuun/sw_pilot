package unit05;

import java.io.*;
import java.util.*;

public class BiodomeForever09 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("중복 파일 검색기에 오신 걸 환영합니다.");
        System.out.print("탐색할 폴더를 입력하세요: ");

        String folderPath = scanner.nextLine().trim();
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("→ 탐색 결과\n\n올바르지 않은 경로입니다.");
            return;
        }

        try {
            List<File> fileList = getAllFiles(folder);
            findDuplicateFiles(fileList);
        } catch (IOException e) {
            System.out.println("파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private static List<File> getAllFiles(File folder) {
        List<File> files = new ArrayList<>();
        File[] list = folder.listFiles();
        if (list != null) {
            for (File f : list) {
                if (f.isFile()) {
                    files.add(f);
                }
            }
        }
        return files;
    }

    private static boolean compareFiles(File f1, File f2) throws IOException {
        if (f1.length() != f2.length()) return false;

        try (InputStream is1 = new FileInputStream(f1);
             InputStream is2 = new FileInputStream(f2)) {

            int byte1, byte2;
            while ((byte1 = is1.read()) != -1 && (byte2 = is2.read()) != -1) {
                if (byte1 != byte2) return false;
            }
        }
        return true;
    }

    private static void findDuplicateFiles(List<File> files) throws IOException {
        boolean foundDuplicate = false;
        Set<String> reported = new HashSet<>();

        System.out.println("→ 탐색 결과\n");
        for (int i = 0; i < files.size(); i++) {
            for (int j = i + 1; j < files.size(); j++) {
                File f1 = files.get(i);
                File f2 = files.get(j);
                if (compareFiles(f1, f2)) {
                    String key = f1.getName() + ":" + f2.getName();
                    if (!reported.contains(key)) {
                        System.out.println(f1.getName() + " 파일과 " + f2.getName() + " 파일은 동일한 파일입니다.");
                        reported.add(key);
                        foundDuplicate = true;
                    }
                }
            }
        }
        if (!foundDuplicate) {
            System.out.println("중복된 파일이 없습니다.");
        }
    }
}
