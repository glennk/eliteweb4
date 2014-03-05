package com.grk.config;

import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories
@Profile("default")
public class MongoConfigDefaultProfile {

    private Logger LOG = LoggerFactory.getLogger(MongoConfigDefaultProfile.class);

    private String dbName = "eliteweb3";

    @Bean
    public MongoDbFactory mongoDbFactory() {
        LOG.info("mongoDbFactory() - default profile [dbName: {}]", dbName);

        try {
            MongoClient mongoClient = new MongoClient();
            mongoClient.setWriteConcern(WriteConcern.SAFE);
            return new SimpleMongoDbFactory(mongoClient, dbName);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }
}
