package ec.edu.uce.web.api.application.representation;

public class LinkDTO {
    public String rel;
    public String href;

    public LinkDTO() {
    }

    public LinkDTO(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

}
