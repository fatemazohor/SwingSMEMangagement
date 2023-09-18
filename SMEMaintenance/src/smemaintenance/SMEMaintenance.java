package smemaintenance;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DbCon;

public class SMEMaintenance {

    public static void main(String[] args) {
        
        DbCon db = new DbCon();
        String sql = "Insert into demoTable(name,age) values(?,?)";
        
        try {
            PreparedStatement ps = null;
            ps.setString(1, "Rakib");
            ps.setInt(2, 25);
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEMaintenance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//      convert sql date to utill date
//        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
//        System.out.println("sql date "+date);
//        java.util.Date conUtilldate = convertsqltoutill(date);
//        System.out.println("con utill date "+conUtilldate);
//        System.out.println("for utill date "+ formatUtilDate(conUtilldate));
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String date1= dateFormat.format(date);
//        System.out.println(date1);
//        System.out.println();
        //convert utill date sql date
//        java.util.Date utilldate1 = new java.util.Date();
//        Date conDate = convertutilltosql(utilldate1);
//        System.out.println("Og utill date " + utilldate1);
//        System.out.println("convertto sql date "+convertutilltosql(utilldate1));
//        System.out.println("for sql date "+formatSqlDate(conDate));
        
        
        // without method
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        String date1 = dateFormat.format(utilldate1);
//        String date2 = dateFormat.format(convertutilltosql(utilldate1));
//        System.out.println("format og utill date " + date1);
//        System.out.println("convertto sql utill date formate ");
//        System.out.println(date2);

    }
    //conver utill date to sql date
    public static Date convertutilltosql(java.util.Date utilldate) {

        if (utilldate != null) {
            return new Date(utilldate.getTime());

        }
        return null;

    }
    //convert sql date to utill date
    public static java.util.Date convertsqltoutill(Date utilldate) {

        if (utilldate != null) {
            return new java.util.Date(utilldate.getTime());

        }
        return null;

    }
    //date format for sql date

    public static String formatSqlDate(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = dateFormat.format(date);
        return formattedDate;

    }
    
    //date format for utill date
    public static String formatUtilDate(java.util.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = dateFormat.format(date);
        return formattedDate;

    }

}
