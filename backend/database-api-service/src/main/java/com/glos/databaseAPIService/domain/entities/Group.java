package com.glos.databaseAPIService.domain.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(
        name = "`groups`",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "owner_id"}, name = "uq_groups_name_owner_id")}
)
public class Group
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",  nullable = false)
    private Long id;

    @Column(name = "`name`", length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false, foreignKey = @ForeignKey(name = "fk_groups_users_id"))
    private User owner;

    @ManyToMany
    @JoinTable(name = "groups_users", joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            foreignKey = @ForeignKey(name = "fk_groups_users_groups_id"),
            inverseForeignKey = @ForeignKey(name = "fk_groups_users_users_id"))
    private Set<User> users;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Group()
    {
        this.users = new HashSet<>();
    }

    public Group(Long id,
                 String name,
                 User owner,
                 Set<User> users) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id) && Objects.equals(name, group.name) && Objects.equals(owner, group.owner) && Objects.equals(users, group.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, users);
    }

    @Override
    public String toString() {
        return name;
    }
}