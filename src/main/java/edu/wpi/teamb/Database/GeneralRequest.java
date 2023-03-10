package edu.wpi.teamb.Database;

import edu.wpi.teamb.Entities.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
// this will need to be changed to have inheritance work
@Table(name = "GeneralRequest")
@Inheritance(strategy = InheritanceType.JOINED)
public class GeneralRequest {
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Setter
  @Getter
  private int id;

  @Column(name = "firstname", length = 60)
  @Getter
  @Setter
  private String firstname;

  @Column(name = "lastname", length = 60)
  @Getter
  @Setter
  private String lastname;

  @Column(name = "email", length = 60)
  @Getter
  @Setter
  private String email;

  @Column(name = "employeeid", length = 60)
  @Getter
  @Setter
  private String employeeID;

  @Column(name = "urgency", length = 40)
  @Getter
  @Setter
  private String urgency;

  @Column(name = "assignedto", length = 60)
  @Getter
  @Setter
  private String assignedEmployee;

  @Column(name = "notes", length = 60)
  @Getter
  @Setter
  private String notes;

  @Column(name = "status", length = 60)
  @Getter
  @Setter
  private RequestStatus status;

  @Column(name = "date", length = 60)
  @Getter
  @Setter
  private String date;

  public void generalRequest() {}
}
