package com.example.demo.graphs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.example.demo.DTOs.CategoryExpenseDTO;

public class PieChartTotal {
    public String saveChartAsImage(List<CategoryExpenseDTO> data) {
        int width = 800;
        int height = 600;
        int padding = 50;

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Determine total value for the pie chart
        double totalValue = data.stream().mapToDouble(CategoryExpenseDTO::total).sum();

        // Starting angle
        int startAngle = 0;

        // Colors for each pie slice
        Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.MAGENTA,
            Color.CYAN, Color.PINK, Color.YELLOW, Color.GRAY, Color.LIGHT_GRAY};

        // Adjust the pie chart's position to leave room for the legend
        int pieX = padding;
        int pieY = padding;
        int pieWidth = width - 2 * padding - 200; // Reduce width to leave space for the legend
        int pieHeight = height - 2 * padding;

        // Draw each slice
        for (int i = 0; i < data.size(); i++) {
            CategoryExpenseDTO dto = data.get(i);

            // Calculate the angle for this slice
            int angle = (int) Math.round((dto.total() / totalValue) * 360);

            // Set the color for this slice
            g2d.setColor(colors[i % colors.length]);

            // Draw the slice
            g2d.fillArc(pieX, pieY, pieWidth, pieHeight, startAngle, angle);

            // Draw the slice border
            g2d.setColor(Color.BLACK);
            g2d.drawArc(pieX, pieY, pieWidth, pieHeight, startAngle, angle);

            // Update the start angle for the next slice
            startAngle += angle;
        }

        // Draw legend with rectangles and text
        int legendX = pieX + pieWidth + 20; // Position legend to the right of the pie chart
        int legendY = padding;

        for (int i = 0; i < data.size(); i++) {
            CategoryExpenseDTO dto = data.get(i);

            // Set the color for the legend rectangle
            g2d.setColor(colors[i % colors.length]);

            // Draw the rectangle
            g2d.fillRect(legendX, legendY, 20, 20);

            // Draw the border of the rectangle
            g2d.setColor(Color.BLACK);
            g2d.drawRect(legendX, legendY, 20, 20);

            // Draw the legend text
            g2d.setColor(Color.BLACK);
            g2d.drawString(dto.name() + " --> $" + String.format("%.1f", dto.total()), legendX + 30, legendY + 15);

            // Move the legend position down for the next item
            legendY += 30;
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
