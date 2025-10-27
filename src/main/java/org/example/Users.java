package org.example;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import oshi.SystemInfo;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OSSession;


public class Users {
    private SystemInfo si;
    private OperatingSystem os;
    private OSSession[] sessions;


    Users(SystemInfo si){
        this.si = si;
        this.os = si.getOperatingSystem();
        this.sessions = os.getSessions().toArray(new OSSession[0]);
    }

    public void displayInfo(){
        System.out.println("---Active Users---");
        for (OSSession session : sessions){
            System.out.printf("User Name: %s\n",session.getUserName());
            //System.out.printf("Host Name: %s\n",session.getHost()); //host name blank on macos (may not be on others)
            String loginTime = Instant.ofEpochMilli(session.getLoginTime()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"));
            System.out.printf("User Log in Time: %s\n", loginTime);
            System.out.printf("User Terminal Device: %s\n", session.getTerminalDevice());

            System.out.println();
        }
    }

}
