
package GuiClass;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Long running job scheduling millions of updates on the event dispatch
 * queue with no delay between them but using SwingUtilities.invokeAndWait
 * to preserve UI responsiveness and ensure all updates are painted.
 */
public class Gui6 extends JFrame {
  private final JButton button = new JButton( "Press me");
  private final JLabel infoLabel = new JLabel( "Ready...");
  public Gui6() {
    setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(this.getClass().getSimpleName());
    JPanel panel = new JPanel( new GridLayout(2,0));
    panel.add( this.button);
    panel.add( this.infoLabel);
    add( panel);
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
      public void run() {
        for( int i = 0; i < 10000000; i++) {
          queueInfo( "Ouch " + i + "!");
          //try { Thread.sleep( 500); } catch( InterruptedException x) {}
        }
        queueInfo( "Done...");        
      }}).start();
  }

  public void queueInfo( final String info) {
    try {
      SwingUtilities.invokeAndWait( new Runnable() {
        @Override
        public void run() {
          setInfo( info);       
        }});
    } catch (InvocationTargetException | InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public void setInfo( String info) {
    this.infoLabel.setText( info);
  }
  
  public static void main( final String[] args) {
    SwingUtilities.invokeLater( new Runnable() {
      @Override
      public void run() {
        new Gui6();
      }
    });
  }
}
