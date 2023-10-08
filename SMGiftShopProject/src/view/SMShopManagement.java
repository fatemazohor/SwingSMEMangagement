package view;

import InvoiceGenerate.InvoiceCreate;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.DbCon;

public class SMShopManagement extends javax.swing.JFrame {

    DbCon dbCon = new DbCon();
    String sql;
    PreparedStatement ps;
    ResultSet rs;
    InvoiceCreate invoiceCreate = new InvoiceCreate();
    LocalDate today = LocalDate.now();
    java.sql.Date sqltoday = java.sql.Date.valueOf(today);

    public SMShopManagement() {
        initComponents();
        setUIImage();
        init();
        getCartTable();
    }

    public void setUIImage() {

        ImageIcon icon = new ImageIcon("Assest/Image/demoSMlogo.png");
        Image img = icon.getImage().getScaledInstance(lvltestlogo.getWidth(), lvltestlogo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon iconS = new ImageIcon(img);
        lvltestlogo.setIcon(iconS);

        ImageIcon sideBarIcon = new ImageIcon("Assest/Image/Dance To Forget.jpg");
        Image sideImage = sideBarIcon.getImage().getScaledInstance(lvldashboard.getWidth(), lvldashboard.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon sideBImage = new ImageIcon(sideImage);
        lvldashboard.setIcon(sideBImage);

        ImageIcon todaySalesIcon = new ImageIcon("Assest/Image/todayS.jpg");
        Image todayImage = todaySalesIcon.getImage().getScaledInstance(lblTodaySales.getWidth(), lblTodaySales.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon todaySImage = new ImageIcon(todayImage);
        lblTodaySales.setIcon(todaySImage);
        lblTodaySales.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        ImageIcon totalSalesIcon = new ImageIcon("Assest/Image/Radar.jpg");
        Image totalImage = totalSalesIcon.getImage().getScaledInstance(lblTotalSales.getWidth(), lblTotalSales.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon totalSImage = new ImageIcon(totalImage);
        lblTotalSales.setIcon(totalSImage);
        lblTotalSales.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        ImageIcon todaypurchaseIcon = new ImageIcon("Assest/Image/Kyoto.jpg");
        Image todaypurchaseImage = todaypurchaseIcon.getImage().getScaledInstance(lblTodayPurchase.getWidth(), lblTodayPurchase.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon todayPImage = new ImageIcon(todaypurchaseImage);
        lblTodayPurchase.setIcon(todayPImage);
        lblTodayPurchase.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        ImageIcon totalpurchaseIcon = new ImageIcon("Assest/Image/JuicyOrange.jpg");
        Image totalpurchaseImage = totalpurchaseIcon.getImage().getScaledInstance(lblTotalPurchase.getWidth(), lblTotalPurchase.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon totalPImage = new ImageIcon(totalpurchaseImage);
        lblTotalPurchase.setIcon(totalPImage);
        lblTotalPurchase.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        ImageIcon totaldeliveryIcon = new ImageIcon("Assest/Image/Bourbon.jpg");
        Image totaldeliveryImage = totaldeliveryIcon.getImage().getScaledInstance(lblTotalDelivery.getWidth(), lblTotalDelivery.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon totalDImage = new ImageIcon(totaldeliveryImage);
        lblTotalDelivery.setIcon(totalDImage);
        lblTotalDelivery.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        ImageIcon todaydeliveryIcon = new ImageIcon("Assest/Image/Nelson.jpg");
        Image todaydeliveryImage = todaydeliveryIcon.getImage().getScaledInstance(lblTodayDelivery.getWidth(), lblTodayDelivery.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon todayDImage = new ImageIcon(todaydeliveryImage);
        lblTodayDelivery.setIcon(todayDImage);
        lblTodayDelivery.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

    }

    public void init() {
        getAllProducts();
        getAllcustomers();
        setAllProductTag(comboinvUpdateCategory);
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
        getTodaydelivery();
    }

    // date format method
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
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    private void setInvPurchaeProductReset() {
        txtProductPurchaseProductId.setText(null);
        comboPurchaseProductName.setSelectedIndex(0);
        txtProductPurchaseUnitPrice.setText(null);
        spanProductPurchaseQuentity.setValue(0);
        txtProductTotalPrice.setText(null);
        jdatePurchaseProduct.setDate(null);

    }

    private void setbtnInvUpdateReset() {
        txtinvUpdateProId.setText("Auto generated");
        txtinvUpdateProName.setText(null);
        txtinvUpdateUnitPrice.setText(null);
        jdateinvUpdateCreateDate.setDate(null);
        comboinvUpdateCategory.setSelectedIndex(0);

    }

    private void setbtnillInfoReset() {

        txtbillProductName.setText(null);
        txtbillunitPrice.setText(null);
        spanbillquentity.setValue(0);
        txtbillTotalPrice.setText(null);
        txtbillDiscount.setText("0");
        txtbillFinalPrice.setText(null);
        txtbillProductItem.setText(null);

    }

    private void setbtnCustomerReset() {
        txtCustomerInfoId.setText(null);
        txtCustomerInfoName.setText(null);
        txtCustomerInfoCell.setText(null);
        txtCustomerInfoAddress.setText(null);
        comboCustomerDistrict.setSelectedIndex(0);
        jDateCustomerInfo.setDate(null);

    }
    
     private void setbtnDeliveryReportReset() {
        txtDeliveryRepDId.setText(null);
        txtDeliveryRepDCode.setText(null);
        txtDeliveryRepPayment.setText(null);
        txtDeliveryRepDue.setText(null);
        txtDeliveryRepPaid.setText(null);
        comboDeliveryRepStatus.setSelectedIndex(0);
        jDateDeliveryRepDeliveryDate.setDate(null);

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
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setAllProductTag(javax.swing.JComboBox<String> comboBox) {

        comboBox.removeAllItems();
        comboBox.addItem("--Select category--");

        sql = "select product_tag from product_tag";

        try {
            ps = dbCon.getCon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String category = rs.getString("product_tag");
                comboBox.addItem(category);
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();

        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //get table
    private void getAllProducts() {

        String[] columnNames = {"ProduntId", "Name", "Quentity", "Unit_price", "Category", "Entry_date"};
        sql = "select * from products";

        DefaultTableModel producttableModel = new DefaultTableModel();
        producttableModel.setColumnIdentifiers(columnNames);
        tableInvUpdateProduct.setModel(producttableModel);

        try {
            ps = dbCon.getCon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int produntId = rs.getInt("idproducts");
                String name = rs.getString("name");
                int quentity = rs.getInt("quentity");
                float unitprice = rs.getFloat("unit_price");
                String producttag = rs.getString("product_tag");
                Date entryDate = rs.getDate("entry_date");
                //format sql date to string date , so can update date. 
//                String date = formatSqlDate(entryDate);

                producttableModel.addRow(new Object[]{produntId, name, quentity, unitprice, producttag, entryDate});

            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //show purchases table data

    public void getAllPurchaseProduct(javax.swing.JTable tableName) {
        String[] columnNames = {"PurchaseId", "Name", "Quentity", "Unit_price", "Total Price", "Purchase_date"};

        DefaultTableModel purchasetableModel = new DefaultTableModel();
        purchasetableModel.setColumnIdentifiers(columnNames);

        tableName.setModel(purchasetableModel);

        sql = "select * from purchases";
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
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //show products table from database
    private void getAllProductsForSell() {
        String[] columna = {"Product Name", "Item", "Quantity", "Price"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columna);
        tableBillInfoProductDetails.setModel(model);
        sql = "SELECT name,product_tag,quentity,unit_price FROM smgiftshop.products";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String item = rs.getString("product_tag");
                float quentity = rs.getFloat("quentity");
                float unitPrice = rs.getFloat("unit_price");

                model.addRow(new Object[]{name, item, quentity, unitPrice});
            }

            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //show payment table data 
    public void getAllPaymnet(javax.swing.JTable table) {
        String[] columnNames = {"ID","Delivery code", "Payment", "Due", "Status"};

        DefaultTableModel purchasetableModel = new DefaultTableModel();
        purchasetableModel.setColumnIdentifiers(columnNames);

        table.setModel(purchasetableModel);

        sql = "SELECT *  FROM smemanagement.payment ";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("payment_id");
                String delivery_code = rs.getString("delivery_code");

                float payment = rs.getFloat("payment");
                float due = rs.getFloat("due");
                String deliverystatus = rs.getString("delivery_status");
//                String deliveryCompany = rs.getString("d.delivery_company");
//                Date deliverydate = rs.getDate("d.delivery_date");

                purchasetableModel.addRow(new Object[]{id,delivery_code, payment, due, deliverystatus});
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();

        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //stock table data update
    public void addProductToStock() {
        sql = "insert into products(name,quentity)values(?,?)";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setString(1, txtinvUpdateProName.getText());
            ps.setFloat(2, Float.parseFloat(spanProductPurchaseQuentity.getValue().toString()));
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateProductToStock() {

        float quentity = Float.parseFloat(spanProductPurchaseQuentity.getValue().toString());
        sql = "update products set quentity=quentity+? where name=?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setFloat(1, quentity);
            ps.setString(2, comboPurchaseProductName.getSelectedItem().toString());
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
            System.out.println(quentity);
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void subtractProductFromStock() {
        float quantity = Float.parseFloat(spanbillquentity.getValue().toString());
        sql = "update products set quentity=quentity-? where name=?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setFloat(1, quantity);
            ps.setString(2, txtbillProductName.getText());
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //bill calculation
    private float getbillTotalPrice(javax.swing.JTextField unitPrice, javax.swing.JSpinner qty) {
        float unitP = Float.parseFloat(unitPrice.getText().trim().toString());
        float quentity = Float.parseFloat(qty.getValue().toString());

        float totalPrice = (unitP * quentity);

        return totalPrice;
    }

    private float getbillAutualPrice() {
        float discount = Float.parseFloat(txtbillDiscount.getText().trim());
        float totalPrice = getbillTotalPrice(txtbillunitPrice, spanbillquentity);

        float actualPrice = (totalPrice - (totalPrice * discount / 100));

        return actualPrice;
    }

    public void getCartTable() {
        String[] columns = {"Product Name", "Item", "Unit Price", "Quantity", "Discount", "Total", "Date"};
        DefaultTableModel cartdtm = new DefaultTableModel();
        cartdtm.setColumnIdentifiers(columns);
        jTableCart.setModel(cartdtm);

    }

    private void setDeliveryCode() {

        String deliverycode = txtbillDeliveryId.getText();
        sql = "insert into delivery_charge(delivery_code) values(?)";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setString(1, deliverycode);
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
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
                lbltodaySalesDigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getTodaydelivery() {
        sql = "select count(delivery_date) from payment where delivery_date=? and delivery_status=?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setDate(1, sqltoday);
            ps.setString(2, "Order Complete");
            rs = ps.executeQuery();
            while (rs.next()) {
                float todayTotal = rs.getFloat("count(delivery_date)");
                lbltodayDelivery.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
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
//                txtMonthlySalesDigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getTotalSales() {
        sql = "select sum(actual_price) from sales";

        try {
            ps = dbCon.getCon().prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                float todayTotal = rs.getFloat("sum(actual_price)");
                lbltotalSalesdigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
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
                lbltodayPurchaseDigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
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
//                txtMonthlyPurchaseDigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getTotalPurchase() {
        sql = "select sum(total_price) from purchases";
        try {
            ps = dbCon.getCon().prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                float todayTotal = rs.getFloat("sum(total_price)");
                lbltotalPurchaseDigit.setText(todayTotal + "");
            }
            rs.close();
            ps.close();
            dbCon.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printInvoice() {
//            String fileName= "invoice"+txtpaymentdeliveyCode.getText().trim();
        try {
            OutputStream file = new FileOutputStream(new File("Testfile.pdf"));
            Document doc = new Document();
            PdfWriter.getInstance(doc, file);
            PdfPTable irdTable = new PdfPTable(2);
            irdTable.addCell(invoiceCreate.getIrdCell("Invoice"));
            irdTable.addCell(invoiceCreate.getIrdCell("Date"));
            irdTable.addCell(invoiceCreate.getIrdCell(txtbillDeliveryId.getText()));
            irdTable.addCell(invoiceCreate.getIrdCell(formatSqlDate(sqltoday)));

            PdfPTable irhTable = new PdfPTable(3);
//            irhTable.setSpacingBefore(20.0f);
//            irhTable.setSpacingAfter(10.0f);
            irhTable.addCell(invoiceCreate.getIrhCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(invoiceCreate.getIrhCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(invoiceCreate.getIrhCell("Invoice", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(invoiceCreate.getIrhCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(invoiceCreate.getIrhCell("", PdfPCell.ALIGN_RIGHT));
            PdfPCell invoiceTable = new PdfPCell(irdTable);

            irhTable.addCell(invoiceTable);

            //Shop address.
            PdfPTable shopname = new PdfPTable(1);
            shopname.addCell(invoiceCreate.getshopNamecell("SM Gift Shop", PdfPCell.ALIGN_CENTER));
            shopname.addCell(invoiceCreate.getshopaddcell("Address: Shop No.85,New Market,Dhaka-1205"));
            shopname.addCell(invoiceCreate.getshopaddcell("Cell No: 0125325788, Email: galaxygiftshop@gmail.com"));

            // shop logo 
            com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance("Assest/Image/demoSMlogo.png");
            logo.scaleAbsolute(130.0f, 130.0f);
            PdfPCell imgCell = new PdfPCell(logo);
            imgCell.setBorder(0);

            //logo, shop name , address
            PdfPTable logoTable = new PdfPTable(2);
            logoTable.setWidthPercentage(100);
            logoTable.setWidths(new float[]{25f, 80f});

            logoTable.addCell(imgCell);
            PdfPCell shopCell = new PdfPCell(shopname);
            shopCell.setPadding(10.0f);
            shopCell.setBorder(0);
            logoTable.addCell(shopCell);

            //Customer info 
            FontSelector fontSelector = new FontSelector();
            Font billToFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD);
            fontSelector.addFont(billToFont);
            String billHeading = "Bill To";
            Phrase bill = fontSelector.process(billHeading);
            Paragraph billTo = new Paragraph(bill);
            //customer info
            billTo.setSpacingBefore(20.0f);
            Paragraph Customername = new Paragraph(txtCustomerInfoName.getText().trim());
            Customername.setIndentationLeft(20);

            Paragraph contact = new Paragraph(txtCustomerInfoCell.getText().trim());
            contact.setIndentationLeft(20);
            Paragraph address = new Paragraph(txtCustomerInfoAddress.getText().trim());
            address.setIndentationLeft(20);
            //bill table
            PdfPTable billTable = new PdfPTable(6);
            billTable.setWidthPercentage(100);
            billTable.setWidths(new float[]{1, 2, 5, 2, 1, 2});
            billTable.setSpacingBefore(30.0f);

            billTable.addCell(invoiceCreate.getbillHeaderCell("Index"));
            billTable.addCell(invoiceCreate.getbillHeaderCell("Item"));
            billTable.addCell(invoiceCreate.getbillHeaderCell("Description"));
            billTable.addCell(invoiceCreate.getbillHeaderCell("Unit Price"));
            billTable.addCell(invoiceCreate.getbillHeaderCell("Qty"));
            billTable.addCell(invoiceCreate.getbillHeaderCell("Amount"));

            //count order item
            DefaultTableModel model = (DefaultTableModel) jTableCart.getModel();
//            String proName = jTableCart.getModel().getValueAt(WIDTH, ICONIFIED);
            int rowcount = model.getRowCount();
            int index = 1;
            for (int i = 0; i < rowcount; i++) {
                String proName = jTableCart.getModel().getValueAt(i, 0).toString();
                String proitem = jTableCart.getModel().getValueAt(i, 1).toString();
                String unitPrice = jTableCart.getModel().getValueAt(i, 2).toString();
                String qty = jTableCart.getModel().getValueAt(i, 3).toString();
                String total = jTableCart.getModel().getValueAt(i, 5).toString();

                billTable.addCell(invoiceCreate.getbillRowCell(index + ""));
                billTable.addCell(invoiceCreate.getbillRowCell(proitem));
                billTable.addCell(invoiceCreate.getbillRowCell(proName));
                billTable.addCell(invoiceCreate.getbillRowCell(unitPrice));
                billTable.addCell(invoiceCreate.getbillRowCell(qty));
                billTable.addCell(invoiceCreate.getbillRowCell(total));
                index++;
            }

            //validity
            PdfPTable validity = new PdfPTable(1);
            validity.setWidthPercentage(100);
            validity.addCell(invoiceCreate.getvalidityRowCell(""));
            validity.addCell(invoiceCreate.getvalidityRowCell("Warranty"));
            validity.addCell(invoiceCreate.getvalidityRowCell("* Products purchased comes with 1 year national warranty \\n   (if applicable)\""));
            validity.addCell(invoiceCreate.getvalidityRowCell("* Warranty should be claimed only from the respective manufactures\""));

            PdfPCell summeryl = new PdfPCell(validity);
            summeryl.setColspan(3);
            summeryl.setPadding(1.0f);

            billTable.addCell(summeryl);

            //account total, tax ,discount
            PdfPTable account = new PdfPTable(2);
            account.setWidthPercentage(100);
            account.addCell(invoiceCreate.getAccountCell("SubTotal"));
            account.addCell(invoiceCreate.getAccountCellDigit(txtbillTotalPaymentNext.getText().trim()));
            account.addCell(invoiceCreate.getAccountCell("Delivery Charge"));
            account.addCell(invoiceCreate.getAccountCellDigit(txtpaymentDeliveryCharge.getText().trim()));
            account.addCell(invoiceCreate.getAccountCell("Discount"));
            account.addCell(invoiceCreate.getAccountCellDigit("0.00"));
            account.addCell(invoiceCreate.getAccountCell("Total"));
            account.addCell(invoiceCreate.getAccountCellDigit(txtpaymentTotal.getText().trim()));
            account.addCell(invoiceCreate.getAccountCell("Paid"));
            account.addCell(invoiceCreate.getAccountCellDigit(txtpaymentPaid.getText().trim()));
            account.addCell(invoiceCreate.getAccountCell("Due"));
            account.addCell(invoiceCreate.getAccountCellDigit(txtpaymentDue.getText().trim()));
            PdfPCell summeryAcc = new PdfPCell(account);
            summeryAcc.setColspan(3);
            summeryAcc.setPadding(1.0f);
            billTable.addCell(summeryAcc);

            //description
            PdfPTable description = new PdfPTable(1);
            description.setWidthPercentage(100);
            description.addCell(invoiceCreate.getDescripCell(" "));
            description.addCell(invoiceCreate.getDescripCell("Goods once sold will not be taken back or exchanged || Subject to product justification || Product damage no one responsible || "
                    + " Service only at concarned authorized service centers"));

            doc.open();
            doc.add(logoTable);
//            d.add(imge);
            doc.add(irhTable);
            doc.add(billTo);
            doc.add(Customername);
            doc.add(contact);
            doc.add(address);
            doc.add(billTable);
            doc.add(description);
            doc.close();

            file.close();
            System.out.println("File generated");
            JOptionPane.showMessageDialog(rootPane, "invoice created"
                    + "");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollBarCustom1 = new jframescrollbar.ScrollBarCustom();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panDashboard = new javax.swing.JPanel();
        btnHome = new javax.swing.JLabel();
        btnInventory = new javax.swing.JLabel();
        btnOrder = new javax.swing.JLabel();
        btnSalesRep = new javax.swing.JLabel();
        btnCustomer4 = new javax.swing.JLabel();
        btnProduct = new javax.swing.JLabel();
        btnDelivery = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        lvldashboard = new javax.swing.JLabel();
        panShopName = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel43 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtproduntname = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtunitp = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txtqty = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        btnbillinfoAddToCart = new javax.swing.JButton();
        btnillInfoReset = new javax.swing.JButton();
        lvlshopaddress2 = new javax.swing.JLabel();
        lvltestlogo = new javax.swing.JLabel();
        lvlshopname = new javax.swing.JLabel();
        lvlshopaddress = new javax.swing.JLabel();
        mainMenu = new javax.swing.JTabbedPane();
        Home = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        saleslogo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbltodaySalesDigit = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTodaySales = new javax.swing.JLabel();
        purchaseLogo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbltodayPurchaseDigit = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblTodayPurchase = new javax.swing.JLabel();
        shippedlogo = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbltodayDelivery = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblTodayDelivery = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbltotalSalesdigit = new javax.swing.JLabel();
        saleslogo1 = new javax.swing.JLabel();
        purchaseLogo1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblTotalSales = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbltotalPurchaseDigit = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblTotalPurchase = new javax.swing.JLabel();
        shippedlogo1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblTotalDelivery = new javax.swing.JLabel();
        inventory = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnAddUpdate = new javax.swing.JButton();
        btnAddUpdate1 = new javax.swing.JButton();
        btnAddUpdate2 = new javax.swing.JButton();
        addProduct = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtinvUpdateProId = new javax.swing.JTextField();
        txtinvUpdateProName = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtinvUpdateUnitPrice = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jdateinvUpdateCreateDate = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableInvUpdateProduct = new javax.swing.JTable();
        btnInvUpdateAdd = new javax.swing.JButton();
        btnInvUpdateReset = new javax.swing.JButton();
        btnInvUpdate = new javax.swing.JButton();
        btnInvUpdateDelete = new javax.swing.JButton();
        comboinvUpdateCategory = new javax.swing.JComboBox<>();
        purchaseProduct = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jdatePurchaseProduct = new com.toedter.calendar.JDateChooser();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtProductTotalPrice = new javax.swing.JTextField();
        spanProductPurchaseQuentity = new javax.swing.JSpinner();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtProductPurchaseUnitPrice = new javax.swing.JTextField();
        comboPurchaseProductName = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtProductPurchaseProductId = new javax.swing.JTextField();
        btnInvPurchaseProductAdd = new javax.swing.JButton();
        btnInvPurchaseProductReset = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePurchaseProduct = new javax.swing.JTable();
        order = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtCustomerInfoId = new javax.swing.JTextField();
        txtCustomerInfoName = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtCustomerInfoCell = new javax.swing.JTextField();
        btncustomerSearch = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jDateCustomerInfo = new com.toedter.calendar.JDateChooser();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtCustomerInfoAddress = new javax.swing.JTextArea();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        comboCustomerDistrict = new javax.swing.JComboBox<>();
        btnCustomerSave = new javax.swing.JButton();
        btnCustomerUpdate = new javax.swing.JButton();
        btnCustomerReset = new javax.swing.JButton();
        btnCustomerNext = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtableCustomerInfo = new javax.swing.JTable();
        billing = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        billingfrom = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableCart = new javax.swing.JTable();
        jLabel50 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        txtbillCustomerId = new javax.swing.JTextField();
        txtbillDeliveryId = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        btnBillSave = new javax.swing.JButton();
        btnbillDelete = new javax.swing.JButton();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        txtpaymentDue = new javax.swing.JTextField();
        txtpaymentPaid = new javax.swing.JTextField();
        txtpaymentTotal = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        txtbillTotalPaymentNext = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jDatepaymentdeliveyDate = new com.toedter.calendar.JDateChooser();
        combopaymentDeliverycompany = new javax.swing.JComboBox<>();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        txtpaymentDeliveryCharge = new javax.swing.JTextField();
        btnpaymentSave = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        comboPaymentOption = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableBillInfoProductDetails = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        spanbillquentity = new javax.swing.JSpinner();
        txtbillunitPrice = new javax.swing.JTextField();
        txtbillProductName = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jDatebillSalesDate = new com.toedter.calendar.JDateChooser();
        txtbillFinalPrice = new javax.swing.JTextField();
        txtbillDiscount = new javax.swing.JTextField();
        txtbillTotalPrice = new javax.swing.JTextField();
        btnbillinfoAddToCart1 = new javax.swing.JButton();
        btnillInfoReset1 = new javax.swing.JButton();
        jLabel60 = new javax.swing.JLabel();
        txtbillProductItem = new javax.swing.JTextField();
        payment = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        salesRep = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jDateReportToDate = new com.toedter.calendar.JDateChooser();
        jLabel69 = new javax.swing.JLabel();
        jDateReportFromDate = new com.toedter.calendar.JDateChooser();
        jLabel70 = new javax.swing.JLabel();
        radioReportPurchase = new javax.swing.JRadioButton();
        radioReportSales = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableReport = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        customerList = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jDateReportFromDate1 = new com.toedter.calendar.JDateChooser();
        jLabel72 = new javax.swing.JLabel();
        jDateReportToDate1 = new com.toedter.calendar.JDateChooser();
        comboCustomerProduct = new javax.swing.JComboBox<>();
        btnCustomerReport = new javax.swing.JButton();
        radioCustomerReport = new javax.swing.JRadioButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        tableCustomerReport = new javax.swing.JTable();
        ProductList = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tableProductReport = new javax.swing.JTable();
        jLabel73 = new javax.swing.JLabel();
        jDateProReportFromDate2 = new com.toedter.calendar.JDateChooser();
        jLabel74 = new javax.swing.JLabel();
        jDateProReportToDate = new com.toedter.calendar.JDateChooser();
        comboProductList = new javax.swing.JComboBox<>();
        radioProductReport = new javax.swing.JRadioButton();
        btnProductReport = new javax.swing.JButton();
        deliveryUpdate = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtDeliveryRepPaid = new javax.swing.JTextField();
        txtDeliveryRepDue = new javax.swing.JTextField();
        txtDeliveryRepPayment = new javax.swing.JTextField();
        txtDeliveryRepDCode = new javax.swing.JTextField();
        txtDeliveryRepDId = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        comboDeliveryRepStatus = new javax.swing.JComboBox<>();
        jDateDeliveryRepDeliveryDate = new com.toedter.calendar.JDateChooser();
        btnDeliveryRepReset = new javax.swing.JButton();
        btnDeliveryRepUpdate = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        jtableDeliveryReport = new javax.swing.JTable();
        order4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        scrollBarCustom1.setForeground(new java.awt.Color(255, 137, 78));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panDashboard.setBackground(new java.awt.Color(255, 255, 255));
        panDashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHome.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 20)); // NOI18N
        btnHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnHome.setText("Home");
        btnHome.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 78, 80), 2, true));
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
        });
        panDashboard.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 150, 50));

        btnInventory.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 20)); // NOI18N
        btnInventory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnInventory.setText("Inventory");
        btnInventory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(254, 102, 72), 2));
        btnInventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInventoryMouseClicked(evt);
            }
        });
        panDashboard.add(btnInventory, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 150, 50));

        btnOrder.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 20)); // NOI18N
        btnOrder.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnOrder.setText("Order");
        btnOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(253, 121, 65), 2));
        btnOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOrderMouseClicked(evt);
            }
        });
        panDashboard.add(btnOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 150, 50));

        btnSalesRep.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 20)); // NOI18N
        btnSalesRep.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSalesRep.setText("Sales Report");
        btnSalesRep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(252, 131, 62), 2));
        btnSalesRep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalesRepMouseClicked(evt);
            }
        });
        panDashboard.add(btnSalesRep, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 150, 50));

        btnCustomer4.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 20)); // NOI18N
        btnCustomer4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCustomer4.setText("Customer List");
        btnCustomer4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(252, 143, 58), 2));
        btnCustomer4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomer4MouseClicked(evt);
            }
        });
        panDashboard.add(btnCustomer4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 150, 50));

        btnProduct.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 20)); // NOI18N
        btnProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnProduct.setText("Product List");
        btnProduct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(251, 159, 53), 2));
        btnProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductMouseClicked(evt);
            }
        });
        panDashboard.add(btnProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 150, 50));

        btnDelivery.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 20)); // NOI18N
        btnDelivery.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDelivery.setText("Delivery ");
        btnDelivery.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 78, 80), 2));
        btnDelivery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeliveryMouseClicked(evt);
            }
        });
        panDashboard.add(btnDelivery, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 150, 50));

        jButton2.setText("jButton2");
        panDashboard.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, -1, -1));

        lvldashboard.setOpaque(true);
        panDashboard.add(lvldashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 560));

        getContentPane().add(panDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 126, 160, 554));

        panShopName.setBackground(new java.awt.Color(255, 255, 255));
        panShopName.setPreferredSize(new java.awt.Dimension(1042, 339));
        panShopName.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTable1);

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel43.setText("Customer Id");

        jTextField1.setEditable(false);
        jTextField1.setText("Customer Id");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel44.setText("Delivery Id");

        jTextField2.setEditable(false);
        jTextField2.setText("Delivery Id");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setText("Product Name");

        txtproduntname.setText("Customer Id");

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setText("Unit Price");

        txtunitp.setText("Customer Id");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setText("Discount");

        jTextField7.setText("Customer Id");

        jTextField8.setText("Customer Id");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText("Total");

        txtqty.setText("Customer Id");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setText("Qty");

        btnbillinfoAddToCart.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnbillinfoAddToCart.setText("Add to Cart");
        btnbillinfoAddToCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnbillinfoAddToCartMouseClicked(evt);
            }
        });

        btnillInfoReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnillInfoReset.setText("Reset");
        btnillInfoReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnillInfoResetMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField1)
                                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtproduntname, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGap(44, 44, 44)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGap(110, 110, 110)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtunitp, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addGap(125, 125, 125)
                                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnbillinfoAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnillInfoReset, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtproduntname, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtunitp, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnillInfoReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbillinfoAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panShopName.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(-462, -442, -1, -1));

        lvlshopaddress2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lvlshopaddress2.setForeground(new java.awt.Color(102, 102, 102));
        lvlshopaddress2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lvlshopaddress2.setText("Cell No: 0125325788, Email: SMgiftshop@gmail.com");
        panShopName.add(lvlshopaddress2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 1120, 30));
        panShopName.add(lvltestlogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 128));

        lvlshopname.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 36)); // NOI18N
        lvlshopname.setForeground(new java.awt.Color(255, 78, 80));
        lvlshopname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lvlshopname.setText("SM Gift Shop Ltd");
        panShopName.add(lvlshopname, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 1120, 60));

        lvlshopaddress.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lvlshopaddress.setForeground(new java.awt.Color(102, 102, 102));
        lvlshopaddress.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lvlshopaddress.setText("Address: Shop No.85,New Market,Dhaka-1205");
        panShopName.add(lvlshopaddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 1120, 30));

        getContentPane().add(panShopName, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1349, 126));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        saleslogo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        saleslogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/money.png"))); // NOI18N
        saleslogo.setText("Logo");
        jPanel4.add(saleslogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 50, -1));

        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Total Profit");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 160, 30));

        lbltodaySalesDigit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltodaySalesDigit.setText("Total Profit");
        jPanel4.add(lbltodaySalesDigit, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 150, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Total Profit");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 80, 30));

        lblTodaySales.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel4.add(lblTodaySales, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 310, 190));

        purchaseLogo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        purchaseLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/purchase (1).png"))); // NOI18N
        jPanel4.add(purchaseLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, 60, 40));

        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Total Profit");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 160, 30));

        lbltodayPurchaseDigit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltodayPurchaseDigit.setText("Total Profit");
        jPanel4.add(lbltodayPurchaseDigit, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 230, 150, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Today Purchase");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 190, 130, 30));

        lblTodayPurchase.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel4.add(lblTodayPurchase, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 310, 190));

        shippedlogo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        shippedlogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/shipped.png"))); // NOI18N
        jPanel4.add(shippedlogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 130, 70, -1));

        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("Total Profit");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 270, 160, 30));

        lbltodayDelivery.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltodayDelivery.setText("Total Profit");
        jPanel4.add(lbltodayDelivery, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 230, 130, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Today Delivery");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 190, 150, 30));

        lblTodayDelivery.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel4.add(lblTodayDelivery, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 120, 310, 190));

        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Total Profit");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 530, 160, 30));

        lbltotalSalesdigit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltotalSalesdigit.setText("Total Profit");
        jPanel4.add(lbltotalSalesdigit, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 490, 80, 30));

        saleslogo1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        saleslogo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/money.png"))); // NOI18N
        jPanel4.add(saleslogo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, 50, -1));

        purchaseLogo1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        purchaseLogo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/purchase (1).png"))); // NOI18N
        jPanel4.add(purchaseLogo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 400, 60, 40));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Total Profit");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 450, 80, 30));

        lblTotalSales.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel4.add(lblTotalSales, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 310, 190));

        jLabel16.setForeground(new java.awt.Color(153, 153, 153));
        jLabel16.setText("Total Profit");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 530, 160, 30));

        lbltotalPurchaseDigit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltotalPurchaseDigit.setText("Total Profit");
        jPanel4.add(lbltotalPurchaseDigit, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 490, 80, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Total Purchase");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 450, 110, 30));

        lblTotalPurchase.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel4.add(lblTotalPurchase, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 380, 310, 190));

        shippedlogo1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        shippedlogo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/shipped.png"))); // NOI18N
        jPanel4.add(shippedlogo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 390, 70, -1));

        jLabel19.setForeground(new java.awt.Color(153, 153, 153));
        jLabel19.setText("Total Profit");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 530, 160, 30));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Total Profit");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 490, 80, 30));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Total Delivery");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 450, 140, 30));

        lblTotalDelivery.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel4.add(lblTotalDelivery, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 380, 310, 190));

        javax.swing.GroupLayout HomeLayout = new javax.swing.GroupLayout(Home);
        Home.setLayout(HomeLayout);
        HomeLayout.setHorizontalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        HomeLayout.setVerticalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab1", Home);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Inventory");

        btnAddUpdate.setBackground(new java.awt.Color(0, 173, 204));
        btnAddUpdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAddUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/add.png"))); // NOI18N
        btnAddUpdate.setText("Add\\Update Product");
        btnAddUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddUpdateMouseClicked(evt);
            }
        });

        btnAddUpdate1.setBackground(new java.awt.Color(200, 181, 83));
        btnAddUpdate1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAddUpdate1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/buy.png"))); // NOI18N
        btnAddUpdate1.setText("Purchase Product");
        btnAddUpdate1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddUpdate1MouseClicked(evt);
            }
        });

        btnAddUpdate2.setBackground(new java.awt.Color(0, 194, 136));
        btnAddUpdate2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAddUpdate2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/delivery.png"))); // NOI18N
        btnAddUpdate2.setText("Delivery Update");
        btnAddUpdate2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddUpdate2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(355, 355, 355)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAddUpdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(144, 144, 144)
                        .addComponent(btnAddUpdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(225, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddUpdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addComponent(btnAddUpdate2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout inventoryLayout = new javax.swing.GroupLayout(inventory);
        inventory.setLayout(inventoryLayout);
        inventoryLayout.setHorizontalGroup(
            inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        inventoryLayout.setVerticalGroup(
            inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab2", inventory);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Add\\Update Product");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Product Id");

        txtinvUpdateProId.setEditable(false);
        txtinvUpdateProId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtinvUpdateProId.setText("Auto generated");

        txtinvUpdateProName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Product Name");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Unit Price");

        txtinvUpdateUnitPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Category");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("Created Date");

        tableInvUpdateProduct.setModel(new javax.swing.table.DefaultTableModel(
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
        tableInvUpdateProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableInvUpdateProductMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableInvUpdateProduct);

        btnInvUpdateAdd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvUpdateAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/save.png"))); // NOI18N
        btnInvUpdateAdd.setText("Add");
        btnInvUpdateAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvUpdateAddMouseClicked(evt);
            }
        });

        btnInvUpdateReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvUpdateReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/undo.png"))); // NOI18N
        btnInvUpdateReset.setText("Reset");
        btnInvUpdateReset.setMaximumSize(new java.awt.Dimension(77, 23));
        btnInvUpdateReset.setMinimumSize(new java.awt.Dimension(77, 23));
        btnInvUpdateReset.setPreferredSize(new java.awt.Dimension(77, 23));
        btnInvUpdateReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvUpdateResetMouseClicked(evt);
            }
        });

        btnInvUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/update.png"))); // NOI18N
        btnInvUpdate.setText("Update");
        btnInvUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvUpdateMouseClicked(evt);
            }
        });

        btnInvUpdateDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvUpdateDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/delete.png"))); // NOI18N
        btnInvUpdateDelete.setText("Delete");
        btnInvUpdateDelete.setMaximumSize(new java.awt.Dimension(77, 23));
        btnInvUpdateDelete.setMinimumSize(new java.awt.Dimension(77, 23));
        btnInvUpdateDelete.setPreferredSize(new java.awt.Dimension(77, 23));
        btnInvUpdateDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvUpdateDeleteMouseClicked(evt);
            }
        });

        comboinvUpdateCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtinvUpdateUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jdateinvUpdateCreateDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtinvUpdateProName, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtinvUpdateProId, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboinvUpdateCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(360, 360, 360)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(261, 261, 261)
                        .addComponent(btnInvUpdateAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnInvUpdateReset, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnInvUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnInvUpdateDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtinvUpdateProId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtinvUpdateProName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboinvUpdateCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtinvUpdateUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdateinvUpdateCreateDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInvUpdateDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvUpdateReset, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvUpdateAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(123, 123, 123))
        );

        javax.swing.GroupLayout addProductLayout = new javax.swing.GroupLayout(addProduct);
        addProduct.setLayout(addProductLayout);
        addProductLayout.setHorizontalGroup(
            addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        addProductLayout.setVerticalGroup(
            addProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 642, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab2", addProduct);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Purchase Product");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Created_Date");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("Total Price");

        txtProductTotalPrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtProductTotalPriceMouseClicked(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("Quentity");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("Unit Price");

        txtProductPurchaseUnitPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("Product Name");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("Product ID");

        txtProductPurchaseProductId.setEditable(false);
        txtProductPurchaseProductId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

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
        jScrollPane2.setViewportView(jTablePurchaseProduct);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(366, 366, 366)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnInvPurchaseProductAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(btnInvPurchaseProductReset, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jdatePurchaseProduct, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txtProductTotalPrice, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spanProductPurchaseQuentity, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProductPurchaseUnitPrice, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboPurchaseProductName, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtProductPurchaseProductId, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(148, 148, 148))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProductPurchaseProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboPurchaseProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProductPurchaseUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spanProductPurchaseQuentity, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProductTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdatePurchaseProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInvPurchaseProductReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInvPurchaseProductAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(123, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout purchaseProductLayout = new javax.swing.GroupLayout(purchaseProduct);
        purchaseProduct.setLayout(purchaseProductLayout);
        purchaseProductLayout.setHorizontalGroup(
            purchaseProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        purchaseProductLayout.setVerticalGroup(
            purchaseProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab2", purchaseProduct);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Customer Information");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("Name");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setText("ID");

        txtCustomerInfoId.setEditable(false);
        txtCustomerInfoId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtCustomerInfoName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setText("Cell number");

        txtCustomerInfoCell.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btncustomerSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btncustomerSearch.setText("Search");
        btncustomerSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btncustomerSearchMouseClicked(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setText("Created_date");

        txtCustomerInfoAddress.setColumns(20);
        txtCustomerInfoAddress.setRows(5);
        txtCustomerInfoAddress.setText("\n");
        jScrollPane4.setViewportView(txtCustomerInfoAddress);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setText("Address");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText("District");

        comboCustomerDistrict.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select division--", "Barisal", "Dhaka", "Chittagong", "Khulna", "Mymensingh", "Rajshahi", "Rangpur", "Sylhet" }));

        btnCustomerSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/save.png"))); // NOI18N
        btnCustomerSave.setText("Save");
        btnCustomerSave.setPreferredSize(new java.awt.Dimension(73, 23));
        btnCustomerSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerSaveMouseClicked(evt);
            }
        });

        btnCustomerUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/update.png"))); // NOI18N
        btnCustomerUpdate.setText("Update");
        btnCustomerUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerUpdateMouseClicked(evt);
            }
        });

        btnCustomerReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/undo.png"))); // NOI18N
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
        jScrollPane3.setViewportView(jtableCustomerInfo);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(246, 246, 246)
                        .addComponent(btnCustomerSave, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btnCustomerUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btnCustomerReset, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(btnCustomerNext, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCustomerInfoCell, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCustomerInfoName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCustomerInfoId, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btncustomerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(420, 420, 420))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(359, 359, 359)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboCustomerDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jDateCustomerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(153, 153, 153))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtCustomerInfoId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(txtCustomerInfoCell, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCustomerInfoName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jDateCustomerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboCustomerDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btncustomerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCustomerNext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerReset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout orderLayout = new javax.swing.GroupLayout(order);
        order.setLayout(orderLayout);
        orderLayout.setHorizontalGroup(
            orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1185, Short.MAX_VALUE)
        );
        orderLayout.setVerticalGroup(
            orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab2", order);

        billing.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        billingfrom.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        billingfrom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        billingfrom.setText("Billing Form");

        jPanel13.setBackground(new java.awt.Color(251, 230, 198));

        jScrollPane7.setBorder(null);

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
        jTableCart.setGridColor(new java.awt.Color(255, 255, 255));
        jTableCart.setSelectionBackground(new java.awt.Color(251, 159, 53));
        jScrollPane7.setViewportView(jTableCart);

        jLabel50.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(211, 84, 41));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Cart");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setText("Delivery Id");

        txtbillCustomerId.setEditable(false);

        txtbillDeliveryId.setEditable(false);

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setText("Customer ID");

        btnBillSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBillSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/save.png"))); // NOI18N
        btnBillSave.setText("Save");
        btnBillSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBillSaveMouseClicked(evt);
            }
        });

        btnbillDelete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnbillDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/delete.png"))); // NOI18N
        btnbillDelete.setText("Delete");
        btnbillDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnbillDeleteMouseClicked(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel61.setText("Total Payment");

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel62.setText("Paid");

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel63.setText("Due");

        txtpaymentDue.setText("0");
        txtpaymentDue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpaymentDueMouseClicked(evt);
            }
        });
        txtpaymentDue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpaymentDueActionPerformed(evt);
            }
        });

        txtpaymentPaid.setText("0");

        txtpaymentTotal.setEditable(false);
        txtpaymentTotal.setText("0");
        txtpaymentTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpaymentTotalMouseClicked(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel64.setText("Bill Amount");

        txtbillTotalPaymentNext.setEditable(false);

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel65.setText("Delivery Date");

        combopaymentDeliverycompany.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Select --", "Delivery Person 01", "Delivery Person 02", "Delivery Person 03", " " }));

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel66.setText("Delivery Person");

        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel67.setText("Delivery Charge");

        txtpaymentDeliveryCharge.setText("0");
        txtpaymentDeliveryCharge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpaymentDeliveryChargeActionPerformed(evt);
            }
        });

        btnpaymentSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnpaymentSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/payment.png"))); // NOI18N
        btnpaymentSave.setText("Proceed To Payment");
        btnpaymentSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnpaymentSaveMouseClicked(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("Print");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel68.setText("Payment Option");

        comboPaymentOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select Payment method--", "Cash on Delivery", "Pay Online" }));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(btnBillSave, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172)
                .addComponent(btnbillDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(168, 168, 168))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtbillTotalPaymentNext))
                                    .addGroup(jPanel13Layout.createSequentialGroup()
                                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(24, 24, 24)
                                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtpaymentPaid)
                                            .addComponent(txtpaymentDue, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                                    .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDatepaymentdeliveyDate, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combopaymentDeliverycompany, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboPaymentOption, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtpaymentTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtpaymentDeliveryCharge, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addComponent(txtbillCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(txtbillDeliveryId, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(btnpaymentSave, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(241, 241, 241))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtbillCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtbillDeliveryId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnbillDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBillSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPaymentOption, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtbillTotalPaymentNext, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpaymentDeliveryCharge, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtpaymentTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtpaymentPaid, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpaymentDue, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combopaymentDeliverycompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDatepaymentdeliveyDate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(32, 32, 32)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnpaymentSave, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane6.setBorder(null);

        tableBillInfoProductDetails.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
        tableBillInfoProductDetails.setGridColor(new java.awt.Color(255, 255, 255));
        tableBillInfoProductDetails.setRowHeight(25);
        tableBillInfoProductDetails.setSelectionBackground(new java.awt.Color(255, 78, 80));
        tableBillInfoProductDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBillInfoProductDetailsMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tableBillInfoProductDetails);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 78, 80));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Product List");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel53.setText("Product Name");

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel54.setText("Unit Price");

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel55.setText("Qty");

        spanbillquentity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spanbillquentityMouseClicked(evt);
            }
        });

        txtbillunitPrice.setEditable(false);
        txtbillunitPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbillunitPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtbillunitPriceFocusLost(evt);
            }
        });

        txtbillProductName.setEditable(false);

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel56.setText("Total Price");

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel57.setText("Discount");

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel58.setText("Final Price");

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel59.setText("Sales Date");

        txtbillFinalPrice.setEditable(false);
        txtbillFinalPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbillFinalPrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtbillFinalPriceMouseClicked(evt);
            }
        });

        txtbillDiscount.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbillDiscount.setText("0");
        txtbillDiscount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtbillDiscountFocusLost(evt);
            }
        });

        txtbillTotalPrice.setEditable(false);
        txtbillTotalPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtbillTotalPrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtbillTotalPriceMouseClicked(evt);
            }
        });

        btnbillinfoAddToCart1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnbillinfoAddToCart1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/add-to-cart.png"))); // NOI18N
        btnbillinfoAddToCart1.setText("Add to Cart");
        btnbillinfoAddToCart1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnbillinfoAddToCart1MouseClicked(evt);
            }
        });

        btnillInfoReset1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnillInfoReset1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SM/icon/undo.png"))); // NOI18N
        btnillInfoReset1.setText("Reset");
        btnillInfoReset1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnillInfoReset1MouseClicked(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel60.setText("Item/Category");

        txtbillProductItem.setEditable(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(billingfrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(81, 81, 81)
                                        .addComponent(spanbillquentity))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(txtbillunitPrice))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(28, 28, 28)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtbillProductItem, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtbillProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(40, 40, 40)
                                        .addComponent(jDatebillSalesDate, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(txtbillFinalPrice))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(txtbillDiscount))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(txtbillTotalPrice)))
                                .addGap(12, 12, 12))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnbillinfoAddToCart1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(btnillInfoReset1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(billingfrom, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 500, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtbillTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtbillProductItem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtbillDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtbillProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtbillunitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spanbillquentity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtbillFinalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDatebillSalesDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnbillinfoAddToCart1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnillInfoReset1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                        .addGap(22, 22, 22))))
        );

        billing.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1190, 650));

        mainMenu.addTab("tab2", billing);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Payment Form");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(427, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(500, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout paymentLayout = new javax.swing.GroupLayout(payment);
        payment.setLayout(paymentLayout);
        paymentLayout.setHorizontalGroup(
            paymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        paymentLayout.setVerticalGroup(
            paymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab2", payment);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Sales Report");

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel69.setText("From");

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel70.setText("To");

        buttonGroup1.add(radioReportPurchase);
        radioReportPurchase.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radioReportPurchase.setText("Purchase ");

        buttonGroup1.add(radioReportSales);
        radioReportSales.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radioReportSales.setText("Sales Report");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("View");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane8.setBorder(null);

        jTableReport.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
        jTableReport.setGridColor(new java.awt.Color(255, 255, 255));
        jTableReport.setRowHeight(25);
        jTableReport.setSelectionBackground(new java.awt.Color(255, 120, 65));
        jScrollPane8.setViewportView(jTableReport);

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setText("View");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(359, 359, 359)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(radioReportPurchase)
                                .addGap(134, 134, 134)
                                .addComponent(radioReportSales)
                                .addGap(95, 95, 95)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jDateReportFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jDateReportToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1055, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateReportFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateReportToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioReportPurchase)
                            .addComponent(radioReportSales)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(51, 51, 51)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout salesRepLayout = new javax.swing.GroupLayout(salesRep);
        salesRep.setLayout(salesRepLayout);
        salesRepLayout.setHorizontalGroup(
            salesRepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        salesRepLayout.setVerticalGroup(
            salesRepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab2", salesRep);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Customer List");

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel71.setText("From");

        jLabel72.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel72.setText("To");

        btnCustomerReport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerReport.setText("View");
        btnCustomerReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerReportMouseClicked(evt);
            }
        });

        radioCustomerReport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radioCustomerReport.setText("Customer");
        radioCustomerReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioCustomerReportMouseClicked(evt);
            }
        });

        tableCustomerReport.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(tableCustomerReport);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateReportFromDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jDateReportToDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                            .addGap(53, 53, 53)
                            .addComponent(radioCustomerReport)
                            .addGap(128, 128, 128)
                            .addComponent(comboCustomerProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCustomerReport, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                            .addGap(359, 359, 359)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 1016, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateReportFromDate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateReportToDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioCustomerReport)
                        .addComponent(btnCustomerReport, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comboCustomerProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout customerListLayout = new javax.swing.GroupLayout(customerList);
        customerList.setLayout(customerListLayout);
        customerListLayout.setHorizontalGroup(
            customerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        customerListLayout.setVerticalGroup(
            customerListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab2", customerList);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Product List");

        tableProductReport.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane11.setViewportView(tableProductReport);

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel73.setText("From");

        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel74.setText("To");

        radioProductReport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radioProductReport.setText("Product");
        radioProductReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioProductReportMouseClicked(evt);
            }
        });

        btnProductReport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnProductReport.setText("View");
        btnProductReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductReportMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(359, 359, 359)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jDateProReportFromDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(radioProductReport))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jDateProReportToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboProductList, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(161, 161, 161)
                                .addComponent(btnProductReport, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 963, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(176, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateProReportFromDate2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateProReportToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioProductReport)
                    .addComponent(comboProductList, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProductReport, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ProductListLayout = new javax.swing.GroupLayout(ProductList);
        ProductList.setLayout(ProductListLayout);
        ProductListLayout.setHorizontalGroup(
            ProductListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ProductListLayout.setVerticalGroup(
            ProductListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab2", ProductList);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Delivery Update");

        txtDeliveryRepPaid.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtDeliveryRepDue.setEditable(false);
        txtDeliveryRepDue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtDeliveryRepPayment.setEditable(false);
        txtDeliveryRepPayment.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtDeliveryRepDCode.setEditable(false);
        txtDeliveryRepDCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtDeliveryRepDId.setEditable(false);
        txtDeliveryRepDId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel75.setText("Id");

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel76.setText("Delivery Code");

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel77.setText("Payment");

        jLabel78.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel78.setText("Due");

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel79.setText("Paid");

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel80.setText("Delivery Date");

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel81.setText("Delivery Status");

        comboDeliveryRepStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--", "Order Confirmed", "Order Complete", " " }));

        btnDeliveryRepReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDeliveryRepReset.setText("Reset");
        btnDeliveryRepReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeliveryRepResetMouseClicked(evt);
            }
        });

        btnDeliveryRepUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDeliveryRepUpdate.setText("Update");
        btnDeliveryRepUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeliveryRepUpdateMouseClicked(evt);
            }
        });

        jScrollPane12.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane12.setBorder(null);

        jtableDeliveryReport.setModel(new javax.swing.table.DefaultTableModel(
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
        jtableDeliveryReport.setGridColor(new java.awt.Color(255, 255, 255));
        jtableDeliveryReport.setSelectionBackground(new java.awt.Color(255, 188, 190));
        jtableDeliveryReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableDeliveryReportMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(jtableDeliveryReport);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(359, 359, 359)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(txtDeliveryRepDId, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel12Layout.createSequentialGroup()
                                    .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(32, 32, 32)
                                    .addComponent(txtDeliveryRepDCode, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel12Layout.createSequentialGroup()
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(32, 32, 32)
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtDeliveryRepPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDeliveryRepDue, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDeliveryRepPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDateDeliveryRepDeliveryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboDeliveryRepStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(btnDeliveryRepUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(btnDeliveryRepReset, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 1027, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDeliveryRepDId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDeliveryRepStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDeliveryRepDCode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDeliveryRepPayment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(txtDeliveryRepDue, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDeliveryRepPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateDeliveryRepDeliveryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDeliveryRepReset, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeliveryRepUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)))
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );

        javax.swing.GroupLayout deliveryUpdateLayout = new javax.swing.GroupLayout(deliveryUpdate);
        deliveryUpdate.setLayout(deliveryUpdateLayout);
        deliveryUpdateLayout.setHorizontalGroup(
            deliveryUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deliveryUpdateLayout.setVerticalGroup(
            deliveryUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab2", deliveryUpdate);

        javax.swing.GroupLayout order4Layout = new javax.swing.GroupLayout(order4);
        order4.setLayout(order4Layout);
        order4Layout.setHorizontalGroup(
            order4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        order4Layout.setVerticalGroup(
            order4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 642, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab2", order4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 642, Short.MAX_VALUE)
        );

        mainMenu.addTab("tab3", jPanel3);

        getContentPane().add(mainMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 10, 1190, 670));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
// Dashboard button event
    private void btnInventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventoryMouseClicked
        mainMenu.setSelectedIndex(1);

        btnHome.setForeground(Color.BLACK);
        btnHome.setBorder(BorderFactory.createLineBorder(new Color(254, 78, 80), 2));
        btnInventory.setForeground(Color.WHITE);
        btnInventory.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnOrder.setForeground(Color.BLACK);
        btnOrder.setBorder(BorderFactory.createLineBorder(new Color(253, 121, 65), 2));
        btnSalesRep.setForeground(Color.BLACK);
        btnSalesRep.setBorder(BorderFactory.createLineBorder(new Color(252, 131, 62), 2));
        btnCustomer4.setForeground(Color.BLACK);
        btnCustomer4.setBorder(BorderFactory.createLineBorder(new Color(252, 143, 58), 2));
        btnProduct.setForeground(Color.BLACK);
        btnProduct.setBorder(BorderFactory.createLineBorder(new Color(251, 159, 53), 2));
    }//GEN-LAST:event_btnInventoryMouseClicked

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        mainMenu.setSelectedIndex(0);
        btnHome.setForeground(Color.WHITE);
        btnHome.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnInventory.setForeground(Color.BLACK);
        btnInventory.setBorder(BorderFactory.createLineBorder(new Color(254, 102, 72), 2));
        btnOrder.setForeground(Color.BLACK);
        btnOrder.setBorder(BorderFactory.createLineBorder(new Color(253, 121, 65), 2));
        btnSalesRep.setForeground(Color.BLACK);
        btnSalesRep.setBorder(BorderFactory.createLineBorder(new Color(252, 131, 62), 2));
        btnCustomer4.setForeground(Color.BLACK);
        btnCustomer4.setBorder(BorderFactory.createLineBorder(new Color(252, 143, 58), 2));
        btnProduct.setForeground(Color.BLACK);
        btnProduct.setBorder(BorderFactory.createLineBorder(new Color(251, 159, 53), 2));
    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrderMouseClicked
        mainMenu.setSelectedIndex(4);
        btnHome.setForeground(Color.BLACK);
        btnHome.setBorder(BorderFactory.createLineBorder(new Color(254, 78, 80), 2));
        btnInventory.setForeground(Color.BLACK);
        btnInventory.setBorder(BorderFactory.createLineBorder(new Color(254, 102, 72), 2));
        btnOrder.setForeground(Color.WHITE);
        btnOrder.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnSalesRep.setForeground(Color.BLACK);
        btnSalesRep.setBorder(BorderFactory.createLineBorder(new Color(252, 131, 62), 2));
        btnCustomer4.setForeground(Color.BLACK);
        btnCustomer4.setBorder(BorderFactory.createLineBorder(new Color(252, 143, 58), 2));
        btnProduct.setForeground(Color.BLACK);
        btnProduct.setBorder(BorderFactory.createLineBorder(new Color(251, 159, 53), 2));
    }//GEN-LAST:event_btnOrderMouseClicked

    private void btnSalesRepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalesRepMouseClicked
        mainMenu.setSelectedIndex(7);
        btnHome.setForeground(Color.black);
        btnHome.setBorder(BorderFactory.createLineBorder(new Color(254, 78, 80), 2));
        btnInventory.setForeground(Color.BLACK);
        btnInventory.setBorder(BorderFactory.createLineBorder(new Color(254, 102, 72), 2));
        btnOrder.setForeground(Color.BLACK);
        btnOrder.setBorder(BorderFactory.createLineBorder(new Color(253, 121, 65), 2));
        btnSalesRep.setForeground(Color.WHITE);
        btnSalesRep.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnCustomer4.setForeground(Color.BLACK);
        btnCustomer4.setBorder(BorderFactory.createLineBorder(new Color(252, 143, 58), 2));
        btnProduct.setForeground(Color.BLACK);
        btnProduct.setBorder(BorderFactory.createLineBorder(new Color(251, 159, 53), 2));
    }//GEN-LAST:event_btnSalesRepMouseClicked

    private void btnCustomer4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomer4MouseClicked
        mainMenu.setSelectedIndex(8);
        btnHome.setForeground(Color.BLACK);
        btnHome.setBorder(BorderFactory.createLineBorder(new Color(254, 78, 80), 2));
        btnInventory.setForeground(Color.BLACK);
        btnInventory.setBorder(BorderFactory.createLineBorder(new Color(254, 102, 72), 2));
        btnOrder.setForeground(Color.BLACK);
        btnOrder.setBorder(BorderFactory.createLineBorder(new Color(253, 121, 65), 2));
        btnSalesRep.setForeground(Color.BLACK);
        btnSalesRep.setBorder(BorderFactory.createLineBorder(new Color(252, 131, 62), 2));
        btnCustomer4.setForeground(Color.WHITE);
        btnCustomer4.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnProduct.setForeground(Color.BLACK);
        btnProduct.setBorder(BorderFactory.createLineBorder(new Color(251, 159, 53), 2));
    }//GEN-LAST:event_btnCustomer4MouseClicked

    private void btnProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductMouseClicked
        mainMenu.setSelectedIndex(9);
        btnHome.setForeground(Color.BLACK);
        btnHome.setBorder(BorderFactory.createLineBorder(new Color(254, 78, 80), 2));
        btnInventory.setForeground(Color.BLACK);
        btnInventory.setBorder(BorderFactory.createLineBorder(new Color(254, 102, 72), 2));
        btnOrder.setForeground(Color.BLACK);
        btnOrder.setBorder(BorderFactory.createLineBorder(new Color(253, 121, 65), 2));
        btnSalesRep.setForeground(Color.BLACK);
        btnSalesRep.setBorder(BorderFactory.createLineBorder(new Color(252, 131, 62), 2));
        btnCustomer4.setForeground(Color.BLACK);
        btnCustomer4.setBorder(BorderFactory.createLineBorder(new Color(252, 143, 58), 2));
        btnProduct.setForeground(Color.WHITE);
        btnProduct.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    }//GEN-LAST:event_btnProductMouseClicked

    private void btnAddUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddUpdateMouseClicked
        mainMenu.setSelectedIndex(2);
    }//GEN-LAST:event_btnAddUpdateMouseClicked

    private void btnAddUpdate1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddUpdate1MouseClicked
        mainMenu.setSelectedIndex(3);
    }//GEN-LAST:event_btnAddUpdate1MouseClicked

    private void btnInvUpdateAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvUpdateAddMouseClicked
        // TODO add your handling code here:
        String proId = txtinvUpdateProId.getText().trim();
        String pname = txtinvUpdateProName.getText().trim();
        String unitPrice = txtinvUpdateUnitPrice.getText().trim();
        String producttag = comboinvUpdateCategory.getSelectedItem().toString();
        Date createDate = convertutilltosql(jdateinvUpdateCreateDate.getDate());

        if (proId.matches("Auto generated")) {
            sql = "insert into products(name,unit_price,product_tag, entry_date)"
                    + " values(?,?,?,?)";

            try {
                ps = dbCon.getCon().prepareStatement(sql);
                ps.setString(1, pname);
                ps.setFloat(2, Float.parseFloat(unitPrice));
                ps.setString(3, producttag);
                ps.setDate(4, createDate);

                ps.executeUpdate();
                ps.close();
                dbCon.getCon().close();
                JOptionPane.showMessageDialog(rootPane, "Data saved in products table");
                getAllProducts();
//                addProductToStock();
                setProductnametoPurchaseCombo(comboPurchaseProductName);
            } catch (SQLException ex) {
                Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Only update/delete operation is allowed for old products");
        }

    }//GEN-LAST:event_btnInvUpdateAddMouseClicked

    private void btnInvUpdateResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvUpdateResetMouseClicked
        // TODO add your handling code here:
        setbtnInvUpdateReset();
    }//GEN-LAST:event_btnInvUpdateResetMouseClicked

    private void btnInvUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvUpdateMouseClicked
        // TODO add your handling code here:
        String proId = txtinvUpdateProId.getText().trim();
        if (proId.matches("Auto generated")) {
            JOptionPane.showMessageDialog(rootPane, "Only save operation is allowed for new produnt");
        } else {
            sql = "update products set name=?,unit_price=?,product_tag= ? where idproducts = ?";
            try {
                ps = dbCon.getCon().prepareStatement(sql);
                ps.setString(1, txtinvUpdateProName.getText().trim());
                ps.setFloat(2, Float.parseFloat(txtinvUpdateUnitPrice.getText().trim()));
                ps.setString(3, comboinvUpdateCategory.getSelectedItem().toString());
                ps.setInt(4, Integer.parseInt(txtinvUpdateProId.getText().trim()));

                ps.executeUpdate();
                ps.close();
                dbCon.getCon().close();
                JOptionPane.showMessageDialog(rootPane, "product updated in products table");
                setbtnInvUpdateReset();
                getAllProducts();
            } catch (SQLException ex) {
                Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInvUpdateDeleteMouseClicked

    private void btnInvPurchaseProductAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvPurchaseProductAddMouseClicked
        // TODO add your handling code here:

        String pname = comboPurchaseProductName.getSelectedItem().toString();
        float unitPrice = Float.parseFloat(txtProductPurchaseUnitPrice.getText().trim());
        float quentity = Float.parseFloat(spanProductPurchaseQuentity.getValue().toString());
        float totalPrice = Float.parseFloat(txtProductTotalPrice.getText());
        Date purchaseDate = convertutilltosql(jdatePurchaseProduct.getDate());

        sql = "insert into purchases(product_name,quentity,unit_price,total_price, purchase_date)"
                + " values(?,?,?,?,?)";

        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setString(1, pname);
            ps.setFloat(2, quentity);
            ps.setFloat(3, unitPrice);

            ps.setFloat(4, totalPrice);
            ps.setDate(5, purchaseDate);
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
            JOptionPane.showMessageDialog(rootPane, "Data saved in purchases table");
            updateProductToStock();
            setProductnametoPurchaseCombo(comboPurchaseProductName);
            getAllPurchaseProduct(jTablePurchaseProduct);
//            getTodayPurchase();
//            getTotalPurchase();
//            getMonthlyPurchase();

        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnInvPurchaseProductAddMouseClicked

    private void btnInvPurchaseProductResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInvPurchaseProductResetMouseClicked
        // TODO add your handling code here:
        //

        setInvPurchaeProductReset();
    }//GEN-LAST:event_btnInvPurchaseProductResetMouseClicked

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
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btncustomerSearchMouseClicked

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
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnCustomerSaveMouseClicked

    private void btnCustomerUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerUpdateMouseClicked
        String name = txtCustomerInfoName.getText().trim();
        String cell = txtCustomerInfoCell.getText().trim();
        String district = comboCustomerDistrict.getSelectedItem().toString();
        String address = txtCustomerInfoAddress.getText().trim();
        Date createdDate = convertutilltosql(jDateCustomerInfo.getDate());
        int customerid = Integer.parseInt(txtCustomerInfoId.getText().trim());
        sql = "update customers set name=?,cell=?,district=?,address=?,created_date=? where idcustomers=?";

        try {
            ps = dbCon.getCon().prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, cell);
            ps.setString(3, district);
            ps.setString(4, address);
            ps.setDate(5, createdDate);
            ps.setInt(6, customerid);
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
            JOptionPane.showMessageDialog(rootPane, "Data updated in customers table");
            getAllcustomers();
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnCustomerUpdateMouseClicked

    private void btnCustomerResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerResetMouseClicked
        // TODO add your handling code here:
        setbtnCustomerReset();
    }//GEN-LAST:event_btnCustomerResetMouseClicked
    String customerId, deliverId;

    public String setdeliveryId(String customerId) {
        SimpleDateFormat fromt = new SimpleDateFormat("ddMM");

        String date = fromt.format(sqltoday);

        int idnum = (int) (Math.random() * 100);
        String deliveryId = "de" + date + "cId" + customerId + "r" + idnum;
        return deliveryId;
    }
    private void btnCustomerNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerNextMouseClicked
        // TODO add your handling code here:
        customerId = txtCustomerInfoId.getText();
        txtbillCustomerId.setText(customerId);
        deliverId = setdeliveryId(customerId);
        txtbillDeliveryId.setText(deliverId);
//
        mainMenu.setSelectedIndex(5);
        customerId = "";
        deliverId = "";
        getAllProductsForSell();
    }//GEN-LAST:event_btnCustomerNextMouseClicked

    private void btnAddUpdate2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddUpdate2MouseClicked
        mainMenu.setSelectedIndex(10);
    }//GEN-LAST:event_btnAddUpdate2MouseClicked

    private void btnbillinfoAddToCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbillinfoAddToCartMouseClicked

//        DefaultTableModel model = (DefaultTableModel) jTableCart.getModel();
//        String productName = txtbillProductName.getText();
//        float unitPrice = Float.parseFloat(txtbillunitPrice.getText());
//        float quantity = Float.parseFloat(spanbillquentity.getValue().toString());
//        float discount = Float.parseFloat(txtbillDiscount.getText());
//        float actualPrice = Float.parseFloat(txtbillFinalPrice.getText());
//        Date date = convertutilltosql(jDatebillSalesDate.getDate());
//
//        totalPriceNext = actualPrice + totalPriceNext;
//        txtbillTotalPaymentNext.setText(totalPriceNext + "");
//        List<Object> productList = new ArrayList<>();
//
//        productList.add(new Object[]{productName, unitPrice, quantity, discount, actualPrice, date});
//
//        int row = model.getRowCount();
//        for (Object i : productList) {
//            //both method works.
//            model.addRow((Object[]) i);
//            //            model.insertRow(row, (Object[]) i);
//        }

    }//GEN-LAST:event_btnbillinfoAddToCartMouseClicked

    private void btnillInfoResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnillInfoResetMouseClicked
        // TODO add your handling code here:
        setbtnillInfoReset();
    }//GEN-LAST:event_btnillInfoResetMouseClicked

    private void tableInvUpdateProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInvUpdateProductMouseClicked
//        int rowIndex = tableInvUpdateProduct.getSelectedRow();
//
//        String produntId = tableInvUpdateProduct.getModel().getValueAt(rowIndex, 0).toString();
//        String produntname = tableInvUpdateProduct.getModel().getValueAt(rowIndex, 1).toString();
////        String produntQuentity = tableInvUpdateProdunt.getModel().getValueAt(rowIndex,2).toString();
//        String produntUnitPrice = tableInvUpdateProduct.getModel().getValueAt(rowIndex, 3).toString();
//        String productag = tableInvUpdateProduct.getModel().getValueAt(rowIndex, 4).toString();
//        String createDate = tableInvUpdateProduct.getModel().getValueAt(rowIndex, 5).toString();
//
////        System.out.println(createDate);
//        txtinvUpdateProId.setText(produntId);
//        txtinvUpdateProName.setText(produntname);
//        txtinvUpdateUnitPrice.setText(produntUnitPrice);
//        comboinvUpdateCategory.setSelectedItem(productag);
//        jdateinvUpdateCreateDate.setDate(formatStringdateToUtilDate(createDate));
    }//GEN-LAST:event_tableInvUpdateProductMouseClicked

    private void txtProductTotalPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtProductTotalPriceMouseClicked
        float totalAmount = getbillTotalPrice(txtProductPurchaseUnitPrice, spanProductPurchaseQuentity);
//        txtProductTotalPrice.setText(totalAmount+"");
    }//GEN-LAST:event_txtProductTotalPriceMouseClicked

    private void tableBillInfoProductDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBillInfoProductDetailsMouseClicked
        // TODO add your handling code here:
        int rowIndex = tableBillInfoProductDetails.getSelectedRow();
//
        txtbillProductName.setText(tableBillInfoProductDetails.getModel().getValueAt(rowIndex, 0).toString());
        txtbillProductItem.setText(tableBillInfoProductDetails.getModel().getValueAt(rowIndex, 1).toString());
        txtbillunitPrice.setText(tableBillInfoProductDetails.getModel().getValueAt(rowIndex, 3).toString());

    }//GEN-LAST:event_tableBillInfoProductDetailsMouseClicked

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

    private void txtbillunitPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtbillunitPriceFocusLost
        // TODO add your handling code here:
//
//        try {
////            if (!(txtbillunitPrice.getText().trim().isEmpty())) {
//
//            } else {
//                JOptionPane.showMessageDialog(rootPane, "Unit price can not be empty");
////                txtbillunitPrice.requestFocus();
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(rootPane, "An Error ocured" + e.getMessage()
//            );
//        }
    }//GEN-LAST:event_txtbillunitPriceFocusLost

    private void txtbillDiscountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtbillDiscountFocusLost
        // TODO add your handling code here:
//        float finalPrice = getbillAutualPrice();
//        txtbillFinalPrice.setText(finalPrice + "");
    }//GEN-LAST:event_txtbillDiscountFocusLost

    private void txtbillTotalPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtbillTotalPriceMouseClicked
        // TODO add your handling code here:
        float quentity = Float.parseFloat(spanbillquentity.getValue().toString());
        if (quentity > 0) {
            float toatPrice = getbillTotalPrice(txtbillunitPrice, spanbillquentity);
            txtbillTotalPrice.setText(toatPrice + "");
        } else {
            JOptionPane.showMessageDialog(panShopName, "Enter product qty.");
        }

//        
    }//GEN-LAST:event_txtbillTotalPriceMouseClicked
    float totalPriceNext = 0.00f;
    private void btnbillinfoAddToCart1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbillinfoAddToCart1MouseClicked
        // TODO add your handling code here:

        DefaultTableModel model = (DefaultTableModel) jTableCart.getModel();
        String productName = txtbillProductName.getText();
        String proitem = txtbillProductItem.getText();
        float unitPrice = Float.parseFloat(txtbillunitPrice.getText());
        float quantity = Float.parseFloat(spanbillquentity.getValue().toString());
        float discount = Float.parseFloat(txtbillDiscount.getText());
        float actualPrice = Float.parseFloat(txtbillFinalPrice.getText());
        Date date = convertutilltosql(jDatebillSalesDate.getDate());

        totalPriceNext = actualPrice + totalPriceNext;
        txtbillTotalPaymentNext.setText(totalPriceNext + "");
        //        List<Object> productList = new ArrayList<>();
        List<Object> productList = new ArrayList<>();
        productList.add(new Object[]{productName, proitem, unitPrice, quantity, discount, actualPrice, date});

        int row = model.getRowCount();
        for (Object i : productList) {
            //both method works.
            model.addRow((Object[]) i);
            //            model.insertRow(row, (Object[]) i);
        }


    }//GEN-LAST:event_btnbillinfoAddToCart1MouseClicked

    private void btnillInfoReset1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnillInfoReset1MouseClicked
        // TODO add your handling code here:
        setbtnillInfoReset();
    }//GEN-LAST:event_btnillInfoReset1MouseClicked

    private void btnBillSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBillSaveMouseClicked
        // TODO add your handling code here:
        int rowCount = jTableCart.getRowCount();
        System.out.println(rowCount);
        for (int i = 0; i < rowCount; i++) {
            String productN = jTableCart.getModel().getValueAt(i, 0).toString();
            String proitem = jTableCart.getModel().getValueAt(i, 1).toString();
            float unitp = Float.parseFloat(jTableCart.getModel().getValueAt(i, 2).toString());
            float sellQ = Float.parseFloat(jTableCart.getModel().getValueAt(i, 3).toString());

            float discount1 = Float.parseFloat(jTableCart.getModel().getValueAt(i, 4).toString());
            float actualP = Float.parseFloat(jTableCart.getModel().getValueAt(i, 5).toString());
            String dateSt = jTableCart.getModel().getValueAt(i, 6).toString();
            java.util.Date utilsalesDate = formatStringdateToUtilDate(dateSt);
            int cutomerId = Integer.parseInt(txtbillCustomerId.getText());
            String deliverycode = txtbillDeliveryId.getText();
            jDatebillSalesDate.setDate(formatStringdateToUtilDate(dateSt));
            Date salesDate = convertutilltosql(utilsalesDate);
            //            System.out.println("pN " + productN + " uP " + unitp + " sQ " + sellQ + " aP " + actualP + " dis " + discount1 + " date " + dateSt);
            //            System.out.println(utilsalesDate);
            //            System.out.println(salesDate);
            sql = "insert into sales(product_name,item,purchase_quentity, actual_price, discount,sales_date,unit_price,customer_id,delivery_code)"
                    + " values(?,?,?,?,?,?,?,?,?)";

            try {
                ps = dbCon.getCon().prepareStatement(sql);
                ps.setString(1, productN);
                ps.setString(2, proitem);
                ps.setFloat(3, sellQ);
                ps.setFloat(4, actualP);
                ps.setFloat(5, discount1);
                ps.setDate(6, salesDate);
                ps.setFloat(7, unitp);
                ps.setInt(8, cutomerId);
                ps.setString(9, deliverycode);

                ps.executeUpdate();
                ps.close();
                dbCon.getCon().close();
                subtractProductFromStock();
//
//                getTodaySales();
//                getTotalSales();
//                getMonthlySales();
            } catch (SQLException ex) {
                Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JOptionPane.showMessageDialog(rootPane, "Data saved in sales table");
        setDeliveryCode();
        //        DefaultTableModel tableModel = (DefaultTableModel) jTableCart.getModel();
        //        while (tableModel.getRowCount() > 0) {
        //            tableModel.removeRow(0);
        //        }

    }//GEN-LAST:event_btnBillSaveMouseClicked

    private void btnbillDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbillDeleteMouseClicked
        // TODO add your handling code here:
        DefaultTableModel tableModel = (DefaultTableModel) jTableCart.getModel();
        int rowindex = jTableCart.getSelectedRow();
        tableModel.removeRow(rowindex);
    }//GEN-LAST:event_btnbillDeleteMouseClicked

    private void txtpaymentDueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpaymentDueMouseClicked
        // TODO add your handling code here:
        if (txtpaymentTotal.getText().isEmpty() && txtpaymentPaid.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "insert total and paid amount");

        } else {
            float due = Float.parseFloat(txtpaymentTotal.getText()) - Float.parseFloat(txtpaymentPaid.getText());
            txtpaymentDue.setText(due + "");
        }
    }//GEN-LAST:event_txtpaymentDueMouseClicked

    private void txtpaymentTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpaymentTotalMouseClicked
        // TODO add your handling code here:
        if (txtbillTotalPaymentNext.getText().isEmpty() && txtpaymentDeliveryCharge.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Input payment and delivey Charge");

        } else {
            float totalpay = Float.parseFloat(txtbillTotalPaymentNext.getText()) + Float.parseFloat(txtpaymentDeliveryCharge.getText());
            txtpaymentTotal.setText(totalpay + "");
        }
    }//GEN-LAST:event_txtpaymentTotalMouseClicked

    private void txtpaymentDueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpaymentDueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpaymentDueActionPerformed

    private void btnpaymentSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnpaymentSaveMouseClicked
        // TODO add your handling code here:
//        if (flag == 1) {
//
        float total = Float.parseFloat(txtpaymentTotal.getText());
        float paid = Float.parseFloat(txtpaymentPaid.getText());
        float due = Float.parseFloat(txtpaymentDue.getText());
        float deliveryCharge = Float.parseFloat(txtpaymentDeliveryCharge.getText());
        String deliveryCode = txtbillDeliveryId.getText();
        String deliveryperson = combopaymentDeliverycompany.getSelectedItem().toString();
        String paymentOption = comboPaymentOption.getSelectedItem().toString();
        Date deliverydate = convertutilltosql(jDatepaymentdeliveyDate.getDate());
        sql = "insert into payment(delivery_code,payment,paid,due,delivery_charge,payment_option,delivery_person,delivery_date)values(?,?,?,?,?,?,?,?)";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setString(1, deliveryCode);
            ps.setFloat(2, total);
            ps.setFloat(3, paid);
            ps.setFloat(4, due);
            ps.setFloat(5, deliveryCharge);

            ps.setString(6, paymentOption);
            ps.setString(7, deliveryperson);
            ps.setDate(8, deliverydate);
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();
            JOptionPane.showMessageDialog(rootPane, "Order complete");

        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

//        } else {
//            JOptionPane.showMessageDialog(rootPane, "Select Delivery info ");
//        }
    }//GEN-LAST:event_btnpaymentSaveMouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        printInvoice();
    }//GEN-LAST:event_jButton3MouseClicked

    private void txtbillFinalPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtbillFinalPriceMouseClicked
        if (txtbillTotalPrice.getText() != null) {
            System.out.println("true");
            float finalPrice = getbillAutualPrice();
            txtbillFinalPrice.setText(finalPrice + "");
        } else {
            JOptionPane.showMessageDialog(panShopName, "Enter Total Price.");
        }
    }//GEN-LAST:event_txtbillFinalPriceMouseClicked

    private void txtpaymentDeliveryChargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpaymentDeliveryChargeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpaymentDeliveryChargeActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        java.util.Date fromDate = jDateReportFromDate.getDate();
        java.util.Date toDate = jDateReportToDate.getDate();
        if (radioReportPurchase.isSelected()) {
            getPurchaseReport(fromDate, toDate);
        } else if (radioReportSales.isSelected()) {
            getSalesReport(fromDate, toDate);

        } else {
            JOptionPane.showMessageDialog(rootPane, "Select Purchase/Sales to see report");
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        jDateReportFromDate.setDate(null);
        jDateReportToDate.setDate(null);
        buttonGroup1.clearSelection();
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnCustomerReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerReportMouseClicked
        // TODO add your handling code here:
        int comboIndex = comboCustomerProduct.getSelectedIndex();
        if (radioCustomerReport.isSelected()) {
            String[] customerColumn = {"CustomerId", "Product Name", "Order"};
            DefaultTableModel cusModel = new DefaultTableModel();
            cusModel.setColumnIdentifiers(customerColumn);
            tableCustomerReport.setModel(cusModel);
            if (comboIndex == 1) {
                sql = "select customer_id,product_name,count(idsales) from sales group by customer_id,product_name;";
                try {
                    ps = dbCon.getCon().prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String pName = rs.getString(2);
                        float countId = rs.getFloat(3);

                        cusModel.addRow(new Object[]{id, pName, countId});

                    }
                    rs.close();
                    ps.close();
                    dbCon.getCon().close();
                } catch (SQLException ex) {
                    Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (comboIndex == 2) {
                sql = "select customer_id,product_name, sum(actual_price) from sales group by customer_id,product_name";
                try {
                    ps = dbCon.getCon().prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String pName = rs.getString(2);
                        float countId = rs.getFloat(3);

                        cusModel.addRow(new Object[]{id, pName, countId});

                    }
                    rs.close();
                    ps.close();
                    dbCon.getCon().close();
                } catch (SQLException ex) {
                    Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Select a option to see report.");
            }

        } else {
            JOptionPane.showMessageDialog(rootPane, "Select a radio button to see report.");
        }
    }//GEN-LAST:event_btnCustomerReportMouseClicked

    private void radioCustomerReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioCustomerReportMouseClicked
        // TODO add your handling code here:
        comboCustomerProduct.removeAllItems();
        if (radioCustomerReport.isSelected()) {
            comboCustomerProduct.addItem("---Select One--");
            comboCustomerProduct.addItem("Repeat Customer");
            comboCustomerProduct.addItem("Most Spend Customer");
        }
    }//GEN-LAST:event_radioCustomerReportMouseClicked

    private void radioProductReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioProductReportMouseClicked
        // TODO add your handling code here:
        comboProductList.removeAllItems();
        if (radioProductReport.isSelected()) {
            comboProductList.addItem("---Select One--");
            comboProductList.addItem("Most Frequent sold products");
            comboProductList.addItem("Most valuable products");
        }
    }//GEN-LAST:event_radioProductReportMouseClicked

    private void btnProductReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductReportMouseClicked
        // TODO add your handling code here:
        int comboIndex = comboProductList.getSelectedIndex();

        if (radioProductReport.isSelected()) {
            String[] produntColumn = {"ProduntId ", "Unit Price", "Amount"};
            DefaultTableModel proModel = new DefaultTableModel();
            proModel.setColumnIdentifiers(produntColumn);
            tableProductReport.setModel(proModel);
            if (comboIndex == 1) {
                sql = "select product_name,unit_price,count(idsales) from sales group by product_name,unit_price";
                try {
                    ps = dbCon.getCon().prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        //                        int id = rs.getInt(1);
                        String pName = rs.getString(1);
                        float unitPrice = rs.getFloat(2);
                        float amount = rs.getFloat(3);

                        proModel.addRow(new Object[]{pName, unitPrice, amount});

                    }
                    rs.close();
                    ps.close();
                    dbCon.getCon().close();
                } catch (SQLException ex) {
                    Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (comboIndex == 2) {
                sql = "select product_name,unit_price, sum(actual_price) from sales group by product_name,unit_price;";
                try {
                    ps = dbCon.getCon().prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        //                        int id = rs.getInt(1);
                        String pName = rs.getString(1);
                        float unitPrice = rs.getFloat(2);
                        float amount = rs.getFloat(3);

                        proModel.addRow(new Object[]{pName, unitPrice, amount});

                    }
                    rs.close();
                    ps.close();
                    dbCon.getCon().close();
                } catch (SQLException ex) {
                    Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Select a option to see report.");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Select a radio button to see report.");
        }
    }//GEN-LAST:event_btnProductReportMouseClicked

    private void btnDeliveryRepResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeliveryRepResetMouseClicked
        setbtnDeliveryReportReset();
    }//GEN-LAST:event_btnDeliveryRepResetMouseClicked

    private void btnDeliveryRepUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeliveryRepUpdateMouseClicked
        int id = Integer.parseInt(txtDeliveryRepDId.getText());
        String delivery_code = txtDeliveryRepDCode.getText();
        float payment = Float.parseFloat(txtDeliveryRepPayment.getText());
        float due = Float.parseFloat(txtDeliveryRepDue.getText());
        float newpaid = Float.parseFloat(txtDeliveryRepPaid.getText().trim());
        String deliverystatus = comboDeliveryRepStatus.getSelectedItem().toString();
        Date deliverydate = convertutilltosql(jDateDeliveryRepDeliveryDate.getDate());

        sql = "update payment set paid=paid+? ,due=? where payment_id=?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);

            ps.setFloat(1, newpaid);
            ps.setFloat(2, 0);
            ps.setInt(3, id);
            ps.close();
            dbCon.getCon().close();

            JOptionPane.showMessageDialog(rootPane, "Payment updated");
            getAllPaymnet(jtableDeliveryReport);
        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeliveryRepUpdateMouseClicked

    private void jtableDeliveryReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableDeliveryReportMouseClicked
        int row = jtableDeliveryReport.getSelectedRow();

        String id = jtableDeliveryReport.getModel().getValueAt(row, 0).toString();
        String delivery_code = jtableDeliveryReport.getModel().getValueAt(row, 1).toString();
        String payment = jtableDeliveryReport.getModel().getValueAt(row, 2).toString();
        String due = jtableDeliveryReport.getModel().getValueAt(row, 3).toString();
        String deliverystatus = jtableDeliveryReport.getModel().getValueAt(row, 4).toString();
        //        java.util.Date deliverydate = formatStringdateToUtilDate(jtableDeliveryReport.getModel().getValueAt(row, 6).toString());
        txtDeliveryRepDId.setText(id);
        txtDeliveryRepDCode.setText(delivery_code);
        txtDeliveryRepPayment.setText(payment);
        txtDeliveryRepDue.setText(due);
        //        txtDeliveryRepPaid.setText(null);
        comboDeliveryRepStatus.setSelectedItem(deliverystatus);
        //        jDateDeliveryRepDeliveryDate.setDate(deliverydate);
    }//GEN-LAST:event_jtableDeliveryReportMouseClicked

    private void btnDeliveryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeliveryMouseClicked
       mainMenu.setSelectedIndex(10);
               
    }//GEN-LAST:event_btnDeliveryMouseClicked

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
            java.util.logging.Logger.getLogger(SMShopManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SMShopManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SMShopManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SMShopManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SMShopManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Home;
    private javax.swing.JPanel ProductList;
    private javax.swing.JPanel addProduct;
    private javax.swing.JPanel billing;
    private javax.swing.JLabel billingfrom;
    private javax.swing.JButton btnAddUpdate;
    private javax.swing.JButton btnAddUpdate1;
    private javax.swing.JButton btnAddUpdate2;
    private javax.swing.JButton btnBillSave;
    private javax.swing.JLabel btnCustomer4;
    private javax.swing.JButton btnCustomerNext;
    private javax.swing.JButton btnCustomerReport;
    private javax.swing.JButton btnCustomerReset;
    private javax.swing.JButton btnCustomerSave;
    private javax.swing.JButton btnCustomerUpdate;
    private javax.swing.JLabel btnDelivery;
    private javax.swing.JButton btnDeliveryRepReset;
    private javax.swing.JButton btnDeliveryRepUpdate;
    private javax.swing.JLabel btnHome;
    private javax.swing.JButton btnInvPurchaseProductAdd;
    private javax.swing.JButton btnInvPurchaseProductReset;
    private javax.swing.JButton btnInvUpdate;
    private javax.swing.JButton btnInvUpdateAdd;
    private javax.swing.JButton btnInvUpdateDelete;
    private javax.swing.JButton btnInvUpdateReset;
    private javax.swing.JLabel btnInventory;
    private javax.swing.JLabel btnOrder;
    private javax.swing.JLabel btnProduct;
    private javax.swing.JButton btnProductReport;
    private javax.swing.JLabel btnSalesRep;
    private javax.swing.JButton btnbillDelete;
    private javax.swing.JButton btnbillinfoAddToCart;
    private javax.swing.JButton btnbillinfoAddToCart1;
    private javax.swing.JButton btncustomerSearch;
    private javax.swing.JButton btnillInfoReset;
    private javax.swing.JButton btnillInfoReset1;
    private javax.swing.JButton btnpaymentSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboCustomerDistrict;
    private javax.swing.JComboBox<String> comboCustomerProduct;
    private javax.swing.JComboBox<String> comboDeliveryRepStatus;
    private javax.swing.JComboBox<String> comboPaymentOption;
    private javax.swing.JComboBox<String> comboProductList;
    private javax.swing.JComboBox<String> comboPurchaseProductName;
    private javax.swing.JComboBox<String> comboinvUpdateCategory;
    private javax.swing.JComboBox<String> combopaymentDeliverycompany;
    private javax.swing.JPanel customerList;
    private javax.swing.JPanel deliveryUpdate;
    private javax.swing.JPanel inventory;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateCustomerInfo;
    private com.toedter.calendar.JDateChooser jDateDeliveryRepDeliveryDate;
    private com.toedter.calendar.JDateChooser jDateProReportFromDate2;
    private com.toedter.calendar.JDateChooser jDateProReportToDate;
    private com.toedter.calendar.JDateChooser jDateReportFromDate;
    private com.toedter.calendar.JDateChooser jDateReportFromDate1;
    private com.toedter.calendar.JDateChooser jDateReportToDate;
    private com.toedter.calendar.JDateChooser jDateReportToDate1;
    private com.toedter.calendar.JDateChooser jDatebillSalesDate;
    private com.toedter.calendar.JDateChooser jDatepaymentdeliveyDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableCart;
    private javax.swing.JTable jTablePurchaseProduct;
    private javax.swing.JTable jTableReport;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private com.toedter.calendar.JDateChooser jdatePurchaseProduct;
    private com.toedter.calendar.JDateChooser jdateinvUpdateCreateDate;
    private javax.swing.JTable jtableCustomerInfo;
    private javax.swing.JTable jtableDeliveryReport;
    private javax.swing.JLabel lblTodayDelivery;
    private javax.swing.JLabel lblTodayPurchase;
    private javax.swing.JLabel lblTodaySales;
    private javax.swing.JLabel lblTotalDelivery;
    private javax.swing.JLabel lblTotalPurchase;
    private javax.swing.JLabel lblTotalSales;
    private javax.swing.JLabel lbltodayDelivery;
    private javax.swing.JLabel lbltodayPurchaseDigit;
    private javax.swing.JLabel lbltodaySalesDigit;
    private javax.swing.JLabel lbltotalPurchaseDigit;
    private javax.swing.JLabel lbltotalSalesdigit;
    private javax.swing.JLabel lvldashboard;
    private javax.swing.JLabel lvlshopaddress;
    private javax.swing.JLabel lvlshopaddress2;
    private javax.swing.JLabel lvlshopname;
    private javax.swing.JLabel lvltestlogo;
    private javax.swing.JTabbedPane mainMenu;
    private javax.swing.JPanel order;
    private javax.swing.JPanel order4;
    private javax.swing.JPanel panDashboard;
    private javax.swing.JPanel panShopName;
    private javax.swing.JPanel payment;
    private javax.swing.JLabel purchaseLogo;
    private javax.swing.JLabel purchaseLogo1;
    private javax.swing.JPanel purchaseProduct;
    private javax.swing.JRadioButton radioCustomerReport;
    private javax.swing.JRadioButton radioProductReport;
    private javax.swing.JRadioButton radioReportPurchase;
    private javax.swing.JRadioButton radioReportSales;
    private javax.swing.JPanel salesRep;
    private javax.swing.JLabel saleslogo;
    private javax.swing.JLabel saleslogo1;
    private jframescrollbar.ScrollBarCustom scrollBarCustom1;
    private javax.swing.JLabel shippedlogo;
    private javax.swing.JLabel shippedlogo1;
    private javax.swing.JSpinner spanProductPurchaseQuentity;
    private javax.swing.JSpinner spanbillquentity;
    private javax.swing.JTable tableBillInfoProductDetails;
    private javax.swing.JTable tableCustomerReport;
    private javax.swing.JTable tableInvUpdateProduct;
    private javax.swing.JTable tableProductReport;
    private javax.swing.JTextArea txtCustomerInfoAddress;
    private javax.swing.JTextField txtCustomerInfoCell;
    private javax.swing.JTextField txtCustomerInfoId;
    private javax.swing.JTextField txtCustomerInfoName;
    private javax.swing.JTextField txtDeliveryRepDCode;
    private javax.swing.JTextField txtDeliveryRepDId;
    private javax.swing.JTextField txtDeliveryRepDue;
    private javax.swing.JTextField txtDeliveryRepPaid;
    private javax.swing.JTextField txtDeliveryRepPayment;
    private javax.swing.JTextField txtProductPurchaseProductId;
    private javax.swing.JTextField txtProductPurchaseUnitPrice;
    private javax.swing.JTextField txtProductTotalPrice;
    private javax.swing.JTextField txtbillCustomerId;
    private javax.swing.JTextField txtbillDeliveryId;
    private javax.swing.JTextField txtbillDiscount;
    private javax.swing.JTextField txtbillFinalPrice;
    private javax.swing.JTextField txtbillProductItem;
    private javax.swing.JTextField txtbillProductName;
    private javax.swing.JTextField txtbillTotalPaymentNext;
    private javax.swing.JTextField txtbillTotalPrice;
    private javax.swing.JTextField txtbillunitPrice;
    private javax.swing.JTextField txtinvUpdateProId;
    private javax.swing.JTextField txtinvUpdateProName;
    private javax.swing.JTextField txtinvUpdateUnitPrice;
    private javax.swing.JTextField txtpaymentDeliveryCharge;
    private javax.swing.JTextField txtpaymentDue;
    private javax.swing.JTextField txtpaymentPaid;
    private javax.swing.JTextField txtpaymentTotal;
    private javax.swing.JTextField txtproduntname;
    private javax.swing.JTextField txtqty;
    private javax.swing.JTextField txtunitp;
    // End of variables declaration//GEN-END:variables
}
