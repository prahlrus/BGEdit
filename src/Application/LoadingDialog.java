package com.stinja.birg.Application;
import com.stinja.birg.Entities.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Scanner;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.Button;
import java.awt.BorderLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadingDialog
	extends Dialog implements WindowListener, ActionListener {

	private DataModel dm;
	private Pattern deblocPattern;

	private Button accept, reject;
	private TextField entry;

	public LoadingDialog (Frame owner, DataModel dm) {
		super(owner, "Enter the name of a file or press cancel", true);
		setLocationRelativeTo(owner);

		// set up parser
		this.dm = dm;
		this.deblocPattern = 
				Pattern.compile(
					"\\\\debloc\\{(\\S+)\\}\\{(\\d+|\\*)\\}\\{(\\w)\\}\\{(.*)\\}\\{(.*)\\}\\{(.*)\\}");

		// wire up the Listeners
    addWindowListener(this);
    accept = new Button("Accept");
    accept.addActionListener(this);
    reject = new Button("Reject");
    reject.addActionListener(this);

    // layout the dialog
		setLayout(new BorderLayout());
    add(accept, BorderLayout.EAST);
    add(entry = new TextField(60), BorderLayout.CENTER);
    add(reject, BorderLayout.WEST);
    pack();
	}

	/* ActionListener methods */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == accept) {
    	File file = new File(entry.getText());
      try {
        Scanner input = new Scanner (file);
        readLaTeX(input);
        dispose();
      } catch (FileNotFoundException er) {
        entry.setText("No such file...");
      }    	
    } else if (e.getSource() == reject) {
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

	/* Parser methods */
	private void readLaTeX(Scanner s) {
		while (s.hasNextLine()) {
				String l = s.nextLine();
				Matcher m = deblocPattern.matcher(l);
				if (m.find()) {
					dm.registerDescriptor(new Descriptor(m.group(1)));
				}
			}
	}
}