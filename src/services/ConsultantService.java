package services;

import dao.IDao;
import dao.UserIDaoImpl;
import models.User;

import java.sql.SQLException;

public class ConsultantService extends Service<User> {

    protected ConsultantService() {

        super(new UserIDaoImpl()) ;
        try {
            this.refreshData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
