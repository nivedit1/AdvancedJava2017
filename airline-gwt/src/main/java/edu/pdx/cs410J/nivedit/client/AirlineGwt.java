package edu.pdx.cs410J.nivedit.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import edu.pdx.cs410J.AirportNames;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt extends Composite implements EntryPoint {

  /** The weirdo interface required by UI Binder */
  public interface Binder extends UiBinder<HorizontalPanel, AirlineGwt> {}

  private static final Binder uiBinder = GWT.create(Binder.class);

  private final Alerter alerter;
  private final AirlineServiceAsync airlineService;
  private final Logger logger;

  @UiField
  @VisibleForTesting
  Button submitAirline;

  @UiField
  @VisibleForTesting
  TextBox airlineName;

  @UiField
  @VisibleForTesting
  TextBox flightNumber;

  @UiField
  @VisibleForTesting
  ListBox src;

  @UiField
  @VisibleForTesting
  ListBox dest;

  @UiField
  @VisibleForTesting
  DialogBox dialogBox;

  @UiField
  @VisibleForTesting
  DateBox departTimeInDate;

  @UiField
  @VisibleForTesting
  DateBox arriveTimeInDate;

  @UiField
  TextBox airlineNameToSearch;

  @UiField
  ListBox srcToSearch;

  @UiField
  ListBox destToSearch;


  Button okButton = new Button("OK");

  public AirlineGwt() {
    this(new Alerter() {
      @Override
      public void alert(String message) {
        Window.alert(message);
      }
    });
  }

  @VisibleForTesting
  AirlineGwt(Alerter alerter) {
    this.alerter = alerter;
    this.airlineService = GWT.create(AirlineService.class);
    this.logger = Logger.getLogger("airline");
    Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
  }

  private void alertOnException(Throwable throwable) {
    Throwable unwrapped = unwrapUmbrellaException(throwable);
    StringBuilder sb = new StringBuilder();
    sb.append(unwrapped.toString());
    sb.append('\n');

    for (StackTraceElement element : unwrapped.getStackTrace()) {
      sb.append("  at ");
      sb.append(element.toString());
      sb.append('\n');
    }

    this.alerter.alert(sb.toString());
  }

  private Throwable unwrapUmbrellaException(Throwable throwable) {
    if (throwable instanceof UmbrellaException) {
      UmbrellaException umbrella = (UmbrellaException) throwable;
      if (umbrella.getCauses().size() == 1) {
        return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
      }

    }

    return throwable;
  }

  private void addEventHandlers() {
    dialogBox.setWidth("300");
    dialogBox.setHeight("300");
    dialogBox.center();
    dialogBox.add(okButton);
    dialogBox.hide();
    submitAirline.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showAirline();
      }
    });
    flightNumber.addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(BlurEvent blurEvent) {
        int flightNumberInInteger;
        try {
          flightNumberInInteger = Integer.parseInt(flightNumber.getText());
          if (flightNumberInInteger < 0) {
            dialogBox.setText("Flight number should not be negative");
            dialogBox.show();
          }
        } catch (Exception e) {
          dialogBox.setText("Flight number should be a number - " + flightNumber.getText());
          dialogBox.show();
        }
      }
    });
    okButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        dialogBox.hide();
      }
    });
    departTimeInDate.addValueChangeHandler(new ValueChangeHandler<Date>() {
      @Override
      public void onValueChange(ValueChangeEvent<Date> valueChangeEvent) {
        Date date = valueChangeEvent.getValue();
        departTimeInDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy HH:mm a")));
      }
    });
    arriveTimeInDate.addValueChangeHandler(new ValueChangeHandler<Date>() {
      @Override
      public void onValueChange(ValueChangeEvent<Date> valueChangeEvent) {
        Date date = valueChangeEvent.getValue();
        arriveTimeInDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy HH:mm a")));
      }
    });
  }

  private void throwClientSideException() {
    logger.info("About to throw a client-side exception");
    throw new IllegalStateException("Expected exception on the client side");
  }

  private void showUndeclaredException() {
    logger.info("Calling throwUndeclaredException");
    airlineService.throwUndeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }

  private void showDeclaredException() {
    logger.info("Calling throwDeclaredException");
    airlineService.throwDeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }

  private void showAirline() {
    logger.info("Calling getAirline");
    airlineService.getAirline(new AsyncCallback<Airline>() {

      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Airline airline) {
        StringBuilder sb = new StringBuilder(airline.toString());
        Collection<Flight> flights = airline.getFlights();
        for (Flight flight : flights) {
          sb.append(flight);
          sb.append("\n");
        }
        alerter.alert(sb.toString());
      }
    });
  }

  @Override
  public void onModuleLoad() {
    setUpUncaughtExceptionHandler();

    // The UncaughtExceptionHandler won't catch exceptions during module load
    // So, you have to set up the UI after module load...
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        setupUI();
      }
    });

  }

  private void setupUI() {
    Map<String,String> airportNames = AirportNames.getNamesMap();
    RootPanel rootPanel = RootPanel.get();
    rootPanel.add(uiBinder.createAndBindUi(this));
    addEventHandlers();
    for (String key: airportNames.keySet()){
      src.addItem(key);
      dest.addItem(key);
      srcToSearch.addItem(key);
      destToSearch.addItem(key);
    }
  }

  private void setUpUncaughtExceptionHandler() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable throwable) {
        alertOnException(throwable);
      }
    });
  }

  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }
}
