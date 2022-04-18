package model;

import java.util.Arrays;

public class SettingsResponse {
    
    private Settings[] datas;

    public Settings[] getDatas() {
        return datas;
    }

    public void setDatas(Settings[] datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return Arrays.toString(datas);
    }
}
