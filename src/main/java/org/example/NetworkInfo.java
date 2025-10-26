package org.example;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import java.util.List;

public class NetworkInfo{
    public static void main(String[] args){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();



            List<NetworkIF> networkIFs = hal.getNetworkIFs();

            System.out.println("=== Network Interface Information ===");

            // Loop through network interfaces
            for (NetworkIF net : networkIFs) {
                net.updateAttributes(); // refresh data

                boolean hasIPv4 = net.getIPv4addr() != null && net.getIPv4addr().length > 0;
                boolean hasIPv6 = net.getIPv6addr() != null && net.getIPv6addr().length > 0;


                // Only display active interfaces
                if(hasIPv4 && hasIPv6){
                   System.out.println("Interface name   : " + net.getName());
                   System.out.println("Display Name     : " + net.getDisplayName());
                   System.out.println("MAC address      : " + net.getMacaddr());
                   System.out.println("IPv4 Address     : " + String.join(", ", net.getIPv4addr()));
                   System.out.println("IPv6 Address     : " + String.join(", ", net.getIPv6addr()));
                   System.out.println("Bytes Sent       : " + net.getBytesSent());
                   System.out.println("Bytes Received   : " + net.getBytesRecv());
                   System.out.println("Packets Sent     : " + net.getPacketsSent());
                   System.out.println("Packets Received : " + net.getPacketsRecv());
                   System.out.println("Speed (bps)      : " + net.getSpeed() / 1_000_000 + " Mbps");
                   System.out.println("Interface Alias  : " + net.getIfAlias());
                   System.out.println();

                }

            }
    }
}



