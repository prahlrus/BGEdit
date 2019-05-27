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

    add(pane, bl.CENTER);

    entry = new TextField("Hoboken");
    add(entry, bl.SOUTH);

    setSize(300,200);
  }

  /* ActionListener methods */
  public void actionPerformed(ActionEvent e) {
    
    String cmd = e.getActionCommand();
    if (cmd.equals("Create Background")){
      view.addBackground(new Background(entry.getText()));
    }
    else if (cmd.equals("Create Descriptor")) {
      view.addDescriptor(new Descriptor(entry.getText()));
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
    LatexReader reader = new LatexReader();
    for (String filename : filenames) {
      File file = new File(filename);
      try {
        Scanner input = new Scanner (file);
        reader.read(input);
      } catch (FileNotFoundException e) {
        System.err.println("No such file: " + filename);
        continue;
      }
    }

    Background urban, rural;
    List<Background> bgs = new ArrayList<Background>();
    bgs.add(urban = new Background("Rural"));
    bgs.add(rural = new Background("Urban"));
    List<Descriptor> des = reader.getDescriptors();
    List<Expectation> exps = new ArrayList<Expectation>();
    return new EditorWindow(bgs, des, exps);

  }
}