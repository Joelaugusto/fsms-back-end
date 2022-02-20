package joel.fsms.config.utils;

import java.time.LocalDateTime;
import java.util.Date;

public class Converter {

    public static Date toDate(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }
}
