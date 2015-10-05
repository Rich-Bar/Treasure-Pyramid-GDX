package main.types;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;

public class Shader {
  
  /**
   * A simple class used to prevent duplicate shaders from
   * being loaded and compiled onto the video card.</br>
   * @author Chronocide [http://slick.ninjacave.com/forum/viewtopic.php?t=3212, Zugriff 16.05.2015]
   */
  static final class ShaderResourceManager{
	  protected Map<String, Integer> map = new HashMap<String, Integer>();
    
    public ShaderResourceManager(){}
    
    public int getFragementShaderID(String fragmentFileName){
      Integer id = map.get(fragmentFileName);
      if(id==null){
        id = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        map.put(fragmentFileName, id);
      }
      return id;
    }
    
    public int getVertexShaderID(String vertexFileName){
      Integer id = map.get(vertexFileName);
      if(id==null){
        id = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        map.put(vertexFileName, id);
      }
      return id;
    }
  }
  protected static ShaderResourceManager staticSRM = null;

  
  public static final int BRIEF = 128;
  public static final int MODERATE = 512;
  public static final int VERBOSE = 1024;
  protected static int logging = MODERATE;
  
  
  protected int programID = -1; //ID of the compiled Shader program
  
  
  protected Shader(ShaderResourceManager srm,
                 String vertexFileName,
                 String fragmentFileName) throws SlickException{
    StringBuilder errorMessage = new StringBuilder();
    
    int vsid = srm.getVertexShaderID(vertexFileName);
    int fsid = srm.getFragementShaderID(fragmentFileName);
    
    GL20.glShaderSource(vsid, getProgramCode(vertexFileName));
      GL20.glCompileShader(vsid);
      if(!compiledSuccessfully(vsid)){
        errorMessage.append("Could not compile Vertex Shader ");
        errorMessage.append(vertexFileName);
        errorMessage.append(" failed to compile.\n");
        errorMessage.append(getShaderInfoLog(vsid));
        errorMessage.append("\n\n");
      }
    
    GL20.glShaderSource(fsid, getProgramCode(fragmentFileName));
      GL20.glCompileShader(fsid);
      if(!compiledSuccessfully(fsid)){
        errorMessage.append("Could not compile Fragment Shader ");
        errorMessage.append(fragmentFileName);
        errorMessage.append(" failed to compile.\n");
        errorMessage.append(getShaderInfoLog(fsid));
        errorMessage.append("\n\n");
      }

    programID = GL20.glCreateProgram();
      GL20.glAttachShader(programID, vsid);
      GL20.glAttachShader(programID, fsid);
    
      GL20.glLinkProgram(programID);
      if(!linkedSuccessfully()){
        errorMessage.append("Linking Error\n");
        errorMessage.append(getProgramInfoLog(programID));
        errorMessage.append("\n\n");
      }
      
      if(errorMessage.length()!=0){
        errorMessage.append("Stack Trace:");
        throw new SlickException(errorMessage.toString());
      }
  }
  
  
  
  /**
   * Factory method to create a new Shader.
   * @param vertexFileName
   * @param fragmentFileName
   * @return Shader
   * @throws SlickException
   */
  public static Shader makeShader(String vertexFileName,
                                  String fragmentFileName)throws SlickException{
    if(staticSRM == null){staticSRM = new ShaderResourceManager();}
    return new Shader(staticSRM, vertexFileName, fragmentFileName);
  }

  
  
  /**
   * Activates the shader.</br>
   */
  public void startShader(){
    GL20.glUseProgram(programID);
  }
  
  
  
  /**
   * Reverts GL context back to the fixed pixel pipeline.<br>
   */
  public static void forceFixedShader(){
    GL20.glUseProgram(0);
  }
  
  
  
  /**
   * Sets the value of the uniform integer Variable <tt>name</tt>.</br>
   * @param name the variable to set.
   * @param value the value to be set.
   */
  public void setUniformVariable(String name, int value){
    CharSequence param = new StringBuffer(name);
    int location = GL20.glGetUniformLocation(programID, param);
    
    GL20.glUseProgram(programID);
    GL20.glUniform1i(location, value);
    GL20.glUseProgram(0);
  }

  
  
  /**
   * Sets the value of the uniform integer Variable <tt>name</tt>.</br>
   * @param name the variable to set.
   * @param value the value to be set.
   */
  public void setUniformVariable(String name, float value){
    CharSequence param = new StringBuffer(name);
    int location = GL20.glGetUniformLocation(programID, param);
    
    GL20.glUseProgram(programID);
    GL20.glUniform1f(location, value);
    GL20.glUseProgram(0);
  }
  
  
  
  /**
   * Returns true if the shader compiled successfully.</br>
   * @param shaderID
   * @return true if the shader compiled successfully.</br>
   */
  @SuppressWarnings("deprecation")
  protected boolean compiledSuccessfully(int shaderID){
    return GL20.glGetShader(shaderID, GL20.GL_COMPILE_STATUS)==GL11.GL_TRUE;
  }
  
  
  
  /**
   * Returns true if the shader program linked successfully.</br>
   * @param shaderID
   * @return true if the shader program linked successfully.</br>
   */
  @SuppressWarnings("deprecation")
  protected boolean linkedSuccessfully(){
    return GL20.glGetShader(programID, GL20.GL_LINK_STATUS)==GL11.GL_TRUE;
  }
  
  
  
  protected String getShaderInfoLog(int shaderID){
    return GL20.glGetShaderInfoLog(shaderID, logging).trim();
  }
  
  
  
  protected String getProgramInfoLog(int programID){
    return GL20.glGetProgramInfoLog(programID, logging).trim();
  }
  
  
  
  /**
   * Gets the program code from the file "filename" and puts in into a 
   * byte buffer.
   * @param filename the full name of the file.
   * @return a ByteBuffer containing the program code.
   * @throws SlickException
   */
  protected ByteBuffer getProgramCode(String filename)throws SlickException{
    InputStream fileInputStream = null;
    byte[] shaderCode = null;
        
    fileInputStream = ResourceLoader.getResourceAsStream(filename);
    DataInputStream dataStream = new DataInputStream(fileInputStream);
    try {
    dataStream.readFully(shaderCode = new byte[fileInputStream.available()]);
    fileInputStream.close();
    dataStream.close();
    } catch (IOException e) {
    throw new SlickException(e.getMessage());
    }

 
    ByteBuffer shaderPro = BufferUtils.createByteBuffer(shaderCode.length);

    shaderPro.put(shaderCode);
    shaderPro.flip();

    return shaderPro;
  }
  
  /*
   * Nachträglich hinzugefügte Funktionen:
   */
	public void setUniformVariable(String name, int v0, int v1){
	    CharSequence param = new StringBuffer(name);
	    int location = GL20.glGetUniformLocation(programID, param);
	    
	    GL20.glUseProgram(programID);
	    GL20.glUniform2i(location, v0, v1);
	    GL20.glUseProgram(0);
	  }

	  public void setUniformVariable(String name, float v0, float v1){
	    CharSequence param = new StringBuffer(name);
	    int location = GL20.glGetUniformLocation(programID, param);
	    
	    GL20.glUseProgram(programID);
	    GL20.glUniform2f(location, v0, v1);
	    GL20.glUseProgram(0);
	  }

	  public void setUniformVariable(String name, int v0, int v1, int v2){
	    CharSequence param = new StringBuffer(name);
	    int location = GL20.glGetUniformLocation(programID, param);
	    
	    GL20.glUseProgram(programID);
	    GL20.glUniform3i(location, v0, v1, v2);
	    GL20.glUseProgram(0);
	  }

	  public void setUniformVariable(String name, float v0, float v1, float v2){
	    CharSequence param = new StringBuffer(name);
	    int location = GL20.glGetUniformLocation(programID, param);
	    
	    GL20.glUseProgram(programID);
	    GL20.glUniform3f(location, v0, v1, v2);
	    GL20.glUseProgram(0);
	  }

	  public void setUniformVariable(String name, int v0, int v1, int v2, int v3){
	    CharSequence param = new StringBuffer(name);
	    int location = GL20.glGetUniformLocation(programID, param);
	    
	    GL20.glUseProgram(programID);
	    GL20.glUniform4i(location, v0, v1, v2, v3);
	    GL20.glUseProgram(0);
	  }

	  public void setUniformVariable(String name, float v0, float v1, float v2, float v3){
	    CharSequence param = new StringBuffer(name);
	    int location = GL20.glGetUniformLocation(programID, param);
	    
	    GL20.glUseProgram(programID);
	    GL20.glUniform4f(location, v0, v1, v2, v3);
	    GL20.glUseProgram(0);
	  }
	  
	  public void setUniformMatrix2(String name, boolean transpose, FloatBuffer matrices){
		    CharSequence param = new StringBuffer(name);
		    int location = GL20.glGetUniformLocation(programID, param);
		    
		    GL20.glUseProgram(programID);
		    GL20.glUniformMatrix2(location, transpose, matrices);
		    GL20.glUseProgram(0);
	  }
	  
	  public void setUniformMatrix3(String name, boolean transpose, FloatBuffer matrices){
		    CharSequence param = new StringBuffer(name);
		    int location = GL20.glGetUniformLocation(programID, param);
		    
		    GL20.glUseProgram(programID);
		    GL20.glUniformMatrix3(location, transpose, matrices);
		    GL20.glUseProgram(0);
	  }
	  
	  public void setUniformMatrix4(String name, boolean transpose, FloatBuffer matrices){
		    CharSequence param = new StringBuffer(name);
		    int location = GL20.glGetUniformLocation(programID, param);
		    
		    GL20.glUseProgram(programID);
		    GL20.glUniformMatrix4(location, transpose, matrices);
		    GL20.glUseProgram(0);
	  }
  
}