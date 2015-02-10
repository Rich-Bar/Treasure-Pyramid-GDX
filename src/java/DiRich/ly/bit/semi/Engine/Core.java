package DiRich.ly.bit.semi.Engine;

import java.awt.Dimension;

import DiRich.ly.bit.semi.Engine.openGL.DisplayRenderer;

public class Core{
	
	private static DisplayRenderer oGL = new DisplayRenderer(new Dimension(1200, 900));
	
	public static void main(String[] args) {
		for(int i = 0; i < args.length; i++){
			System.out.println(args[i]);
		}
		System.out.println("finished Listing args");
		
		oGL.start();
		
	}
	
}
