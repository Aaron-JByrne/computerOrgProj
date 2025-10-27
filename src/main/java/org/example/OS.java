package org.example;

import java.time.Instant;
import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSSession;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OSProcess;

public class OS{
    private SystemInfo si;
    private OperatingSystem os;
    private OperatingSystem.OSVersionInfo osVersionInfo;
    private HardwareAbstractionLayer hw;
    private Baseboard baseboard;
    private ComputerSystem computerSystem;
    //private OSProcess osProcess;

    OS(SystemInfo si) {
        this.si = si;
        this.os = si.getOperatingSystem();
        this.osVersionInfo = os.getVersionInfo();
        this.hw = si.getHardware();
        this.computerSystem = hw.getComputerSystem();
        this.baseboard = computerSystem.getBaseboard();
        //this.osProcess = os.getCurrentProcess();

    }
    public void displayInfo() {
        System.out.println("---Operating System---");
        System.out.printf("OS Name: %s\n", os.getFamily() );
        System.out.printf("OS Manufacturer: %s\n", os.getManufacturer() );
        System.out.printf("OS Version: %s\n", osVersionInfo.getVersion() );
        System.out.printf("OS Build number: %s\n", osVersionInfo.getBuildNumber() );
        System.out.printf("Architecture: %s\n", System.getProperty("os.arch"));
        System.out.printf("Bitness: %d\n", os.getBitness() );
        System.out.printf("Uptime: %s\n", timeformat(os.getSystemUptime())); // hhh:mm:ss
        System.out.printf("Process Count: %d\n", os.getProcessCount());

        System.out.println();
    }
    public String timeformat(long inSeconds){
        int hours = (int) (inSeconds/3600);
        int minutes = (int) (inSeconds%3600/60);
        int seconds = (int) (inSeconds%60);
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timeString;
    }

}
