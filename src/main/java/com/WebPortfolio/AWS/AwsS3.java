package com.WebPortfolio.AWS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.StringUtils;

public class AwsS3 {

	String accessKey; 
	String secretKey; 	
	
	AmazonS3 conn;
	String endpoint = "s3.ap-northeast-2.amazonaws.com";
	String region = "ap-northeast-2";
	
	public AwsS3(String accessKey , String secretKey) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;		
	}	

	public List<Bucket> getBucketList(){		
		conn = AmazonS3ClientBuilder.standard()
	            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
	            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
	            .build();
					
		List<Bucket> buckets = conn.listBuckets();
		
		for (Bucket bucket : buckets) {
	        System.out.println(bucket.getName() + "\t" +
	                StringUtils.fromDate(bucket.getCreationDate()));
		}
		
		return buckets;	
		
	}
	
	public boolean createBucket(String bucketName) {
		try {
			Boolean result = false;			
			conn = AmazonS3ClientBuilder.standard()
		            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
		            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
		            .build();			
			if (conn.doesBucketExistV2(bucketName)) {
			    System.out.format("Bucket %s already exists.\n", bucketName);
			    result =  false;
			} else {
			    try {
			        conn.createBucket(bucketName);
			        result = true;
			    } catch (AmazonS3Exception e) {
			        System.err.println(e.getErrorMessage());
			    }
			}				
			return result;			
		}catch(Exception e){
			return false;			
		}		
	}
	
	public boolean deleteBucket(String bucketName) {
		
		conn = AmazonS3ClientBuilder.standard()
	            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
	            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
	            .build();			
		
		
		try {
		    System.out.println(" - removing objects from bucket");
		    ObjectListing object_listing = conn.listObjects(bucketName);
		    while (true) {
		        for (Iterator<?> iterator =
		                object_listing.getObjectSummaries().iterator();
		                iterator.hasNext();) {
		            S3ObjectSummary summary = (S3ObjectSummary)iterator.next();
		            conn.deleteObject(bucketName, summary.getKey());
		        }

		        // more object_listing to retrieve?
		        if (object_listing.isTruncated()) {
		            object_listing = conn.listNextBatchOfObjects(object_listing);
		        } else {
		            break;
		        }
		    };
		    
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());		   
		}		
		
		
		try {
		    conn.deleteBucket(bucketName);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		}
		
		return true;		
	}
	
	
	
	
	public boolean putObejct(String bucketName,String keyName, String filePath) {
		
		System.out.format("Uploading %s to S3 bucket %s...\n", filePath, bucketName);
		
		conn = AmazonS3ClientBuilder.standard()
	            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
	            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
	            .build();			
		try {
			conn.putObject(bucketName, keyName, new File(filePath));
			
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		}
		
		return true;		
		
	}
	public boolean putDirectory(String bucketName,String dirName) {
		
		System.out.format("putDirectory %s to S3 bucket %s...\n", dirName, bucketName);
		
		conn = AmazonS3ClientBuilder.standard()
	            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
	            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
	            .build();			
		try {
			conn.putObject(bucketName, dirName + "/", "");
			
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		}
		
		return true;
	}
	
	
	public List<S3ObjectSummary> getObjectList(String bucketName){
		
		conn = AmazonS3ClientBuilder.standard()
	            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
	            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
	            .build();	
		
		ObjectListing ol = conn.listObjects(bucketName);
		List<S3ObjectSummary> objects = ol.getObjectSummaries();
		for (S3ObjectSummary os: objects) {
		    System.out.println("* " + os.getKey());
		}
		
		return objects;	
		
	}
	
	
	public boolean getObject(String bucketName , String keyName, String downPath) {		
		try {
			conn = AmazonS3ClientBuilder.standard()
		            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
		            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
		            .build();	
			
			
		    S3Object o = conn.getObject(bucketName, keyName);
		    S3ObjectInputStream s3is = o.getObjectContent();
		    FileOutputStream fos = new FileOutputStream(new File(downPath));
		    byte[] read_buf = new byte[1024];
		    int read_len = 0;
		    while ((read_len = s3is.read(read_buf)) > 0) {
		        fos.write(read_buf, 0, read_len);
		    }
		    s3is.close();
		    fos.close();
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		} catch (FileNotFoundException e) {
		    System.err.println(e.getMessage());
		} catch (IOException e) {
		    System.err.println(e.getMessage());
		}		
		return true;		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
