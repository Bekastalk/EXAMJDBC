package bekks.dao;

import bekks.config.Config;
import bekks.model.Car;
import bekks.model.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    private final Connection connection = Config.getConnection();

    @Override
    public void createTableCar() {

        try {
            Statement statement = connection.createStatement();
            statement = connection.createStatement();
            statement.executeUpdate("create table cars(" +
                    "id serial primary key," +
                    "brand varchar," +
                    "model varchar," +
                    "year int," +
                    "color varchar," +
                    "price int)");
            System.out.println("Table successfully created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createEnumColor() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("create type " +
                    "color as enum ( 'BLUE'," +
                    "    'RED'," +
                    "    'BLACK'," +
                    "    'WHITE'," +
                    "    'BROWN'," +
                    "    'YELLOW')");
            preparedStatement.executeUpdate();
            System.out.println("Enum successfully created");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
        public void saveCar(Car car) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into  cars(brand, model, year, color, price) " +
                                "values (?, ?, ?, ?, ?)");

                preparedStatement.setString(1, car.getBrand());
                preparedStatement.setString(2, car.getModel());
                preparedStatement.setInt(3, car.getYear());
                preparedStatement.setObject(4, car.getColor().name());
                preparedStatement.setInt(5, car.getPrice());

                preparedStatement.executeUpdate();

                System.out.println("Successfully saved!!!");

            } catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }

    @Override
    public Car getCarById(Long id) {
        Car car = new Car();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("""
                    select * from cars where id=?
                    """);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                car.setId(resultSet.getLong("id"));
                car.setBrand(resultSet.getString("brand"));
                car.setModel(resultSet.getString("model"));
                car.setYear(resultSet.getInt("year"));
                car.setColor(Color.valueOf(resultSet.getString("color")));
                car.setPrice(resultSet.getInt("price"));
            } else {
                throw new RuntimeException("Car with id " + id + " not found!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return car;
    }

    @Override
    public String updateCar(Long id, Car car) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    update  cars set brand=?,model=?,year=?,color=?,price=? where id=? 
                    """);
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setString(4, car.getColor().name());
            preparedStatement.setInt(5, car.getPrice());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Successfully updated";
    }

    @Override
    public String deleteCar(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    delete from cars where id=?
                    """);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "With id " + id + " successfully delete!!!";
    }

    @Override
    public List<Car> getCarsBySearch(String brand, String model) {
        Car car = new Car();
        List<Car> cars = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select * from cars where brand=? and model=?
                    """);
            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, model);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cars.add(new Car(
                        resultSet.getLong("id"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("year"),
                        Color.valueOf(resultSet.getString("color")),
                        resultSet.getInt("price")
                ));
            } else {
                throw new RuntimeException("not founded!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }

    @Override
    public List<Car> getCarsByPriceRange(int min, int max) {
        List<Car> cars=new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    select * from cars where price between ? and ?
                    """);
            preparedStatement.setInt(1,min);
            preparedStatement.setInt(2,max);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                cars.add(new Car(
                        resultSet.getLong("id"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getInt("year"),
                        Color.valueOf(resultSet.getString("color")),
                        resultSet.getInt("price")
                ));

            }
            if(cars.isEmpty()){
                throw new RuntimeException("There are no cars within the range of "+min+" to "+max);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }
}


