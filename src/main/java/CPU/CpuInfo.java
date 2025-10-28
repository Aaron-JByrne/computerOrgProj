package CPU; 

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;
import java.util.List;

public class CpuInfo {
    
    private SystemInfo systemInfo ;

    public CpuInfo(SystemInfo si) {
        this.systemInfo= si;
    }

    public void getCpuInformation() {
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

        // Frequency
        long baseFreq = cpu.getProcessorIdentifier().getVendorFreq();
        if (baseFreq > 0) {
            System.out.printf("Base Frequency: %.2f GHz%n", baseFreq / 1_000_000_000.0);
        }

        long[] currentFreqs = cpu.getCurrentFreq();
        double avgFreq = 0;
        if (currentFreqs != null && currentFreqs.length > 0) {
            for (long f : currentFreqs) avgFreq += f;
            avgFreq /= currentFreqs.length;
            System.out.printf("Average Current Frequency: %.2f GHz%n", avgFreq / 1_000_000_000.0);
        }

        // Cache sizes
        List<CentralProcessor.ProcessorCache> caches = cpu.getProcessorCaches();
        if (caches != null && !caches.isEmpty()) {
            System.out.println("Cache Information:");
            for (CentralProcessor.ProcessorCache cache : caches) {
                System.out.printf("  Level %d Cache: %d KB (%s)%n",
                        cache.getLevel(),
                        cache.getCacheSize() / 1024,
                        cache.getType());
            }
        } else {
            System.out.println("No cache information available.");
        }

        // Load per core
        double[] loadPerCore = cpu.getProcessorCpuLoad(1000);
        System.out.println("Load per core:");
        for (int i = 0; i < loadPerCore.length; i++) {
            System.out.printf("  Core %d: %.2f%%%n", i + 1, loadPerCore[i] * 100);
        }

        // Average load
        double avgLoad = cpu.getSystemCpuLoad(1000) * 100;
        System.out.printf("Average CPU Load: %.2f%%%n", avgLoad);

        // Temperature
        double temp = sensors.getCpuTemperature();
        System.out.printf("Temperature: %.1f °C%n", temp);

        // Efficiency ratios
        double efficiencyTemp = avgLoad / (temp > 0 ? temp : 1);
        double efficiencyFreq = avgLoad / (avgFreq > 0 ? avgFreq / 1_000_000_000.0 : 1);
        System.out.printf("Efficiency Ratio (Load/Temp): %.2f%n", efficiencyTemp);
        System.out.printf("Efficiency Ratio (Load/GHz): %.2f%n", efficiencyFreq);

        // Uptime
        long uptime = os.getSystemUptime();
        System.out.printf("System Uptime: %d hours %d minutes%n",
                uptime / 3600, (uptime % 3600) / 60);

        // Context switches and interrupts
        long contextSwitches = cpu.getContextSwitches();
        long interrupts = cpu.getInterrupts();
        System.out.println("Context Switches: " + contextSwitches);
        System.out.println("Interrupts: " + interrupts);

        System.out.println("-------------------------------------------------");

        // Live CPU usage graph
        showLiveCpuGraph(cpu);
    }

    // Live CPU usage graph method
    private void showLiveCpuGraph(CentralProcessor cpu) {
        System.out.println("\nLive CPU Usage Graph (each # ≈ 2% load):");
        System.out.println("Press ENTER to stop the graph.\n");

        try {
            long[] prevTicks = cpu.getSystemCpuLoadTicks();

            java.util.Scanner scanner = new java.util.Scanner(System.in);

            while (true) {
                // Check if user pressed Enter
                if (System.in.available() > 0) {
                    scanner.nextLine(); // consume the Enter key
                    break; // exit the loop
                }

                Thread.sleep(1000);
                double load = cpu.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
                prevTicks = cpu.getSystemCpuLoadTicks();
                int bars = (int) (load / 2);
                String graph = "#".repeat(Math.max(0, bars));
                System.out.printf("CPU Load: %5.2f%% [%s]%n", load, graph);
            }

            System.out.println("\nGraph stopped by user.");
        } catch (Exception e) {
            System.out.println("Error running live graph: " + e.getMessage());
        }
    }

}
