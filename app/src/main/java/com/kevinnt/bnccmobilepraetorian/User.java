package com.kevinnt.bnccmobilepraetorian;

public class User {
    private String uid;
    private String nama;
    private String email;

    public User() {
    }

    public User(String uid, String nama, String email) {
        this.uid = uid;
        this.nama = nama;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
