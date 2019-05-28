package com.stinja.birg.Application;
import com.stinja.birg.Entities.*;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Top-level Component for the application.
 */
public class EditorWindow extends Frame implements WindowListener, ActionListener
{
  private ScrollPane pane;
  private ExpectationView view;
  private TextField entry;

  private DataModel dm;

  private Menu edit;
  private MenuItem createBackground, createDescriptor;

  public EditorWindow ( DataModel dm ) {
    super();
    addWindowListener(this);

    this.dm = dm;

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
    pane.add(view = new ExpectationView(dm));
    add(pane, bl.CENTER);

    entry = new TextField("Hoboken");
    add(entry, bl.SOUTH);

    setSize(300,200);
  }

  /* ActionListener methods */
  public void actionPerformed(ActionEvent e) {
    
    String cmd = e.getActionCommand();
    if (cmd.equals("Create Background")){
      dm.registerBackground(new Background(entry.getText()));
    }
    else if (cmd.equals("Create Descriptor")) {
      dm.registerDescriptor(new Descriptor(entry.getText()));
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
    EditorWindow e = fromFiles(new String[]{"decat.tex"});
    e.setVisible(true);
  }

  public static EditorWindow fromFiles(String[] filenames) {
    DataModel dm = new DataModel(null, null, null);

    LatexReader reader = new LatexReader();
    for (String filename : filenames) {
      File file = new File(filename);
      try {
        Scanner input = new Scanner (file);
        reader.read(input, dm);
      } catch (FileNotFoundException e) {
        System.err.println("No such file: " + filename);
        continue;
      }
    }

    Background urban, rural;
    dm.registerBackground(urban = new Background("Rural"));
    dm.registerBackground(rural = new Background("Urban"));
    
    return new EditorWindow(dm);
  }
}