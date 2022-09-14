package com.sydac.bjmmts.hmi.fe.gui.binder;

import com.sydac.bjmmts.cabscreens.fe.gui.binder.AbstractScreenBinder;
import com.sydac.bjmmts.hmi.common.io.IAppDemoMainScreenHandler;
import com.sydac.project.runtimedata.ProjectObjectConstants;

public class DateTimeBinder extends AbstractScreenBinder
{

  private IAppDemoMainScreenHandler dateTimeHandler;

  public DateTimeBinder(int carIndex, IAppDemoMainScreenHandler dateTimeHandler)
  {
    super(carIndex);
    this.dateTimeHandler = dateTimeHandler;
  }

  @Override
  public void doBinding()
  {

    frontendDataModel.bindIORawData(ProjectObjectConstants.HMIOutputs.DEMO_DATE,
        () -> dateTimeHandler.setDate(new String(
            frontendDataModel.getIORawData(ProjectObjectConstants.HMIOutputs.DEMO_DATE))));

    frontendDataModel.bindIORawData(ProjectObjectConstants.HMIOutputs.DEMO_TIME,
        () -> dateTimeHandler.setTime(new String(
            frontendDataModel.getIORawData(ProjectObjectConstants.HMIOutputs.DEMO_TIME))));
  }

}
