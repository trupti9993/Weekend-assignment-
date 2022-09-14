package com.sydac.bjmmts.hmi.fe.gui.presenter;

import com.sydac.bjmmts.cabscreens.fe.gui.AbstractController;
import com.sydac.bjmmts.hmi.fe.gui.binder.DateTimeBinder;
import com.sydac.bjmmts.hmi.fe.gui.model.DateTimeModel;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DemoHeaderController extends AbstractController
{

  @FXML
  private Label dateLabel;

  @FXML
  private Label timeLabel;

  @FXML
  private Label stationLabel;

  @FXML
  private Label endStationLabel;

  @FXML
  private Label trainSpeedLabel;

  private DateTimeModel dateTimeModel;

  private ChangeListener<String> dateListener;

  private ChangeListener<String> timeListener;

  @Override
  public void initializeImpl()
  {
    dateTimeModel = new DateTimeModel(scenarioCarIndex);
    screenBinders.add(new DateTimeBinder(scenarioCarIndex, dateTimeModel));

    this.dateListener = (observable, oldVal, newVal) -> updateDate(newVal);
    this.dateTimeModel.getDate().addListener(this.dateListener);

    this.timeListener = (observable, oldVal, newVal) -> updateTime(newVal);
    this.dateTimeModel.getTime().addListener(this.timeListener);

    updateDate(this.dateTimeModel.getDate().getValue());
    updateTime(this.dateTimeModel.getTime().getValue());

  }

  private void updateTime(String newVal)
  {
    System.out.println("DateTimeController.updateTime() --> " + newVal);
    timeLabel.setText(newVal);
  }

  private void updateDate(String newVal)
  {
    System.out.println("DateTimeController.updateDate() --> " + newVal);
    dateLabel.setText(newVal);
  }

  @Override
  public void removeListeners()
  {
    // TODO Auto-generated method stub

  }

}
