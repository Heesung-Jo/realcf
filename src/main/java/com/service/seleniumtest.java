package com.service; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.*;


import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

@Service
public class seleniumtest { 

	
    private static final Logger logger = LoggerFactory
            .getLogger(seleniumtest.class);
	/*
    private WebDriver driver;
    
    private WebElement webElement;
   
    //Properties
    public static final String WEB_DRIVER_ID = "phantomjs.binary.path";   //"webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "";
    */
    private HashMap<String, JSONObject> list = new HashMap<>();
    public static String download_path = "";
    
    
    public String time = "";
     
	public seleniumtest() {
		
	    	String rootPath = System.getProperty("user.dir");
	    //  	WEB_DRIVER_PATH =  rootPath + "/src/main/resources/static/phantomjs.exe"; //  // chromedriver.exe
	      	download_path = "/home/download"; // rootPath + "/src/main/resources/static/stock";  // 
	      	File path = new File(download_path);
	      	download_path = path.getAbsolutePath();
	     
	
	}
 
	    //WebDriver
	    //????????? ??? URL
	    private String base_url;
	    
	    

	    @PostConstruct
	    public void simulation_start() {
	    	 //crawl("http://data.krx.co.kr/contents/MDC/MDI/mdiLoader/index.cmd?menuId=MDC0201020101");
	         // test??? ????????? ???????????? crawl??? ???????????? ??????
	    	 // ???????????? ??????????????? ???????????? ????????? ?????? 
	    	 File file = findfile(download_path);
	         readcsv(file.getAbsolutePath());
	    }
	    
	    
	    @Scheduled(cron = "0 0 12 * * * ") 
	    public void simulation() {
	         //crawl("http://data.krx.co.kr/contents/MDC/MDI/mdiLoader/index.cmd?menuId=MDC0201020101");
	         File file = findfile(download_path);
	         readcsv(file.getAbsolutePath());
	    }
	 
	    /* ????????? ???????????? ???????????? ??????????????? ?????? ?????? ????????? ????????? ??????
	    // ????????? ?????? ??????????????? ?????? ???????????? ??????
	    //@Async
	    public void crawl(String url) {
	        //System Property SetUp
	        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
	        
	                
	        //Driver SetUp
	         ChromeOptions options = new ChromeOptions();
	         options.setCapability("ignoreProtectedModeSettings", true);
	         options.addArguments("headless");
	         options.addArguments("disable-gpu");
	         
	         options.addArguments("no-sandbox");
	         options.addArguments("disable-dev-shm-usage");
	         
	         options.addArguments("lang=ko_KR");
	         
	         
	         
	         //options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
	        
	         //HashMap<String, Object> chromePrefs= new HashMap<String, Object>();
	         
	         //chromePrefs.put("download.default_directory", download_path);
	         //chromePrefs.put("download.prompt_for_download", false);
	         //chromePrefs.put("download.directory_upgrade", true);
	         //chromePrefs.put("safebrowsing.enabled", true);
	         
	         //options.setExperimentalOption("prefs", chromePrefs);
	         
	        try {
	        	driver = new PhantomJSDriver();   // new ChromeDriver(options);
	            //get page (= ?????????????????? url??? ???????????? ?????? ??? request ??? ?????? ??????)
	            driver.get(url);

	            
	            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	            
	            WebElement table = driver.findElement(By.className("CI-GRID-BODY-TABLE"));
	            Thread.sleep(60000);
	            WebElement button = driver.findElement(By.className("CI-MDI-UNIT-DOWNLOAD"));
	            button.click();
	            WebElement tbody = table.findElement(By.tagName("tbody"));
	            
	            button = driver.findElement(By.cssSelector("#ui-id-1 > div > div:nth-child(2) > a"));
	            Thread.sleep(3000);
	            button.click();
	            
	            Thread.sleep(60000);
	          
	            
	            //iframe?????? ????????? ?????? ?????? ??????????????? ???????????????.
	           // driver.switchTo().frame(driver.findElement(By.id("loginForm")));
	            
	    
	        } catch (Exception e) {
	            
	            e.printStackTrace();
	        
	        } finally {
	 
	            driver.close();
	        }
	 
	    }
	    */
	    
	    public File findfile(String path) {

	        File dir = new File(path);
	        if (dir.isDirectory()) {

                // ????????? ?????? ????????????
	        	while(dir.listFiles().length > 10) {
		            Optional<File> oldfile = Arrays.stream(dir.listFiles(File::isFile))
			  	              .min((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
			            
			            System.out.println(oldfile.get().getAbsolutePath());
			            oldfile.get().delete();
	        	}
	        	
	        	Optional<File> opFile = Arrays.stream(dir.listFiles(File::isFile))
	              .max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));

	            
	            if (opFile.isPresent()){
	            	System.out.println(opFile.get().getAbsolutePath());
	                return opFile.get();
	            }
	        }
	        
	        return null;
	    }	
	    	
	    public void readcsv(String path) { 	
	    	//List<List<String>> list = new ArrayList<List<String>>();
	    	//path = path.replace("\\", "/");
	    	System.out.println(path);
	    	logger.info(path);
	    	File csv = new File(path);
			BufferedReader br = null;
			try {
				//FileReader read = new FileReader(csv); //, Charset.forName("EUC-KR")

		        FileInputStream input=new FileInputStream(path);
		        InputStreamReader reader=new InputStreamReader(input,"EUC-KR");
		        BufferedReader read =new BufferedReader(reader);
				
				
				br = new BufferedReader(read);

				String line = "";
				
				int first = 0;
				List<String> firstlist = new ArrayList<>();
				
				while((line=br.readLine()) != null) {
					
					/*String[] token = line.split(",");
					List<String> tempList = new ArrayList<String>(Arrays.asList(token));
					list.add(tempList);
					*/
					
					String[] token = line.split(",");
					List<String> tempList = new ArrayList<String>(Arrays.asList(token));
					JSONObject temp = new JSONObject();

					if(first == 0) {
						first = 1;
						firstlist = tempList;
						System.out.println(firstlist);
					}else {
						
						if(tempList.get(4).equals("") == true) {
						  System.out.println("success");
						}
						
						if(tempList.get(4).equals("") == false) {
	                        for(int i = 0; i < tempList.size(); i++) {
	                        	temp.put(firstlist.get(i), tempList.get(i).replace("\"", ""));
	                        }
						}
						
                        
                        list.put(tempList.get(1).replace("\"", ""), temp);
					}
					
					
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(br != null) {br.close();}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			//	System.out.println(list);
				time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			}
			
	    }
	    
	    
	    public HashMap<String, JSONObject> getlist(){
	    	return this.list;
	    }
	    
	    public String gettime(){
	    	return this.time;
	    }	 

}