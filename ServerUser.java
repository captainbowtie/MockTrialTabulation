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

import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author captainbowtie
 */
public class ServerUser implements Serializable {
    private static final long serialVersionUID = 20161002;
    public static final int NONE = 0;
    public static final int CANREAD = 1;
    public static final int CANWRITE = 2;
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleIntegerProperty privileges;
    public ServerUser(String uname, String pwd, int priv){
        this.username = new SimpleStringProperty(uname);
        this.password = new SimpleStringProperty(pwd);
        this.privileges = new SimpleIntegerProperty(priv);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public int getPrivileges() {
        return privileges.get();
    }

    public void setPrivileges(int privileges) {
        this.privileges.set(privileges);
    }
    
}
