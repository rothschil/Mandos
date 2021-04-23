package xyz.wongs.weathertop.dependency.injection;

import xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.Book;

/** {@link Book} And xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain.BookSupport
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName BookHandler
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/23 14:45
 * @Version 1.0.0
 */
public class BookHandler {

    private Book book;

    public BookHandler() {
    }

    public BookHandler(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookHandler{" +
                "book= " + book.toString() +
                '}';
    }
}
