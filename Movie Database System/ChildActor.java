public class ChildActor extends Performer{
    int age;

    ChildActor(String id,String name, String surname, String country, int age) {
        super(name, surname, country, id);
        this.age=age;
    }
}
