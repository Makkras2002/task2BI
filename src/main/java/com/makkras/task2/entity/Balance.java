package com.makkras.task2.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


@Entity
public class Balance {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal active;
    private BigDecimal passive;

    public Balance() {
    }

    public Balance(BigDecimal active, BigDecimal passive) {
        this.active = active;
        this.passive = passive;
    }

    public Balance(Long id, BigDecimal active, BigDecimal passive) {
        this.id = id;
        this.active = active;
        this.passive = passive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getActive() {
        return active;
    }

    public void setActive(BigDecimal active) {
        this.active = active;
    }

    public BigDecimal getPassive() {
        return passive;
    }

    public void setPassive(BigDecimal passive) {
        this.passive = passive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        if (id != null ? !id.equals(balance.id) : balance.id != null) return false;
        if (active != null ? !active.equals(balance.active) : balance.active != null) return false;
        return passive != null ? passive.equals(balance.passive) : balance.passive == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (passive != null ? passive.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Balance{");
        sb.append("id=").append(id);
        sb.append(", active=").append(active);
        sb.append(", passive=").append(passive);
        sb.append('}');
        return sb.toString();
    }
}
