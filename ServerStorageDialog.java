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

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author captainbowtie
 */
public class ServerStorageDialog extends Stage {

    private String url = null;
    private String username = null;
    private String password = null;
    private final GridPane grid = new GridPane();
    private final Label urlPrompt = new Label("Server URL:");
    private final Label usernamePrompt = new Label("Username:");
    private final Label passwordPrompt = new Label("Password:");
    private final TextField urlField = new TextField("localhost");
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Button cancelButton = new Button("Cancel");
    private final Button okayButton = new Button();
    public static final int SAVE = 0;
    public static final int OPEN = 1;

    public ServerStorageDialog(int dialogType) {
        this.titleProperty().setValue("Server URL");
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        if (dialogType == 0) {
            okayButton.setText("Save");
        } else if (dialogType == 1) {
            okayButton.setText("Open");
        } else {
            Exception dialogErrorException = new Exception("Unknown server save dialog type error");
            dialogErrorException.printStackTrace();
        }
        cancelButton.setOnAction(e -> {
            this.close();
        });
        okayButton.setOnAction(e -> {
            url = urlField.getText();
            username = usernameField.getText();
            password = passwordField.getText();
            this.close();
        });
        grid.add(urlPrompt, 0, 0);
        grid.add(urlField, 0, 1, 4, 1);
        grid.add(usernamePrompt, 0, 2);
        grid.add(usernameField, 1, 2);
        grid.add(passwordPrompt, 2, 2);
        grid.add(passwordField, 3, 2);
        grid.add(cancelButton, 1, 3);
        grid.add(okayButton, 3, 3);
        Scene scene = new Scene(grid);
        scene.getStylesheets().add(ServerStorageDialog.class.getResource("MockTrialTabulation.css").toExternalForm());
        this.setScene(scene);
    }

    public String getURL() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
