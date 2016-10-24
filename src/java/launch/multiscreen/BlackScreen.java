package launch.multiscreen;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class BlackScreen extends BasicGame{

	public BlackScreen() {
		super("Blackness");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		try {
			Display.makeCurrent();
		} catch (LWJGLException e) {}
		arg1.setColor(Color.black);
		arg1.drawRect(0, 0, 300, 200);
		arg1.setColor(Color.white);
		arg1.drawString("Inventory", 100, 100);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}

}
