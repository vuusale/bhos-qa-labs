package com.example.springproj11;

import org.apache.logging.log4j.internal.DefaultLogBuilder;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "address_table")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    public Address(String name, String addressLine, Double lat, Double lon) {
        this.name = name;
        this.addressLine = addressLine;
        this.lat = lat;
        this.lon = lon;
    }

    public void setAddressLine(String newAddress) {
        this.addressLine = newAddress;
    }

    public UUID getId() {
        return id;
    }

    public Address() {}

    @Override
    public String toString() {
        return String.format("Address<id=%s, name=%s, addressLine=%s, lat=%f, lon=%f>", id, name, addressLine, lat, lon);
    }
}
