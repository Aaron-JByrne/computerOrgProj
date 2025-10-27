package org.example;

import java.net.InetAddress;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.hardware.*;

import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystem.OSVersionInfo;
import oshi.SystemInfo;

public class Main {
    public static void main(String[] args) {
        OS os = new OS(new SystemInfo());
        os.displayInfo();

        Motherboard mobo = new Motherboard(new SystemInfo());
        mobo.displayInfo();

        Users users = new Users(new SystemInfo());
        users.displayInfo();



        /*
        try {

            // get system name
            String SystemName
                = InetAddress.getLocalHost().getHostName();

            // SystemName stores the name of the system
            System.out.println("System Name : "
                               + SystemName);
        }
        catch (Exception E) {
            System.err.println(E.getMessage());
        }
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        CentralProcessor cpu = si.getHardware().getProcessor();
        GlobalMemory memory = si.getHardware().getMemory();

        System.out.println("OS: " + os);
        System.out.println("Kernel Version: " + os.getVersionInfo().getVersion());
        System.out.println("CPU: " + cpu.getProcessorIdentifier().getName());
        System.out.println("Cores: " + cpu.getLogicalProcessorCount());
        System.out.printf("Memory: %.2f GB total, %.2f GB used%n",
                memory.getTotal() / 1e9,
                (memory.getTotal() - memory.getAvailable()) / 1e9);

        System.out.println();

        System.out.println(si.getHardware().getGraphicsCards());
        System.out.println()
         */
    }
}
