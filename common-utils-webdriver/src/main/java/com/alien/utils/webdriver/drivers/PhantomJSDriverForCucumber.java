package com.alien.utils.webdriver.drivers;

import static org.apache.commons.io.FileUtils.writeByteArrayToFile;
import static org.apache.commons.io.IOUtils.toByteArray;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.openqa.selenium.OutputType;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.alien.utils.webdriver.CucumberWebDriver;
import cucumber.api.Scenario;

public class PhantomJSDriverForCucumber  {
//	public class PhantomJSDriverForCucumber extends PhantomJSDriver implements CucumberWebDriver {
//
//    private static final String IMAGE_PNG = "image/png";
//    private static final String PHANTOMJS_DRIVER_PATH = System.getProperty("user.dir") + "/target/classes/phantomdriver/2_1_1";
//    private static final String PHANTOMJS_CLASSPATH = "phantomdriver/2_1_1/phantomjs";
//
//    public PhantomJSDriverForCucumber(DesiredCapabilities capabilities) {
//        super(capabilities);
//    }
//
//    public static CucumberWebDriver getPhantomDriver() throws IOException {
//
//        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
//
//        ArrayList<String> cliArgsCap = createCliArgs();
//        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
//        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "loadImages", true);
//        capabilities.setCapability("takesScreenshot", true);
//
//        final String absolutePath = loadExecutable();
//
//        capabilities.setCapability(
//                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, absolutePath);
//
//        return new PhantomJSDriverForCucumber(capabilities);
//    }
//
//    private static String loadExecutable() throws IOException {
//
//        final File phantomJsExecutable = new File(PHANTOMJS_DRIVER_PATH + "/phantomjs");
//
//        if (phantomJsExecutable.exists()) {
//            return phantomJsExecutable.getPath();
//        }
//
//        final File phantomJsPath = new File(PHANTOMJS_DRIVER_PATH);
//        if (!phantomJsPath.exists()) {
//            phantomJsPath.mkdirs();
//        }
//
//        createFileFromResourceOnClasspath(phantomJsExecutable, PHANTOMJS_CLASSPATH);
//
//        return phantomJsExecutable.getPath();
//    }
//
//    private static void createFileFromResourceOnClasspath(File phantomJsExecutable, String classpath) throws IOException {
//        final InputStream phantomJsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(classpath);
//        writeByteArrayToFile(phantomJsExecutable, toByteArray(phantomJsStream));
//        phantomJsExecutable.setExecutable(true);
//    }
//
//    private static ArrayList<String> createCliArgs() {
//        ArrayList<String> cliArgsCap = new ArrayList<>();
//        cliArgsCap.add("--ignore-ssl-errors=true");
//        cliArgsCap.add("--web-security=false");
//        cliArgsCap.add("--disk-cache=true");
//        cliArgsCap.add("--max-disk-cache-size=256");
//        cliArgsCap.add("--proxy-type=none");
//        return cliArgsCap;
//    }
//
//    @Override
//    public void takeScreenShot(Scenario scenario, String linkText) {
//        scenario.embed(this.getScreenshotAs(OutputType.BYTES), IMAGE_PNG);
//    }
}
