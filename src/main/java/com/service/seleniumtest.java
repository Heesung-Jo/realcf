package com.service; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.*;

import com.auth.SpringSecurityUserContext;
import com.repository.CoagroupdataRepository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

@Service
public class seleniumtest { 

	
    private static final Logger logger = LoggerFactory
            .getLogger(seleniumtest.class);
	
    private WebDriver driver;
    
    private WebElement webElement;
    private HashMap<String, JSONObject> list = new HashMap<>();
    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static String WEB_DRIVER_PATH = "";
    public static String download_path = "";
    public String time = "";
     
	public seleniumtest() {
	    	String rootPath = System.getProperty("user.dir");
	      	WEB_DRIVER_PATH = rootPath + "/src/main/resources/static/chromedriver.exe";
	      	download_path = rootPath + "/src/main/resources/static/stock";
	      	//File path = new File(download_path);
	      	//download_path = path.getAbsolutePath();
	}
 
	    //WebDriver
	    //크롤링 할 URL
	    private String base_url;
	    
	    
	    
	    
	    public void test() {
	    	System.out.println("123123123");
	    	
	    }
	    

	    @PostConstruct
	    public void simulation_start() {
	    	 //crawl("http://data.krx.co.kr/contents/MDC/MDI/mdiLoader/index.cmd?menuId=MDC0201020101");
	         // test만 된다면 여기서는 crawl이 들어가면 안됨
	    	 File file = findfile(download_path);
	         readcsv(file.getAbsolutePath());
	    }
	    
	    
	    @Scheduled(cron = "0 0 12 * * * ") 
	    public void simulation() {
	         //crawl("http://data.krx.co.kr/contents/MDC/MDI/mdiLoader/index.cmd?menuId=MDC0201020101");
	         File file = findfile(download_path);
	         readcsv(file.getAbsolutePath());
	    }
	 
	    // 거래소 매일 크롤링해서 정보 업데이트 하기
	    public void crawl(String url) {
	        //System Property SetUp
	        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
	        
	                
	        //Driver SetUp
	         ChromeOptions options = new ChromeOptions();
	         options.setCapability("ignoreProtectedModeSettings", true);
	         
	         options.addArguments("disable-gpu");
	         options.addArguments("lang=ko_KR");
	         options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
	        
	         HashMap<String, Object> chromePrefs= new HashMap<String, Object>();
	         
	         chromePrefs.put("download.default_directory", download_path);
	         chromePrefs.put("download.prompt_for_download", false);
	         chromePrefs.put("download.directory_upgrade", true);
	         chromePrefs.put("safebrowsing.enabled", true);
	         
	         options.setExperimentalOption("prefs", chromePrefs);
	         
	         driver = new ChromeDriver(options);
	         
	        try {
	            //get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
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
	           /* 
	            //iframe으로 구성된 곳은 해당 프레임으로 전환시킨다.
	            driver.switchTo().frame(driver.findElement(By.id("loginForm")));
	            */
	    
	        } catch (Exception e) {
	            
	            e.printStackTrace();
	        
	        } finally {
	 
	            driver.close();
	        }
	 
	    }
	    
	    
	    public File findfile(String path) {

	        File dir = new File(path);
	        if (dir.isDirectory()) {

                // 오래된 파일 삭제하기
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
				FileReader read = new FileReader(csv); //, Charset.forName("EUC-KR")
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