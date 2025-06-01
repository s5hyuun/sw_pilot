package unit03;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

abstract class Menu {
    private String name;
    private int price;

    public Menu(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }

    public abstract String getDetails();
}

class Coffee extends Menu {
    private String beanType;
    private String size;

    public Coffee(String name, int price, String beanType, String size) {
        super(name, price);
        this.beanType = beanType;
        this.size = size;
        System.out.println("커피가 추가되었습니다. " + name + ": " + price + "원");
    }

    @Override
    public String getDetails() {
        return getName() + " 커피 (사이즈: " + size + ")";
    }
}

class Beverage extends Menu {
    private String size;

    public Beverage(String name, int price, String size) {
        super(name, price);
        this.size = size;
        System.out.println("음료가 추가되었습니다. " + name + ": " + price + "원");
    }

    @Override
    public String getDetails() {
        return getName() + " (사이즈: " + size + ")";
    }
}

class Sandwich extends Menu {
    private String ingredient;
    public LocalDate expirationDate;

    public Sandwich(String name, int price, String ingredient, LocalDate expirationDate) {
        super(name, price);
        this.ingredient = ingredient;
        this.expirationDate = expirationDate;
        System.out.println("샌드위치가 추가되었습니다. " + name + " 샌드위치: " + price + "원 (재료: " + ingredient + ", 만료일: " + expirationDate + ")");
    }

    public boolean isExpired(LocalDate today) {
        return expirationDate.isBefore(today);
    }

    @Override
    public String getDetails() {
        return getName() + " 샌드위치";
    }
}

class Order {
    private List<Menu> items = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
    private String customerName;
    private LocalDateTime orderTime;

    public Order(String customerName) {
        this.customerName = customerName;
        this.orderTime = LocalDateTime.now();
    }

    public void addItem(Menu item, int quantity) {
        items.add(item);
        quantities.add(quantity);
    }

    public int calculateTotal() {
        int sum = 0;
        for (int i = 0; i < items.size(); i++) {
            sum += items.get(i).getPrice() * quantities.get(i);
        }
        return sum;
    }

    public void printOrder(int number) {
        System.out.println("주문" + number + ".");
        System.out.println("고객: " + customerName);
        System.out.println("주문 시각: " + orderTime);
        for (int i = 0; i < items.size(); i++) {
            String name = items.get(i).getDetails();
            int quantity = quantities.get(i);
            int price = items.get(i).getPrice() * quantity;
            System.out.println(name + " - " + quantity + "개: " + price + "원");
        }
        System.out.println("총 금액: " + calculateTotal() + "원\n");
    }
}

class OrderList {
    private Queue<Order> orders = new LinkedList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void printAllOrders() {
        int count = 1;
        for (Order o : orders) {
            o.printOrder(count++);
        }
    }
}

class Caffe {
    private List<Menu> menuList = new ArrayList<>();
    private OrderList orderList = new OrderList();

    public void addMenu(Menu menu) {
        menuList.add(menu);
    }

    public Menu findMenu(String name) {
        for (Menu m : menuList) {
            if (m.getName().equals(name)) return m;
        }
        return null;
    }

    public void placeOrder(String customerName, List<String> productNames, List<Integer> quantities) {
        Order order = new Order(customerName);
        LocalDate today = LocalDate.now();

        for (int i = 0; i < productNames.size(); i++) {
            String name = productNames.get(i);
            int qty = quantities.get(i);
            Menu menu = findMenu(name);
            if (menu instanceof Sandwich && ((Sandwich) menu).isExpired(today)) {
                System.out.println("--\n" + name + " 샌드위치 주문 시도...");
                System.out.println("오류: 주문할 수 없는 상품입니다. (만료날짜: " + ((Sandwich) menu).expirationDate + ")\n");
                return;
            }
            order.addItem(menu, qty);
        }

        orderList.addOrder(order);
        System.out.println("=== 주문이 추가되었습니다. ===");
        order.printOrder(0);
    }

    public void showAllOrders() {
        orderList.printAllOrders();
    }
}

public class BiodomeFamily09 {
    public static void main(String[] args) {
        Caffe caffe = new Caffe();

        caffe.addMenu(new Coffee("블렌드", 4000, "블렌드", "톨"));
        caffe.addMenu(new Coffee("다크", 4500, "다크", "톨"));
        caffe.addMenu(new Coffee("디카페인", 4200, "디카페인", "톨"));

        caffe.addMenu(new Beverage("캐모마일", 3000, "톨"));
        caffe.addMenu(new Beverage("오렌지 쥬스", 3500, "톨"));
        caffe.addMenu(new Beverage("물", 1000, "톨"));

        caffe.addMenu(new Sandwich("야채", 5000, "야채", LocalDate.of(2123, 10, 10)));
        caffe.addMenu(new Sandwich("햄", 6000, "햄", LocalDate.of(2123, 10, 11)));
        caffe.addMenu(new Sandwich("치즈", 5500, "치즈", LocalDate.of(2122, 1, 6)));

        caffe.placeOrder("제이미", Arrays.asList("블렌드", "야채"), Arrays.asList(2, 1));
        caffe.placeOrder("레냐", Arrays.asList("캐모마일"), Arrays.asList(1));
        caffe.placeOrder("테스트", Arrays.asList("치즈"), Arrays.asList(1));

        caffe.showAllOrders();
    }
}