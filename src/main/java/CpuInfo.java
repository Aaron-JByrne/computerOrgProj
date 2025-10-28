import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;
//import oshi.software.os.OperatingSystem.ProcessStats;

public class CpuInfo {

    public void getCpuInformation() {

        SystemInfo systemInfo = new SystemInfo();

        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor cpu = hardware.getProcessor();
        Sensors sensors = hardware.getSensors();
        OperatingSystem os = systemInfo.getOperatingSystem();

        System.out.println("-------------------------------------------------");
        System.out.println("CPU INFORMATION");
        System.out.println("-------------------------------------------------");
        System.out.println("CPU Model: " + cpu.getProcessorIdentifier().getName());
        System.out.println("Vendor: " + cpu.getProcessorIdentifier().getVendor());
        System.out.println("Physical Cores: " + cpu.getPhysicalProcessorCount());
        System.out.println("Logical Cores: " + cpu.getLogicalProcessorCount());

        //Frequency
        long freq = cpu.getProcessorIdentifier().getVendorFreq();
        if (freq > 0) {
            System.out.printf("Base Frequency: %.2f GHz%n", freq / 1_000_000_000.0);
        }

        long[] currentFreqs = cpu.getCurrentFreq();
        if (currentFreqs != null && currentFreqs.length > 0) {
            double avgFreq = 0;
            for (long f : currentFreqs) avgFreq += f;
            avgFreq /= currentFreqs.length;
            System.out.printf("Average Current Frequency: %.2f GHz%n", avgFreq / 1_000_000_000.0);
        }

        // Get and print CPU load (percentage)
        long[] oldTicks = cpu.getSystemCpuLoadTicks();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double load = cpu.getSystemCpuLoadBetweenTicks(oldTicks) * 100;
        System.out.println("CPU Load: " + String.format("%.2f", load) + "%");
    }
}
