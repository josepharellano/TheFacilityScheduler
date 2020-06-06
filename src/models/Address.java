package models;

import java.sql.Date;
import java.sql.Timestamp;

public class Address extends ModelRecord {
    public Address(int id, String name, Date createDate, String creator, Timestamp lastUpdate, String lastUpdateBy) {
        super(id, name, createDate, creator, lastUpdate, lastUpdateBy);
    }
}
