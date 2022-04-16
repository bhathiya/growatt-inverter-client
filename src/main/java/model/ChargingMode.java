package model;

public enum ChargingMode {

    SOLAR_PRIORITY("0"), SOLAR_ONLY("2");

    private final String mode;

    ChargingMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
