package model;

public class Settings {

    private String outputConfig;
    private String chargeConfig;
    private String utiChargeStart;
    private String utiChargeEnd;
    private String status;
    private String lastUpdateTime;

    public String getOutputConfig() {
        return outputConfig;
    }

    public void setOutputConfig(String outputConfig) {
        this.outputConfig = outputConfig;
    }

    public String getChargeConfig() {
        return chargeConfig;
    }

    public void setChargeConfig(String chargeConfig) {
        this.chargeConfig = chargeConfig;
    }

    public String getUtiChargeStart() {
        return utiChargeStart;
    }

    public void setUtiChargeStart(String utiChargeStart) {
        this.utiChargeStart = utiChargeStart;
    }

    public String getUtiChargeEnd() {
        return utiChargeEnd;
    }

    public void setUtiChargeEnd(String utiChargeEnd) {
        this.utiChargeEnd = utiChargeEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "outputConfig='" + outputConfig + '\'' +
                ", chargeConfig='" + chargeConfig + '\'' +
                ", utiChargeStart='" + utiChargeStart + '\'' +
                ", utiChargeEnd='" + utiChargeEnd + '\'' +
                ", status='" + status + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'';
    }
}
