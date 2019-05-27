import java.awt.Panel;
import java.awt.GridLayout;
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

	private void rebuild() {
		GridLayout grid = new GridLayout(0, (2 * selections[0].length) + 1);
		setLayout(grid);
		String[] bgLabels = new String[selections[0].length];
		for (int y = 0; y < bgLabels.length; y++) {
			bgLabels[y] = bgs.get(y).getName();
		}

		for (int x = 0; x < selections.length; x++) {
			for (int y = 0; y < selections[0].length; y++) {
				add(selections[x][y]);
				add(new Label(bgLabels[y]));
			}
			add(new Label(des.get(x).getName()));
		}
	}

	private Choice makeChoice() {
		Choice c = new Choice();
		c.addItem(" ");
		for (ExpectationType et : ExpectationType.values())
			c.addItem(et.abbreviation);
		c.select(" ");
		return c;
	}


/*
	public void addBackground(Background bg) {
		int i = 0;
		while (i<bgs.size()) {
			if (bg.getName().compareTo(bgs.get(i++).getName()) <= 0)
				break;
		}
		bgs.add(i,bg);
		rebuild();
	}

	public void addDescriptor(Descriptor de) {
		int i = 0;
		while (i<des.size()) {
			if (de.getName().compareTo(des.get(i++).getName()) <= 0)
				break;
		}
		des.add(i,de);
		rebuild();
	}
	*/
}