package edu.ucsb.cs56.ratcalc.formbeans;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Bean object that corresponds to fields in HTML form for doing an operation on
 * two rational numbers
 */
public class RatCalcForm {

    

    @NotNull
    private Integer num1;

    @NotNull
    private Integer denom1;

    @NotNull
    private Integer num2;

    @NotNull
    private Integer denom2;

    private String op;
    private Integer numResult;
    private Integer denomResult;
    private String frac1Error;
    private String frac2Error;

    public Integer getNum1() {
        return this.num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public Integer getDenom1() {
        return this.denom1;
    }

    public void setDenom1(Integer denom1) {
        this.denom1 = denom1;
    }

    public Integer getNum2() {
        return this.num2;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }

    public Integer getDenom2() {
        return this.denom2;
    }

    public void setDenom2(Integer denom2) {
        this.denom2 = denom2;
    }

    public String getOp() {
        return this.op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Integer getNumResult() {
        return this.numResult;
    }

    public void setNumResult(Integer numResult) {
        this.numResult = numResult;
    }

    public Integer getDenomResult() {
        return this.denomResult;
    }

    public void setDenomResult(Integer denomResult) {
        this.denomResult = denomResult;
    }

    public String getFrac1Error() {
        return this.frac1Error;
    }

    public void setFrac1Error(String frac1Error) {
        this.frac1Error = frac1Error;
    }

    public String getFrac2Error() {
        return this.frac2Error;
    }

    public void setFrac2Error(String frac2Error) {
        this.frac2Error = frac2Error;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RatCalcForm)) {
            return false;
        }
        RatCalcForm ratCalcForm = (RatCalcForm) o;
        return Objects.equals(num1, ratCalcForm.num1) && Objects.equals(denom1, ratCalcForm.denom1) && Objects.equals(num2, ratCalcForm.num2) && Objects.equals(denom2, ratCalcForm.denom2) && Objects.equals(op, ratCalcForm.op) && Objects.equals(numResult, ratCalcForm.numResult) && Objects.equals(denomResult, ratCalcForm.denomResult) && Objects.equals(frac1Error, ratCalcForm.frac1Error) && Objects.equals(frac2Error, ratCalcForm.frac2Error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num1, denom1, num2, denom2, op, numResult, denomResult, frac1Error, frac2Error);
    }
   

    @Override
    public String toString() {
        return "{" +
            " num1='" + num1 + "'" +
            ", denom1='" + denom1 + "'" +
            ", num2='" + num2 + "'" +
            ", denom2='" + denom2 + "'" +
            ", op='" + op + "'" +
            ", numResult='" + numResult + "'" +
            ", denomResult='" + denomResult + "'" +
            ", frac1Error='" + frac1Error + "'" +
            ", frac2Error='" + frac2Error + "'" +
            "}";
    }


}