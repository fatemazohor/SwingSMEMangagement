/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gauzechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import javax.swing.JComponent;

public class GauzeCreate extends JComponent {

    private int gauzeSize = 15;
    private Color color1 = new Color(255, 78, 80);
    private Color color2 = new Color(250, 120, 52);
    private int value;
    private int maximum = 100;

    public GauzeCreate() {
    }

    public int getGauzeSize() {
        return gauzeSize;
    }

    public void setGauzeSize(int gauzeSize) {
        this.gauzeSize = gauzeSize;
        repaint();
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
        repaint();
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
        repaint();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value<0) {
            value=0;
        }
        this.value = value;
        repaint();
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) - (gauzeSize + 5);
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        int centerX = width / 2;
        int centerY = height / 2;
        g2.setColor(new Color(200, 200, 200));
        g2.setStroke(new BasicStroke(gauzeSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        int angleStart = -35;
        Shape s = new Arc2D.Double(x, y, size, size, angleStart, 250, Arc2D.OPEN);
        g2.draw(s);
        double angle = getAngleOfValue();
        if (angle > 0) {
            s = new Arc2D.Double(x, y, size, size, angleStart+250-angle, angle, Arc2D.OPEN);
            GradientPaint gra = new GradientPaint(0, 0, color1, width, 0, color2);
            g2.setPaint(gra);
            g2.draw(s);
        }

        super.paint(g); //To change body of generated methods, choose Tools | Templates.
    }

    private double getAngleOfValue() {
        double max = maximum;
        double v = getValueFixed();
        double n = v / max * 100f;
        double angle = n * 250f / 100f;
        return angle;
    }

    private int getValueFixed() {
        return value > maximum ? value : maximum;

    }

}
