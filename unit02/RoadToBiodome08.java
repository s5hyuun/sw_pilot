package unit02;

public class RoadToBiodome08 {
    static int[] queue = new int[10]; // ++ 기본 큐 크기 10
    static int front = 0;
    static int rear = 0;
    static int capacity = 10;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("자원 요청값이 입력되지 않았습니다.");
            return;
        }

        for (String arg : args) {
            try {
                int value = Integer.parseInt(arg);
                enqueue(value);
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력이 포함되어 있습니다: " + arg);
                return;
            }
        }

        while (!isEmpty()) {
            int resource = dequeue();
            System.out.println("자원 " + resource + "을(를) 제공했습니다.");
        }

        System.out.println("모든 요청이 처리되었습니다.");
    }

    // ++
    public static void enqueue(int value) {
        if (rear == capacity) {
            expandQueue();
        }
        queue[rear++] = value;
    }

    public static int dequeue() {
        if (isEmpty()) throw new RuntimeException("큐가 비어 있습니다");
        return queue[front++];
    }

    public static int peek() {
        if (isEmpty()) throw new RuntimeException("큐가 비어 있습니다");
        return queue[front];
    }

    public static boolean isEmpty() {
        return front == rear;
    }

    // ++ 큐 크기를 10 추가
    public static void expandQueue() {
        int newCapacity = capacity + 10;
        int[] newQueue = new int[newCapacity];

        for (int i = front; i < rear; i++) {
            newQueue[i] = queue[i];
        }

        queue = newQueue;
        capacity = newCapacity;

        System.out.println("Queue의 크기가 " + capacity + "으로 늘어났습니다.");
    }
}