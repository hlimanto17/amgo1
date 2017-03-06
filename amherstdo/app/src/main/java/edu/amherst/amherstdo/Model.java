package edu.amherst.amherstdo;

/**
 * Created by Hutomo Limanto on 3/5/2017.
 */

public class Model {
    String name;
    String description;
    String priority;
    //int value; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */

    Model(String name, String description, String priority){
        this.name = name;
        this.description = description;
        this.priority = priority;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }

    public String getPriority(){
        return this.priority;
    }
}
