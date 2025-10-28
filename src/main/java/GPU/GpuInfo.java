package GPU; 

import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;
import oshi.hardware.GlobalMemory;
import java.util.List;

public class GpuInfo {
    
    private SystemInfo systemInfo; 
    public GpuInfo(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    public void getGpuInformation() {

        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        Sensors sensors = hardware.getSensors();
        GlobalMemory memory = hardware.getMemory();

        // List of graphics cards
        List<GraphicsCard> gpus = hardware.getGraphicsCards();

        System.out.println("-------------------------------------------------");
        System.out.println("GPU INFORMATION");
        System.out.println("-------------------------------------------------");

        if (gpus == null || gpus.isEmpty()) {
            System.out.println("No GPU detected.");
            return;
        }

        for (GraphicsCard gpu : gpus) {
            String friendlyVendor = getGpuVendorName(gpu.getVendor());

            System.out.println("GPU Name: " + gpu.getName());
            System.out.println("Vendor: " + friendlyVendor);
            System.out.println("VRAM (MB): " + gpu.getVRam() / (1024 * 1024));

            // VRAM-to-RAM ratio
            long systemMemory = memory.getTotal();
            double ratio = (double) gpu.getVRam() / systemMemory * 100;
            System.out.printf("VRAM-to-RAM Ratio: %.2f%%%n", ratio);

            System.out.println("Device ID: " + gpu.getDeviceId());
            System.out.println("Version Info: " + gpu.getVersionInfo());
            System.out.println("----------------------------------");
        }

        // Multiple GPUs
        if (gpus.size() > 1) {
            System.out.println("You have multiple GPUs!");
        } else {
            System.out.println("You have one GPU.");
        }

        System.out.println("-------------------------------------------------");

        // Fan Speeds
        int[] fanSpeeds = sensors.getFanSpeeds();
        System.out.println("FAN SPEEDS");
        if (fanSpeeds == null || fanSpeeds.length == 0) {
            System.out.println("No fan speed data available (some systems don't expose GPU fans).");
        } else {
            for (int i = 0; i < fanSpeeds.length; i++) {
                System.out.printf("Fan %d Speed: %d RPM%n", i + 1, fanSpeeds[i]);
            }

            // Average fan speed
            double avg = 0;
            for (int rpm : fanSpeeds) avg += rpm;
            avg /= fanSpeeds.length;
            System.out.printf("Average Fan Speed: %.0f RPM%n", avg);
        }

        // GPU Temperature (may reflect CPU sensor)
        double temperature = sensors.getCpuTemperature();
        System.out.printf("GPU Temperature: %.1f °C%n", temperature);

        // Simulated live GPU usage graph (approximation)
        showLiveGpuGraph(gpus);
    }

    // Vendor mapping helper
    private String getGpuVendorName(String vendor) {
        vendor = vendor.toLowerCase();
        if (vendor.contains("nvidia")) return "NVIDIA";
        if (vendor.contains("amd") || vendor.contains("ati")) return "AMD";
        if (vendor.contains("intel")) return "Intel";
        return "Other";
    }

    // Simulated GPU usage graph (OSHI does not provide real GPU load)
    private void showLiveGpuGraph(List<GraphicsCard> gpus) {
        System.out.println("\nSimulated Live GPU Usage Graph (each # ≈ 2% load):");
        System.out.println("Press ENTER to stop the graph.\n");

        try {
            java.util.Scanner scanner = new java.util.Scanner(System.in);

            while (true) {
                // Check if user pressed Enter
                if (System.in.available() > 0) {
                    scanner.nextLine(); // consume Enter key
                    break; // stop the graph
                }

                Thread.sleep(1000); // wait 1 second

                for (GraphicsCard gpu : gpus) {
                    // simulate GPU usage randomly (since OSHI doesn’t provide GPU load)
                    double load = Math.random() * 100;
                    int bars = (int) (load / 2);
                    String graph = "#".repeat(Math.max(0, bars));

                    System.out.printf("%s GPU Load: %5.2f%% [%s]%n", gpu.getName(), load, graph);
                }

                System.out.println("----------------------------------");
            }

            System.out.println("\nGPU Graph stopped by user.");

        } catch (Exception e) {
            System.out.println("Error running GPU live graph: " + e.getMessage());
        }
    }

}
