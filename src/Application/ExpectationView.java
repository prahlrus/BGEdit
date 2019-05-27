package com.stinja.birg.Application;
import com.stinja.birg.Entities.*;

import java.awt.Panel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Choice;
import java.awt.Label;

import java.util.List;
import java.util.Collections;

public class ExpectationView extends Panel {
	private List<Background> bgs;
	private List<Descriptor> des;
	private Choice[][] selections;

	public ExpectationView ( 
		List<Background> bgs, 
		List<Descriptor> des, 
		List<Expectation> exps
	 ) {
		super();

		for (Expectation exp : exps) {
			Background bg = exp.getBackground();
			if (! bgs.contains(bg))
				bgs.add(bg);
			Descriptor de = exp.getDescriptor();
			if (! des.contains(de))
				des.add(de);
		}

		Collections.sort(bgs);
		Collections.sort(des);

		this.bgs = bgs;
		this.des = des;
		
		selections = new Choice[des.size()][bgs.size()];
		for (int x = 0; x < selections.length; x++) {
			for (int y = 0; y < selections[0].length; y++) {
				selections[x][y] = makeChoice();
			}
		}
		for (Expectation exp : exps) {
			int x = des.indexOf(exp.getDescriptor());
			int y = bgs.indexOf(exp.getBackground());
			selections[x][y].select(exp.getExpectationType().abbreviation);
		}
		rebuild();
	}

	private Choice makeChoice() {
		Choice c = new Choice();
		c.addItem(" ");
		for (ExpectationType et : ExpectationType.values())
			c.addItem(et.abbreviation);
		c.select(" ");
		return c;
	}

	private void rebuild() {
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gb);
		gbc.fill = GridBagConstraints.BOTH;

		String[] bgLabels = new String[selections[0].length];
		for (int y = 0; y < bgLabels.length; y++) {
			bgLabels[y] = bgs.get(y).getName();
		}

		for (int x = 0; x < selections.length; x++) {
			gbc.gridwidth = 1;
			for (int y = 0; y < selections[0].length; y++) {
				gbc.weightx = 0.0;
				gb.setConstraints(selections[x][y],gbc);
				add(selections[x][y]);

				gbc.weightx = 1.0;
				Label l = new Label(bgLabels[y]);
				gb.setConstraints(l,gbc);
				add(l);
			}
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			Label l = new Label(des.get(x).getName());
			gb.setConstraints(l,gbc);
			add(l);
		}
	}

	public void addBackground(Background bg) {
		int i = 0;
		while (i<bgs.size()) {
			if (bg.getName().compareTo(bgs.get(i++).getName()) <= 0)
				break;
		}
		bgs.add(i,bg);

		// create a new column in the selections array
		Choice[][] updated = new Choice[selections.length][selections[0].length + 1];
		for (int x = 0; x < updated.length; x++) {
			for (int y = 0; y < updated[0].length; y++) {
				if  (y < i) {
					updated[x][y] = selections[x][y];
				} else if (y == i) {
					updated[x][y] = makeChoice();
				} else {
					updated[x][y] = selections[x][y-1];
				}
			}
		}
		selections = updated;

		// rebuild the layout
		removeAll();
		rebuild();
		revalidate();
		repaint();
	}

	public void addDescriptor(Descriptor de) {
		int i = 0;
		while (i<des.size()) {
			if (de.getName().compareTo(des.get(i++).getName()) <= 0)
				break;
		}
		des.add(i,de);

		// create a new row in the selections array
		Choice[][] updated = new Choice[selections.length + 1][selections[0].length];
		for (int x = 0; x < updated.length; x++) {
			if (x < 1) 
				for (int y = 0; y < updated[0].length; y++)
					updated[x][y] = selections[x][y];
			else if (x == i)
				for (int y = 0; y < updated[0].length; y++)
					updated[x][y] = makeChoice();
			else
				for (int y = 0; y < updated[0].length; y++)
					updated[x][y] = selections[x-1][y];
		}	
		selections = updated;

		// rebuild the layout
		removeAll();
		rebuild();
		revalidate();
		repaint();	}
}
