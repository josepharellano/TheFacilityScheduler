package customcontrols;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import utilities.Exceptions;

import java.awt.*;
import java.util.Calendar;
import java.util.function.Consumer;

public class WeekTextField extends TextField {

        private Consumer<Integer> callBack;

        public WeekTextField(){
            super();
            /*
             *Listens for input changes to textbox and ensures only numbers are entered.
             */
            this.textProperty().addListener((observable, oldValue, newValue) -> {
                String input = this.getText();

                final int weeksInYear =Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_YEAR);
                if(!input.isEmpty()) {
                    try {
                        int week = Integer.parseInt(input);
                        if(input.length() <= 2 && week > 0 && week <= weeksInYear){
                            this.setText(newValue);
                            callBack.accept(week);
                        }else{
                            this.setText(oldValue);
                            callBack.accept(week);
                            throw new Exception();
                        }
                    } catch (Exception ex) {
                        this.setText(oldValue);
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Can only enter numeric values 1-"+ weeksInYear +" in input box");
                        alert.showAndWait();
                    }
                }
            });

        }

    public void setCallBack(Consumer<Integer> callBack) {
        this.callBack = callBack;
    }
}

