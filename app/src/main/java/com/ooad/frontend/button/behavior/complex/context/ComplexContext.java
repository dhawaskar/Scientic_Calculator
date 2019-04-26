package com.ooad.frontend.button.behavior.complex.context;

public class ComplexContext {

    public ComplexContext() {
    }

    private boolean addOpt, mulOpt, divOpt, subOpt;

    public boolean isAddOpt() {
        return addOpt;
    }

    public void setAddOpt(boolean addOpt) {
        this.addOpt = addOpt;
    }

    public boolean isMulOpt() {
        return mulOpt;
    }

    public void setMulOpt(boolean mulOpt) {
        this.mulOpt = mulOpt;
    }

    public boolean isDivOpt() {
        return divOpt;
    }

    public void setDivOpt(boolean divOpt) {
        this.divOpt = divOpt;
    }

    public boolean isSubOpt() {
        return subOpt;
    }

    public void setSubOpt(boolean subOpt) {
        this.subOpt = subOpt;
    }
}
