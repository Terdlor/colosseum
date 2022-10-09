package com.example.terdlor_first_bot.stateMachine

import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.config.EnableStateMachineFactory
import org.springframework.statemachine.config.StateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import java.util.*


@Configuration
@EnableStateMachineFactory
class StateMachineConfiguration : StateMachineConfigurerAdapter<States, Events>() {

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
                .initial(States.STATE1)
                .end(States.STATE4)
                .states(EnumSet.allOf(States::class.java))
    }

    @Throws(Exception::class)
    override fun configure(transitions: StateMachineTransitionConfigurer<States, Events>
    ) {
        transitions
                .withExternal()
                .source(States.STATE1).target(States.STATE2).event(Events.EVENT1).action(ActionWork()).and()
                .withExternal()
                .source(States.STATE2).target(States.STATE3).event(Events.EVENT2).action(ActionWork()).and()
                .withExternal()
                .source(States.STATE3).target(States.STATE4).event(Events.EVENT3).action(ActionWork())
    }
}