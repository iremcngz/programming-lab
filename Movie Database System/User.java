import java.util.HashMap;
public class User extends Person {

    User(String id,String name, String surname, String country ) {
        super(name, surname, country, id);
    }
    // "name of movie": "rate"


    @Override
    public String toString() {
        return "User";
    }
}
