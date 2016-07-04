package library;

import java.util.ArrayList;

public class General {
	/*
	 * Input String, and returns padded string with space
	 */
	private ArrayList<Colors> appColors;

	public final static String OUTPUT_DIR = System.getProperty("user.dir") + "\\src\\dataaccess\\storage";

	General() {
		appColors = new ArrayList<Colors>();

		appColors.add(new Colors("AliceBlue", 0xF0, 0xF8, 0xFF));
		appColors.add(new Colors("AntiqueWhite", 0xFA, 0xEB, 0xD7));
		appColors.add(new Colors("Aqua", 0x00, 0xFF, 0xFF));
		appColors.add(new Colors("Aquamarine", 0x7F, 0xFF, 0xD4));
		appColors.add(new Colors("Azure", 0xF0, 0xFF, 0xFF));
		appColors.add(new Colors("Beige", 0xF5, 0xF5, 0xDC));
		appColors.add(new Colors("Bisque", 0xFF, 0xE4, 0xC4));
		appColors.add(new Colors("Black", 0x00, 0x00, 0x00));
		appColors.add(new Colors("BlanchedAlmond", 0xFF, 0xEB, 0xCD));
		appColors.add(new Colors("Blue", 0x00, 0x00, 0xFF));
		appColors.add(new Colors("BlueViolet", 0x8A, 0x2B, 0xE2));
		appColors.add(new Colors("Brown", 0xA5, 0x2A, 0x2A));
		appColors.add(new Colors("BurlyWood", 0xDE, 0xB8, 0x87));
		appColors.add(new Colors("CadetBlue", 0x5F, 0x9E, 0xA0));
		appColors.add(new Colors("Chartreuse", 0x7F, 0xFF, 0x00));
		appColors.add(new Colors("Chocolate", 0xD2, 0x69, 0x1E));
		appColors.add(new Colors("Coral", 0xFF, 0x7F, 0x50));
		appColors.add(new Colors("Crimson", 0xDC, 0x14, 0x3C));

	}

	public Colors getColors(int index) {
		Colors temp;
		if (index >= this.appColors.size()) {
			temp = new Colors("Red", 255, 0, 0);
			return temp;
		}

		temp = appColors.get(index);

		return temp;
	}

	public static String pad(String text, int size) {
		StringBuilder builder = new StringBuilder(text);
		while (builder.length() < size) {
			builder.append(' ');
		}
		return builder.toString();
	}
}
