package keji;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class fucard {
public static void main(String []args) throws Exception{
int version=20;
//n=(version-1)*4+21
//n*3+4
int imageSize =67+(version-1)*12;
Qrcode qrcode=new Qrcode();
//纠错等级
qrcode.setQrcodeErrorCorrect('H');
//A代表a-z,B代表其他字符
qrcode.setQrcodeEncodeMode('B');
//版本号
qrcode.setQrcodeVersion(version);
BufferedImage bufferedImage=new BufferedImage(imageSize,imageSize,BufferedImage.TYPE_INT_RGB);
Graphics2D gs=bufferedImage.createGraphics();
gs.setBackground(Color.WHITE);
gs.setColor(Color.BLACK);
//清除画布
gs.clearRect(0, 0, imageSize, imageSize);
int pixoff=2;//偏移量
//设置二维码内容
String content="BEGIN:VCARD\n"+
"N:付\n"+
"FN:卫丽\n"+
"ORG:科师\n"+
"TITLE:学生\n"+
"ADR;WORK:海港区河北大街\n"+
"TEL;CELL,VOICE:17733551074\n"+
"END:VCARD";
byte[] data=content.getBytes("utf-8");
boolean[][] qrdata=qrcode.calQrcode(data);

int startR=50,startG=0,startB=0;
int endR=255,endG=150,endB=255;

//偏移量
//int pixoff = 1;
boolean [][] s = qrcode.calQrcode(data);
for(int i=0;i<qrdata.length;i++){
	for(int j=0;j<qrdata.length;j++){
		if(qrdata[i][j]){
			//Random rand=new Random();
			int num1=startR+(endR-startR)*(j+1)/qrdata.length;
			int num2=startG+(endG-startG)*(j+1)/qrdata.length;
			int num3=startB+(endB-startB)*(j+1)/qrdata.length;
			Color color=new Color(num1,num2,num3);
			gs.setColor(color);
			gs.fillRect(i*3+pixoff, j*3+pixoff, 3, 3);
		}
	}
}
//BufferedImage logo=ImageIO.read(new File("D:/logo.jpg"));
BufferedImage logo=scale("D:/logo.jpg",50,50,true);
int o=(imageSize-logo.getHeight())/2;
gs.drawImage(logo,o,o,50,50,null);
//int logoSize=imageSize/4;
//设置logo图片在二维码中心
//int o=(imageSize-logoSize)/2;
//gs.drawImage(logo, o, o, logoSize, logoSize, null);
gs.dispose();//关闭绘图
bufferedImage.flush();//输出缓冲中的资源缓冲流的flush方法的作用是强制将缓冲内部缓冲区已经缓存的数据一次性写出。
try{
	ImageIO.write(bufferedImage,"png",new File("D:/qrcode.png"));
}catch (IOException e){
	e.printStackTrace();
	System.out.println("产生了问题");
}
System.out.println("二维码生成了！");
}

private static BufferedImage scale(String logoPath,int width,int height,boolean hasFiller)throws Exception{
double ratio = 0.0;
File file = new File(logoPath);
BufferedImage srcImage = ImageIO.read(file);
Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);

//计算比例
if((srcImage.getHeight()>height) || (srcImage.getWidth()>width)){
if(srcImage.getHeight()>srcImage.getWidth()){
	ratio = (new Integer(height)).doubleValue()/srcImage.getHeight();
}else{
	ratio = (new Integer(width)).doubleValue()/srcImage.getWidth();
}
AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio),null);
destImage = op.filter(srcImage,null);
}
if(hasFiller){//补白
BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
Graphics2D graphic = image.createGraphics();
graphic.setColor(Color.white);
graphic.fillRect(0, 0, width, height);
if(width==destImage.getWidth(null)){
	graphic.drawImage(destImage,0,(height-destImage.getHeight(null))/2,destImage.getWidth(null),destImage.getHeight(null),Color.white,null);
}else{
	graphic.drawImage(destImage,(width-destImage.getWidth(null))/2,0,destImage.getWidth(null),destImage.getHeight(null),Color.white,null);
}
graphic.dispose();
destImage=image;
}
return (BufferedImage) destImage;
}
}
