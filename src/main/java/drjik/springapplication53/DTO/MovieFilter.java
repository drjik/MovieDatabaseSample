package drjik.springapplication53.DTO;

import lombok.Data;

@Data
public class MovieFilter {
    private String name;
    private Integer yearFrom;
    private Integer yearTo;
    private Double rating;
    private String actorName;
    private String directorName;
}
