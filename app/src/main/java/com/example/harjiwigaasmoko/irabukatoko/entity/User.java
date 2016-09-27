package com.example.harjiwigaasmoko.irabukatoko.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by harjiwigaasmoko on 8/5/16.
 */
public class User implements Parcelable,Cloneable{

    private Integer id;
    private String name;
    private String email;
    private String phoneNum;
    private String address;
    private String idType;
    private String idNumber;
    private boolean selected;

    public User() {
    }

    public User(String id){

    }

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        phoneNum = in.readString();
        address = in.readString();
        idType = in.readString();
        idNumber = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id)+". "+name+" "+email;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phoneNum);
        dest.writeString(address);
        dest.writeString(idType);
        dest.writeString(idNumber);
        dest.writeByte((byte) (selected ? 1 : 0));
    }
}
