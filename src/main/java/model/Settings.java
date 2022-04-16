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
