package keji;
import java.awt.Color;
import java.awt.Graphics;
	import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
	import java.awt.image.BufferedImage;
	import java.io.File;
	import java.io.IOException;
	import javax.imageio.ImageIO;
import com.swetake.util.Qrcode;
	public class CreatQRcode {
	public static void main(String [] args){
		int version=6;
		int imageSize=127;
		Qrcode qrcode=new Qrcode();
		qrcode.setQrcodeVersion(version);
		String content="http://www.dijiaxueshe.com";
		byte[] data=content.getBytes();
		boolean[][]qrdata=qrcode.calQrcode(data);
		BufferedImage bufferedImage= new BufferedImage(imageSize,imageSize,BufferedImage.TYPE_INT_RGB);
		Graphics2D gs=bufferedImage.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.setColor(Color.BLACK);
		gs.clearRect(0, 0, imageSize, imageSize);
		int starR=255,starG=0,starB=0;
		int endR=0,endG=0,endB=255;
	int pixoff =2;
	boolean[][] s=qrcode.calQrcode(content.getBytes());
	for(int i=0;i<s.length;i++){
		for(int j=0;j<s.length;j++){
			if(s[i][j]){
				
				int num1=starR+(endR-starR)*(j+1)/s.length;
				int num2=starG+(endG-starG)*(j+1)/s.length;
				int num3=starB+(endB-starB)*(j+1)/s.length;
				Color color=new Color(num1,num2,num3);
				gs.setColor(color);
				gs.fillRect(i*3+pixoff, j*3+pixoff, 3, 3);
		}
	}
	}
	BufferedImage logo = null;
	try {
		logo = ImageIO.read(new File("D:/logo.jpg"));
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	int logoSize=imageSize/4;
	int o=(imageSize-logoSize)/2;
	gs.drawImage(logo, o, o, logoSize,logoSize, null);
		gs.dispose();
		bufferedImage.flush();
		try{
			ImageIO.write(bufferedImage,"png", new File("D:/qrcode.png"));
		}catch(IOException e){
				e.printStackTrace();
				System.out.println("产生了问题");
			}
			System.out.println("我的二维码生成了");
	}
	private static BufferedImage scale(String logoPath,int width,int height,boolean hasFiller) throws Exception{
		double ratio=0.0;
		File file =new File(logoPath);
		BufferedImage srcImage=ImageIO.read(file);
		Image destImage=srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		//计算比例
		if((srcImage.getHeight()>height)||(srcImage.getWidth()>width)){
			if(srcImage.getHeight()>srcImage.getWidth()){
			ratio=(new Integer(height)).doubleValue() / srcImage.getHeight();
			}else{
				ratio =(new Integer(width)).doubleValue() / srcImage.getWidth();
			}
			AffineTransformOp op= new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio),null);
			destImage =op.filter(srcImage, null);
			}
		if(hasFiller){
			BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic=image.createGraphics();
			graphic.setColor(Color.white);
			graphic.fillRect(0,0, width, height);
			if(width ==destImage.getWidth(null)){
				graphic.drawImage(destImage, 0,(height-destImage.getHeight(null)) / 2,destImage.getWidth(null),
			            destImage.getHeight(null),Color.white,null);
			}else{
				graphic.drawImage(destImage,(width-destImage.getWidth(null)) / 2,0,destImage.getWidth(null),
					            destImage.getHeight(null),Color.white,null);
			}
			graphic.dispose();
			destImage=image;
			}
		return (BufferedImage) destImage;
				
			}
		}
		
		