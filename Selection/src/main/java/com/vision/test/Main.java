package com.vision.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class Main {
	public static String searchButton = "//input[@value='Search']";
	public static String selectDropdown = "//select[@name='dist']";
	public static String pagination = "//li[@class='page-item']/a";

	/*
	 * This is a web scraping tool created to get the cutoff marks from tnea website
	 */

	public static void main(String[] args) throws InterruptedException {
		// Here we are using chrome options to use an existing browser session as the
		// tnea website has captacha

		// run following command in your cmd and perform the initial captacha acceptance
		// and then navigate to search screen

		// "C:\Program Files\Google\Chrome\Application\chrome.exe"
		// --remote-debugging-port=9991
		// --user-data-dir="C:\Users\<user_account>\Downloads\ChromeProfileDirectory

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("debuggerAddress", "127.0.01:9991");
		WebDriver driver;
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(options);

		Select select = new Select(driver.findElement(By.xpath(selectDropdown)));
		select.selectByVisibleText("Chennai");
		driver.findElement(By.xpath(searchButton)).click();

		List<String> college = new ArrayList<String>();

		// pagination to get all the page data
		for (WebElement pag : driver.findElements(By.xpath(pagination))) {
			pag.click();
			for (WebElement ele : driver.findElements(By.xpath("//table/tbody/tr"))) {
				if (!ele.findElement(By.xpath("td[6]")).getText().equalsIgnoreCase("")
						&& !ele.findElement(By.xpath("td[6]")).getText().equalsIgnoreCase("***")) {
					if (Double.parseDouble(ele.findElement(By.xpath("td[6]")).getText()) <= 155) {
						college.add(ele.findElement(By.xpath("td[1]")).getText() + "|"
								+ ele.findElement(By.xpath("td[2]")).getText() + "|"
								+ ele.findElement(By.xpath("td[4]")).getText() + "|"
								+ ele.findElement(By.xpath("td[6]")).getText());

					}
				}

			}
		}

		// goal was to write it to csv due to time constraint, simply sysout and then
		// save as csv
		for (String key : college) {
			System.out.println(key);
		}

	}

}
