package com.kts.taxify.services.checkout;

import com.kts.taxify.dto.response.PaymentResponse;
import com.kts.taxify.exception.RideNotFoundException;
import com.kts.taxify.model.*;
import com.kts.taxify.repository.ChargeRepository;
import com.kts.taxify.services.auth.GetSelfAsPassenger;
import com.kts.taxify.services.passenger.GetPassengerByCustomerId;
import com.kts.taxify.services.passenger.NotifyPassengerOfPaymentResultForRide;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentTests {

    @Mock
    private GetSelfAsPassenger getSelfAsPassenger;

    @Mock
    private ChargeRepository chargeRepository;

    @Mock
    private Checkout checkout;

    @Mock
    private SaveCharge saveChargeMock;

    @Mock
    private RefundChargedPassengersInRide refundChargedPassengersInRide;

    @Mock
    private GetPassengerByCustomerId getPassengerByCustomerId;
    @Mock
    private CheckoutPassengerForRide checkoutPassengerForRideMock;

    @Mock
    private NotifyPassengerOfPaymentResultForRide notifyPassengerOfPaymentResultForRide;


    @InjectMocks
    private AddPaymentMethodToCustomer addPaymentMethodToCustomer;

    @InjectMocks
    private CreateStripeCustomer createStripeCustomer;

    @InjectMocks
    private CreatePaymentMethod createPaymentMethod;

    @InjectMocks
    private CreatePaymentIntent createPaymentIntent;

    @InjectMocks
    private CheckoutRide checkoutRide;

    @InjectMocks
    private SaveCharge saveCharge;

    @InjectMocks
    private CheckoutPassengerForRide checkoutPassengerForRide;

    @InjectMocks
    private Refund refund;

    private static Customer customer;

    private static Customer customer2;

    private static PaymentMethod paymentMethod;
    private static PaymentMethodCreateParams.CardDetails cardDetails;

    private static Passenger passenger;

    private static Passenger passenger2;

    private static Ride ride;

    @Captor
    private ArgumentCaptor<Charge> chargeArgumentCaptor;

    @BeforeAll
    static void init() {
        Stripe.apiKey = "sk_test_51MRkdwDSxhX8YRHr3mkGtJ7VehBqLGWvVVadF9JIruoksnLsqZ7daUGM0rJhYOy8aUDihEcMeqBfIf1FxSx5kRWa00Qlcw681K";
    }

    @BeforeAll
    static void createCustomer() throws StripeException {
        passenger = Passenger.builder().email("test@gmail.com").name("Marko")
                .surname("Markovic").phoneNumber("+38765656244").build();
        passenger2 = Passenger.builder().email("test@gmail.com").name("Marko")
                .surname("Markovic").phoneNumber("+38765656244").build();
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(passenger.getEmail())
                .setName(passenger.getName() + " " + passenger.getSurname())
                .setPhone(passenger.getPhoneNumber())
                .build();
        customer = createCustomerForPassenger(passenger);
        passenger.setCustomerId(customer.getId());

        customer2 = createCustomerForPassenger(passenger2);
        passenger2.setCustomerId(customer2.getId());


        Vehicle vehicle = new Vehicle();
        Driver driver = Driver.builder().vehicle(vehicle).build();
        ride = Ride.builder().route(Route.builder().price(100.00).build()).driver(driver).status(RideStatus.ACCEPTED).build();
    }

    public static Customer createCustomerForPassenger(Passenger customerPassenger) throws StripeException {
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(customerPassenger.getEmail())
                .setName(customerPassenger.getName() + " " + customerPassenger.getSurname())
                .setPhone(customerPassenger.getPhoneNumber())
                .build();
        return Customer.create(params);
    }

    @BeforeEach
    void createPaymentMethod() throws StripeException {
        cardDetails = new PaymentMethodCreateParams.CardDetails.Builder()
                .setNumber("4242424242424242").setCvc("234").setExpMonth(12L).setExpYear(24L).build();
        PaymentMethodCreateParams createParams = PaymentMethodCreateParams.builder()
                .setCard(cardDetails)
                .setType(PaymentMethodCreateParams.Type.CARD)
                .build();
        paymentMethod = PaymentMethod.create(createParams);
    }


    @Test
    @DisplayName("Should add payment method with visa card for customer")
    public void shouldAddPaymentMethodForCustomer() throws StripeException {

        PaymentMethod actualPaymentMethod = addPaymentMethodToCustomer.execute(paymentMethod, customer.getId());
        assertEquals(customer.getId(), actualPaymentMethod.getCustomer());
        assertEquals("visa", actualPaymentMethod.getCard().getBrand());
    }

    @Test
    @DisplayName("Should create customer with name Marko Markovic")
    public void shouldCreateCustomerWithNameMarkoMarkovic() throws StripeException {

        Customer actualCustomer = createStripeCustomer.execute(passenger);
        assertEquals(passenger.getName() + ' ' + passenger.getSurname(), actualCustomer.getName());
    }

    @Test
    @DisplayName("Should create card")
    public void shouldCreateCardDetails() throws StripeException {

        Customer actualCustomer = createStripeCustomer.execute(passenger);
        assertEquals(passenger.getName() + ' ' + passenger.getSurname(), actualCustomer.getName());
    }

    @Test
    @DisplayName("Should checkout passenger for ride")
    public void shouldCheckoutPassengerForRide() throws StripeException {
        Charge charge = Charge.builder().ride(new Ride()).customerId(passenger.getCustomerId()).build();
        Set<Charge> charges = new HashSet<>();
        charges.add(charge);
        Set<Passenger> passengers = new HashSet<>();
        passengers.add(passenger);
        ride.setPassengersCharges(charges);
        ride.setPassengers(passengers);

        PaymentResponse paymentResponse = PaymentResponse.builder().id("1l").build();

        when(checkout.execute(charge)).thenReturn(paymentResponse);
        when(saveChargeMock.execute(charge)).thenReturn(charge);

        checkoutPassengerForRide.execute(ride, passenger);

        verify(saveChargeMock, times(1)).execute(chargeArgumentCaptor.capture());
        assertEquals(paymentResponse.getId(), chargeArgumentCaptor.getValue().getPaymentId());
    }

    @Test
    @DisplayName("Should throw ride not found when if passenger is not in ride")
    public void shouldThrowExceptionForRideNotFound() throws StripeException {
        Charge charge = Charge.builder().ride(new Ride()).customerId("radnom-id").build();
        Set<Passenger> passengers = new HashSet<>();
        Set<Charge> charges = new HashSet<>();
        charges.add(charge);
        passengers.add(passenger);
        ride.setPassengersCharges(charges);
        ride.setPassengers(passengers);

        RideNotFoundException exception = assertThrows(RideNotFoundException.class, () -> checkoutPassengerForRide.execute(ride, passenger));
        assertEquals("ride_not_found", exception.getKey().getCode());
    }


    @Test
    @DisplayName("Should create payment method with visa card")
    public void shouldCreatePaymentMethodWithVisaCard() throws StripeException {

        PaymentMethod actualPaymentMethod = createPaymentMethod.execute(cardDetails);
        assertEquals("visa", actualPaymentMethod.getCard().getBrand());
        assertEquals(cardDetails.getExpMonth(), actualPaymentMethod.getCard().getExpMonth());
    }

    @Test
    @DisplayName("Should save charge")
    public void shouldSaveCharge() {
        Charge charge = Charge.builder()
                .ride(new Ride())
                .customerId(customer.getId())
                .paymentMethodId(paymentMethod.getId())
                .build();

        when(chargeRepository.save(any(Charge.class))).thenReturn(charge);

        Charge actualCharge = saveCharge.execute(charge);
        assertEquals(charge.getCustomerId(), actualCharge.getCustomerId());
    }

    @Test
    @DisplayName("Should create payment intent for charge")
    public void shouldCreatePaymentIntentForCharge() throws StripeException {
        Charge charge = Charge.builder().ride(new Ride()).customerId(customer.getId()).amount(100.00).paymentMethodId(paymentMethod.getId()).build();

        when(getSelfAsPassenger.execute()).thenReturn(passenger);

        PaymentIntent paymentIntent = createPaymentIntent.execute(charge);
        assertEquals(charge.getCustomerId(), paymentIntent.getCustomer());
        assertEquals(charge.getAmount().longValue(), paymentIntent.getAmount() / 100);
    }

    @Test
    @DisplayName("Should charge one passenger")
    public void shouldChargeOnePassenger() throws StripeException {
        Charge charge = Charge.builder().ride(new Ride()).customerId(customer.getId()).build();
        Set<Charge> charges = new HashSet<>(Collections.singletonList(charge));
        ride.setPassengersCharges(charges);
        PaymentResponse paymentResponse = PaymentResponse.builder().id("1L").build();

        when(getPassengerByCustomerId.execute(charge.getCustomerId())).thenReturn(passenger);
        when(checkoutPassengerForRideMock.execute(ride, passenger)).thenReturn(paymentResponse);

        Boolean paymentDone = checkoutRide.execute(ride);
        assertEquals(true, paymentDone);
        verify(saveChargeMock, times(1)).execute(chargeArgumentCaptor.capture());
        verify(notifyPassengerOfPaymentResultForRide, times(1)).execute(anyString(), anyString());
        assertEquals(true, chargeArgumentCaptor.getValue().getCharged());
    }

    @Test
    @DisplayName("Should charge two passengers")
    public void shouldChargeTwoPassengers() throws StripeException {
        Charge charge = Charge.builder().ride(new Ride()).customerId(customer.getId()).build();
        Charge charge2 = Charge.builder().ride(new Ride()).customerId(customer2.getId()).build();
        Set<Charge> charges = new HashSet<>(Arrays.asList(charge, charge2));
        ride.setPassengersCharges(charges);
        PaymentResponse paymentResponse = PaymentResponse.builder().id("1L").build();
        PaymentResponse paymentResponse2 = PaymentResponse.builder().id("2L").build();

        when(getPassengerByCustomerId.execute(charge.getCustomerId())).thenReturn(passenger);
        when(checkoutPassengerForRideMock.execute(ride, passenger)).thenReturn(paymentResponse);
        when(getPassengerByCustomerId.execute(charge2.getCustomerId())).thenReturn(passenger2);
        when(checkoutPassengerForRideMock.execute(ride, passenger2)).thenReturn(paymentResponse2);

        Boolean paymentDone = checkoutRide.execute(ride);
        assertEquals(true, paymentDone);
        verify(saveChargeMock, times(2)).execute(chargeArgumentCaptor.capture());
        verify(notifyPassengerOfPaymentResultForRide, times(2)).execute(anyString(), anyString());
    }
}
