/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import InvoiceGenerate.InvoiceCreate;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
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
import java.util.Arrays;
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
    InvoiceCreate invoiceCreate = new InvoiceCreate();
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
        getAllPaymnet(jtableDeliveryReport);
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
        setTime();
    }

    private void setTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    java.util.Date date = new java.util.Date();
                    SimpleDateFormat tf = new SimpleDateFormat("h:mm:ss aa");
                    SimpleDateFormat df = new SimpleDateFormat("EEEE, dd-MM-yyyy");
                    String time = tf.format(date);
                    String dateT = df.format(date);
                    txtTime.setText(time.split(" ")[0] + " " + time.split(" ")[1]);
                    txtDateClock.setText(dateT);
                }
            }
        }).start();
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
        tableInvUpdateProduct.setModel(producttableModel);

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

    //show payment table data 
    public void getAllPaymnet(javax.swing.JTable table) {
        String[] columnNames = {"ID","Delivery code", "Payment", "Due", "Status","Date"};

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
                Date deliverydate = rs.getDate("delivery_date");

                purchasetableModel.addRow(new Object[]{id,delivery_code, payment, due,deliverystatus,deliverydate});
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

    public void subtractProductFromStock(String proName,float quantity) {
//         quantity = Float.parseFloat(spanbillquentity.getValue().toString());
//         proName = txtbillProductName.getText();
        sql = "update product_stock set quantity=quantity-? where product_name=?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
            ps.setFloat(1, quantity);
            ps.setString(2, proName);
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
        txtbillDiscount.setText("0");
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

    private void setbtnDeliveryReportReset() {
        txtDeliveryRepDId.setText(null);
        txtDeliveryRepDCode.setText(null);
        txtDeliveryRepPayment.setText(null);
        txtDeliveryRepDue.setText(null);
        txtDeliveryRepPaid.setText(null);
        comboDeliveryRepStatus.setSelectedIndex(0);
        jDateDeliveryRepDeliveryDate.setDate(null);

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

    public void printInvoice() {
//            String fileName= "invoice"+txtpaymentdeliveyCode.getText().trim();
        try {
            OutputStream file = new FileOutputStream(new File("Testfile.pdf"));
            Document doc = new Document();
            PdfWriter.getInstance(doc, file);
            PdfPTable irdTable = new PdfPTable(2);
            irdTable.addCell(invoiceCreate.getIrdCell("Invoice"));
            irdTable.addCell(invoiceCreate.getIrdCell("Date"));
            irdTable.addCell(invoiceCreate.getIrdCell(txtpaymentdeliveyCode.getText()));
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
            Image logo = Image.getInstance("assest/logo/demoSMlogo.png");
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
                String unitPrice = jTableCart.getModel().getValueAt(i, 1).toString();
                String qty = jTableCart.getModel().getValueAt(i, 2).toString();
                String total = jTableCart.getModel().getValueAt(i, 4).toString();

                billTable.addCell(invoiceCreate.getbillRowCell(index + ""));
                billTable.addCell(invoiceCreate.getbillRowCell("demo"));
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
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
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

        buttonGroupReport = new javax.swing.ButtonGroup();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        buttonGroupCustomerorProduct = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        txtDateClock = new javax.swing.JLabel();
        txtTime = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btndashborad = new javax.swing.JButton();
        btnInventory = new javax.swing.JButton();
        btnorderSales = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btnDeliveryReport = new javax.swing.JButton();
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
        tableInvUpdateProduct = new javax.swing.JTable();
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
        comboPaymentOption = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        txtpaymentPayment = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtpaymentdeliveyCode = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtpaymentTotal = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        txtpaymentPaid = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        txtpaymentDue = new javax.swing.JTextField();
        jDatepaymentdeliveyDate = new com.toedter.calendar.JDateChooser();
        btnpaymentSave = new javax.swing.JButton();
        btnpaymentDeliveySet = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtpaymentDeliveryAddress = new javax.swing.JTextArea();
        jLabel52 = new javax.swing.JLabel();
        txtpaymentDeliveryCharge = new javax.swing.JTextField();
        combopaymentDeliverycompany = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
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
        customerProduct = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jDateReportFromDate1 = new com.toedter.calendar.JDateChooser();
        jDateReportToDate1 = new com.toedter.calendar.JDateChooser();
        radioCustomerReport = new javax.swing.JRadioButton();
        radioProductReport = new javax.swing.JRadioButton();
        btnCustomerProductReport = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        tableCutomerProduntReport = new javax.swing.JTable();
        comboCustomerProduct = new javax.swing.JComboBox<>();
        deliveryReport = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jtableDeliveryReport = new javax.swing.JTable();
        jLabel54 = new javax.swing.JLabel();
        txtDeliveryRepDCode = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txtDeliveryRepDue = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        txtDeliveryRepPaid = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        comboDeliveryRepStatus = new javax.swing.JComboBox<>();
        jLabel58 = new javax.swing.JLabel();
        txtDeliveryRepPayment = new javax.swing.JTextField();
        btnDeliveryRepUpdate = new javax.swing.JButton();
        btnDeliveryRepReset = new javax.swing.JButton();
        jLabel59 = new javax.swing.JLabel();
        jDateDeliveryRepDeliveryDate = new com.toedter.calendar.JDateChooser();
        jLabel60 = new javax.swing.JLabel();
        txtDeliveryRepDId = new javax.swing.JTextField();
        tpLast1 = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();

        jScrollPane7.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(16, 171, 227));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("SM Shop Management");

        txtDateClock.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtDateClock.setForeground(new java.awt.Color(255, 255, 255));
        txtDateClock.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtDateClock.setText("jLabel53");

        txtTime.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtTime.setForeground(new java.awt.Color(255, 255, 255));
        txtTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTime.setText("jLabel53");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(240, Short.MAX_VALUE)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDateClock, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtDateClock, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 90));

        jPanel2.setBackground(new java.awt.Color(23, 105, 255));

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
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jButton4.setText("Customer List");

        jButton5.setText("Sales Report");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });

        btnDeliveryReport.setText("Delivery Report");
        btnDeliveryReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeliveryReportMouseClicked(evt);
            }
        });

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
                    .addComponent(btnDeliveryReport, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
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
                .addComponent(btnDeliveryReport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        lvlDashboard.setFont(new java.awt.Font("Rockwell", 1, 36)); // NOI18N
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

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        btnInventoryPurchase.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnInventoryPurchase.setText("Purchase Product");
        btnInventoryPurchase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInventoryPurchaseMouseClicked(evt);
            }
        });

        btnInventoryUpdate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
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
                .addGap(74, 74, 74)
                .addComponent(btnInventoryPurchase, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(btnInventoryUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        txtProductPurchaseProductId.setEditable(false);
        txtProductPurchaseProductId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Rockwell", 1, 36)); // NOI18N
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
        btnInvPurchaseProductAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/save.png"))); // NOI18N
        btnInvPurchaseProductAdd.setText("Add");
        btnInvPurchaseProductAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvPurchaseProductAddMouseClicked(evt);
            }
        });

        btnInvPurchaseProductReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvPurchaseProductReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/undo.png"))); // NOI18N
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
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnInvPurchaseProductAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(95, 95, 95)
                                .addComponent(btnInvPurchaseProductReset, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spanProductPurchaseQuentity)
                            .addComponent(txtProductTotalPrice)
                            .addComponent(jdatePurchaseProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(comboPurchaseProductName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtProductPurchaseUnitPrice)
                            .addComponent(txtProductPurchaseProductId))
                        .addGap(96, 96, 96)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProductPurchaseProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboPurchaseProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProductPurchaseUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spanProductPurchaseQuentity, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProductTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdatePurchaseProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInvPurchaseProductReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInvPurchaseProductAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        tpInvPurchaseProduct.addTab("tab1", jPanel4);

        menu.addTab("tab2", tpInvPurchaseProduct);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Rockwell", 1, 36)); // NOI18N
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

        btnInvUpdateDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvUpdateDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/delete.png"))); // NOI18N
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
        btnInvUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/update.png"))); // NOI18N
        btnInvUpdate.setText("Update");
        btnInvUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInvUpdateMouseClicked(evt);
            }
        });

        btnInvUpdateReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInvUpdateReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/undo.png"))); // NOI18N
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
        btnInvUpdateAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/save.png"))); // NOI18N
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
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(41, 41, 41)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtinvUpdatebuyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jdateinvUpdateCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(36, 36, 36)
                            .addComponent(txtinvUpdateUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtinvUpdateProName, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(36, 36, 36)
                        .addComponent(txtinvUpdateProId, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(btnInvUpdateAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnInvUpdateReset, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnInvUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btnInvUpdateDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(txtinvUpdateProId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtinvUpdateProName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtinvUpdateUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtinvUpdatebuyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jdateinvUpdateCreateDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInvUpdateDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInvUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInvUpdateReset, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInvUpdateAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        tpInvUpdateProduct.addTab("tab1", jPanel7);

        menu.addTab("tab4", tpInvUpdateProduct);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Rockwell", 1, 36)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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
        btnCustomerSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/save.png"))); // NOI18N
        btnCustomerSave.setText("Save");
        btnCustomerSave.setPreferredSize(new java.awt.Dimension(73, 23));
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
        btnCustomerReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/undo.png"))); // NOI18N
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
        btnCustomerNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/next.png"))); // NOI18N
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

        txtCustomerInfoId.setEditable(false);
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
        btnCustomerSave1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/update.png"))); // NOI18N
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
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCustomerInfoCell, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCustomerInfoName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCustomerInfoId, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btncustomerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboCustomerDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jDateCustomerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8)
                .addContainerGap())
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCustomerSave, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btnCustomerSave1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btnCustomerReset, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnCustomerNext, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboCustomerDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtCustomerInfoId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(txtCustomerInfoCell, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCustomerInfoName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateCustomerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncustomerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCustomerNext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerSave1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerSave, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerReset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        tpOrder.addTab("tab1", jPanel8);

        menu.addTab("tab6", tpOrder);

        tpBillingInfo.setMinimumSize(new java.awt.Dimension(805, 552));
        tpBillingInfo.setPreferredSize(new java.awt.Dimension(805, 552));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
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
        txtbillDiscount.setText("0");
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

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(23, 102, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Cart");
        jPanel9.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 271, 205, 30));

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(16, 171, 227));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Product List");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 196, 30));

        btnBillPayment.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnBillPayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/payment.png"))); // NOI18N
        btnBillPayment.setText("Payment");
        btnBillPayment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBillPaymentMouseClicked(evt);
            }
        });
        jPanel9.add(btnBillPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 460, 110, 40));

        btnbillDelete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnbillDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/delete.png"))); // NOI18N
        btnbillDelete.setText("Delete");
        btnbillDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnbillDeleteMouseClicked(evt);
            }
        });
        jPanel9.add(btnbillDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 460, 100, 40));

        btnillInfoReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnillInfoReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/undo.png"))); // NOI18N
        btnillInfoReset.setText("Reset");
        btnillInfoReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnillInfoResetMouseClicked(evt);
            }
        });
        jPanel9.add(btnillInfoReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 460, 110, 40));

        btnBillSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBillSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/save.png"))); // NOI18N
        btnBillSave.setText("Save");
        btnBillSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBillSaveMouseClicked(evt);
            }
        });
        jPanel9.add(btnBillSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 460, 110, 40));

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
        btnbillinfoAddToCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/add-to-cart.png"))); // NOI18N
        btnbillinfoAddToCart.setText("Add to Cart");
        btnbillinfoAddToCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnbillinfoAddToCartMouseClicked(evt);
            }
        });
        jPanel9.add(btnbillinfoAddToCart, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 130, 40));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setText("Customer ID");
        jPanel9.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 80, 29));

        txtbillCustomerId.setEditable(false);
        jPanel9.add(txtbillCustomerId, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 160, 30));

        txtbillDeliveryId.setEditable(false);
        jPanel9.add(txtbillDeliveryId, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 160, 30));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setText("Delivery Id");
        jPanel9.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 70, 29));

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText("Total Payment");
        jPanel9.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, 92, 29));
        jPanel9.add(txtbillTotalPaymentNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 400, 160, 30));

        tpBillingInfo.addTab("tab1", jPanel9);

        menu.addTab("tab7", tpBillingInfo);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Rockwell", 1, 36)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Payment Information");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel38.setText("Payment Option");

        comboPaymentOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select Payment method--", "Cash on Delivery", "Pay Online" }));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel39.setText("Payment");

        txtpaymentPayment.setText("Total Amount");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setText("Delivery code");

        txtpaymentdeliveyCode.setEditable(false);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setText("Delivey Address");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText("Delivery Date");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setText("Delivery Person");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setText("Total Payment");

        txtpaymentTotal.setText("0");
        txtpaymentTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpaymentTotalMouseClicked(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setText("Paid");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setText("Due");

        txtpaymentDue.setText("0");
        txtpaymentDue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpaymentDueMouseClicked(evt);
            }
        });

        btnpaymentSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnpaymentSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/payment.png"))); // NOI18N
        btnpaymentSave.setText("Proceed To Payment");
        btnpaymentSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnpaymentSaveMouseClicked(evt);
            }
        });

        btnpaymentDeliveySet.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnpaymentDeliveySet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/save.png"))); // NOI18N
        btnpaymentDeliveySet.setText("Save Delivery");
        btnpaymentDeliveySet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnpaymentDeliveySetMouseClicked(evt);
            }
        });

        txtpaymentDeliveryAddress.setColumns(20);
        txtpaymentDeliveryAddress.setRows(5);
        jScrollPane9.setViewportView(txtpaymentDeliveryAddress);

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel52.setText("Delivery Charge");

        combopaymentDeliverycompany.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Select --", "Delivery Person 01", "Delivery Person 02", "Delivery Person 03", " " }));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/printer.png"))); // NOI18N
        jButton2.setText("Print");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnpaymentDeliveySet, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtpaymentdeliveyCode)
                            .addComponent(jDatepaymentdeliveyDate, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                .addGap(7, 7, 7))
                            .addComponent(txtpaymentDeliveryCharge)
                            .addComponent(combopaymentDeliverycompany, 0, 150, Short.MAX_VALUE))))
                .addGap(38, 38, 38)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(comboPaymentOption, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtpaymentPayment)
                                .addComponent(txtpaymentTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtpaymentPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtpaymentDue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(80, 80, 80))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnpaymentSave, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))))
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtpaymentdeliveyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDatepaymentdeliveyDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(combopaymentDeliverycompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtpaymentDeliveryCharge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(comboPaymentOption, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtpaymentPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(txtpaymentTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(txtpaymentPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtpaymentDue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnpaymentDeliveySet, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnpaymentSave, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        tpPayment.addTab("tab1", jPanel10);

        menu.addTab("tab8", tpPayment);

        salesReport.setBackground(new java.awt.Color(255, 255, 255));
        salesReport.setMinimumSize(new java.awt.Dimension(805, 552));
        salesReport.setPreferredSize(new java.awt.Dimension(805, 552));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel32.setFont(new java.awt.Font("Rockwell", 1, 36)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Sales Report");

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
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
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
                                        .addComponent(jDateReportToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(radioReportstock)
                                        .addGap(61, 61, 61)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 15, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87)
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
                .addGap(36, 36, 36)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        salesReport.addTab("tab1", jPanel11);

        menu.addTab("tab9", salesReport);

        customerProduct.setBackground(new java.awt.Color(255, 255, 255));
        customerProduct.setMinimumSize(new java.awt.Dimension(805, 552));
        customerProduct.setPreferredSize(new java.awt.Dimension(805, 552));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel49.setFont(new java.awt.Font("Rockwell", 1, 36)); // NOI18N
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Report");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setText("From");

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel51.setText("To");

        buttonGroupCustomerorProduct.add(radioCustomerReport);
        radioCustomerReport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radioCustomerReport.setText("Customer");
        radioCustomerReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioCustomerReportMouseClicked(evt);
            }
        });

        buttonGroupCustomerorProduct.add(radioProductReport);
        radioProductReport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        radioProductReport.setText("Product");
        radioProductReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioProductReportMouseClicked(evt);
            }
        });

        btnCustomerProductReport.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerProductReport.setText("View");
        btnCustomerProductReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerProductReportMouseClicked(evt);
            }
        });

        tableCutomerProduntReport.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(tableCutomerProduntReport);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(radioCustomerReport)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(radioProductReport))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jDateReportFromDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jDateReportToDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(comboCustomerProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCustomerProductReport, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)))))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateReportFromDate1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateReportToDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioCustomerReport)
                    .addComponent(radioProductReport)
                    .addComponent(comboCustomerProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomerProductReport, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        customerProduct.addTab("tab1", jPanel12);

        menu.addTab("tab9", customerProduct);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Delivery Report");

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
        jtableDeliveryReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableDeliveryReportMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(jtableDeliveryReport);

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel54.setText("Delivery Code");

        txtDeliveryRepDCode.setEditable(false);
        txtDeliveryRepDCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel55.setText("Due");

        txtDeliveryRepDue.setEditable(false);
        txtDeliveryRepDue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel56.setText("Paid");

        txtDeliveryRepPaid.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel57.setText("Delivery Status");

        comboDeliveryRepStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--", "Order Confirmed", "Order Complete", " " }));

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel58.setText("Payment");

        txtDeliveryRepPayment.setEditable(false);
        txtDeliveryRepPayment.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnDeliveryRepUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDeliveryRepUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/update.png"))); // NOI18N
        btnDeliveryRepUpdate.setText("Update");
        btnDeliveryRepUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeliveryRepUpdateMouseClicked(evt);
            }
        });

        btnDeliveryRepReset.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDeliveryRepReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SemiCON/undo.png"))); // NOI18N
        btnDeliveryRepReset.setText("Reset");
        btnDeliveryRepReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeliveryRepResetMouseClicked(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel59.setText("Delivery Date");

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel60.setText("Id");

        txtDeliveryRepDId.setEditable(false);
        txtDeliveryRepDId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(160, 160, 160)
                                .addComponent(btnDeliveryRepUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77)
                                .addComponent(btnDeliveryRepReset, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32)
                                        .addComponent(txtDeliveryRepDId, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(32, 32, 32)
                                            .addComponent(txtDeliveryRepDCode, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(32, 32, 32)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtDeliveryRepPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtDeliveryRepDue, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtDeliveryRepPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboDeliveryRepStatus, 0, 158, Short.MAX_VALUE)
                                    .addComponent(jDateDeliveryRepDeliveryDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDeliveryRepDId, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboDeliveryRepStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateDeliveryRepDeliveryDate, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDeliveryRepDCode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDeliveryRepPayment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(txtDeliveryRepDue, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDeliveryRepPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeliveryRepUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeliveryRepReset, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        deliveryReport.addTab("tab1", jPanel5);

        menu.addTab("tab3", deliveryReport);

        jLabel53.setText("Last page");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(377, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(420, Short.MAX_VALUE))
        );

        tpLast1.addTab("tab1", jPanel13);

        menu.addTab("tab3", tpLast1);

        getContentPane().add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 810, 580));

        pack();
        setLocationRelativeTo(null);
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

    private void tableInvUpdateProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableInvUpdateProductMouseClicked
        // TODO add your handling code here:
        int rowIndex = tableInvUpdateProduct.getSelectedRow();

        String produntId = tableInvUpdateProduct.getModel().getValueAt(rowIndex, 0).toString();
        String produntname = tableInvUpdateProduct.getModel().getValueAt(rowIndex, 1).toString();
//        String produntQuentity = tableInvUpdateProdunt.getModel().getValueAt(rowIndex,2).toString();
        String produntUnitPrice = tableInvUpdateProduct.getModel().getValueAt(rowIndex, 3).toString();
        String produntbuyPrice = tableInvUpdateProduct.getModel().getValueAt(rowIndex, 4).toString();
        String createDate = tableInvUpdateProduct.getModel().getValueAt(rowIndex, 5).toString();

//        System.out.println(createDate);
        txtinvUpdateProId.setText(produntId);
        txtinvUpdateProName.setText(produntname);
        txtinvUpdateUnitPrice.setText(produntUnitPrice);
        txtinvUpdatebuyPrice.setText(produntbuyPrice);
        jdateinvUpdateCreateDate.setDate(formatStringdateToUtilDate(createDate));
    }//GEN-LAST:event_tableInvUpdateProductMouseClicked

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

            float discount1 = Float.parseFloat(jTableCart.getModel().getValueAt(i, 3).toString());
            float actualP = Float.parseFloat(jTableCart.getModel().getValueAt(i, 4).toString());
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
                ps.setString(8, deliverycode);

                ps.executeUpdate();
                ps.close();
                dbCon.getCon().close();
                

                getTodaySales();
                getTotalSales();
                getMonthlySales();
            } catch (SQLException ex) {
                Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            subtractProductFromStock(productN,sellQ);
        }
        JOptionPane.showMessageDialog(rootPane, "Data saved in smemanagement.sales table");
        setDeliveryCode();
//        DefaultTableModel tableModel = (DefaultTableModel) jTableCart.getModel();
//        while (tableModel.getRowCount() > 0) {
//            tableModel.removeRow(0);
//        }


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

        totalPriceNext = actualPrice + totalPriceNext;
        txtbillTotalPaymentNext.setText(totalPriceNext + "");
//        List<Object> productList = new ArrayList<>();
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

        menu.setSelectedIndex(5);
        customerId = "";
        deliverId = "";
        getAllProductsForSell();

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
        if (txtpaymentPayment.getText().isEmpty() && txtpaymentDeliveryCharge.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Input payment and delivey Charge");

        } else {
            float totalpay = Float.parseFloat(txtpaymentPayment.getText()) + Float.parseFloat(txtpaymentDeliveryCharge.getText());
            txtpaymentTotal.setText(totalpay + "");
        }
    }//GEN-LAST:event_txtpaymentTotalMouseClicked

    private void txtpaymentDueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpaymentDueMouseClicked
        // TODO add your handling code here:
        if (txtpaymentTotal.getText().isEmpty() && txtpaymentPaid.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "insert total and paid amount");

        } else {
            float due = Float.parseFloat(txtpaymentTotal.getText()) - Float.parseFloat(txtpaymentPaid.getText());
            txtpaymentDue.setText(due + "");
        }
    }//GEN-LAST:event_txtpaymentDueMouseClicked

    private void btnpaymentSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnpaymentSaveMouseClicked
        // TODO add your handling code here:
        if (flag == 1) {

            float total = Float.parseFloat(txtpaymentTotal.getText());
            float paid = Float.parseFloat(txtpaymentPaid.getText());
            float due = Float.parseFloat(txtpaymentDue.getText());
            String deliveryCode = txtpaymentdeliveyCode.getText();
            String paymentOption = comboPaymentOption.getSelectedItem().toString();
            sql = "insert into payment(payment,paid,due,delivery_code,payment_option)values(?,?,?,?,?)";
            try {
                ps = dbCon.getCon().prepareStatement(sql);
                ps.setFloat(1, total);
                ps.setFloat(2, paid);
                ps.setFloat(3, due);
                ps.setString(4, deliveryCode);
                ps.setString(5, paymentOption);
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
    int flag = 0;
    private void btnpaymentDeliveySetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnpaymentDeliveySetMouseClicked
        // TODO add your handling code here:

        String customerid = txtbillCustomerId.getText();
        String deliverycode = txtpaymentdeliveyCode.getText();
        float deliveyC = Float.parseFloat(txtpaymentDeliveryCharge.getText());

        Date orderD = sqltoday;
        Date deliveryD = convertutilltosql(jDatepaymentdeliveyDate.getDate());
        String deliveryAddress = txtpaymentDeliveryAddress.getText().trim();
        String deliveryCompany = combopaymentDeliverycompany.getSelectedItem().toString();

        sql = "update delivery_charge set customer_id=?,"
                + "delivery_chargecol=?,order_date=?,delivery_date=?,delivery_address=?,delivery_company=?"
                + " where delivery_code=?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);
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

    private void btnbillDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnbillDeleteMouseClicked
        // TODO add your handling code here:
        DefaultTableModel tableModel = (DefaultTableModel) jTableCart.getModel();
        int rowindex = jTableCart.getSelectedRow();
        tableModel.removeRow(rowindex);

    }//GEN-LAST:event_btnbillDeleteMouseClicked

    private void btnCustomerProductReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerProductReportMouseClicked
        // TODO add your handling code here:
        int comboIndex = comboCustomerProduct.getSelectedIndex();
        if (radioCustomerReport.isSelected()) {
            String[] customerColumn = {"CustomerId", "Product Name", "Order"};
            DefaultTableModel cusModel = new DefaultTableModel();
            cusModel.setColumnIdentifiers(customerColumn);
            tableCutomerProduntReport.setModel(cusModel);
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
                    Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Select a option to see report.");
            }
        } else if (radioProductReport.isSelected()) {
            String[] produntColumn = {"ProduntId ", "Unit Price", "Amount"};
            DefaultTableModel proModel = new DefaultTableModel();
            proModel.setColumnIdentifiers(produntColumn);
            tableCutomerProduntReport.setModel(proModel);
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
                    Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Select a option to see report.");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Select a customer or radio to see report.");
        }
    }//GEN-LAST:event_btnCustomerProductReportMouseClicked

    private void radioCustomerReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioCustomerReportMouseClicked
        // TODO add your handling code here:
        comboCustomerProduct.removeAllItems();
        if (radioCustomerReport.isSelected()) {
            comboCustomerProduct.addItem("---Select One--");
            comboCustomerProduct.addItem("Repeat Customer");
            comboCustomerProduct.addItem("Most Spend Customer");
        }
    }//GEN-LAST:event_radioCustomerReportMouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        menu.setSelectedIndex(8);
    }//GEN-LAST:event_jButton3MouseClicked

    private void radioProductReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioProductReportMouseClicked
        // TODO add your handling code here:
        comboCustomerProduct.removeAllItems();
        if (radioProductReport.isSelected()) {
            comboCustomerProduct.addItem("---Select One--");
            comboCustomerProduct.addItem("Most Frequent sold products");
            comboCustomerProduct.addItem("Most valuable products");
        }
    }//GEN-LAST:event_radioProductReportMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        printInvoice();
    }//GEN-LAST:event_jButton2MouseClicked

    private void btnDeliveryReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeliveryReportMouseClicked
        menu.setSelectedIndex(9);
        getAllPaymnet(jtableDeliveryReport);
    }//GEN-LAST:event_btnDeliveryReportMouseClicked

    private void btnDeliveryRepResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeliveryRepResetMouseClicked
        setbtnDeliveryReportReset();
    }//GEN-LAST:event_btnDeliveryRepResetMouseClicked

    private void jtableDeliveryReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableDeliveryReportMouseClicked
        int row = jtableDeliveryReport.getSelectedRow();

        String id = jtableDeliveryReport.getModel().getValueAt(row, 0).toString();
        String delivery_code = jtableDeliveryReport.getModel().getValueAt(row, 1).toString();
        String payment = jtableDeliveryReport.getModel().getValueAt(row, 2).toString();
        String due = jtableDeliveryReport.getModel().getValueAt(row, 3).toString();
        String deliverystatus = jtableDeliveryReport.getModel().getValueAt(row, 4).toString();
        java.util.Date deliverydate = formatStringdateToUtilDate(jtableDeliveryReport.getModel().getValueAt(row, 5).toString());
        txtDeliveryRepDId.setText(id);
        txtDeliveryRepDCode.setText(delivery_code);
        txtDeliveryRepPayment.setText(payment);
        txtDeliveryRepDue.setText(due);
//        txtDeliveryRepPaid.setText(null);
        comboDeliveryRepStatus.setSelectedItem(deliverystatus);
        jDateDeliveryRepDeliveryDate.setDate(deliverydate);

    }//GEN-LAST:event_jtableDeliveryReportMouseClicked

    private void btnDeliveryRepUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeliveryRepUpdateMouseClicked
        int id = Integer.parseInt(txtDeliveryRepDId.getText());
        String delivery_code = txtDeliveryRepDCode.getText();
        float payment = Float.parseFloat(txtDeliveryRepPayment.getText());
        float due = Float.parseFloat(txtDeliveryRepDue.getText());
        float newpaid = Float.parseFloat(txtDeliveryRepPaid.getText().trim());
        String deliverystatus = comboDeliveryRepStatus.getSelectedItem().toString();
        Date deliverydate = convertutilltosql(jDateDeliveryRepDeliveryDate.getDate());
        System.out.println("deCode "+delivery_code+ "\n"+" payment "+ payment
        +" due "+due+" newPaid "+newpaid+""
                );
        sql = "update smemanagement.payment set paid=paid+? ,due=due-?,delivery_status=?,delivery_date=? where delivery_code=?";
        try {
            ps = dbCon.getCon().prepareStatement(sql);

            ps.setFloat(1, newpaid);
            ps.setFloat(2, newpaid);
            ps.setString(3, deliverystatus);
            ps.setDate(4, deliverydate);
            ps.setString(5, delivery_code);
            ps.executeUpdate();
            ps.close();
            dbCon.getCon().close();

            JOptionPane.showMessageDialog(rootPane, "Payment updated");
            getAllPaymnet(jtableDeliveryReport);
        } catch (SQLException ex) {
            Logger.getLogger(SMEDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeliveryRepUpdateMouseClicked

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
                if ("Windows".equals(info.getName())) {
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
    private javax.swing.JButton btnCustomerProductReport;
    private javax.swing.JButton btnCustomerReset;
    private javax.swing.JButton btnCustomerSave;
    private javax.swing.JButton btnCustomerSave1;
    private javax.swing.JButton btnDeliveryRepReset;
    private javax.swing.JButton btnDeliveryRepUpdate;
    private javax.swing.JButton btnDeliveryReport;
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
    private javax.swing.ButtonGroup buttonGroupCustomerorProduct;
    private javax.swing.ButtonGroup buttonGroupReport;
    private javax.swing.JComboBox<String> comboCustomerDistrict;
    private javax.swing.JComboBox<String> comboCustomerProduct;
    private javax.swing.JComboBox<String> comboDeliveryRepStatus;
    private javax.swing.JComboBox<String> comboPaymentOption;
    private javax.swing.JComboBox<String> comboPurchaseProductName;
    private javax.swing.JComboBox<String> combopaymentDeliverycompany;
    private javax.swing.JTabbedPane customerProduct;
    private javax.swing.JTabbedPane deliveryReport;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private com.toedter.calendar.JDateChooser jDateCustomerInfo;
    private com.toedter.calendar.JDateChooser jDateDeliveryRepDeliveryDate;
    private com.toedter.calendar.JDateChooser jDateReportFromDate;
    private com.toedter.calendar.JDateChooser jDateReportFromDate1;
    private com.toedter.calendar.JDateChooser jDateReportToDate;
    private com.toedter.calendar.JDateChooser jDateReportToDate1;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
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
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableCart;
    private javax.swing.JTable jTablePurchaseProduct;
    private javax.swing.JTable jTableReport;
    private javax.swing.JTree jTree1;
    private com.toedter.calendar.JDateChooser jdatePurchaseProduct;
    private com.toedter.calendar.JDateChooser jdateinvUpdateCreateDate;
    private javax.swing.JTable jtableCustomerInfo;
    private javax.swing.JTable jtableDeliveryReport;
    private javax.swing.JLabel lvlDashboard;
    private javax.swing.JTabbedPane menu;
    private javax.swing.JRadioButton radioCustomerReport;
    private javax.swing.JRadioButton radioProductReport;
    private javax.swing.JRadioButton radioReportPurchase;
    private javax.swing.JRadioButton radioReportSales;
    private javax.swing.JRadioButton radioReportstock;
    private javax.swing.JTabbedPane salesReport;
    private javax.swing.JSpinner spanProductPurchaseQuentity;
    private javax.swing.JSpinner spanbillquentity;
    private javax.swing.JTable tableBillInfoProductDetails;
    private javax.swing.JTable tableCutomerProduntReport;
    private javax.swing.JTable tableInvUpdateProduct;
    private javax.swing.JTabbedPane tpBillingInfo;
    private javax.swing.JTabbedPane tpInvPurchaseProduct;
    private javax.swing.JTabbedPane tpInvUpdateProduct;
    private javax.swing.JTabbedPane tpInventory;
    private javax.swing.JTabbedPane tpLast1;
    private javax.swing.JTabbedPane tpOrder;
    private javax.swing.JTabbedPane tpPayment;
    private javax.swing.JTabbedPane tpdashboard;
    private javax.swing.JTextArea txtCustomerInfoAddress;
    private javax.swing.JTextField txtCustomerInfoCell;
    private javax.swing.JTextField txtCustomerInfoId;
    private javax.swing.JTextField txtCustomerInfoName;
    private javax.swing.JLabel txtDateClock;
    private javax.swing.JTextField txtDeliveryRepDCode;
    private javax.swing.JTextField txtDeliveryRepDId;
    private javax.swing.JTextField txtDeliveryRepDue;
    private javax.swing.JTextField txtDeliveryRepPaid;
    private javax.swing.JTextField txtDeliveryRepPayment;
    private javax.swing.JTextField txtMonthlyPurchase;
    private javax.swing.JTextField txtMonthlyPurchaseDigit;
    private javax.swing.JTextField txtMonthlySalesDigit;
    private javax.swing.JTextField txtProductPurchaseProductId;
    private javax.swing.JTextField txtProductPurchaseUnitPrice;
    private javax.swing.JTextField txtProductTotalPrice;
    private javax.swing.JLabel txtTime;
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
