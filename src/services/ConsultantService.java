package services;

import dao.IDao;
import dao.UserIDaoImpl;
import models.User;

public class ConsultantService extends Service<User> {

    protected ConsultantService() {
        super(new UserIDaoImpl());
    }
}
