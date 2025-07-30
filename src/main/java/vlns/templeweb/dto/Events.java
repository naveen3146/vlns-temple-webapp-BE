package vlns.templeweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Events {
    private Long   eventId;
    private String  templeName;
    private String  title;
    private String  description;
    private String  date;
    private String  location;
    private String  contact;
    private String  imageUrl;
}
