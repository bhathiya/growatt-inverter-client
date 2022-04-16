import model.ChargingMode;
import model.OutputMode;
import model.SettingsResponse;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Timer;

public class MonitorTest {

    Monitor monitor = new Monitor();

    @Test
    public void testMonitor() throws InterruptedException {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(monitor, 0, 300000);
        Thread.currentThread().join();
    }

    @Test(testName = "[OUTPUT][Energy Saver] Set to SBU")
    public void testOutputChangeToSBU() throws IOException {
        monitor.updateOutputMode(OutputMode.SBU);
    }

    @Test(testName = "[OUTPUT][Power-cut Safe] Set to SUB")
    public void testOutputChangeToSUB() throws IOException {
        monitor.updateOutputMode(OutputMode.SUB);
    }

    @Test(testName = "[CHARGING][Energy Saver] Set to Solar Only")
    public void testChargingChangeToSolarOnly() throws IOException {
        monitor.updateChargingMode(ChargingMode.SOLAR_ONLY);
    }

    @Test(testName = "[CHARGING][Power-cut Safe] Set to Solar Priority")
    public void testChargingChangeToSolarPriority() throws IOException {
        monitor.updateChargingMode(ChargingMode.SOLAR_PRIORITY);
    }

    @Test
    public void testReadSettings() throws IOException {
        SettingsResponse settings = monitor.readSettings();
        System.out.println(settings);
    }

}