package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

	private static Color[] color = new Color[] {new Color(31,136,193), new Color(5,102,53), 
								   new Color(181,170,32),new Color(74,192,179), 
								   new Color(251,205,209), new Color(151,216,100),
								   new Color(118,32,61), new Color(/*56,72,60*/28,128,128), 
								   new Color(163,210,193), new Color(255,254,147),
								   new Color(193,7,54), new Color(198,94,187),
								   new Color(215,110,79), new Color(/*209,215,224*/189,195,123), 
								   new Color(/*51,166,208*/93,23,205)};
	
//	
//									1. light blue
//									2. dark green
//									3. mustard yellow
//									4. blue green
//									5. light pink
//									6. light green
//									7. red violet
//									8. dark blue green
//									9. very light green
//									10. light yellow
//									11. bright red
//									12. violet
//									13. dark orange
//									14. light dark green
//									15. blue violet
//									
//									dark green ewy
//									very light violet
									
	public static Color getColor(int i) {
		return color[i];
	}
	
	public static Font getFont(String path,float size) {
		Font f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File(path));
		} catch(FontFormatException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return f.deriveFont(size);
	}
	
	public static ArrayList<Integer> convertList(Object o) {
		o=((String)o).replaceAll("<", "").replaceAll(">", "").replaceAll("br", "").
				replaceAll("/html", "").replaceAll("div style='text-align: center;'", "").
				replaceAll("/div", "").replaceAll("html", "");
		
		if (((String)o).equals(""))
			return null;
		
		String[] token=((String)o).split(",");
		ArrayList<Integer> list=new ArrayList<Integer>();
		
		for (int i=0; i<token.length; i++) {
			list.add(Integer.parseInt(token[i]));
		}
		return list;
	}
}