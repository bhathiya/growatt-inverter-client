/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

import com.google.gson.Gson;
import model.Card;
import model.ChargingMode;
import model.CurrentStatus;
import model.Header;
import model.KeyValue;
import model.OutputMode;
import model.Section;
import model.SettingsResponse;
import model.WebhookBody;
import model.Widget;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class Monitor extends TimerTask {

    private String jSessionId = null;
    private String serverId = null;
    private Date lastFetchedTime = new Date();
    private boolean power = true;

    public static void main(String[] args) {
        Timer timer = new Timer();
        Monitor monitor = new Monitor();
        timer.scheduleAtFixedRate(monitor, 0, 300000);
    }

    public void monitor() throws IOException, NoSuchAlgorithmException {
        if (jSessionId == null || lastFetchedTime == null ||
                Math.abs(new Date().getTime() - lastFetchedTime.getTime()) > 21600000) {
            loginToMobileService();
        }
        getStatus();
    }

    public void loginToMobileService() throws IOException, NoSuchAlgorithmException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server.growatt.com/")
                .build();

        GrowattMobileService service = retrofit.create(GrowattMobileService.class);

        byte[] passwordInBytes = Configurations.password.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] passwordMd5 = md.digest(passwordInBytes);
        Call<ResponseBody> call = service.mobileLogin(Configurations.username, passwordMd5);
        Response<ResponseBody> response = call.execute();
        Headers headers = response.headers();
        List<String> cookies = headers.values("Set-Cookie");
        String J_SESSION_ID = "JSESSIONID=";
        jSessionId = cookies.stream()
                .filter(cookie -> cookie.startsWith(J_SESSION_ID))
                .findAny()
                .orElse("");
        String SERVER_ID = "SERVERID=";
        serverId = cookies.stream()
                .filter(cookie -> cookie.startsWith(SERVER_ID))
                .findAny()
                .orElse("");

        System.out.println(jSessionId + serverId);
        lastFetchedTime = new Date();
    }

    private void getStatus() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server.growatt.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GrowattMobileService service = retrofit.create(GrowattMobileService.class);

        String cookie = jSessionId + serverId;

        Call<CurrentStatus> status = service.getCurrentStatus(Configurations.plantId, Configurations.serialNo, cookie);
        Response<CurrentStatus> response = status.execute();
        assert response.body() != null;
        SimpleDateFormat sd = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        Date date = new Date();
        sd.setTimeZone(TimeZone.getTimeZone("IST"));
        String voltage = response.body().getObj().getvAcInput();
        System.out.println(sd.format(date) + " > " + voltage + "v");
        double vol = Double.parseDouble(voltage);

        LocalTime target = LocalTime.now(ZoneId.of("Asia/Colombo"));
        boolean targetInZone = (target.isAfter(LocalTime.parse("07:00:00"))
                && target.isBefore(LocalTime.parse("07:05:00")));

        if (vol == 0) {
            if (power || targetInZone) {
                alertNoPower();
                power = false;
            }
        } else {
            if (!power || targetInZone) {
                alertPowerAvailable();
                power = true;
            }
        }
    }

    private void alertNoPower() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chat.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GmailWebhookService service = retrofit.create(GmailWebhookService.class);

        Header header = new Header("No Power", "https://e7.pngegg.com/pngimages/950/279/png-clipart" +
                "-electricity-electric-power-electrical-energy-computer-icons-power-socket-angle-electronics.png");
        Card card = new Card(header, new Section[] {});
        WebhookBody webhookBody = new WebhookBody(new Card[] {card});

        String today = new SimpleDateFormat("ddMMyyyy").format(new Date());

        Call<ResponseBody> response = service.alert(webhookBody, today);
        System.out.println(Objects.requireNonNull(response.execute().body()).string());
    }

    private void alertPowerAvailable() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chat.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GmailWebhookService service = retrofit.create(GmailWebhookService.class);

        Header header = new Header("Power Available", "https://previews.123rf.com/images/dmvector/dmvector" +
                "2002/dmvector200200132/140724065-lightning-electric-power-icon-vector-illustration-isolated-on-" +
                "white-background.jpg");
        Card card = new Card(header, new Section[] {});
        WebhookBody webhookBody = new WebhookBody(new Card[] {card});

        String today = new SimpleDateFormat("ddMMyyyy").format(new Date());

        Call<ResponseBody> response = service.alert(webhookBody, today);
        System.out.println(Objects.requireNonNull(response.execute().body()).string());
    }

    private void alertError(String title, String message) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chat.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GmailWebhookService service = retrofit.create(GmailWebhookService.class);

        Header header = new Header(title, "https://thumbs.dreamstime.com/b/vector-" +
                "yellow-hazard-warning-symbol-danger-icon-sign-warn-isolated-white-background-use-web-typography-app" +
                "-road-155959729.jpg");
        KeyValue keyValue = new KeyValue("Error", message);
        Widget widget = new Widget(keyValue);
        Section section = new Section(new Widget[] {widget});
        Card card = new Card(header, new Section[] {section});
        WebhookBody webhookBody = new WebhookBody(new Card[] {card});

        String today = new SimpleDateFormat("ddMMyyyy").format(new Date());

        Call<ResponseBody> response = service.alert(webhookBody, today);
        System.out.println(Objects.requireNonNull(response.execute().body()).string());
    }

    public String loginToWebService() throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server.growatt.com/")
                .build();

        GrowattWebService service = retrofit.create(GrowattWebService.class);

        Call<ResponseBody> call = service.webLogin(Configurations.username, Configurations.password, "");
        Response<ResponseBody> response = call.execute();
        Headers headers = response.headers();
        List<String> cookies = headers.values("Set-Cookie");
        String J_SESSION_ID = "JSESSIONID=";
        String jSessionId = cookies.stream()
                .filter(cookie -> cookie.startsWith(J_SESSION_ID))
                .findAny()
                .orElse("");
        String SERVER_ID = "SERVERID=";
        String serverId = cookies.stream()
                .filter(cookie -> cookie.startsWith(SERVER_ID))
                .findAny()
                .orElse("");

        System.out.println(jSessionId + serverId);

        return jSessionId + serverId;
    }

    public void updateOutputMode(OutputMode outputMode) throws IOException {

        try {

            SettingsResponse settings = readSettings();

            if (settings.getDatas()[0].getOutputConfig().equals(outputMode.getMode())) {
                System.out.println("The setting is already set to " + outputMode + ". Operation aborted.");
                return;
            }

            String cookie = loginToWebService();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://server.growatt.com/")
                    .build();

            GrowattWebService service = retrofit.create(GrowattWebService.class);

            Map<String, String> fieldMap = new HashMap<>();
            fieldMap.put("action", "storageSPF5000Set");
            fieldMap.put("serialNum", Configurations.serialNo);
            fieldMap.put("type", "storage_spf5000_ac_output_source");
            fieldMap.put("param1", outputMode.getMode());
            fieldMap.put("param2", "");
            fieldMap.put("param3", "");
            fieldMap.put("param4", "");

            Call<ResponseBody> call = service.changeSetting(fieldMap, cookie);
            Response<ResponseBody> response = call.execute();
            assert response.body() != null;
            System.out.println(response.body().string());
        } catch (Exception ex) {
            alertError("Error Switching to " + outputMode, ex.getMessage());
        }
    }

    public void updateChargingMode(ChargingMode chargingMode) throws IOException {

        SettingsResponse settings = readSettings();

        if (settings.getDatas()[0].getChargeConfig().equals(chargingMode.getMode())) {
            System.out.println("The setting is already set to " + chargingMode + ". Operation aborted.");
            return;
        }

        String cookie = loginToWebService();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server.growatt.com/")
                .build();

        GrowattWebService service = retrofit.create(GrowattWebService.class);

        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("action", "storageSPF5000Set");
        fieldMap.put("serialNum", Configurations.serialNo);
        fieldMap.put("type", "storage_spf5000_charge_source");
        fieldMap.put("param1", chargingMode.getMode());
        fieldMap.put("param2", "");
        fieldMap.put("param3", "");
        fieldMap.put("param4", "");

        Call<ResponseBody> call = service.changeSetting(fieldMap, cookie);
        Response<ResponseBody> response = call.execute();
        assert response.body() != null;
        System.out.println(response.body().string());
    }

    public SettingsResponse readSettings() throws IOException {

        String cookie = loginToWebService();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://server.growatt.com/")
                .build();

        GrowattWebService service = retrofit.create(GrowattWebService.class);
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("storageSn", "");
        fieldMap.put("plantId", Configurations.plantId);
        fieldMap.put("currPage", "1");

        Call<ResponseBody> call = service.getSettings(fieldMap, cookie);
        Response<ResponseBody> response = call.execute();
        assert response.body() != null;
        String settings = response.body().string();
        System.out.println(settings);

        return new Gson().fromJson(settings, SettingsResponse.class);
    }

    @Override
    public void run() {
        try {
            monitor();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}


