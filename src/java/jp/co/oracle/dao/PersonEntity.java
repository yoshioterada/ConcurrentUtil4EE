package jp.co.oracle.dao;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PERSON")
@NamedQueries({
    @NamedQuery(name = "PersonEntity.findAll", query = "SELECT p FROM PersonEntity p"),
    @NamedQuery(name = "PersonEntity.findById", query = "SELECT p FROM PersonEntity p WHERE p.id = :id"),
    @NamedQuery(name = "PersonEntity.findByAddress", query = "SELECT p FROM PersonEntity p WHERE p.address = :address"),
    @NamedQuery(name = "PersonEntity.findByBirthday", query = "SELECT p FROM PersonEntity p WHERE p.birthday = :birthday"),
    @NamedQuery(name = "PersonEntity.findByBirthmonth", query = "SELECT p FROM PersonEntity p WHERE p.birthmonth = :birthmonth"),
    @NamedQuery(name = "PersonEntity.findByBirthyear", query = "SELECT p FROM PersonEntity p WHERE p.birthyear = :birthyear"),
    @NamedQuery(name = "PersonEntity.findByCity", query = "SELECT p FROM PersonEntity p WHERE p.city = :city"),
    @NamedQuery(name = "PersonEntity.findByEmailaddress", query = "SELECT p FROM PersonEntity p WHERE p.emailaddress = :emailaddress"),
    @NamedQuery(name = "PersonEntity.findByKatakananame", query = "SELECT p FROM PersonEntity p WHERE p.katakananame = :katakananame"),
    @NamedQuery(name = "PersonEntity.findByName", query = "SELECT p FROM PersonEntity p WHERE p.name = :name"),
    @NamedQuery(name = "PersonEntity.findByPassword", query = "SELECT p FROM PersonEntity p WHERE p.password = :password"),
    @NamedQuery(name = "PersonEntity.findByPassword2", query = "SELECT p FROM PersonEntity p WHERE p.password2 = :password2"),
    @NamedQuery(name = "PersonEntity.findByPhonenumber", query = "SELECT p FROM PersonEntity p WHERE p.phonenumber = :phonenumber"),
    @NamedQuery(name = "PersonEntity.findByPrefecture", query = "SELECT p FROM PersonEntity p WHERE p.prefecture = :prefecture"),
    @NamedQuery(name = "PersonEntity.findBySex", query = "SELECT p FROM PersonEntity p WHERE p.sex = :sex")})
public class PersonEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private String id;
    @Size(max = 255)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 255)
    @Column(name = "BIRTHDAY")
    private String birthday;
    @Size(max = 255)
    @Column(name = "BIRTHMONTH")
    private String birthmonth;
    @Size(max = 255)
    @Column(name = "BIRTHYEAR")
    private String birthyear;
    @Size(max = 255)
    @Column(name = "CITY")
    private String city;
    @Size(max = 255)
    @Column(name = "EMAILADDRESS")
    private String emailaddress;
    @Size(max = 255)
    @Column(name = "KATAKANANAME")
    private String katakananame;
    @Size(max = 255)
    @Column(name = "NAME")
    private String name;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 255)
    @Column(name = "PASSWORD2")
    private String password2;
    @Size(max = 255)
    @Column(name = "PHONENUMBER")
    private String phonenumber;
    @Size(max = 255)
    @Column(name = "PREFECTURE")
    private String prefecture;
    @Size(max = 255)
    @Column(name = "SEX")
    private String sex;

    public PersonEntity() {
    }

    public PersonEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof jp.co.oracle.dao.PersonEntity)){
            return false;
        }
        jp.co.oracle.dao.PersonEntity other = (jp.co.oracle.dao.PersonEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.oracle.cdi.PersonEntity[ "
                + "id=" + id + "\t" 
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
    
}
