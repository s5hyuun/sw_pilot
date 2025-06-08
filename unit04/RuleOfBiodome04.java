package unit04;
import java.util.*;

// 종별 특징 클래스 정의
class AnimalFeature {
    String behavior;
    String reproduction;
    String predator;
    String prey;
    String lifespan;

    public AnimalFeature(String behavior, String reproduction, String predator, String prey, String lifespan) {
        this.behavior = behavior;
        this.reproduction = reproduction;
        this.predator = predator;
        this.prey = prey;
        this.lifespan = lifespan;
    }

    public String toString() {
        return behavior + ", " + reproduction + ", " + predator + ", " + prey + ", " + lifespan;
    }
}

class PlantFeature {
    String flowerColor;
    String fruit;
    String bloomSeason;

    public PlantFeature(String flowerColor, String fruit, String bloomSeason) {
        this.flowerColor = flowerColor;
        this.fruit = fruit;
        this.bloomSeason = bloomSeason;
    }

    public String toString() {
        return flowerColor + ", " + fruit + ", " + bloomSeason;
    }
}

class MicrobeFeature {
    String habitat;
    boolean pathogenic;
    String metabolism;

    public MicrobeFeature(String habitat, boolean pathogenic, String metabolism) {
        this.habitat = habitat;
        this.pathogenic = pathogenic;
        this.metabolism = metabolism;
    }

    public String toString() {
        return habitat + ", " + metabolism;
    }
}

// 생물 클래스
class BiologicalEntity<T> {
    String name;
    String category;
    T feature;

    public BiologicalEntity(String name, String category, T feature) {
        this.name = name;
        this.category = category;
        this.feature = feature;
    }

    public String toString() {
        return name + ", " + category + ", " + feature.toString();
    }
}

// 생물 시스템 클래스
class BiologicalSystem<T> {
    List<BiologicalEntity<T>> entities = new ArrayList<>();

    public void add(BiologicalEntity<T> entity) {
        entities.add(entity);
        System.out.println("새로운 생물이 등록되었습니다 : " + entity.name);
    }

    public void delete() {
        if (!entities.isEmpty()) {
            BiologicalEntity<T> removed = entities.remove(entities.size() - 1);
            System.out.println("생물이 삭제 되었습니다 : " + removed.name);
        }
    }

    public void clear() {
        entities.clear();
        System.out.println("모든 정보를 삭제했습니다.");
    }

    public void show() {
        if (!entities.isEmpty()) {
            BiologicalEntity<T> latest = entities.get(entities.size() - 1);
            System.out.println("최신 등록 생물 : " + latest);
        }
    }

    public void isEmpty() {
        if (entities.isEmpty()) {
            System.out.println("생물 정보 리스트는 비어있습니다.");
        } else {
            System.out.println("생물 정보 리스트가 비어있지 않습니다.");
        }
    }
}

public class RuleOfBiodome04 {
    public static void main(String[] args) {
        System.out.println("생물정보 시스템이 생성되었습니다.\n");

        BiologicalSystem<Object> system = new BiologicalSystem<>();

        // 동물
        system.add(new BiologicalEntity<>("고양이", "동물", new AnimalFeature("귀여움", "포유류", "없음", "쥐", "20년")));
        system.add(new BiologicalEntity<>("얼룩말", "동물", new AnimalFeature("잘 달린다", "포유류", "사자", "풀", "10년")));

        // 식물
        system.add(new BiologicalEntity<>("로즈마리", "식물", new PlantFeature("보라색", "열매 없음", "7월")));
        system.add(new BiologicalEntity<>("벚꽃", "식물", new PlantFeature("분홍색", "열매 있음", "3월")));

        // 미생물
        system.add(new BiologicalEntity<>("이콜라이", "미생물", new MicrobeFeature("약 산성", true, "호흡 및 발효 대사")));
        system.add(new BiologicalEntity<>("바실러스", "미생물", new MicrobeFeature("약 산성", false, "호흡 대사")));

        // 기능 실행
        system.delete();
        system.show();
        system.isEmpty();
        system.clear();
        system.isEmpty();
    }
}
