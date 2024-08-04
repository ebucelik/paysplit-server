package com.ebucelik.paysplit.controller

import com.ebucelik.paysplit.dto.PaymentIntentResponse
import com.stripe.Stripe
import com.stripe.exception.CardException
import com.stripe.exception.InvalidRequestException
import com.stripe.exception.StripeException
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/payment")
class PaymentController() {
    @PostMapping("create-payment-intent")
    fun createPaymentIntent(): ResponseEntity<PaymentIntentResponse> {
        Stripe.apiKey = System.getenv("STRIPE_TEST_KEY")

        val publishableKey = System.getenv("PUBLISHABLE_TEST_KEY")

        //val customerParams = CustomerCreateParams.builder().build()
        //val customer = Customer.create(customerParams)

        val paymentIntentCreateParams = PaymentIntentCreateParams.builder()
            .setAmount(1999L)
            .setCurrency("eur")
            .build()

        try {
            val paymentIntent = PaymentIntent.create(paymentIntentCreateParams)

            val paymentIntentResponse = PaymentIntentResponse(
                paymentIntent.clientSecret,
                publishableKey
            )
            return ResponseEntity.ok(paymentIntentResponse)
        } catch (e: StripeException) {
            println("A stripe exception occurred.")
        } catch (e: CardException) {
            println("A payment error occurred: {}")
        } catch (e: InvalidRequestException) {
            println("An invalid request occurred.")
        } catch (e: java.lang.Exception) {
            println("Another problem occurred, maybe unrelated to Stripe.")
        }

        throw Exception("An error occurred.")
    }
}