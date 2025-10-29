
package PCI;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class pci {
    
    public void printPCIinfo() {

        System.out.println("========== PCI / Hardware Devices ==========");
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("linux")) {
            listLinuxPci();
        } else if (os.contains("windows")) {
            listWindowsDevices();
        } else if (os.contains("mac")) {
            listMacPci();
        } else {
            System.out.println("Unsupported OS: " + os);
        }

    }

    private static void listLinuxPci() {
        List<String> output = runCommand("lspci", "-nn");
        if (!output.isEmpty()) {
            output.forEach(System.out::println);
            return;
        }

        // fallback if lspci not available
        Path pciDir = Paths.get("/sys/bus/pci/devices");
        if (!Files.exists(pciDir)) {
            System.out.println("No /sys/bus/pci directory found.");
            return;
        }

        try {
            Files.list(pciDir).forEach(dev -> {
                try {
                    String vendor = Files.readString(dev.resolve("vendor")).trim();
                    String device = Files.readString(dev.resolve("device")).trim();
                    System.out.printf("%s | Vendor: %s | Device: %s%n",
                            dev.getFileName(), vendor, device);
                } catch (IOException ignored) {}
            });
        } catch (IOException e) {
            System.err.println("Error reading /sys/bus/pci/devices: " + e.getMessage());
        }
    }

    private static void listWindowsDevices() {
        List<String> output = runCommand("wmic", "path", "Win32_PnPEntity", "get", "Name,DeviceID");
        if (output.isEmpty()) {
            System.out.println("No devices found or WMIC unavailable.");
        } else {
            output.forEach(System.out::println);
        }
    }

    private static void listMacPci() {
        List<String> output = runCommand("system_profiler", "SPPCIDataType");
        if (output.isEmpty()) {
            System.out.println("No PCI devices found or insufficient permissions.");
        } else {
            output.forEach(System.out::println);
        }
    }

    private static List<String> runCommand(String... cmd) {
        List<String> output = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty())
                        output.add(line.trim());
                }
            }
            p.waitFor();
        } catch (Exception e) {
            // command may not exist
        }
        return output;
    }
}
