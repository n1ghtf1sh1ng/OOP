package oop.nonmod.markdown;

import java.util.List;

public class NonModBlock {
    public int type; //0:Paragraph, 1:Heading, 2:List
    public String content;
    public List<String> listItems;
    public List<String> paragraphLines;

    public static final int TYPE_PARAGRAPH = 0;
    public static final int TYPE_HEADING = 1;
    public static final int TYPE_LIST = 2;
}
