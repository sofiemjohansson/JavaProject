import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class UserInterface1 extends JFrame implements Runnable { // class that makes it runnable
	

	  

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	CPanel displayPanel;

     JButton sharpenButton, blurringButton, edButton, resetButton;

     public UserInterface1() {
       super();
       Container container = getContentPane();

       displayPanel = new CPanel();
       container.add(displayPanel);
    
       JPanel panel = new JPanel();
       panel.setLayout(new GridLayout(2, 2));
       panel
           .setBorder(new TitledBorder(
               "Change layout"));

       sharpenButton = new JButton("Sharpen");
       sharpenButton.addActionListener(new ButtonListener());//This is the buttons thats being showed in the frame that showes options of editing
       blurringButton = new JButton("Blur");
       blurringButton.addActionListener(new ButtonListener()); // and makes the editing you chooses
       edButton = new JButton("Darkner");
       edButton.addActionListener(new ButtonListener());
       resetButton = new JButton("Reset");
       resetButton.addActionListener(new ButtonListener());

       panel.add(sharpenButton);
       panel.add(blurringButton);
       panel.add(edButton);
       panel.add(resetButton);

       container.add(BorderLayout.EAST, panel);

       addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
           System.exit(0);
         }
       });
       setSize(displayPanel.getWidth(), displayPanel.getHeight() + 10);
       setVisible(true); 
     }
     public static void main(String arg[]) {
       new UserInterface1();
     }

     class ButtonListener implements ActionListener { // this method create buttons for editing.
       public void actionPerformed(ActionEvent e) {
         JButton button = (JButton) e.getSource();

         if (button.equals(sharpenButton)) {
           displayPanel.sharpen();
           displayPanel.repaint();
         } else if (button.equals(blurringButton)) {
           displayPanel.blur();
           displayPanel.repaint();
         } else if (button.equals(edButton)) {
           displayPanel.edgeDetect();
           displayPanel.repaint();
         } else if (button.equals(resetButton)) {
           displayPanel.reset();
           displayPanel.repaint();
         }
       }
     }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


	}
   

class CPanel extends JLabel {
     
	Image displayImage;

     BufferedImage biSrc; // source

     BufferedImage biDest; // Destination image is mandetory.

     BufferedImage bi; // Only an additional reference.

     Graphics2D big;  // extending by using Graphics2D

     CPanel() {
       setBackground(Color.RED); // sets the size of imagewindow, I dont get it to work like how I want it to, as you can see.
       loadImage();
       setSize(displayImage.getWidth(this), displayImage.getWidth(this)); 
      createBufferedImages();
      bi = biSrc;
    }

     public void loadImage() { // finds the image and present it in JFrame
       displayImage = Toolkit.getDefaultToolkit().getImage("200.jpg"); 
       MediaTracker mt = new MediaTracker(this);
       mt.addImage(displayImage, 1);
       try {
         mt.waitForAll();
       } catch (Exception e) {
         System.out.println("Exception while loading."); // if image not found i will search for it
       }
       if (displayImage.getWidth(this) == -1) {
         System.out.println("No jpg file"); // displayed when image file not found.
         System.exit(0);
       }
     }

     public void createBufferedImages() { // shows the image and create graphics
       biSrc = new BufferedImage(displayImage.getWidth(this), displayImage
           .getHeight(this), BufferedImage.TYPE_INT_RGB);

       big = biSrc.createGraphics();
       big.drawImage(displayImage, 0, 0, this);

       biDest = new BufferedImage(displayImage.getWidth(this), displayImage
           .getHeight(this), BufferedImage.TYPE_INT_RGB);
     }

     public void sharpen() { // class for button that makes the image get sharper
       float data[] = { -1.0f, -1.0f, -1.0f, -1.0f, 9.0f, -1.0f, -1.0f, -1.0f,
           -1.0f };
       Kernel kernel = new Kernel(3, 3, data);
       ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
           null);
       convolve.filter(biSrc, biDest); // this makes the image sharper
       bi = biDest;
     }

     public void blur() { // method for button makes the images go bluring.
       float data[] = { 0.0625f, 0.125f, 0.0625f, 0.125f, 0.25f, 0.125f,
           0.0625f, 0.125f, 0.0625f };
       Kernel kernel = new Kernel(3, 3, data);
       ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
           null);
       convolve.filter(biSrc, biDest);
       bi = biDest;
     }

       public void edgeDetect() { // method for button that makes the image goes darker.
       float data[] = { 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f,
           -1.0f };

       Kernel kernel = new Kernel(3, 3, data);
       ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
           null);
       convolve.filter(biSrc, biDest);

       bi = biDest;
     }

     public void reset() { // method that makes you able to reset the image, so you can do it all over again if you want to.
       big.setColor(Color.black);
       big.clearRect(0, 0, bi.getWidth(this), bi.getHeight(this));
       big.drawImage(displayImage, 0, 0, this);
       bi = biSrc;
     }

     public void update(Graphics g) { // updates the graphics in image
       g.clearRect(0, 0, getWidth(), getHeight());
       paintComponent(g);
     }

     public void paintComponent(Graphics g) { // shows the colors
       super.paintComponent(g);
       Graphics2D g2D = (Graphics2D) g;

       g2D.drawImage(bi, 0, 0, this);
     }
}