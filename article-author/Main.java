import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    private ArrayList<Author> authors;
    private ArrayList<Article> articles;
    private String authorFileName, commandFileName;
    BufferedWriter writer;

    Main(String authorFileName, String commandFileName) throws java.io.IOException {
        this.authorFileName = authorFileName;
        this.commandFileName = commandFileName;
        authors = getAuthorsList();
        articles = new ArrayList<>();
        writer = new BufferedWriter(new FileWriter("output.txt"));
    }

    public void executeCommands() throws java.io.IOException {
        BufferedReader reader;
        String line;
        reader = new BufferedReader(new FileReader(commandFileName));
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(" ");
            if (words[0].equals("read")) {
                updateArticlesList(words[1]);
            } else if (words[0].equals("list")) {
                listAuthorsAndArticles();
            } else if (words[0].equals("completeAll")) {
                completeAll();
            } else if (words[0].equals("sortedAll")) {
                sortArticles();
            } else if (words[0].equals("del")) {
                deleteAuthor(words[1]);
            }
        }
        reader.close();
        writer.close();
    }

    public static void main(String[] args) throws java.io.IOException {
        String authorFileName = args.length > 0 ? args[0] : "author.txt";
        String commandFileName = args.length > 1 ? args[1] : "command.txt";
        Main main = new Main(authorFileName, commandFileName);
        main.executeCommands();
    }

    public ArrayList<Author> getAuthorsList() throws java.io.IOException {
        ArrayList<Author> authors = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(authorFileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(" ");
            Author author = new Author(words[1]);
            if (words.length > 2)
                author.setName(words[2]);
            if (words.length > 3)
                author.setUniversity(words[3]);
            if (words.length > 4)
                author.setDepartment(words[4]);
            if (words.length > 5)
                author.setEmail(words[5]);
            for (int i = 6; i < words.length; i++) {
                author.addToArticles(words[i]);
            }
            authors.add(author);
        }
        reader.close();
        return authors;
    }

    public void updateArticlesList(String fileName) throws java.io.IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(" ");
            Article article = new Article(words[1], words[2], words[3], Integer.parseInt(words[4]));
            articles.add(article);
        }
        reader.close();
    }

    public void listAuthorsAndArticles() throws java.io.IOException {
        writer.write("--------------------------------------List-------------------------------------\n");
        for (int i = 0; i < authors.size(); i++) {
            Author author = authors.get(i);
            writer.write(author.toString() + "\n");
            for (int j = 0; j < author.getArticles().size(); j++) {
                Article article = getArticleById(author.getArticles().get(j));
                writer.write(article.toString() + "\n");
            }
            if (i != authors.size() - 1) {
                writer.write("\n");
            }
        }
        writer.write("--------------------------------------End--------------------------------------\n");
    }

    public Article getArticleById(String id) {
        for (int i = 0; i < articles.size(); i++) {
            if (articles.get(i).getPaperId().equals(id)) {
                return articles.get(i);
            }
        }
        return null;
    }

    public Author getAuthorById(String id) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getId().equals(id)) {
                return authors.get(i);
            }
        }
        return null;
    }

    public void completeAll() throws java.io.IOException {
        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            String authorId = article.getPaperId().substring(0, 3);
            Author author = getAuthorById(authorId);
            if (author != null && !author.getArticles().contains(article.getPaperId())) {
                author.addToArticles(article.getPaperId());
            }
        }
        writer.write("*****************************completeAll Successful****************************\n");
    }

    public void sortArticles() throws java.io.IOException {
        for (int i = 0; i < authors.size(); i++) {
            Collections.sort(authors.get(i).getArticles());
        }
        writer.write("******************************SortedAll Successful*****************************\n");
    }

    public void deleteAuthor(String id) throws java.io.IOException {
        authors.remove(getAuthorById(id));
        writer.write("*********************************del Successful********************************\n");
    }

}
