package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

public class TimeManager {
    public static ObservableList<LocalTime> getTimes(int i){
        ObservableList<LocalTime> localTimes = FXCollections.observableArrayList();
        LocalDateTime businessLDT = LocalDateTime.of(LocalDate.now(), LocalTime.of(i,0));
        ZoneId estZID = ZoneId.of("America/New_York");
        ZonedDateTime estZDT = ZonedDateTime.of(businessLDT, estZID);
        ZoneId localZID = ZoneId.systemDefault();
        ZonedDateTime localZDT = ZonedDateTime.ofInstant(estZDT.toInstant(), localZID);
        System.out.println(localZDT);
        int midnight = 0;
        for(i = localZDT.getHour(); i < localZDT.getHour() + 14; i++){
//            System.out.println(LocalTime.of(i, 0));
            if(i < 24) {
                localTimes.add(LocalTime.of(i, 0));
            }
            else {
                localTimes.add(LocalTime.of(midnight, 0));
                midnight += 1;
            }
        }
        return localTimes;
    }
}
