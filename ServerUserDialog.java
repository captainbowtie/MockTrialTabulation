/*
 * Copyright (C) 2016 captainbowtie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.allenbarr.MockTrialTabulation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ServerUserDialog extends Stage {

    private TableView<ServerUser> table = new TableView<>();
    private final ObservableList<ServerUser> data;
    final HBox hb = new HBox();

    public ServerUserDialog(ObservableList<ServerUser> su) {
        data = su;
        Scene scene = new Scene(new Group());

        table.setEditable(true);
        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        TableColumn usernameCol = new TableColumn("Username");
        usernameCol.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        usernameCol.setCellFactory(cellFactory);
        usernameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<ServerUser, String>>() {
            @Override
            public void handle(CellEditEvent<ServerUser, String> t) {
                ((ServerUser) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setUsername(t.getNewValue());
            }
        }
        );

        TableColumn passwordCol = new TableColumn("Password");
        passwordCol.setCellValueFactory(
                new PropertyValueFactory<ServerUser, String>("password"));
        passwordCol.setCellFactory(cellFactory);
        passwordCol.setOnEditCommit(
                new EventHandler<CellEditEvent<ServerUser, String>>() {
            @Override
            public void handle(CellEditEvent<ServerUser, String> t) {
                ((ServerUser) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setPassword(t.getNewValue());
            }
        }
        );

        TableColumn privCol = new TableColumn("Priviledges");
        privCol.setCellValueFactory(
                new PropertyValueFactory<ServerUser, Integer>("priviledges"));
        privCol.setCellFactory(cellFactory);
        privCol.setOnEditCommit(
                new EventHandler<CellEditEvent<ServerUser, Integer>>() {
            @Override
            public void handle(CellEditEvent<ServerUser, Integer> t) {
                ((ServerUser) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setPrivileges(t.getNewValue());
            }
        }
        );

        table.setItems(data);
        table.getColumns().addAll(usernameCol, passwordCol, privCol);

        final TextField addUsername = new TextField();
        addUsername.setPromptText("Username");
        final TextField addPassword = new TextField();
        addPassword.setPromptText("Password");
        final TextField addPriviledges = new TextField();
        addPriviledges.setPromptText("0, 1, or 2");

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new ServerUser(
                        addUsername.getText(),
                        addPassword.getText(),
                        Integer.parseInt(addPriviledges.getText())));
                addUsername.clear();
                addPassword.clear();
                addPriviledges.clear();
            }
        });

        hb.getChildren().addAll(addUsername, addPassword, addPriviledges, addButton);

        final VBox vbox = new VBox();
        vbox.getChildren().addAll(table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        this.setScene(scene);
    }


    class EditingCell extends TableCell<ServerUser, String> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                        Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(textField.getText());
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}
