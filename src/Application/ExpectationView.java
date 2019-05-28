package com.stinja.birg.Application;
import com.stinja.birg.Entities.*;

import java.awt.Panel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Choice;
import java.awt.Label;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class ExpectationView extends Panel {

	private List<String> bgs;
	private List<Descriptor> des;
	private Set<Expectation> xps;
	private List<List<Choice>> selections;

	private Observer<Background> bObserver;
	private Observer<Descriptor> dObserver;

	public ExpectationView ( DataModel dm ) {
		super();
		bgs = new ArrayList<String>();
		des = new ArrayList<Descriptor>();

		for (Background b : dm.getBackgrounds())
			bgs.add(b.getName());
		Collections.sort(bgs);

		for (Descriptor d : dm.getDescriptors())
			des.add(d);
		Collections.sort(des);

		selections = new ArrayList<List<Choice>>();
		for (int x = 0; x < des.size(); x++) {
			List<Choice> row = new ArrayList<Choice>();
			for (int y = 0; y < bgs.size(); y++) {
				row.add(y, makeChoice());
			}
			selections.add(x, row);
		}

		xps = dm.getExpectations();
		for (Expectation e : xps) {
			int x = des.indexOf(e.getDescriptor());
			int y = bgs.indexOf(e.getBackground().getName());
			selections.get(x).get(y).select(e.getExpectationType().abbreviation);
		}

		reformat();

		// Attach Observers to the DataModel to make sure that updates
		// are propogated correctly.
		dm.addBackgroundObserver(bObserver = new Observer<Background> () {
			public void register(Background b) {
				registerBackground(b);
			}

			public void unregister(Background b) {
				forgetBackground(b);
			}
		});

		dm.addDescriptorObserver(dObserver = new Observer<Descriptor> () {
			public void register(Descriptor d) {
				registerDescriptor(d);
			}

			public void unregister(Descriptor d) {
				forgetDescriptor(d);
			}
		});
	}

	public void closeDown(DataModel dm, boolean writeChanges) {
		dm.removeBackgroundObserver(bObserver);
		dm.removeDescriptorObserver(dObserver);
	}

	private Choice makeChoice() {
		Choice c = new Choice();
		c.addItem(" ");
		for (ExpectationType et : ExpectationType.values())
			c.addItem(et.abbreviation);
		c.select(" ");
		return c;
	}

	private void reformat() {
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gb);
		gbc.fill = GridBagConstraints.BOTH;

		for (int x = 0; x < des.size(); x++) {
			gbc.gridwidth = 1;
			for (int y = 0; y < bgs.size(); y++) {
				gbc.weightx = 0.0;
				Choice c = selections.get(x).get(y);
				gb.setConstraints(c,gbc);
				add(c);

				gbc.weightx = 1.0;
				Label l = new Label(bgs.get(y));
				gb.setConstraints(l,gbc);
				add(l);
			}
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			Label l = new Label(des.get(x).getName());
			gb.setConstraints(l,gbc);
			add(l);
		}
	}

	private void rebuild() {
		removeAll();
		reformat();
		revalidate();
		repaint();
	}

	/********************* OBSERVER METHODS ***********************/

	public void registerBackground(Background b) {
		String n = b.getName();
		if (! bgs.contains(n)) {
			int i = 0;
			while (n.compareTo(bgs.get(i)) > 0)
				i++;
			bgs.add(i,n);

			for (int x = 0; x < des.size(); x++) {
				List<Choice> row = selections.get(x);
				row.add(i, makeChoice());
			}

			// the panel needs updated
			rebuild();
		}
	}

	public void forgetBackground(Background b) {
		String n = b.getName();
		int i = bgs.indexOf(n);
		if (i >= 0) {
			bgs.remove(i);
			for (List<Choice> row : selections)
				row.remove(i);

			// the panel needs updated
			rebuild();
		}
	}

	public void registerDescriptor(Descriptor d) {
		if (! des.contains(d)) {
			int i = 0;
			while (d.compareTo(des.get(i)) > 0)
				i++;
			des.add(i,d);

			List<Choice> row = new ArrayList<Choice>();
			for (int x = 0; x < des.size(); x++) {
				row.add(x, makeChoice());
			}
			selections.add(i,row);

			// the panel needs updated
			rebuild();
		}
	}

	public void forgetDescriptor(Descriptor d) {
		int i = bgs.indexOf(d);
		if (i >= 0) {
			des.remove(i);
			selections.remove(i);

			// the panel needs updated
			rebuild();
		}
	}
}
