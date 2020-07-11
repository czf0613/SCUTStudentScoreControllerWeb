package com.czf.server.entities;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "course",schema = "dealate")
@TypeDef(name="json",typeClass = JsonStringType.class)
public class Course {
    private Integer id;
    private String courseName;
    private List<Integer> teachers;
    private BigDecimal credit;
    private List<String> grade;
    private Date startDate;
    private Date endDate;
    private List<Integer> students;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "course_name", nullable = false, length = 50)
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Basic
    @Type(type = "json")
    @Column(name = "teachers", nullable = true,columnDefinition = "json")
    public List<Integer> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Integer> teachers) {
        if(teachers==null)
            teachers=new ArrayList<>();
        this.teachers = teachers;
    }

    @Basic
    @Column(name = "credit", nullable = false, precision = 2)
    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    @Basic
    @Type(type = "json")
    @Column(name = "grade", nullable = false,columnDefinition = "json")
    public List<String> getGrade() {
        return grade;
    }

    public void setGrade(List<String> grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Type(type = "json")
    @Column(name = "students", nullable = true,columnDefinition = "json")
    public List<Integer> getStudents() {
        if(students==null)
            students=new ArrayList<>();
        return students;
    }

    public void setStudents(List<Integer> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
