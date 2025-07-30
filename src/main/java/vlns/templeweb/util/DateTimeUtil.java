package vlns.templeweb.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;


public class DateTimeUtil {

    /**
     * Converts "2025-07-29T14:45" to LocalDate (2025-07-29)
     */
    public static LocalDate parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }

        try {
            // Parse as LocalDateTime first, then extract date
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
            return dateTime.toLocalDate();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid datetime format: " + dateTimeString +
                    ". Expected format: yyyy-MM-ddTHH:mm", e);
        }
    }
}
