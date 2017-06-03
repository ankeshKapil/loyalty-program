package com.cosmos.app.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Transaction entity.
 */
public class TransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime time;

    @NotNull
    private Integer fuelVolume;

    private Integer amount;

    private Long driverId;

    private String driverCardNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Integer getFuelVolume() {
        return fuelVolume;
    }

    public void setFuelVolume(Integer fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverCardNumber() {
        return driverCardNumber;
    }

    public void setDriverCardNumber(String driverCardNumber) {
        this.driverCardNumber = driverCardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if(transactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", fuelVolume='" + getFuelVolume() + "'" +
            ", amount='" + getAmount() + "'" +
            "}";
    }
}
