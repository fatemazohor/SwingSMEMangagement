package view;

import java.awt.Color;
import java.awt.Image;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    LocalDate today = LocalDate.now();
    java.sql.Date sqltoday = java.sql.Date.valueOf(today);

    public SMShopManagement() {
        initComponents();
        setUIImage();
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

//        txtbillProductName.setText(null);
//        txtbillunitPrice.setText(null);
//        spanbillquentity.setValue(0);
//        txtbillTotalPrice.setText(null);
//        txtbillDiscount.setText(null);
//        txtbillFinalPrice.setText(null);

    }

    private void setbtnCustomerReset() {
        txtCustomerInfoId.setText(null);
        txtCustomerInfoName.setText(null);
        txtCustomerInfoCell.setText(null);
        txtCustomerInfoAddress.setText(null);
        comboCustomerDistrict.setSelectedIndex(0);
        jDateCustomerInfo.setDate(null);

    }
    
    //get table
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
            Logger.getLogger( SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
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
        panDashboard = new javax.swing.JPanel();
        btnHome = new javax.swing.JLabel();
        btnInventory = new javax.swing.JLabel();
        btnOrder = new javax.swing.JLabel();
        btnSalesRep = new javax.swing.JLabel();
        btnCustomer4 = new javax.swing.JLabel();
        btnProduct = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
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
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblTodayDelivery = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbltotalSalesDigit = new javax.swing.JLabel();
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
        txtinvUpdatebuyPrice = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jdateinvUpdateCreateDate = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableInvUpdateProdunt = new javax.swing.JTable();
        btnInvUpdateAdd = new javax.swing.JButton();
        btnInvUpdateReset = new javax.swing.JButton();
        btnInvUpdate = new javax.swing.JButton();
        btnInvUpdateDelete = new javax.swing.JButton();
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
        jTable2 = new javax.swing.JTable();
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
        btnCustomerSave1 = new javax.swing.JButton();
        btnCustomerReset = new javax.swing.JButton();
        btnCustomerNext = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        billing = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        billingfrom = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPanel13 = new javax.swing.JPanel();
        txtAPro = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        txtAproname = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txtAPrice = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jCheckBox8 = new javax.swing.JCheckBox();
        jPanel17 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jSpinner3 = new javax.swing.JSpinner();
        jRadioButton3 = new javax.swing.JRadioButton();
        jPanel19 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jSpinner4 = new javax.swing.JSpinner();
        jRadioButton4 = new javax.swing.JRadioButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtunitp1 = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        txtqty1 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtunitp2 = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        txtqty2 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        txtunitp3 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel80 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        payment = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        salesRep = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        customerList = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        ProductList = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        deliveryUpdate = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
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

        jLabel9.setFont(new java.awt.Font("PMingLiU-ExtB", 1, 20)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Home");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 78, 80), 2));
        panDashboard.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 150, 50));

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
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnbillinfoAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnillInfoReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Total Profit");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 230, 130, 30));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Today Delivery");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 190, 150, 30));

        lblTodayDelivery.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel4.add(lblTodayDelivery, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 120, 310, 190));

        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Total Profit");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 530, 160, 30));

        lbltotalSalesDigit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltotalSalesDigit.setText("Total Profit");
        jPanel4.add(lbltotalSalesDigit, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 490, 80, 30));

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
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        txtinvUpdatebuyPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtinvUpdatebuyPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtinvUpdatebuyPriceActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Buying Price");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("Created Date");

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
        jScrollPane1.setViewportView(tableInvUpdateProdunt);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(41, 41, 41)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtinvUpdatebuyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jdateinvUpdateCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(36, 36, 36)
                            .addComponent(txtinvUpdateUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtinvUpdateProName, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txtinvUpdateProId, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtinvUpdateUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtinvUpdatebuyPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

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
        btnCustomerSave.setText("Save");
        btnCustomerSave.setPreferredSize(new java.awt.Dimension(73, 23));
        btnCustomerSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerSaveMouseClicked(evt);
            }
        });

        btnCustomerSave1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCustomerSave1.setText("Update");
        btnCustomerSave1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerSave1MouseClicked(evt);
            }
        });

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

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable3);

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
                        .addComponent(btnCustomerSave1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboCustomerDistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jDateCustomerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(btnCustomerSave1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(billingfrom, javax.swing.GroupLayout.DEFAULT_SIZE, 1190, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(billingfrom, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        billing.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1190, 90));

        jScrollPane7.setVerticalScrollBar(scrollBarCustom1);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        txtAPro.setBackground(new java.awt.Color(255, 255, 255));
        txtAPro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 209, 218), 2, true));
        txtAPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAProMouseClicked(evt);
            }
        });

        jLabel50.setText("image");
        jLabel50.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

        txtAproname.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        txtAproname.setForeground(new java.awt.Color(89, 89, 89));
        txtAproname.setText("Name of Product");

        jLabel51.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(89, 89, 89));
        jLabel51.setText("Price ");

        txtAPrice.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        txtAPrice.setForeground(new java.awt.Color(89, 89, 89));
        txtAPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtAPrice.setText("0.00");

        jLabel52.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(89, 89, 89));
        jLabel52.setText("Qty");

        jRadioButton1.setText("Purchase");

        javax.swing.GroupLayout txtAProLayout = new javax.swing.GroupLayout(txtAPro);
        txtAPro.setLayout(txtAProLayout);
        txtAProLayout.setHorizontalGroup(
            txtAProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtAProLayout.createSequentialGroup()
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(txtAProLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txtAProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jRadioButton1)
                    .addComponent(txtAproname, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(txtAProLayout.createSequentialGroup()
                        .addGroup(txtAProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(txtAProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(txtAProLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        txtAProLayout.setVerticalGroup(
            txtAProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtAProLayout.createSequentialGroup()
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtAproname, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(txtAProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtAProLayout.createSequentialGroup()
                        .addGroup(txtAProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(txtAProLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton1)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 209, 218), 2, true));

        jLabel57.setText("image");

        jLabel58.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(89, 89, 89));
        jLabel58.setText("Name of Product");

        jLabel59.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(89, 89, 89));
        jLabel59.setText("Price ");

        jLabel60.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(89, 89, 89));
        jLabel60.setText("0.00");

        jCheckBox8.setText("Add");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jCheckBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 209, 218), 2, true));

        jLabel61.setText("image");

        jLabel62.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(89, 89, 89));
        jLabel62.setText("Name of Product");

        jLabel63.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(89, 89, 89));
        jLabel63.setText("Price ");

        jLabel64.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(89, 89, 89));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel64.setText("0.00");

        jLabel65.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(89, 89, 89));
        jLabel65.setText("Qty");

        jRadioButton2.setText("Purchase");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jRadioButton2)
                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 209, 218), 2, true));

        jLabel66.setText("image");

        jLabel67.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(89, 89, 89));
        jLabel67.setText("Name of Product");

        jLabel68.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(89, 89, 89));
        jLabel68.setText("Price ");

        jLabel69.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(89, 89, 89));
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel69.setText("0.00");

        jLabel70.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(89, 89, 89));
        jLabel70.setText("Qty");

        jRadioButton3.setText("Purchase");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jRadioButton3)
                    .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel69, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton3)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 209, 218), 2, true));

        jLabel71.setText("image");

        jLabel72.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(89, 89, 89));
        jLabel72.setText("Name of Product");

        jLabel73.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(89, 89, 89));
        jLabel73.setText("Price ");

        jLabel74.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(89, 89, 89));
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel74.setText("0.00");

        jLabel75.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(89, 89, 89));
        jLabel75.setText("Qty");

        jRadioButton4.setText("Purchase");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jRadioButton4)
                    .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel74, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton4)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtAPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(198, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtAPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 517, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane7.setViewportView(jPanel13);

        jTabbedPane2.addTab("tab1", jScrollPane7);

        billing.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 770, 490));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Product Nmae");

        txtunitp1.setText("Customer Id");

        jLabel76.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel76.setText("Qty");

        txtqty1.setText("Customer Id");

        jLabel77.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel77.setText("Total");

        jTextField9.setText("Customer Id");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel53.setText("Unit Price");

        txtunitp2.setText("Customer Id");

        jLabel79.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel79.setText("Discount");

        txtqty2.setText("Customer Id");

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel54.setText("Unit Price");

        txtunitp3.setText("Customer Id");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTable4);

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel80.setText("Customer Id");

        jTextField11.setText("Customer Id");
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel81.setText("Delivery Id");

        jTextField12.setText("Customer Id");
        jTextField12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtunitp1)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtunitp2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtunitp3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtqty1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtqty2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(49, 49, 49))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)))
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtunitp1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtunitp3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtunitp2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel76, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtqty1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtqty2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        billing.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 180, 410, 460));

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

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(427, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(500, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(427, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(500, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(427, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(500, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(427, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(500, Short.MAX_VALUE))
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
//                addProductToStock();
//                setProductnametoPurchaseCombo(comboPurchaseProductName);
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
//            updateProductToStock();
//            setProductnametoPurchaseCombo(comboPurchaseProductName);
//            getAllPurchaseProduct(jTablePurchaseProduct);
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
//            getAllcustomers();

        } catch (SQLException ex) {
            Logger.getLogger(SMShopManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnCustomerSaveMouseClicked

    private void btnCustomerSave1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerSave1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCustomerSave1MouseClicked

    private void btnCustomerResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerResetMouseClicked
        // TODO add your handling code here:
        setbtnCustomerReset();
    }//GEN-LAST:event_btnCustomerResetMouseClicked

    private void btnCustomerNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerNextMouseClicked
        // TODO add your handling code here:
//        customerId = txtCustomerInfoId.getText();
//        txtbillCustomerId.setText(customerId);
//        deliverId = setdeliveryId(customerId);
//        txtbillDeliveryId.setText(deliverId);
//
        mainMenu.setSelectedIndex(5);
//        customerId = "";
//        deliverId = "";
//        getAllProductsForSell();
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

    private void txtAProMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAProMouseClicked
        // TODO add your handling code here:
        //        if (txtAPro.is) {

            //        }
    }//GEN-LAST:event_txtAProMouseClicked

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

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
    private javax.swing.JLabel btnCustomer4;
    private javax.swing.JButton btnCustomerNext;
    private javax.swing.JButton btnCustomerReset;
    private javax.swing.JButton btnCustomerSave;
    private javax.swing.JButton btnCustomerSave1;
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
    private javax.swing.JLabel btnSalesRep;
    private javax.swing.JButton btnbillinfoAddToCart;
    private javax.swing.JButton btncustomerSearch;
    private javax.swing.JButton btnillInfoReset;
    private javax.swing.JComboBox<String> comboCustomerDistrict;
    private javax.swing.JComboBox<String> comboPurchaseProductName;
    private javax.swing.JPanel customerList;
    private javax.swing.JPanel deliveryUpdate;
    private javax.swing.JPanel inventory;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox8;
    private com.toedter.calendar.JDateChooser jDateCustomerInfo;
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
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private com.toedter.calendar.JDateChooser jdatePurchaseProduct;
    private com.toedter.calendar.JDateChooser jdateinvUpdateCreateDate;
    private javax.swing.JLabel lblTodayDelivery;
    private javax.swing.JLabel lblTodayPurchase;
    private javax.swing.JLabel lblTodaySales;
    private javax.swing.JLabel lblTotalDelivery;
    private javax.swing.JLabel lblTotalPurchase;
    private javax.swing.JLabel lblTotalSales;
    private javax.swing.JLabel lbltodayPurchaseDigit;
    private javax.swing.JLabel lbltodaySalesDigit;
    private javax.swing.JLabel lbltotalPurchaseDigit;
    private javax.swing.JLabel lbltotalSalesDigit;
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
    private javax.swing.JPanel salesRep;
    private javax.swing.JLabel saleslogo;
    private javax.swing.JLabel saleslogo1;
    private jframescrollbar.ScrollBarCustom scrollBarCustom1;
    private javax.swing.JLabel shippedlogo;
    private javax.swing.JLabel shippedlogo1;
    private javax.swing.JSpinner spanProductPurchaseQuentity;
    private javax.swing.JTable tableInvUpdateProdunt;
    private javax.swing.JLabel txtAPrice;
    private javax.swing.JPanel txtAPro;
    private javax.swing.JLabel txtAproname;
    private javax.swing.JTextArea txtCustomerInfoAddress;
    private javax.swing.JTextField txtCustomerInfoCell;
    private javax.swing.JTextField txtCustomerInfoId;
    private javax.swing.JTextField txtCustomerInfoName;
    private javax.swing.JTextField txtProductPurchaseProductId;
    private javax.swing.JTextField txtProductPurchaseUnitPrice;
    private javax.swing.JTextField txtProductTotalPrice;
    private javax.swing.JTextField txtinvUpdateProId;
    private javax.swing.JTextField txtinvUpdateProName;
    private javax.swing.JTextField txtinvUpdateUnitPrice;
    private javax.swing.JTextField txtinvUpdatebuyPrice;
    private javax.swing.JTextField txtproduntname;
    private javax.swing.JTextField txtqty;
    private javax.swing.JTextField txtqty1;
    private javax.swing.JTextField txtqty2;
    private javax.swing.JTextField txtunitp;
    private javax.swing.JTextField txtunitp1;
    private javax.swing.JTextField txtunitp2;
    private javax.swing.JTextField txtunitp3;
    // End of variables declaration//GEN-END:variables
}
