import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import javax.swing.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HerokuAppTest {
    WebDriver driver;
    public final static String BASE_URL = "http://the-internet.herokuapp.com";

    @BeforeMethod
    public void startActions() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }


    @Test
    public void addRemoveElements() {
        WebElement addRemoveElementsSection = driver.findElement(By.xpath("//a[contains(text(),'Add/Remove Elements')]"));
        addRemoveElementsSection.click();
        WebElement findAddElementButton = driver.findElement(By.xpath("//button[contains(text(),'Add Element')]"));
        findAddElementButton.click();
        WebElement findDeleteButtonOne = driver.findElement(By.xpath("//button[contains(text(),'Delete')]"));
        findAddElementButton.click();
        WebElement findDeleteButtonTwo = driver.findElement(By.xpath("//body/div[2]/div[1]/div[1]/div[1]/button[2]"));
        findDeleteButtonTwo.click();
        int count = driver.findElements(By.className("added-manually")).size();
        Assert.assertEquals(count, 1);
    }

    @Test
    public void checkCheckboxes() {
        startActions();
        WebElement openCheckboxesSection = driver.findElement(By.cssSelector("div.row:nth-child(2) div.large-12.columns:nth-child(2) ul:nth-child(4) li:nth-child(6) > a:nth-child(1)"));
        openCheckboxesSection.click();
        WebElement checkCheckboxOne = driver.findElement(By.cssSelector("div.row:nth-child(2) div.large-12.columns:nth-child(2) div.example:nth-child(1) form:nth-child(2) > input:nth-child(1)"));
        Assert.assertTrue(!checkCheckboxOne.isSelected());
        checkCheckboxOne.click();
        Assert.assertTrue(checkCheckboxOne.isSelected());
        WebElement checkCheckboxTwo = driver.findElement(By.cssSelector("div.row:nth-child(2) div.large-12.columns:nth-child(2) div.example:nth-child(1) form:nth-child(2) > input:nth-child(3)"));
        Assert.assertTrue(checkCheckboxTwo.isSelected());
        checkCheckboxTwo.click();
        Assert.assertTrue(!checkCheckboxTwo.isSelected());
        closeBrowser();
    }

    @Test
    public void checkDropdown() {
        startActions();
        WebElement openDropdownSection = driver.findElement(By.linkText("Dropdown"));
        openDropdownSection.click();
        WebElement element = driver.findElement(By.xpath("//select[@id='dropdown']"));
        element.click();
        Select select = new Select(element);
        List<WebElement> allOptions = select.getOptions();
        Assert.assertTrue(true, "Please select an option");
        WebElement optionOne = driver.findElement(By.xpath("//option[contains(text(),'Option 1')]"));
        optionOne.click();
        Assert.assertTrue(optionOne.isSelected());
        element.click();
        WebElement optionTwo = driver.findElement(By.xpath("//option[contains(text(),'Option 2')]"));
        optionTwo.click();
        Assert.assertTrue(optionTwo.isSelected());
        closeBrowser();
    }

    @Test
    public void checkInputs() {
        startActions();
        WebElement openInputSection = driver.findElement(By.xpath("//a[contains(text(),'Inputs')]"));
        openInputSection.click();
        WebElement inputField = driver.findElement(By.tagName("input"));
        inputField.click();
        inputField.sendKeys("12345");
        Assert.assertTrue(true, "12345");
        inputField.clear();
        inputField.sendKeys("-145.555e");
        Assert.assertTrue(true, "-145.555e");
        inputField.clear();
        closeBrowser();
    }

    @Test
    public void checkSortableDataTables() {
        startActions();
        WebElement openIDataTablesSection = driver.findElement(By.xpath("//a[contains(text(),'Sortable Data Tables')]"));
        openIDataTablesSection.click();
        WebElement table = driver.findElement(By.id("table1"));
        List<WebElement> allRows = table.findElements(By.tagName("tr"));
        List<WebElement> allCells = table.findElements(By.tagName("td"));
     /*for (WebElement row : allRows) {
        List<WebElement> rows = row.findElements(By.tagName("tr[%s]",i));
        for (WebElement cell : allCells) {
            List<WebElement> cells = cell.findElements(By.tagName("td[]"));
        }
        Assert.assertEquals(allCells.get(0),"Smith");
        Assert.assertEquals(allCells.get(1),"John");
        Assert.assertEquals(allCells.get(2),"jsmith@gmail.com");
        Assert.assertEquals(allCells.get(3),"$50.00");
        Assert.assertEquals(allCells.get(4),"http://www.jsmith.com");
           */
    }

    @Test
    public void checkTypos() {
        startActions();
        WebElement openTyposSection = driver.findElement(By.linkText("Typos"));
        openTyposSection.click();
        WebElement getStringOne = driver.findElement(By.xpath("//p[contains(text(),'This example demonstrates a typo being introduced.')]"));
        WebElement getStringTwo = driver.findElement(By.xpath("//p[contains(text(),\"Sometimes you'll see a typo, other times you won't\")]"));
        Assert.assertEquals(getStringOne.getText(), "This example demonstrates a typo being introduced. It does it randomly on each page load.");
        Assert.assertEquals(getStringTwo.getText(), "Sometimes you'll see a typo, other times you won't.");
        closeBrowser();
    }

    @Test
    public void checkHovers() {
        WebElement openHoversSection = driver.findElement(By.linkText("Hovers"));
        openHoversSection.click();
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("(//div[@class='figcaption'])[1]"))).build().perform();
        WebElement checkImage= driver.findElement(By.xpath("(//div[@class='figcaption'])[1]/h5"));
        Assert.assertEquals(checkImage.getText(), "name: user1");
        }

    @Test
    public void checkNotification(){
        WebElement hoverNotification = driver.findElement(By.linkText("Notification Messages"));
        hoverNotification.click();
        WebElement clickNotification = driver.findElement(By.xpath("//a[contains(text(),'Click here')]"));
        clickNotification.click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        WebElement findMessage=driver.findElement(By.xpath("//div[@id='flash']"));
        Assert.assertEquals(findMessage.getText(),"Action successful");
    }
}
