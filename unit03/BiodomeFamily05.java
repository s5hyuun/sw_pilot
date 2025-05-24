package unit03;

import java.util.ArrayList;
import java.util.List;

public class BiodomeFamily05 {

    public static void main(String[] args) {
        // 마법사 생성
        Sorcerer ariel = new Sorcerer("아리엘");
        System.out.println("마법사 '" + ariel.getName() + "'이 생성되었습니다.");

        // 유물 생성
        SolarStone solarStone = new SolarStone("태양의 돌");
        WindAmulet windAmulet = new WindAmulet("바람의 부적");
        WaterMirror waterMirror = new WaterMirror("물의 거울");

        System.out.println(solarStone.getName() + " 유물이 생성되었습니다.");
        System.out.println(windAmulet.getName() + " 유물이 생성되었습니다.");
        System.out.println(waterMirror.getName() + " 유물이 생성되었습니다. \n");

        // 유물 소유
        ariel.addArtifact(solarStone);
        ariel.addArtifact(windAmulet);
        ariel.addArtifact((waterMirror));

        ariel.checkArtifactAbility(solarStone);
        ariel.useEnergyArtifact(waterMirror);
    }
}

abstract class AncientArtifact {
    private final String name;

    public AncientArtifact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void describe();
}

// 에너지 생성
interface EnergyGenerator {
    void generateEnergy();
}

// 날씨 조절
interface WeatherController {
    void controlWeather();
}

// 태양의 돌
class SolarStone extends AncientArtifact implements EnergyGenerator {

    public SolarStone(String name) {
        super(name);
    }

    @Override
    public void describe() {
        System.out.println("\"" + getName() + "로 에너지 생성 중! 빛을 받은 시간에 따라 에너지의 양이 달라집니다.\"");
    }

    @Override
    public void generateEnergy() {
        System.out.println(getName() + "이 빛을 받아 에너지를 생성합니다.");
    }
}

// 바람의 부적
class WindAmulet extends AncientArtifact implements WeatherController {

    public WindAmulet(String name) {
        super(name);
    }

    @Override
    public void describe() {
        System.out.println("\"" + getName() + "으로 날씨를 조절합니다. 저기압, 고기압, 강풍 등이 가능합니다.\"");
    }

    @Override
    public void controlWeather() {
        System.out.println(getName() + "이 공기의 흐름을 이용해 날씨를 조절합니다.");
    }
}

// 물의 거울
class WaterMirror extends AncientArtifact implements EnergyGenerator, WeatherController {

    public WaterMirror(String name) {
        super(name);
    }

    @Override
    public void describe() {
        System.out.println("\"" + getName() + "은 수증기를 모아 에너지를 생성하고, 비와 눈을 내리게 합니다.\"");
    }

    @Override
    public void generateEnergy() {
        System.out.println(getName() + "을 이용해 수증기로 에너지를 생성했습니다!");
    }

    @Override
    public void controlWeather() {
        System.out.println(getName() + "이 비와 눈을 내리게 합니다.");
    }
}

// 마법사
class Sorcerer {
    private final String name;
    private final List<AncientArtifact> artifacts;

    public Sorcerer(String name) {
        this.name = name;
        this.artifacts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addArtifact(AncientArtifact artifact) {
        artifacts.add(artifact);
        System.out.println("마법사 '" + name + "'이 " + artifact.getName() + "을 소유하게 되었습니다.");
    }

    public void checkArtifactAbility(AncientArtifact artifact) {
        System.out.println("\n마법사 '" + name + "'이 " + artifact.getName() + "의 능력을 확인합니다.\n");
        artifact.describe();
    }

    public void useEnergyArtifact(AncientArtifact artifact) {
        if (artifact instanceof EnergyGenerator) {
            System.out.println("\n마법사 '" + name + "'이 " + artifact.getName() + "의 에너지 생성 능력을 사용합니다.");
            ((EnergyGenerator) artifact).generateEnergy();
        } else {
            System.out.println(artifact.getName() + "은 에너지를 생성할 수 없습니다.");
        }
    }

    public void useWeatherArtifact(AncientArtifact artifact) {
        if (artifact instanceof WeatherController) {
            System.out.println("\n마법사 '" + name + "'이 " + artifact.getName() + "의 날씨 조절 능력을 사용합니다.");
            ((WeatherController) artifact).controlWeather();
        } else {
            System.out.println(artifact.getName() + "은 날씨를 조절할 수 없습니다.");
        }
    }
}
