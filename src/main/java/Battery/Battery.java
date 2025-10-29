package Battery;

import oshi.hardware.PowerSource;

import java.sql.ResultSet;
import java.util.List;
import oshi.SystemInfo;

public class Battery {
    private List<PowerSource> powerSources;

    public Battery(SystemInfo si) {
        this.powerSources = si.getHardware().getPowerSources();
    }

    public void printBatteryInformation() {

        for (PowerSource source : this.powerSources) {
            System.out.printf("-------- %s --------%n", source.getDeviceName());
            System.out.println("Battery Life: " + getPercentageFormattedString(source));
            System.out.println(getTimeRemainingString(source));
            System.out.println("Status: " + isChargingString(source));
            System.out.println();
        } 
    }

    private String isChargingString(PowerSource source) {
        if (source.isCharging()) {
            return "Charging";
        }
        return "Discharging";
    } 

    private String getTimeRemainingString(PowerSource source) {
        double time = source.getTimeRemainingInstant();
        if (time < 0) {
            if (source.isCharging()) {
                return "Time remaining: Device is Charging";
            }
            else {
                return "Time remaining: N/A";
            }
        }
        double result = time/60.0;
        return "Time remaining: " + Math.round(result) + "min";

    }

    private String getPercentageFormattedString(PowerSource powerSource) {
        double percentage = powerSource.getRemainingCapacityPercent() * 100;

        // for windows workaround 
        if (percentage == 100.0 && powerSource.getTimeRemainingInstant() < 0 && powerSource.isCharging()) {
            return "[##########] (Fully Charged / Unknown)";
        }

        int rounded = (int) Math.round(percentage);
        StringBuilder bar = new StringBuilder("[");
        int filled = rounded / 10;

        for (int i = 0; i < 10; i++) {
            bar.append(i < filled ? "#" : ".");
        }
        bar.append("] ");

        return String.format("%s %d%%", bar, rounded);    
    }

}
