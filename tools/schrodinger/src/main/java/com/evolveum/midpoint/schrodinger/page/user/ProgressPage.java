package com.evolveum.midpoint.schrodinger.page.user;

import com.codeborne.selenide.SelenideElement;
import com.evolveum.midpoint.schrodinger.component.common.FeedbackBox;
import com.evolveum.midpoint.schrodinger.page.BasicPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by matus on 3/22/2018.
 */
public class ProgressPage extends BasicPage {

    public FeedbackBox<ProgressPage> feedback() {
        SelenideElement feedback = $(By.cssSelector("div.feedbackContainer"));

        return new FeedbackBox<ProgressPage>(this, feedback);
    }
}
