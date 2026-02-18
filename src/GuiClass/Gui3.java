
package GuiClass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Long running jobs need to be executed on a separate thread to allow the 
 * event dispatch thread to remain reponsive.
 */
public class Gui3 extends JFrame {
  private final JButton button = new JButton( "Press me");
  public Gui3() {
    setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(this.getClass().getSimpleName());
    add( this.button);
    this.button.addActionListener( new ActionListener() {
      @Override
      public void actionPerformed( final ActionEvent ev) {
        doButton();
      }
    });
    pack();
    setSize(300,getHeight());
    setVisible( true);
  }
  
  private void doButton() {
    new Thread( new Runnable() {
      @Override
      public void run() {
        for( int i = 0; i < 50; i++) {
          System.out.println( "Ouch!");
          try { Thread.sleep( 500); } catch( InterruptedException x) {}
        }
        System.out.println();
      }}).start();
  }

  public static void main( final String[] args) {
    SwingUtilities.invokeLater( new Runnable() {
      @Override
      public void run() {
        new Gui3();
      }
    });
  }
}
