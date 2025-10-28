package org.example;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import java.util.List;

public class NetworkInfo{

    public void printNetworkInfo(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        List<NetworkIF> networkIFs = hal.getNetworkIFs();

        System.out.println("=== Network Interface Information ===");

        System.out.println();

        // Loop through network interfaces
        for (NetworkIF net : networkIFs) {
            net.updateAttributes(); // refresh data

            String[] ipv4 = net.getIPv4addr();
            String[] ipv6 = net.getIPv6addr();

            boolean hasIPv4 = net.getIPv4addr() != null && net.getIPv4addr().length > 0;
            boolean hasIPv6 = net.getIPv6addr() != null && net.getIPv6addr().length > 0;


            // Only display active interfaces
            if(hasIPv4 || hasIPv6){
                System.out.println(" ---Interface Type : " + getInterfaceTypeName(net) + "---");
                System.out.println("Interface name     : " + net.getName());
                System.out.println("Display Name       : " + net.getDisplayName());
                System.out.println("MAC address        : " + net.getMacaddr());
                if (hasIPv4) {
                    System.out.println("IPv4 Address       : " + String.join(", ", ipv4));
                }
                if (hasIPv6) {
                    System.out.println("IPv6 Address       : " + String.join(", ", ipv6));
                }
                System.out.println("Bytes Sent         : " + net.getBytesSent());
                System.out.println("Bytes Received     : " + net.getBytesRecv());
                System.out.println("Packets Sent       : " + net.getPacketsSent());
                System.out.println("Packets Received   : " + net.getPacketsRecv());
                System.out.println("Speed (bps)        : " + net.getSpeed() / 1_000_000 + " Mbps");
                //System.out.println("Interface Alias    : " + net.getIfAlias());
                System.out.println();
            }


        }
    }
    // convert interface type code to readable names
    private String getInterfaceTypeName(NetworkIF net) {
        int type = net.getIfType();
        String name = net.getName().toLowerCase();
        String display = net.getDisplayName().toLowerCase();

        if (name.contains("bth") || display.contains("bluetooth")) {
            return "Bluetooth";
        } else if (type == 71 || name.contains("wlan") || display.contains("wifi")) {
            return "Wi-Fi";
        } else if (type == 6) {
            return "Ethernet";
        } else if (type == 24) {
            return "Loopback";
        }
        return "Unknown (" + type + ")";
    }
}



