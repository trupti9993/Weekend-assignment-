package com.sydac.bjmmts.hmi.fe.gui.presenter;

import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.BREAK_AIR_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.DOOR_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.PROP_AUXI_BETTERY_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.ACU_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.PIDS_BTN_REQUEST;

import com.sydac.bjmmts.cabscreens.fe.base.CommsNumber;
import com.sydac.bjmmts.cabscreens.fe.gui.AbstractController;
import com.sydac.bjmmts.hmi.fe.base.HMIFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DemoFooterPanelController extends AbstractController
{

  @FXML

  @CommsNumber(DOOR_BTN_REQUEST)
  private Button doorButton;

  @FXML
  @CommsNumber(BREAK_AIR_BTN_REQUEST)
  private Button airBrakesButton;

  @FXML
  @CommsNumber(PROP_AUXI_BETTERY_BTN_REQUEST)
  private Button propAuxBatteryButton;

  @FXML
  @CommsNumber(ACU_BTN_REQUEST)
  private Button acuButton;

  @FXML
  @CommsNumber(PIDS_BTN_REQUEST)
  private Button pidsButton;

  @FXML
  void onAcuBtnAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex).sendButtonClickRequest(ACU_BTN_REQUEST);
  }

  @FXML
  void onPropAuxBatteryBtnAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex)
        .sendButtonClickRequest(PROP_AUXI_BETTERY_BTN_REQUEST);

  }

  @FXML
  void onAirBrakesBtnAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex).sendButtonClickRequest(BREAK_AIR_BTN_REQUEST);

  }

  @FXML
  void onDoorBtnAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex).sendButtonClickRequest(DOOR_BTN_REQUEST);
  }

  @FXML
  void onPidsBtnAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex).sendButtonClickRequest(PIDS_BTN_REQUEST);
  }

  @Override
  public void initializeImpl()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeListeners()
  {
    // TODO Auto-generated method stub

  }

}
