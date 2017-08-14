package edu.pdx.cs410J.nivedit.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import edu.pdx.cs410J.AirportNames;
import java.io.IOException;
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

  /** The interface required by UI Binder */
  public interface Binder extends UiBinder<HorizontalPanel, AirlineGwt> {}

  /**
   * This creates the UI widgets
   */
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

  /**
   * This method adds the event handlers for different UI widgets
   */
  private void addEventHandlers() {
    dialogBox.setWidth("300");
    dialogBox.setHeight("300");
    dialogBox.center();
    dialogBox.add(okButton);
    dialogBox.hide();
    submitAirline.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        prettyText.setVisible(false);
        close.setVisible(false);
        if(airlineName.getText().isEmpty()){
          String message = "Please provide airline name";
          dialogBox.setText(message);
          dialogBox.show();
          return;
        } else if(flightNumber.getText().isEmpty()){
          String message = "Please provide flight number";
          dialogBox.setText(message);
          dialogBox.show();
          return;
        } else if(src.getSelectedValue().equals(null)){
          String message = "Please provide source airport code";
          dialogBox.setText(message);
          dialogBox.show();
          return;
        }else if(dest.getSelectedValue().equals(null)) {
          String message = "Please provide destination airport code";
          dialogBox.setText(message);
          dialogBox.show();
          return;
        }else if(departTimeInDate.getTextBox().getText().isEmpty()) {
          String message = "Please provide departure time for the flight";
          dialogBox.setText(message);
          dialogBox.show();
          return;
        } else if(arriveTimeInDate.getTextBox().getText().isEmpty()) {
          String message = "Please provide arrival time for the flight";
          dialogBox.setText(message);
          dialogBox.show();
          return;
        } else {
          try {
            flightNumberInInteger = Integer.parseInt(flightNumber.getText());
            if (flightNumberInInteger < 0) {
              dialogBox.setText("Flight Number should not be negative - " + flightNumber.getText());
              dialogBox.show();
              return;
            }
          } catch (Exception e) {
            dialogBox.setText("Flight Number is not a number - " + flightNumber.getText());
            dialogBox.show();
            return;
          }
          try {
            com.google.gwt.i18n.shared.DateTimeFormat formatter = DateTimeFormat.getFormat("MM/dd/yyyy HH:mm a");
            formatter.format(departTimeInDate.getValue());

          } catch (Exception e){
            String message = "The Departure time does not match the format \"MM/DD/YY HH:MM\" - " +
                    departTimeInDate.getTextBox().getText();
            dialogBox.setText(message);
            dialogBox.show();
            return;
          }
          try {
            com.google.gwt.i18n.shared.DateTimeFormat formatter = DateTimeFormat.getFormat("MM/dd/yyyy HH:mm a");
            formatter.format(arriveTimeInDate.getValue());

          } catch (Exception e){
            String message = "The arrival time does not match the format \"MM/DD/YY HH:MM\" - " +
                    arriveTimeInDate.getTextBox().getText();
            dialogBox.setText(message);
            dialogBox.show();
            return;
          }
        }
        postAirline();
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
        departTimeInDate.setFormat(new DateBox.DefaultFormat
                (com.google.gwt.i18n.client.DateTimeFormat.getFormat("MM/dd/yyyy HH:mm a")));
        departTimeInDateValue = departTimeInDate.getValue();
      }
    });
    arriveTimeInDate.addValueChangeHandler(new ValueChangeHandler<Date>() {
      @Override
      public void onValueChange(ValueChangeEvent<Date> valueChangeEvent) {
        Date date = valueChangeEvent.getValue();
        arriveTimeInDate.setFormat(new DateBox.DefaultFormat
                (com.google.gwt.i18n.client.DateTimeFormat.getFormat("MM/dd/yyyy HH:mm a")));
        arriveTimeInDateValue = arriveTimeInDate.getValue();
      }
    });
    searchAirline.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        prettyText.setVisible(false);
        close.setVisible(false);
        if(airlineNameToSearch.getText().isEmpty()) {
          String message = "Please provide airline name";
          dialogBox.setText(message);
          dialogBox.show();
          return;
        }else if(srcToSearch.getSelectedValue().equals(null)){
          String message = "Please provide source airport code";
          dialogBox.setText(message);
          dialogBox.show();
          return;
        }else if(destToSearch.getSelectedValue().equals(null)) {
          String message = "Please provide destination airport code";
          dialogBox.setText(message);
          dialogBox.show();
          return;
        }
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
        return;
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

  /**
   * This method is called when the submit button is clicked on the UI
   * after input validation.
   */
  private void postAirline() {
    logger.info("Posting airline");
    Collection<Flight> flights = new ArrayList<>();
    Flight flight = new Flight(flightNumberInInteger, src.getSelectedValue(),
            departTimeInDateValue,dest.getSelectedValue(),arriveTimeInDateValue);
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
          return;
        }
      }
    });
  }

  /**
   * This method is called when the search button is clicked
   * after input validation.
   */
  private void showAirline(){
    logger.info("Calling getAirline");
    airlineService.getAirline(airlineNameToSearch.getText(),srcToSearch.getSelectedValue(),destToSearch.getSelectedValue(),new AsyncCallback<Airline>() {

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
          PrettyPrinter prettyPrinter = new PrettyPrinter(prettyText);
          try {
            prettyPrinter.dump(airline);
          } catch (IOException e){
            alertOnException(e);
          }
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

  /**
   * This method sets up the UI when the module is loaded
   */
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

  /**
   * This method gets the ReadMe text for the application
   * @return readMe text
   */
  private static String getReadme() {

    String title = "Name of the project:\n" +
            "====================\n" +
            "CS410J Project 5: A Rich Internet Application for an Airline";
    String applicationDescription = "\n\nApplication Description:\n" +
            "========================\n" +
            "This application lets you add and search for flights." +
            "You can search for flights in an airline by providing the airline name, source and destination. " +
            "Flights are sorted based on their Airport Source and Departure Times.\n";
    String argumentList = "\nArguments List:" +
            "\n===============\n" +
            "Pass arguments to this application as follows:\n" +
            "  Airline Name -> The name of the airline  [Format: String]\n" +
            "  Flight Number -> The Flight Number which identifies the flight  [Format: Numeric Code]\n" +
            "  Source -> Code of Departure airport  [Format: \"AAA\" (3 Letter Code)]\n" +
            "  Destination -> Code of Arrival airport  [Format: \"AAA\" (3 Letter Code)]\n" +
            "  departTime -> Departure date and time am/pm  [Format: mm/dd/yyyy hh:mm am/pm]\n" +
            "  arriveTime -> Arrival date and time am/pm  [Format: mm/dd/yyyy hh:mm am/pm]\n";
    String developedBy = "\nDeveloped By:\n" +
            "=============" +
            "\nNiveditha Venugopal" +
            "\nEmail ID: nivedit@pdx.edu" +
            "\nPSU ID:   978073102";
    String readMeText = title + applicationDescription + argumentList + developedBy;
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
  interface DialogBoxMessage {
    void alert(String message);
  }
}
