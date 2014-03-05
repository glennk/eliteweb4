package com.grk.core.repository;


import com.grk.core.domain.Player;
import com.lordofthejars.nosqlunit.annotation.ShouldMatchDataSet;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestPlayerRepository {

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb(
            "grk-mongo"); // this needs to match 'dbname=' in mongo-config.xml

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @ShouldMatchDataSet(location = "/testdata/player-p1.json")
    public void shouldSavePlayer() {
        playerRepository.save(createPlayer());
    }

    @Test
    @UsingDataSet(locations = {"/testdata/player-p1.json"})
    public void shouldFindByPlayerName() {
        final List<Player> players = playerRepository.findByLastname("Kronschnabl");
        assertNotNull(players);
        assertTrue(players.size() > 0);
        assertTrue("32".equals(players.get(0).getJerseyNum()));
    }

    private Player createPlayer() {
        final Player player = new Player();
        player.setId("abcd-efgh-1234");
        player.setFirstname("Michael");
        player.setLastname("Kronschnabl");
        player.setEmail("mkron00@gmail.com");
        player.setPhone("512-555-1212");
        player.setJerseyNum("32");
        return player;
    }

//
//    @Configuration
//    @EnableMongoRepositories
//    @ComponentScan
//    static class MongoConfig {
//
//        @Bean
//        public Mongo Fongo() {
//            return new Fongo("test-db").getMongo();
//        }
//
//        @Bean
//        public MongoDbFactory mongoDbFactory() {
//            return Fongo();
//        }
//
//        @Bean
//        public MongoTemplate mongoTemplate() {
//            return new MongoTemplate(mongoDbFactory());
//        }
//    }

//    <bean name="fongo" class="com.github.fakemongo.Fongo">
//    <constructor-arg value="InMemoryMongo" />
//    </bean>
//    <bean id="mongo" factory-bean="fongo" factory-method="getMongo" />
//
//    <mongo:db-factory id="mongoDbFactory" mongo-ref="mongo" />
//
//    <!-- localhost settings for mongo -->
//    <!--<mongo:db-factory id="mongoDbFactory" />-->
//
//    <bean id="mongoTemplate" class="org.springframework.data.mongodb.core.MongoTemplate">
//    <constructor-arg ref="mongoDbFactory"/>
//    </bean>

    @ImportResource("classpath:/mongo-config.xml")
    @Configuration
    @EnableMongoRepositories
    @ComponentScan(basePackageClasses = {PlayerRepository.class})
    static class MongoConfiguration {
    }

//    static class MongoConfiguration extends AbstractMongoConfiguration {
//
//        @Override
//        protected String getDatabaseName() {
//            return "trade-db";
//        }
//
//        @Override
//        public Mongo mongo() {
//            System.out.println("Fongo fakeout");
//            return new Fongo("trade-test").getMongo();
//        }
//
//        @Override
//        protected String getMappingBasePackage() {
//            return "com.grk.core";
//        }
//    }

}
