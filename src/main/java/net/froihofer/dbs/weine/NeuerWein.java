package net.froihofer.dbs.weine;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.froihofer.dbs.weine.entities.Erzeuger;
import net.froihofer.dbs.weine.entities.Wein;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lorenz Froihofer
 * @version $Id: NeuerWein.java 1:3d18a343f773 2012/12/02 01:44:16 Lorenz Froihofer $
 */
public class NeuerWein extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(NeuerWein.class);
  
  public static final String ERROR_MSG_PARAM = "WEIN_SPEICHERUNG_FEHLERMELDUNG";
  public static final String SUCCESS_MSG_PARAM = "WEIN_SPEICHERUNG_ERFOLGSMELDUNG";
  public static final String WEINE = "WEINE_VON_ERZEUGER";

  private EntityManagerFactory emf;
  
  @Override
  public void init() throws ServletException {
    super.init();
    emf = Persistence.createEntityManagerFactory("dbs-weine");
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String name = req.getParameter("name");
    PrintWriter pw = resp.getWriter();
    if (StringUtils.isBlank(name)) {
      req.setAttribute(ERROR_MSG_PARAM,"Name darf nicht leer sein.");
      req.getRequestDispatcher("/neuer-wein.jsp").forward(req, resp);
      return;
    }

    Wein wein = new Wein(name, req.getParameter("farbe"), 
            req.getParameter("jahrgang") != null ? Integer.parseInt(req.getParameter("jahrgang")) : null,
            req.getParameter("weingut"));

    try {
      if (req.getParameter("useJpa") != null && "on".equalsIgnoreCase(req.getParameter("useJpa"))) {
        createWeinUsingJpa(wein);
      }
      else {
        createWeinUsingJDBC(wein);
      }
      req.setAttribute(SUCCESS_MSG_PARAM, "Wein \""+wein.getName()+"\" wurde erfolgreich gespeichert.");      
    }
    catch (Exception e) {
      log.error("Fehler beim Speichern des Weines.",e);
      req.setAttribute(ERROR_MSG_PARAM, e.getMessage());
    }
    req.setAttribute(WEINE, getWeineFromWeingut(wein.getErzeuger().getWeingut()));
    req.getRequestDispatcher("/neuer-wein.jsp").forward(req, resp);
  }
  
  private void createWeinUsingJpa(Wein wein) {
    try {
      EntityManager em = emf.createEntityManager();
      EntityTransaction tx = em.getTransaction();
      try {
        tx.begin();
        Erzeuger erzeuger = em.find(Erzeuger.class, wein.getErzeuger().getWeingut());
        if (erzeuger == null) {
          throw new PersistenceException("Erzeuger/Weingut existiert nicht.");
        }
        wein.setErzeuger(erzeuger);
        em.persist(wein);
        tx.commit();
      }
      catch (Exception e) {
        tx.rollback();
        throw e;
      }
      finally {
        em.close();
      }
    }
    catch (Exception e) {
      throw new PersistenceException("Fehler beim Speichern des Weines: "+e.getMessage(), e);
    }
  }
  
  private void createWeinUsingJDBC(Wein wein) throws PersistenceException {
    Connection con = null;
    try {
    InitialContext ctx = new InitialContext();
    DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/WeineDB");

    con = ds.getConnection();
    String sqlStr = "INSERT INTO Weine (Name, Farbe, Jahrgang, Weingut) VALUES (?, ?, ?, ?)";
      PreparedStatement ps = con.prepareStatement(sqlStr);
      ps.setString(1, wein.getName());
      ps.setString(2, wein.getFarbe());
      if (wein.getJahrgang() != null) {
        ps.setInt(3, wein.getJahrgang());
      }
      else {
        ps.setNull(3, Types.INTEGER);
      }
      ps.setString(4, wein.getErzeuger().getWeingut());
      int count = ps.executeUpdate();
      if (count != 1) {
        throw new PersistenceException("Unbekannter Fehler beim Speichern des neuen Weines (updateCount = 0).");
      }
    }
    catch (Exception e) {
      throw new PersistenceException("Fehler beim Anlegen des Weines: " + e.getMessage(), e);
    }
    finally {
      try {
        con.close();
      }
      catch (Exception e) {
        log.debug("Fehler beim Schließen der Connection.", e);
      }
    }
  }
  
  private List<Wein> getWeineFromWeingut(String weingut) {
    List<Wein> result = null;
    try {
      EntityManager em = emf.createEntityManager();
      EntityTransaction tx = em.getTransaction();
      try {
        tx.begin();
        result = em.createQuery("from Wein w where w.erzeuger.weingut = :weingut", Wein.class)
              .setParameter("weingut", weingut).getResultList();
        tx.commit();
        return result;
      }
      catch (Exception e) {
        tx.rollback();
        throw e;
      }
      finally {
        em.close();
      }
    }
    catch (Exception e) {
      log.error("Konnte die Weine des Weinguts \""+weingut+"\" nicht auslesen: "+e.getMessage(), e);
      return null;
    }
  }
}
