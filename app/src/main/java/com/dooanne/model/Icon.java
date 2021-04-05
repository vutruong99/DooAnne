package com.dooanne.model;

public class Icon {
    int iconCode;
    boolean isChecked = false;

    public Icon(int iconCode) {
        this.iconCode = iconCode;
    }

    public int getIconCode() {
        return iconCode;
    }

    public void setIconCode(int iconCode) {
        this.iconCode = iconCode;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
