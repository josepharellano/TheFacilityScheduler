package controllers;

import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import models.IModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AutoCompleteTextField<T extends IModel> extends TextField {

    private ContextMenu suggestionsMenu;
    private T selection;
    private ObservableList<T> data;

    public AutoCompleteTextField(){
        super();

        suggestionsMenu = new ContextMenu();

        /*
         * Lambda Expressions to create a listener to listen for changes in the textfield.
         * The Context Menu will be filtered down based on the newValue
         */
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            String textField = this.getText();

            List<MenuItem> menuItems = this.data.stream().filter(value-> {
                if(!newValue.isEmpty()) {
                    return value.getLabel().toLowerCase().contains(textField.toLowerCase());
                }else{
                    return true;
                }
        }).map(customer ->{
                MenuItem item = new MenuItem(customer.getLabel());
                item.setOnAction(evt->{
                    this.setText(customer.getLabel());
                    selection = customer;
                });
                return item;
            }).collect(Collectors.toList());
            suggestionsMenu.getItems().clear();
            suggestionsMenu.getItems().addAll(menuItems);
            if(!suggestionsMenu.isShowing()) suggestionsMenu.show(AutoCompleteTextField.this,Side.BOTTOM,0,0);
        });

        /*
         * Display Complete List of available customers when textfield is in focus.
         */
        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(selection == null) {
                if (newValue) {
                    if (this.getText().isEmpty() && this.data != null) {
                        //Populate suggestionsMenu
                        List<MenuItem> menuItems = this.data.stream().map(customer -> {
                            MenuItem item = new MenuItem(customer.getLabel());
                            item.setOnAction(evt -> {
                                this.setText(customer.getLabel());
                                selection = customer;
                            });
                            return item;
                        }).collect(Collectors.toList());
                        suggestionsMenu.getItems().addAll(menuItems);
                        suggestionsMenu.show(this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    //Clear out all suggestions
                    suggestionsMenu.getItems().clear();
                    suggestionsMenu.hide();
                }
            }else{
                this.setText(selection.getLabel());
            }
        });

    }



    public void setAutoFillItems(List<T> items){
        this.data = FXCollections.observableArrayList(items);
    }

    public void setSelection(T selection){
        this.selection = selection;
    }
    public T getSelectedModel(){ return selection;}

}

