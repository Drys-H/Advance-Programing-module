
package GuiClass;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Now a long running job running on a worker thread is sending updates
 * back to the UI - and these updates need to be sent as jobs scheduled
 * on the event dispatch queue.
 */
public class Gui4 extends JFrame {
  private final JButton button = new JButton( "Press me");
  private final JLabel infoLabel = new JLabel( "Ready...");
  public Gui4() {
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
        for( int i = 0; i < 100; i++) {
          queueInfo( "Ouch " + i + "!");
          try { Thread.sleep( 500); } catch( InterruptedException x) {}
        }
        queueInfo( "Done...");        
      }}).start();
  }

  public void queueInfo( final String info) {
    SwingUtilities.invokeLater( new Runnable() {
      @Override
      public void run() {
        setInfo( info);       
      }});
  }
  
  public void setInfo( String info) {
    this.infoLabel.setText( info);
  }
  
  public static void main( final String[] args) {
    SwingUtilities.invokeLater( new Runnable() {
      @Override
      public void run() {
        new Gui4();
      }
    });
  }
}
