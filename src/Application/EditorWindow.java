package com.stinja.birg.Application;
import com.stinja.birg.Entities.*;

import java.awt.Frame;
import java.awt.BorderLayout;

import java.awt.ScrollPane;
import java.awt.Panel;
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
  private Panel currentView;
  private ExpectationView expectationView;

  private DataModel dm;
  private Menu file, view, edit;
  private MenuItem registerBackground, registerDescriptor;
  private MenuItem load, export;
  private MenuItem byExpectations;

  public EditorWindow ( DataModel dm ) {
    super();
    addWindowListener(this);

    this.dm = dm;

    setTitle("BIRG Background Editor");
    BorderLayout bl = new BorderLayout();
    setLayout(bl);

    MenuBar mb = new MenuBar();
    mb.add(file = new Menu("File"));

    file.add(load = new MenuItem("Load more data"));
    load.setActionCommand("load");
    load.addActionListener(this);
    file.add(export = new MenuItem("Export data"));
    export.setActionCommand("export");
    export.addActionListener(this);

    mb.add(view = new Menu("View"));

    view.add(byExpectations = new MenuItem("By expectations"));
    byExpectations.setActionCommand("expectationView");
    byExpectations.addActionListener(this);

    mb.add(edit = new Menu("Edit"));

    edit.add(registerBackground = new MenuItem("Create/update background"));
    registerBackground.setActionCommand("registerBackground");
    registerBackground.addActionListener(this);
    edit.add(registerDescriptor = new MenuItem("Create/update descriptor"));
    registerDescriptor.setActionCommand("registerDescriptor");
    registerDescriptor.addActionListener(this);

    setMenuBar(mb);

    pane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    add(pane, bl.CENTER);

    setSize(500,300);
  }

  /* ActionListener methods */
  public void actionPerformed(ActionEvent e) {
    
    String cmd = e.getActionCommand();
    
    if (cmd.equals("load")) {
      LoadingDialog d = new LoadingDialog(this, dm);
      d.setVisible(true);
    } 

    else if (cmd.equals("expectationView")) {
      switchToExpectationView();
    } 

    else if (cmd.equals("registerDescriptor")) {
      DescriptorDialog d = new DescriptorDialog(this, dm, null);
      d.setVisible(true);
    }
  }

  private void switchToExpectationView() {
    if (expectationView == null)
    expectationView = new ExpectationView(dm);

    if (currentView != expectationView) {
      if (currentView != null)
        pane.removeAll();
      pane.add(currentView = expectationView);
      pane.revalidate();
      pane.repaint();
    }
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

  public static void main(String[] args) {
    DataModel dm = new DataModel();
    Background urban, rural;
    dm.registerBackground(urban = new Background("Rural"));
    dm.registerBackground(rural = new Background("Urban"));

    EditorWindow e = new EditorWindow(dm);
    e.setVisible(true);
    LoadingDialog d = new LoadingDialog(e, dm);
    d.setLocationRelativeTo(e);
    d.setVisible(true);
  }
}