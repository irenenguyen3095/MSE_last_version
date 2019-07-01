package de.uni_due.paluno.chuj.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class partConversationModel {


        @SerializedName("Username")
        @Expose
        private String username;
        @SerializedName("Password")
        @Expose
        private String password;
        @SerializedName("Recipient")
        @Expose
        private String recipient;
        @SerializedName("Offset")
        @Expose
        private String offset;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRecipient() {
            return recipient;
        }

        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }

        public String getOffset() {
            return offset;
        }

        public void setOffset(String offset) {
            this.offset = offset;
        }

    public partConversationModel(String username, String password, String recipient, String offset) {
        this.username = username;
        this.password = password;
        this.recipient = recipient;
        this.offset = offset;
    }
}


