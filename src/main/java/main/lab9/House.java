package main.lab9;

import javax.persistence.*;

@Entity
public class House {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    private int flat_number;
    @Basic
    private Double area;
    @Basic
    private int floor_number;
    @Basic
    private int number_of_rooms;
    @Basic
    private String street_name;
    @Basic
    private int id_type;

    public House(int id, int flat_number, Double area, int floor_number, int number_of_rooms, String street_name, int id_type) {
        this.id = id;
        this.flat_number = flat_number;
        this.area = area;
        this.floor_number = floor_number;
        this.number_of_rooms = number_of_rooms;
        this.street_name = street_name;
        this.id_type = id_type;
    }

    public House() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlat_number() {
        return flat_number;
    }

    public void setFlat_number(int flat_number) {
        this.flat_number = flat_number;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public int getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(int floor_number) {
        this.floor_number = floor_number;
    }

    public int getNumber_of_rooms() {
        return number_of_rooms;
    }

    public void setNumber_of_rooms(int number_of_rooms) {
        this.number_of_rooms = number_of_rooms;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    @Override
    public String toString() {
        return  "Id: " + id +
                ", Номер квартири: " + flat_number +
                ", Площа: " + area +
                " м2, Поверх: " + floor_number +
                ", Кількість кімнат: " + number_of_rooms +
                ", Вулиця: '" + street_name + '\'';
    }

}
