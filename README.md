# Growatt Client App
Java Client for the Growatt Inverter

This Java application currently supports 3 main actions.

A1) Notify when the grid voltage is 0v (i.e. Power cut)
A2) Change power input priority (SUB / SBU)
A3) Change battery charging priority (Solar Only / Solar Priority)

## A1) Notify when the grid voltage is 0v (i.e. Power cut). 

Currently, the Growatt mobile app does not have any notification mechanism to alert when the grid power goes away. This app uses an ugly hack to achieve that up to some level.  

The application keeps polling (once every 5 minutes) the Growatt cloud and checks if the Grid voltage is 0v. If it is, it fires a configured Gmail webhook which sends a chat message to the user. Since the Datalogger only uploads data once every 5 minutes, we can't get an accuracy higher than 5 minites even if we change the app to run more frequently.   

This will also send a power status chat every day at 7 AM to let you know that the app is running properly. 

**Note**: Please don't use this action unless you really need it, as this introduces considerable traffic to the Growatt cloud. 


## A2) Change power input priority (SUB / SBU) 

This can be used to change power input priority of the inverter automatically, based on a time schedule. 

This is written as a test case so that it can be automated using a Git Action workflow. Have a look at the Github workflow files in this repo. 


## A3) Change battery charging priority (Solar Only / Solar Priority)

This can be used to change the battery charging priority of the inverter automatically.

This is also written as a test case.

# How to run?

1. Configure authentication details in the Configurations.java file. 
2. To run #A1, build the repo and simply run the main method of the Monitor.java class. 
3. To run #A2 manually, you can use the below command. 
  
```
mvn clean install -P change-settings -Dtest="MonitorTest#testOutputChangeToSUB"
```
or 
```
mvn clean install -P change-settings -Dtest="MonitorTest#testOutputChangeToSBU"
```

To automate #A2, create a **private** git clone of this repo (as you're configuring your credentials in step 1). Change cron jobs based on your requirement. 

4. To run #A3 manually, you can use the below command.

```
mvn clean install -P change-settings -Dtest="MonitorTest#testChargingChangeToSolarOnly"
```
or
```
mvn clean install -P change-settings -Dtest="MonitorTest#testChargingChangeToSolarPriority"
```