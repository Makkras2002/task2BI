package com.makkras.task2.entity;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
public class Turnover {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal debit;
    private BigDecimal credit;

    public Turnover() {
    }

    public Turnover(BigDecimal debit, BigDecimal credit) {
        this.debit = debit;
        this.credit = credit;
    }

    public Turnover(Long id, BigDecimal debit, BigDecimal credit) {
        this.id = id;
        this.debit = debit;
        this.credit = credit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Turnover turnover = (Turnover) o;

        if (id != null ? !id.equals(turnover.id) : turnover.id != null) return false;
        if (debit != null ? !debit.equals(turnover.debit) : turnover.debit != null) return false;
        return credit != null ? credit.equals(turnover.credit) : turnover.credit == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (debit != null ? debit.hashCode() : 0);
        result = 31 * result + (credit != null ? credit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Turnover{");
        sb.append("id=").append(id);
        sb.append(", debit=").append(debit);
        sb.append(", credit=").append(credit);
        sb.append('}');
        return sb.toString();
    }
}
