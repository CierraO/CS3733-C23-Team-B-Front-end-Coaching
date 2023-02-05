package edu.wpi.teamb.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PatientTransportationRequest")
public class PatientTransportationRequest extends GeneralRequest {
  @Id private Long id;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "equipmentNeeded", length = 20)
  @Getter
  @Setter
  private String equipmentNeeded;

  @Column(name = "patientLocation", length = 20)
  @Getter
  @Setter
  private String patientLocation;

  @Column(name = "patientDestination", length = 20)
  @Getter
  @Setter
  private String patientDestination;

  @Column(name = "patientid", length = 20)
  @Getter
  @Setter
  private String patientID;

  private static PatientTransportationRequest instance = null;

  public static PatientTransportationRequest getInstance() {
    if (instance == null) {
      instance = new PatientTransportationRequest();
    }
    return instance;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}
