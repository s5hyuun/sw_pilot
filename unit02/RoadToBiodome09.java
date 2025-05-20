package unit02;

import java.util.*;

public class RoadToBiodome09 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("지형 정보가 입력되지 않았습니다.");
            return;
        }

        int N = args.length;
        int M = args[0].length();

        for (String row : args) {
            if (row.length() != M) {
                System.out.println("모든 행의 길이가 같아야 합니다");
                return;
            }
            if (!row.matches("[01]+")) {
                System.out.println("0과 1로만 구성되어야 합니다");
                return;
            }
        }

        // 2차원 배열로 변환
        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            String row = args[i];
            for (int j = 0; j < M; j++) {
                map[i][j] = row.charAt(j) - '0';
            }
        }

        // BFS > 최단 거리
        int result = bfs(map, N, M);

        if (result == -1) {
            System.out.println("입구에서 출구로 이동할 수 없습니다");
        } else {
            System.out.println(result);
        }
    }

    public static int bfs(int[][] map, int N, int M) {
        boolean[][] visited = new boolean[N][M];
        int[][] distance = new int[N][M];

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        Queue<int[]> queue = new LinkedList<>();

        if (map[0][0] == 0) return -1;

        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        distance[0][0] = 1;

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0];
            int y = pos[1];

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx >= 0 && nx < N && ny >= 0 && ny < M) {
                    if (!visited[nx][ny] && map[nx][ny] == 1) {
                        visited[nx][ny] = true;
                        distance[nx][ny] = distance[x][y] + 1;
                        queue.offer(new int[]{nx, ny});
                    }
                }
            }
        }

        return distance[N - 1][M - 1] == 0 ? -1 : distance[N - 1][M - 1];
    }
}