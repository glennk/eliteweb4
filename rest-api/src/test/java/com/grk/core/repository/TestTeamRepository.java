package com.grk.core.repository;


import com.grk.core.domain.Team;
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
public class TestTeamRepository {

    @Rule
    public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb(
            "grk-mongo"); // this needs to match 'dbname=' in mongo-config.xml

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @ShouldMatchDataSet(location = "/testdata/team-t1.json")
    public void shouldSaveTeam() {
        teamRepository.save(createTeam());
    }

    @Test
    @UsingDataSet(locations = {"/testdata/team-t1.json"})
    public void shouldFindByTeamName() {
        final List<Team> teams = teamRepository.findByName("Austin Elite 15U");
        assertNotNull(teams);
        assertTrue(teams.size() > 0);
        assertTrue("12".equals(teams.get(0).getId()));
    }

    private Team createTeam() {
        final Team team = new Team();
        team.setId("12");
        team.setName("Austin Elite 15U");
        team.setLevel("Major");
        team.setAge("15U");
        return team;
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
    @ComponentScan(basePackageClasses = {TeamRepository.class})
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
