package com.sydac.bjmmts.hmi.fe.gui.presenter;

import com.sydac.bjmmts.cabscreens.fe.base.CommsNumber;
import com.sydac.bjmmts.cabscreens.fe.gui.AbstractController;
import com.sydac.bjmmts.hmi.fe.base.HMIFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.EVENT_HISTORY_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.EVENT_OVERVIEW_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.TRAIN_STATUS_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.INTERLOCK_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.BRAKE_TEST_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.TRACTION_BREAK_TEST_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.SETTING_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.PROGRAM_VERSION_BTN_REQUEST;
import static com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs.OPERATIONAL_DATA_BTN_REQUEST;

public class DemoMiddlePanelController extends AbstractController
{

  @FXML
  @CommsNumber(EVENT_HISTORY_BTN_REQUEST)
  private Button eventHistoryButton;

  @FXML
  @CommsNumber(EVENT_OVERVIEW_BTN_REQUEST)
  private Button eventOverviewButton;

  @FXML
  @CommsNumber(TRAIN_STATUS_BTN_REQUEST)
  private Button trainStatusButton;

  @FXML
  @CommsNumber(INTERLOCK_BTN_REQUEST)
  private Button interlocksButton;

  @FXML
  @CommsNumber(BRAKE_TEST_BTN_REQUEST)
  private Button brakeTestButton;

  @FXML
  @CommsNumber(TRACTION_BREAK_TEST_BTN_REQUEST)
  private Button tractionBreakTestButton;

  @FXML
  @CommsNumber(SETTING_BTN_REQUEST)
  private Button settingButton;

  @FXML
  @CommsNumber(PROGRAM_VERSION_BTN_REQUEST)
  private Button programVersionButton;

  @FXML
  @CommsNumber(OPERATIONAL_DATA_BTN_REQUEST)
  private Button operationalDataButton;

  @FXML
  void onSettingButtonAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex).sendButtonClickRequest(SETTING_BTN_REQUEST);

  }

  @FXML
  void onBrakeTestButtonAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex).sendButtonClickRequest(BRAKE_TEST_BTN_REQUEST);

  }

  @FXML
  void onEventHistoryButtonAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex).sendButtonClickRequest(EVENT_HISTORY_BTN_REQUEST);

  }

  @FXML
  void onEventOverviewButtonAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex).sendButtonClickRequest(EVENT_OVERVIEW_BTN_REQUEST);

  }

  @FXML
  void onInterlocksButtonAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex).sendButtonClickRequest(INTERLOCK_BTN_REQUEST);

  }

  @FXML
  void onProgramVersionButtonAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex).sendButtonClickRequest(PROGRAM_VERSION_BTN_REQUEST);

  }

  @FXML
  void onTrainStatusButtonAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex).sendButtonClickRequest(TRAIN_STATUS_BTN_REQUEST);

  }

  @FXML
  void onOperationalDataButtonAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex)
        .sendButtonClickRequest(OPERATIONAL_DATA_BTN_REQUEST);

  }

  @FXML
  void onTractionBreakTestButtonAction(ActionEvent event)
  {
    HMIFactory.getServiceRequestHandler(scenarioCarIndex)
        .sendButtonClickRequest(TRACTION_BREAK_TEST_BTN_REQUEST);

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
