package com.stinja.birg.Application;
import com.stinja.birg.Entities.*;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Frame;
import java.awt.Panel;
import java.awt.Dialog;
import java.awt.Button;
import java.awt.BorderLayout;

public abstract class DataDialog
	extends Dialog implements WindowListener, ActionListener {

  protected Panel dataPanel;
	protected Button accept, reject;
	protected DataModel dm;

	protected DataDialog(Frame owner, String title, DataModel dm) {
		super(owner, title);
    this.dm = dm;

    addWindowListener(this);
    
    accept = new Button("Accept");
    accept.addActionListener(this);

    reject = new Button("Reject");
    reject.addActionListener(this);

    setLayout(new BorderLayout());
    add(accept, BorderLayout.EAST);
    Panel p = new Panel();
    layoutDataPanel(p);
    add(p, BorderLayout.CENTER);
    add(reject, BorderLayout.WEST);
    pack();
	}

  protected abstract void layoutDataPanel(Panel p);

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