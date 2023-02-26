import java.util.ArrayList;

public class Author {
    private String id, name, university, department, email;
    private ArrayList<String> articles;

    Author(String id) {
        this.id = id;
        articles = new ArrayList<>();
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void addToArticles(String articleId) {
        if (articles.size() < 5) {
            articles.add(articleId);
        }
    }

    public ArrayList<String> getArticles() {
        return articles;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Author:" +
                id +
                (name != null ? ("\t" + name) : "") +
                (university != null ? ("\t" + university) : "") +
                (department != null ? ("\t" + department) : "") +
                (email != null ? ("\t" + email) : "");
    }
}
