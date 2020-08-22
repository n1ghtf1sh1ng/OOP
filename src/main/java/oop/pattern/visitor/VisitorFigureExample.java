package oop.pattern.visitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.PrintWriter;

public class VisitorFigureExample implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new VisitorFigureExample());
    }

    JFrame frame;

    public void run() {
        frame = new JFrame(this.toString());

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        {
            VisitorFigureView view = new VisitorFigureView();

            mainPane.add(view, BorderLayout.CENTER);

            JToolBar tool = new JToolBar();
            {
                tool.add(new SaveAction(view.getFigures()));
                tool.add(new SaveVisitorAction(view.getFigures()));
            }
            mainPane.add(tool, BorderLayout.NORTH);
        }
        frame.add(mainPane);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static class VisitorFigureView extends JPanel  {
        VFigureGroup figures;

        VisitorFigureView() {
            this.setPreferredSize(new Dimension(1000, 1000));
            figures = new VFigureGroup();
            setUp();
        }

        public VFigureGroup getFigures() {
            return figures;
        }

        void setUp() {
            VLine line = new VLine();
            line.setXY(100, 100);
            line.setEndXY(200, 200);
            figures.addFigure(line);

            VRectangle rect = new VRectangle();
            rect.setXY(300, 300);
            rect.setSize(100, 200);
            figures.addFigure(rect);
        }

        protected void paintComponent(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            g.setColor(Color.black);
            figures.accept(new FigureDrawVisitor(g));
        }

    }

    static class SaveAction extends AbstractAction {
        VFigure fig;

        public SaveAction(VFigure fig) {
            this.fig = fig;
            putValue(NAME, "Save");
        }

        public void actionPerformed(ActionEvent e) {
            PrintWriter w = openWriter();
            FigureSavingNonVisitor visitor = new FigureSavingNonVisitor();
            if (fig instanceof VLine) {
                visitor.saveLine((VLine) fig, w);
            } else if (fig instanceof VRectangle) {
                visitor.saveRectangle((VRectangle) fig, w);
            } else if (fig instanceof VFigureGroup) {
                visitor.saveGroup((VFigureGroup) fig, w);
            }
            w.flush();
        }

        PrintWriter openWriter() {
            return new PrintWriter(System.out);
        }
    }

    static class SaveVisitorAction extends AbstractAction {
        VFigure fig;

        public SaveVisitorAction(VFigure fig) {
            this.fig = fig;
            putValue(NAME, "Save by Visitor");
        }

        public void actionPerformed(ActionEvent e) {
            PrintWriter writer = openWriter();
            fig.accept(new FigureSavingVisitor(writer));
            writer.flush();
        }

        PrintWriter openWriter() {
            return new PrintWriter(System.out);
        }
    }
}
