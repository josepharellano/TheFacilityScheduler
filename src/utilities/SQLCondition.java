package utilities;

import javafx.util.Pair;

public class SQLCondition {
    StringBuilder condition;

    public SQLCondition(Pair<String,String> condition){
        this.condition = new StringBuilder();
        this.condition.append(condition.getKey()).append("=").append(condition.getValue());
    }

    public void addAndCondition(Pair<String,String> condition){
        this.condition.append(" AND ").append(condition.getKey()).append("=").append(condition.getValue());
    }

    public void addOrCondition(Pair<String,String> condition){
        this.condition.append(" OR ").append(condition.getKey()).append("=").append(condition.getValue());
    }

    public String getSQLStatement(){
        return this.condition.toString();
    }

}
