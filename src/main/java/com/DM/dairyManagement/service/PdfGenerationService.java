package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.model.Item;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGenerationService {
    
    private static final Logger logger = LoggerFactory.getLogger(PdfGenerationService.class);
    
    @Value("${app.name:MilkMate}")
    private String appName;
    
    /**
     * Generate PDF for a bill
     */
    public byte[] generateBillPdf(Bill bill) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // Add header
            addHeader(document, bill);
            
            // Add customer info
            addCustomerInfo(document, bill);
            
            // Add items table
            addItemsTable(document, bill);
            
            // Add totals
            addTotals(document, bill);
            
            // Add footer
            addFooter(document);
            
            document.close();
            
            logger.info("PDF generated for bill #{}", bill.getBillNo());
            return baos.toByteArray();
            
        } catch (Exception e) {
            logger.error("Failed to generate PDF for bill #{}: {}", bill.getBillNo(), e.getMessage());
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
    
    private void addHeader(Document document, Bill bill) {
        // Company name
        Paragraph companyName = new Paragraph(appName)
            .setFontSize(24)
            .setBold()
            .setFontColor(ColorConstants.DARK_BLUE)
            .setTextAlignment(TextAlignment.CENTER);
        document.add(companyName);
        
        // Title
        Paragraph title = new Paragraph("INVOICE")
            .setFontSize(18)
            .setBold()
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginBottom(10);
        document.add(title);
        
        // Bill info
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Paragraph billInfo = new Paragraph()
            .add("Bill #: " + bill.getBillNo() + "  |  ")
            .add("Date: " + bill.getBillDate().format(formatter))
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(10)
            .setMarginBottom(20);
        document.add(billInfo);
        
        // Separator line
        Line separator = new LineSeparator();
        separator.setMarginBottom(10);
        document.add(separator);
    }
    
    private void addCustomerInfo(Document document, Bill bill) {
        Paragraph customerTitle = new Paragraph("Customer Information")
            .setBold()
            .setFontSize(12)
            .setMarginBottom(5);
        document.add(customerTitle);
        
        Paragraph customerInfo = new Paragraph()
            .add("Name: " + bill.getFullName() + "\n")
            .add("Mobile: " + bill.getMobileNumber() + "\n")
            .add("Type: " + bill.getCustomerType())
            .setFontSize(10)
            .setMarginBottom(15);
        document.add(customerInfo);
    }
    
    private void addItemsTable(Document document, Bill bill) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{40, 20, 20, 20}))
            .useAllAvailableWidth();
        
        // Header row
        table.addHeaderCell(new Cell().add(new Paragraph("Product").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Quantity").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Price").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Total").setBold()));
        
        // Data rows
        double grandTotal = 0;
        for (Item item : bill.getItems()) {
            double itemTotal = item.getQuantity() * item.getPrice();
            grandTotal += itemTotal;
            
            table.addCell(new Cell().add(new Paragraph(item.getProductName())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
            table.addCell(new Cell().add(new Paragraph("₹" + String.format("%.2f", item.getPrice()))));
            table.addCell(new Cell().add(new Paragraph("₹" + String.format("%.2f", itemTotal))));
        }
        
        document.add(table);
        document.add(new Paragraph("\n"));
    }
    
    private void addTotals(Document document, Bill bill) {
        double subtotal = bill.getItems().stream()
            .mapToDouble(item -> item.getQuantity() * item.getPrice())
            .sum();
        
        double discount = bill.getDiscount() != null ? bill.getDiscount() : 0;
        double total = subtotal - discount;
        double paid = bill.getPaidAmount() != null ? bill.getPaidAmount() : 0;
        double balance = total - paid;
        
        Paragraph totalsTitle = new Paragraph("Payment Summary")
            .setBold()
            .setFontSize(12)
            .setMarginBottom(5);
        document.add(totalsTitle);
        
        Paragraph totals = new Paragraph()
            .add("Subtotal: ₹" + String.format("%.2f", subtotal) + "\n")
            .add("Discount: ₹" + String.format("%.2f", discount) + "\n")
            .add("Total: ₹" + String.format("%.2f", total) + "\n")
            .add("Paid: ₹" + String.format("%.2f", paid) + "\n")
            .add("Balance Due: ₹" + String.format("%.2f", balance))
            .setFontSize(11)
            .setMarginBottom(20);
        document.add(totals);
    }
    
    private void addFooter(Document document) {
        LineSeparator separator = new LineSeparator();
        separator.setMarginTop(20);
        document.add(separator);
        
        Paragraph footer = new Paragraph()
            .add("\nThank you for your business!\n")
            .add("This is a computer-generated invoice.")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(9)
            .setFontColor(ColorConstants.GRAY);
        document.add(footer);
    }
}
