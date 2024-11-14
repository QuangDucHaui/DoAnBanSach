package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ConnectionPool connectionPool;

    public ProductServiceImpl(ConnectionPool connectionPool) {

        this.connectionPool = connectionPool;
    }

    @Override
    public List<Product> getAllProducts() {
        Connection connection = connectionPool.getConnection();
        List<Product> products = new ArrayList<>();
        try {
            String query = "SELECT * FROM products";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return products;
    }
}