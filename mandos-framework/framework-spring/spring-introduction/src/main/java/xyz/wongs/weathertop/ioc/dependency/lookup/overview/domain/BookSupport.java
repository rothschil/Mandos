package xyz.wongs.weathertop.ioc.dependency.lookup.overview.domain;

import xyz.wongs.weathertop.ioc.dependency.lookup.overview.annotaion.Mandos;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName BookSupport
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/4/2 14:52
 * @Version 1.0.0
 */
@Mandos
public class BookSupport extends Book{

    private Long price;

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BookSupport{" +
                "price=" + price +
                "} " + super.toString();
    }
}
