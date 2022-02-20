package joel.fsms.config.utils;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelpers {
    public String getCurrentTime(){
        return format(LocalDateTime.now());
    }
    public String format(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
    public String format(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String format(Double number) {
        NumberFormat instance = NumberFormat.getInstance();
        instance.setGroupingUsed(true);
        instance.setMaximumFractionDigits(2);
        instance.setMinimumFractionDigits(2);
        return instance.format(number);
    }

    public String format(Float number) {
        return format(number.doubleValue());
    }

    public String format(Integer number) {
        return format(number.doubleValue());
    }
}

