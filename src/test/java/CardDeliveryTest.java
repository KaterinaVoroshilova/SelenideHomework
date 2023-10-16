import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    int addDays = 4;
    String planningDate = LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @Test
    public void shouldBeSuccessfully(){
        open("http://localhost:9999");
        String day = Integer.toString(LocalDate.now().plusDays(addDays).getDayOfMonth());
        $("[data-test-id='city'] input").setValue("Сы");
        $$("span.menu-item__control").find(exactText("Сыктывкар")).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("span.icon").click();
        $$("tr.calendar__row td.calendar__day").find(exactText(day)).click();
        $("[data-test-id='name'] input").setValue("Ворошилова Екатерина Анатольевна");
        $("[data-test-id='phone'] input").setValue("+79048631049");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));
    }
}
