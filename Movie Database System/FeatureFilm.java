import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FeatureFilm extends Films {
    public String releaseDate;
    public String writers;
    private int budget;
    public String genre;
    double rate;
    static double numberOfRate;

    public FeatureFilm(String id, String title, String language, String directors, int runtime, String country, String cast,
                       String filmGenre, String releaseDate, String writersOfMove,int budget) {
        super(id, title, language, country, directors, cast, runtime);
        this.genre=filmGenre;
        this.releaseDate=releaseDate;
        this.writers=writersOfMove;
        this.budget=budget;
    }



    public int getBudget(){return budget;}
    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String toString(){return "FeatureFilm";}



    public double getRate(){return  rate;}


}
