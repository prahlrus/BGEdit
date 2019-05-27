package com.stinja.birg.Application;
import com.stinja.birg.Entities.Descriptor;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

public class LatexReader {
	private List<Descriptor> des;
	private Pattern deblocPattern;

	public LatexReader() {
		this.des = new ArrayList<Descriptor>();
		this.deblocPattern = 
			Pattern.compile(
				"\\\\debloc\\{(\\S+)\\}\\{(\\d+|\\*)\\}\\{(\\w)\\}\\{(.*)\\}\\{(.*)\\}\\{(.*)\\}");
	}

	public void read(Scanner s) {
		while (s.hasNextLine()) {
			String l = s.nextLine();
			Matcher m = deblocPattern.matcher(l);
			if (m.find()) {
				des.add(new Descriptor(m.group(1)));
			}
		}
	}

	public List<Descriptor> getDescriptors() {
		return des;
	}
}