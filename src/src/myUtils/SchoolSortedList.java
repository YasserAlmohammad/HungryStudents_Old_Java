package myUtils;

import java.awt.Graphics2D;
import java.awt.Color;
import hungrystudents.core.School;

/**
 * <p>Title: </p>
 *
 * <p>Description:
 * a graphical representation of a school sorted linked list, this class extends the MySortedLinkedList and overrides
 * <br> some of it's methods to get a fine graphical view
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: FIT</p>
 *
 * @author Yasser Almohammad
 * @version 1.0
 */
public class SchoolSortedList extends MySortedList {
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private int schoolHeight = 0;
    private int schoolWidth = 0;
    private int margin = 4;
    public Color color = new Color(72, 39, 119);
    /**
     * coordinates of the list and the width of each school
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param schoolWidth int
     */
    public SchoolSortedList(int x, int y, int width, int height,
                            int schoolWidth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.schoolWidth = schoolWidth-margin;
        schoolHeight = height - margin * 2;
    }

    /**
     * insert a school and update coords
     * @param s School
     * @return School
     */
    public School insert(School s){
        School temp=(School)super.insert(s);
        updateCoords(x,y,width,height);
        return temp;
    }

    /**
     * draw the schools linked list and pass action to each school
     * @param g Graphics2D
     */
    public void draw(Graphics2D g) {
        //draw empty first
        g.setColor(color);
        g.fillRoundRect(x, y, width, height,20,20);
        //then draw cars
        Node temp = head;
        while (temp != null) {
                 ((School)temp.obj).draw(g);
            temp = temp.next;
        }
    }

    /**
     * update the coords of each school upon every change in the list
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void updateCoords(int x,int y,int width,int height){
        if (head == null)
            return;
        Node temp = head;
        int prevX = x + margin;
        while (temp != null) {
            School school = (School) temp.obj;
            school.updateCoords(prevX,y+margin,schoolWidth,schoolHeight);
            prevX = prevX + schoolWidth + margin;
            temp = temp.next;
        }
}


}
