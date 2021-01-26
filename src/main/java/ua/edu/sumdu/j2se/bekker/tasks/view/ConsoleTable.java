package ua.edu.sumdu.j2se.bekker.tasks.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class helps to create a table in java command
 * line application using System.out.printf method.
 */
public class ConsoleTable {
    private static final String HORIZONTAL_SEP = "-";
    private String verticalSep;
    private String joinSep;
    private String[] headers;
    private final List<String[]> rows = new ArrayList<>();

    /**
     * Class constructor.
     */
    public ConsoleTable() {
        setShowVerticalLines(false);
    }


    /**
     * Shows vertical lines in a table.
     *
     * @param showVerticalLines if false (default) then no vertical lines are shown.
     */
    public void setShowVerticalLines(boolean showVerticalLines) {
        verticalSep = showVerticalLines ? "|" : "";
        joinSep = showVerticalLines ? "+" : " ";
    }

    /**
     * Sets headers to a console table.
     * Optional - if not used then there will be no header and horizontal lines.
     *
     * @param headers array of strings to set headers.
     */
    public void setHeaders(String... headers) {
        this.headers = headers;
    }

    /**
     * Adds a row to a table.
     *
     * @param cells array of string cells.
     */
    public void addRow(String... cells) {
        rows.add(cells);
    }

    /**
     * Prints a table into console.
     */
    public void print() {
        int[] maxWidths = headers != null ?
                Arrays.stream(headers).mapToInt(String::length).toArray() : null;

        for (String[] cells : rows) {
            if (maxWidths == null) {
                maxWidths = new int[cells.length];
            }
            if (cells.length != maxWidths.length) {
                throw new IllegalArgumentException("Number of row-cells and headers should be consistent");
            }
            for (int i = 0; i < cells.length; i++) {
                maxWidths[i] = Math.max(maxWidths[i], cells[i].length());
            }
        }

        if (headers != null) {
            printLine(maxWidths);
            printRow(headers, maxWidths);
            printLine(maxWidths);
        }
        for (String[] cells : rows) {
            printRow(cells, maxWidths);
        }
        if (headers != null) {
            printLine(maxWidths);
        }
    }

    private void printLine(int[] columnWidths) {
        for (int i = 0; i < columnWidths.length; i++) {
            String line = String.join("", Collections.nCopies(columnWidths[i] +
                    verticalSep.length() + 1, HORIZONTAL_SEP));
            System.out.print(joinSep + line + (i == columnWidths.length - 1 ? joinSep : ""));
        }
        System.out.println();
    }

    private void printRow(String[] cells, int[] maxWidths) {
        for (int i = 0; i < cells.length; i++) {
            String s = cells[i];
            String verStrTemp = i == cells.length - 1 ? verticalSep : "";
            System.out.printf("%s %-" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
        }
        System.out.println();
    }
}
