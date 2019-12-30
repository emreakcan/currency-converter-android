package com.emre.currency

import com.emre.currency.rules.InstantExecutorExtension
import com.emre.currency.rules.RxImmediateSchedulerRule
import io.mockk.MockKAnnotations
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach

/**
 * Created on 2019-12-10.
 * @author EMRE AKCAN
 */
open class BaseTest {

    @get:Rule
    var instantExecutor = InstantExecutorExtension()

    @get:Rule
    var testSchedulerRule = RxImmediateSchedulerRule()

    @BeforeEach
    open fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)


}