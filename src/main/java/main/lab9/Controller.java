package main.lab9;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {


    @FXML private ListView houseList;
    public TableView<House> houseTable;
    public TableColumn<House, Integer> idColumn;
    public TableColumn<House, Integer> flatNumberColumn;
    public TableColumn<House, Double> areaColumn;
    public TableColumn<House, Integer> floorNumberColumn;
    public TableColumn<House, Integer> numberOfRoomsColumn;
    public TableColumn<House, String> streetNameColumn;
    public TableColumn<House, Integer> idTypeColumn;

    @FXML private Button see_all;
    @FXML private Button see_floor_numbers;
    @FXML private Button search_house;

    @FXML private TextField number_of_rooms_search;
    @FXML private TextField floor_number_low;
    @FXML private TextField floor_number_high;
    @FXML private TextField area_min;

    private Show show = new Show();
    private List<House> tempFlats = new ArrayList<>();

    public void initialize(){
        try{
            Connection admin = DriverManager.getConnection("jdbc:mariadb://localhost:3306/housebd", "admin", "123");
            HouseDAO houseDAO = new HouseDAO(admin);
            List<House> flats = houseDAO.findAll();

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            flatNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flat_number"));
            areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
            floorNumberColumn.setCellValueFactory(new PropertyValueFactory<>("floor_number"));
            numberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("number_of_rooms"));
            streetNameColumn.setCellValueFactory(new PropertyValueFactory<>("street_name"));
            idTypeColumn.setCellValueFactory(new PropertyValueFactory<>("id_type"));

            show.showHouseList(flats, houseTable, houseList);

            see_all.setOnAction(event->{
                show.showHouseList(houseDAO.findAll(), houseTable, houseList);
                show.clearHouseSearch(number_of_rooms_search, floor_number_low,
                        floor_number_high,area_min);
            });

            search_house.setOnAction(event->{
                tempFlats = fieldsUseCasesForSearching(houseDAO);
                show.showHouseList(tempFlats, houseTable,houseList);
            });

            see_floor_numbers.setOnAction(event->{
                show.showFloorNumberList(houseDAO.listOfFlatsOnFloors(),houseTable,houseList);
            });
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<House> fieldsUseCasesForSearching(HouseDAO houseDAO) {
        List<House> temp = new ArrayList<>();
        if (!number_of_rooms_search.getText().equals("")) {
            temp = houseDAO.housesWithNumOfRooms(
                            Integer.parseInt(number_of_rooms_search.getText()));
        }
        else if (!floor_number_low.getText().equals("") &&
                !floor_number_high.getText().equals("")) {
            temp = houseDAO.housesWithFloorNumInRange(
                            Integer.parseInt(floor_number_low.getText()),
                            Integer.parseInt(floor_number_high.getText()));
        }
        else if (!area_min.getText().equals("")) {
            temp = houseDAO.housesWithMinArea(
                            Double.parseDouble(area_min.getText()));
        }

        if(!number_of_rooms_search.getText().equals("") &&
                !floor_number_low.getText().equals("") &&
                !floor_number_high.getText().equals("")){
            temp = houseDAO.housesWithNumOfRoomsAndFloorNumInRange(
                    Integer.parseInt(number_of_rooms_search.getText()),
                    Integer.parseInt(floor_number_low.getText()),
                    Integer.parseInt(floor_number_high.getText()));
        }
        else if (!number_of_rooms_search.getText().equals("") &&
                !area_min.getText().equals("")){
            temp = houseDAO.housesWithNumOfRoomsAndMinArea(
                    Integer.parseInt(number_of_rooms_search.getText()),
                    Double.parseDouble(area_min.getText()));
        }
        else if(!floor_number_low.getText().equals("") &&
                !floor_number_high.getText().equals("") &&
                !area_min.getText().equals("")){
            temp = houseDAO.housesWithFloorNumInRangeAndMinArea(
                    Integer.parseInt(floor_number_low.getText()),
                    Integer.parseInt(floor_number_high.getText()),
                    Double.parseDouble(area_min.getText()));
        }

        if(number_of_rooms_search.getText().equals("") &&
                floor_number_low.getText().equals("") &&
                floor_number_high.getText().equals("") &&
                area_min.getText().equals("")) {
            temp = houseDAO.findAll();
        }
        return temp;
    }

}
