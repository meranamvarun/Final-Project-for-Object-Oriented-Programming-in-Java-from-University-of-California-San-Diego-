package module1;
import processing.core.*;

public class MyPApplet extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String URL = "http://i742.photobucket.com/albums/xx62/wanderingSoul_photos/LittleAndamanBeach.jpg";
	private PImage backgroundImage;
	public void setup(){
		size(600,600);
		backgroundImage = loadImage(URL,"jpg");
		
		
	}
	
	public void draw(){
		backgroundImage.resize(600,600);
		image(backgroundImage,0,0);
		/*fill(255,200,0);
		ellipse(200,200, 400,400);
		
		fill(0,0,0);
		ellipse(100,150,50,75);
		
		fill(0,0,0);
		ellipse(300,150,50,75);
		
		fill(0,0,0);
		ellipse(200,250,20,20);
		noFill();
		ellipse(200,300,50,30);
		//noFill(); */
		
		int [] values = new int[3];
		values = sunColor(second());
		fill(values[0],values[1],values[2]);
		ellipse(450,100,75,75);
		
		
		
	}
	
	public int[] sunColor(float second){
		
		int[] rgb=new int[3];
		float diffFrom30 = Math.abs(30-second);
		float ratio = diffFrom30/30;
		rgb[0]=(int)(ratio*255);
		rgb[1]=(int)(ratio*255);
		rgb[2]=0;
		return rgb;
		
	}
	
	

}
