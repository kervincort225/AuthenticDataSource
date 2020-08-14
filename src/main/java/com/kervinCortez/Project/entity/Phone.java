package com.kervinCortez.Project.entity;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "PHONE")
public class Phone {


    //field used for table of database
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY,generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( name = "ID_PHONES")
    private String id;

    @Column(name = "NUMBER_PH")
    private Integer number_ph;

    @Column(name = "CITYCODE_PH")
    private Integer citycode_ph;

    @Column(name = "CONTRYCODE_PH")
    private Integer contrycode_ph;


    @Column( name = "ID_USER")
    private String id_user;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name="rut_U")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Usuario usuario;


    //SET AND GET


    public Phone() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumber_ph() {
        return number_ph;
    }

    public void setNumber_ph(Integer number_ph) {
        this.number_ph = number_ph;
    }

    public Integer getCitycode_ph() {
        return citycode_ph;
    }

    public void setCitycode_ph(Integer citycode_ph) {
        this.citycode_ph = citycode_ph;
    }

    public Integer getContrycode_ph() {
        return contrycode_ph;
    }

    public void setContrycode_ph(Integer contrycode_ph) {
        this.contrycode_ph = contrycode_ph;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}


