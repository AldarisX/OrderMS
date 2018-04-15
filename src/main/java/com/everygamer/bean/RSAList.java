package com.everygamer.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Entity
@Table(name = "rsa_list")
public class RSAList {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private PublicKey pubKey;
    @Getter
    @Setter
    private PrivateKey priKey;

    public String getPubKeyString() {
        return new String(Base64.getEncoder().encode(pubKey.getEncoded()));
    }

    public String getPriKeyString() {
        return new String(Base64.getEncoder().encode(priKey.getEncoded()));
    }
}
