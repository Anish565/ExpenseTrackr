package com.example.demo.graphs;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import com.example.demo.DTOs.CategoryExpenseDTO;
import java.util.List;

public class BarGraphTotal extends JPanel {
    private final List<CategoryExpenseDTO> data;

    public BarGraphTotal(List<CategoryExpenseDTO> data) {
        this.data = data;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = 500;
        int height = 300;
        int padding = 40;
        int labelPadding = 40;
        int numberOfBars = data.size();
        int barWidth = (width - (2 * padding) - (numberOfBars * 10)) / numberOfBars;

        double maxExpense = data.stream().mapToDouble(CategoryExpenseDTO::total).max().orElse(1);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(padding + labelPadding, height - padding - labelPadding, padding + labelPadding, padding);
        g2d.drawLine(padding + labelPadding, height - padding - labelPadding, width - padding, height - padding - labelPadding);

        int x = padding + labelPadding;
        for (CategoryExpenseDTO entry: data){
            int barHeight = (int) ((entry.total() / maxExpense) * (height - 2 * padding - labelPadding));
            g.setColor(new Color(51,153,255)); // set color to blue
            g.fillRect(x, height - labelPadding - barHeight - padding, barWidth, barHeight);

            g.setColor(Color.BLACK);
            g.drawString(entry.name(), x + barWidth / 2 -g.getFontMetrics().stringWidth(entry.name()) / 2, height - padding);

            g.drawString(String.valueOf(entry.total()), x + barWidth / 2 - g.getFontMetrics().stringWidth(String.valueOf(entry.total())) /2, height - padding - barHeight - 5);

            x += barWidth + 10;
        }

        int maxYValue = (int) Math.ceil(maxExpense);
        int increment = maxYValue / 10;

        for(int i = 0; i <= 10; i++){
            int yLabel = padding + labelPadding + i * (height - 2 * padding - labelPadding) / 10;
            String yLabelStr = String.valueOf(maxYValue - i * increment);
            g2d.drawString(yLabelStr, padding, yLabel + 5);
            g2d.drawLine(padding + labelPadding - 5, yLabel, padding + labelPadding, yLabel);
        }
    }

    public void saveChartAsImage(List<CategoryExpenseDTO> data, String filePath) {
        
        int width = 800;
        int height = 600;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        this.paint(g2d);
        g2d.dispose();

        
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()){
            parentDir.mkdirs();
            System.out.println("created folder");
        }

        // save the image to a file
        try {
            ImageIO.write(image, "png", file);
            System.out.println("Chart image saved to: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
