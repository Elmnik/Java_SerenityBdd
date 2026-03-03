package com.bdg.ui.userinterfaces;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

@DefaultUrl("https://www.saucedemo.com") //Se usa una sola instancia con url
public class LoginPage extends PageObject {

    public static final Target USERNAME_FIELD = Target.the("campo usuario")
            .located(By.id("user-name"));

    public static final Target PASSWORD_FIELD = Target.the("campo password")
            .located(By.id("password"));

    public static final Target LOGIN_BUTTON = Target.the("botón login")
            .located(By.id("login-button"));

    public static final Target ERROR_MESSAGE = Target.the("mensaje de error")
            .locatedBy("h3[data-test='error']");
}