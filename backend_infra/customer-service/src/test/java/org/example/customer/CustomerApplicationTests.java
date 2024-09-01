package org.example.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class CustomerApplicationTests {

    @Autowired
    DataSource source;

    @Test
    public void testConnection() throws SQLException {
        try (Connection connection = source.getConnection()) {
            assert connection != null;
        }
    }

}
