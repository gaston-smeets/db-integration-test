package com.devgs.demo.dbintegrationtest.repository;

import com.devgs.demo.dbintegrationtest.model.Author;
import com.devgs.demo.dbintegrationtest.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookStoreRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public BookStoreRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Book> findByTitle(String title) {
        String sql = "SELECT * FROM bookstore.book WHERE title = :title";
        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        return namedParameterJdbcTemplate.query(sql, params, new BookMapper());
    }

    public List<Book> findByAuthor(String lastName) {
        String sql = "SELECT * FROM bookstore.book WHERE id IN (" +
                "SELECT book_id from bookstore.book_author WHERE author_id IN (" +
                "SELECT id from bookstore.author WHERE last_name=:lastname" +
                ")" +
                ")";
        Map<String, Object> params = new HashMap<>();
        params.put("lastname", lastName);
        return namedParameterJdbcTemplate.query(sql, params, new BookMapper());
    }

    private static final class BookMapper implements RowMapper<Book> {
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            return book;
        }
    }
}

