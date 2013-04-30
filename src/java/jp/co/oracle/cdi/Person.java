package jp.co.oracle.cdi;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Size;
import jp.co.oracle.dao.PersonEntity;


@Named(value = "person")
@RequestScoped

public class Person implements Serializable {

    @Size(max = 255)
    private String address;
    @Size(max = 255)
    private String birthday;
    @Size(max = 255)
    private String birthmonth;
    @Size(max = 255)
    private String birthyear;
    @Size(max = 255)
    private String city;
    @Size(max = 255)
    private String emailaddress;
    @Size(max = 255)
    private String katakananame;
    @Size(max = 255)
    private String name;
    @Size(max = 255)
    private String password;
    @Size(max = 255)
    private String password2;
    @Size(max = 255)
    private String phonenumber;
    @Size(max = 255)
    private String prefecture;
    @Size(max = 255)
    private String sex;
    
    private PersonEntity entityObject;
    
    public Person() {
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthmonth() {
        return birthmonth;
    }

    public void setBirthmonth(String birthmonth) {
        this.birthmonth = birthmonth;
    }

    public String getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(String birthyear) {
        this.birthyear = birthyear;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getKatakananame() {
        return katakananame;
    }

    public void setKatakananame(String katakananame) {
        this.katakananame = katakananame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "jp.co.oracle.dao.Person[ "
                + "name=" + name + "\t" 
                + "katakananame=" + katakananame + "\t" 
                + "emailaddress=" + emailaddress + "\t" 
                + "phonenumber=" + phonenumber + "\t" 
                + "prefecture=" + prefecture + "\t" 
                + "city=" + city + "\t" 
                + "address=" + address + "\t" 
                + "sex=" + sex + "\t" 
                + "password=" + password + "\t" 
                + "password2=" + password2 + "\t" 
                + " ]";
    }
    
    public PersonEntity getEntityObject() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(getName());
        personEntity.setKatakananame(getKatakananame());
        personEntity.setBirthyear(getBirthyear());
        personEntity.setBirthmonth(getBirthmonth());
        personEntity.setBirthday(getBirthday());
        personEntity.setPrefecture(getPrefecture());
        personEntity.setCity(getCity());
        personEntity.setAddress(getAddress());
        personEntity.setEmailaddress(getEmailaddress());
        personEntity.setSex(getSex());
        personEntity.setPhonenumber(getPhonenumber());
        personEntity.setPassword(getPassword());
        personEntity.setPassword2(getPassword2());
        return personEntity;
    }
}
