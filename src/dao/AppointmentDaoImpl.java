package dao;

import com.mysql.jdbc.Connection;
import models.Appointment;
import utilities.Constants;
import utilities.DBConnection;
import utilities.DBQuery;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class AppointmentDaoImpl implements IDao<Appointment> {

    @Override
    public Integer insert(Appointment record, String creator) throws SQLException {
        return null;
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public void update(Appointment record, String updatedBy) throws SQLException {

    }

    @Override
    public Appointment select(String name) throws SQLException {
        return null;
    }

    @Override
    public HashMap<Integer,Appointment> selectAll() throws SQLException {
        HashMap<Integer,Appointment> appointments = new HashMap<Integer, Appointment>();

        String sqlQuery = "SELECT appointmentId, customerId,userId,title,description,location,contact,type,url,start,end FROM " + Constants.APPOINTMENT_TABLE;

        try(Connection conn = DBConnection.startConnection()){

            PreparedStatement ps = DBQuery.setPreparedStatement(conn,sqlQuery);
            DBQuery.makeQuery();

            ResultSet rs = DBQuery.getResultSet();

            while(rs.next()){
                Appointment appointment = getAppointmentFromResultSet(rs);
                appointments.put(appointment.getId(),appointment);
            }
        }
        return appointments;
    }

    public List<Appointment> selectByConsultant(int consultantID) throws SQLException{

        List<Appointment> consultantAppointments = new ArrayList<>();

        String sqlQuery = "SELECT appointmentId,customerId,userId,title,description,location,contact,type,url,start,end FROM " + Constants.APPOINTMENT_TABLE +
                            "WHERE userId =" + consultantID;

        try(Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = DBQuery.setPreparedStatement(conn,sqlQuery);
            DBQuery.makeQuery();

            ResultSet rs = DBQuery.getResultSet();

            while(rs.next()){
                consultantAppointments.add(getAppointmentFromResultSet(rs));
            }
            return consultantAppointments;
        }

    }

    private Appointment getAppointmentFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);  //Appointment Id
        int customerId = rs.getInt(2); // Customer ID
        int userId = rs.getInt(3); //User ID
        String title = rs.getString(4); //Title of Appointment
        String desc = rs.getString(5); //Description of Appointment
        String location = rs.getString(6); // Location of Appointment
        String contact = rs.getString(7); //Contact name for appointment
        String type = rs.getString(8); //Appointment Type
        String url = rs.getString(9);// Link to online meeting room
        String start = rs.getString(10);
        String end = rs.getString(11); //End time of meeting

        //Create a LocalDateTime from String -> ZonedDateTime at UTC and adjust ZonedDateTime to System Zone
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        ZonedDateTime zdtStart = LocalDateTime.parse(start,df).atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime zdtEnd = LocalDateTime.parse(end,df).atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());

        return new Appointment(id,userId,customerId,title,desc,contact,type,location,zdtStart,zdtEnd,url);
    }
}
