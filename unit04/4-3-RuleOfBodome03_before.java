interface Device {
    void powerOn();
    void powerOff();
    void activateFeature();  
}

class DeviceDetails {
    String model;
    static final String BRAND = "DOMETech";

    DeviceDetails(String model) {
        this.model = model;
    }

    public void displayAndPowerOn() {
        System.out.println("Model: " + model);
        System.out.println("Brand: " + BRAND);
        powerOn();
    }
    
    public void displayAndPowerOff() {
        System.out.println("Model: " + model);
        System.out.println("Brand: " + BRAND);
        powerOff();
    }

    public void powerOn() {
        System.out.println(model + " 가 켜집니다.");
    }
    public void powerOff() {
    	System.out.println(model + " 가 꺼집니다.");
    }
}


class AllPurposeDevice implements Device {
    DeviceDetails details;
    String feature;

    AllPurposeDevice(String model, String feature) {
        this.details = new DeviceDetails(model);
        this.feature = feature;
    }

    @Override
    public void powerOn() {
        details.displayAndPowerOn(); 
    }

    @Override
    public void activateFeature() {
        System.out.println(feature + " 기능을 킵니다.");
    }

	@Override
	public void powerOff() {
		details.displayAndPowerOff();
	}
}

class DeviceController {
    public void controlDevice(Device device) {
        device.powerOn();
        device.activateFeature();
    }
		
		public void powerOffAllDevices(Device... devices) {
				for (Device device : devices) {
	            device.powerOff();
        }
		}
}

public class BiodomeFamily09_Before {
    public static void main(String[] args) {
        Device allPurposeDevice1 = new AllPurposeDevice("스마트 라이트", "자동 빛 색상 조절");
        DeviceController controller = new DeviceController();
        controller.controlDevice(allPurposeDevice1);
				
        Device allPurposeDevice2 = new AllPurposeDevice("냉장고", null);
        controller.controlDevice(allPurposeDevice2);

        controller.powerOffAllDevices(allPurposeDevice1, allPurposeDevice2);
    }
}
