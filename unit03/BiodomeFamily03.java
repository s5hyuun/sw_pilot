package unit03;

import java.util.ArrayList;

public class BiodomeFamily03 {
    public static void main(String[] args) {
        LifeNest lifeNest = new LifeNest();

        Animal penguin = new Animal("펭귄", "남극", "육식", "물고기");
        Animal koala = new Animal("코알라", "호주", "초식", "유칼립투스");

        Plant cactus = new Plant("선인장", "사막", "11월", "열매 있음");
        Plant peppermint = new Plant("페퍼민트", "정원", "7월", "열매 없음");

        lifeNest.addOrganism(penguin);
        lifeNest.addOrganism(koala);
        lifeNest.addOrganism(cactus);
        lifeNest.addOrganism(peppermint);

        lifeNest.displayAll();

        lifeNest.removeOrganism("코알라");
        lifeNest.removeOrganism("선인장");

        lifeNest.displayAll();

        // 서식지 변경
        Organism found = lifeNest.findOrganism("펭귄");
        if (found != null) {
            found.setHabitat("해변");
            System.out.println("\n[수정됨] " + found.getName() + "의 서식지가 변경되었습니다.");
        }

        lifeNest.displayAll();
    }
}

class Organism {
    private String name;
    private String species;
    private String habitat;

    public Organism(String name, String species, String habitat) {
        this.name = name;
        this.species = species;
        this.habitat = habitat;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public void displayInfo() {
        System.out.println(name + ", " + species + ", " + habitat);
    }
}

class Animal extends Organism {
    private String digestionType;
    private String food;

    public Animal(String name, String habitat, String digestionType, String food) {
        super(name, "동물", habitat);
        this.digestionType = digestionType;
        this.food = food;
    }

    @Override
    public void displayInfo() {
        System.out.println(getName() + ", " + getSpecies() + ", " + getHabitat() + ", " + digestionType + ", " + food);
    }
}

class Plant extends Organism {
    private String bloomSeason;
    private String hasFruit;

    public Plant(String name, String habitat, String bloomSeason, String hasFruit) {
        super(name, "식물", habitat);
        this.bloomSeason = bloomSeason;
        this.hasFruit = hasFruit;
    }

    @Override
    public void displayInfo() {
        System.out.println(getName() + ", " + getSpecies() + ", " + getHabitat() + ", " + bloomSeason + ", " + hasFruit);
    }
}

class LifeNest {
    private ArrayList<Organism> organismList;

    public LifeNest() {
        organismList = new ArrayList<>();
    }

    public void addOrganism(Organism organism) {
        organismList.add(organism);
        System.out.println("[LifeNest] " + organism.getName() + "이(가) 추가되었습니다.");
    }

    public void removeOrganism(String name) {
        Organism toRemove = null;
        for (Organism o : organismList) {
            if (o.getName().equals(name)) {
                toRemove = o;
                break;
            }
        }
        if (toRemove != null) {
            organismList.remove(toRemove);
            System.out.println("[LifeNest] " + toRemove.getName() + "이(가) 삭제되었습니다.");
        }
    }

    public void displayAll() {
        System.out.println("\n전체 동식물 목록 출력:");
        int index = 1;
        for (Organism o : organismList) {
            System.out.print(index + ". ");
            o.displayInfo();
            index++;
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
