package com.batch08.srucms;

public class Model {
    private boolean isSelected;
    private String hno;

    public void setHno(String hno) {
        this.hno = hno;
    }

    public String getHno() {
        return hno;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
