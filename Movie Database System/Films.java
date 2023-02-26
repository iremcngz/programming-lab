import java.util.ArrayList;

public class Films {
    public String id,title,language,country;
    public int runtime;
    public String directors;
    public String cast;
    //cast = performer
    double rate;
    int numberOfRate;





    //constructor
    public Films(String id,String title,String language,String country,String directors,String cast,int runtime){
        this.id=id;
        this.title=title;
        this.language=language;
        this.country=country;
        this.directors=directors;
        this.runtime=runtime;
        this.cast=cast;
    }


    public String releaseDate;
    public String genre;
    public String writers;
    public String performers;
    public String startDate;
    public String endDate;
    public int numberOfSeasons;
    public int numberOfepisodes;


}
