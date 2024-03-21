package myUtils;

import java.awt.*;
import hungrystudents.core.Tray;
/**
 * <p>Title: </p>
 *
 * <p>Description:
 * Graphical Tray data, this class has the stack functionality and few of his own
 * drawing will be from right to left </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TrayStack extends MyStack{
    private int x=0;
    private int y=0;
    private int width=0;
    private int height=0;

    private int trayWidth=0;  //for every single tray
    private int trayHeight=0; //for every single tray
    static final int margin=2; //2 pixels between two lines
    public Color color=new Color(72 ,39,119);
    public TrayStack(int x,int y,int width,int height,int trayWidth) {
        updateCoords(x,y,width,height,trayWidth);
    }

    /**
     * coordinate data are inherited from the head
     * each insertion propagates change through the whole stack
     * @param tray Tray
     * @return Tray
     */
    public Tray push(Tray tray){
        super.push(tray);
        //update coords
        updateCoords(x,y,width,height,trayWidth);

        return tray;
    }

    /**
     * pop top from stack
     * @return Tray
     */
    public Tray pop(){
        Tray obj=(Tray)super.pop();
        updateCoords(x,y,width,height,trayWidth);
        return obj;
    }

    /**
     * draw stack space then pass drawing command to trays
     * @param g Graphics2D
     */
    public void draw(Graphics2D g){
        //draw empty first
        g.setColor(color);
        g.fillRoundRect(x,y,width,height,20,20);
        //then draw trays
        Node temp = head;
        while (temp != null) {
            ((Tray)temp.obj).draw(g);
            temp = temp.next;
        }
    }

    /**
     * upon each change in the stack, this method gets called
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param trayWidth int
     */
    public void updateCoords(int x, int y, int width, int height,int trayWidth){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.trayWidth = trayWidth;
        trayHeight = height - margin * 2;

        if(head==null)
            return;
        Node temp=head;
        int prevX=x+margin;
        while(temp!=null){
            Tray tray=(Tray)temp.obj;
            tray.updateCoords(prevX,y+margin, trayWidth,trayHeight);
            prevX = prevX+trayWidth+margin;
            temp=temp.next;
        }
    }

}
