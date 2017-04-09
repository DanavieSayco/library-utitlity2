/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryutility;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "students", catalog = "library_schema", schema = "")
@NamedQueries({
    @NamedQuery(name = "Students.findAll", query = "SELECT s FROM Students s")
    , @NamedQuery(name = "Students.findByStudId", query = "SELECT s FROM Students s WHERE s.studId = :studId")
    , @NamedQuery(name = "Students.findByLastName", query = "SELECT s FROM Students s WHERE s.lastName = :lastName")
    , @NamedQuery(name = "Students.findByFirstName", query = "SELECT s FROM Students s WHERE s.firstName = :firstName")
    , @NamedQuery(name = "Students.findByGender", query = "SELECT s FROM Students s WHERE s.gender = :gender")
    , @NamedQuery(name = "Students.findByYear", query = "SELECT s FROM Students s WHERE s.year = :year")
    , @NamedQuery(name = "Students.findByPassword", query = "SELECT s FROM Students s WHERE s.password = :password")
    , @NamedQuery(name = "Students.findByHrsRendered", query = "SELECT s FROM Students s WHERE s.hrsRendered = :hrsRendered")
    , @NamedQuery(name = "Students.findByStatus", query = "SELECT s FROM Students s WHERE s.status = :status")
    , @NamedQuery(name = "Students.findByEnrolled", query = "SELECT s FROM Students s WHERE s.enrolled = :enrolled")})
public class Students implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "stud_id")
    private String studId;
    @Basic(optional = false)
    @Column(name = "LastName")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "FirstName")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "gender")
    private String gender;
    @Basic(optional = false)
    @Column(name = "year")
    private String year;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "hrsRendered")
    @Temporal(TemporalType.TIME)
    private Date hrsRendered;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "enrolled")
    private String enrolled;

    public Students() {
    }

    public Students(String studId) {
        this.studId = studId;
    }

    public Students(String studId, String lastName, String firstName, String gender, String year, String password, Date hrsRendered, String status, String enrolled) {
        this.studId = studId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.year = year;
        this.password = password;
        this.hrsRendered = hrsRendered;
        this.status = status;
        this.enrolled = enrolled;
    }

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        String oldStudId = this.studId;
        this.studId = studId;
        changeSupport.firePropertyChange("studId", oldStudId, studId);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        String oldLastName = this.lastName;
        this.lastName = lastName;
        changeSupport.firePropertyChange("lastName", oldLastName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        String oldFirstName = this.firstName;
        this.firstName = firstName;
        changeSupport.firePropertyChange("firstName", oldFirstName, firstName);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        String oldGender = this.gender;
        this.gender = gender;
        changeSupport.firePropertyChange("gender", oldGender, gender);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        String oldYear = this.year;
        this.year = year;
        changeSupport.firePropertyChange("year", oldYear, year);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String oldPassword = this.password;
        this.password = password;
        changeSupport.firePropertyChange("password", oldPassword, password);
    }

    public Date getHrsRendered() {
        return hrsRendered;
    }

    public void setHrsRendered(Date hrsRendered) {
        Date oldHrsRendered = this.hrsRendered;
        this.hrsRendered = hrsRendered;
        changeSupport.firePropertyChange("hrsRendered", oldHrsRendered, hrsRendered);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        String oldStatus = this.status;
        this.status = status;
        changeSupport.firePropertyChange("status", oldStatus, status);
    }

    public String getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(String enrolled) {
        String oldEnrolled = this.enrolled;
        this.enrolled = enrolled;
        changeSupport.firePropertyChange("enrolled", oldEnrolled, enrolled);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studId != null ? studId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Students)) {
            return false;
        }
        Students other = (Students) object;
        if ((this.studId == null && other.studId != null) || (this.studId != null && !this.studId.equals(other.studId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "libraryutility.Students[ studId=" + studId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
