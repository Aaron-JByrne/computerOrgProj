package org.example;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.net.InetAddress;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkInfo{

    public void printNetworkInfo(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        List<NetworkIF> networkIFs = hal.getNetworkIFs();

        List<NetworkIF> activeInterfaces = new ArrayList<>();

        System.out.println("=== Network Interface Information ===");

        System.out.println();

        // Loop through network interfaces
        for (NetworkIF net : networkIFs) {
            net.updateAttributes(); // refresh data

            String[] ipv4 = net.getIPv4addr();
            String[] ipv6 = net.getIPv6addr();

            boolean hasIPv4 = ipv4 != null && ipv4.length > 0;
            boolean hasIPv6 = ipv6 != null && ipv6.length > 0;

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

                // Only ping if interface is active
                long totalBytes = net.getBytesSent() + net.getBytesRecv();
                if (totalBytes > 0 && net.getSpeed() > 0) {
                    activeInterfaces.add(net);
                }
            }
        }

        Scanner scanner= new Scanner(System.in);
        System.out.println("Would you like to view ping test? (yes/no)");
        String userInput = scanner.nextLine();
        boolean doPing = userInput.equals("yes") || userInput.equals("y") ;

        if(doPing) {
            System.out.println("\n=== Ping Test Results === \n");

            for (NetworkIF net : activeInterfaces) {
                System.out.println("Interface: " + net.getName());

                String[] ipv4 = net.getIPv4addr();
                String[] ipv6 = net.getIPv6addr();

                if (ipv4 != null) {
                    for (String ip : ipv4) pingAddress(ip, "IPv4");
                }

                if (ipv6 != null) {
                    for (String ip : ipv6) pingAddress(ip, "IPv6");
                }

                System.out.println();
            }
        }else{
                System.out.println("Ping tests skipped.");
            }
            scanner.close();
    }

    private void pingAddress(String ip, String version) {
        try {
            InetAddress inet = InetAddress.getByName(ip);
            long start = System.currentTimeMillis();
            boolean reachable = inet.isReachable(3000); // 3 sec timeout
            long latency = System.currentTimeMillis() - start;

            if (reachable) {
                System.out.println("Ping to " + ip + " (" + version + ") successful: " + latency + " ms");
            } else {
                System.out.println("Ping to " + ip + " (" + version + ") failed");
            }
        } catch (Exception e) {
            System.out.println("Ping test error for " + ip + " (" + version + "): " + e.getMessage());
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



