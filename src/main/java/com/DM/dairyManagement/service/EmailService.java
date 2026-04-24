package com.DM.dairyManagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username:noreply@milkmate.com}")
    private String fromEmail;
    
    @Value("${app.name:MilkMate}")
    private String appName;
    
    /**
     * Send a simple text email
     */
    public void sendSimpleEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            
            mailSender.send(message);
            logger.info("Simple email sent successfully to {}", to);
        } catch (MailException e) {
            logger.error("Failed to send simple email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
    
    /**
     * Send HTML email
     */
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            
            mailSender.send(message);
            logger.info("HTML email sent successfully to {}", to);
        } catch (MailException | MessagingException e) {
            logger.error("Failed to send HTML email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
    
    /**
     * Send welcome email to new user
     */
    public void sendWelcomeEmail(String to, String username, String fullName) {
        String subject = "Welcome to " + appName + "!";
        String htmlBody = buildWelcomeEmailHtml(username, fullName);
        sendHtmlEmail(to, subject, htmlBody);
    }
    
    /**
     * Send password reset email
     */
    public void sendPasswordResetEmail(String to, String resetToken, String username) {
        String subject = "Password Reset Request - " + appName;
        String htmlBody = buildPasswordResetEmailHtml(resetToken, username);
        sendHtmlEmail(to, subject, htmlBody);
    }
    
    /**
     * Send low stock alert email
     */
    public void sendLowStockAlert(String to, String productName, double currentQuantity, double reorderLevel) {
        String subject = "Low Stock Alert: " + productName;
        String htmlBody = buildLowStockAlertHtml(productName, currentQuantity, reorderLevel);
        sendHtmlEmail(to, subject, htmlBody);
    }
    
    /**
     * Send payment receipt email
     */
    public void sendPaymentReceipt(String to, String billNumber, double amount, String paymentMethod) {
        String subject = "Payment Receipt - Bill #" + billNumber;
        String htmlBody = buildPaymentReceiptHtml(billNumber, amount, paymentMethod);
        sendHtmlEmail(to, subject, htmlBody);
    }
    
    /**
     * Send bill notification email
     */
    public void sendBillNotification(String to, String billNumber, double totalAmount, String customerName) {
        String subject = "New Bill Generated - #" + billNumber;
        String htmlBody = buildBillNotificationHtml(billNumber, totalAmount, customerName);
        sendHtmlEmail(to, subject, htmlBody);
    }
    
    // HTML Email Templates
    
    private String buildWelcomeEmailHtml(String username, String fullName) {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h1 style="color: #2563eb;">Welcome to MilkMate!</h1>
                    <p>Dear %s,</p>
                    <p>Your account has been successfully created. You can now log in with the following credentials:</p>
                    <div style="background-color: #f3f4f6; padding: 15px; border-radius: 5px; margin: 20px 0;">
                        <p><strong>Username:</strong> %s</p>
                        <p><strong>Role:</strong> As assigned by your administrator</p>
                    </div>
                    <p>For security reasons, please change your password after your first login.</p>
                    <p>If you have any questions or need assistance, please contact your system administrator.</p>
                    <br>
                    <p>Best regards,<br>MilkMate Team</p>
                    <hr style="border: none; border-top: 1px solid #e5e7eb; margin: 20px 0;">
                    <p style="font-size: 12px; color: #6b7280;">This is an automated message. Please do not reply to this email.</p>
                </div>
            </body>
            </html>
            """.formatted(fullName, username);
    }
    
    private String buildPasswordResetEmailHtml(String resetToken, String username) {
        String resetLink = "http://localhost:5173/reset-password?token=" + resetToken;
        return """
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h1 style="color: #2563eb;">Password Reset Request</h1>
                    <p>Dear %s,</p>
                    <p>We received a request to reset your password. Click the button below to reset it:</p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" 
                           style="background-color: #2563eb; color: white; padding: 12px 30px; 
                                  text-decoration: none; border-radius: 5px; display: inline-block;">
                            Reset Password
                        </a>
                    </div>
                    <p>This link will expire in 1 hour for security reasons.</p>
                    <p>If you didn't request this password reset, please ignore this email.</p>
                    <br>
                    <p>Best regards,<br>MilkMate Team</p>
                    <hr style="border: none; border-top: 1px solid #e5e7eb; margin: 20px 0;">
                    <p style="font-size: 12px; color: #6b7280;">This is an automated message. Please do not reply to this email.</p>
                </div>
            </body>
            </html>
            """.formatted(username, resetLink);
    }
    
    private String buildLowStockAlertHtml(String productName, double currentQuantity, double reorderLevel) {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h1 style="color: #dc2626;">⚠️ Low Stock Alert</h1>
                    <p>This is an automated alert from MilkMate inventory system.</p>
                    <div style="background-color: #fef2f2; padding: 15px; border-left: 4px solid #dc2626; margin: 20px 0;">
                        <p><strong>Product:</strong> %s</p>
                        <p><strong>Current Quantity:</strong> %.2f</p>
                        <p><strong>Reorder Level:</strong> %.2f</p>
                        <p style="color: #dc2626; font-weight: bold;">Action Required: Please reorder this product soon.</p>
                    </div>
                    <p>Please log in to MilkMate to manage your inventory.</p>
                    <br>
                    <p>Best regards,<br>MilkMate Inventory System</p>
                    <hr style="border: none; border-top: 1px solid #e5e7eb; margin: 20px 0;">
                    <p style="font-size: 12px; color: #6b7280;">This is an automated alert. Please do not reply to this email.</p>
                </div>
            </body>
            </html>
            """.formatted(productName, currentQuantity, reorderLevel);
    }
    
    private String buildPaymentReceiptHtml(String billNumber, double amount, String paymentMethod) {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h1 style="color: #059669;">Payment Receipt</h1>
                    <p>Thank you for your payment!</p>
                    <div style="background-color: #f0fdf4; padding: 15px; border-radius: 5px; margin: 20px 0;">
                        <p><strong>Bill Number:</strong> #%s</p>
                        <p><strong>Amount Paid:</strong> ₹%.2f</p>
                        <p><strong>Payment Method:</strong> %s</p>
                        <p><strong>Status:</strong> Payment Received ✓</p>
                    </div>
                    <p>This email serves as your payment receipt. Please keep it for your records.</p>
                    <br>
                    <p>Thank you for your business!<br>MilkMate Team</p>
                    <hr style="border: none; border-top: 1px solid #e5e7eb; margin: 20px 0;">
                    <p style="font-size: 12px; color: #6b7280;">This is an automated receipt. Please do not reply to this email.</p>
                </div>
            </body>
            </html>
            """.formatted(billNumber, amount, paymentMethod);
    }
    
    private String buildBillNotificationHtml(String billNumber, double totalAmount, String customerName) {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                    <h1 style="color: #2563eb;">New Bill Generated</h1>
                    <p>Dear %s,</p>
                    <p>A new bill has been generated for you.</p>
                    <div style="background-color: #eff6ff; padding: 15px; border-radius: 5px; margin: 20px 0;">
                        <p><strong>Bill Number:</strong> #%s</p>
                        <p><strong>Total Amount:</strong> ₹%.2f</p>
                        <p><strong>Status:</strong> Pending Payment</p>
                    </div>
                    <p>Please arrange for payment at your earliest convenience.</p>
                    <br>
                    <p>Best regards,<br>MilkMate Team</p>
                    <hr style="border: none; border-top: 1px solid #e5e7eb; margin: 20px 0;">
                    <p style="font-size: 12px; color: #6b7280;">This is an automated notification. Please do not reply to this email.</p>
                </div>
            </body>
            </html>
            """.formatted(customerName, billNumber, totalAmount);
    }
}
