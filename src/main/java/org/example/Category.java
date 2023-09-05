package org.example;

public class Category {

    //data members
    private String categoryId;
    private String categoryName;
    private String description;
    private Toy toys;

    public Category(){
        this("", "", "");
    }

    public Category(String categoryId, String categoryName, String description){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }

    //getter
    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDescription() {
        return description;
    }

    //setter
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //method
}
