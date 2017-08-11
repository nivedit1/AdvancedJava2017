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
import java.util.ArrayList;
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

  @UiField
  Button searchAirline;

  @UiField
  Button help;

  @UiField
  TextArea prettyText;

  @UiField
  Button close;


  Button okButton = new Button("OK");

  int flightNumberInInteger;
  Date departTimeInDateValue;
  Date arriveTimeInDateValue;

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
        postAirline();
      }
    });
    flightNumber.addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(BlurEvent blurEvent) {
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
        departTimeInDateValue = departTimeInDate.getValue();
      }
    });
    arriveTimeInDate.addValueChangeHandler(new ValueChangeHandler<Date>() {
      @Override
      public void onValueChange(ValueChangeEvent<Date> valueChangeEvent) {
        Date date = valueChangeEvent.getValue();
        arriveTimeInDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy HH:mm a")));
        arriveTimeInDateValue = arriveTimeInDate.getValue();
      }
    });
    searchAirline.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showAirline();
      }
    });
    help.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        String readMeText = getReadme();
        prettyText.setText(readMeText);
        prettyText.setVisible(true);
        close.setVisible(true);
      }
    });
    close.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        prettyText.setVisible(false);
        close.setVisible(false);
      }
    });
  }

  private void postAirline() {
    logger.info("Posting airline");
    Collection<Flight> flights = new ArrayList<>();
    Flight flight = new Flight(flightNumberInInteger, src.getSelectedValue(),departTimeInDateValue,dest.getSelectedValue(),arriveTimeInDateValue);
    Airline airline = new Airline(airlineName.getText(),flights);
    airline.addFlight(flight);
    airlineService.postAirline(airline.getName(),flight, new AsyncCallback<String>() {

      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(String successText) {
        if(successText.equals("Success")){
          String message = "Successfully added flight " +flightNumber.getText()+" to airline "+airlineName.getText();
          dialogBox.setText(message);
          dialogBox.show();
        }
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
    airlineService.getAirline(airlineName.getText(),srcToSearch.getSelectedValue(),destToSearch.getSelectedValue(),new AsyncCallback<Airline>() {

      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Airline airline) {
        Collection<Flight> flights = airline.getFlights();
        if(flights.isEmpty()){
          String message = "Flights starting from " + srcToSearch.getSelectedValue()+ " landing in "+
                  destToSearch.getSelectedValue()+" not found!";
          dialogBox.setText(message);
          dialogBox.show();
        }
        else {
          StringBuilder sb = new StringBuilder(airline.toString());
          for (Flight flight : flights) {
            sb.append(flight);
            sb.append("\n");
          }
          prettyText.setText(sb.toString());
          prettyText.setVisible(true);
          close.setVisible(true);
        }
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

  private static String getReadme() {

    String title = "\nName of the project:\n" +
            "====================\n" +
            "CS410J Project 4: A REST-ful Airline Web Service";
    String applicationDescription = "\n\nApplication Description:\n" +
            "========================\n" +
            "This application parses the command line arguments provided by the user " +
            "and assigns the values to the airline and the respective flight stated by the user.\n" +
            "It also checks the validity of flight number, airport codes, and " +
            "arrival and departure times.\n"+
            "It optionally adds the airline information to a file specified on the command line.\n"
            +"It optionally pretty prints the airline information.\n"
            +"It optionally posts to a host and port mentioned by a -host and -port options. \n" +
            "This also lets the user search for flights in an airline between a source and destination.\n";
    String argumentList = "\nArguments List:" +
            "\n===============\n" +
            "Pass arguments to this program as follows:\n" +
            "  name -> The name of the airline  [Format: String]\n" +
            "  flightNumber -> The Flight Number which identifies the flight  [Format: Numeric Code]\n" +
            "  src -> Code of Departure airport  [Format: \"AAA\" (3 Letter Code)]\n" +
            "  departTime -> Departure date and time am/pm  [Format: mm/dd/yyyy hh:mm am/pm]\n" +
            "  dest -> Code of Arrival airport  [Format: \"AAA\" (3 Letter Code)]\n" +
            "  arriveTime -> Arrival date and time am/pm  [Format: mm/dd/yyyy hh:mm am/pm]\n\n";
    String options = "Options:" +
            "\n========\n" +
            "Options are as follows:\n" +
            "  -print -> Prints a description of the new flight\n" +
            "  -README -> Prints a README for this project and exits\n" +
            "  -search -> Search for flights\n" +
            "  -host hostname -> Search for flights\n" +
            "  -port port -> Port on which the server is listening\n";
    String executionInstruction = "\nExecution Instruction:" +
            "\n======================\n" +
            "Run the program with -> java edu.pdx.cs410J.nivedit.Project4 [options] <args>";
    String developedBy = "\n\nDeveloped By:\n" +
            "=============" +
            "\n-Niveditha Venugopal" +
            "\n-Email ID: nivedit@pdx.edu" +
            "\n-PSU ID:   978073102";
    String readMeText = title + applicationDescription + argumentList + options + executionInstruction + developedBy;
    return readMeText;
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
