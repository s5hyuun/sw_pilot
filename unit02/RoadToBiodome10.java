package unit02;

public class RoadToBiodome10 {
    static final int MAX = 101;
    static boolean[][] graph = new boolean[MAX][MAX];
    static boolean[] visited = new boolean[MAX];

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("입력값이 없습니다");
            return;
        }

        for (String pair : args) {
            String[] nodes = pair.split(",");
            if (nodes.length != 2) {
                System.out.println("입력 형식이 잘못되었습니다");
                return;
            }

            int a, b;
            try {
                a = Integer.parseInt(nodes[0]);
                b = Integer.parseInt(nodes[1]);
            } catch (NumberFormatException e) {
                System.out.println("입력 값에 문자가 포함되어 있습니다");
                return;
            }

            if (a < 1 || a > 100 || b < 1 || b > 100) {
                System.out.println("식물의 범위를 벗어난 숫자가 포함되어 있습니다");
                return;
            }

            graph[a][b] = true;
            graph[b][a] = true;
        }

        int groupCount = 0;

        for (int i = 1; i < MAX; i++) {
            if (!visited[i]) {
                if (hasConnection(i)) {
                    dfs(i);
                    groupCount++;
                }
            }
        }

        System.out.println(groupCount);
    }

    static void dfs(int node) {
        visited[node] = true;
        for (int i = 1; i < MAX; i++) {
            if (graph[node][i] && !visited[i]) {
                dfs(i);
            }
        }
    }

    static boolean hasConnection(int node) {
        for (int i = 1; i < MAX; i++) {
            if (graph[node][i]) return true;
        }
        return false;
    }
}
