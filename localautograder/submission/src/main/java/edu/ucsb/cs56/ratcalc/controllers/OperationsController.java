package edu.ucsb.cs56.ratcalc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.ucsb.cs56.ratcalc.formbeans.RatCalcForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.ucsb.cs56.ratcalc.model.Rational;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class OperationsController {

    private Logger logger = LoggerFactory.getLogger(OperationsController.class);

    /**
     * Check for denominator errors.  Sets error message(s) in the
     * form bean if either denominator is zero.
     * 
     * @param ratCalcForm form bean for rational number calculation
     * @return true if there are denominator errors
     */
    public boolean checkDenominatorErrors(RatCalcForm ratCalcForm) {
        String errorMsg = "Denominator should be non-zero";
        boolean result = false;

        if (ratCalcForm.getDenom1() == 0) {
            ratCalcForm.setFrac1Error(errorMsg);
            result = true;
        }

        if (ratCalcForm.getDenom2() == 0) {
            ratCalcForm.setFrac2Error(errorMsg);
            result = true;
        }

        return result;
    }

    public boolean checkDivideByZero(RatCalcForm ratCalcForm) {
        String errorMsg = "Cannot divide by zero";
        if (ratCalcForm.getNum2() == 0) {
            ratCalcForm.setFrac2Error(errorMsg);
            return true;
        }
        return false;
    }

    @GetMapping("/add")
    public String getAdd(Model model) {
        RatCalcForm ratCalcForm = new RatCalcForm();
        ratCalcForm.setOp("+");
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/add";
    }

    @GetMapping("/add/results")
    public String getAddResult(Model model, @Valid RatCalcForm ratCalcForm, BindingResult bindingResult) {
        logger.info("getAddResult ratCalcForm=" + ratCalcForm);
        ratCalcForm.setOp("+");

        if (!bindingResult.hasErrors() && !checkDenominatorErrors(ratCalcForm)) {
            Rational r1 = new Rational(ratCalcForm.getNum1(), ratCalcForm.getDenom1());
            Rational r2 = new Rational(ratCalcForm.getNum2(), ratCalcForm.getDenom2());
            Rational result = Rational.sum(r1, r2);
            logger.info("r1=" + r1 + " r2=" + r2 + " result=" + result);
            ratCalcForm.setNumResult(result.getNumerator());
            ratCalcForm.setDenomResult(result.getDenominator());
        }

        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/add";
    }

    @GetMapping("/subtract")
    public String getSubtract(Model model) {
        RatCalcForm ratCalcForm = new RatCalcForm();
        ratCalcForm.setOp("-");
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/subtract";
    }

    @GetMapping("/subtract/results")
    public String getSubtractResult(Model model, @Valid RatCalcForm ratCalcForm, BindingResult bindingResult) {
        logger.info("getSubtractResult ratCalcForm=" + ratCalcForm);
        ratCalcForm.setOp("-");

        if (!bindingResult.hasErrors() && !checkDenominatorErrors(ratCalcForm)) {
            Rational r1 = new Rational(ratCalcForm.getNum1(), ratCalcForm.getDenom1());
            Rational r2 = new Rational(ratCalcForm.getNum2(), ratCalcForm.getDenom2());
            Rational result = Rational.difference(r1, r2);
            logger.info("r1=" + r1 + " r2=" + r2 + " result=" + result);
            ratCalcForm.setNumResult(result.getNumerator());
            ratCalcForm.setDenomResult(result.getDenominator());
        }
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/subtract";
    }

    @GetMapping("/multiply")
    public String getMultiply(Model model) {
        RatCalcForm ratCalcForm = new RatCalcForm();
        ratCalcForm.setOp("x");
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/multiply";
    }

    @GetMapping("/multiply/results")
    public String getMultiplyResult(Model model, @Valid RatCalcForm ratCalcForm, BindingResult bindingResult) {
        ratCalcForm.setOp("x");

        if (!bindingResult.hasErrors() && !checkDenominatorErrors(ratCalcForm)) {
            Rational r1 = new Rational(ratCalcForm.getNum1(), ratCalcForm.getDenom1());
            Rational r2 = new Rational(ratCalcForm.getNum2(), ratCalcForm.getDenom2());
            Rational result = Rational.product(r1, r2);
            logger.info("r1=" + r1 + " r2=" + r2 + " result=" + result);
            ratCalcForm.setNumResult(result.getNumerator());
            ratCalcForm.setDenomResult(result.getDenominator());
        }
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/multiply";
    }

    @GetMapping("/divide")
    public String getDivide(Model model) {
        RatCalcForm ratCalcForm = new RatCalcForm();
        ratCalcForm.setOp("/");
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/divide";
    }

    @GetMapping("/divide/results")
    public String getDivideResult(Model model, @Valid RatCalcForm ratCalcForm, BindingResult bindingResult) {
        ratCalcForm.setOp("/");

        if (!bindingResult.hasErrors() && !checkDenominatorErrors(ratCalcForm) && !checkDivideByZero(ratCalcForm)) {
            Rational r1 = new Rational(ratCalcForm.getNum1(), ratCalcForm.getDenom1());
            Rational r2 = new Rational(ratCalcForm.getNum2(), ratCalcForm.getDenom2());
            Rational result = Rational.quotient(r1, r2);
            logger.info("r1=" + r1 + " r2=" + r2 + " result=" + result);
            ratCalcForm.setNumResult(result.getNumerator());
            ratCalcForm.setDenomResult(result.getDenominator());
        }
        model.addAttribute("ratCalcForm", ratCalcForm);
        return "operations/divide";
    }

}
