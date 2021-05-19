package xyz.wongs.weathertop.module;

import net.hasor.core.ApiBinder;
import net.hasor.core.DimModule;
import net.hasor.db.JdbcModule;
import net.hasor.db.Level;
import net.hasor.spring.SpringModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author <a href="mailto:WCNGS@QQ.COM">Sam</a>
 * @ClassName HasorModule
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 2021/5/13 9:52
 * @Version 1.0.0
 */
@DimModule
@Component
public class HasorModule implements SpringModule {

    @Autowired
    private DataSource dataSource = null;

    @Override
    public void loadModule(ApiBinder apiBinder) throws Throwable {
        apiBinder.installModule(new JdbcModule(Level.Full, this.dataSource));
    }
}
