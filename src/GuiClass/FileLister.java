package GuiClass;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

// worksheets 2026 version
public class FileLister extends JFrame {

  JButton button = new JButton( "List files");
  JList<File> list = new JList<>( new DefaultListModel<File>());
  
  public FileLister() {

    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
    
    JPanel panel = new JPanel( new BorderLayout());
    JScrollPane sp = new JScrollPane( this.list);
    
    panel.add( sp, BorderLayout.CENTER);
    panel.add( this.button, BorderLayout.SOUTH);
    
    add( panel);
    
    this.button.addActionListener( new ActionListener() {
      @Override
      public void actionPerformed( ActionEvent e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listHardDrive();
            }
        }).start();
      }
    });
    
    setSize( 800, 600);
    setVisible( true);
  }

  private void listHardDrive() {
    File root = new File("C:\\Program Files");
    int maxFilesToList = 10000;
    int count = 0;
    ArrayList<File> toDo = new ArrayList<File>();
    toDo.add( root);
    while( !toDo.isEmpty() && count < maxFilesToList) {
      File file = toDo.remove(0);
      System.out.println( file);
      appendFile( file);
      count++;
      if ( file.isDirectory()) {
        File[] files = file.listFiles();
        if ( files != null) {
          toDo.addAll( Arrays.asList( files));
        }
      }    
    }
  }
  
  private void appendFile( File file) {
    DefaultListModel<File> model = (DefaultListModel<File>)this.list.getModel();
    model.addElement( file);
    int lastIndex = model.getSize() - 1;
    this.list.ensureIndexIsVisible(lastIndex); // scroll to last file
  }
  
  public static void main( final String[] args) {
    SwingUtilities.invokeLater( new Runnable() {
      @Override
      public void run() {
        new FileLister();
      }
    });
  }
}
