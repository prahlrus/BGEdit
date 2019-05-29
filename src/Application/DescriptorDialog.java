package com.stinja.birg.Application;
import com.stinja.birg.Entities.*;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Panel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DescriptorDialog 
	extends DataDialog {

	private TextField name, base;

	public DescriptorDialog(Frame owner, DataModel dm, Descriptor d) {
		super(owner, "Create or update a descriptor.", dm);
		if (d != null) {
			name.setText(d.getName());
			base.setText(d.getBase());
		}
	}

	protected void layoutDataPanel(Panel p) {
		name = new TextField(30);
		base = new TextField(30);

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		p.setLayout(gb);

		Label l = new Label("Name:");
		gbc.gridwidth = 1;
		gb.setConstraints(l,gbc);
		p.add(l);
		l = new Label("Base (if variant):");
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(l,gbc);
		p.add(l);

		gbc.gridwidth = 1;
		gb.setConstraints(name, gbc);
		p.add(name);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(base,gbc);
		p.add(base);
	}

	/* ActionListener methods */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == accept) {
    	String n = name.getText().trim();
    	String b = base.getText().trim();
    	if (n.length() > 0) {
	    	dm.registerDescriptor(
	    		new Descriptor(n, b)
	    		);
	    	if (b.length() > 0 && dm.lookupDescriptor(b) == null)
	    		dm.registerDescriptor(
	    			new Descriptor(b));
	    	dispose();
	    }
    } else if (e.getSource() == reject) {
    	dispose();
    }
  }
}