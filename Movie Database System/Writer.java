public class Writer extends Artist{
    String writingType;

    Writer(String id,String name, String surname, String country,  String writingType) {
        super(name, surname, country, id);
        this.writingType=writingType;
    }
}
