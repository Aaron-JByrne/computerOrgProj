package MotherBoard;

import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Firmware;

public class Motherboard {
    private SystemInfo si;
    private HardwareAbstractionLayer hw;
    private ComputerSystem cs;
    private Baseboard baseboard;
    private Firmware fw;

    public Motherboard(SystemInfo si){
        this.si = si;
        this.hw = si.getHardware();
        this.cs = hw.getComputerSystem();
        this.baseboard = cs.getBaseboard();
        this.fw = cs.getFirmware();

    }

    public void displayInfo(){
        System.out.println("=== Motherboard ===");

        System.out.println();

        System.out.println("---Firmware---");
        System.out.printf("Firmware version: %s\n", fw.getVersion());
        System.out.printf("Firmware name: %s\n", fw.getName());
        System.out.printf("Firmware Manufacturer: %s\n", fw.getManufacturer());

        System.out.println();

        System.out.println("---Baseboard---");
        System.out.printf("Baseboard Manufacturer: %s\n", baseboard.getManufacturer());
        System.out.printf("Baseboard Model: %s\n", baseboard.getModel());
        System.out.printf("Baseboard Serial Number: %s\n", baseboard.getSerialNumber());
        System.out.printf("Hardware UUID: %s\n", cs.getHardwareUUID());
        
        System.out.println();
    }
}
