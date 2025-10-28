// import java.net.InetAddress;
// import oshi.SystemInfo;
// import oshi.hardware.CentralProcessor;
// import oshi.hardware.GlobalMemory;
// import oshi.software.os.OperatingSystem;
import Battery.*;
import Memory.*;
import oshi.SystemInfo;

public class Main {
    public static void main(String[] args) {
        // try {
        //
        //     // get system name
        //     String SystemName
        //         = InetAddress.getLocalHost().getHostName();
        //
        //     // SystemName stores the name of the system
        //     System.out.println("System Name : "
        //                        + SystemName);
        // }
        // catch (Exception E) {
        //     System.err.println(E.getMessage());
        // }
        // SystemInfo si = new SystemInfo();
        // OperatingSystem os = si.getOperatingSystem();
        // CentralProcessor cpu = si.getHardware().getProcessor();
        // GlobalMemory memory = si.getHardware().getMemory();
        //
        // System.out.println("OS: " + os);
        // System.out.println("Kernel Version: " + os.getVersionInfo().getVersion());
        // System.out.println("CPU: " + cpu.getProcessorIdentifier().getName());
        // System.out.println("Cores: " + cpu.getLogicalProcessorCount());
        // System.out.printf("Memory: %.2f GB total, %.2f GB used%n",
        //         memory.getTotal() / 1e9,
        //         (memory.getTotal() - memory.getAvailable()) / 1e9);
        //
        // System.out.println();
        //
        // System.out.println(si.getHardware().getGraphicsCards());
        //

        SystemInfo si = new SystemInfo();
        Battery battery = new Battery(si);
        battery.printBatteryInformation();

        Memory memory = new Memory(si);
        memory.printMemoryInformation();
    }
}
