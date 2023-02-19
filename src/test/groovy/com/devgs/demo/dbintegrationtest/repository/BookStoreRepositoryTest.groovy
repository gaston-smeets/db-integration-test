package com.devgs.demo.dbintegrationtest.repository

import com.devgs.demo.dbintegrationtest.model.Book
import lombok.extern.slf4j.Slf4j
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import spock.lang.Specification

@Slf4j
@SpringBootTest
@ActiveProfiles("itest")
class BookStoreRepositoryTest extends Specification {

    @Autowired
    BookStoreRepository bookStoreRepository;

    private static final Logger LOG = LogManager.getLogger(BookStoreRepositoryTest.class);

    static PostgreSQLContainer postgres

    static {
        postgres = (PostgreSQLContainer) new PostgreSQLContainer(PostgreSQLContainer.IMAGE)
                .withDatabaseName("bookstore")
                .withUsername("devgs")
                .withPassword("secret")
                .withExposedPorts(5432)
        postgres.start()
        def jdbcUrl = postgres.jdbcUrl
        LOG.info("Setting db url to '{}'", jdbcUrl)
        System.setProperty("spring.datasource.url", jdbcUrl)
    }

    def 'When the test runs, then the database is loaded'() {
        given: 'Initial data has loaded'

        when: 'Getting the books of Erich Gamma'
        def books = bookStoreRepository.findByAuthor("Gamma")

        then: 'Design Patterns is returned'
        books.size() == 1
        books[0].title == 'Design Patterns'
    }
}
