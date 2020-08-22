package oop.nonmod.markdown;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NonModMarkdown {
    public static void main(String[] args) throws Exception {
        String infile = args[0];
        String outfile = args[1];
        String input = Files.readString(Paths.get(infile));

        List<NonModBlock> blocks = new ArrayList<>();
        NonModBlock lastBlock = null;

        int lineStart = 0;
        int lineBreak;
        while (true) {
            int length = input.length();
            if (lineStart >= length) {
                lineBreak = -1;
            } else {
                lineBreak = length;
                for (int i = lineStart; i < length; ++i) {
                    char c = input.charAt(i);
                    if (c == '\n' || c == '\r') {
                        lineBreak = i;
                        break;
                    }
                }
            }

            if (lineBreak == -1) {
                break;
            }

            String line = input.substring(lineStart, lineBreak);

            boolean headMatch;
            String headContent;

            boolean listMatch;
            String listContent;

            String paragraphContent;

            NonModBlock newBlock;
            headMatch = line.startsWith("#");
            if (headMatch) {
                headContent = line.substring(1).trim();
                newBlock = new NonModBlock();
                newBlock.type = NonModBlock.TYPE_HEADING;
                newBlock.content = headContent;
            } else {
                listMatch = line.startsWith("*");
                if (listMatch) {
                    listContent = line.substring(1).trim();
                    newBlock = new NonModBlock();
                    newBlock.type = NonModBlock.TYPE_LIST;
                    newBlock.listItems = new ArrayList<>();
                    newBlock.listItems.add(listContent);
                } else {
                    paragraphContent = line.trim();
                    newBlock = new NonModBlock();
                    newBlock.type = NonModBlock.TYPE_PARAGRAPH;
                    newBlock.paragraphLines = new ArrayList<>();
                    newBlock.paragraphLines.add(paragraphContent);
                }
            }

            boolean canContain = false;
            if (lastBlock != null && lastBlock.type == NonModBlock.TYPE_LIST) {
                if (newBlock.type == NonModBlock.TYPE_LIST) {
                    canContain = true;
                }
            } else if (lastBlock != null && lastBlock.type == NonModBlock.TYPE_PARAGRAPH) {
                if (newBlock.type == NonModBlock.TYPE_PARAGRAPH) {
                    canContain = true;
                    boolean isBlank = lastBlock.paragraphLines.get(lastBlock.paragraphLines.size() - 1).trim().isEmpty();
                    if (isBlank) {
                        canContain = false;
                    }
                }
            }

            if (canContain) {
                if (newBlock.type == NonModBlock.TYPE_LIST) {
                    lastBlock.listItems.addAll(newBlock.listItems);
                } else if (newBlock.type == NonModBlock.TYPE_PARAGRAPH) {
                    lastBlock.paragraphLines.addAll(newBlock.paragraphLines);
                }
            } else {
                if (lastBlock != null) {
                    blocks.add(lastBlock);
                }
                lastBlock = newBlock;
            }

            if (lineBreak + 1 < input.length() && input.charAt(lineBreak) == '\r' & input.charAt(lineBreak + 1) == '\n') {
                lineStart = lineBreak + 2;
            } else {
                lineStart = lineBreak + 1;
            }
        }
        if (lastBlock != null) {
            blocks.add(lastBlock);
        }

        List<String> outputLines = new ArrayList<>();
        outputLines.add("<html>");
        for (int bi = 0; bi < blocks.size(); ++bi) {
            NonModBlock b = blocks.get(bi);
            if (b.type == NonModBlock.TYPE_HEADING) {
                outputLines.add("<h1>" + b.content + "</h1>");
            } else if (b.type == NonModBlock.TYPE_LIST) {
                outputLines.add("<ul>");
                for (int i = 0; i < b.listItems.size(); ++i) {
                    String line = b.listItems.get(i);
                    outputLines.add("<li>" + line + "</li>");
                }
                outputLines.add("</ul>");
            } else if (b.type == NonModBlock.TYPE_PARAGRAPH) {
                outputLines.add("<p>");
                for (int i = 0; i < b.paragraphLines.size(); ++i) {
                    String p = b.paragraphLines.get(i);
                    if (!p.trim().isEmpty()) {
                        outputLines.add(p);
                    }
                }
                outputLines.add("</p>");
            }
        }
        outputLines.add("</html>");

        String output = String.join("\n", outputLines);
        Files.writeString(Paths.get(outfile), output);
    }
}
