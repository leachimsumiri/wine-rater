package net.froihofer.dbs.weine.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lorenz Froihofer
 * @version $Id: Erzeuger.java 1:3d18a343f773 2012/12/02 01:44:16 Lorenz Froihofer $
 */
@Entity
public class Erzeuger implements Serializable {
  private static final Logger log = LoggerFactory.getLogger(Erzeuger.class);
  
  @Id
  private String weingut;
  private String anbaugebiet;
  private String region;

  @OneToMany(mappedBy="erzeuger")
  private List<Wein> weine;
  
  /**
   * @return the weingut
   */
  public String getWeingut() {
    return weingut;
  }

  /**
   * @param weingut the weingut to set
   */
  public void setWeingut(String weingut) {
    this.weingut = weingut;
  }

  /**
   * @return the anbaugebiet
   */
  public String getAnbaugebiet() {
    return anbaugebiet;
  }

  /**
   * @param anbaugebiet the anbaugebiet to set
   */
  public void setAnbaugebiet(String anbaugebiet) {
    this.anbaugebiet = anbaugebiet;
  }

  /**
   * @return the region
   */
  public String getRegion() {
    return region;
  }

  /**
   * @param region the region to set
   */
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * @return the weine
   */
  public List<Wein> getWeine() {
    return weine;
  }

  /**
   * @param weine the weine to set
   */
  public void setWeine(List<Wein> weine) {
    this.weine = weine;
  }
}
