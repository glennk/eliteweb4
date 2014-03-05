package com.grk.config;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.cloudfoundry.runtime.env.MongoServiceInfo;
import org.cloudfoundry.runtime.service.document.MongoServiceCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@Profile("cloud")
public class MongoConfigCloudProfile {

    private Logger LOG = LoggerFactory.getLogger(MongoConfigCloudProfile.class);

    private String dbName = "mongolab-be5bb";

    @Autowired
    private Environment env;

    @Bean
    public MongoDbFactory mongoDbFactory() {
        LOG.info("mongoDbFactory() - cloud profile [{}]", dbName);

        Assert.notNull(System.getenv("VCAP_APPLICATION"),
                "Spring Cloud Profile expects VCAP_APPLICATION env variable to be set!");

        LOG.debug("VCAP_APPLICATION:: {}", System.getenv("VCAP_APPLICATION"));
        LOG.debug("VCAP_SERVICES:: {}", System.getenv("VCAP_SERVICES"));

        CloudEnvironment cloudEnvironment = new CloudEnvironment();
        MongoServiceInfo serviceInfo = cloudEnvironment.getServiceInfo(dbName
                , MongoServiceInfo.class);
        LOG.info("serviceInfo: {}", serviceInfo.getUri());
        MongoServiceCreator serviceCreator = new MongoServiceCreator();
        return serviceCreator.createService(serviceInfo);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }
}
