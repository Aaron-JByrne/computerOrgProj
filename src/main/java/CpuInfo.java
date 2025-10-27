import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class CpuInfo {

    public static void main(String[] args) {

        SystemInfo systemInfo = new SystemInfo();

        // Get hardware info
        HardwareAbstractionLayer hardware = systemInfo.getHardware();

        // Get CPU info
        CentralProcessor cpu = hardware.getProcessor();

        //System.out.println("CPU Model: " + cpu.getProcessorIdentifier().getName());
        //System.out.println("Vendor: " + cpu.getProcessorIdentifier().getVendor());
        //System.out.println("Physical Cores: " + cpu.getPhysicalProcessorCount());
        //System.out.println("Logical Cores: " + cpu.getLogicalProcessorCount());

        // Get and print CPU load (percentage)
        long[] oldTicks = cpu.getSystemCpuLoadTicks();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double load = cpu.getSystemCpuLoadBetweenTicks(oldTicks) * 100;
        //System.out.println("CPU Load: " + String.format("%.2f", load) + "%");
    }
}

