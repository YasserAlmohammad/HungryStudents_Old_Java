package myUtils;

import java.awt.*;
import hungrystudents.core.Student;
/**
 * <p>Title: </p>
 *
 * <p>Description:
 * Graphical Hungry Student Queue, it extends the MyQueue and overrides few functions for added functionality
 * <br>over graphics coords
 * drawing will be from right to left </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class StudentQueue extends MyQueue{
    private int rightX=0; //of the student queue
    private int rightY=0; //of the student queue
    private int x=0;
    private int y=0;
    private int width=0;
    private int height=0;
    private int stdWidth=0;  //for every single std
    private int stdHeight=0; //for every single std
    static final int margin=2; //2 pixels between two lines
    /**
     * passing coords of the queue we construct a graphical queue
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public StudentQueue(int x,int y,int width,int height) {
        updateCoords(x,y,width,height);
    }

    /**
     * coordinate data are inherited from head to tail
     * @param std std
     * @return std
     */
    public Student queue(Student std){
        /*
        if(head==null)
            std.updateCoords(rightX-stdWidth-margin,rightY+margin,stdWidth,stdHeight);
        else //get data from tail
            std.updateCoords(((Student)tail.obj).x - stdWidth - margin,((Student)tail.obj).y,((Student)tail.obj).width,((Student)tail.obj).height);
        */
        super.queue(std);
        updateCoords(x,y,width,height);
        return std;
    }

    /**
     * commit an update command to update each student coords
     * @return Student
     */
    public Student dequeue(){
        Student obj=(Student)super.dequeue();
        updateCoords(x,y,width,height);
        return obj;
    }

    /**
     * draw the queue shape then pass the drawing to each student in the queue
     * @param g Graphics2D
     */
    public void draw(Graphics2D g){
        //draw empty first
        g.setColor(new Color(128,128,255));
        g.fillRoundRect(rightX-width,rightY,width,height,20,20);
        Node temp = head;
        while (temp != null) {
            ((Student)temp.obj).draw(g);
            temp = temp.next;
        }
    }

    /**
     * method is called upon every change in the queue structure
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void updateCoords(int x,int y,int width,int height){
        this.x=x;
        this.y=y;
        rightX = x + width;
        rightY = y;
        this.width = width;
        this.height = height;
        stdWidth = width / 9;
        stdHeight = height - margin * 2;


        if(head==null)
            return;
        Node temp=head;

        int prevX=rightX-stdWidth-margin;
        while(temp!=null){
            Student std=(Student)temp.obj;
            std.updateCoords(prevX,rightY+margin,stdWidth,stdHeight);
            //calc new x,y
            prevX = prevX - stdWidth - margin;
            temp=temp.next;
        }
    }

}
