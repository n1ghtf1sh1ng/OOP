package oop.sprite;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//a simple animation window for Sprite
public class SpriteDisplay {
    static SpriteDisplay display;

    public static SpriteDisplay getDisplay() {
        if (display == null) {
            display = new SpriteDisplay();
        }
        return display;
    }

    JFrame window;
    List<Sprite> sprites = new ArrayList<>();
    BufferedImage image;
    double imageAspect;

    public SpriteDisplay() {
        SwingUtilities.invokeLater(this::init);
    }

    void init() {
        window = new JFrame("SpriteDisplay");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                SpriteDisplay.this.paint(g, getWidth(), getHeight());
            }
        });
        window.setSize(new Dimension(800, 600));
        window.setVisible(true);
    }

    public synchronized void draw(Sprite s) {
        if (!sprites.contains(s)) {
            sprites.add(s);
        }
        SwingUtilities.invokeLater(() -> {window.repaint();});
    }

    public synchronized void remove(Sprite s) {
        sprites.remove(s);
    }

    synchronized void paint(Graphics g, int width, int height) {
        if (image == null) {
            initImage();
        }

        Graphics2D g2 = (Graphics2D) g;

        for (Sprite sprite : sprites) {

            int sx = toGraphicsPositionX(sprite.x, width);
            int sy = toGraphicsPositionY(sprite.y, height);
            int sw = 100;
            int sh = (int) (sw * imageAspect);
            sy -= sw / 2;

            int d = sprite.direction % 360;
            if (d < 0) {
                d = 360 - d;
            }

            if (d <= 180) { //flip x: the image is originally turning left.
                sx += sw;
                sw *= -1;
            }

            g2.drawImage(image, sx, sy, sw, sh, null);
        }
    }

    int toGraphicsPositionX(int p, int width) {
        return (int) (((p + 240) / 480.0) * width);
    }

    int toGraphicsPositionY(int p, int height) {
        return (int) (((p + 180) / 360.0) * height);
    }

    void initImage() {
        try (InputStream in = getClass().getResourceAsStream("fantasy_pixy2.png")) {
            image = ImageIO.read(in);
        } catch (Exception ex) {
            System.err.println("error: cannot load the image");
            ex.printStackTrace();
            image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics ig = image.getGraphics();
            ig.setColor(new Color(0, 0, 0, 0));
            ig.fillRect(0, 0, image.getWidth(), image.getHeight());
            ig.setColor(Color.gray);
            ig.drawOval(5, 5, 90, 90);
            ig.dispose();
        }
        imageAspect = image.getWidth() / (double) image.getHeight();
    }
}
