package com.WebPortfolio.Bean;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

@Component
public class ImgControl {

	public String makeThumbnail(File pFile, String fileName,String uploadPath, String fileExt) throws Exception { 
		// 저장된 원본파일로부터 BufferedImage 객체를 생성합니다. 
		BufferedImage srcImg = ImageIO.read(pFile);
		// 썸네일의 너비와 높이 입니다. 
		int dw = 400, dh = 300; 
		
		// 원본 이미지의 너비와 높이 입니다. 
		int ow = srcImg.getWidth();
		int oh = srcImg.getHeight(); 
		// 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
		int nw = ow; int nh = (ow * dh) / dw; 
		// 계산된 높이가 원본보다 높다면 crop이 안되므로
		// 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다. 
		if(nh > oh) { nw = (oh * dw) / dh; nh = oh; }
		// 계산된 크기로 원본이미지를 가운데에서 crop 합니다. 
		BufferedImage cropImg = Scalr.crop(srcImg, (ow-nw)/2, (oh-nh)/2, nw, nh); 
		// crop된 이미지로 썸네일을 생성합니다. 
		BufferedImage destImg = Scalr.resize(cropImg, dw, dh); 
		// 썸네일을 저장합니다. 이미지 이름 앞에 "THUMB_" 를 붙여 표시했습니다.
		
		String thumbName = uploadPath + "/THUMB_" + fileName; 
		File thumbFile = new File(thumbName); 
	    if (thumbFile.exists()) {
	    	thumbFile.delete();
	    }	    	    
		ImageIO.write(destImg, fileExt.toUpperCase(), thumbFile); 
		return  "THUMB_" + fileName; 
		
	}	

	public String makeResizeImg(File pFile, String fileName,String uploadPath, String fileExt) throws IOException {

		
	    // 원본 이미지 입니다.
	    BufferedImage srcImg = ImageIO.read(pFile);	    

	    // 썸네일 크기 입니다.
	    int dw = 960, dh = 540;
		
	    // 원본이미지 크기 입니다.
	    int ow = srcImg.getWidth();
	    int oh = srcImg.getHeight();
		
	    // 늘어날 길이를 계산하여 패딩합니다.
	    int pd = 0;
	    if(ow > oh) {
	 	pd = (int)(Math.abs((dh * ow / (double)dw) - oh) / 2d);
	    } else {
		pd = (int)(Math.abs((dw * oh / (double)dh) - ow) / 2d);
	    }
	    
	    if (pd>0) {
	    srcImg = Scalr.pad(srcImg, pd, Color.WHITE , Scalr.OP_ANTIALIAS);
	    }
	    // 이미지 크기가 변경되었으므로 다시 구합니다.
	    ow = srcImg.getWidth();
	    oh = srcImg.getHeight();
		
	    // 썸네일 비율로 크롭할 크기를 구합니다.
	    int nw = ow;
	    int nh = (ow * dh) / dw;
	    if(nh > oh) {
		nw = (oh * dw) / dh;
		nh = oh;
	    }
		
	    // 늘려진 이미지의 중앙을 썸네일 비율로 크롭 합니다.
	    BufferedImage cropImg = Scalr.crop(srcImg, (ow-nw)/2, (oh-nh)/2, nw, nh);
		
	    // 리사이즈(썸네일 생성)
	    BufferedImage destImg = Scalr.resize(cropImg, dw, dh);
		
	    // 이미지 출력
	    String thumbName = uploadPath + "/" + fileName;
	    File thumbFile = new File(thumbName);
	    if (thumbFile.exists()) {
	    	thumbFile.delete();
	    }	    
	    
	    ImageIO.write(destImg, fileExt.toUpperCase(), thumbFile);
	    return  fileName;
	    
	}	
}
