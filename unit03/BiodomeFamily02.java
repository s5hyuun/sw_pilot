package unit03;

import java.util.ArrayList;
import java.util.Iterator;

class Organism {
    private String name;
    private String species;
    private String habitat;

    public Organism(String name, String species, String habitat) {
        this.name = name;
        this.species = species;
        this.habitat = habitat;
    }
    public void displayInfo(int index) {
        System.out.println(index + ". " + name + ", " + species + ", " + habitat);
    }

    // getter 메서드
    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getHabitat() {
        return habitat;
    }

    // setter 메서드 > 서식지 수정
    public void setHabitat(String habitat) {
        this.habitat = habitat;
        System.out.println("[LifeNest] " + name + " 서식지가 변경되었습니다.");
    }
}

class LifeNest {
    private ArrayList<Organism> organismList;

    public LifeNest() {
        organismList = new ArrayList<>();
    }

    // 조사 맞춰서 출력
    private String getSubjectParticle(String word) {
        char lastChar = word.charAt(word.length() - 1);
        if (lastChar >= 0xAC00 && lastChar <= 0xD7A3) {
            int uniVal = lastChar - 0xAC00;
            int jongSungIndex = uniVal % 28;
            return (jongSungIndex == 0) ? "가" : "이";
        }
        return "이";
    }

    // 추가
    public void addOrganism(Organism o) {
        organismList.add(o);
        System.out.println("[LifeNest] " + o.getName() + getSubjectParticle(o.getName()) + " 추가되었습니다.");
    }

    // 삭제
    public void removeOrganism(String name) {
        Iterator<Organism> iterator = organismList.iterator();
        while (iterator.hasNext()) {
            Organism o = iterator.next();
            if (o.getName().equals(name)) {
                iterator.remove();
                System.out.println("[LifeNest] " + name + getSubjectParticle(name) + " 삭제되었습니다.");
                return;
            }
        }
        System.out.println("[LifeNest] " + name + "을(를) 찾을 수 없습니다.");
    }

    // 전체 동식물 출력
    public void displayAll() {
        System.out.println("\n전체 동식물 목록 출력:");
        int index = 1;
        for (Organism o : organismList) {
            o.displayInfo(index++);
        }
    }

    // 이름으로 생물 객체 찾기
    public Organism findOrganism(String name) {
        for (Organism o : organismList) {
            if (o.getName().equals(name)) {
                return o;
            }
        }
        return null;
    }
}

public class BiodomeFamily02 {
    public static void main(String[] args) {
        LifeNest lifeNest = new LifeNest();

        // 동물
        Organism penguin = new Organism("펭귄", "동물", "남극");
        Organism koala = new Organism("코알라", "동물", "호주");

        // 식물
        Organism cactus = new Organism("선인장", "식물", "사막");
        Organism peppermint = new Organism("페퍼민트", "식물", "정원");

        // 추가
        lifeNest.addOrganism(penguin);
        lifeNest.addOrganism(koala);
        lifeNest.addOrganism(cactus);
        lifeNest.addOrganism(peppermint);

        lifeNest.displayAll();

        // 삭제
        lifeNest.removeOrganism("코알라");
        lifeNest.removeOrganism("선인장");

        // 서식지 변경
        Organism found = lifeNest.findOrganism("펭귄");
        if (found != null) {
            found.setHabitat("해변");
        }

        lifeNest.displayAll();
    }
}