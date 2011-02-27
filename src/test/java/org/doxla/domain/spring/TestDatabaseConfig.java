package org.doxla.domain.spring;

import org.doxla.domain.DataCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

@Configuration
public class TestDatabaseConfig {

    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase createDB() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(HSQL)
                                .setName("test_db2")
                                .addScript("sql/schema.sql")
                                .addScript("sql/test-data.sql").build();
        return db;
    }

    @Bean
    public DataCreator dataCreator() {
        return new DataCreator();
    }

}
