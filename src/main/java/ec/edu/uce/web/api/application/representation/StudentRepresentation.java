package ec.edu.uce.web.api.application.representation;

import java.time.LocalDateTime;
import java.util.List;

public class StudentRepresentation {

    public Long id;
    public String name;
    public String lastName;
    public String email;
    public LocalDateTime birthDay;
    public String province;
    public String gender;
    public List<LinkDTO> links;

}
