package Interface;
import java.util.Scanner;
import Memory.Memory;
import StorageInfo.StorageInfo;
import NetworkInfo.NetworkInfo;
import Battery.*;
import CPU.CpuInfo;
import GPU.GpuInfo;
import Users.*;
import OS.*;
import MotherBoard.*;
import oshi.SystemInfo;
import PCI.pci;

/**
 * Interface
 */
public class Interface{

    public Interface() {
        appLoop(); 
    }

    private void appLoop() {
        SystemInfo si = new SystemInfo(); 
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("=========== Endor Sysinfo ==========="); 
            System.out.println("1. CPU");
            System.out.println("2. Memory"); 
            System.out.println("3. Storage"); 
            System.out.println("4. Network"); 
            System.out.println("5. Power/Battery"); 
            System.out.println("6. GPU"); 
            System.out.println("7. MotherBoard"); 
            System.out.println("8. Opersating system"); 
            System.out.println("9. Users and sessions"); 
            System.out.println("10. PCI devices"); 
            System.out.print("Enter your choice: "); 
            System.out.println();
            int choice = 0;
            try {
                choice = scanner.nextInt();
            }
            catch (Exception e)
            {
                System.out.println("You entered a value that cannot be casted into an int: " + e.getMessage());
                continue;
            }

            switch (choice) {
                case 1 -> {
                    new CpuInfo(si).getCpuInformation();
                }

                case 2 -> {
                    new Memory(si).printMemoryInformation();
                }
                case 3 -> {
                    new StorageInfo(si).printStorageInfo();
                }
                case 4 -> {
                    new NetworkInfo(si).printNetworkInfo();
                }
                case 5 -> {
                    new Battery(si).printBatteryInformation();
                }
                case 6 -> {
                    new GpuInfo(si).getGpuInformation();
                }

                case 7 -> {
                    new Motherboard(si).displayInfo();
                }
                case 8 -> {
                    new OS(si).displayInfo();
                }
                case 9 -> {
                    new Users(si).displayInfo();
                }
                case 10 -> {
                    new pci().printPCIinfo();
                }
            } 
        }
    }
}
