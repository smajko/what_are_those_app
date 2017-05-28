package org.smajko.watapp;

/*************************************
 *  Final result to display to user
 ************************************/

public class Result {
    public String name;
    public String description;
    public String percent;

    public Result(String name, String description, String percent) {
        this.name = name;
        this.description = description;
        this.percent = percent;
    }
}