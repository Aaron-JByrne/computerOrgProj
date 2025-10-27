import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.List;

public class GpuInfo {

    public static void main(String[] args) {

        // Make a SystemInfo object
        SystemInfo systemInfo = new SystemInfo();

        // Get hardware info
        HardwareAbstractionLayer hardware = systemInfo.getHardware();

        // Get a list of graphics cards
        List<GraphicsCard> gpus = hardware.getGraphicsCards();

        for (GraphicsCard gpu : gpus) {
            System.out.println("GPU Name: " + gpu.getName());
            System.out.println("Vendor: " + gpu.getVendor());
            System.out.println("VRAM (MB): " + gpu.getVRam() / (1024 * 1024));
            System.out.println("Device ID: " + gpu.getDeviceId());
            System.out.println("----------------------------------");
        }

        //checking if system has more than one GPU
        if (gpus.size() > 1) {
            System.out.println("You have multiple GPUs!");
        } else {
            System.out.println("You have one GPU.");
        }
    }
}

