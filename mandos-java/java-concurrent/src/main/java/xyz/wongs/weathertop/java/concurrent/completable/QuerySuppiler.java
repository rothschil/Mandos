package xyz.wongs.weathertop.java.concurrent.completable;

import java.util.function.Supplier;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName QuerySuppiler
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/4/25 8:56
 * @Version 1.0.0
 */
public class QuerySuppiler implements Supplier<String> {

    private Integer id;
    private String type;
    private QueryUtils queryUtils;

    public QuerySuppiler() {
    }

    public QuerySuppiler(Integer id, String type, QueryUtils queryUtils) {
        this.id = id;
        this.type = type;
        this.queryUtils = queryUtils;
    }

    @Override
    public String get() {
        if("home".equals(type)){
            return queryUtils.queryHome(id);
        }else if ("job".equals(type)){
            return queryUtils.queryJob(id);
        }else if ("car".equals(type)){
            return queryUtils.queryCar(id);
        }
        return null;
    }


}
