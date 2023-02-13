package edu.wpi.teamb.Database;

import edu.wpi.teamb.Database.DAO.LoginDAO;
import edu.wpi.teamb.Database.DAO.MapDAO;
import edu.wpi.teamb.Database.DAO.RequestDAO;
import edu.wpi.teamb.Entities.SessionGetter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DBSession {

  private static DBSession instance = null;

  private DBSession() {};

  public static void addNode(Node n) {
    MapDAO.addNode(n);
  }

  public static void deleteNode(Node n) {
    MapDAO.deleteNode(n);
  }

  public static void updateNode(Node n) {
    MapDAO.updateNode(n);
  }

  public static void addLogin(Login l) {
    LoginDAO.addLogin(l);
  }

  public static void deleteLogin(Login l) {
    LoginDAO.deleteLogin(l);
  }

  public static void addEdge(Edge e) {
    MapDAO.addEdge(e);
  }

  public static void deleteEdge(Node n1, Node n2) {
    MapDAO.deleteEdge(n1, n2);
  }

  public static void addLocationName(LocationName ln) {
    MapDAO.addLocationName(ln);
  }

  public static void deleteLocationName(LocationName ln) {
    MapDAO.deleteLocationName(ln);
  }

  public static void updateLocationName(LocationName newLN, LocationName oldLN) {
    MapDAO.updateLocationName(newLN, oldLN);
  }

  public static void addMove(Move m) {
    MapDAO.addMove(m);
  }

  public static void deleteMove(Move m) {
    MapDAO.deleteMove(m);
  }

  public static void addRequest(GeneralRequest r) {
    RequestDAO.addRequest(r);
  }

  public static Map<String, Login> getAllLogins() {
    return LoginDAO.getAllLogins();
  }

  public static Map<String, Node> getAllNodes() {
    return MapDAO.getAllNodes();
  }

  public static List<Edge> getAllEdges() {
    return MapDAO.getAllEdges();
  }

  public static Map<String, LocationName> getAllLocationNames() {
    return MapDAO.getAllLocationNames();
  }

  public static List<GeneralRequest> getAllRequests() {
    return RequestDAO.getALLRequests();
  }

  public static List<PatientTransportationRequest> getAllPTRequests() {
    return RequestDAO.getALLPTRequests();
  }

  public static List<SanitationRequest> getAllSRequests() {
    return RequestDAO.getALLSRequests();
  }

  public static List<ComputerRequest> getAllCRequests() {
    return RequestDAO.getALLCRequests();
  }

  public static Map<String, ArrayList<Move>> getIDMoves(Date d) {
    return MapDAO.getIDMoves(d);
  }

  public static Map<String, Move> getLNMoves(Date d) {
    return MapDAO.getLNMoves(d);
  }

  public static void updateUser(String user, String first, String last, String email) {
    LoginDAO.updateUser(user, first, last, email);
  }

  public static void updateAdmin(String user, Boolean b) {
    LoginDAO.updateAdmin(user, b);
  }

  public static List<Move> getMostRecentMoves(String NodeID) {
    try {
      return MapDAO.getIDMoves(new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01")).get(NodeID);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public static String getMostRecentNodeID(String longName) {
    try {
      return MapDAO.getLNMoves(new SimpleDateFormat("yyyy-mm-dd").parse("2023-01-01"))
          .get(longName)
          .getNode()
          .getNodeID();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public static Node getMostRecentNode(String longName) {
    String id = getMostRecentNodeID(longName);
    return getAllNodes().get(id);
  }

  // this stuff should be in a DAO if its gonna be reused
  public static Move getMostRecentMove(String nodeID) {
    SessionFactory sf = SessionGetter.CONNECTION.getSessionFactory();
    Session session = sf.openSession();
    List<Move> moves;
    try {
      Transaction tx = session.beginTransaction();
      String str = "FROM Move WHERE nodeID = '" + nodeID + "'";
      Query q = session.createQuery(str);
      moves = q.list();
      session.close();
      if (moves.isEmpty()) {
        return null;
      } else {
        Move mostRecent = moves.get(0);
        for (Move move : moves) if (moreRecentThan(move, mostRecent)) mostRecent = move;
        return mostRecent;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      session.close();
    }
  }

  public static boolean moreRecentThan(Move move1, Move move2) {
    return move1.getMoveDate().after(move2.getMoveDate());
  }

  public static void switchMoveLN(String newN, String oldN, LocationName ln) {

    //    Move oldM = getMostRecentMove(oldN);
    //    delete(oldM);
    //
    //    Move m = getMostRecentMove(newN);
    //    Move mnew = new Move();
    //    mnew.setNodeID(newN);
    //    mnew.setLongName(ln.getLongName());
    //    if (m != null) {
    //      mnew.setMoveDate(m.getMoveDate());
    //      delete(m);
    //    } else {
    //      mnew.setMoveDate(Date.valueOf(LocalDate.now()));
    //    }
    //    addORM(mnew);
  }
}
