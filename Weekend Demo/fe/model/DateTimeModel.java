package com.sydac.bjmmts.hmi.fe.gui.model;

import com.sydac.bjmmts.cabscreens.fe.gui.model.AbstractScreenModel;
import com.sydac.bjmmts.hmi.common.io.IAppDemoMainScreenHandler;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DateTimeModel extends AbstractScreenModel implements IAppDemoMainScreenHandler
{

  StringProperty date = new SimpleStringProperty();

  StringProperty time = new SimpleStringProperty();

  public DateTimeModel(int carIndex)
  {
    super(carIndex);
  }

  public StringProperty getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    System.out.println("DateTimeModel.setDate()");
    this.date.set(date);
  }

  public StringProperty getTime()
  {
    return time;
  }

  public void setTime(String time)
  {
    this.time.set(time);
  }

}
