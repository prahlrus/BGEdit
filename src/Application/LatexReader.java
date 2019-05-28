package com.stinja.birg.Application;
import com.stinja.birg.Entities.Descriptor;
import com.stinja.birg.Entities.DataModel;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

public class LatexReader {
	private Pattern deblocPattern;

	public LatexReader() {
		this.deblocPattern = 
			Pattern.compile(
				"\\\\debloc\\{(\\S+)\\}\\{(\\d+|\\*)\\}\\{(\\w)\\}\\{(.*)\\}\\{(.*)\\}\\{(.*)\\}");
	}

	public void read(Scanner s, DataModel dm) {
		while (s.hasNextLine()) {
			String l = s.nextLine();
			Matcher m = deblocPattern.matcher(l);
			if (m.find()) {
				dm.registerDescriptor(new Descriptor(m.group(1)));
			}
		}
	}
}