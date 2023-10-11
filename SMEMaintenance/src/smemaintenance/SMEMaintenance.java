package smemaintenance;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String sql = "SELECT sum(actual_price),sum(purchase_quentity) FROM sales where sales_date=?";
        Date newMonth = getProductsalesAmount(1);
        String month = newMonth.toString().substring(0, 8) + "%";
        float sumAmount = 0;
        float sumCount = 0;
        System.out.println(month);
        System.out.println(getlastMonthSales(1));
        System.out.println(getlastMonthpurchase(1));
//        try {
//            PreparedStatement ps = db.getCon().prepareStatement(sql);
//
//            ps.setDate(1, yesterday);
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                sumAmount = rs.getFloat("sum(actual_price)");
//                sumCount = rs.getFloat("sum(purchase_quentity)");
//            }
//            System.out.println("amount " + sumAmount + " count " + sumCount);
//            rs.close();
//            ps.close();
//            db.getCon().close();
//        } catch (SQLException ex) {
//            Logger.getLogger(SMEMaintenance.class.getName()).log(Level.SEVERE, null, ex);
//        }

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

    public static java.sql.Date getProductsalesAmount(int number) {
        LocalDate todayDate = LocalDate.now();
        LocalDate yesterDay = todayDate.minusDays(1);
        LocalDate lastMonth = todayDate.minusMonths(1);
        java.sql.Date newDate = java.sql.Date.valueOf(yesterDay);
        java.sql.Date newMonth = java.sql.Date.valueOf(lastMonth);
        String month = newMonth.toString().substring(0, 8) + "%";
        return newMonth;

    }

    //getlast month
    public static java.sql.Date getLastMonth(int number) {
        LocalDate todayDate = LocalDate.now();
        LocalDate lastMonth = todayDate.minusMonths(number);
        java.sql.Date newMonth = java.sql.Date.valueOf(lastMonth);
        return newMonth;
    }
    //lastMonth total sales month as variable
    public static float getlastMonthSales(int num) {
        DbCon db = new DbCon();
        String sql = "SELECT sum(actual_price),sum(purchase_quentity) FROM sales where sales_date Like?";
        float sumAmount = 0;
        float sumCount = 0;
        java.sql.Date newMonth = getLastMonth(num);
        String month = newMonth.toString().substring(0, 8) + "%";
        try {
            PreparedStatement ps = db.getCon().prepareStatement(sql);

            ps.setString(1, month);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sumAmount = rs.getFloat("sum(actual_price)");
                sumCount = rs.getFloat("sum(purchase_quentity)");
            }
            System.out.println("amount " + sumAmount + " count " + sumCount);
            rs.close();
            ps.close();
            db.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEMaintenance.class.getName()).log(Level.SEVERE, null, ex);
        }
       return sumAmount;
//       return sumCount;
        
    }
    //lastMonth total purchase month as variable
    public static float getlastMonthpurchase(int num) {
        DbCon db = new DbCon();
        String sql = "SELECT sum(total_price),sum(quentity) FROM purchases where purchase_date Like?";
        float sumAmount = 0;
        float sumCount = 0;
        java.sql.Date newMonth = getLastMonth(num);
        String month = newMonth.toString().substring(0, 8) + "%";
        try {
            PreparedStatement ps = db.getCon().prepareStatement(sql);

            ps.setString(1, month);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sumAmount = rs.getFloat("sum(total_price)");
                sumCount = rs.getFloat("sum(quentity)");
            }
            System.out.println("amount " + sumAmount + " count " + sumCount);
            rs.close();
            ps.close();
            db.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEMaintenance.class.getName()).log(Level.SEVERE, null, ex);
        }
       return sumAmount;
//       return sumCount;
        
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
