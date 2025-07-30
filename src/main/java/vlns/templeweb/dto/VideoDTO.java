package vlns.templeweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {
    private Long id;
    private String title;
    private String description;
    private String s3Url;
    private String fileName;

}
