package com.example.terdlor_first_bot.collosseum.stateMachine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.action.Action
import org.springframework.statemachine.config.EnableStateMachineFactory
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import org.springframework.statemachine.guard.Guard
import java.util.*


@Configuration
@EnableStateMachineFactory
class StateMachineConfiguration : StateMachineConfigurerAdapter<States, Events>() {

    @Autowired
    @Qualifier("actionCall")
    lateinit var actionCall : Action<States, Events>
    @Autowired
    @Qualifier("actionAnswer")
    lateinit var actionAnswer : Action<States, Events>
    @Autowired
    @Qualifier("actionBat")
    lateinit var actionBat : Action<States, Events>

    @Autowired
    @Qualifier("actionError")
    lateinit var actionError : Action<States, Events>

    @Throws(Exception::class)
    override fun configure(config: StateMachineConfigurationConfigurer<States?, Events?>) {
        config
                .withConfiguration()
                .autoStartup(true)
                //.listener(Listener())
    }

    override fun configure(states: StateMachineStateConfigurer<States, Events>) {
        states
                .withStates()
                .initial(States.INIT)
                .end(States.RESULT)
                .states(EnumSet.allOf(States::class.java))
    }

    @Throws(Exception::class)
    override fun configure(transitions: StateMachineTransitionConfigurer<States, Events>
    ) {
        transitions
                .withExternal()
                .source(States.INIT).target(States.CALLING).event(Events.CALL).action(actionCall).and()
                .withExternal()
                .source(States.CALLING).target(States.READY).event(Events.ANSWER).action(actionAnswer).and()
                .withExternal()
                .source(States.READY).target(States.RESULT).event(Events.BAT).action(actionBat)
    }
}