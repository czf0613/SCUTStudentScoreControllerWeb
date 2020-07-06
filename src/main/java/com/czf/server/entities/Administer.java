package com.czf.server.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "admin",schema = "dealate")
public class Administer {
    private Integer id;
    private int commandLevel;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "command_level")
    public int getCommandLevel() {
        return commandLevel;
    }

    public void setCommandLevel(int commandLevel) {
        this.commandLevel = commandLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administer that = (Administer) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
