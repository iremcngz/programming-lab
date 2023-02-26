import java.util.HashMap;
import java.util.LinkedHashMap;

public class Person {
    public String name,surname,country,id;
    public LinkedHashMap<String, Double> rateListOfUser = new LinkedHashMap<>();


    //constructor
    Person(String name,String surname,String country,String id){
        this.name=name;
        this.surname=surname;
        this.country=country;
        this.id=id;
    }
}
