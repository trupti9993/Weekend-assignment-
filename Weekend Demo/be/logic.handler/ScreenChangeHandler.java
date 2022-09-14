/*
 * ================================================================== (C)
 * Copyright 2020 Sydac Pty Ltd., all rights reserved. This is unpublished
 * proprietary source code of Sydac. The copyright notice above does not
 * evidence any actual or intended publication of such source code.
 * ==================================================================
 */
package com.sydac.bjmmts.hmi.backend.logic.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sydac.common.registry.Registry;
import com.sydac.foundation.IModelListener;
import com.sydac.foundation.PropertyKey;
import com.sydac.bjmmts.hmi.backend.app.io.properties.IAppInputsScreenChangeProperties;
import com.sydac.bjmmts.hmi.backend.comms.CommsBinding;
import com.sydac.bjmmts.hmi.backend.logic.AbstractFunctionalLogic;
import com.sydac.bjmmts.hmi.backend.logic.SharedDataModel;
import com.sydac.bjmmts.hmi.backend.logic.SharedDataModel.Change;
import com.sydac.bjmmts.hmi.backend.logic.annotation.FunctionalLogicHandler;
import com.sydac.bjmmts.hmi.common.ScreenId;
import com.sydac.bjmmts.hmi.common.io.IAppFooterPanelOutputHandler;
import com.sydac.bjmmts.hmi.common.state.ApplicationConstants;
import com.sydac.bjmmts.hmi.common.utils.timer.CommsTimer;

/**
 * This handler class is used for screen change handling. All screen change
 * logic
 * 
 * @author potdars
 *
 */
@Service
@FunctionalLogicHandler(arguments = { CommsBinding.class, SharedDataModel.class })
public class ScreenChangeHandler extends AbstractFunctionalLogic implements IScreenChangeHandler
{
  private IAppFooterPanelOutputHandler appFooterPanelOutputHandler;

  private final SharedDataModel shareDataModel;

  private CommsTimer powerUpSplashScreenTimer;

  private CommsTimer powerUpBlueScreenTimer;

  /**
   * The screen change listener map. Currently supports only on
   * {@link IScreenChangeListener} for one screen. If more than one listener
   * added for same screen, first listener will be removed.
   */
  private Map<ScreenId, IScreenChangeListener> screenChangeListenerMap = new HashMap<>();

  /** The current screen. */
  private ScreenId currentScreen = ScreenId.BLANK_SCREEN;

  /**
   * Listener to update HMI Power State
   */
  IModelListener hmiStateListener = (arg0, arg1) -> processTMSPowerState();

  private CommsBinding commsBinding;

  /** Runnable to perform actions of start up sequence */
  private Runnable powerUpSplashScreenRunnable = () -> {
    if (powerUpSplashScreenTimer != null)
    {
      powerUpSplashScreenTimer.safeCancel();

      // Display Blue screen and start its timer
      setCurrentScreen(ScreenId.POWER_ON_BLUE_SCREEN);
      if (!powerUpBlueScreenTimer.isRunning())
      {
        powerUpBlueScreenTimer.start();
      }
    }
  };

  /** Runnable to perform actions of start up sequence */
  private Runnable powerUpBlueScreenRunnable = () -> {
    if (powerUpBlueScreenTimer != null)
    {
      powerUpBlueScreenTimer.safeCancel();
      setCurrentScreen(ScreenId.OPERATION_SCREEN);
    }
  };

  public ScreenChangeHandler(CommsBinding commsBinding, SharedDataModel aShareDataModel)
  {
    super(commsBinding, IAppInputsScreenChangeProperties.class);

    this.commsBinding = commsBinding;

    shareDataModel = aShareDataModel;
    initializeOutputHandler();
    int splashScreenTime = Registry.getInstance().getInteger(ApplicationConstants.DOMAIN_NAME,
        ApplicationConstants.SPLASH_SCREEN_TIME, 10000); // time in millisecond
    int blueScreenTime = Registry.getInstance().getInteger(ApplicationConstants.DOMAIN_NAME,
        ApplicationConstants.BLUE_SCREEN_TIME, 10000); // time in millisecond

    powerUpSplashScreenTimer = CommsTimer.newScheduledTimer(powerUpSplashScreenRunnable, splashScreenTime);
    powerUpBlueScreenTimer = CommsTimer.newScheduledTimer(powerUpBlueScreenRunnable, blueScreenTime);
  }

  /**
   * Registering ScreenChangeListeners can be done from BusinessLogic.
   * 
   * @param screenId
   *          the screen id
   * @param screenChangeListener
   *          the screen change listener
   */
  public final void register(ScreenId screenId, IScreenChangeListener screenChangeListener)
  {
    screenChangeListenerMap.put(screenId, screenChangeListener);

  }

  @Override
  public void attachListeners()
  {
    super.attachListeners();
    shareDataModel.addModelListener(hmiStateListener, Change.HMI_STATE, true);
    initializeOutputHandler();
  }

  private void initializeOutputHandler()
  {
    if (appFooterPanelOutputHandler == null)
      appFooterPanelOutputHandler = (IAppFooterPanelOutputHandler)commsBinding.getAppOutputsSetter();
  }

  @Override
  public void detachListeners()
  {
    super.detachListeners();
    shareDataModel.removeModelListener(hmiStateListener, Change.HMI_STATE);
    appFooterPanelOutputHandler = null;
  }

  /**
   * Notify leaving.
   * 
   * @param screenId
   *          the screen id
   */
  private void notifyLeaving(ScreenId screenId)
  {
    if (screenChangeListenerMap.containsKey(screenId))
    {
      screenChangeListenerMap.get(screenId).onLeavingScreen(screenId.getId());
    }
  }

  /**
   * Notify entering.
   * 
   * @param screenId
   *          the screen id
   */
  private void notifyEntering(ScreenId screenId)
  {
    if (screenChangeListenerMap.containsKey(screenId))
    {
      screenChangeListenerMap.get(screenId).onEnteringScreen(screenId.getId());
    }
  }

  private void processScreenChangeRequest(int screenChangeRequest)
  {
    if (screenChangeRequest > 0)
    {
      ScreenId request = ScreenId.valueOf(screenChangeRequest);
      setCurrentScreen(request);
    }
  }

  /**
   * While TMS is power up then it display Splash screen for 10 second and Blue
   * screen for 20 second (as part of startup sequence) then it display Run
   * interface screen.
   */
  private void processTMSPowerState()
  {
    if (shareDataModel.isHMIPowered())
    {
      setCurrentScreen(ScreenId.BLANK_SCREEN);
      if (!powerUpSplashScreenTimer.isRunning())
      {
        powerUpSplashScreenTimer.start();
      }
    }
    else
    {
      powerUpSplashScreenTimer.safeCancel();
      powerUpBlueScreenTimer.safeCancel();
      if (appFooterPanelOutputHandler != null)
      {
        setCurrentScreen(ScreenId.BLANK_SCREEN);
      }
    }
  }

  protected void setCurrentScreen(ScreenId request)
  {
    ScreenId newScreen = request;
    if (currentScreen != newScreen)
    {
      notifyLeaving(currentScreen);
      currentScreen = newScreen;
      notifyEntering(currentScreen);
    }
    appFooterPanelOutputHandler.setCurrentScreenId(currentScreen.getId());
    shareDataModel.setScreenId(currentScreen.getId());
  }

  @Override
  public <T> void appInputsPropertyUpdated(PropertyKey<T> key, T oldValue, T newValue, int vehicleIndex)
  {
    initializeOutputHandler();
    if (getActiveVehicleIndex() == vehicleIndex)
    {
      if (key.equals(IAppInputsScreenChangeProperties.SCREEN_CHANGE_REQUEST))
      {
        processScreenChangeRequest((Integer)newValue);
      }
      else if (key.equals(IAppInputsScreenChangeProperties.DEMO_SPEED_LIMIT))
      {
        System.out.println("ScreenChangeHandler.appInputsPropertyUpdated() " + newValue);
        appFooterPanelOutputHandler.setDemoSpeedLimit((Integer)newValue);
        if ((Integer)newValue > 0)
        {
          processScreenChangeRequest(ScreenId.CURRENT_TRAIN_SPEED.getId());
        }

        appFooterPanelOutputHandler.setDemoDate("13/09/2022".getBytes());
        appFooterPanelOutputHandler.setDemoTime("15:44:52".getBytes());
      }

      // if
      // (IAppInputsScreenChangeProperties.MAIN_MENU_BUTTON_PRESSED.equals(key)
      // && (Integer)newValue > 0)
      // {
      // processScreenChangeRequest(ScreenId.MAIN_MENU_SCREEN.getId());
      // }
      // else if
      // (IAppInputsScreenChangeProperties.OPERATION_BUTTON_PRESSED.equals(key)
      // && (Integer)newValue > 0)
      // {
      // processScreenChangeRequest(ScreenId.OPERATION_SCREEN.getId());
      // }
      // else if
      // (IAppInputsScreenChangeProperties.BRAKE_BUTTON_PRESSED.equals(key) &&
      // (Integer)newValue > 0)
      // {
      // processScreenChangeRequest(ScreenId.BRAKE_SCREEN.getId());
      // }
      // else if
      // (IAppInputsScreenChangeProperties.TRACTION_AUXILIARY_BUTTON_PRESSED.equals(key)
      // && (Integer)newValue > 0)
      // {
      // processScreenChangeRequest(ScreenId.TRACTION_AUXILIARY_SCREEN.getId());
      // }
      // else if
      // (IAppInputsScreenChangeProperties.NETWORK_BUTTON_PRESSED.equals(key) &&
      // (Integer)newValue > 0)
      // {
      // processScreenChangeRequest(ScreenId.NETWORK_SCREEN.getId());
      // }
      // else if
      // (IAppInputsScreenChangeProperties.ANNOUNCEMENT_BUTTON_PRESSED.equals(key)
      // && (Integer)newValue > 0)
      // {
      // processScreenChangeRequest(ScreenId.ANNOUNCEMENT_SCREEN.getId());
      // }
      // else if
      // (IAppInputsScreenChangeProperties.AIR_CONDITIONING_BUTTON_PRESSED.equals(key)
      // && (Integer)newValue > 0)
      // {
      // processScreenChangeRequest(ScreenId.AIR_CONDITIONING_SCREEN.getId());
      // }

      handleEventButtonClickRequest(key, newValue);

    }
  }

  private <T> void handleEventButtonClickRequest(PropertyKey<T> key, T newValue)
  {}

  @Override
  public void requestScreenChange(ScreenId newScreenId)
  {
    setCurrentScreen(newScreenId);
  }
}
