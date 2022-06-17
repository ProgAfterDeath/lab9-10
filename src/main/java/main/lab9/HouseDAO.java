package main.lab9;

import java.util.Collections;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class HouseDAO{

    private final Connection connection;

    public HouseDAO(Connection connection){
        this.connection = connection;
    }
    //метод отображающий все элементы коллекции извлекаемой из таблицы flats базы данных housebd
    public List<House> findAll(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from flats");
            return getHouseList(resultSet);
        }catch (SQLException e){
            return Collections.emptyList();
        }
    }
    //метод формирующий коллекцию элементов извлекая данные из таблицы flats, базы данных housebd
    public List<House> getHouseList(ResultSet resultSet) throws SQLException{
        List<House> flats = new ArrayList<>();
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            int flat_number = resultSet.getInt("flat_number");
            double area = resultSet.getDouble("area");
            int floor_number = resultSet.getInt("floor_number");
            int number_of_rooms = resultSet.getInt("number_of_rooms");
            String street_name = resultSet.getString("street_name");
            int id_type = resultSet.getInt("id_type");
            flats.add(new House(id,flat_number,area,floor_number,number_of_rooms,street_name, id_type));
        }
        return flats;
    }
    // метод, фильтрующий элементы коллекции согласно количеству комнат
    public List<House> housesWithNumOfRooms(int number_of_rooms_search){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from flats join flat_type f on flats.id_type = f.id_type where number_of_rooms = ?");
            preparedStatement.setInt(1,number_of_rooms_search);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getHouseList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
    // метод, фильтрующий элементы коллекции согласно диапазону в котором находится искомый этаж
    public List<House> housesWithFloorNumInRange(int floor_number_low, int floor_number_high){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from flats join flat_type f on flats.id_type = f.id_type where floor_number >= ? and floor_number <= ?");
            preparedStatement.setInt(1,floor_number_low);
            preparedStatement.setInt(2,floor_number_high);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getHouseList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
    // метод, фильтрующий элементы коллекции согласно минимальному размеру площади
    public List<House> housesWithMinArea(double area_min){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from flats join flat_type f on flats.id_type = f.id_type where area >= ?");
            preparedStatement.setDouble(1,area_min);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getHouseList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    // метод, фильтрующий элементы коллекции согласно количество комнат и диапазону в котором находится искомый этаж
    public List<House> housesWithNumOfRoomsAndFloorNumInRange(int number_of_rooms_search,
                                                              int floor_number_low, int floor_number_high){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from flats join flat_type f on flats.id_type = f.id_type where number_of_rooms = ? and floor_number >= ? and floor_number <=?");
            preparedStatement.setInt(1,number_of_rooms_search);
            preparedStatement.setInt(2,floor_number_low);
            preparedStatement.setInt(3,floor_number_high);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getHouseList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
    // метод, фильтрующий элементы коллекции согласно количеству комнат и минимальному размеру площади
    public List<House> housesWithNumOfRoomsAndMinArea(int number_of_rooms_search,
                                                      double area_min){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from flats join flat_type f on flats.id_type = f.id_type where number_of_rooms = ? and area >= ?");
            preparedStatement.setInt(1, number_of_rooms_search);
            preparedStatement.setDouble(2,area_min);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getHouseList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
    // метод, фильтрующий элементы коллекции согласно диапазону в котором находится искомый этаж и минимальному размеру площади
    public List<House> housesWithFloorNumInRangeAndMinArea(int floor_number_low,
                                                           int floor_number_high,
                                                           double area_min){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from flats join flat_type f on flats.id_type = f.id_type where floor_number >= ? and floor_number <=? and area >= ?");
            preparedStatement.setInt(1, floor_number_low);
            preparedStatement.setInt(2, floor_number_high);
            preparedStatement.setDouble(3,area_min);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getHouseList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
    // метод, создающий карту с перечнем квартир на каждом этаже
    public Map<Integer, List<House>> listOfFlatsOnFloors(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from flats join flat_type f on flats.id_type = f.id_type");
            ResultSet resultSet = preparedStatement.executeQuery();
            return getHouseList(resultSet).stream()
                    .collect(Collectors.groupingBy(House::getFloor_number));
        } catch (SQLException e) {
            return Collections.emptyMap();
        }
    }

}
