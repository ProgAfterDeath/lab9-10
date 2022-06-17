package main.lab9;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Show {
    // метод, отображающий коллекцию в таблице
    public void showHouseList(List<House> flats, TableView<House> houseTable,
                              ListView houseList){
        houseList.setVisible(false);
        houseTable.setVisible(true);
        houseTable.getItems().clear();
        houseTable.setItems(FXCollections.observableList(flats));
    }
    // метод, очищающий все поля из "House Searching"
    public void clearHouseSearch(TextField number_of_rooms_search, TextField floor_number_low,
                                 TextField floor_number_high, TextField area_min){
        number_of_rooms_search.clear();
        floor_number_low.clear();
        floor_number_high.clear();
        area_min.clear();
    }
    // метод, отображающий перечень квартир на каждом этаже
    public void showFloorNumberList(Map<Integer, List<House>> map, TableView<House> houseTable,
                                    ListView houseList){
        houseList.setVisible(true);
        houseTable.setVisible(false);
        houseList.getItems().clear();
        for(Integer key: map.keySet()){
            houseList.getItems().add("Этаж № "+ key + ", Квартиры на этаже: " + map.get(key)
                    .stream().map(House::getFlat_number).sorted().collect(Collectors.toList()));
        }
    }

}
