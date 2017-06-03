package com.cosmos.app.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Scheme entity.
 */
public class SchemeDTO implements Serializable {

    private Long id;

    private String name;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Integer targetVolume;

    private Long driverId;

    private String driverCardNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getTargetVolume() {
        return targetVolume;
    }

    public void setTargetVolume(Integer targetVolume) {
        this.targetVolume = targetVolume;
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

        SchemeDTO schemeDTO = (SchemeDTO) o;
        if(schemeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schemeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchemeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", targetVolume='" + getTargetVolume() + "'" +
            "}";
    }
}
