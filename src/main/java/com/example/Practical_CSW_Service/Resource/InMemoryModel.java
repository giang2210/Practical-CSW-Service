package com.example.Practical_CSW_Service.Resource;

import com.example.Practical_CSW_Service.entity.Product;
import com.example.Practical_CSW_Service.until.ConnectionHelper;
import com.example.Practical_CSW_Service.until.SqlConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryModel implements ProductModel {
    @Override
    public Product save(Product product) {
        try
        {
            System.out.println("Add");
            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SqlConfig.INSERT_PRODUCT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(2, product.getQuantity());
            preparedStatement.execute();
            return product;
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product update(Product product, int id) {
        try {
            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(SqlConfig.UPDATE_PRODUCT);
            statement.setInt(1,id);
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.setInt(5, id);
            statement.execute();
            return product;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findall() {
        List<Product> list = new ArrayList<>();
        try {

            Connection connection = ConnectionHelper.getConnection();
            String sqlSelect = SqlConfig.LIST_PRODUCT;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelect);
            while (resultSet.next()){
                int Id = resultSet.getInt("id");
                String Name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                Product pro = new Product(Id,Name,price,quantity);
                list.add(pro);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Product findbyid(int id) {
        Product obj = null;
        Connection connection = null;
        try {
            connection = ConnectionHelper.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SqlConfig.DETAIL_PRODUCT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultSet.next()) {
                int Id = resultSet.getInt("id");
                String Name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                obj = new Product(Id,Name, price, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  obj;
    }

}
