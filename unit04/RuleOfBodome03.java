package unit04;

interface PowerControllable {
    void powerOn();
    void powerOff();
    boolean isPoweredOn();
}

interface SmartFeatureActivatable {
    void activateSmartFeature();
}

class DeviceDetails {
    private String model;
    private static final String BRAND = "DOMETech";

    public DeviceDetails(String model) {
        this.model = model;
    }

    public void printInfo() {
        System.out.println("Model: " + model);
        System.out.println("Brand: " + BRAND);
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return BRAND;
    }
}

abstract class AbstractDevice implements PowerControllable {
    protected DeviceDetails details;
    protected boolean powered = false;

    public AbstractDevice(String model) {
        this.details = new DeviceDetails(model);
    }

    public void powerOn() {
        powered = true;
        System.out.println(details.getModel() + " 전원을 켰습니다.");
    }

    public void powerOff() {
        powered = false;
        System.out.println(details.getModel() + " 전원을 껐습니다.");
    }

    public boolean isPoweredOn() {
        return powered;
    }

    public String getModel() {
        return details.getModel();
    }
}

class BasicDevice extends AbstractDevice {
    public BasicDevice(String model) {
        super(model);
        System.out.println("일반 기기가 생성되었습니다 : " + model + ", " + details.getBrand());
    }
}

class SmartDevice extends AbstractDevice implements SmartFeatureActivatable {
    private String smartFeature;

    public SmartDevice(String model, String smartFeature) {
        super(model);
        this.smartFeature = smartFeature;
        System.out.println("스마트 기기가 생성되었습니다 : " + model + " , " + details.getBrand() + ", " + smartFeature);
    }

    @Override
    public void activateSmartFeature() {
        if (!isPoweredOn()) {
            System.out.println(getModel() + " 기기의 전원이 꺼져 있어 고급 기능을 활성화할 수 없습니다.");
            return;
        }
        if (smartFeature == null || smartFeature.isEmpty()) {
            System.out.println(getModel() + " 기기에 고급 기능이 등록되어 있지 않습니다.");
            return;
        }
        System.out.println(getModel() + " 고급 기능을 활성화 시켰습니다: " + smartFeature);
    }
}

class DeviceController {
    private java.util.List<AbstractDevice> deviceList = new java.util.ArrayList<>();

    public void registerDevice(AbstractDevice device) {
        deviceList.add(device);
        System.out.println("컨트롤러에 기기가 등록되었습니다 : " + device.getModel());
    }

    public void powerOnDevice(AbstractDevice device) {
        device.powerOn();
    }

    public void activateSmartFeature(AbstractDevice device) {
        if (device instanceof SmartFeatureActivatable) {
            ((SmartFeatureActivatable) device).activateSmartFeature();
        } else {
            System.out.println(device.getModel() + " 은(는) 고급 기능이 없는 기기입니다.");
        }
    }

    public void powerOffAllDevices() {
        System.out.print("모든 기기 전원을 종료합니다 : ");
        for (AbstractDevice device : deviceList) {
            System.out.print(device.getModel() + ", ");
            device.powerOff();
        }
        System.out.println();
    }
}

public class RuleOfBodome03 {
    public static void main(String[] args) {
        AbstractDevice basicDevice = new BasicDevice("도어 오프너");
        AbstractDevice smartDevice = new SmartDevice("자동 거울", "기분을 인식해 옷을 추천하는 기능");

        DeviceController controller = new DeviceController();

        controller.registerDevice(basicDevice);
        controller.registerDevice(smartDevice);

        controller.powerOnDevice(smartDevice);
        controller.activateSmartFeature(smartDevice);

        controller.powerOnDevice(basicDevice);

        controller.powerOffAllDevices();
    }
}