package com.WebPortfolio.Controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.WebPortfolio.AWS.AwsS3;
import com.WebPortfolio.Bean.ImgControl;
import com.WebPortfolio.Model.Portfolio;
import com.WebPortfolio.Model.PortfolioImg;
import com.WebPortfolio.Reporitory.PortfolioImgReporitory;
import com.WebPortfolio.Reporitory.PortfolioReporitory;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/portfolio")
public class PortFolioController {

	@Autowired
	ImgControl imgControl;

	@Autowired
	PortfolioReporitory portfolioReporitory;

	@Autowired
	PortfolioImgReporitory portfolioImgReporitory;

		
	@Value("${upload.uploadingdir}") 
	String uploadingdir; 

	@Value("${upload.portfolioPath}") 
	String portfolioPath; 
	
	@Value("${aws.accessKey}") 
	String accessKey;

	@Value("${aws.secretKey}") 
	String secretKey; 	
	

	
	@RequestMapping("/")
	public String uploading(Model model) {

		List<Portfolio> list = portfolioReporitory.findAll();		
		model.addAttribute("list",list);
		
		
		File file = new File(uploadingdir);
		model.addAttribute("files", file.listFiles());
		return "upload";
	}

	@Transactional
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String uploadingPost(Portfolio portfolio, @RequestParam("input-b3[]") MultipartFile[] uploadingFiles,
			HttpServletRequest req, Model model) throws Exception {


		portfolioReporitory.save(portfolio);
		
		for ( int i =0 ; i<uploadingFiles.length; i++) {
			
			
	/*		AwsS3 s3 = new AwsS3(accessKey,secretKey);			
			s3.createBucket("jiyong-portfolio");	
			*/
			
	        MultipartFile uploadedFile = uploadingFiles[i];	 
	        
			File file = new File(uploadingdir + uploadedFile.getOriginalFilename());
			File uploadPath = new File(uploadingdir);

			if (uploadPath.exists() == false) {
				uploadPath.mkdir();
			}

			if (!uploadedFile.isEmpty()) {
				uploadedFile.transferTo(file);
			
				PortfolioImg pfImg = new PortfolioImg();				
				portfolioImgReporitory.save(pfImg);
				
				String uploadedPath=imgControl.makeResizeImg(file, portfolio.getId() + "_" + i + ".JPG", portfolioPath, "JPG");
				
				if (i==0) {
					String thumbPath=imgControl.makeThumbnail(file, portfolio.getId() + ".JPG", portfolioPath, "JPG");							
					portfolio.setThumbnailPath(thumbPath);					
					
	/*				if (s3.putObejct("jiyong-portfolio", portfolio.getId() + "/" + thumbPath, portfolioPath + thumbPath)) {
						File tmpFile = new File(portfolioPath + thumbPath);
						if (tmpFile.exists()) {
							tmpFile.delete();
						}
					}*/
					
				}
				
				pfImg.setFileName(uploadedPath);
				pfImg.setPortfolio(portfolio);
				
				/*				
				if (s3.putObejct("jiyong-portfolio", portfolio.getId() + "/" + uploadedPath, portfolioPath + uploadedPath)) {
					File tmpFile = new File(portfolioPath + uploadedPath);
					if (tmpFile.exists()) {
						tmpFile.delete();
					}
				}
				*/				
				
				if (file.exists()) {
					file.delete();
				}
				
			}
		}
				
		return "redirect:/portfolio/";
	}

}
