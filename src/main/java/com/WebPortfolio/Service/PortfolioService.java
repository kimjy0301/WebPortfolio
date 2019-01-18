package com.WebPortfolio.Service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.WebPortfolio.Bean.ImgControl;
import com.WebPortfolio.Model.Portfolio;
import com.WebPortfolio.Model.PortfolioImg;
import com.WebPortfolio.Reporitory.PortfolioImgReporitory;
import com.WebPortfolio.Reporitory.PortfolioReporitory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PortfolioService {
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

	public List<Portfolio> getPortfolioList() {
		
		/*
		 * AwsS3 s3 = new AwsS3(accessKey,secretKey);
		 * s3.createBucket("jiyong-portfolio");
		 * 
		 * for (Portfolio portfolio : list2) {
		 * s3.getObject("jiyong-portfolio",portfolio.getId() + "/" +
		 * portfolio.getThumbnailPath(), portfolioPath + portfolio.getThumbnailPath());
		 * for (PortfolioImg portfolioimg : portfolio.getListPortfolioImg()) {
		 * s3.getObject("jiyong-portfolio", portfolio.getId() + "/" +
		 * portfolioimg.getFileName(), portfolioPath + portfolioimg.getFileName()); } }
		 */
		
		

		List<Portfolio> list = portfolioReporitory.findAll();

		return list;
	}


	@Transactional	
	public void uploadPortfolio(Portfolio portfolio, MultipartFile[] uploadingFiles) {

		try {
			portfolioReporitory.save(portfolio);
			for (int i = 0; i < uploadingFiles.length; i++) {

				/*
				 * AwsS3 s3 = new AwsS3(accessKey,secretKey);
				 * s3.createBucket("jiyong-portfolio");
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

					String uploadedPath = imgControl.makeResizeImg(file, portfolio.getId() + "_" + i + ".JPG",
							portfolioPath, "JPG");

					if (i == 0) {
						String thumbPath = imgControl.makeThumbnail(file, portfolio.getId() + ".JPG", portfolioPath,
								"JPG");
						portfolio.setThumbnailPath(thumbPath);

						/*
						 * if (s3.putObejct("jiyong-portfolio", portfolio.getId() + "/" + thumbPath,
						 * portfolioPath + thumbPath)) { File tmpFile = new File(portfolioPath +
						 * thumbPath); if (tmpFile.exists()) { tmpFile.delete(); } }
						 */

					}

					pfImg.setFileName(uploadedPath);
					pfImg.setPortfolio(portfolio);

					/*
					 * if (s3.putObejct("jiyong-portfolio", portfolio.getId() + "/" + uploadedPath,
					 * portfolioPath + uploadedPath)) { File tmpFile = new File(portfolioPath +
					 * uploadedPath); if (tmpFile.exists()) { tmpFile.delete(); } }
					 */

					if (file.exists()) {
						file.delete();
					}
				}
			}
		} catch (Exception ex) {
			log.error(ex.toString());
		}

	}

}
