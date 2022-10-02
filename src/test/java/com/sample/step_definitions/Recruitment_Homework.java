package com.sample.step_definitions;

import com.sample.utilities.BrowserUtils;
import com.sample.utilities.Driver;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Recruitment_Homework {
    Actions actions = new Actions(Driver.getDriver());
    JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);


    @Test
    public void allAutomationSteps() {

    // 1.Go to www.transperfect.com
        Driver.getDriver().get("https://www.transperfect.com");

    // 2.Close the popup if needed
        WebElement popUp = Driver.getDriver().findElement(By.id("pop-box"));
        WebElement popCloseButton = Driver.getDriver().findElement(By.id("close-me-click"));

        if (popUp.isDisplayed()) {
            popCloseButton.click();
        }

    // 3.Click on Industries in the top navigation bar
        WebElement industries = Driver.getDriver().findElement(By.partialLinkText("Industries"));
        industries.click();

    // 4.Click on Retail & E-commerce
        industries = Driver.getDriver().findElement(By.partialLinkText("Industries")); // avoiding staleElementException with re-Assigning
        WebElement retailAndECommerce = Driver.getDriver().findElement(By.id("block-retailecommerce"));

        actions.moveToElement(industries).perform();
        retailAndECommerce.click();

    // 5.Wait 5 seconds
      try {
          Thread.sleep(5000);
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

    // 6.Scroll down/move the screen until Client Stories are visible
        WebElement clientStories = Driver.getDriver().findElement(By.xpath("//div[@class='text-left']/div"));

        js.executeScript("arguments[0].scrollIntoView(true);", clientStories);  //I use JavascriptExecutor because action.moveToElement returns "MoveTargetOutOfBoundsException"
        BrowserUtils.sleep(2); // I use Thread.sleep because next action not wait the JavaScriptExecutor

    // 7.Click on the search engine icon in the top navigation bar
        WebElement searchEngine = Driver.getDriver().findElement(By.xpath("//span[@class='t-search-link']"));
        searchEngine.click();

    // 8.Enter text "translation" in the Search text... textbox
        WebElement textBox = Driver.getDriver().findElement(By.id("edit-search-api-fulltext--2"));
        textBox.sendKeys("translation");
        BrowserUtils.sleep(1);

    // 9.Delete the text you just entered
        textBox.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        BrowserUtils.sleep(1);

    // 10.Enter "quote" in the Search text... textbox
        textBox.sendKeys("quote");

    // 11.Wait for the "Request a Free Quote" search result to appear
        WebElement requestFreeQuote = Driver.getDriver().findElement(By.xpath("//a[.='Request a Free Quote']"));
        wait.until(ExpectedConditions.visibilityOf(requestFreeQuote));

    // 12.Click on Request a Free Quote
        requestFreeQuote.click();

    // 13.Hover the mouse button over Website Localization to cause the popup with the description to appear
        WebElement websiteLocalization = Driver.getDriver().findElement(By.xpath("//div[@id='edit-your-interests']/div[2]"));
        actions.moveToElement(websiteLocalization).perform();
        BrowserUtils.sleep(1);

    // 14.Tick the boxes for Translation Services and Legal Services
        WebElement translationServices = Driver.getDriver().findElement(By.xpath("//div[@id='edit-your-interests']/div[1]"));
        WebElement legalServices = Driver.getDriver().findElement(By.xpath("//div[@id='edit-your-interests']/div[7]"));

        translationServices.click();
        legalServices.click();

    // 15.Enter text into First Name text box
        WebElement firstNameTextBox = Driver.getDriver().findElement(By.id("edit-first-name"));
        firstNameTextBox.sendKeys("John");

    // 16.Generate a random number and enter it into Telephone text box
        WebElement telephoneTextBox = Driver.getDriver().findElement(By.id("edit-phone-work"));
        int randomNumber = ThreadLocalRandom.current().nextInt(100000000, 999999999);

        telephoneTextBox.sendKeys(String.valueOf(randomNumber));

    // 17.Take a screenshot and save it to your desktop
        File source = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        String desktop = System.getProperty("user.home") + "/Desktop";

        try {
            FileUtils.copyFile(source, new File(desktop+"\\screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    // 18.Change the website language from English to Italian
        WebElement languageSwitcher = Driver.getDriver().findElement(By.className("language-switcher-language-url-btn"));
        actions.moveToElement(languageSwitcher).perform();

        WebElement italianLanguage = Driver.getDriver().findElement(By.xpath("//a[.='Italiano']"));
        italianLanguage.click();

    // 19.Open the Solutions (Soluzioni) page in a new tab
        WebElement soluzioni = Driver.getDriver().findElement(By.xpath("//a[.='Soluzioni']"));
        soluzioni.sendKeys(Keys.CONTROL,Keys.RETURN);
        BrowserUtils.sleep(2);

        //switch to new opened TAB
        String soluzioniURL = soluzioni.getAttribute("href");
        Set<String> windowHandles = Driver.getDriver().getWindowHandles();

        for (String eachHandle : windowHandles) {
            Driver.getDriver().switchTo().window(eachHandle);
            if (Driver.getDriver().getCurrentUrl().equals(soluzioniURL)){
                break;
            }
        }
        BrowserUtils.sleep(2);

    // 20.Close the browser
        Driver.closeDriver();
    }

}
