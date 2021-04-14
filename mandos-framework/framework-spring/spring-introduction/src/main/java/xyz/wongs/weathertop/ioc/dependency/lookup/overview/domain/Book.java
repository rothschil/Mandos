package xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName Book
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/2 10:22
 * @Version 1.0.0
 */
public class Book {

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static Book initBean(){
        Book book = new Book();
        book.setId(2L);
        book.setName("Thinking In C++");
        return  book;
    }

    private Long id ;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
