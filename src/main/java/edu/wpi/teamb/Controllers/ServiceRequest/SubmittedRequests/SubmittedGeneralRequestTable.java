package edu.wpi.teamb.Controllers.ServiceRequest.SubmittedRequests;

import edu.wpi.teamb.Controllers.Profile.SigninController;
import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Database.GeneralRequest;
import edu.wpi.teamb.Database.Login;
import edu.wpi.teamb.Entities.RequestStatus;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public abstract class SubmittedGeneralRequestTable {
  @FXML protected TableView table = new TableView<>();
  @FXML private TableColumn employeeID = new TableColumn();
  @FXML private TableColumn firstname = new TableColumn();
  @FXML private TableColumn lastname = new TableColumn();
  @FXML private TableColumn email = new TableColumn();
  @FXML private TableColumn urgency = new TableColumn();
  @FXML private TableColumn<GeneralRequest, String> assignedEmployee = new TableColumn<>();
  @FXML private TableColumn<GeneralRequest, RequestStatus> status = new TableColumn<>();
  @FXML private TableColumn<GeneralRequest, String> notes = new TableColumn();
  @FXML TableColumn date = new TableColumn();
  Login l;
  private List<TableColumn> cols = new ArrayList<>();
  private List<String> colNames = new ArrayList<>();
  private ObservableList<RequestStatus> Status =
      FXCollections.observableArrayList(
          RequestStatus.BLANK, RequestStatus.PROCESSING, RequestStatus.DONE);
  private ObservableList<String> staff = DBSession.getStaff();

  public void initialize() {
    l = SigninController.getCurrentUser();
    addcol(date, "date");
    addcol(firstname, "firstname");
    addcol(lastname, "lastname");
    addcol(employeeID, "employeeID");
    addcol(email, "email");
    addcol(urgency, "urgency");
    addcol(assignedEmployee, "assignedEmployee");
    addcol(status, "status");
    addcol(notes, "notes");
  }

  public TableView getTable() {
    return table;
  }

  public void editableCols() {
    notes.setCellFactory(TextFieldTableCell.forTableColumn());
    notes.setOnEditCommit(
        e -> {
          GeneralRequest r = e.getTableView().getItems().get(e.getTablePosition().getRow());
          r.setNotes(e.getNewValue());
          DBSession.updateRequest(r);
        });

    if (DBSession.isAdmin(l)) {
      //      edit assignEmployee
      assignedEmployee.setCellFactory(new ComboBoxTableCell().forTableColumn(staff));
      assignedEmployee.setOnEditCommit(
          e -> {
            GeneralRequest r = e.getTableView().getItems().get(e.getTablePosition().getRow());
            r.setAssignedEmployee(e.getNewValue());
            DBSession.updateRequest(r);
          });

      //       edit staff
      status.setCellFactory(new ComboBoxTableCell().forTableColumn(Status));
      status.setOnEditCommit(
          e -> {
            GeneralRequest r = e.getTableView().getItems().get(e.getTablePosition().getRow());
            r.setStatus(e.getNewValue());
            DBSession.updateRequest(r);
          });
    }
    table.setEditable(true);
  }

  private void addcol(TableColumn col, String colName) {
    this.cols.add(col);
    this.colNames.add(colName);
  }

  protected void addCol(TableColumn col, String colName) {
    int insert = 3;
    this.cols.add(cols.size() - insert, col);
    this.colNames.add(colNames.size() - insert, colName);
  }

  protected void setTable() {
    for (int i = 0; i < cols.size(); i++) {
      cols.get(i).setCellValueFactory(new PropertyValueFactory<>(colNames.get(i)));
      table.getColumns().add(cols.get(i));
      cols.get(i).setText(colNames.get(i));
    }
    editableCols();
  }
}
