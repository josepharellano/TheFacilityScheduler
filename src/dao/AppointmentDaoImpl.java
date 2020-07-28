package dao;

import com.mysql.jdbc.Connection;
import models.Appointment;
import utilities.Constants;
import utilities.DBConnection;
import utilities.DBQuery;
import utilities.SQLHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class AppointmentDaoImpl implements IDao<Appointment> {

    //Columns used to insert record
    private static final List<String> insertColumns = Arrays.asList("customerId","userId","title","description","location","contact","type","url","start","end",
                                                     "createDate","createdBy","lastUpdate","lastUpdateBy");

    //Columns used to update record
    private static final List<String> updateColumns = Arrays.asList("customerId","userId","title","description","location","contact","type","url","start","end",
                                                                    "lastUpdate","lastUpdateBy");

    @Override
    public Integer insert(Appointment record, String creator) throws SQLException {

        try(Connection conn = DBConnection.startConnection()){

            //Create PreparedStatement
            PreparedStatement ps = DBQuery.setPreparedStatement(conn, SQLHelper.insertRecordSQL(Constants.APPOINTMENT_TABLE,insertColumns));
            //Build Query Statement
            ps.setInt(1,record.getCustomer());
            ps.setInt(2,record.getUser());
            ps.setString(3,record.getTitle());
            ps.setString(4,record.getDesc());
            ps.setString(5,record.getLocation());
            ps.setString(6,record.getContact());
            ps.setString(7,record.getType());
            ps.setString(8,record.getUrl());
            ps.setTimestamp(9,Timestamp.valueOf(record.getStart().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
            ps.setTimestamp(10,Timestamp.valueOf(record.getEnd().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
            ps.setTimestamp(11,Timestamp.valueOf(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
            ps.setString(12,creator);
            ps.setTimestamp(13,Timestamp.valueOf(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
            ps.setString(14,creator);

            //Make Query
            DBQuery.makeQuery();

            //Return the ID of the new appointment record
            ResultSet rs = DBQuery.getPreparedStatement().getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }else{
                return null;
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try(Connection conn = DBConnection.startConnection()) {
            //Deletions will always occur on customerId
            String condition = "appointmentId =" + id;
            //Set PreparedStatement
            PreparedStatement ps = DBQuery.setPreparedStatement(conn, SQLHelper.deleteRecordsSQL(Constants.APPOINTMENT_TABLE,condition));
            //Complete Query
            DBQuery.makeQuery();
        }
    }

    @Override
    public void update(Appointment record, String updatedBy) throws SQLException {
        try(Connection conn = DBConnection.startConnection()){

            //Updates will always occur on customerId
            String condition ="appointmentId=" + record.getId();

            //Set PreparedStatement
            PreparedStatement ps = DBQuery.setPreparedStatement(conn,SQLHelper.updateRecordSQL(Constants.APPOINTMENT_TABLE,updateColumns,condition));

            //Build Query Statement
            ps.setInt(1,record.getCustomer());
            ps.setInt(2,record.getUser());
            ps.setString(3,record.getTitle());
            ps.setString(4,record.getDesc());
            ps.setString(5,record.getLocation());
            ps.setString(6,record.getContact());
            ps.setString(7,record.getType());
            ps.setString(8,record.getUrl());
            ps.setTimestamp(9,Timestamp.valueOf(record.getStart().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
            ps.setTimestamp(10,Timestamp.valueOf(record.getEnd().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
            ps.setTimestamp(11,Timestamp.valueOf(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()));
            ps.setString(12,updatedBy);

            //Make Query
            DBQuery.makeQuery();
        }
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
                            " WHERE userId =" + consultantID;

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
