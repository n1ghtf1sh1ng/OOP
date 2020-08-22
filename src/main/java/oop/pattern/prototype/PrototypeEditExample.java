package oop.pattern.prototype;

import oop.drawing.moving.EditExample;
import oop.drawing.moving.EditView;
import oop.drawing.moving.Line;
import oop.pattern.composite.FigureGroup;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PrototypeEditExample extends EditExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new PrototypeEditExample());
    }

    @Override
    public void initToolPane(JPanel toolPane) {
        LinePrototype lineProto = new LinePrototype();
        lineProto.move(0, 0);
        lineProto.setEndPoint(200, 200);

        JButton lineButton = new JButton(new NewPrototypeAction(this.view, lineProto, "Line"));
        toolPane.add(lineButton);


        FigureGroupPrototype groupProto = new FigureGroupPrototype();
        {  // <- "block statement" syntax : this "{ }" just groups following sub-statements for readability.
            LinePrototype member1 = lineProto.copy();
            member1.move(100, 0);
            member1.setEndPoint(0, 100);
            groupProto.addFigure(member1);

            LinePrototype member2 = lineProto.copy();
            member2.move(0, 100);
            member2.setEndPoint(100, 200);
            groupProto.addFigure(member2);

            LinePrototype member3 = lineProto.copy();
            member3.move(100, 0);
            member3.setEndPoint(200, 100);
            groupProto.addFigure(member3);

            LinePrototype member4 = lineProto.copy();
            member4.move(200, 100);
            member4.setEndPoint(100, 200);
            groupProto.addFigure(member4);
        }
        JButton groupButton = new JButton(new NewPrototypeAction(this.view, groupProto, "Group"));
        toolPane.add(groupButton);
    }

    public class NewPrototypeAction extends AbstractAction {
        EditView view;
        FigurePrototype prototype;

        public NewPrototypeAction(EditView view, FigurePrototype prototype, String name) {
            this.view = view;
            this.prototype = prototype;
            this.putValue(NAME, name);
        }

        public void actionPerformed(ActionEvent e) {
            this.view.addFigure(this.prototype.copy());
        }
    }
}
