package unit03;

import java.util.ArrayList;
import java.util.List;

public class BiodomeFamily06 {

    public static void main(String[] args) {
        Animal monkey = new Animal("제니", "원숭이", 4);
        Animal tiger = new Animal("타이", "호랑이", 9);
        Animal deer = new Animal("바비", "사슴", 7);
        Animal elephant = new Animal("고먀", "코끼리", 4);
        Animal rhino = new Animal("로아", "코뿔소", 5);

        List<Animal> originalList = new ArrayList<>();
        originalList.add(monkey);
        originalList.add(elephant);
        originalList.add(tiger);
        originalList.add(rhino);
        originalList.add(deer);

        System.out.println(" 원래 순서: ");
        printAnimalList(originalList);

        List<Animal> safeOrder = getSafeOrder(originalList);
        if (safeOrder != null) {
            System.out.println("\n 안전한 이동 순서: ");
            printAnimalList(safeOrder);
        } else {
            System.out.println("\n 안전한 순서를 만들 수 없습니다 ");
        }
    }

    // Animal 클래스
    private static class Animal {
        private final String name;
        private final String species;
        private final int age;

        public Animal(String name, String species, int age) {
            this.name = name;
            this.species = species;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public String getSpecies() {
            return species;
        }

        public int getAge() {
            return age;
        }

        public void printInfo() {
            System.out.print(name + "(" + species + ", " + age + "살)");
        }
    }

    // 안전한 순서 계산
    public static List<Animal> getSafeOrder(List<Animal> animals) {
        List<Animal> result = new ArrayList<>();
        return arrange(animals, result) ? result : null;
    }

    // 재귀적으로 모든 조합 탐색
    private static boolean arrange(List<Animal> remaining, List<Animal> arranged) {
        if (remaining.isEmpty()) return true;

        for (int i = 0; i < remaining.size(); i++) {
            Animal current = remaining.get(i);
            if (isSafeToAdd(arranged, current)) {
                arranged.add(current);
                List<Animal> next = new ArrayList<>(remaining);
                next.remove(i);
                if (arrange(next, arranged)) return true;
                arranged.remove(arranged.size() - 1);
            }
        }

        return false;
    }

    // 조건에 따라 안전한지 판단
    private static boolean isSafeToAdd(List<Animal> list, Animal current) {
        int size = list.size();

        // 사슴 뒤에는 호랑이 올 수 없음
        if (size > 0 && list.get(size - 1).getSpecies().equals("사슴") && current.getSpecies().equals("호랑이")) {
            return false;
        }

        // 5살 이하 코끼리는 호랑이 앞에 올 수 없음
        if (current.getSpecies().equals("코끼리") && current.getAge() <= 5) {
            for (Animal a : list) {
                if (a.getSpecies().equals("호랑이")) {
                    return false;  // 앞에 호랑이가 있으면 안 됨
                }
            }
        }
        return true;
    }

    private static void printAnimalList(List<Animal> list) {
        System.out.print("[");
        for (int i = 0; i < list.size(); i++) {
            list.get(i).printInfo();
            if (i < list.size() - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
