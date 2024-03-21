package hungrystudents.core;

import java.awt.Graphics2D;
import java.awt.Color;

/**
 * <p>Title: </p>
 *
 * <p>Description:
 * a single tray, has a color and knows how to draw itself
 *  </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: FIT</p>
 *
 * @author Yasser Almohammad
 * @version 1.0
 */
public class Tray {
    public Color color;

    //coordinates of the graphical figure
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;

    public Tray() {
    }

    /**
     * every tray has a color
     * @param c Color
     */
    public Tray(Color c) {
        color=c;
    }

    /**
     * draw the try
     * @param g Graphics2D
     */
    public void draw(Graphics2D g){
       g.setColor(color);
       g.fill3DRect(x,y,width,height,true);
   }

   /**
    * just to control access to it's private coord data
    * @param x int
    * @param y int
    * @param width int
    * @param height int
    */
   public void updateCoords(int x, int y, int width, int height) {
       this.x = x;
       this.y = y;
       this.width = width;
       this.height = height;
   }


}
