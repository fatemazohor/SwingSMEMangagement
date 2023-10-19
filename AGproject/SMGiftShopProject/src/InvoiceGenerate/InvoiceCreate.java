package InvoiceGenerate;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.Phaser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceCreate {
    String filename,text;
    int alingnment;

    public InvoiceCreate() {
    }

    public InvoiceCreate(String filename, String text, int alingnment) {
        this.filename = filename;
        this.text = text;
        this.alingnment = alingnment;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAlingnment() {
        return alingnment;
    }

    public void setAlingnment(int alingnment) {
        this.alingnment = alingnment;
    }

    public void createPDF(){  
        try {
            OutputStream os = new FileOutputStream(new File(filename));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InvoiceCreate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   // invoice no, date table
    public static PdfPCell getIrdCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell pCell = new PdfPCell(phrase);
        pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pCell.setPadding(5.0f);
        pCell.setBorderColor(BaseColor.LIGHT_GRAY);
        return pCell;
    }
    // invoice heading and getirdcell table as child.
    public static PdfPCell getIrhCell(String text, int alignment) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;

    }
    //Header shop name
    public static PdfPCell getshopNamecell(String text, int alignment) {
        FontSelector sf = new FontSelector();
        Font shopfont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
        sf.addFont(shopfont);
        Phrase shopphrase = sf.process(text);
        PdfPCell cell = new PdfPCell(shopphrase);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPadding(5.0f);
        return cell;

    } 
    // Header shop Address
    public static PdfPCell getshopaddcell(String text){
        PdfPCell pCell = new PdfPCell(new Paragraph(text));
        pCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        pCell.setBorder(PdfPCell.NO_BORDER);
        pCell.setPadding(5.0f);
        return pCell;
    
    
    }
    // bill table  header
    public static PdfPCell getbillHeaderCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase p = fs.process(text);
        PdfPCell cell = new PdfPCell(p);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        return cell;

    }
    // bill table data row with product name , qty, amount 
    public static PdfPCell getbillRowCell(String text) {
        PdfPCell pCell = new PdfPCell(new Paragraph(text));
        pCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pCell.setPadding(5.0f);
        pCell.setBorderWidthBottom(0);
        pCell.setBorderWidthTop(0);
        return pCell;

    }
    // validity message
    public static PdfPCell getvalidityRowCell(String text) {
        FontSelector fontSelector = new FontSelector();
        Font f = FontFactory.getFont(FontFactory.HELVETICA, 10);
        f.setColor(BaseColor.GRAY);
        fontSelector.addFont(f);
        Phrase phrase = fontSelector.process(text);
        PdfPCell pCell = new PdfPCell(phrase);
        pCell.setBorder(0);
        return pCell;

    }
    // total bill calculation
    public static PdfPCell getAccountCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell pCell = new PdfPCell(phrase);
        pCell.setBorderWidthLeft(0);
        pCell.setBorderWidthTop(0);
        pCell.setPadding(5.0f);
        return pCell;

    }
    // total account bill digit.
    public static PdfPCell getAccountCellDigit(String text) {
        FontSelector selector = new FontSelector();
        Font f = FontFactory.getFont(FontFactory.HELVETICA, 10);
        selector.addFont(f);
        Phrase phrase = selector.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(5.0f);
        cell.setPaddingRight(20.0f);
        return cell;
    }
    // last message , footer
    public static PdfPCell getDescripCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase p = fs.process(text);
        PdfPCell cell = new PdfPCell(p);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);

        return cell;

    }
    
    //qrcode cell create
    public static PdfPCell getqraddcell(String text) {
        PdfPCell pCell = new PdfPCell(new Paragraph(text));
        pCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        pCell.setBorder(PdfPCell.NO_BORDER);
        pCell.setPadding(5.0f);
        return pCell;

    }

   
    
    
    
}
