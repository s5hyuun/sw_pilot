package unit03;

public class BiodomeFamily04 {

    public static void main(String[] args) {
        SolarEnergy solar = new SolarEnergy();
        WindEnergy wind = new WindEnergy();
        GeothermalEnergy geo = new GeothermalEnergy();

        // 에너지 생산
        solar.produceEnergy(5);
        wind.produceEnergy(12);
        geo.produceEnergy(4);

        // 에너지 소모
        solar.useEnergy(30);
        wind.useEnergy(30);
        geo.useEnergy(30);

        // 남은 에너지
        int totalRemaining = EnergyManager.getTotalEnergy(solar, wind, geo);
        System.out.println("\n남은 에너지: " + totalRemaining);
    }
}

abstract class EnergySource {
    private final String sourceName;
    protected int energyAmount;

    public EnergySource(String sourceName) {
        this.sourceName = sourceName;
        this.energyAmount = 0;
    }

    public String getSourceName() {
        return sourceName;
    }

    public int getEnergyAmount() {
        return energyAmount;
    }

    public void useEnergy(int amount) {
        if (amount <= energyAmount) {
            energyAmount -= amount;
            System.out.println(sourceName + " 에너지를 " + amount + " 사용했습니다.");
        } else {
            System.out.println(sourceName + " 에너지가 부족합니다.");
        }
    }

    // abstract 키워드
    public abstract void produceEnergy(int conditionValue);
}

// 태양광
class SolarEnergy extends EnergySource {
    public SolarEnergy() {
        super("태양광");
    }

    @Override
    public void produceEnergy(int sunlightHours) {
        int produced = sunlightHours * 10;
        energyAmount += produced;
        System.out.println(getSourceName() + " 에너지를 " + produced + " 생산했습니다.");
    }
}

// 풍력
class WindEnergy extends EnergySource {
    public WindEnergy() {
        super("풍력");
    }

    @Override
    public void produceEnergy(int windSpeed) {
        int produced = windSpeed * 5;
        energyAmount += produced;
        System.out.println(getSourceName() + " 에너지를 " + produced + " 생산했습니다.");
    }
}

// 지열
class GeothermalEnergy extends EnergySource {
    public GeothermalEnergy() {
        super("지열");
    }

    @Override
    public void produceEnergy(int groundTemperature) {
        int produced = groundTemperature * 5 + 20;
        energyAmount += produced;
        System.out.println(getSourceName() + " 에너지를 " + produced + " 생성했습니다. \n");
    }
}

// 에너지 관리
class EnergyManager {
    public static int getTotalEnergy(EnergySource... sources) {
        int total = 0;
        for (EnergySource source : sources) {
            total += source.getEnergyAmount();
        }
        return total;
    }
}
