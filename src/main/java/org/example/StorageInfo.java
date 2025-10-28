package org.example;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.HWDiskStore;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

class StorageInfo {
    public void printStorageInfo(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        System.out.println("===== STORAGE INFORMATION =====");
        System.out.println();
        System.out.println("--- Disk Information ---");

        // Physical disks
        for(HWDiskStore disk : hal.getDiskStores()){
            System.out.println("Disk Name                : " + disk.getName());
            System.out.println("Disk Size                : " + disk.getSize() + "B");
            System.out.println("Disk Model               : " + disk.getModel());
            System.out.println("Disk Partitions          : " + disk.getPartitions());
            System.out.println("Disk Serial              : " + disk.getSerial());
            System.out.println("Length of Disk Queue     : " + disk.getCurrentQueueLength());
            System.out.println("Number of writes to disk : " + disk.getWrites());
            System.out.println("Number of reads to disk  : " + disk.getReads());


            long ts = disk.getTimeStamp();
            if (ts > 0) {
                java.util.Date date = new java.util.Date(ts);
                System.out.println("Time Disk was updated    : " + date);
            } else {
                System.out.println("Time Disk was updated: unavailable");
            }

        }
        System.out.println();

        System.out.println("--- File Information ---");

        // File Systems
        FileSystem fs = si.getOperatingSystem().getFileSystem();
        for(OSFileStore file : fs.getFileStores()){
            System.out.println("File Name    : " + file.getName());
            System.out.println("Mount        : " + file.getMount());
            System.out.println("File Type    : " + file.getType());
            System.out.println("File UUID    : " + file.getUUID());
            System.out.println("Total Space  : " + file.getTotalSpace() / (1024 * 1024 * 1024) + " GB");
            System.out.println("Usable Space : " + file.getUsableSpace() / (1024 * 1024 * 1024) + " GB");
            System.out.println("Used Space   : "  + ((file.getTotalSpace() / (1024 * 1024 * 1024)) - (file.getUsableSpace() / (1024 * 1024 * 1024)) + " GB"));
        }
    }
}
