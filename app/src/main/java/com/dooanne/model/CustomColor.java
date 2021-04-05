package com.dooanne.model;

public class CustomColor {
    int colorId;
    String colorCode;
    boolean isChecked = false;

    public CustomColor(String colorCode) {
        this.colorCode = colorCode;
    }

    public CustomColor(int colorId) {
        this.colorId = colorId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
