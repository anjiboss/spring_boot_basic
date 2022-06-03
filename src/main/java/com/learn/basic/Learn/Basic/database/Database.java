package com.learn.basic.Learn.Basic.database;

import com.learn.basic.Learn.Basic.models.Product;
import com.learn.basic.Learn.Basic.repositories.ProductRepository;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;


/*
*   docker run -d --rm --name mysql-spring-boot-tutorial \
        -e MYSQL_ROOT_PASSWORD=123456                    \
        -e MYSQL_USER=anji                               \
        -e MYSQL_PASSWORD=123456                         \
        -e MYSQL_DATABASE=test_db                        \
        -p 3309:3306                                     \
        --volume mysql-spring-boot-basic:/var/lib/mysql  \
        mysql:latest

    mysql -h localhost -P 3309 --protocol=tcp -u anji -p

*  */

@Configuration
public class Database {
    private static final Logger logger =  Logger.getLogger("DB Initialize");
    // Code First : Code run first then add database
    // DB First   : Database already have data => don't have to care about initial data
    @Bean // initialize data for H2 Database
    CommandLineRunner iniDatabase(ProductRepository repo){
        // Logger
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Product productA = new Product( "Macbook Pro");
                Product productB = new Product( "Macbook Air");
                Product productC = new Product( "Samsung Galaxy Fold 3");
                logger.info("Insert Data: " + repo.save(productA));
                logger.info("Insert Data: " + repo.save(productB));
                logger.info("Insert Data: " + repo.save(productC));
            }
        };
    }
}
