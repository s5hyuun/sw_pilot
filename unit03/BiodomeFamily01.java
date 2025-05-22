package unit03;

import java.util.ArrayList;
import java.util.Iterator;

class Organism {
    private String name;
    private String species;
    private String habitat;

    // 생성자
    public Organism(String name, String species, String habitat) {
        this.name = name;
        this.species = species;
        this.habitat = habitat;
    }

    public void displayInfo(int index) {
        System.out.println(index + ". " + name + ", " + species + ", " + habitat);
    }

    public void setHabitat(String newHabitat) {
        this.habitat = newHabitat;
    }

    public String getName() {
        return name;
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
        // 유니코드 기준
        if (lastChar >= 0xAC00 && lastChar <= 0xD7A3) {
            int uniVal = lastChar - 0xAC00;
            int jongSungIndex = uniVal % 28;
            if (jongSungIndex == 0) {
                return "가"; // 받침이 없을 경우
            } else {
                return "이"; // 받침이 있을 경우
            }
        }
        return "이";
    }

    public void addOrganism(Organism organism) {
        organismList.add(organism);
        String particle = getSubjectParticle(organism.getName());
        System.out.println("[LifeNest] " + organism.getName() + particle + " 추가되었습니다.");
    }

    public void removeOrganism(String name) {
        Iterator<Organism> iterator = organismList.iterator();
        while (iterator.hasNext()) {
            Organism o = iterator.next();
            if (o.getName().equals(name)) {
                iterator.remove();
                String particle = getSubjectParticle(name);
                System.out.println("[LifeNest] " + name + particle + " 삭제되었습니다.");
                return;
            }
        }
        System.out.println("[LifeNest] " + name + "을(를) 찾을 수 없습니다.");
    }

    public void displayAll() {
        System.out.println("\n전체 동식물 목록 출력:");
        int index = 1;
        for (Organism o : organismList) {
            o.displayInfo(index++);
        }
    }

    public Organism findOrganism(String name) {
        for (Organism o : organismList) {
            if (o.getName().equals(name)) {
                return o;
            }
        }
        return null;
    }
}

public class BiodomeFamily01 {
    public static void main(String[] args) {
        LifeNest lifeNest = new LifeNest();

        // 동물
        Organism penguin = new Organism("펭귄", "동물", "남극");
        Organism koala = new Organism("코알라", "동물", "호주");

        // 식물
        Organism cactus = new Organism("선인장", "식물", "사막");
        Organism peppermint = new Organism("페퍼민트", "식물", "정원");

        lifeNest.addOrganism(penguin);
        lifeNest.addOrganism(koala);
        lifeNest.addOrganism(cactus);
        lifeNest.addOrganism(peppermint);

        lifeNest.displayAll();

        lifeNest.removeOrganism("코알라");
        lifeNest.removeOrganism("선인장");

        Organism foundPenguin = lifeNest.findOrganism("펭귄");
        if (foundPenguin != null) {
            foundPenguin.setHabitat("해변");
        }

        lifeNest.displayAll();
    }
}