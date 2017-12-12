package com.fyt.loki.fyt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ergas on 11/13/2017.
 */

public class ProfileModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profile")
    @Expose
    private Profile profile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public class Profile {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("birth_date")
        @Expose
        private String birthDate;
        @SerializedName("gender")
        @Expose
        private Integer gender;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("friends_count")
        @Expose
        private Integer friendsCount;
        @SerializedName("channel")
        @Expose
        private String channel;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("notifications_status")
        @Expose
        private Boolean notificationsStatus;
        @SerializedName("messages_status")
        @Expose
        private Boolean messagesStatus;
        @SerializedName("updated")
        @Expose
        private String updated;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getFriendsCount() {
            return friendsCount;
        }

        public void setFriendsCount(Integer friendsCount) {
            this.friendsCount = friendsCount;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Object getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public Boolean getNotificationsStatus() {
            return notificationsStatus;
        }

        public void setNotificationsStatus(Boolean notificationsStatus) {
            this.notificationsStatus = notificationsStatus;
        }

        public Boolean getMessagesStatus() {
            return messagesStatus;
        }

        public void setMessagesStatus(Boolean messagesStatus) {
            this.messagesStatus = messagesStatus;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

    }
}
