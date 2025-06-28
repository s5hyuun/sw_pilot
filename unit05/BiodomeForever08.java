package unit05;

import java.io.*;
import java.util.*;

class EnvironmentData {
    private String dateTime;
    private double oxygen;
    private double humidity;
    private double temperature;

    public EnvironmentData(String dateTime, double oxygen, double humidity, double temperature) {
        this.dateTime = dateTime;
        this.oxygen = oxygen;
        this.humidity = humidity;
        this.temperature = temperature;
    }

    public String getDate() {
        return dateTime.split(" ")[0];
    }

    public String getDateTime() {
        return dateTime;
    }

    public double getOxygen() { return oxygen; }
    public double getHumidity() { return humidity; }
    public double getTemperature() { return temperature; }

    public void setOxygen(double oxygen) { this.oxygen = oxygen; }
    public void setHumidity(double humidity) { this.humidity = humidity; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public String toDataString() {
        return dateTime + "," + oxygen + "," + humidity + "," + temperature;
    }

    @Override
    public String toString() {
        return oxygen + "," + humidity + "," + temperature;
    }
}

class Node {
    EnvironmentData data;
    Node left;
    Node right;

    public Node(EnvironmentData data) {
        this.data = data;
    }

    public String getKeyDate() {
        return data.getDate();
    }
}

class BinarySearchTree {
    private Node root;

    public void insert(EnvironmentData data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node node, EnvironmentData data) {
        if (node == null) return new Node(data);
        int cmp = data.getDate().compareTo(node.getKeyDate());
        if (cmp < 0) node.left = insertRec(node.left, data);
        else if (cmp > 0) node.right = insertRec(node.right, data);
        else node.data = data;
        return node;
    }

    public Node search(String date) {
        return searchRec(root, date);
    }

    private Node searchRec(Node node, String date) {
        if (node == null || node.getKeyDate().equals(date)) return node;
        int cmp = date.compareTo(node.getKeyDate());
        if (cmp < 0) return searchRec(node.left, date);
        else return searchRec(node.right, date);
    }
}

public class BiodomeForever08 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinarySearchTree bst = new BinarySearchTree();
        File file = new File("unit05/environment_data_Lake.txt");
        List<String> allLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                allLines.add(line);
                String[] parts = line.trim().split(",");
                if (parts.length != 4 || !parts[0].contains(" ")) continue;
                String dateTime = parts[0];
                double oxygen = Double.parseDouble(parts[1]);
                double humidity = Double.parseDouble(parts[2]);
                double temp = Double.parseDouble(parts[3]);
                bst.insert(new EnvironmentData(dateTime, oxygen, humidity, temp));
            }
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
            return;
        }

        System.out.println("환경 데이터 검색 및 수정 프로그램에 오신 것을 환영합니다!");
        System.out.print("검색하고 싶은 날짜를 입력하세요 : ");
        String inputDate = scanner.nextLine().trim();

        Node result = bst.search(inputDate);
        if (result == null) {
            System.out.println("→ 해당 날짜의 데이터는 존재하지 않습니다.");
            return;
        }

        System.out.println("→ 검색 결과: " + result.data);
        System.out.print("데이터를 수정하시겠습니까? (Y/N): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y")) {
            System.out.print("새로운 데이터 값을 입력하세요 : ");
            String[] newVals = scanner.nextLine().trim().split(",");
            if (newVals.length != 3) {
                System.out.println("→ 입력 형식이 잘못되었습니다. 산소,습도,온도 형식으로 입력하세요.");
                return;
            }
            try {
                double newOxy = Double.parseDouble(newVals[0]);
                double newHum = Double.parseDouble(newVals[1]);
                double newTemp = Double.parseDouble(newVals[2]);

                result.data.setOxygen(newOxy);
                result.data.setHumidity(newHum);
                result.data.setTemperature(newTemp);

                System.out.println("→ 데이터 수정 완료!");
                System.out.println("날짜 " + inputDate + ", 수정된 데이터 " + result.data);

                File tempFile = new File("unit05/environment_data_Lake_tmp.txt");
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
                    for (String line : allLines) {
                        if (line.startsWith(result.data.getDateTime())) {
                            bw.write(result.data.toDataString());
                        } else {
                            bw.write(line);
                        }
                        bw.newLine();
                    }
                }
                if (file.delete()) {
                    if (!tempFile.renameTo(file)) {
                        System.out.println("→ 임시 파일을 원본 파일로 변경하는 데 실패했습니다.");
                    }
                } else {
                    System.out.println("→ 원본 파일 삭제에 실패하였습니다.");
                }

            } catch (NumberFormatException e) {
                System.out.println("→ 입력값에 문자가 포함되어 있습니다. 숫자 형식으로 입력하세요.");
            } catch (IOException e) {
                System.out.println("→ 파일 저장 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("수정을 취소하였습니다.");
        }
    }
}
