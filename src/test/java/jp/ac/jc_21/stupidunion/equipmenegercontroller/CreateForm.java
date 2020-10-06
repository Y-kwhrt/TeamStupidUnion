package jp.ac.jc_21.stupidunion.equipmenegercontroller;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.Optional;

public class CreateForm {
    static final String urlCreate = "/equipment/create";

    HtmlTextInput type;
    HtmlTextInput model;
    HtmlTextInput manufacturer;
    HtmlTextInput spec;
    HtmlDateInput purchaseDate;
    HtmlNumberInput lifespanInYears;
    HtmlSubmitInput submitButton;
    private CreateForm(
            HtmlTextInput type,
            HtmlTextInput model,
            HtmlTextInput manufacture,
            HtmlTextInput spec,
            HtmlDateInput purchaseDate,
            HtmlNumberInput lifespanInYears,
            HtmlSubmitInput submitButton
    ) {
        this.type = type;
        this.model = model;
        this.manufacturer = manufacture;
        this.spec = spec;
        this.purchaseDate = purchaseDate;
        this.lifespanInYears = lifespanInYears;
        this.submitButton = submitButton;
    }

    public void setType(String value) {
        type.setValueAttribute(value);
    }
    public void setModel(String value) {
        model.setValueAttribute(value);
    }
    public void setManufacturer(String value) {
        manufacturer.setValueAttribute(value);
    }
    public void setSpec(String value) {
        spec.setValueAttribute(value);
    }
    public void setpurchaseDate(String value) {
        purchaseDate.setValueAttribute(value);
    }
    public void setLifespanInYears(String value) {
        lifespanInYears.setValueAttribute(value);
    }
    public Optional<Page> submit() {
        try {
            return Optional.of(submitButton.click());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static CreateForm from(HtmlPage page) {
        HtmlForm form = getElementFrom(page);
        var type = (HtmlTextInput) form.getInputByName("type");
        var model = (HtmlTextInput) form.getInputByName("model");
        var manufacture = (HtmlTextInput) form.getInputByName("manufacturer");
        var spec = (HtmlTextInput) form.getInputByName("spec");
        var purchaseDate = (HtmlDateInput) form.getInputByName("purchaseDate");
        var lifespanInYears = (HtmlNumberInput) form.getInputByName("lifespanInYears");
        var submitButton = getSubmitButtonElementFrom(form);
        return new CreateForm(type, model, manufacture, spec, purchaseDate, lifespanInYears, submitButton);
    }
    private static HtmlForm getElementFrom(HtmlPage page) {
        return (HtmlForm) page.getElementsByTagName("FORM")
                .stream()
                .filter(element -> element.getAttribute("action").equals(urlCreate))
                .findFirst().orElseThrow(() -> new IllegalStateException("create form not found"));
    }
    private static HtmlSubmitInput getSubmitButtonElementFrom(HtmlForm form) {
        return (HtmlSubmitInput) form.getElementsByAttribute("INPUT", "type", "submit")
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("create form submit button not found"));
    }
}
