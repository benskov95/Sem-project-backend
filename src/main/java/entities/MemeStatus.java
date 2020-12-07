/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Tha-Y
 */
@Entity
@NamedQuery (name = "MemeStatus.deleteAllRows", query = "DELETE FROM MemeStatus ")
@Table(name = "meme_status")
public class MemeStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "meme_status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status_name")
    private String statusName;

    @OneToMany(mappedBy = "memeStatus")
    private List<Meme> memeList;




    public MemeStatus() {
    }


    public MemeStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<Meme> getMemeList() {
        return memeList;
    }

    public void setMemeList(List<Meme> memeList) {
        this.memeList = memeList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemeStatus)) {
            return false;
        }
        MemeStatus other = (MemeStatus) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.MemeStatus[ id=" + id + " ]";
    }
    
}
