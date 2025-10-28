package Memory;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.ProcessorCache;
import java.util.List;

public class Memory {
    private final GlobalMemory memory;
    private final CentralProcessor cpu;

    public Memory(SystemInfo si) {
        this.memory = si.getHardware().getMemory();
        this.cpu = si.getHardware().getProcessor();
    }

    public void printMemoryInformation() {
        System.out.println();
        System.out.println();
        System.out.println("========== MEMORY INFORMATION ==========");
    
        // Physical memory
        long total = memory.getTotal();
        long available = memory.getAvailable();
        long used = total - available;
        double usagePercent = (double) used / total * 100;

        System.out.printf("Total Physical Memory : %.2f GB%n", bytesToGB(total));
        System.out.printf("Used Memory           : %.2f GB%n", bytesToGB(used));
        System.out.printf("Available Memory      : %.2f GB%n", bytesToGB(available));
        System.out.printf("Usage                 : %.1f%%%n", usagePercent);

        System.out.println();

        //  Swap Memory
        long swapTotal = memory.getVirtualMemory().getSwapTotal();
        long swapUsed = memory.getVirtualMemory().getSwapUsed();
        System.out.println("---------- SWAP MEMORY ----------");
        System.out.printf("Total Swap : %.2f GB%n", bytesToGB(swapTotal));
        System.out.printf("Used Swap  : %.2f GB%n", bytesToGB(swapUsed));

        System.out.println();

        //  Caches (CPU)
        List<ProcessorCache> caches = cpu.getProcessorCaches();
        if (!caches.isEmpty()) {
            System.out.println("---------- CACHE MEMORY ----------");
            for (ProcessorCache cache : caches) {
                System.out.printf("%s Cache: %d KB (%s, %s)%n",
                        cache.getLevel(),
                        cache.getCacheSize() / 1024,
                        cache.getType(),
                        cache.getAssociativity());
            }
        } else {
            System.out.println("No cache information available.");
        }

        System.out.println();

        //  Virtual Memory Details
        System.out.println("---------- VIRTUAL MEMORY ----------");
        System.out.printf("Virtual Memory Used   : %.2f GB%n",
                bytesToGB(memory.getVirtualMemory().getVirtualInUse()));
        System.out.printf("Virtual Memory Total  : %.2f GB%n",
                bytesToGB(memory.getVirtualMemory().getVirtualMax()));

        System.out.println("=====================================");
    }

    private double bytesToGB(long bytes) {
        return bytes / 1_073_741_824.0; // 1 GiB = 2^30 bytes
    }
}

