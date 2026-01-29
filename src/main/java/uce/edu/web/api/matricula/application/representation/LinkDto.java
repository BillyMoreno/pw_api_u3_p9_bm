package materia.application.representation;

public class LinkDto {
    public String href;
    public String rel;


    public LinkDto(){
        
    }

    public LinkDto (String href, String rel){
        this.href=href;
        this.rel=rel;
    }


}
