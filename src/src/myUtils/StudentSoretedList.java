package myUtils;

import java.awt.Graphics2D;
import java.awt.Color;
import hungrystudents.core.Student;

/**
 * <p>Title: </p>
 *
 * <p>Description:
 * a graphical representation of a student sorted linked list, this class extends the MySortedLinkedList and overrides
 * <br> some of it's methods to get a fine graphical view, students sorted list is required for every school
 * <br> the list is drawn vertically
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: FIT</p>
 *
 * @author Yasser Almohammad
 * @version 1.0
 */
public class StudentSoretedList extends MySortedList {
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private int studentHeight = 0;
    private int studentWidth = 0;
    int margin = 2;
    public Color color = Color.white;

    public StudentSoretedList() {}

    /**
     * pasing the coords of the list, and the height of student space to draw against
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param studentHeight int
     */
    public StudentSoretedList(int x, int y, int width, int height,
                              int studentHeight) {
        updateCoords(x, y, width, height, studentHeight - margin);
    }

    /**
     * insert a student and update coords
     * @param s Student
     * @return Student
     */

    public Student insert(Student s) {
        Student temp = (Student)super.insert(s);
        updateCoords();
        return temp;
    }

    /**
     * draw the students linked list and pass action to each student
     * @param g Graphics2D
     */
    public void draw(Graphics2D g) {
        //draw empty first
        g.setColor(color);
        //    g.fillRect(x, y, width, height);
        Node temp = head;
        while (temp != null) {
            ((Student) temp.obj).draw(g);
            temp = temp.next;
        }
    }

    /**
     * students are drawn vertically against their schools
     */
    public void updateCoords(int x, int y, int width, int height,
                             int studentHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.studentWidth = width - margin*3;
        this.studentHeight = studentHeight;

        if (head == null)
            return;
        Node temp = head;
        int prevY = y + margin;
        while (temp != null) {
            Student student = (Student) temp.obj;
            student.updateCoords(x + margin, prevY, studentWidth, studentHeight);
            prevY = prevY + studentHeight + margin*2;
            temp = temp.next;
        }
    }

    /**
     * call update for original values [shortcut nothing more]
     */
    public void updateCoords() {
        updateCoords(x, y, width, height, studentHeight - margin);
    }


}
