package net.froihofer.dbs.weine.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lorenz Froihofer
 * @version $Id: Wein.java 1:3d18a343f773 2012/12/02 01:44:16 Lorenz Froihofer $
 */
@Entity
@Table(name="Weine")
public class Wein implements Serializable {
  private static final Logger log = LoggerFactory.getLogger(Wein.class);

  @Id
  @GeneratedValue
  private Integer weinId;
  private String name;
  private Integer jahrgang;
  @Column(name="farbe", columnDefinition="ENUM('Rot', 'Weiß', 'Rose')")
  private String farbe;
  private boolean empfohlen;

  public boolean isEmpfohlen() {
    return empfohlen;
  }

  public void setEmpfohlen(boolean empfohlen) {
    this.empfohlen = empfohlen;
  }
  
  @ManyToOne
  @JoinColumn(name="weingut")
  private Erzeuger erzeuger;

  //No arguments constructor required for entity class
  public Wein(String name, String farbe, Integer integer, String weingut){}
  
  public Wein(String name, String farbe, Integer jahrgang, String weingut, Boolean empfohlen) {
    this.name = name;
    this.jahrgang = jahrgang;
    this.farbe = farbe;
    this.erzeuger = new Erzeuger();
    this.erzeuger.setWeingut(weingut);
    this.empfohlen = empfohlen;
  }
  
  /**
   * @return the weinId
   */
  public Integer getWeinId() {
    return weinId;
  }

  /**
   * @param weinId the weinId to set
   */
  public void setWeinId(Integer weinId) {
    this.weinId = weinId;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the jahrgang
   */
  public Integer getJahrgang() {
    return jahrgang;
  }

  /**
   * @param jahrgang the jahrgang to set
   */
  public void setJahrgang(Integer jahrgang) {
    this.jahrgang = jahrgang;
  }

  /**
   * @return the farbe
   */
  public String getFarbe() {
    return farbe;
  }

  /**
   * @param farbe the farbe to set
   */
  public void setFarbe(String farbe) {
    this.farbe = farbe;
  }

  /**
   * @return the erzeuger
   */
  public Erzeuger getErzeuger() {
    return erzeuger;
  }

  /**
   * @param erzeuger the erzeuger to set
   */
  public void setErzeuger(Erzeuger erzeuger) {
    this.erzeuger = erzeuger;
  }
}
