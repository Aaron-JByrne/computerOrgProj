package Interface;
import java.util.Scanner;
import Memory.Memory;
import Battery.*;
import oshi.SystemInfo;

/**
 * Interface
 */
public class Interface{

    public Interface() {
         
    }

    private void appLoop() {
        SystemInfo si = new SystemInfo(); 
        while (true) {
            Scanner scanner = new Scanner(System.in);
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
            System.out.print("Enter your choice: "); 
            
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
                }

                case 2 -> {
                    new Memory(si).printMemoryInformation();
                }
                case 3 -> {

                }
                case 4 -> {

                }
                case 5 -> {
                    new Battery(si).printBatteryInformation();
                }
                case 6 -> {

                }
                case 7 -> {

                }
                case 8 -> {

                }
                case 9 -> {

                }
            } 
        }
    }
}
