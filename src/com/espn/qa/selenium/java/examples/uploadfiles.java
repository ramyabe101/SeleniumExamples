package com.espn.qa.selenium.java.examples;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.NoSuchElementException;
public class uploadfiles{
public static String GuruURL = "http://demo.guru99.com/test/upload/";
public static String FileLocation = "C:\\testfile.txt";
public static WebDriver driver = null;
public static void main(String[] args){
	System.setProperty("webdriver.chrome.driver","C:\\chromedriver_win32\\chromedriver.exe");
	driver = new ChromeDriver();
	//instantiating a uploadfiles class by creating an object
	uploadfiles obj = new uploadfiles();
    //navigating to the URL by calling navigate method and intiliazing the return value to URLTitle variable
	String URLTitle = obj.navigate();
	//if the title of the webpage is availble proceed to other methods
	if((URLTitle.compareTo("File Upload Demo")) == 0) {
		//initializing fileStatus variable
		String fileStatus = "Page opened";
		//Calling method locateuploadbox and reading the Status into NewStatus
		String NewStatus = obj.locateuploadbox(fileStatus);
		//if the NewStatus is "File Ready" call the method clickonAccepttermscheckbox
		if ((NewStatus.compareTo("File Ready")) == 0 ) {
		//if the status returned from clickonAccepttermscheckbox is 
			String UpdatedStatus = obj.clickonAccepttermscheckbox(NewStatus);
			if((UpdatedStatus.compareTo("Checkbox clicked")) == 0 ) {
				
				String FinalStatus = obj.submitfile(UpdatedStatus);
				
				if((FinalStatus.compareTo("File Submitted"))== 0) {
					System.out.println(FinalStatus);
					driver.quit();
				}
					
			}
			
			else {
				
				System.out.println("Issues with submitting File");
				driver.quit();
				
			}
			
		}
		
		else {
			fileStatus = "Issues with locating the uploading box";
			System.out.println(fileStatus);
			driver.quit();
		}
		
	}
	
	//if the webpage title is not available then quit
	else {
		String fileStatus = "Issues with loading the webpage";
		System.out.println(fileStatus);
		driver.quit();
	}
		
}

public String navigate(){
	driver.get(GuruURL);
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	String title = driver.getTitle();
	System.out.println(title);
	return (title);
}

public String locateuploadbox(String fileStatus){
	String Status = fileStatus;
	try {
	//creating explicit wait for 10 seconds
    WebDriverWait myWaitVar = new WebDriverWait(driver,10);
    myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(By.id("uploadfile_0")));
    //locating uploadbox on the webpage
	WebElement uploadbox = driver.findElement(By.id("uploadfile_0"));
	//using mouse actions to move to the uploadbox 
	Actions builder = new Actions(driver);
	Action clickonExecTypebox = builder.moveToElement(uploadbox).build();
	//using mouse click to click on upload box
	uploadbox.click();
	//sending file name to the uploadbox
	uploadbox.sendKeys(FileLocation);
	System.out.println("File is selected in the upload box...");
	Status = "File Ready";
	
	}
	catch(NoSuchElementException e) {
		System.out.println("Upload box not found");
		driver.quit();
		
	}
	
	return (Status);
}

public String clickonAccepttermscheckbox(String fileStatus) {
	String checkboxStatus = fileStatus;
	try {
	WebElement checkbox = driver.findElement(By.id("terms"));
	checkbox.click();
	checkboxStatus = "Checkbox clicked";
	}
	catch(NoSuchElementException e) {
		System.out.println("Accept terms checkbox not found");
		driver.quit();
	}
return(checkboxStatus);
}

public String submitfile(String fileStatus) throws NoSuchElementException {
	String submitStatus = fileStatus;
	WebElement submitbutton = driver.findElement(By.id("submitbutton"));
	submitbutton.click();
	submitStatus = "File Submitted";
	return(submitStatus);
	
}
}