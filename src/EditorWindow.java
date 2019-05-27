import java.awt.Frame;
import java.awt.BorderLayout;

import java.awt.ScrollPane;
import java.awt.TextField;

import java.awt.MenuBar;
import java.awt.Menu;
import java.awt.MenuItem;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.ArrayList;

/**
 * Top-level Component for the application.
 */
public class EditorWindow extends Frame implements WindowListener, ActionListener
{
  private ScrollPane pane;
  private ExpectationView view;
  private TextField entry;

  private Menu edit;
  private MenuItem createBackground, createDescriptor;

  public EditorWindow ( 
    List<Background> bgs,
    List<Descriptor> des,
    List<Expectation> exs 
    ) {
    super();
    addWindowListener(this);

    setTitle("BIRG Background Editor");
    BorderLayout bl = new BorderLayout();
    setLayout(bl);

    MenuBar mb = new MenuBar();
    mb.add(edit = new Menu("Edit"));
    edit.add(createBackground = new MenuItem("Create Background"));
    edit.add(createDescriptor = new MenuItem("Create Descriptor"));

    setMenuBar(mb);
    createBackground.addActionListener(this);
    createDescriptor.addActionListener(this);

    pane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    pane.add(view = new ExpectationView
      ( bgs 
      , des 
      , exs
      )
    );

    add(pane, bl.NORTH);

    entry = new TextField("Hoboken");
    add(entry, bl.SOUTH);

    pack();
  }

  public static void main(String[] args) {
    Background urban, rural;
    List<Background> bgs = new ArrayList<Background>();
    bgs.add(urban = new Background("Rural"));
    bgs.add(rural = new Background("Urban"));

    Descriptor hale, quick;
    List<Descriptor> des = new ArrayList<Descriptor>();
    des.add(hale = new Descriptor("hale"));
    des.add(quick = new Descriptor("quick"));

    List<Expectation> exps = new ArrayList<Expectation>();
    exps.add(
      new Expectation(urban, ExpectationType.ORDINARY, quick)
    );
    exps.add(
      new Expectation(rural, ExpectationType.ORDINARY, hale)
    );

    EditorWindow e = new EditorWindow(bgs, des, exps);
    e.setVisible(true);
  }

  /* ActionListener methods */
  public void actionPerformed(ActionEvent e) {
    /*
    String cmd = e.getActionCommand();
    if (cmd.equals("Create Background")){
      view.addBackground(new Background(entry.getText()));
    }
    else if (cmd.equals("Create Descriptor")) {
      view.addDescriptor(new Descriptor(entry.getText()));
    }

    view.revalidate();
    view.repaint();
    */
  }

  /* WindowListener methods */
  public void windowOpened(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowClosing(WindowEvent e) {
      dispose();
  }

  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
}