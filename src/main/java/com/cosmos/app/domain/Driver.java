package com.cosmos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "card_number", nullable = false)
    private Long cardNumber;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "phone_number", length = 10, nullable = false)
    private String phoneNumber;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime createdOn;

    @NotNull
    @Column(name = "updated_on", nullable = false)
    private ZonedDateTime updatedOn;

    @Column(name = "id_card_type")
    private String idCardType;

    @Column(name = "id_card_number")
    private String idCardNumber;

    @NotNull
    @Column(name = "vehicle_number", nullable = false)
    private String vehicleNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "common_routes")
    private String commonRoutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public Driver cardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public Driver name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Driver phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public Driver createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ZonedDateTime getUpdatedOn() {
        return updatedOn;
    }

    public Driver updatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public Driver idCardType(String idCardType) {
        this.idCardType = idCardType;
        return this;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public Driver idCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
        return this;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public Driver vehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
        return this;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getAddress() {
        return address;
    }

    public Driver address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCommonRoutes() {
        return commonRoutes;
    }

    public Driver commonRoutes(String commonRoutes) {
        this.commonRoutes = commonRoutes;
        return this;
    }

    public void setCommonRoutes(String commonRoutes) {
        this.commonRoutes = commonRoutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Driver driver = (Driver) o;
        if (driver.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driver.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            ", cardNumber='" + getCardNumber() + "'" +
            ", name='" + getName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", idCardType='" + getIdCardType() + "'" +
            ", idCardNumber='" + getIdCardNumber() + "'" +
            ", vehicleNumber='" + getVehicleNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", commonRoutes='" + getCommonRoutes() + "'" +
            "}";
    }
}
