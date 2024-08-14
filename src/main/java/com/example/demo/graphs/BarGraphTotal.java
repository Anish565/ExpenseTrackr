package com.example.demo.graphs;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import com.example.demo.DTOs.CategoryExpenseDTO;

import java.util.Base64;
import java.util.List;

public class BarGraphTotal extends JPanel {
    public String saveChartAsImage(List<CategoryExpenseDTO> data) {
        int width = 800;
        int height = 600;
        int padding = 50;
        int labelPadding = 50;
        int barWidth = 30;
        int barSpacing = 30; // Increased spacing between bars
        int numberOfBars = data.size();

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(padding, height - padding, width - padding, height - padding); // x-axis
        g2d.drawLine(padding, padding, padding, height - padding); // y-axis

        // Determine scale
        double maxValue = data.stream().mapToDouble(CategoryExpenseDTO::total).max().orElse(1);
        double scale = (height - 2 * padding) / maxValue;

        // Array of colors for bars
        Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA,
                          Color.CYAN, Color.PINK, Color.YELLOW, Color.GRAY, Color.LIGHT_GRAY};

        // Draw bars and labels
        for (int i = 0; i < numberOfBars; i++) {
            CategoryExpenseDTO dto = data.get(i);
            int x = padding + labelPadding + i * (barWidth + barSpacing);
            int y = height - padding - (int) (dto.total() * scale);
            int barHeight = (int) (dto.total() * scale);

            // Draw the bar with a different color
            g2d.setColor(colors[i % colors.length]);
            g2d.fillRect(x, y, barWidth, barHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, barWidth, barHeight);

            // Set the font size based on the label length
            Font originalFont = g2d.getFont();
            if (dto.name().length() > 6) {
                g2d.setFont(originalFont.deriveFont(originalFont.getSize() - 2.0f));
            }

            // Draw the category label at a 45-degree angle
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int labelWidth = fontMetrics.stringWidth(dto.name());

            // Save the current transform
            AffineTransform originalTransform = g2d.getTransform();

            // Rotate the label 45 degrees
            g2d.rotate(Math.toRadians(-45), x + barWidth / 2, height - padding + 20);

            // Draw the label
            g2d.drawString(dto.name(), x + (barWidth - labelWidth) / 2, height - padding + fontMetrics.getHeight() + 5);

            // Restore the original transform and font
            g2d.setTransform(originalTransform);
            g2d.setFont(originalFont);
        }

        // Draw y-axis labels
        for (int i = 0; i <= 10; i++) {
            int y = height - padding - i * (height - 2 * padding) / 10;
            String yLabel = String.format("%.1f", maxValue * i / 10);
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int labelWidth = fontMetrics.stringWidth(yLabel);
            g2d.drawString("$" + yLabel, padding - labelWidth - 10, y + fontMetrics.getHeight() / 2);
            g2d.drawLine(padding + 1, y, padding + 5, y); // Minor tick mark
        }

        // Clean up
        g2d.dispose();

        // Save the image to a file
        try {
            ImageIO.write(image, "png", outputStream);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
