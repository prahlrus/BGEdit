import java.awt.Panel;
import java.awt.GridLayout;
import java.awt.Checkbox;
import java.awt.Label;
import java.util.List;

public class BackgroundDescriptorView extends Panel {
	private List<Background> bgs;
	private List<Descriptor> des;
	private Checkbox[][] expectations;

	public BackgroundDescriptorView ( List<Background> bgs , List<Descriptor> des ) {
		super();

		this.bgs = bgs;
		this.des = des;

		rebuild();
	}

	private void rebuild() {
		removeAll();

		expectations = new Checkbox[des.size()][bgs.size()];
		GridLayout grid = new GridLayout(expectations.length, expectations[0].length + 1);
		setLayout(grid);
	
		for (int y = 0; y < expectations[0].length; y++) {
			Background bg = bgs.get(y);
			for (int x = 0; x < expectations.length; x++) {
				Descriptor de = des.get(x);
				Checkbox cb = new Checkbox(bg.getName(), bg.getDeState(de));
				expectations[x][y] = cb;
			}
		}

		for (int x = 0; x < expectations.length; x++) {
			for (int y = 0; y < expectations[0].length; y++) {
				add(expectations[x][y]);
			}
			Descriptor de = des.get(x);
			add(new Label(de.getName()));
		}
	}

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
}