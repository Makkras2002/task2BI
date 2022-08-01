package com.makkras.task2.entity;


import javax.persistence.*;

@Entity
public class Data {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bch;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Balance incomingBalance;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Turnover currentTurnover;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Balance outcomingBalance;

    public Data() {
    }

    public Data(Long bch, Balance incomingBalance, Turnover currentTurnover, Balance outcomingBalance) {
        this.bch = bch;
        this.incomingBalance = incomingBalance;
        this.currentTurnover = currentTurnover;
        this.outcomingBalance = outcomingBalance;
    }

    public Data(Long id, Long bch, Balance incomingBalance, Turnover currentTurnover, Balance outcomingBalance) {
        this.id = id;
        this.bch = bch;
        this.incomingBalance = incomingBalance;
        this.currentTurnover = currentTurnover;
        this.outcomingBalance = outcomingBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBch() {
        return bch;
    }

    public void setBch(Long bch) {
        this.bch = bch;
    }

    public Balance getIncomingBalance() {
        return incomingBalance;
    }

    public void setIncomingBalance(Balance incomingBalance) {
        this.incomingBalance = incomingBalance;
    }

    public Turnover getCurrentTurnover() {
        return currentTurnover;
    }

    public void setCurrentTurnover(Turnover currentTurnover) {
        this.currentTurnover = currentTurnover;
    }

    public Balance getOutcomingBalance() {
        return outcomingBalance;
    }

    public void setOutcomingBalance(Balance outcomingBalance) {
        this.outcomingBalance = outcomingBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (id != null ? !id.equals(data.id) : data.id != null) return false;
        if (bch != null ? !bch.equals(data.bch) : data.bch != null) return false;
        if (incomingBalance != null ? !incomingBalance.equals(data.incomingBalance) : data.incomingBalance != null)
            return false;
        if (currentTurnover != null ? !currentTurnover.equals(data.currentTurnover) : data.currentTurnover != null)
            return false;
        return outcomingBalance != null ? outcomingBalance.equals(data.outcomingBalance) : data.outcomingBalance == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (bch != null ? bch.hashCode() : 0);
        result = 31 * result + (incomingBalance != null ? incomingBalance.hashCode() : 0);
        result = 31 * result + (currentTurnover != null ? currentTurnover.hashCode() : 0);
        result = 31 * result + (outcomingBalance != null ? outcomingBalance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Data{");
        sb.append("id=").append(id);
        sb.append(", bch=").append(bch);
        sb.append(", incomingBalance=").append(incomingBalance);
        sb.append(", currentTurnover=").append(currentTurnover);
        sb.append(", outcomingBalance=").append(outcomingBalance);
        sb.append('}');
        return sb.toString();
    }
}
