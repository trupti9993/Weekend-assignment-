/*******************************************************************************
 * (C) Copyright 2020 Sydac Pty Ltd., all rights reserved. This is unpublished
 * proprietary source code of Sydac. The copyright notice above does not
 * evidence any actual or intended publication of such source code.
 *******************************************************************************/
package com.sydac.bjmmts.hmi.backend.app.io.properties;

import static com.sydac.foundation.PropertyKey.create;

import com.sydac.common.febe.binding.CommsBinding;
import com.sydac.foundation.PropertyKey;
import com.sydac.project.runtimedata.ProjectObjectConstants.HMIInputs;
import com.sydac.project.runtimedata.ProjectObjectConstants.HMIOutputs;

public interface IAppOutputHeaderPanelProperties
{

  @CommsBinding(property = HMIOutputs.CURRENT_SPEED)
  PropertyKey<Double> CURRENT_SPEED = create();

  @CommsBinding(property = HMIOutputs.TRAIN_NUMBER)
  PropertyKey<byte[]> TRAIN_NUMBER = create();

  @CommsBinding(property = HMIOutputs.NEXT_STATION_NAME)
  PropertyKey<String> NEXT_STATION_ID = create();

  @CommsBinding(property = HMIOutputs.TERMINAL_STATION_NAME)
  PropertyKey<String> DESTINATION_STATION_ID = create();

  @CommsBinding(property = HMIOutputs.LINE_VOLTAGE)
  PropertyKey<Integer> LINE_VOLTAGE = create();

  @CommsBinding(property = HMIOutputs.LINE_CURRENT)
  PropertyKey<Integer> LINE_CURRENT = create();

  @CommsBinding(property = HMIOutputs.HANDLE_POSITION)
  PropertyKey<Integer> HANDLE_POSITION = create();

  @CommsBinding(property = HMIOutputs.FAULT_INDICATOR_STATUS)
  PropertyKey<Integer> FAULT_INDICATOR_STATUS = create();

  @CommsBinding(property = HMIOutputs.COMMUNICATION_LOST)
  PropertyKey<Integer> COMMUNICATION_LOST = create();

  @CommsBinding(property = HMIOutputs.DEMO_SPEED_LIMIT)
  PropertyKey<Integer> DEMO_SPEED_LIMIT = create();

  @CommsBinding(property = HMIOutputs.DEMO_TIME)
  PropertyKey<byte[]> DEMO_TIME = create();

  @CommsBinding(property = HMIOutputs.DEMO_DATE)
  PropertyKey<byte[]> DEMO_DATE = create();
}
