/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.DbCon;

public class SMEDashboard extends javax.swing.JFrame {

    //database variable
    DbCon dbCon = new DbCon();
    String sql;
    PreparedStatement ps;
    ResultSet rs;
    LocalDate today = LocalDate.now();
    java.sql.Date sqltoday = java.sql.Date.valueOf(today);

    /**
     * Creates new form SMEDashboard
     */
    public SMEDashboard() {
        initComponents();
        init();
    }

    private void init() {
        getAllProducts();
        setProductnametoPurchaseCombo(comboPurchaseProductName);

        getAllPurchaseProduct(jTablePurchaseProduct);
        getAllProductsForSell();
        //Dashboard methods call.
        getTodaySales();
        getTotalSales();
        getMonthlySales();
        getTodayPurchase();
        getTotalPurchase();
        getMonthlyPurchase();
        getCartTable();
        getAllcustomers();
    }
    
    private void setpaymentIdOnSalesTable(){
    
    
    
    }

    private void setDeliveryCode(){
    
        String deliverycode = txtbillDeliveryId.getText();
        sql="insert into delivery_charge(delivery_code) values(?)";
        try {
            ps=dbCon.getCon().prepareStatement(sql);
            ps.setString(1, deliverycode);
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    //show all customer data
    private void getAllcustomers() {
        String[] columna = {"idcustomers", "name", "cell", "district", "address", "created_date"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columna);
        jtableCustomerInfo.setModel(model);
        sql = "Select * from customers";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idcustomers");
                String name = rs.getString("name");
                String cell = rs.getString("cell");
                String district = rs.getString("district");
                String address = rs.getString("address");
                Date created_date = rs.getDate("created_date");
                model.addRow(new Object[]{id, name, cell, district, address, created_date});
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//show products table from database

    private void getAllProductsForSell() {
        String[] columna = {"Product Name", "Quantity", "Price"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columna);
        tableBillInfoProductDetails.setModel(model);
        sql = "SELECT p.name, s.quantity, p.unit_price  FROM smemanagement.product_stock s inner join smemanagement.products p on s.idproduct_stock= p.idproducts;";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("p.name");
                float quentity = rs.getFloat("s.quantity");
                float unitPrice = rs.getFloat("p.unit_price");

                model.addRow(new Object[]{name, quentity, unitPrice});
            }

            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void getAllProducts() {

        String[] columnNames = {"ProduntId", "Name", "Quentity", "Unit_price", "Buy_price", "Entry_date"};
        sql = "select * from smemanagement.products";

        DefaultTableModel producttableModel = new DefaultTableModel();
        producttableModel.setColumnIdentifiers(columnNames);
        tableInvUpdateProdunt.setModel(producttableModel);

        try {
            ps = dbCon.getCon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int produntId = rs.getInt("idproducts");
                String name = rs.getString("name");
                int quentity = rs.getInt("quentity");
                float unitprice = rs.getFloat("unit_price");
                float purchasePrice = rs.getFloat("purchase_price");
                Date entryDate = rs.getDate("entry_date");
                //format sql date to string date , so can update date. 
//                String date = formatSqlDate(entryDate);

                producttableModel.addRow(new Object[]{produntId, name, quentity, unitprice, purchasePrice, entryDate});

            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//show purchases table data

    public void getAllPurchaseProduct(javax.swing.JTable tableName) {
        String[] columnNames = {"PurchaseId", "Name", "Quentity", "Unit_price", "Total Price", "Purchase_date"};
//        String[][] data=new String[1][columnNames.length];
//        data[0][1]=ts
//        DefaultTableModel model= new DefaultTableModel(data, columnNames);

        DefaultTableModel purchasetableModel = new DefaultTableModel();
        purchasetableModel.setColumnIdentifiers(columnNames);

        tableName.setModel(purchasetableModel);

        sql = "select * from smemanagement.purchases";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idpurchases");
                String name = rs.getString("product_name");
                int quentity = rs.getInt("quentity");
                float unitPrice = rs.getFloat("unit_price");
                float totalPrice = rs.getFloat("total_price");
                Date purchaseDate = rs.getDate("purchase_date");

                purchasetableModel.addRow(new Object[]{id, name, quentity, unitPrice, totalPrice, purchaseDate});
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();

        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//set combo product name in product and purchase

    public void setProductnametoPurchaseCombo(javax.swing.JComboBox<String> comboBox) {

        comboBox.removeAllItems();
        comboBox.addItem("--Select Product Name--");

        sql = "select name from products";

        try {
            ps = dbCon.getCon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String productName = rs.getString("name");
                comboBox.addItem(productName);
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();

        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//stock table data update

    public void addProductToStock() {
        sql = "insert into product_stock(product_name,quantity)values(?,?)";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setString(1, txtinvUpdateProName.getText());
            ps.setFloat(2, 0);
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateProductToStock() {

        float quantity = Float.parseFloat(spanProductPurchaseQuentity.getValue().toString());
        sql = "update product_stock set quantity=quantity+? where product_name=?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setFloat(1, quantity);
            ps.setString(2, comboPurchaseProductName.getSelectedItem().toString());
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void subtractProductFromStock() {
        float quantity = Float.parseFloat(spanbillquentity.getValue().toString());
        sql = "update product_stock set quantity=quantity-? where product_name=?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setFloat(1, quantity);
            ps.setString(2, txtbillProductName.getText());
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    date format method
    //convert utill date to sql date

    public static Date convertutilltosql(java.util.Date utilldate) {

        if (utilldate != null) {
            return new Date(utilldate.getTime());

        }
        return null;

    }

    //date format for sql date
    public static String formatSqlDate(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = dateFormat.format(date);
        return formattedDate;

    }

    //convert sql date to utill date
    public static java.util.Date convertsqltoutill(Date sqldate) {

        if (sqldate != null) {
            return new java.util.Date(sqldate.getTime());

        }
        return null;

    }

    //date format for utill date, util to String date
    public static String formatUtilDate(java.util.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);
        return formattedDate;

    }

    //date format for utill date, String date to utill date
    public static java.util.Date formatStringdateToUtilDate(String dateString) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return dateformat.parse(dateString);
        } catch (ParseException ex) {
            System.out.println("Date format wrong");
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    private void setInvPurchaeProductReset() {
        txtProductPurchaseProductId.setText(null);
        comboPurchaseProductName.setSelectedIndex(0);
        txtProductPurchaseUnitPrice.setText(null);
        spanProductPurchaseQuentity.setValue(0);
        jdatePurchaseProduct.setDate(null);

    }

    private void setbtnInvUpdateReset() {
        txtinvUpdateProId.setText("Auto generated");
        txtinvUpdateProName.setText(null);
        txtinvUpdateUnitPrice.setText(null);
        jdateinvUpdateCreateDate.setDate(null);
        txtinvUpdatebuyPrice.setText(null);

    }

    private void setbtnillInfoReset() {

        txtbillProductName.setText(null);
        txtbillunitPrice.setText(null);
        spanbillquentity.setValue(0);
        txtbillTotalPrice.setText(null);
        txtbillDiscount.setText(null);
        txtbillFinalPrice.setText(null);

    }

    private void setbtnCustomerReset() {
        txtCustomerInfoId.setText(null);
        txtCustomerInfoName.setText(null);
        txtCustomerInfoCell.setText(null);
        txtCustomerInfoAddress.setText(null);
        comboCustomerDistrict.setSelectedIndex(0);
        jDateCustomerInfo.setDate(null);

    }

    private float getbillTotalPrice() {
        float unitPrice = Float.parseFloat(txtbillunitPrice.getText().trim().toString());
        float quentity = Float.parseFloat(spanbillquentity.getValue().toString());

        float totalPrice = (unitPrice * quentity);

        return totalPrice;
    }

    private float getbillAutualPrice() {
        float discount = Float.parseFloat(txtbillDiscount.getText().trim());
        float totalPrice = getbillTotalPrice();

        float actualPrice = (totalPrice - (totalPrice * discount / 100));

        return actualPrice;
    }

    //Report table data set
    public void getPurchaseReport(java.util.Date fromDate, java.util.Date toDate) {
        String[] purchaseColumns = {"Product Name", "Product Quantity", "Final Price"};
        DefaultTableModel purchaseModel = new DefaultTableModel();
        purchaseModel.setColumnIdentifiers(purchaseColumns);

        jTableReport.setModel(purchaseModel);

        sql = "select * from purchases where purchase_date between ? and ?";

        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setDate(1, convertutilltosql(fromDate));
            ps.setDate(2, convertutilltosql(toDate));
            rs = ps.executeQuery();
            while (rs.next()) {
                String pname = rs.getString("product_name");
                float quentity = rs.getFloat("quentity");
                float actualPrice = rs.getFloat("total_price");
                purchaseModel.addRow(new Object[]{pname, quentity, actualPrice});
            }

            rs.close();
            ps.close();
            dbCon.getCon().close();
//                JOptionPane.showMessageDialog(rootPane, "");
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getSalesReport(java.util.Date fromDate, java.util.Date toDate) {
        String[] salesColumns = {"Product Name", "Product Quantity", "Final Price"};
        DefaultTableModel salesModel = new DefaultTableModel();
        salesModel.setColumnIdentifiers(salesColumns);
        jTableReport.setModel(salesModel);
        sql = "select * from sales where sales_date between ? and ?";

        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setDate(1, convertutilltosql(fromDate));
            ps.setDate(2, convertutilltosql(toDate));
            rs = ps.executeQuery();
            while (rs.next()) {
                String pname = rs.getString("product_name");
                float quentity = rs.getFloat("purchase_quentity");
                float actualPrice = rs.getFloat("actual_price");
                salesModel.addRow(new Object[]{pname, quentity, actualPrice});
            }

            rs.close();
            ps.close();
            dbCon.getCon().close();
//                JOptionPane.showMessageDialog(rootPane, "");
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Dashboard data method
    public void getTodaySales() {
        sql = "select sum(actual_price) from sales where sales_date=?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setDate(1, sqltoday);
            rs = ps.executeQuery();
            while (rs.next()) {
                float todayTotal = rs.getFloat("sum(actual_price)");
                txtTodaySalesDigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getMonthlySales() {
        sql = "select sum(actual_price) from sales where sales_date Like?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setString(1, sqltoday.toString().substring(0, 8) + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                float todayTotal = rs.getFloat("sum(actual_price)");
                txtMonthlySalesDigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getTotalSales() {
        sql = "select sum(actual_price) from sales";

        try {
            ps = dbCon.getCon().prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                float todayTotal = rs.getFloat("sum(actual_price)");
                txtTotalSalesDigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getTodayPurchase() {
        sql = "select sum(total_price) from purchases where purchase_date=?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setDate(1, sqltoday);
            rs = ps.executeQuery();
            while (rs.next()) {
                float todayTotal = rs.getFloat("sum(total_price)");
                txtTodayPurchaseDigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getMonthlyPurchase() {
        sql = "select sum(total_price) from purchases where purchase_date Like?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setString(1, sqltoday.toString().substring(0, 8) + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                float todayTotal = rs.getFloat("sum(total_price)");
                txtMonthlyPurchaseDigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getTotalPurchase() {
        sql = "select sum(total_price) from purchases";
        try {
            ps = dbCon.getCon().prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                float todayTotal = rs.getFloat("sum(total_price)");
                txtTotalPurchaseDigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getCartTable() {
        String[] columns = {"Product Name", "Unit Price", "Quantity", "Discount", "Total", "Date"};
        DefaultTableModel cartdtm = new DefaultTableModel();
        cartdtm.setColumnIdentifiers(columns);
        jTableCart.setModel(cartdtm);

    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupReport = new javax.swing.ButtonGroup();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btndashborad = new javax.swing.JButton();
        btnInventory = new javax.swing.JButton();
        btnorderSales = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        menu = new javax.swing.JTabbedPane();
        tpdashboard = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanelTodaySales = new javax.swing.JPanel();
        txtTodaySalesDigit = new javax.swing.JTextField();
        txtTodaySales = new javax.swing.JTextField();
        jPanelTodayPurchase = new javax.swing.JPanel();
        txtTodayPurchaseDigit = new javax.swing.JTextField();
        txtTodayPurchase = new javax.swing.JTextField();
        jPanelTotalPurchase = new javax.swing.JPanel();
        txtTotalPurchaseDigit = new javax.swing.JTextField();
        txtTotalPurchase = new javax.swing.JTextField();
        jPanelMonthlyPurchase = new javax.swing.JPanel();
        txtMonthlyPurchaseDigit = new javax.swing.JTextField();
        txtMonthlyPurchase = new javax.swing.JTextField();
        jPanelMonthlySales = new javax.swing.JPanel();
        txtMonthlySalesDigit = new javax.swing.JTextField();
        txtTotalSales1 = new javax.swing.JTextField();
        jPanelTotalSales = new javax.swing.JPanel();
        txtTotalSalesDigit = new javax.swing.JTextField();
        txtTotalSales = new javax.swing.JTextField();
        lvlDashboard = new javax.swing.JLabel();
        tpInventory = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        btnInventoryPurchase = new javax.swing.JButton();
        btnInventoryUpdate = new javax.swing.JButton();
        tpInvPurchaseProduct = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        txtProductPurchaseProductId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtProductPurchaseUnitPrice = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        spanProductPurchaseQuentity = new javax.swing.JSpinner();
        btnInvPurchaseProductAdd = new javax.swing.JButton();
        btnInvPurchaseProductReset = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jdatePurchaseProduct = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        txtProductTotalPrice = new javax.swing.JTextField();
        comboPurchaseProductName = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTablePurchaseProduct = new javax.swing.JTable();
        tpInvUpdateProduct = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtinvUpdateProId = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtinvUpdateProName = new javax.swing.JTextField();
        txtinvUpdateUnitPrice = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtinvUpdatebuyPrice = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableInvUpdateProdunt = new javax.swing.JTable();
        btnInvUpdateDelete = new javax.swing.JButton();
        btnInvUpdate = new javax.swing.JButton();
        btnInvUpdateReset = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jdateinvUpdateCreateDate = new com.toedter.calendar.JDateChooser();
        btnInvUpdateAdd = new javax.swing.JButton();
        tpOrder = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtCustomerInfoName = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtCustomerInfoCell = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        comboCustomerDistrict = new javax.swing.JComboBox<>();
        btnCustomerSave = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtCustomerInfoAddress = new javax.swing.JTextArea();
        jLabel30 = new javax.swing.JLabel();
        jDateCustomerInfo = new com.toedter.calendar.JDateChooser();
        btnCustomerReset = new javax.swing.JButton();
        btnCustomerNext = new javax.swing.JButton();
        btncustomerSearch = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        txtCustomerInfoId = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        jtableCustomerInfo = new javax.swing.JTable();
        btnCustomerSave1 = new javax.swing.JButton();
        tpBillingInfo = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtbillunitPrice = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtbillTotalPrice = new javax.swing.JTextField();
        txtbillDiscount = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtbillFinalPrice = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableBillInfoProductDetails = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableCart = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btnBillPayment = new javax.swing.JButton();
        btnbillDelete = new javax.swing.JButton();
        btnillInfoReset = new javax.swing.JButton();
        btnBillSave = new javax.swing.JButton();
        spanbillquentity = new javax.swing.JSpinner();
        txtbillProductName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jDatebillSalesDate = new com.toedter.calendar.JDateChooser();
        btnbillinfoAddToCart = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        txtbillCustomerId = new javax.swing.JTextField();
        txtbillDeliveryId = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        txtbillTotalPaymentNext = new javax.swing.JTextField();
        tpPayment = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        txtpaymentPayment = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtpaymentdeliveyCode = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtpaymentDeliveryCharge = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtpaymentTotal = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtpaymentPaid = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtpaymentDue = new javax.swing.JTextField();
        jDatepaymentdeliveyDate = new com.toedter.calendar.JDateChooser();
        combopaymentDeliverycompany = new javax.swing.JComboBox<>();
        btnpaymentSave = new javax.swing.JButton();
        btnpaymentDeliveySet = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtpaymentDeliveryAddress = new javax.swing.JTextArea();
        salesReport = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jDateReportFromDate = new com.toedter.calendar.JDateChooser();
        jDateReportToDate = new com.toedter.calendar.JDateChooser();
        radioReportPurchase = new javax.swing.JRadioButton();
        radioReportSales = new javax.swing.JRadioButton();
        radioReportstock = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableReport = new javax.swing.JTable();
        tpLast = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        jScrollPane7.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 970, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 90));

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));

        btndashborad.setText("DashBoard");
        btndashborad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndashboradMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btndashboradMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btndashboradMouseExited(evt);
            }
        });

        btnInventory.setText("Inventory");
        btnInventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInventoryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInventoryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInventoryMouseExited(evt);
            }
        });

        btnorderSales.setText("Order/Sales");
        btnorderSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnorderSalesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnorderSalesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnorderSalesMouseExited(evt);
            }
        });

        jButton3.setText("Product List");

        jButton4.setText("Customer List");

        jButton5.setText("Sales Report");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });

        jButton6.setText("jButton1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btndashborad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnorderSales, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btndashborad, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnorderSales, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 160, 490));

        txtTodaySalesDigit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTodaySalesDigit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTodaySalesDigit.setText("0.00");

        txtTodaySales.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTodaySales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTodaySales.setText("Today Sales");

        javax.swing.GroupLayout jPanelTodaySalesLayout = new javax.swing.GroupLayout(jPanelTodaySales);
        jPanelTodaySales.setLayout(jPanelTodaySalesLayout);
        jPanelTodaySalesLayout.setHorizontalGroup(
            jPanelTodaySalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTodaySalesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanelTodaySalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTodaySalesDigit, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTodaySales, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)))
        );
        jPanelTodaySalesLayout.setVerticalGroup(
            jPanelTodaySalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTodaySalesLayout.createSequentialGroup()
                .addComponent(txtTodaySales, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtTodaySalesDigit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtTodayPurchaseDigit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTodayPurchaseDigit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTodayPurchaseDigit.setText("0.00");

        txtTodayPurchase.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTodayPurchase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTodayPurchase.setText("Today Purchase");

        javax.swing.GroupLayout jPanelTodayPurchaseLayout = new javax.swing.GroupLayout(jPanelTodayPurchase);
        jPanelTodayPurchase.setLayout(jPanelTodayPurchaseLayout);
        jPanelTodayPurchaseLayout.setHorizontalGroup(
            jPanelTodayPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTodayPurchaseLayout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addGroup(jPanelTodayPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTodayPurchaseDigit, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTodayPurchase, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)))
        );
        jPanelTodayPurchaseLayout.setVerticalGroup(
            jPanelTodayPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTodayPurchaseLayout.createSequentialGroup()
                .addComponent(txtTodayPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtTodayPurchaseDigit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtTotalPurchaseDigit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTotalPurchaseDigit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalPurchaseDigit.setText("0.00");

        txtTotalPurchase.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTotalPurchase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalPurchase.setText("Total Purchase");

        javax.swing.GroupLayout jPanelTotalPurchaseLayout = new javax.swing.GroupLayout(jPanelTotalPurchase);
        jPanelTotalPurchase.setLayout(jPanelTotalPurchaseLayout);
        jPanelTotalPurchaseLayout.setHorizontalGroup(
            jPanelTotalPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTotalPurchaseDigit)
            .addComponent(txtTotalPurchase, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
        );
        jPanelTotalPurchaseLayout.setVerticalGroup(
            jPanelTotalPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTotalPurchaseLayout.createSequentialGroup()
                .addComponent(txtTotalPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtTotalPurchaseDigit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtMonthlyPurchaseDigit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMonthlyPurchaseDigit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMonthlyPurchaseDigit.setText("0.00");

        txtMonthlyPurchase.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMonthlyPurchase.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMonthlyPurchase.setText("Monthly Purchase");

        javax.swing.GroupLayout jPanelMonthlyPurchaseLayout = new javax.swing.GroupLayout(jPanelMonthlyPurchase);
        jPanelMonthlyPurchase.setLayout(jPanelMonthlyPurchaseLayout);
        jPanelMonthlyPurchaseLayout.setHorizontalGroup(
            jPanelMonthlyPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMonthlyPurchaseDigit)
            .addComponent(txtMonthlyPurchase, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
        );
        jPanelMonthlyPurchaseLayout.setVerticalGroup(
            jPanelMonthlyPurchaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMonthlyPurchaseLayout.createSequentialGroup()
                .addComponent(txtMonthlyPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtMonthlyPurchaseDigit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtMonthlySalesDigit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMonthlySalesDigit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMonthlySalesDigit.setText("0.00");

        txtTotalSales1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTotalSales1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalSales1.setText("Monthly Sales");

        javax.swing.GroupLayout jPanelMonthlySalesLayout = new javax.swing.GroupLayout(jPanelMonthlySales);
        jPanelMonthlySales.setLayout(jPanelMonthlySalesLayout);
        jPanelMonthlySalesLayout.setHorizontalGroup(
            jPanelMonthlySalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMonthlySalesDigit)
            .addComponent(txtTotalSales1, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
        );
        jPanelMonthlySalesLayout.setVerticalGroup(
            jPanelMonthlySalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMonthlySalesLayout.createSequentialGroup()
                .addComponent(txtTotalSales1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtMonthlySalesDigit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtTotalSalesDigit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTotalSalesDigit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalSalesDigit.setText("0.00");

        txtTotalSales.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtTotalSales.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalSales.setText("Total Sales");

        javax.swing.GroupLayout jPanelTotalSalesLayout = new javax.swing.GroupLayout(jPanelTotalSales);
        jPanelTotalSales.setLayout(jPanelTotalSalesLayout);
        jPanelTotalSalesLayout.setHorizontalGroup(
            jPanelTotalSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTotalSalesLayout.createSequentialGroup()
                .addGroup(jPanelTotalSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtTotalSalesDigit, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotalSales, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelTotalSalesLayout.setVerticalGroup(
            jPanelTotalSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTotalSalesLayout.createSequentialGroup()
                .addComponent(txtTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtTotalSalesDigit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lvlDashboard.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lvlDashboard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lvlDashboard.setText("DashBoard");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelTodayPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelTodaySales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanelMonthlySales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                                .addComponent(jPanelTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanelMonthlyPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanelTotalPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lvlDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(243, 243, 243)))
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(lvlDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelTodaySales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelMonthlySales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelTodayPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelMonthlyPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelTotalPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanelTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        tpdashboard.addTab("tab1", jPanel3);

        menu.addTab("menu", tpdashboard);

        btnInventoryPurchase.setText("Purchase Product");
        btnInventoryPurchase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInventoryPurchaseMouseClicked(evt);
            }
        });

        btnInventoryUpdate.setText("Add/Update Product");
        btnInventoryUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInventoryUpdateMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(btnInventoryPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(btnInventoryUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInventoryPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInventoryUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(327, Short.MAX_VALUE))
        );

        tpInventory.addTab("tab1", jPanel6);

        menu.addTab("tab5", tpInventory);

        txtProductPurchaseProductId.setEditable(false);
        txtProductPurchaseProductId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Purchase Product");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Product ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Product Name");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Unit Price");

        txtProductPurchaseUnitPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Quentity");

        btnInvPurchaseProductAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvPurchaseProductAdd.setText("Add");
        btnInvPurchaseProductAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvPurchaseProductAddMouseClicked(evt);
            }
        });

        btnInvPurchaseProductReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvPurchaseProductReset.setText("Reset");
        btnInvPurchaseProductReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvPurchaseProductResetMouseClicked(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Created_Date");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Total Price");

        jTablePurchaseProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTablePurchaseProduct);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(btnInvPurchaseProductAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104)
                        .addComponent(btnInvPurchaseProductReset, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spanProductPurchaseQuentity)
                    .addComponent(txtProductTotalPrice)
                    .addComponent(jdatePurchaseProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProductPurchaseProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboPurchaseProductName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProductPurchaseUnitPrice))
                .addGap(108, 108, 108)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProductPurchaseProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboPurchaseProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProductPurchaseUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spanProductPurchaseQuentity, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProductTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jdatePurchaseProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(70, 70, 70)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInvPurchaseProductAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInvPurchaseProductReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33))
        );

        tpInvPurchaseProduct.addTab("tab1", jPanel4);

        menu.addTab("tab2", tpInvPurchaseProduct);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Update/Add Product");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Product Id");

        txtinvUpdateProId.setEditable(false);
        txtinvUpdateProId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtinvUpdateProId.setText("Auto generated");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Product Name");

        txtinvUpdateProName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtinvUpdateUnitPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Unit Price");

        txtinvUpdatebuyPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtinvUpdatebuyPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtinvUpdatebuyPriceActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Buying Price");

        tableInvUpdateProdunt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableInvUpdateProdunt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableInvUpdateProduntMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableInvUpdateProdunt);

        btnInvUpdateDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvUpdateDelete.setText("Delete");
        btnInvUpdateDelete.setMaximumSize(new java.awt.Dimension(77, 23));
        btnInvUpdateDelete.setMinimumSize(new java.awt.Dimension(77, 23));
        btnInvUpdateDelete.setPreferredSize(new java.awt.Dimension(77, 23));
        btnInvUpdateDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvUpdateDeleteMouseClicked(evt);
            }
        });

        btnInvUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvUpdate.setText("Update");
        btnInvUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvUpdateMouseClicked(evt);
            }
        });

        btnInvUpdateReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvUpdateReset.setText("Reset");
        btnInvUpdateReset.setMaximumSize(new java.awt.Dimension(77, 23));
        btnInvUpdateReset.setMinimumSize(new java.awt.Dimension(77, 23));
        btnInvUpdateReset.setPreferredSize(new java.awt.Dimension(77, 23));
        btnInvUpdateReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvUpdateResetMouseClicked(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("Created Date");

        btnInvUpdateAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvUpdateAdd.setText("Add");
        btnInvUpdateAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvUpdateAddMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 23, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtinvUpdateProName, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtinvUpdateProId, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtinvUpdateUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtinvUpdatebuyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdateinvUpdateCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(btnInvUpdateAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(btnInvUpdateReset, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(btnInvUpdate)
                        .addGap(85, 85, 85)
                        .addComponent(btnInvUpdateDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtinvUpdateProId))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(txtinvUpdateProName))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(txtinvUpdateUnitPrice))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtinvUpdatebuyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jdateinvUpdateCreateDate, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(79, 79, 79)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInvUpdateDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvUpdateReset, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvUpdateAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );

        tpInvUpdateProduct.addTab("tab1", jPanel7);

        menu.addTab("tab4", tpInvUpdateProduct);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setText("Customer Information");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Name");

        txtCustomerInfoName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Cell number");

        txtCustomerInfoCell.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("District");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Address");

        comboCustomerDistrict.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select division--", "Barisal", "Dhaka", "Chittagong", "Khulna", "Mymensingh", "Rajshahi", "Rangpur", "Sylhet" }));

        btnCustomerSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerSave.setText("Save");
        btnCustomerSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerSaveMouseClicked(evt);
            }
        });

        txtCustomerInfoAddress.setColumns(20);
        txtCustomerInfoAddress.setRows(5);
        txtCustomerInfoAddress.setText("\n");
        jScrollPane4.setViewportView(txtCustomerInfoAddress);

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Created_date");

        btnCustomerReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerReset.setText("Reset");
        btnCustomerReset.setMaximumSize(new java.awt.Dimension(61, 23));
        btnCustomerReset.setMinimumSize(new java.awt.Dimension(61, 23));
        btnCustomerReset.setPreferredSize(new java.awt.Dimension(61, 23));
        btnCustomerReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerResetMouseClicked(evt);
            }
        });

        btnCustomerNext.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerNext.setText("Next");
        btnCustomerNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerNextMouseClicked(evt);
            }
        });

        btncustomerSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btncustomerSearch.setText("Search");
        btncustomerSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btncustomerSearchMouseClicked(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("ID");

        txtCustomerInfoId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jtableCustomerInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(jtableCustomerInfo);

        btnCustomerSave1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerSave1.setText("Update");
        btnCustomerSave1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerSave1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(btncustomerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jDateCustomerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtCustomerInfoId, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel8Layout.createSequentialGroup()
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(44, 44, 44)
                                            .addComponent(txtCustomerInfoName, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(44, 44, 44)
                                        .addComponent(txtCustomerInfoCell, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(44, 44, 44)
                                        .addComponent(comboCustomerDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(44, 44, 44)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCustomerSave, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btnCustomerSave1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnCustomerReset, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnCustomerNext, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCustomerInfoId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboCustomerDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCustomerInfoName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCustomerInfoCell, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncustomerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateCustomerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCustomerNext, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerReset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerSave1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(268, 268, 268))
        );

        tpOrder.addTab("tab1", jPanel8);

        menu.addTab("tab6", tpOrder);

        tpBillingInfo.setMinimumSize(new java.awt.Dimension(805, 552));
        tpBillingInfo.setPreferredSize(new java.awt.Dimension(805, 552));

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setText("Billing Information");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 33, 294, 40));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Product Name");
        jPanel9.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 92, 29));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Unit Price");
        jPanel9.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 92, 29));

        txtbillunitPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbillunitPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtbillunitPriceFocusLost(evt);
            }
        });
        jPanel9.add(txtbillunitPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 161, 29));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Quentity");
        jPanel9.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 92, 29));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Total Price");
        jPanel9.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 92, 29));

        txtbillTotalPrice.setEditable(false);
        txtbillTotalPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbillTotalPrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtbillTotalPriceMouseClicked(evt);
            }
        });
        jPanel9.add(txtbillTotalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 161, 29));

        txtbillDiscount.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbillDiscount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtbillDiscountFocusLost(evt);
            }
        });
        jPanel9.add(txtbillDiscount, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 161, 29));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Discount");
        jPanel9.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 92, 29));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Final Price");
        jPanel9.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 92, 29));

        txtbillFinalPrice.setEditable(false);
        txtbillFinalPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel9.add(txtbillFinalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 161, 29));

        tableBillInfoProductDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableBillInfoProductDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBillInfoProductDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableBillInfoProductDetails);

        jPanel9.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 170, 384, 88));

        jTableCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTableCart);

        jPanel9.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 300, 384, 86));

        jLabel27.setText("Cart");
        jPanel9.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 271, 205, 20));

        jLabel28.setText("Product List");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 196, -1));

        btnBillPayment.setText("Payment");
        btnBillPayment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBillPaymentMouseClicked(evt);
            }
        });
        jPanel9.add(btnBillPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 460, 110, 34));

        btnbillDelete.setText("Delete");
        jPanel9.add(btnbillDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 460, 97, 34));

        btnillInfoReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnillInfoReset.setText("Reset");
        btnillInfoReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnillInfoResetMouseClicked(evt);
            }
        });
        jPanel9.add(btnillInfoReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 460, 91, 34));

        btnBillSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBillSave.setText("Save");
        btnBillSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBillSaveMouseClicked(evt);
            }
        });
        jPanel9.add(btnBillSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 460, 95, 34));

        spanbillquentity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spanbillquentityMouseClicked(evt);
            }
        });
        jPanel9.add(spanbillquentity, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 160, 30));
        jPanel9.add(txtbillProductName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 160, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Sales Date");
        jPanel9.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 80, 30));
        jPanel9.add(jDatebillSalesDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 160, 30));

        btnbillinfoAddToCart.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnbillinfoAddToCart.setText("Add to Cart");
        btnbillinfoAddToCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnbillinfoAddToCartMouseClicked(evt);
            }
        });
        jPanel9.add(btnbillinfoAddToCart, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, -1, 30));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("Customer ID");
        jPanel9.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 92, 29));
        jPanel9.add(txtbillCustomerId, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 160, 30));
        jPanel9.add(txtbillDeliveryId, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 160, 30));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("Delivery Id");
        jPanel9.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, 92, 29));

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText("Total Payment");
        jPanel9.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, 92, 29));
        jPanel9.add(txtbillTotalPaymentNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 400, 160, 30));

        tpBillingInfo.addTab("tab1", jPanel9);

        menu.addTab("tab7", tpBillingInfo);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel16.setText("Payment Information");

        jLabel38.setText("Payment Option");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select Payment method--", "Cash on Delivery", "Pay Online" }));

        jLabel39.setText("Payment");

        txtpaymentPayment.setText("Total Amount");

        jLabel40.setText("Delivery code");

        txtpaymentdeliveyCode.setEditable(false);

        jLabel41.setText("Delivey Address");

        jLabel42.setText("Delivery Date");

        jLabel43.setText("Delivery Company");

        jLabel44.setText("Delivery Charge");

        jLabel45.setText("Total Payment");

        txtpaymentTotal.setText("0");
        txtpaymentTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpaymentTotalMouseClicked(evt);
            }
        });

        jLabel46.setText("Paid");

        jLabel47.setText("Due");

        txtpaymentDue.setText("0");
        txtpaymentDue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpaymentDueMouseClicked(evt);
            }
        });

        combopaymentDeliverycompany.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnpaymentSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnpaymentSave.setText("Save");
        btnpaymentSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnpaymentSaveMouseClicked(evt);
            }
        });

        btnpaymentDeliveySet.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnpaymentDeliveySet.setText("Save Delivery");
        btnpaymentDeliveySet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnpaymentDeliveySetMouseClicked(evt);
            }
        });

        txtpaymentDeliveryAddress.setColumns(20);
        txtpaymentDeliveryAddress.setRows(5);
        jScrollPane9.setViewportView(txtpaymentDeliveryAddress);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnpaymentDeliveySet, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtpaymentdeliveyCode)
                                    .addComponent(txtpaymentDeliveryCharge)
                                    .addComponent(jDatepaymentdeliveyDate, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                                    .addComponent(combopaymentDeliverycompany, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                        .addGap(7, 7, 7)))))
                        .addGap(17, 17, 17)))
                .addGap(87, 87, 87)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel38))))
                        .addGap(18, 23, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtpaymentPayment)
                            .addComponent(txtpaymentTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(92, 92, 92))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtpaymentPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnpaymentSave, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtpaymentDue, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpaymentPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpaymentTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpaymentdeliveyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDatepaymentdeliveyDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combopaymentDeliverycompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpaymentDeliveryCharge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addComponent(btnpaymentDeliveySet, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpaymentPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpaymentDue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addComponent(btnpaymentSave, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 131, Short.MAX_VALUE))))
        );

        tpPayment.addTab("tab1", jPanel10);

        menu.addTab("tab8", tpPayment);

        salesReport.setMinimumSize(new java.awt.Dimension(805, 552));
        salesReport.setPreferredSize(new java.awt.Dimension(805, 552));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Report");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("From");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("To");

        buttonGroupReport.add(radioReportPurchase);
        radioReportPurchase.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radioReportPurchase.setText("Purchase ");

        buttonGroupReport.add(radioReportSales);
        radioReportSales.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radioReportSales.setText("Sales Report");

        buttonGroupReport.add(radioReportstock);
        radioReportstock.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radioReportstock.setText("Stock");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("View");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jTableReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTableReport);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 15, Short.MAX_VALUE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(radioReportPurchase)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(radioReportSales))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jDateReportFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(49, 49, 49)
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(38, 38, 38)
                                        .addComponent(jDateReportToDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(273, 273, 273))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(radioReportstock)
                                        .addGap(61, 61, 61)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateReportFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateReportToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioReportPurchase)
                            .addComponent(radioReportSales)
                            .addComponent(radioReportstock)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        salesReport.addTab("tab1", jPanel11);

        menu.addTab("tab9", salesReport);

        jLabel7.setText("Last page");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(377, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(420, Short.MAX_VALUE))
        );

        tpLast.addTab("tab1", jPanel5);

        menu.addTab("tab3", tpLast);

        getContentPane().add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 810, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btndashboradMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndashboradMouseClicked
        // TODO add your handling code here:
        menu.setSelectedIndex(0);

    }//GEN-LAST:event_btndashboradMouseClicked

    private void btnInventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventoryMouseClicked
        // TODO add your handling code here:
        menu.setSelectedIndex(1);
    }//GEN-LAST:event_btnInventoryMouseClicked

    private void btnorderSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnorderSalesMouseClicked
        // TODO add your handling code here:
        menu.setSelectedIndex(4);
    }//GEN-LAST:event_btnorderSalesMouseClicked
    //Inventory Add Produnts page Reset 
    private void btnInvPurchaseProductResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvPurchaseProductResetMouseClicked
        // TODO add your handling code here:
//        

        setInvPurchaeProductReset();

    }//GEN-LAST:event_btnInvPurchaseProductResetMouseClicked

    private void btnInventoryPurchaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventoryPurchaseMouseClicked
        // TODO add your handling code here:
        menu.setSelectedIndex(2);
    }//GEN-LAST:event_btnInventoryPurchaseMouseClicked

    private void btnInventoryUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventoryUpdateMouseClicked
        // TODO add your handling code here:
        menu.setSelectedIndex(3);

    }//GEN-LAST:event_btnInventoryUpdateMouseClicked

    private void btnInvPurchaseProductAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvPurchaseProductAddMouseClicked
        // TODO add your handling code here:

        String pname = comboPurchaseProductName.getSelectedItem().toString();
        String unitPrice = txtProductPurchaseUnitPrice.getText().trim();
        String quentity = spanProductPurchaseQuentity.getValue().toString();
        String totalPrice = txtProductTotalPrice.getText();
        Date purchaseDate = convertutilltosql(jdatePurchaseProduct.getDate());

        sql = "insert into purchases(product_name,quentity,unit_price,total_price, purchase_date)"
                + " values(?,?,?,?,?)";

        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setString(1, pname);
            ps.setString(2, quentity);
            ps.setString(3, unitPrice);
            ps.setString(3, unitPrice);
            ps.setString(4, totalPrice);
            ps.setDate(5, purchaseDate);
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
            JOptionPane.showMessageDialog(rootPane, "Data saved in purchases table");
            updateProductToStock();
            setProductnametoPurchaseCombo(comboPurchaseProductName);
            getAllPurchaseProduct(jTablePurchaseProduct);
            getTodayPurchase();
            getTotalPurchase();
            getMonthlyPurchase();

        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnInvPurchaseProductAddMouseClicked

    private void btnCustomerSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerSaveMouseClicked
        // TODO add your handling code here:
        String name = txtCustomerInfoName.getText().trim();
        String cell = txtCustomerInfoCell.getText().trim();
        String district = comboCustomerDistrict.getSelectedItem().toString();
        String address = txtCustomerInfoAddress.getText().trim();
        Date createdDate = convertutilltosql(jDateCustomerInfo.getDate());
        sql = "insert into customers(name,cell,district,address,created_date)values(?,?,?,?,?)";

        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, cell);
            ps.setString(3, district);
            ps.setString(4, address);
            ps.setDate(5, createdDate);
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
            JOptionPane.showMessageDialog(rootPane, "Data saved in customers table");
            getAllcustomers();
            
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnCustomerSaveMouseClicked

    private void btndashboradMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndashboradMouseEntered
        // TODO add your handling code here:
        btndashborad.setBackground(new Color(51, 204, 255));
    }//GEN-LAST:event_btndashboradMouseEntered

    private void btndashboradMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndashboradMouseExited
        // TODO add your handling code here:
        btndashborad.setBackground(getBackground());
        btndashborad.setForeground(getForeground());
    }//GEN-LAST:event_btndashboradMouseExited

    private void btnInventoryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventoryMouseEntered
        // TODO add your handling code here:
        btnInventory.setBackground(new Color(51, 204, 255));
    }//GEN-LAST:event_btnInventoryMouseEntered

    private void btnInventoryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventoryMouseExited
        // TODO add your handling code here:
        btnInventory.setBackground(getBackground());
        btnInventory.setForeground(getForeground());
    }//GEN-LAST:event_btnInventoryMouseExited

    private void btnorderSalesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnorderSalesMouseEntered
        // TODO add your handling code here:
        btnorderSales.setBackground(new Color(51, 204, 255));
    }//GEN-LAST:event_btnorderSalesMouseEntered

    private void btnorderSalesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnorderSalesMouseExited
        // TODO add your handling code here:
        btnorderSales.setBackground(getBackground());
        btnorderSales.setForeground(getForeground());
    }//GEN-LAST:event_btnorderSalesMouseExited

    private void btnInvUpdateResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvUpdateResetMouseClicked
        // TODO add your handling code here:
        setbtnInvUpdateReset();
    }//GEN-LAST:event_btnInvUpdateResetMouseClicked

    private void btnillInfoResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnillInfoResetMouseClicked
        // TODO add your handling code here:
        setbtnillInfoReset();
    }//GEN-LAST:event_btnillInfoResetMouseClicked

    private void txtbillunitPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtbillunitPriceFocusLost
        // TODO add your handling code here:

        try {
            if (!(txtbillunitPrice.getText().trim().isEmpty())) {

            } else {
                JOptionPane.showMessageDialog(rootPane, "Unit price can not be empty");
                txtbillunitPrice.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "An Error ocured" + e.getMessage()
            );
        }
    }//GEN-LAST:event_txtbillunitPriceFocusLost

    private void spanbillquentityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spanbillquentityMouseClicked
        // TODO add your handling code here:
//        int jsQuentity = Integer.parseInt(txtbillquentity.getValue().toString());
//        try {
//            if (txtbillunitPrice.getText().trim().isEmpty()) {
//                txtbillunitPrice.requestFocus();
//            } else if (jsQuentity == 0) {
//                JOptionPane.showMessageDialog(rootPane, "Quentity can not be Zero");
//                txtbillquentity.requestFocus();
//            } else {
////                JOptionPane.showMessageDialog(rootPane, "Quentity can not be Zero");
////                txtbillquentity.requestFocus();
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(rootPane, "An Error ocured" + e.getMessage()
//            );
//        }

    }//GEN-LAST:event_spanbillquentityMouseClicked

    private void txtbillDiscountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtbillDiscountFocusLost
        // TODO add your handling code here:
        float finalPrice = getbillAutualPrice();
        txtbillFinalPrice.setText(finalPrice + "");
    }//GEN-LAST:event_txtbillDiscountFocusLost

    private void txtbillTotalPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtbillTotalPriceMouseClicked
        // TODO add your handling code here:
        float toatPrice = getbillTotalPrice();

        txtbillTotalPrice.setText(toatPrice + "");

    }//GEN-LAST:event_txtbillTotalPriceMouseClicked

    private void btnCustomerResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerResetMouseClicked
        // TODO add your handling code here:
        setbtnCustomerReset();
    }//GEN-LAST:event_btnCustomerResetMouseClicked

    private void tableInvUpdateProduntMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInvUpdateProduntMouseClicked
        // TODO add your handling code here:
        int rowIndex = tableInvUpdateProdunt.getSelectedRow();

        String produntId = tableInvUpdateProdunt.getModel().getValueAt(rowIndex, 0).toString();
        String produntname = tableInvUpdateProdunt.getModel().getValueAt(rowIndex, 1).toString();
//        String produntQuentity = tableInvUpdateProdunt.getModel().getValueAt(rowIndex,2).toString();
        String produntUnitPrice = tableInvUpdateProdunt.getModel().getValueAt(rowIndex, 3).toString();
        String produntbuyPrice = tableInvUpdateProdunt.getModel().getValueAt(rowIndex, 4).toString();
        String createDate = tableInvUpdateProdunt.getModel().getValueAt(rowIndex, 5).toString();

//        System.out.println(createDate);
        txtinvUpdateProId.setText(produntId);
        txtinvUpdateProName.setText(produntname);
        txtinvUpdateUnitPrice.setText(produntUnitPrice);
        txtinvUpdatebuyPrice.setText(produntbuyPrice);
        jdateinvUpdateCreateDate.setDate(formatStringdateToUtilDate(createDate));
    }//GEN-LAST:event_tableInvUpdateProduntMouseClicked

    private void btnInvUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvUpdateMouseClicked
        // TODO add your handling code here:
        String proId = txtinvUpdateProId.getText().trim();
        if (proId.matches("Auto generated")) {
            JOptionPane.showMessageDialog(rootPane, "Only save operation is allowed for new produnt");
        } else {
            sql = "update products set name=?,unit_price=?,purchase_price = ? where idproducts = ?";
            try {
                ps = dbCon.getCon().prepareStatement(sql);
                ps.setString(1, txtinvUpdateProName.getText().trim());
                ps.setFloat(2, Float.parseFloat(txtinvUpdateUnitPrice.getText().trim()));
                ps.setFloat(3, Float.parseFloat(txtinvUpdatebuyPrice.getText().trim()));
                ps.setInt(4, Integer.parseInt(txtinvUpdateProId.getText().trim()));

                ps.executeUpdate();
                ps.close();
                dbCon.getCon().close();
                JOptionPane.showMessageDialog(rootPane, "produt update in products table");
                setbtnInvUpdateReset();
                getAllProducts();
            } catch (SQLException ex) {
                Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_btnInvUpdateMouseClicked

    private void btnInvUpdateDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvUpdateDeleteMouseClicked
        // TODO add your handling code here:

        sql = "delete from products where idproducts = ?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(txtinvUpdateProId.getText().trim()));

            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
            JOptionPane.showMessageDialog(rootPane, "product deleted from products table");
            setbtnInvUpdateReset();
            getAllProducts();
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnInvUpdateDeleteMouseClicked

    private void txtinvUpdatebuyPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtinvUpdatebuyPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtinvUpdatebuyPriceActionPerformed

    private void btnInvUpdateAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvUpdateAddMouseClicked
        // TODO add your handling code here:
        String proId = txtinvUpdateProId.getText().trim();
        String pname = txtinvUpdateProName.getText().trim();
        String unitPrice = txtinvUpdateUnitPrice.getText().trim();
        String buyPrice = txtinvUpdatebuyPrice.getText().trim();
        Date createDate = convertutilltosql(jdateinvUpdateCreateDate.getDate());

        if (proId.matches("Auto generated")) {
            sql = "insert into products(name,unit_price, purchase_price, entry_date)"
                    + " values(?,?,?,?)";

            try {
                ps = dbCon.getCon().prepareStatement(sql);
                ps.setString(1, pname);
                ps.setString(2, unitPrice);
                ps.setString(3, buyPrice);
                ps.setDate(4, createDate);
                ps.executeUpdate();
                ps.close();
                dbCon.getCon().close();
                JOptionPane.showMessageDialog(rootPane, "Data saved in smemanagement.products table");
                getAllProducts();
                addProductToStock();
                setProductnametoPurchaseCombo(comboPurchaseProductName);
            } catch (SQLException ex) {
                Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Only update/delete operation is allowed for old products");
        }


    }//GEN-LAST:event_btnInvUpdateAddMouseClicked

    private void btnBillSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBillSaveMouseClicked
        // TODO add your handling code here:
        int rowCount = jTableCart.getRowCount();
        System.out.println(rowCount);
        for (int i = 0; i < rowCount; i++) {
            String productN = jTableCart.getModel().getValueAt(i, 0).toString();
            float unitp = Float.parseFloat(jTableCart.getModel().getValueAt(i, 1).toString());
            float sellQ = Float.parseFloat(jTableCart.getModel().getValueAt(i, 2).toString());
            float actualP = Float.parseFloat(jTableCart.getModel().getValueAt(i, 3).toString());
            float discount1 = Float.parseFloat(jTableCart.getModel().getValueAt(i, 4).toString());
            String dateSt = jTableCart.getModel().getValueAt(i, 5).toString();
            java.util.Date utilsalesDate = formatStringdateToUtilDate(dateSt);
            int cutomerId = Integer.parseInt(txtbillCustomerId.getText());
            String deliverycode = txtbillDeliveryId.getText();
            jDatebillSalesDate.setDate(formatStringdateToUtilDate(dateSt));
            Date salesDate = convertutilltosql(utilsalesDate);
//            System.out.println("pN " + productN + " uP " + unitp + " sQ " + sellQ + " aP " + actualP + " dis " + discount1 + " date " + dateSt);
//            System.out.println(utilsalesDate);
//            System.out.println(salesDate);
            sql = "insert into sales(product_name,purchase_quentity, actual_price, discount,sales_date,unit_price,customer_id,delivery_code)"
                    + " values(?,?,?,?,?,?,?,?)";

            try {
                ps = dbCon.getCon().prepareStatement(sql);
                ps.setString(1, productN);
                ps.setFloat(2, sellQ);
                ps.setFloat(3, actualP);
                ps.setFloat(4, discount1);
                ps.setDate(5, salesDate);
                ps.setFloat(6, unitp);
                ps.setInt(7, cutomerId);
                ps.setString(8, deliverycode );

                ps.executeUpdate();
                ps.close();
                dbCon.getCon().close();
                subtractProductFromStock();

                getTodaySales();
                getTotalSales();
                getMonthlySales();
            } catch (SQLException ex) {
                Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(rootPane, "Data saved in smemanagement.sales table");
        setDeliveryCode();
        DefaultTableModel tableModel = (DefaultTableModel) jTableCart.getModel();
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
        

    }//GEN-LAST:event_btnBillSaveMouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        // TODO add your handling code here:
        menu.setSelectedIndex(7);
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        java.util.Date fromDate = jDateReportFromDate.getDate();
        java.util.Date toDate = jDateReportToDate.getDate();
        if (radioReportPurchase.isSelected()) {
            getPurchaseReport(fromDate, toDate);
        } else if (radioReportSales.isSelected()) {
            getSalesReport(fromDate, toDate);
        } else if (radioReportstock.isSelected()) {
//            getStock
        } else {
            JOptionPane.showMessageDialog(rootPane, "Select Purchase/Sales/Stock to see report");
        }
    }//GEN-LAST:event_jButton1MouseClicked
float totalPriceNext = 0.00f;
    private void btnbillinfoAddToCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbillinfoAddToCartMouseClicked
        // TODO add your handling code here:
//        String[] columns = {"Product Name", "Unit Price", "Quantity", "Discount", "Total"};
//        DefaultTableModel cartdtm = new DefaultTableModel();
//        cartdtm.setColumnIdentifiers(columns);
//        jTableCart.setModel(cartdtm);
//        cartdtm.;
        DefaultTableModel model = (DefaultTableModel) jTableCart.getModel();
        String productName = txtbillProductName.getText();
        float unitPrice = Float.parseFloat(txtbillunitPrice.getText());
        float quantity = Float.parseFloat(spanbillquentity.getValue().toString());
        float discount = Float.parseFloat(txtbillDiscount.getText());
        float actualPrice = Float.parseFloat(txtbillFinalPrice.getText());
        Date date = convertutilltosql(jDatebillSalesDate.getDate());
        
        totalPriceNext = actualPrice+totalPriceNext;
        txtbillTotalPaymentNext.setText(totalPriceNext+"");
        List<Object> productList = new ArrayList<>();

        productList.add(new Object[]{productName, unitPrice, quantity, discount, actualPrice, date});

        int row = model.getRowCount();
        for (Object i : productList) {
            //both method works.
            model.addRow((Object[]) i);
//            model.insertRow(row, (Object[]) i);
        }

//        if (row == 0) {
//            for (Object i : productList) {
//            cartdtm.addRow((Object[]) i);
//                cartdtm.insertRow(row, (Object[]) i);
//            }
//        } else {
//            row++;
//            for (Object i : productList) {
//            cartdtm.addRow((Object[]) i);
//                cartdtm.insertRow(row, (Object[]) i);
//                System.out.println("row"+row);
//            }
//        }

    }//GEN-LAST:event_btnbillinfoAddToCartMouseClicked
    String customerId, deliverId;

    public String setdeliveryId(String customerId) {
        SimpleDateFormat fromt = new SimpleDateFormat("ddMM");
        
        String date =fromt.format(sqltoday);
         
         int idnum = (int)(Math.random()*100);
         String deliveryId = "de"+date+"cId"+customerId+"r"+idnum;
         return deliveryId;
    }
    private void btnCustomerNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerNextMouseClicked
        // TODO add your handling code here:
        customerId = txtCustomerInfoId.getText();
        txtbillCustomerId.setText(customerId);
        deliverId=setdeliveryId(customerId);
        txtbillDeliveryId.setText(deliverId);
           
        menu.setSelectedIndex(5);
        customerId="";
        deliverId="";
        

    }//GEN-LAST:event_btnCustomerNextMouseClicked

    private void btncustomerSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncustomerSearchMouseClicked
        // TODO add your handling code here:
        String cell = txtCustomerInfoCell.getText().trim();

        sql = "Select * from customers where cell =?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setString(1, cell);
            rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getInt("idcustomers") + "";
                String name = rs.getString("name");
                String district = rs.getString("district");
                String address = rs.getString("address");
                Date entrydate = rs.getDate("created_date");
                txtCustomerInfoId.setText(id);
                txtCustomerInfoName.setText(name);
                txtCustomerInfoAddress.setText(address);
                comboCustomerDistrict.setSelectedItem(district);
                jDateCustomerInfo.setDate(entrydate);

            }

            rs.close();
            ps.close();
            dbCon.getCon().close();
            if (txtCustomerInfoId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "No data found.Now customers");
            } else {
                JOptionPane.showMessageDialog(rootPane, "Old customer data");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btncustomerSearchMouseClicked

    private void tableBillInfoProductDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBillInfoProductDetailsMouseClicked
        // TODO add your handling code here:
        int rowIndex = tableBillInfoProductDetails.getSelectedRow();

        txtbillProductName.setText(tableBillInfoProductDetails.getModel().getValueAt(rowIndex, 0).toString());
        txtbillunitPrice.setText(tableBillInfoProductDetails.getModel().getValueAt(rowIndex, 2).toString());
    }//GEN-LAST:event_tableBillInfoProductDetailsMouseClicked

    private void btnCustomerSave1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerSave1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCustomerSave1MouseClicked

    private void btnBillPaymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBillPaymentMouseClicked
        // TODO add your handling code here:
        String customerid = txtbillCustomerId.getText();
        String deliveryid = txtbillDeliveryId.getText();
        txtpaymentdeliveyCode.setText(deliveryid);
        txtpaymentPayment.setText(txtbillTotalPaymentNext.getText());
        
        
        menu.setSelectedIndex(6);
    }//GEN-LAST:event_btnBillPaymentMouseClicked

    private void txtpaymentTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpaymentTotalMouseClicked
        // TODO add your handling code here:
        if (txtpaymentPayment.getText().isEmpty()&& txtpaymentDeliveryCharge.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Input payment and delivey Charge");
            
        } else {
            float totalpay = Float.parseFloat(txtpaymentPayment.getText())+Float.parseFloat(txtpaymentDeliveryCharge.getText());
            txtpaymentTotal.setText(totalpay+"");
        }
    }//GEN-LAST:event_txtpaymentTotalMouseClicked

    private void txtpaymentDueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpaymentDueMouseClicked
        // TODO add your handling code here:
        if (txtpaymentTotal.getText().isEmpty() && txtpaymentPaid.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "insert total and paid amount");
            
        } else {
            float due = Float.parseFloat(txtpaymentTotal.getText())-Float.parseFloat(txtpaymentPaid.getText());
            txtpaymentDue.setText(due+"");
        }
    }//GEN-LAST:event_txtpaymentDueMouseClicked
   
    private void btnpaymentSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnpaymentSaveMouseClicked
        // TODO add your handling code here:
        if (flag==1) {
        
        float total = Float.parseFloat(txtpaymentTotal.getText());
        float paid = Float.parseFloat(txtpaymentPaid.getText());
        float due = Float.parseFloat(txtpaymentDue.getText());
        
        sql="insert into payment(payment,paid,due)values(?,?,?)";
        try {
            ps=dbCon.getCon().prepareStatement(sql);
            ps.setFloat(1, total);
            ps.setFloat(2, paid);
            ps.setFloat(3, due);
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
            JOptionPane.showMessageDialog(rootPane, "Order complete");
            
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        } else {
            JOptionPane.showMessageDialog(rootPane, "Select Delivery info ");
        }
        
    }//GEN-LAST:event_btnpaymentSaveMouseClicked
int flag=0;
    private void btnpaymentDeliveySetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnpaymentDeliveySetMouseClicked
        // TODO add your handling code here:
        
         String customerid = txtbillCustomerId.getText();
        String deliverycode = txtpaymentdeliveyCode.getText();
        float deliveyC = Float.parseFloat(txtpaymentDeliveryCharge.getText());
        
        Date orderD = sqltoday;
        Date deliveryD = convertutilltosql(jDatepaymentdeliveyDate.getDate());
        String deliveryAddress = txtpaymentDeliveryAddress.getText().trim();
        String deliveryCompany = combopaymentDeliverycompany.getSelectedItem().toString();
        
        sql="update delivery_charge set customer_id=?,"
                + "delivery_chargecol=?,order_date=?,delivery_date=?,delivery_address=?,delivery_company=?"
                + " where delivery_code=?";
        try {
            ps=dbCon.getCon().prepareStatement(sql);
            ps.setString(1, customerid);
            
            ps.setFloat(2, deliveyC);
            ps.setDate(3, orderD);
            ps.setDate(4, deliveryD);
            ps.setString(5, deliveryAddress);
            ps.setString(6, deliveryCompany);
            ps.setString(7, deliverycode);
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
            JOptionPane.showMessageDialog(rootPane, "Delivery info save");
            flag++;
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnpaymentDeliveySetMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SMEDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SMEDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SMEDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SMEDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SMEDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBillPayment;
    private javax.swing.JButton btnBillSave;
    private javax.swing.JButton btnCustomerNext;
    private javax.swing.JButton btnCustomerReset;
    private javax.swing.JButton btnCustomerSave;
    private javax.swing.JButton btnCustomerSave1;
    private javax.swing.JButton btnInvPurchaseProductAdd;
    private javax.swing.JButton btnInvPurchaseProductReset;
    private javax.swing.JButton btnInvUpdate;
    private javax.swing.JButton btnInvUpdateAdd;
    private javax.swing.JButton btnInvUpdateDelete;
    private javax.swing.JButton btnInvUpdateReset;
    private javax.swing.JButton btnInventory;
    private javax.swing.JButton btnInventoryPurchase;
    private javax.swing.JButton btnInventoryUpdate;
    private javax.swing.JButton btnbillDelete;
    private javax.swing.JButton btnbillinfoAddToCart;
    private javax.swing.JButton btncustomerSearch;
    private javax.swing.JButton btndashborad;
    private javax.swing.JButton btnillInfoReset;
    private javax.swing.JButton btnorderSales;
    private javax.swing.JButton btnpaymentDeliveySet;
    private javax.swing.JButton btnpaymentSave;
    private javax.swing.ButtonGroup buttonGroupReport;
    private javax.swing.JComboBox<String> comboCustomerDistrict;
    private javax.swing.JComboBox<String> comboPurchaseProductName;
    private javax.swing.JComboBox<String> combopaymentDeliverycompany;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateCustomerInfo;
    private com.toedter.calendar.JDateChooser jDateReportFromDate;
    private com.toedter.calendar.JDateChooser jDateReportToDate;
    private com.toedter.calendar.JDateChooser jDatebillSalesDate;
    private com.toedter.calendar.JDateChooser jDatepaymentdeliveyDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelMonthlyPurchase;
    private javax.swing.JPanel jPanelMonthlySales;
    private javax.swing.JPanel jPanelTodayPurchase;
    private javax.swing.JPanel jPanelTodaySales;
    private javax.swing.JPanel jPanelTotalPurchase;
    private javax.swing.JPanel jPanelTotalSales;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTableCart;
    private javax.swing.JTable jTablePurchaseProduct;
    private javax.swing.JTable jTableReport;
    private javax.swing.JTree jTree1;
    private com.toedter.calendar.JDateChooser jdatePurchaseProduct;
    private com.toedter.calendar.JDateChooser jdateinvUpdateCreateDate;
    private javax.swing.JTable jtableCustomerInfo;
    private javax.swing.JLabel lvlDashboard;
    private javax.swing.JTabbedPane menu;
    private javax.swing.JRadioButton radioReportPurchase;
    private javax.swing.JRadioButton radioReportSales;
    private javax.swing.JRadioButton radioReportstock;
    private javax.swing.JTabbedPane salesReport;
    private javax.swing.JSpinner spanProductPurchaseQuentity;
    private javax.swing.JSpinner spanbillquentity;
    private javax.swing.JTable tableBillInfoProductDetails;
    private javax.swing.JTable tableInvUpdateProdunt;
    private javax.swing.JTabbedPane tpBillingInfo;
    private javax.swing.JTabbedPane tpInvPurchaseProduct;
    private javax.swing.JTabbedPane tpInvUpdateProduct;
    private javax.swing.JTabbedPane tpInventory;
    private javax.swing.JTabbedPane tpLast;
    private javax.swing.JTabbedPane tpOrder;
    private javax.swing.JTabbedPane tpPayment;
    private javax.swing.JTabbedPane tpdashboard;
    private javax.swing.JTextArea txtCustomerInfoAddress;
    private javax.swing.JTextField txtCustomerInfoCell;
    private javax.swing.JTextField txtCustomerInfoId;
    private javax.swing.JTextField txtCustomerInfoName;
    private javax.swing.JTextField txtMonthlyPurchase;
    private javax.swing.JTextField txtMonthlyPurchaseDigit;
    private javax.swing.JTextField txtMonthlySalesDigit;
    private javax.swing.JTextField txtProductPurchaseProductId;
    private javax.swing.JTextField txtProductPurchaseUnitPrice;
    private javax.swing.JTextField txtProductTotalPrice;
    private javax.swing.JTextField txtTodayPurchase;
    private javax.swing.JTextField txtTodayPurchaseDigit;
    private javax.swing.JTextField txtTodaySales;
    private javax.swing.JTextField txtTodaySalesDigit;
    private javax.swing.JTextField txtTotalPurchase;
    private javax.swing.JTextField txtTotalPurchaseDigit;
    private javax.swing.JTextField txtTotalSales;
    private javax.swing.JTextField txtTotalSales1;
    private javax.swing.JTextField txtTotalSalesDigit;
    private javax.swing.JTextField txtbillCustomerId;
    private javax.swing.JTextField txtbillDeliveryId;
    private javax.swing.JTextField txtbillDiscount;
    private javax.swing.JTextField txtbillFinalPrice;
    private javax.swing.JTextField txtbillProductName;
    private javax.swing.JTextField txtbillTotalPaymentNext;
    private javax.swing.JTextField txtbillTotalPrice;
    private javax.swing.JTextField txtbillunitPrice;
    private javax.swing.JTextField txtinvUpdateProId;
    private javax.swing.JTextField txtinvUpdateProName;
    private javax.swing.JTextField txtinvUpdateUnitPrice;
    private javax.swing.JTextField txtinvUpdatebuyPrice;
    private javax.swing.JTextArea txtpaymentDeliveryAddress;
    private javax.swing.JTextField txtpaymentDeliveryCharge;
    private javax.swing.JTextField txtpaymentDue;
    private javax.swing.JTextField txtpaymentPaid;
    private javax.swing.JTextField txtpaymentPayment;
    private javax.swing.JTextField txtpaymentTotal;
    private javax.swing.JTextField txtpaymentdeliveyCode;
    // End of variables declaration//GEN-END:variables
}
