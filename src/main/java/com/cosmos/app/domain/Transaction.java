package com.cosmos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_time", nullable = false)
    private ZonedDateTime time;

    @NotNull
    @Column(name = "fuel_volume", nullable = false)
    private Integer fuelVolume;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne(optional = false)
    @NotNull
    private Driver driver;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Transaction time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Integer getFuelVolume() {
        return fuelVolume;
    }

    public Transaction fuelVolume(Integer fuelVolume) {
        this.fuelVolume = fuelVolume;
        return this;
    }

    public void setFuelVolume(Integer fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    public Integer getAmount() {
        return amount;
    }

    public Transaction amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Driver getDriver() {
        return driver;
    }

    public Transaction driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        if (transaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", fuelVolume='" + getFuelVolume() + "'" +
            ", amount='" + getAmount() + "'" +
            "}";
    }
}
